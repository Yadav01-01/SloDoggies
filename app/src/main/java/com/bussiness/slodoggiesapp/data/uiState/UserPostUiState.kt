package com.bussiness.slodoggiesapp.data.uiState

import com.bussiness.slodoggiesapp.data.newModel.PostItem


data class UserPostUiState(
    val isLoading: Boolean = false,
    val posts: List<PostItem> = emptyList(),
    val message: String? = null,       // for toast
    val showCommentsDialog: Boolean = false,
    val showCommentsType: String = "",
    val deleteComment: Boolean = false,
    val deleteCommentId: String = "",
    val isNavigating: Boolean = false
)



