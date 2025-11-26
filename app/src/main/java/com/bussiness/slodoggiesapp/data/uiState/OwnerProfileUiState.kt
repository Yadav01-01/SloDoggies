package com.bussiness.slodoggiesapp.data.uiState

import com.bussiness.slodoggiesapp.data.newModel.ownerProfile.OwnerData

data class OwnerProfileUiState(
    val isLoading: Boolean = false,
    val data: OwnerData = OwnerData(),
    val errorMessage: String? = null,
    val showPetInfoDialog: Boolean = false,
    val petAddedSuccessDialog: Boolean = false,
)

