package com.bussiness.slodoggiesapp.data.uiState

data class LocationUiState(
    var latitude: Double? = null,
    var longitude: Double? = null,
    var address: String? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
