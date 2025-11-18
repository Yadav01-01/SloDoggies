package com.bussiness.slodoggiesapp.viewModel.businessProvider

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bussiness.slodoggiesapp.data.remote.Repository
import com.bussiness.slodoggiesapp.data.uiState.BusinessRegistrationUiState
import com.bussiness.slodoggiesapp.network.Resource
import com.bussiness.slodoggiesapp.util.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BusinessRegistrationViewModel @Inject constructor(
    private val repository: Repository,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(BusinessRegistrationUiState())
    val uiState: StateFlow<BusinessRegistrationUiState> = _uiState.asStateFlow()

    // --- Field Updaters ---
    fun updateName(value: String) = _uiState.update { it.copy(name = value.trim()) }
    fun updateEmail(value: String) = _uiState.update { it.copy(email = value.trim()) }
    fun updateBusinessAddress(value: String) = _uiState.update { it.copy(businessAddress = value.trim()) }
    fun updateWebsite(value: String) = _uiState.update { it.copy(website = value.trim()) }
    fun updateContact(value: String) = _uiState.update { it.copy(contact = value.trim()) }
    fun updateCity(value: String) = _uiState.update { it.copy(city = value.trim()) }
    fun updateState(value: String) = _uiState.update { it.copy(state = value.trim()) }
    fun updateZip(value: String) = _uiState.update { it.copy(zipCode = value.trim()) }


    init {
        getPreFillDetail()
    }

    fun addCategory(category: String) {
        _uiState.update {
            it.copy(categories = it.categories + category)
        }
    }

    fun removeCategory(category: String) {
        _uiState.update {
            it.copy(categories = it.categories - category)
        }
    }

    fun addImage(uri: Uri) {
        _uiState.update {
            it.copy(verificationDocs = it.verificationDocs + uri)
        }
    }

    fun removeImage(uri: Uri) {
        _uiState.update {
            it.copy(verificationDocs = it.verificationDocs - uri)
        }
    }

    private fun getPreFillDetail(){
        viewModelScope.launch {
            repository.getBusinessDetail(sessionManager.getUserId()).collectLatest { result ->
                when (result) {
                    is Resource.Loading -> _uiState.update { it.copy(isLoading = true) }
                    is Resource.Success -> {
                        val response = result.data
                        if (response.success && response.data != null) {
                            val data = response.data
                            _uiState.update {
                                it.copy(
                                    name = data.name.orEmpty(),
                                    email = data.email.orEmpty(),
                                    contact = data.phone.orEmpty(),
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

    // --- Submit Form ---
    fun submitForm(onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            val state = _uiState.value
            if (!state.isValid) {
                onError("Please fill all required fields and upload at least one image.")
                return@launch
            }

            _uiState.update { it.copy(isLoading = true) }

            try {
                repository.getBusinessDetail(sessionManager.getUserId()).collectLatest { result ->
                    when (result) {
                        is Resource.Loading -> _uiState.update { it.copy(isLoading = true) }
                        is Resource.Success -> {
                            val response = result.data
                            if (response.success && response.data != null) {

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

                // Simulate Success
                _uiState.update { it.copy(isLoading = false, formSubmitted = true) }
                onSuccess()

            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false) }
                onError(e.message ?: "Submission failed, please try again.")
            }
        }
    }

    private fun onError(message: String?) {
        _uiState.update { it.copy(errorMessage = message ?: "Something went wrong") }
    }
}
