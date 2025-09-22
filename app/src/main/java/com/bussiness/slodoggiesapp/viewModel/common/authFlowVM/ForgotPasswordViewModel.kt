package com.bussiness.slodoggiesapp.viewModel.common.authFlowVM

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(ForgotPasswordUiState())
    val uiState: StateFlow<ForgotPasswordUiState> = _uiState

    fun onEmailChange(newEmail: String) {
        _uiState.value = _uiState.value.copy(email = newEmail)
    }

    fun sendCode(onSuccess: () -> Unit, onError: (String) -> Unit) {
        val state = _uiState.value

        if (state.email.isBlank()) {
            onError("Email/Phone cannot be empty")
            return
        }

        if (!(android.util.Patterns.EMAIL_ADDRESS.matcher(state.email).matches() ||
                    android.util.Patterns.PHONE.matcher(state.email).matches())) {
            onError("Enter valid email or mobile number")
            return
        }


        viewModelScope.launch {
            try {
                // TODO: call repository.sendResetCode(state.email)
                onSuccess()
            } catch (e: Exception) {
                onError(e.message ?: "Failed to send code")
            }
        }
    }
}

data class ForgotPasswordUiState(
    val email: String = ""
)
