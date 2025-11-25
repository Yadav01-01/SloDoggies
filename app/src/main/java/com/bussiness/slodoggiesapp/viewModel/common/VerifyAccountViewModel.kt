package com.bussiness.slodoggiesapp.viewModel.common

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.bussiness.slodoggiesapp.data.newModel.otpsendverify.Data
import com.bussiness.slodoggiesapp.data.newModel.otpsendverify.OtpVerifyModel
import com.bussiness.slodoggiesapp.data.remote.Repository
import com.bussiness.slodoggiesapp.network.Resource
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
class VerifyAccountViewModel @Inject constructor(
    private val repository: Repository,
    private val sessionManager: SessionManager
) : ViewModel() {


    private val _uiState = MutableStateFlow(OtpVerifyModel())
    val uiState: StateFlow<OtpVerifyModel> = _uiState.asStateFlow()


    private val _otp = MutableStateFlow("")
    val otp: StateFlow<String> = _otp.asStateFlow()

    fun updateOtp(newOtp: String) {
        _otp.value = newOtp
    }

    fun userEmailPhone(value: String) {
        _uiState.update { state ->
            val current = state.data ?: Data()
            val trimmed = value.trim()
            state.copy(
                data = current.copy(emailOrPhone = trimmed)
            )
        }
    }

    fun sendOtpRequest(type:String?="",
                       onSuccess: () -> Unit = { },
                       onError: (String) -> Unit){
        viewModelScope.launch {
            val phoneEmail= uiState.value.data?.emailOrPhone?:""

            if (type.equals("dialogPhone")){
                if (phoneEmail.isEmpty()){
                    onError("Phone cant be empty")
                    return@launch
                }
            }else{
                if (phoneEmail.isEmpty()){
                    onError("Email cant be empty")
                    return@launch
                }
            }
            repository.sendOtpRequest(phoneEmail,sessionManager.getUserId()).collectLatest { result ->
                when (result) {
                    is Resource.Loading -> _uiState.update { it.copy(isLoading = true) }
                    is Resource.Success -> {
                        _uiState.value = _uiState.value.copy(isLoading = false)
                        val response = result.data
                        if (response.success == true && response.data != null) {
                            val data = response.data
                            _uiState.value = _uiState.value.copy(
                                data = data
                            )
                            onSuccess()
                        } else {
                            onError(response.message ?: "Failed to fetch owner details")
                        }
                    }
                    is Resource.Error -> {
                        _uiState.update { it.copy(isLoading = false) }
                        onError(result.message)
                    }
                    Resource.Idle -> Unit
                }
            }
        }
    }

    fun isOtpValid(): Boolean =_otp.value.length == 4



    fun onVerifyClick(
        context: Context,
        email:String="",
        onSuccess: () -> Unit = { },
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            if (!isOtpValid()) {
                Toast.makeText(context, Messages.ENTER_VALID_OTP, Toast.LENGTH_SHORT).show()
                return@launch
            }
            repository.otpVerifyEmailPhoneRequest(email,sessionManager.getUserId(),_otp.value).collectLatest { result ->
                when (result) {
                    is Resource.Loading -> _uiState.update { it.copy(isLoading = true) }
                    is Resource.Success -> {
                        _uiState.value = _uiState.value.copy(isLoading = false)
                        val response = result.data
                        if (response.success == true) {
                            val data = response.data
                            _uiState.value = _uiState.value.copy(
                                data = data
                            )
                            onSuccess()
                        } else {
                            onError(response.message ?: "Failed to fetch owner details")
                        }
                    }
                    is Resource.Error -> {
                        _uiState.update { it.copy(isLoading = false) }
                        onError(result.message)
                    }
                    Resource.Idle -> Unit
                }
            }
        }
    }


}