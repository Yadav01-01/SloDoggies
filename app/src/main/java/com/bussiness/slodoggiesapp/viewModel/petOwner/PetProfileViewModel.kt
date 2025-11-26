package com.bussiness.slodoggiesapp.viewModel.petOwner


import android.app.Application
import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bussiness.slodoggiesapp.data.newModel.updatepet.Data
import com.bussiness.slodoggiesapp.data.remote.Repository
import com.bussiness.slodoggiesapp.network.Resource
import com.bussiness.slodoggiesapp.ui.component.saveBitmapToCache
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
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
    private val app: Application
) : ViewModel() {

    private val _uiState = MutableStateFlow(PetProfileUiState())
    val uiState: StateFlow<PetProfileUiState> = _uiState

    val ageOptions = (0..20).map { age ->
        when (age) {
            0 -> "< 1 Year"
            1 -> "1 Year"
            else -> "$age Years"
        }
    }
    // ---------------------- UPDATE TEXT FIELDS -------------------------

    fun onPetNameChange(name: String) = updateState {
        copy(petProfileData = petProfileData?.copy(pet_name = name))
    }

    fun onPetBreedChange(breed: String) = updateState {
        copy(petProfileData = petProfileData?.copy(pet_breed = breed))
    }

    fun onPetAgeChange(age: String) = updateState {
        copy(petProfileData = petProfileData?.copy(pet_age = age))
    }

    fun onPetBioChange(bio: String) = updateState {
        copy(petProfileData = petProfileData?.copy(pet_bio = bio))
    }

    // ---------------------- DROPDOWNS -------------------------

    fun toggleAgeDropdown(expanded: Boolean) = updateState { copy(showAgeDropdown = expanded) }
    fun toggleManagedByDropdown(expanded: Boolean) = updateState { copy(showManagedByDropdown = expanded) }
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
                            updateState { copy(isLoading = true) }
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
}
