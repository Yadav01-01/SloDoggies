package com.bussiness.slodoggiesapp.data.uiState

import com.bussiness.slodoggiesapp.data.newModel.ownerService.CategoryItem
import com.bussiness.slodoggiesapp.data.newModel.ownerService.ServiceItem

data class PetServiceUiState(
    val searchText: String = "",
    val selectedServiceType: String? = null,
    val serviceCategory: List<CategoryItem> = emptyList(),
    val services: List<ServiceItem> = emptyList(),
    val filteredServices: List<ServiceItem> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
