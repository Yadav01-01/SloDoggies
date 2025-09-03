package com.bussiness.slodoggiesapp.model.petOwner


data class UserDetailsUiState(
    val name: String = "",
    val phoneNumber: String = "",
    val email: String = "",
    val isEmailVerified: Boolean = false,
    val isPhoneVerified: Boolean = false,
    val bio: String = ""
)
