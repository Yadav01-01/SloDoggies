package com.bussiness.slodoggiesapp.viewModel.petOwner

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bussiness.slodoggiesapp.model.petOwner.PetInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PetInfoViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(PetInfoUiState())
    val uiState: StateFlow<PetInfoUiState> = _uiState

    private val _selectedPhoto = MutableStateFlow<Uri?>(null)
    val selectedPhoto: StateFlow<Uri?> = _selectedPhoto

    // Update selected photo
    fun setSelectedPhoto(uri: Uri?) {
        viewModelScope.launch {
            _selectedPhoto.value = uri
        }
    }

    val ageOptions = listOf(
        "Puppy (0-1 year)",
        "Young (1-3 years)",
        "Adult (3-7 years)",
        "Senior (7+ years)"
    )
    val managedByOptions = listOf("Pet Mom", "Pet Dad", "Family Member", "Caregiver")

    fun updatePetName(name: String) = update { it.copy(petName = name.take(24)) }
    fun updatePetBreed(breed: String) = update { it.copy(petBreed = breed) }
    fun updatePetAge(age: String) = update { it.copy(petAge = age) }
    fun updatePetBio(bio: String) = update { it.copy(petBio = bio.take(150)) }
    fun updateManagedBy(value: String) = update { it.copy(managedBy = value) }

    fun toggleAgeDropdown(expanded: Boolean) = update { it.copy(showAgeDropdown = expanded) }
    fun toggleManagedByDropdown(expanded: Boolean) = update { it.copy(showManagedByDropdown = expanded) }

    private fun update(block: (PetInfoUiState) -> PetInfoUiState) {
        _uiState.value = block(_uiState.value)
    }
}

data class PetInfoUiState(
    val petName: String = "",
    val petBreed: String = "",
    val petAge: String = "",
    val petBio: String = "",
    val managedBy: String = "Pet Mom",
    val showAgeDropdown: Boolean = false,
    val showManagedByDropdown: Boolean = false
) {
    fun toPetInfo(): PetInfo = PetInfo(
        name = petName,
        breed = petBreed,
        age = petAge,
        bio = petBio,
        managedBy = managedBy
    )
}
