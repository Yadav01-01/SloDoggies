package com.bussiness.slodoggiesapp.data.uiState

data class LocationUiState(
    val latitude: Double? = null,
    val longitude: Double? = null,
    val address: String? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
