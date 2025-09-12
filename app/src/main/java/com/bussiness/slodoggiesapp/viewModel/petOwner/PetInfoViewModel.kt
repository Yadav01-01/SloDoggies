package com.bussiness.slodoggiesapp.viewModel.petOwner

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bussiness.slodoggiesapp.model.petOwner.PetInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject



@HiltViewModel
class PetInfoViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(PetInfoUiState())
    val uiState: StateFlow<PetInfoUiState> = _uiState

    private val _selectedPhoto = MutableStateFlow<Uri?>(null)
    val selectedPhoto: StateFlow<Uri?> = _selectedPhoto

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    val ageOptions = listOf("0-5 Years", "5-10 Years", "10-20 Years")

    fun setSelectedPhoto(uri: Uri?) {
        _selectedPhoto.value = uri
    }

    fun updatePetName(name: String) = update { it.copy(petName = name.take(24)) }
    fun updatePetBreed(breed: String) = update { it.copy(petBreed = breed) }
    fun updatePetAge(age: String) = update { it.copy(petAge = age) }
    fun updatePetBio(bio: String) = update { it.copy(petBio = bio.take(150)) }

    /**  Validation function with error message */
    fun validate(): String? {
        val state = _uiState.value
        return when {
            state.petName.isBlank() -> "Please enter pet name"
            state.petBreed.isBlank() -> "Please enter pet breed"
            state.petAge.isBlank() -> "Please select pet age"
            state.petBio.isBlank() -> "Please enter pet bio"
            else -> null
        }
    }

    /** Continue button */
    fun onContinue(onProfile: Boolean) {
        if (onProfile){
            val error = validate()
            if (error == null) {
                sendEvent(UiEvent.ContinueSuccess(_uiState.value.toPetInfo()))
            } else {
                sendEvent(UiEvent.ShowToast(error))
            }
        }else{
            UiEvent.CloseDialog
        }

    }


    /** Add Pet button */
    fun onAddPet() {
        val error = validate()
        if (error == null) {
            sendEvent(UiEvent.AddPetSuccess(_uiState.value.toPetInfo()))
            resetForm()
        } else {
            sendEvent(UiEvent.ShowToast(error))
        }
    }

    private fun resetForm() {
        _uiState.value = PetInfoUiState()
        _selectedPhoto.value = null
    }

    private fun update(block: (PetInfoUiState) -> PetInfoUiState) {
        _uiState.value = block(_uiState.value)
    }

    private fun sendEvent(event: UiEvent) {
        viewModelScope.launch { _uiEvent.send(event) }
    }
}

//  UI State
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

//  UI Events
sealed class UiEvent {
    data class ShowToast(val message: String) : UiEvent()
    data class ContinueSuccess(val petInfo: PetInfo) : UiEvent()
    data class AddPetSuccess(val petInfo: PetInfo) : UiEvent()
    data object CloseDialog : UiEvent()
}
