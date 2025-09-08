package com.bussiness.slodoggiesapp.viewModel.petOwner

import android.content.Context
import android.net.Uri
import android.widget.Toast
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
        _selectedPhoto.value = uri
    }

    fun onNameChanged(name: String) {
        _uiState.update { it.copy(name = name.take(30)) }
    }

    fun onPhoneChanged(phone: String) {
        _uiState.update { it.copy(phoneNumber = phone.take(10)) }
    }

    fun onBioChanged(bio: String) {
        _uiState.update { it.copy(bio = bio) }
    }

    fun onEmailChanged(email: String) {
        _uiState.update { it.copy(email = email) }
    }

    fun setPhoneVerified(verified: Boolean) {
        _uiState.value = _uiState.value.copy(isPhoneVerified = verified)
    }

    fun setEmailVerified(verified: Boolean) {
        _uiState.value = _uiState.value.copy(isEmailVerified = verified)
    }


    fun verifyEmail() {
        viewModelScope.launch {
            // fake verify for now
            _uiState.update { it.copy(isEmailVerified = true) }
        }
    }


    // Validate name and profile image
    fun submitDetails(context: Context, onSuccess: () -> Unit) {
        viewModelScope.launch {
            val name = _uiState.value.name.trim()
            val photo = _selectedPhoto.value

            when {
                name.isEmpty() -> {
                    Toast.makeText(context, "Name cannot be empty", Toast.LENGTH_SHORT).show()
                }
                photo == null -> {
                    Toast.makeText(context, "Profile image cannot be empty", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    // Proceed with API call or next step
                    onSuccess()
                }
            }
        }
    }
}


