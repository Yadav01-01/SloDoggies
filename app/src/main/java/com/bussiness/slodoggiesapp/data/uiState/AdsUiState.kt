package com.bussiness.slodoggiesapp.data.uiState

import com.bussiness.slodoggiesapp.data.newModel.sponsered.BusinessAdsData

data class AdsUiState(
    var selectedStatus: String = "Approved",
    val showStatusDialog: Boolean = true,
    val isNavigating: Boolean = false,
    val fromPreview: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val data: BusinessAdsData = BusinessAdsData()
)

