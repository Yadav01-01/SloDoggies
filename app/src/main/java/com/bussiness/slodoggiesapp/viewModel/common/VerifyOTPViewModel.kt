package com.bussiness.slodoggiesapp.viewModel.common

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.bussiness.slodoggiesapp.model.main.UserType
import com.bussiness.slodoggiesapp.navigation.Routes
import com.bussiness.slodoggiesapp.util.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class VerifyOTPViewModel @Inject constructor(
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _otp = MutableStateFlow("")
    val otp: StateFlow<String> = _otp.asStateFlow()

    private val _successDialog = MutableStateFlow(false)
    val successDialog: StateFlow<Boolean> = _successDialog.asStateFlow()

    private val _disclaimerDialog = MutableStateFlow(false)
    val disclaimerDialog: StateFlow<Boolean> = _disclaimerDialog.asStateFlow()

    fun updateOtp(newOtp: String) {
        _otp.value = newOtp
    }

    fun showDisclaimerDialog() {
        _disclaimerDialog.value = true
    }

    private fun showSuccessDialog() {
        _successDialog.value = true
    }

    fun dismissSuccessDialog() {
        _successDialog.value = false
    }
    fun dismissDisclaimerDialog(navController: NavHostController) {
        _disclaimerDialog.value = false
        if (sessionManager.getUserType() == UserType.BUSINESS_PROVIDER) {
            navController.navigate(Routes.BUSINESS_REGISTRATION) {
                popUpTo(Routes.SIGNUP_SCREEN) { inclusive = true }
            }
        } else {
            navController.navigate(Routes.NOTIFICATION_PERMISSION_SCREEN) {
                popUpTo(Routes.SIGNUP_SCREEN) { inclusive = true }
            }
        }
    }


    fun isOtpValid(): Boolean = _otp.value.length == 4

    fun onVerifyClick(type: String, navController: NavHostController, context: Context) {
        if (type == "forgotPass") {
            navController.navigate(Routes.NEW_PASSWORD_SCREEN)
            return
        }

        if (isOtpValid()) {
            sessionManager.setLogin(true)
            showSuccessDialog()
        } else {
            Toast.makeText(context, "Please enter valid OTP", Toast.LENGTH_SHORT).show()
        }
    }

}
