package com.bussiness.slodoggiesapp.viewModel.common.authFlowVM

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bussiness.slodoggiesapp.data.newModel.OtpResponse
import com.bussiness.slodoggiesapp.data.remote.Repository
import com.bussiness.slodoggiesapp.network.Resource
import com.bussiness.slodoggiesapp.util.Messages
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    // Tracks OTP API response (loading/success/error)
    private val _otpState = MutableStateFlow<Resource<OtpResponse>>(Resource.Idle)
    val otpState: StateFlow<Resource<OtpResponse>> = _otpState.asStateFlow()

    //  Holds email input field value
    private val _uiState = MutableStateFlow(UiStateEmail())
    val uiState: StateFlow<UiStateEmail> = _uiState.asStateFlow()

    fun onEmailChange(newEmail: String) {
        _uiState.value = _uiState.value.copy(email = newEmail)
    }

    fun sendCode(onSuccess: () -> Unit= { }, onError: (String) -> Unit) {
        val emailValue = _uiState.value.email.trim()

        //  Input validation
        when {
            emailValue.isBlank() -> {
                onError(Messages.EMAIL_PHONE_CANNOT_EMPTY)
                return
            }

            !(android.util.Patterns.EMAIL_ADDRESS.matcher(emailValue).matches() ||
                    android.util.Patterns.PHONE.matcher(emailValue).matches()) -> {
                onError(Messages.VALID_INPUT)
                return
            }
        }

        //  Network call with proper state handling
        viewModelScope.launch {
            repository.sendOtp(emailValue, "forgot").collectLatest { result ->
                when (result) {
                    is Resource.Loading -> _otpState.value = Resource.Loading

                    is Resource.Success -> {
                        _otpState.value = Resource.Success(result.data)
                        onSuccess()
                    }

                    is Resource.Error -> {
                        _otpState.value = Resource.Error(result.message)
                        onError(result.message)
                    }

                    else -> _otpState.value = Resource.Idle
                }
            }
        }
    }
}

//  Holds input field values safely for Compose UI
data class UiStateEmail(
    val email: String = ""
)
