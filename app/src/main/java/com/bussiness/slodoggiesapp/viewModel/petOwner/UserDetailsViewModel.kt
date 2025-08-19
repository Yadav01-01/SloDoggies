package com.bussiness.slodoggiesapp.viewModel.petOwner

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bussiness.slodoggiesapp.model.petOwner.UserDetailsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDetailsViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(UserDetailsUiState())
    val uiState: StateFlow<UserDetailsUiState> = _uiState.asStateFlow()

    private val _selectedPhoto = MutableStateFlow<Uri?>(null)
    val selectedPhoto: StateFlow<Uri?> = _selectedPhoto

    // Update selected photo
    fun setSelectedPhoto(uri: Uri?) {
        viewModelScope.launch {
            _selectedPhoto.value = uri
        }
    }

    fun onNameChanged(name: String) {
        _uiState.update { it.copy(name = name) }
    }

    fun onPhoneChanged(phone: String) {
        _uiState.update { it.copy(phoneNumber = phone) }
    }

    fun onBioChanged(bio: String) {
        _uiState.update { it.copy(bio = bio) }
    }

    fun onEmailChanged(email: String) {
        _uiState.update { it.copy(email = email) }
    }

    fun verifyEmail() {
        viewModelScope.launch {
            // fake verify for now
            _uiState.update { it.copy(isEmailVerified = true) }
        }
    }

//    fun submitDetails(onSuccess: () -> Unit) {
//        viewModelScope.launch {
//            _uiState.update { it.copy(isLoading = true, error = null) }
//
//            try {
//                val response = repository.submitUserDetails(
//                    name = _uiState.value.name,
//                    phone = _uiState.value.phoneNumber,
//                    email = _uiState.value.email,
//                    bio = _uiState.value.bio
//                )
//
//                if (response.isSuccessful) {
//                    _uiState.update { it.copy(isLoading = false, isSuccess = true) }
//                    onSuccess()
//                } else {
//                    _uiState.update { it.copy(isLoading = false, error = "Failed to submit") }
//                }
//            } catch (e: Exception) {
//                _uiState.update { it.copy(isLoading = false, error = e.message) }
//            }
//        }
//    }
}

