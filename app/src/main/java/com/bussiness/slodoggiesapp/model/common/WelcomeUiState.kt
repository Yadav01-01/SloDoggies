package com.bussiness.slodoggiesapp.model.common

data class WelcomeUiState(
    val showDialog: Boolean = false,
    val title: String = "",
    val description: String = "",
    val button: String = "Get Started"
)