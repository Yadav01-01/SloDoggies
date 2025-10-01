package com.bussiness.slodoggiesapp.viewModel.common.authFlowVM

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState

    fun onContactChange(newInput: String) {
        val trimmedInput = newInput.trim()

        val isEmail = android.util.Patterns.EMAIL_ADDRESS.matcher(trimmedInput).matches()
        val isPhone = trimmedInput.matches(Regex("^[6-9][0-9]{9}$")) // 10-digit Indian mobile numbers

        when {
            isEmail -> {
                _uiState.value = _uiState.value.copy(
                    contactInput = trimmedInput,
                    isValid = true,
                    contactType = ContactType.EMAIL,
                    errorMessage = null
                )
            }
            isPhone -> {
                _uiState.value = _uiState.value.copy(
                    contactInput = trimmedInput,
                    isValid = true,
                    contactType = ContactType.PHONE,
                    errorMessage = null
                )
            }
            else -> {
                _uiState.value = _uiState.value.copy(
                    contactInput = trimmedInput,
                    isValid = false,
                    contactType = null,
                    errorMessage = "Enter valid email or phone number"
                )
            }
        }
    }

    fun onPasswordChange(newPassword: String) {
        _uiState.value = _uiState.value.copy(password = newPassword)
    }

    fun login(onSuccess: () -> Unit, onError: (String) -> Unit) {
        val state = _uiState.value

        if (state.contactInput.isBlank() || state.password.isBlank()) {
            onError("Email/Phone and Password cannot be empty")
            return
        }

        if (!state.isValid) {
            onError(state.errorMessage ?: "Invalid input")
            return
        }

        viewModelScope.launch {
            try {
                // TODO: Replace with repository.login(state.contactInput, state.password)
                delay(500) // simulate API call
                onSuccess()
            } catch (e: Exception) {
                onError(e.message ?: "Login failed")
            }
        }
    }
}

data class LoginUiState(
    val contactInput: String = "",
    val password: String = "",
    val contactType: ContactType? = null,
    val isValid: Boolean = false,
    val errorMessage: String? = null
)

enum class ContactType {
    EMAIL, PHONE
}
