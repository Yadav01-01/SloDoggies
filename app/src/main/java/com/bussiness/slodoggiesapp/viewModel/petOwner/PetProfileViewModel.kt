package com.bussiness.slodoggiesapp.viewModel.petOwner


import android.app.Application
import android.graphics.Bitmap
import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.bussiness.slodoggiesapp.ui.component.saveBitmapToCache
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PetProfileUiState(
    val petName: String = "",
    val petBreed: String = "",
    val petAge: String = "",
    val petBio: String = "",
    val managedBy: String = "Pet Mom",
    val profileImageUri: Uri? = null,
    val showAgeDropdown: Boolean = false,
    val showManagedByDropdown: Boolean = false,
    val showImagePickerDialog: Boolean = false,
    val showConfirmationDialog: Boolean = false,
    val navigateToProfile: Boolean = false,
    val deleteDialog: Boolean = false
)

@HiltViewModel
class PetProfileViewModel @Inject constructor(
    private val app: Application
) : AndroidViewModel(app) {

    private val _uiState = MutableStateFlow(PetProfileUiState())
    val uiState: StateFlow<PetProfileUiState> = _uiState

    val ageOptions = listOf("1 Year", "2 Year", "3 Year", "4 Year", "5 Year","6 Year", "7 Year", "8 Year", "9 Year", "10 Year",
        "11 Year", "12 Year", "13 Year", "14 Year", "15 Year", "16 Year", "17 Year", "18 Year", "19 Year", "20 Year" )

    val managedByOptions = listOf("Pet Mom", "Pet Dad", "Family Member", "Caregiver")

    fun onPetNameChange(name: String) = updateState { copy(petName = name) }
    fun onPetBreedChange(breed: String) = updateState { copy(petBreed = breed) }
    fun onPetAgeChange(age: String) = updateState { copy(petAge = age) }
    fun onPetBioChange(bio: String) = updateState { copy(petBio = bio) }
    fun onManagedByChange(value: String) = updateState { copy(managedBy = value) }

    fun toggleAgeDropdown(expanded: Boolean) = updateState { copy(showAgeDropdown = expanded) }
    fun toggleManagedByDropdown(expanded: Boolean) = updateState { copy(showManagedByDropdown = expanded) }
    fun toggleImagePicker(show: Boolean) = updateState { copy(showImagePickerDialog = show) }

    fun setImageUri(uri: Uri?) = updateState { copy(profileImageUri = uri) }

    fun saveBitmapAndSetUri(bitmap: Bitmap) {
        viewModelScope.launch {
            val uri = saveBitmapToCache(app, bitmap)
            updateState { copy(profileImageUri = uri) }
        }
    }

    fun showConfirmationDialog(show: Boolean) = updateState {
        copy(showConfirmationDialog = show)
    }

    fun showDeleteDialog(show: Boolean) = updateState {
        copy(deleteDialog = show)
    }

    fun dismissDeleteDialog() {
        showDeleteDialog(false)
    }



    fun saveChanges() {
        // TODO: Call Repository API here
        updateState { copy(navigateToProfile = true) }
    }

    fun resetNavigation() = updateState { copy(navigateToProfile = false) }

    private inline fun updateState(update: PetProfileUiState.() -> PetProfileUiState) {
        _uiState.value = _uiState.value.update()
    }
}
