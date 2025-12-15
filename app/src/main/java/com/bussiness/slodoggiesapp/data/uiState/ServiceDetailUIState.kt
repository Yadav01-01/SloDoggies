package com.bussiness.slodoggiesapp.data.uiState

import com.bussiness.slodoggiesapp.data.newModel.ownerService.ServiceDetailsData

data class ServiceDetailUIState(
    val serviceDetail: ServiceDetailsData? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
