package com.bussiness.slodoggiesapp.viewModel.petOwner

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bussiness.slodoggiesapp.data.model.petOwner.PetInfo
import com.bussiness.slodoggiesapp.data.remote.Repository
import com.bussiness.slodoggiesapp.network.Resource
import com.bussiness.slodoggiesapp.ui.component.common.createMultipartList
import com.bussiness.slodoggiesapp.util.Messages
import com.bussiness.slodoggiesapp.util.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject



@HiltViewModel
class PetInfoViewModel @Inject constructor(
    private val repository: Repository,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(PetInfoUiState())
    val uiState: StateFlow<PetInfoUiState> = _uiState

    private val _selectedPhoto = MutableStateFlow<Uri?>(null)
    private val selectedPhoto: StateFlow<Uri?> = _selectedPhoto

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    val ageOptions = listOf("< than 1 Year") + (1..20).map { "$it Year" }

    fun setSelectedPhoto(uri: Uri?) {
        _selectedPhoto.value = uri
    }

    fun updatePetName(name: String) = update { it.copy(petName = name.take(24)) }
    fun updatePetBreed(breed: String) = update { it.copy(petBreed = breed) }
    fun updatePetAge(age: String) = update { it.copy(petAge = age) }
    fun updatePetBio(bio: String) = update { it.copy(petBio = bio.take(150)) }

    /** Validation */
    private fun validate(): String? {
        val s = _uiState.value
        return when {
            s.petName.isBlank() -> Messages.ENTER_PET_NAME
            s.petBreed.isBlank() -> Messages.ENTER_PET_BREED
            s.petAge.isBlank() -> Messages.ENTER_PET_AGE
            s.petBio.isBlank() -> Messages.ENTER_PET_BIO
            _selectedPhoto.value == null -> "Please select a pet image"
            else -> null
        }
    }

    /** Continue button */
    fun onContinue(context: Context, onProfile: Boolean) {
        val error = validate()
        if (error != null) {
            sendEvent(UiEvent.ShowToast(error))
            return
        }

        if (!onProfile) {
            sendEvent(UiEvent.CloseDialog)
            return
        }

        viewModelScope.launch {

            // Convert selectedPhoto to multipart list
            val imageList = listOfNotNull(selectedPhoto.value)
            val imageParts = createMultipartList(
                context = context,
                uris = imageList,
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

                    is Resource.Loading -> {

                    }

                    is Resource.Success -> {
                        sendEvent(UiEvent.ContinueSuccess(_uiState.value.toPetInfo()))
                    }

                    is Resource.Error -> {
                        sendEvent(UiEvent.ShowToast(result.message ?: "Something went wrong"))
                    }

                    Resource.Idle -> TODO()
                }
            }
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
    fun toPetInfo(): PetInfo =
        PetInfo(
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
