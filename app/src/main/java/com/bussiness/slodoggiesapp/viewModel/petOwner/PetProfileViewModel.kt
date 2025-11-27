package com.bussiness.slodoggiesapp.viewModel.petOwner


import android.app.Application
import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bussiness.slodoggiesapp.data.newModel.updatepet.Data
import com.bussiness.slodoggiesapp.data.remote.Repository
import com.bussiness.slodoggiesapp.network.Resource
import com.bussiness.slodoggiesapp.ui.component.saveBitmapToCache
import com.bussiness.slodoggiesapp.util.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

data class PetProfileUiState(
    val petProfileData: Data? = null,
    val showAgeDropdown: Boolean = false,
    val showManagedByDropdown: Boolean = false,
    val showImagePickerDialog: Boolean = false,
    val showConfirmationDialog: Boolean = false,
    val navigateToProfile: Boolean = false,
    val deleteDialog: Boolean = false,
    val isLoading : Boolean = false,
    val error : String? = null
)

@HiltViewModel
class PetProfileViewModel @Inject constructor(
    private val repository: Repository,
    private val app: Application,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(PetProfileUiState())
    val uiState: StateFlow<PetProfileUiState> = _uiState

    val ageOptions = (0..20).map { age ->
        when (age) {
            0 -> "< 1 "
            1 -> "1 "
            else -> "$age "
        }
    }

    var selectedAge by mutableStateOf("")

    fun setInitialAge(age: String?) {
        if (selectedAge.isEmpty() && !age.isNullOrEmpty()) {
            selectedAge = age
        }
    }

    fun onAgeSelected(age: String) {
        selectedAge = age
        onPetAgeChange(age)
    }

    // ---------------------- UPDATE TEXT FIELDS -------------------------

    fun onPetNameChange(name: String) = updateState {
        copy(petProfileData = petProfileData?.copy(pet_name = name))
    }

    fun onPetBreedChange(breed: String) = updateState {
        copy(petProfileData = petProfileData?.copy(pet_breed = breed))
    }

    private fun onPetAgeChange(age: String) = updateState {
        copy(petProfileData = petProfileData?.copy(pet_age = age))
    }

    fun onPetBioChange(bio: String) = updateState {
        copy(petProfileData = petProfileData?.copy(pet_bio = bio))
    }

    // ---------------------- DROPDOWNS -------------------------
    fun toggleImagePicker(show: Boolean) = updateState { copy(showImagePickerDialog = show) }

    // ---------------------- IMAGE -------------------------

    fun setImageUri(uri: Uri?) = updateState {
        copy(petProfileData = petProfileData?.copy(pet_image = uri.toString()))
    }

    fun saveBitmapAndSetUri(bitmap: Bitmap) {
        viewModelScope.launch {
            val uri = saveBitmapToCache(app, bitmap)
            updateState {
                copy(petProfileData = petProfileData?.copy(pet_image = uri.toString()))
            }
        }
    }

    // ---------------------- DIALOGS -------------------------

    fun showConfirmationDialog(show: Boolean) = updateState { copy(showConfirmationDialog = show) }
    fun showDeleteDialog(show: Boolean) = updateState { copy(deleteDialog = show) }
    fun dismissDeleteDialog() = showDeleteDialog(false)

    // ---------------------- SAVE -------------------------

    fun saveChanges() {
        updateState { copy(navigateToProfile = true) }
    }

    fun resetNavigation() = updateState { copy(navigateToProfile = false) }

    // ---------------------- GENERIC UPDATER -------------------------

    private inline fun updateState(update: PetProfileUiState.() -> PetProfileUiState) {
        _uiState.value = _uiState.value.update()
    }

    fun getPetProfile(petId: String) {
        viewModelScope.launch {
            repository.getPetDetail(petId)
                .collectLatest { result ->
                    when (result) {
                        is Resource.Loading -> {
                            updateState { copy(isLoading = true, petProfileData = petProfileData ?: Data()) }
                        }
                        is Resource.Success -> {
                            updateState {
                                copy(isLoading = false,
                                    petProfileData = result.data.data,
                                    error = null
                                )
                            }

                        }
                        is Resource.Error -> {
                            updateState { copy(isLoading = false
                                        , error = result.message) }
                        }

                        Resource.Idle -> {

                        }
                    }
                }
        }
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


    fun updatePetProfile() {
        viewModelScope.launch {

            val imagePart = prepareFilePart(uiState.value.petProfileData?.pet_image)

            repository.updatePetRequest(
                petName = uiState.value.petProfileData?.pet_name ?: "",
                petBreed = uiState.value.petProfileData?.pet_breed ?: "",
                petAge = uiState.value.petProfileData?.pet_age ?: "",
                petBio = uiState.value.petProfileData?.pet_bio ?: "",
                petId = uiState.value.petProfileData?.id.toString(),
                userId = sessionManager.getUserId(),
                image = imagePart
            ).collectLatest { result ->
                when (result) {
                    is Resource.Loading -> updateState { copy(isLoading = true) }
                    is Resource.Success -> updateState { copy(isLoading = false, /*navigateToProfile = true*/) }
                    is Resource.Error -> updateState { copy(isLoading = false, error = result.message) }
                    Resource.Idle -> Unit
                }
            }
        }
    }
}
