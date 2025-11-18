package com.bussiness.slodoggiesapp.viewModel.common.authFlowVM

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.bussiness.slodoggiesapp.data.remote.Repository
import com.bussiness.slodoggiesapp.navigation.Routes
import com.bussiness.slodoggiesapp.network.Resource
import com.bussiness.slodoggiesapp.util.Messages
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class NewPasswordViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _uiState = MutableStateFlow(NewPasswordUiState())
    val uiState: StateFlow<NewPasswordUiState> = _uiState

    fun showUpdatePasswordDialog() {
        _uiState.update { it.copy(showUpdatePasswordDialog = true) }
    }

    fun dismissUpdatePasswordDialog() {
        _uiState.update { it.copy(showUpdatePasswordDialog = false) }
    }

    fun onSubmitClickDialog(navController: NavController) {
        _uiState.update { it.copy(showUpdatePasswordDialog = false) }
        navController.navigate(Routes.LOGIN_SCREEN) {
            popUpTo(navController.graph.startDestinationId) { inclusive = true }
            launchSingleTop = true
        }
    }

    fun onPasswordChange(newPassword: String) {
        _uiState.update { it.copy(password = newPassword) }
    }

    fun onConfirmPasswordChange(newPassword: String) {
        _uiState.update { it.copy(confirmPassword = newPassword) }
    }

    fun updatePassword(
        emailOrPhone: String,
        onSuccess: () -> Unit = {},
        onError: (String) -> Unit = {}
    ) {
        val state = _uiState.value

        when {
            state.password.length < 6 -> {
                onError(Messages.PASSWORD_VALIDATION_OF_6_CHAR)
                return
            }
            state.password != state.confirmPassword -> {
                onError(Messages.PASSWORD_MISMATCH)
                return
            }
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            repository.resetPassword(emailOrPhone, state.confirmPassword).collectLatest { result ->
                when (result) {
                    is Resource.Loading -> _uiState.update { it.copy(isLoading = true) }

                    is Resource.Success -> {
                        _uiState.update { it.copy(isLoading = false) }
                        showUpdatePasswordDialog()
                        onSuccess()
                    }

                    is Resource.Error -> {
                        _uiState.update { it.copy(isLoading = false) }
                        onError(result.message ?: "Something went wrong. Please try again.")
                    }

                    else -> Unit
                }
            }
        }
    }
}

data class NewPasswordUiState(
    val password: String = "",
    val confirmPassword: String = "",
    val showUpdatePasswordDialog: Boolean = false,
    val isLoading: Boolean = false
)


