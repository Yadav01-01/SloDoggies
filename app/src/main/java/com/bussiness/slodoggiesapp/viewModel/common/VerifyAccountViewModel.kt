package com.bussiness.slodoggiesapp.viewModel.common

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class VerifyAccountViewModel @Inject constructor() : ViewModel() {

    private val _otp = MutableStateFlow("")
    val otp: StateFlow<String> = _otp.asStateFlow()

    fun updateOtp(newOtp: String) {
        _otp.value = newOtp
    }

    fun isOtpValid(): Boolean = _otp.value.length == 4

    fun onVerifyClick(
        type: String,
        navController: NavHostController,
        context: Context
    ) {
        if (!isOtpValid()) {
            Toast.makeText(context, "Please enter valid OTP", Toast.LENGTH_SHORT).show()
            return
        }

        //  Set verification result in previousBackStackEntry
        navController.previousBackStackEntry
            ?.savedStateHandle
            ?.set("verification_result", type)

        navController.popBackStack()
    }


}