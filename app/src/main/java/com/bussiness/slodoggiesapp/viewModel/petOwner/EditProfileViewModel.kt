package com.bussiness.slodoggiesapp.viewModel.petOwner

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.bussiness.slodoggiesapp.data.remote.Repository
import com.bussiness.slodoggiesapp.navigation.Routes
import com.bussiness.slodoggiesapp.network.Resource
import com.bussiness.slodoggiesapp.util.Messages
import com.bussiness.slodoggiesapp.util.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

data class EditProfileUiState(
    val name: String = "",
    val mobileNumber: String = "",
    val email: String = "",
    val bio: String = "",
    val relation: String = "",
    val showRelationDropdown: Boolean = false,
    var profileImageUri: Uri? = null,
    val showImagePickerDialog: Boolean = false,
    val isMobileVerified: Boolean = false,
    val isEmailVerified: Boolean = false,
    val updateProfileDialog: Boolean = false,
    val isLoading: Boolean = false,
    val relationOptions: List<String> = listOf("Father", "Mother", "Partner", "Etc")
)

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val repository: Repository,
    private val app: Application,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(EditProfileUiState())
    val uiState: StateFlow<EditProfileUiState> = _uiState

    // user selected images (URIs)
    private val _selectedPhotos = MutableStateFlow<List<Uri>>(emptyList())
    val selectedPhotos: StateFlow<List<Uri>> = _selectedPhotos


    // ------------ FIELD UPDATES ------------
    fun updateName(name: String) = _uiState.update { it.copy(name = name) }

    fun updateBio(bio: String) = _uiState.update { it.copy(bio = bio) }

    fun updateRelation(relation: String) =
        _uiState.update { it.copy(relation = relation, showRelationDropdown = false) }

    fun updateEmail(email: String) = _uiState.update { it.copy(email = email) }

    fun updateMobileNumber(mobileNumber: String) =
        _uiState.update { it.copy(mobileNumber = mobileNumber.take(10)) }

    fun updateProfileImage(uri: Uri) {
        _uiState.update { it.copy(profileImageUri = uri) }
        _selectedPhotos.value = listOf(uri)   // only 1 profile image
    }

    // ------------ DROPDOWNS & DIALOGS ------------
    fun toggleRelationDropdown() =
        _uiState.update { it.copy(showRelationDropdown = !it.showRelationDropdown) }

    fun toggleImagePickerDialog() =
        _uiState.update { it.copy(showImagePickerDialog = !it.showImagePickerDialog) }

    fun hideImagePickerDialog() =
        _uiState.update { it.copy(showImagePickerDialog = false) }

    fun toggleUpdateProfileDialog() =
        _uiState.update { it.copy(updateProfileDialog = !it.updateProfileDialog) }

    fun hideUpdateProfileDialog(navController: NavController) {
        _uiState.update { it.copy(updateProfileDialog = false) }
        navController.navigate(Routes.PET_PROFILE_SCREEN)
    }


    // ------------ VERIFICATION FLAGS ------------
    fun setPhoneVerified(verified: Boolean) =
        _uiState.update { it.copy(isMobileVerified = verified) }

    fun setEmailVerified(verified: Boolean) =
        _uiState.update { it.copy(isEmailVerified = verified) }


    fun onVerify(navController: NavHostController, type: String, data: String) {
        navController.navigate("${Routes.VERIFY_ACCOUNT_SCREEN}?type=$type&data=$data")
    }


    // -----------------------------------------------------
    // convert URI -> Multipart
    // -----------------------------------------------------
    private fun uriToMultipart(context: Context, uri: Uri): MultipartBody.Part? {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri) ?: return null

            val file = File.createTempFile("profile_", ".jpg", context.cacheDir)
            val output = FileOutputStream(file)
            inputStream.copyTo(output)
            output.close()
            inputStream.close()

            val req = file.asRequestBody("image/*".toMediaTypeOrNull())
            MultipartBody.Part.createFormData("profileImage", file.name, req)

        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }


    private fun getImagePartsFromUris(context: Context, uris: List<Uri>): List<MultipartBody.Part> {
        return uris.mapNotNull { uriToMultipart(context, it) }
    }

    private fun prepareFilePart(uriString: String?): MultipartBody.Part? {
        if (uriString.isNullOrEmpty()) return null

        // Case 1: If it's an HTTP URL -> skip multipart
        if (uriString.startsWith("http")) {
            return null
        }

        // Case 2: local URI -> create Multipart
        val uri = Uri.parse(uriString)
        val file = File(app.cacheDir, "pet_image_${System.currentTimeMillis()}.jpg")

        app.contentResolver.openInputStream(uri)?.use { input ->
            FileOutputStream(file).use { output ->
                input.copyTo(output)
            }
        }

        val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())

        return MultipartBody.Part.createFormData(
            "pet_image",
            file.name,
            requestFile
        )
    }
    // -----------------------------------------------------
    //  MAIN SAVE CHANGES API CALL
    // -----------------------------------------------------
    fun saveChanges(context: Context, onSuccess: () -> Unit) {
        viewModelScope.launch {

            val state = _uiState.value
            val name = state.name.trim()
            val photos = _selectedPhotos.value

            // VALIDATIONS
            when {
                name.isEmpty() -> {
                    Toast.makeText(context, Messages.NAME_CANNOT_EMPTY, Toast.LENGTH_SHORT).show()
                    return@launch
                }

                photos.isEmpty() -> {
                    Toast.makeText(context, Messages.PROFILE_IMAGE_CANNOT_EMPTY, Toast.LENGTH_SHORT).show()
                    return@launch
                }

                !state.isMobileVerified -> {
                    Toast.makeText(context, "Please verify your phone number.", Toast.LENGTH_SHORT).show()
                    return@launch
                }

                !state.isEmailVerified -> {
                    Toast.makeText(context, "Please verify your email.", Toast.LENGTH_SHORT).show()
                    return@launch
                }
            }

            // Convert image URIs → Multipart
            val imageParts = getImagePartsFromUris(context, photos)

            val imagePart = prepareFilePart(uiState.value.profileImageUri.toString())

            if (imageParts.isEmpty()) {
                Toast.makeText(context, "Unable to process images", Toast.LENGTH_SHORT).show()
                return@launch
            }
            // API CALL
            repository.updateOwnerDetail(
                userId = sessionManager.getUserId(),
                name = state.name,
                email = state.email,
                phone = state.mobileNumber,
                bio = state.bio,
                profile_image = imageParts
            ).collectLatest { result ->

                when (result) {
                    is Resource.Loading -> _uiState.update { it.copy(isLoading = true) }

                    is Resource.Success -> {
                        _uiState.update { it.copy(isLoading = false) }
                        val response = result.data

                        Toast.makeText(
                            context,
                            response.message ?: "Profile updated successfully",
                            Toast.LENGTH_SHORT
                        ).show()

                        if (response.success) onSuccess()
                    }

                    is Resource.Error -> {
                        _uiState.update { it.copy(isLoading = false) }
                        Toast.makeText(context, result.message ?: "Something went wrong", Toast.LENGTH_SHORT).show()
                    }

                    Resource.Idle -> Unit
                }
            }
        }
    }

    companion object {
        /** Save Bitmap → Cache → URI */
        fun Bitmap.saveToCache(context: Context): Uri {
            val file = File.createTempFile("profile_", ".jpg", context.cacheDir)
            FileOutputStream(file).use {
                compress(Bitmap.CompressFormat.JPEG, 92, it)
            }
            return Uri.fromFile(file)
        }
    }
}
