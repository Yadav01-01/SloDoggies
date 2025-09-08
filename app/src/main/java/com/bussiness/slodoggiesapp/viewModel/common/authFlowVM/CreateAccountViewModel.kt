package com.bussiness.slodoggiesapp.viewModel.common.authFlowVM

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.bussiness.slodoggiesapp.navigation.Routes
import com.bussiness.slodoggiesapp.util.ErrorMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(SignUpUiState())
    val uiState: StateFlow<SignUpUiState> = _uiState

    fun onUserNameChange(newUserName: String) {
        _uiState.value = _uiState.value.copy(userName = newUserName)
    }

    fun onContactChange(newInput: String) {
        val trimmedInput = newInput.trim()

        val isEmail = android.util.Patterns.EMAIL_ADDRESS.matcher(trimmedInput).matches()
        val isPhone = trimmedInput.matches(Regex("^[6-9][0-9]{9}$")) // 10-digit

        when {
            isEmail -> {
                _uiState.value = _uiState.value.copy(
                    contactInput = trimmedInput,
                    isValid = true,
                    contactType = "email",
                    errorMessage = null
                )
            }
            isPhone -> {
                _uiState.value = _uiState.value.copy(
                    contactInput = trimmedInput,
                    isValid = true,
                    contactType = "phone",
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

    fun onConfirmPasswordChange(newPassword: String) {
        _uiState.value = _uiState.value.copy(confirmPassword = newPassword)
    }

    fun showUpdateAccountDialog() {
        _uiState.value = _uiState.value.copy(updateAccountDialog = true)
    }

    fun dismissUpdateAccountDialog(navController: NavController) {
        _uiState.value = _uiState.value.copy(updateAccountDialog = false)
        navController.navigate(Routes.MAIN_SCREEN) {
            popUpTo(Routes.SIGNUP_SCREEN) { inclusive = true }
        }
    }

    fun createAccount(onSuccess: () -> Unit, onError: (String) -> Unit) {
        val state = _uiState.value

        when {
            state.userName.isBlank() -> {
                onError(ErrorMessage.USER_NAME_NOT_EMPTY)
                return
            }
            state.contactInput.isBlank() -> {
                onError(ErrorMessage.EMAIL_PHONE_NOT_EMPTY)
                return
            }
            !state.isValid -> {
                onError(state.errorMessage ?: ErrorMessage.INVALID_CONTACT)
                return
            }
            state.password.length < 6 -> {
                onError(ErrorMessage.PASSWORD_LIMIT)
                return
            }
            state.password != state.confirmPassword -> {
                onError(ErrorMessage.PASSWORD_NOT_MATCH)
                return
            }
        }

        viewModelScope.launch {
            try {
                // TODO: repository.createAccount(state.userName, state.contactInput, state.password)
                onSuccess()
            } catch (e: Exception) {
                onError(e.message ?: "Failed to create account")
            }
        }
    }
}

data class SignUpUiState(
    val userName: String = "",
    val contactInput: String = "",
    val contactType: String? = null, // "email" or "phone"
    val isValid: Boolean = false,
    val password: String = "",
    val confirmPassword: String = "",
    val updateAccountDialog: Boolean = false,
    val errorMessage: String? = null
)
