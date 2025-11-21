package com.bussiness.slodoggiesapp.viewModel.petOwner.petadd

import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bussiness.slodoggiesapp.data.remote.Repository
import com.bussiness.slodoggiesapp.data.uiState.PetAddUpDateUiState
import com.bussiness.slodoggiesapp.network.Resource
import com.bussiness.slodoggiesapp.ui.component.common.createSingleMultipart
import com.bussiness.slodoggiesapp.util.Messages
import com.bussiness.slodoggiesapp.util.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PetAddViewModel @Inject constructor(
    private val repository: Repository,
    private var sessionManager: SessionManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(PetAddUpDateUiState())
    val uiState: StateFlow<PetAddUpDateUiState> = _uiState

    val ageOptions = listOf("< than 1 Year") + (1..20).map { "$it Year" }

    fun onPetNameChange(name: String) {
        _uiState.value = _uiState.value.copy(name = name)
    }

    fun onPetBreedChange(breed: String) {
        _uiState.value = _uiState.value.copy(breed = breed)
    }

    fun onPetAgeChange(age: String) {
        _uiState.value = _uiState.value.copy(age = age)
    }

    fun onPetBioChange(bio: String) {
        _uiState.value = _uiState.value.copy(bio = bio)
    }

    fun onPetImageChange(image: String) {
        _uiState.value = _uiState.value.copy(image = image)
    }

    fun updatePet(onSuccess: () -> Unit = { }, onError: (String) -> Unit) {
        val state = _uiState.value
        if (state.name==null){
            onError(Messages.PET_NAME)
            return
        }
        if (state.breed==null){
            onError(Messages.BREED_NAME)
            return
        }
        if (state.bio==null){
            onError(Messages.BIO_SMS)
            return
        }
        val imagePart = createSingleMultipart(uri = "".toUri(), keyName = "pet_image")
        viewModelScope.launch {
            repository.updatePetRequest(
                petName = state.name?:"",
                petBreed = state.breed?:"",
                petAge = state.age?:"",
                petBio = state.bio?:"",
                petId = "",
                userId = sessionManager.getUserId(),
                image = imagePart,
            ).collectLatest { result ->
                when (result) {
                    is Resource.Loading -> {
                        _uiState.value = _uiState.value.copy(isLoading = true)
                    }
                    is Resource.Success -> {
                        _uiState.value = _uiState.value.copy(isLoading = false)
                        result.data.let { response ->
                            if (response.success) {
//                                _uiState.value = _uiState.value.copy(loginSuccess = true)
                                onSuccess()
                            } else {
                                onError(response.message ?: "Login failed")
                            }
                        }
                    }
                    is Resource.Error -> {
                        _uiState.value = _uiState.value.copy(isLoading = false)
                        onError(result.message)
                    }
                    Resource.Idle -> TODO()
                }
            }
        }
    }


}



