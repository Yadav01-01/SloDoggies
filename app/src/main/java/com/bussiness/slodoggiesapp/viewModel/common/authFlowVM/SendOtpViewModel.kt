package com.bussiness.slodoggiesapp.viewModel.common.authFlowVM

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.bussiness.slodoggiesapp.data.newModel.OtpResponse
import com.bussiness.slodoggiesapp.data.remote.Repository
import com.bussiness.slodoggiesapp.data.uiState.SignUpUiState
import com.bussiness.slodoggiesapp.navigation.Routes
import com.bussiness.slodoggiesapp.network.Resource
import com.bussiness.slodoggiesapp.util.ErrorMessage
import com.bussiness.slodoggiesapp.util.Messages
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
class SendOtpViewModel @Inject constructor(
    private val repository: Repository,
    private val sessionManager: SessionManager
) : ViewModel() {

    // Handles OTP API result (loading, success, error)
    private val _otpState = MutableStateFlow<Resource<OtpResponse>>(Resource.Idle)
    val otpState: StateFlow<Resource<OtpResponse>> = _otpState.asStateFlow()

    // Holds all UI input states
    private val _uiState = MutableStateFlow(SignUpUiState())
    val uiState: StateFlow<SignUpUiState> = _uiState.asStateFlow()

    // --- UI Field Updates ---
    fun onUserNameChange(newUserName: String) {
        _uiState.value = _uiState.value.copy(userName = newUserName)
    }

    fun onContactChange(newInput: String) {
        val trimmedInput = newInput.trim()
        val isEmail = android.util.Patterns.EMAIL_ADDRESS.matcher(trimmedInput).matches()
        val isPhone = trimmedInput.matches(Regex("^[6-9][0-9]{9}$"))

        when {
            isEmail -> _uiState.value = _uiState.value.copy(
                contactInput = trimmedInput,
                isValid = true,
                contactType = "email",
                errorMessage = null
            )
            isPhone -> _uiState.value = _uiState.value.copy(
                contactInput = trimmedInput,
                isValid = true,
                contactType = "phone",
                errorMessage = null
            )
            else -> _uiState.value = _uiState.value.copy(
                contactInput = trimmedInput,
                isValid = false,
                contactType = null,
                errorMessage = Messages.VALID_INPUT
            )
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

    // --- API Call: Send OTP ---
    fun sendOtp(onSuccess: () -> Unit = {}, onError: (String) -> Unit = {}) {
        val state = _uiState.value

        when {
            state.contactInput.isBlank() -> {
                onError(ErrorMessage.EMAIL_PHONE_NOT_EMPTY)
                return
            }
            !state.isValid -> {
                onError(state.errorMessage ?: ErrorMessage.INVALID_CONTACT)
                return
            }
        }

        viewModelScope.launch {
            repository.sendOtp(state.contactInput, "signup").collectLatest { result ->
                when (result) {
                    is Resource.Loading -> {
                        _uiState.update { it.copy(isLoading = true) }
                        _otpState.value = Resource.Loading
                    }
                    is Resource.Success -> {
                        _otpState.value = result
                        onSuccess()
                        sessionManager.setSignupFlow(true)
                    }
                    is Resource.Error -> {
                        _otpState.value = Resource.Error(result.message)
                        onError(result.message)
                    }
                    else -> Unit
                }
            }
        }
    }

    /** Reset state if needed */
    fun resetOtpState() {
        _otpState.value = Resource.Idle
    }
}



