package com.bussiness.slodoggiesapp.data.model.petOwner


data class UserDetailsUiState(
    val isLoading: Boolean = false,
    val name: String = "",
    val email: String = "",
    val phoneNumber: String = "",
    val bio: String = "",
    val isEmailVerified: Boolean = false,
    val isPhoneVerified: Boolean = false,
    val profilePicUrl: String? = null,
    val loginSuccess: Boolean = false,
    val errorMessage: String? = null
)
