package com.bussiness.slodoggiesapp.viewModel.common.authFlowVM

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.bussiness.slodoggiesapp.navigation.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class NewPasswordViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(NewPasswordUiState())
    val uiState: StateFlow<NewPasswordUiState> = _uiState

    fun showUpdatePasswordDialog() {
        _uiState.value = _uiState.value.copy(showUpdatePasswordDialog = true)
    }

    fun dismissUpdatePasswordDialog() {
        _uiState.value = _uiState.value.copy(showUpdatePasswordDialog = false)
    }

    fun onSubmitClickDialog(navController: NavController) {
        _uiState.value = _uiState.value.copy(showUpdatePasswordDialog = false)
        navController.navigate(Routes.LOGIN_SCREEN) {
            popUpTo(navController.graph.startDestinationId) {
                inclusive = true
            }
            launchSingleTop = true // prevent multiple copies of login screen
        }

    }

    fun onPasswordChange(newPassword: String) {
        _uiState.value = _uiState.value.copy(password = newPassword)
    }

    fun onConfirmPasswordChange(newPassword: String) {
        _uiState.value = _uiState.value.copy(confirmPassword = newPassword)
    }

    fun updatePassword(onSuccess: () -> Unit, onError: (String) -> Unit) {
        val state = _uiState.value

        when {
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
                // TODO: repository.updatePassword(state.password)
                delay(1000) // simulate API
                onSuccess()
            } catch (e: Exception) {
                onError(e.message ?: "Failed to update password")
            }
        }
    }
}

data class NewPasswordUiState(
    val password: String = "",
    val confirmPassword: String = "",
    val showUpdatePasswordDialog: Boolean = false
)

