package com.bussiness.slodoggiesapp.data.uiState

import com.bussiness.slodoggiesapp.data.model.common.AudienceItem

data class FollowerFollowingUiState(
    val isLoading: Boolean = false,
    val followers: List<AudienceItem> = emptyList(),
    val following: List<AudienceItem> = emptyList(),
    val error: String? = null,
    val selectedOption: String = "Follower",
    val query: String = "",
    val removeDialog: Boolean = false,
    val removeFollowing: Boolean = false,
    val removeToast: Boolean = false,
    val isNavigating: Boolean = false,
    val isEmptyData: Boolean = false,
    val isMoreLoading: Boolean = false,
    val endReached: Boolean = false,
    val isRefreshing: Boolean = false,
    val selectedUserId : String = "",
    val totalFollower : String = "",
    val totalFollowing : String = ""
)
