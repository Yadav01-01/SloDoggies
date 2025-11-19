package com.bussiness.slodoggiesapp.data.uiState

data class PetAddUpDateUiState(
    val name: String? = null,
    val breed: String? = null,
    val age: String? = "< than 1 Year",
    val bio: String? = null,
    val image: String? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
