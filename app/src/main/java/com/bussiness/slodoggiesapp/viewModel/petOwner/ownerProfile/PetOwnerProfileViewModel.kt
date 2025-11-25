package com.bussiness.slodoggiesapp.viewModel.petOwner.ownerProfile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bussiness.slodoggiesapp.data.newModel.ownerProfile.Pet
import com.bussiness.slodoggiesapp.data.remote.Repository
import com.bussiness.slodoggiesapp.data.uiState.OwnerProfileUiState
import com.bussiness.slodoggiesapp.network.Resource
import com.bussiness.slodoggiesapp.util.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PetOwnerProfileViewModel @Inject constructor(
    private val repository: Repository,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(OwnerProfileUiState())
    val uiState: StateFlow<OwnerProfileUiState> = _uiState

    // Selected Pet state
    private val _selectedPet = MutableStateFlow<Pet?>(null)
    val selectedPet: StateFlow<Pet?> = _selectedPet

    init {
        profileDetail()
    }

    fun onPetSelected(pet: Pet) {
        _selectedPet.value = pet
    }

    fun petInfoDialog(toggle : Boolean){
        _uiState.update { it.copy(showPetInfoDialog = toggle) }
    }

    fun petAddedSuccessDialog(toggle: Boolean){
        _uiState.update { it.copy(petAddedSuccessDialog = toggle) }
    }

    private fun profileDetail() {
        viewModelScope.launch {
            repository.getOwnerProfileDetails(sessionManager.getUserId())
                .collectLatest { result ->
                    when (result) {

                        is Resource.Loading -> {
                            _uiState.update {
                                it.copy(
                                    isLoading = true,
                                    errorMessage = null
                                )
                            }
                        }

                        is Resource.Success -> {
                            val response = result.data
                            if (response.success) {
                                _uiState.update {
                                    it.copy(
                                        isLoading = false,
                                        data = response.data
                                    )
                                }
                            } else {
                                _uiState.update {
                                    it.copy(
                                        isLoading = false,
                                        errorMessage = response.message
                                    )
                                }
                            }
                        }

                        is Resource.Error -> {
                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    errorMessage = result.message
                                )
                            }
                        }

                        Resource.Idle -> Unit
                    }
                }
        }
    }
}
