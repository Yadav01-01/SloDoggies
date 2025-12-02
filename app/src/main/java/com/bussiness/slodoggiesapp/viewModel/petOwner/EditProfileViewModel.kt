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
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.FileOutputStream
import java.net.URL
import javax.inject.Inject

data class EditProfileUiState(
    val name: String = "",
    val mobileNumber: String = "",
    val email: String = "",
    val bio: String = "",
    val parent_type: String = "",
    val showRelationDropdown: Boolean = false,
    var image: String? = null,
    val showImagePickerDialog: Boolean = false,
    val isMobileVerified: Boolean = false,
    val isEmailVerified: Boolean = false,
    val updateProfileDialog: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
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


    private val _selectedPhotos =  MutableStateFlow<List<Uri>>(emptyList())
    val selectedPhotos: StateFlow<List<Uri>> = _selectedPhotos


    var savedMobileNumber: String? = null
    var saveEmailId:String?=null


    // ------------ FIELD UPDATES ------------
    fun updateName(name: String) = _uiState.update { it.copy(name = name) }

    fun updateBio(bio: String) = _uiState.update { it.copy(bio = bio) }

    fun updateRelation(relation: String) =
        _uiState.update { it.copy(parent_type = relation, showRelationDropdown = false) }

   // fun updateEmail(email: String) = _uiState.update { it.copy(email = email) }

    fun updateEmail(email: String) {
        _uiState.value = _uiState.value.copy(
            email = email,
            isEmailVerified = email == saveEmailId
        )
    }

//    fun updateMobileNumber(newNumber: String) =
//        _uiState.update {
//            it.copy(mobileNumber = mobileNumber.take(10))
//        }

    fun updateMobileNumber(newNumber: String) {
        _uiState.value = _uiState.value.copy(
            mobileNumber = newNumber,
            isMobileVerified = newNumber == savedMobileNumber
        )
    }

    fun updateSelectedPhoto(uri: Uri) {
       // _selectedPhotos.update { it + uri }
        _uiState.value = _uiState.value.copy(
            image = uri?.toString() ?: ""
        )
    }
    fun resetImage() {
        _uiState.update { it.copy(image = null) }
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
        val isHttpUrl = uri.scheme == "http" || uri.scheme == "https"
        if (isHttpUrl) {
            return null
        }
        return try {
            val inputStream = context.contentResolver.openInputStream(uri) ?: return null

            val file = File.createTempFile("profile_", ".jpg", context.cacheDir)
            val output = FileOutputStream(file)
            inputStream.copyTo(output)
            output.close()
            inputStream.close()

            val req = file.asRequestBody("image/*".toMediaTypeOrNull())
            MultipartBody.Part.createFormData("profile_image", file.name, req)

        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun singleItemToMultipart(
        context: Context,
        item: String
    ): MultipartBody.Part? {

        val bytes: ByteArray? = try {
            if (item.startsWith("http://") || item.startsWith("https://")) {
                // Remote URL: download bytes
                val url = URL(item)
                url.openStream().use { it.readBytes() }
            } else {
                // Local URI
                val uri = Uri.parse(item)
                context.contentResolver.openInputStream(uri)?.use { it.readBytes() }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }

        return bytes?.let {
            val fileName = "file_${System.currentTimeMillis()}.jpg"
            val body = it.toRequestBody("image/*".toMediaType())
            MultipartBody.Part.createFormData(
                "profile_image",
                fileName,
                body
            )
        }
    }



    private fun getImagePartsFromUris(context: Context, uris: List<Uri>): List<MultipartBody.Part> {
        return uris.mapNotNull { singleItemToMultipart(context, it.toString()) }
    }

    private fun prepareFilePart(uriString: String?): MultipartBody.Part? {
        if (uriString.isNullOrEmpty()) return null

        // Case 1: If it's an HTTP URL -> skip multipart
        if (uriString.startsWith("http")) {
            return null
        }

        // Case 2: local URI -> create Multipart
        val uri = Uri.parse(uriString)
        val file = File(app.cacheDir, "profile_image${System.currentTimeMillis()}.jpg")

        app.contentResolver.openInputStream(uri)?.use { input ->
            FileOutputStream(file).use { output ->
                input.copyTo(output)
            }
        }

        val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())

        return MultipartBody.Part.createFormData(
            "profile_image",
            file.name,
            requestFile
        )
    }

    // --- API Call: Fetch Owner Details ---
    fun fetchOwnerDetails() {
        viewModelScope.launch {
            repository.ownerDetail(sessionManager.getUserId()).collectLatest { result ->
                when (result) {
                    is Resource.Loading -> _uiState.update { it.copy(isLoading = true) }
                    is Resource.Success -> {
                        val response = result.data
                        if (response.success && response.data != null) {
                            val owner = response.data
                            savedMobileNumber = owner.phone
                            saveEmailId = owner.email
                            _uiState.update {
                                it.copy(
                                    name = owner.name.orEmpty(),
                                    email = owner.email.orEmpty(),
                                    mobileNumber = owner.phone.orEmpty(),
                                    image = owner.image.orEmpty(),
                                    bio = owner.bio.orEmpty(),
                                    parent_type = owner.parent_type.orEmpty(),
                                    isEmailVerified = !owner.email.isNullOrBlank(),
                                    isMobileVerified = !owner.phone.isNullOrBlank(),
                                    isLoading = false
                                )
                            }
                        } else {
                            onError(response.message ?: "Failed to fetch owner details")
                        }
                    }

                    is Resource.Error -> {
                        _uiState.update { it.copy(isLoading = false) }
                        onError(result.message)
                    }

                    Resource.Idle -> Unit
                }
            }
        }
    }
    private fun onError(message: String?) {
        _uiState.update { it.copy(errorMessage = message ?: "Something went wrong") }
    }
    // -----------------------------------------------------
    //  MAIN SAVE CHANGES API CALL
    // -----------------------------------------------------
    fun saveChanges(context: Context, onSuccess: () -> Unit) {
        viewModelScope.launch {

            val state = _uiState.value
            val name = state.name.trim()
            val photos = if (state.image != null) {
                listOf(Uri.parse(state.image))
            } else {
                emptyList()
            }

            // VALIDATIONS
            when {
                name.isEmpty() -> {
                    Toast.makeText(context, Messages.NAME_CANNOT_EMPTY, Toast.LENGTH_SHORT).show()
                    return@launch
                }

//                photos.isEmpty() -> {
//                    Toast.makeText(context, Messages.PROFILE_IMAGE_CANNOT_EMPTY, Toast.LENGTH_SHORT).show()
//                    return@launch
//                }

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

        //    val imagePart = prepareFilePart(photos)

//            if (imageParts.isEmpty()) {
//                Toast.makeText(context, "Unable to process images", Toast.LENGTH_SHORT).show()
//                return@launch
//            }
            // API CALL
            repository.updateOwnerDetail(
                userId = sessionManager.getUserId(),
                name = state.name,
                email = state.email,
                phone = state.mobileNumber,
                bio = state.bio,
                parent_type = state.parent_type,
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

    fun sendOtpRequest(type:String?="",
                       onSuccess: () -> Unit = { },
                       onError: (String) -> Unit){
        viewModelScope.launch {
            val state = _uiState.value
            val email= state.email
            val phone = state.mobileNumber
            var phoneEmail = ""

            if (type.equals("dialogPhone")){
                phoneEmail = phone
                if (phone.isEmpty()){
                    onError("Phone cant be empty")
                    return@launch
                }
            }else{
                phoneEmail =  email
                if (phoneEmail.isEmpty()){
                    onError("Email cant be empty")
                    return@launch
                }
            }
            repository.sendOtpRequest(phoneEmail,sessionManager.getUserId()).collectLatest { result ->
                when (result) {
                    is Resource.Loading -> _uiState.update { it.copy(isLoading = true) }
                    is Resource.Success -> {
                        _uiState.value = _uiState.value.copy(isLoading = false)
                        val response = result.data
                        if (response.success == true && response.data != null) {
                            val data = response.data
                            onSuccess()
                        } else {
                            onError(response.message ?: "Failed to fetch owner details")
                        }
                    }
                    is Resource.Error -> {
                        _uiState.update { it.copy(isLoading = false) }
                        onError(result.message)
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
