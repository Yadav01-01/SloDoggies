package com.bussiness.slodoggiesapp.viewModel.common.authFlowVM

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.bussiness.slodoggiesapp.navigation.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor( ) : ViewModel() {

    private val _uiState = MutableStateFlow(SignUpUiState())
    val uiState: StateFlow<SignUpUiState> = _uiState

    fun onUserNameChange(newUserName: String) {
        _uiState.value = _uiState.value.copy(userName = newUserName)
    }

    fun onEmailChange(newEmail: String) {
        _uiState.value = _uiState.value.copy(email = newEmail)
    }

    fun onPasswordChange(newPassword: String) {
        _uiState.value = _uiState.value.copy(password = newPassword)
    }

    fun onConfirmPasswordChange(newPassword: String) {
        _uiState.value = _uiState.value.copy(confirmPassword = newPassword)
    }

    fun showUpdateAccountDialog() {
        _uiState.value = _uiState.value.copy(updateAccountDialog = true)
    }

    fun dismissUpdateAccountDialog(navController: NavController) {
        _uiState.value = _uiState.value.copy(updateAccountDialog = false)
        navController.navigate(Routes.MAIN_SCREEN){
            popUpTo(Routes.SIGNUP_SCREEN) { inclusive = true }
        }
    }

    fun createAccount(onSuccess: () -> Unit, onError: (String) -> Unit) {
        val state = _uiState.value

        when {
            state.userName.isBlank() -> {
                onError("Username cannot be empty")
                return
            }
            state.email.isBlank() -> {
                onError("Email cannot be empty")
                return
            }
            !android.util.Patterns.EMAIL_ADDRESS.matcher(state.email).matches() -> {
                onError("Invalid email address")
                return
            }
            state.password.length < 6 -> {
                onError("Password must be at least 6 characters")
                return
            }
            state.password != state.confirmPassword -> {
                onError("Passwords do not match")
                return
            }
        }

        viewModelScope.launch {
            try {
                // TODO: repository.createAccount(state.userName, state.email, state.password)
                onSuccess()
            } catch (e: Exception) {
                onError(e.message ?: "Failed to create account")
            }
        }
    }
}


data class SignUpUiState(
    val userName: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    var updateAccountDialog: Boolean = false
)
