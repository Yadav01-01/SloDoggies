package com.bussiness.slodoggiesapp.data.uiState

import com.bussiness.slodoggiesapp.data.newModel.ownerProfile.OwnerData
import com.bussiness.slodoggiesapp.data.newModel.ownerProfile.OwnerPostItem

data class OwnerProfileUiState(
    val isLoading: Boolean = false,
    val data: OwnerData = OwnerData(),
    val posts: List<OwnerPostItem> = emptyList(),
    val errorMessage: String? = null,
    val showPetInfoDialog: Boolean = false,
    val petAddedSuccessDialog: Boolean = false,
    val selectedPet: Int = 0,
    val isLoadingMore: Boolean = false,
    val canLoadMore: Boolean = true,
    val currentPage: Int = 1,
    val isRefreshing: Boolean = false
)

