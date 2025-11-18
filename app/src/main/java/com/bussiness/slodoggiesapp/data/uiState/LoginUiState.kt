package com.bussiness.slodoggiesapp.data.uiState

import com.bussiness.slodoggiesapp.viewModel.common.authFlowVM.ContactType

data class LoginUiState(
    val contactInput: String = "",
    val password: String = "",
    val contactType: ContactType? = null,
    val isValid: Boolean = false,
    val errorMessage: String? = null,
    val disclaimerDialog: Boolean = true,
    val isLoading: Boolean = false,
    val loginSuccess: Boolean = false
)