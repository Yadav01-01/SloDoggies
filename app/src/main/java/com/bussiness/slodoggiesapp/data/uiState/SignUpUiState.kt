package com.bussiness.slodoggiesapp.data.uiState

data class SignUpUiState(
    val userName: String = "",
    val contactInput: String = "",
    val contactType: String? = null, // "email" or "phone"
    val isValid: Boolean = false,
    val password: String = "",
    val confirmPassword: String = "",
    val updateAccountDialog: Boolean = false,
    val errorMessage: String? = null,
    val isLoading: Boolean = false
)
