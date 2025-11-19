package com.bussiness.slodoggiesapp.viewModel.petOwner

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bussiness.slodoggiesapp.data.model.petOwner.PetInfo
import com.bussiness.slodoggiesapp.data.remote.Repository
import com.bussiness.slodoggiesapp.data.uiState.PetInfoUiState
import com.bussiness.slodoggiesapp.network.Resource
import com.bussiness.slodoggiesapp.ui.component.common.createMultipartList
import com.bussiness.slodoggiesapp.util.Messages
import com.bussiness.slodoggiesapp.util.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PetInfoViewModel @Inject constructor(
    private val repository: Repository,
    private val sessionManager: SessionManager
) : ViewModel() {

    /** UI State */
    private val _uiState = MutableStateFlow(PetInfoUiState())
    val uiState: StateFlow<PetInfoUiState> = _uiState.asStateFlow()

    /** Selected Image */
    private val _selectedPhoto = MutableStateFlow<Uri?>(null)
    private val selectedPhoto: StateFlow<Uri?> = _selectedPhoto.asStateFlow()

    /** UI Events - One-time events */
    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    /** Age List */
    val ageOptions: List<String> = listOf("< than 1 Year") + (1..20).map { "$it Year" }

    fun setSelectedPhoto(uri: Uri?) { _selectedPhoto.value = uri }

    fun updatePetName(name: String) = update { it.copy(petName = name.take(24)) }
    fun updatePetBreed(breed: String) = update { it.copy(petBreed = breed) }
    fun updatePetAge(age: String) = update { it.copy(petAge = age) }
    fun updatePetBio(bio: String) = update { it.copy(petBio = bio.take(150)) }


    private fun validate(): String? = with(_uiState.value) {
        when {
            petName.isBlank() -> Messages.ENTER_PET_NAME
            petBreed.isBlank() -> Messages.ENTER_PET_BREED
            petAge.isBlank() -> Messages.ENTER_PET_AGE
            petBio.isBlank() -> Messages.ENTER_PET_BIO
            selectedPhoto.value == null -> Messages.SELECT_PET_IMAGE
            else -> null
        }
    }


    private fun submitPet(
        context: Context,
        onSuccess: (PetInfo) -> UiEvent
    ) {

        val error = validate()
        if (error != null) {
            sendEvent(UiEvent.ShowToast(error))
            return
        }

        viewModelScope.launch {

            val imageParts = createMultipartList(
                context = context,
                uris = listOfNotNull(selectedPhoto.value),
                keyName = "pet_images[]"
            )

            repository.addPets(
                userId = sessionManager.getUserId(),
                petName = uiState.value.petName,
                petBreed = uiState.value.petBreed,
                petAge = uiState.value.petAge,
                petBio = uiState.value.petBio,
                pet_image = imageParts
            ).collectLatest { result ->

                when (result) {
                    is Resource.Loading -> sendEvent(UiEvent.Loading(true))

                    is Resource.Success -> {
                        sendEvent(UiEvent.Loading(false))
                        sendEvent(onSuccess(_uiState.value.toPetInfo()))
                        resetForm()
                    }

                    is Resource.Error -> {
                        sendEvent(UiEvent.Loading(false))
                        sendEvent(UiEvent.ShowToast(result.message))
                    }

                    Resource.Idle -> Unit
                }
            }
        }
    }


    fun onContinue(context: Context, onProfile: Boolean) {
        if (!onProfile) {
            sendEvent(UiEvent.CloseDialog)
            return
        }
        submitPet(context) { UiEvent.ContinueSuccess(it) }
    }

    fun onAddPet(context: Context) {
        submitPet(context) { UiEvent.AddPetSuccess(it) }
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




sealed class UiEvent {
    data class ShowToast(val message: String) : UiEvent()
    data class ContinueSuccess(val petInfo: PetInfo) : UiEvent()
    data class AddPetSuccess(val petInfo: PetInfo) : UiEvent()
    data class Loading(val show: Boolean) : UiEvent()
    data object CloseDialog : UiEvent()
}
