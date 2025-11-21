package com.bussiness.slodoggiesapp.data.uiState

import com.bussiness.slodoggiesapp.data.newModel.PostItem


data class UserPostUiState(
    val isLoading: Boolean = false,
    val posts: List<PostItem> = emptyList(),
    val message: String? = null,       // for toast
    val showNoData: Boolean = false    // show "No data available"
)



