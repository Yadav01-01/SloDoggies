package com.bussiness.slodoggiesapp.data.uiState

data class VerifyOtpUiState(
    val otp: String = "",
    val emailOrPhone: String = "",
    val successDialogVisible: Boolean = false,
    val disclaimerDialogVisible: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isTimerFinished: Boolean = false,
)