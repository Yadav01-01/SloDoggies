package com.bussiness.slodoggiesapp.viewModel.common

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.bussiness.slodoggiesapp.data.model.main.UserType
import com.bussiness.slodoggiesapp.data.remote.Repository
import com.bussiness.slodoggiesapp.data.uiState.VerifyOtpUiState
import com.bussiness.slodoggiesapp.navigation.Routes
import com.bussiness.slodoggiesapp.network.Resource
import com.bussiness.slodoggiesapp.util.SessionManager
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VerifyOTPViewModel @Inject constructor(
    private val sessionManager: SessionManager,
    private val repository: Repository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(VerifyOtpUiState())
    val uiState: StateFlow<VerifyOtpUiState> = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<UiEvent>()
    val events = _events.asSharedFlow()
    var fcmToken by mutableStateOf<String?>(null)
        private set

    init {
        fetchToken()
    }

    sealed class UiEvent {
        data class ShowToast(val message: String) : UiEvent()
        data object NavigateToNext : UiEvent()
    }

    // --- UI State Updates ---
    fun updateOtp(newOtp: String) {
        _uiState.update { it.copy(otp = newOtp) }
    }

    private fun showSuccessDialog() {
        _uiState.update { it.copy(successDialogVisible = true, isLoading = false) }
    }

    fun dismissSuccessDialog(navController: NavHostController) {
        _uiState.update { it.copy(successDialogVisible = false) }
        navigateToNextScreen(navController)
    }

    fun onTimerFinish() {
        _uiState.update { it.copy(isTimerFinished = true) }
    }

    fun resetTimer() {
        _uiState.update { it.copy(isTimerFinished = false) }
    }

    private fun navigateToNextScreen(navController: NavHostController) {
        val route = if (sessionManager.getUserType() == UserType.Professional) {
            Routes.BUSINESS_REGISTRATION
        } else {
            Routes.NOTIFICATION_PERMISSION_SCREEN
        }
        navController.navigate(route) {
            popUpTo(Routes.SIGNUP_SCREEN) { inclusive = true }
        }
    }

    // --- OTP Validation ---
    fun isOtpValid(): Boolean = _uiState.value.otp.length == 4

    // --- Verify OTP and Register User ---
    fun onVerifyClick(
        type: String,
        name: String,
        emailOrPhone: String,
        password: String
    ) {
        if (!isOtpValid()) {
            viewModelScope.launch {
                _events.emit(UiEvent.ShowToast("Please enter a valid OTP"))
            }
            return
        }

        when (type) {
            //  Forgot Password flow
            "forgot" -> {
                viewModelScope.launch {
                    _uiState.update { it.copy(isLoading = true, errorMessage = null) }
                    repository.verifyForgotOtp(emailOrPhone, uiState.value.otp)
                        .collectLatest { result ->
                            when (result) {
                                is Resource.Loading -> _uiState.update { it.copy(isLoading = true) }
                                is Resource.Success -> {
                                    // Success → navigate to reset password screen
                                    _uiState.update { it.copy(isLoading = false) }
                                    _events.emit(UiEvent.NavigateToNext)
                                }
                                is Resource.Error -> {
                                    _uiState.update { it.copy(isLoading = false) }
                                    _events.emit(
                                        UiEvent.ShowToast(result.message ?: "Failed to verify OTP")
                                    )
                                }
                                else -> Unit
                            }
                        }
                }
            }
            // Normal Registration flow
            else -> {
                viewModelScope.launch {
                    _uiState.update { it.copy(isLoading = true, errorMessage = null) }
                    repository.registerUser(
                        fullName = name,
                        emailOrPhone = emailOrPhone,
                        password = password,
                        deviceType = "Android",
                        fcm_token = fcmToken.toString(),
                        userType = sessionManager.getUserType().toString(),
                        otp = _uiState.value.otp
                    ).collectLatest { result ->
                        when (result) {
                            is Resource.Loading -> _uiState.update { it.copy(isLoading = true) }
                            is Resource.Success -> {
                                sessionManager.setLogin(true)
                                sessionManager.setUserId(result.data.data?.user?.id.toString())
                                sessionManager.setToken(result.data.data?.token.toString())
                                showSuccessDialog()
                            }
                            is Resource.Error -> {
                                _uiState.update { it.copy(isLoading = false) }
                                _events.emit(UiEvent.ShowToast(result.message))
                            }
                            else -> Unit
                        }
                    }
                }
            }
        }
    }

    fun resendOtp(emailOrPhone: String, type: String) {
        viewModelScope.launch {

            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            repository.resendOTP(emailOrPhone, type).collectLatest { result ->
                when (result) {

                    is Resource.Loading -> {
                        _uiState.update { it.copy(isLoading = true) }
                    }

                    is Resource.Success -> {
                        _uiState.update { it.copy(isLoading = false) }

                        result.data.message?.let { message ->
                            _events.emit(UiEvent.ShowToast(message))
                        }
                    }

                    is Resource.Error -> {
                        _uiState.update { it.copy(isLoading = false) }
                        onError(result.message)
                    }

                    Resource.Idle -> Unit // No action needed
                }
            }
        }
    }


    fun fetchToken() {
        FirebaseMessaging.getInstance().token
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    fcmToken = task.result
                    Log.d("FCM", "FCM Token: ${task.result}")
                } else {
                    fcmToken = "Fetching FCM token failed"
                    Log.e("FCM", "Fetching FCM token failed", task.exception)
                }
            }
    }


    private fun onError(message: String?) {
        _uiState.update { it.copy(errorMessage = message ?: "Something went wrong") }
    }

}
