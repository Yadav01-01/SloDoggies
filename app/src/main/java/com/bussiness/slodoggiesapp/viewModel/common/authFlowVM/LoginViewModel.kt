package com.bussiness.slodoggiesapp.viewModel.common.authFlowVM

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bussiness.slodoggiesapp.data.remote.Repository
import com.bussiness.slodoggiesapp.data.uiState.LoginUiState
import com.bussiness.slodoggiesapp.network.Resource
import com.bussiness.slodoggiesapp.util.Messages
import com.bussiness.slodoggiesapp.util.SessionManager
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: Repository,
    private var sessionManager: SessionManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState
    var fcmToken by mutableStateOf<String?>(null)
        private set


    init {
        fetchToken()
    }

    fun onContactChange(newInput: String) {
        val trimmedInput = newInput.trim()
        val isEmail = android.util.Patterns.EMAIL_ADDRESS.matcher(trimmedInput).matches()
        val isPhone = trimmedInput.matches(Regex("^[6-9][0-9]{9}$")) // 10-digit valid mobile numbers

        _uiState.value = when {
            isEmail -> _uiState.value.copy(
                contactInput = trimmedInput,
                isValid = true,
                contactType = ContactType.EMAIL,
                errorMessage = null
            )
            isPhone -> _uiState.value.copy(
                contactInput = trimmedInput,
                isValid = true,
                contactType = ContactType.PHONE,
                errorMessage = null
            )
            else -> _uiState.value.copy(
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

    fun showDisclaimerDialog() {
        _uiState.value = _uiState.value.copy(disclaimerDialog = true)
    }

    fun dismissDisclaimerDialog() {
        _uiState.value = _uiState.value.copy(disclaimerDialog = false)
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

    fun login(onSuccess: () -> Unit = { }, onError: (String) -> Unit) {
        val state = _uiState.value
        // Local validation before hitting API
        when {
            state.contactInput.isBlank() -> {
                onError(Messages.VALID_INPUT)
                return
            }
            state.password.isBlank() -> {
                onError(Messages.PASSWORD_CANNOT_EMPTY)
                return
            }
            !state.isValid -> {
                onError(state.errorMessage ?: Messages.INVALID_INPUT)
                return
            }
        }

        viewModelScope.launch {
            repository.userLogin(
                emailOrPhone = state.contactInput,
                password = state.password,
                deviceType = "Android",
                fcm_token = fcmToken.toString(),
                userType = sessionManager.getUserType().toString()
            ).collectLatest { result ->
                when (result) {
                    is Resource.Loading -> {
                        _uiState.value = _uiState.value.copy(isLoading = true)
                    }
                    is Resource.Success -> {
                        _uiState.value = _uiState.value.copy(isLoading = false)
                        result.data.let { response ->
                            if (response.success == true) {
                                _uiState.value = _uiState.value.copy(loginSuccess = true)
                                sessionManager.setUserId(response.data?.user?.id.toString())
                                sessionManager.setToken(response.data?.token.toString())
                                sessionManager.setLogin(true)
                                onSuccess()
                            } else {
                                onError(response.message ?: "Login failed")
                            }
                        }
                    }
                    is Resource.Error -> {
                        _uiState.value = _uiState.value.copy(isLoading = false)
                        onError(result.message)
                    }
                    Resource.Idle -> TODO()
                }
            }
        }
    }
}



enum class ContactType {
    EMAIL, PHONE
}
