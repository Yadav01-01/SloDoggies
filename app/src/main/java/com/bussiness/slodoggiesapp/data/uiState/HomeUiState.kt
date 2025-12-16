package com.bussiness.slodoggiesapp.data.uiState

import com.bussiness.slodoggiesapp.data.newModel.home.FriendItem
import com.bussiness.slodoggiesapp.data.newModel.home.PostItem

data class HomeUiState(
    val posts: List<PostItem> = emptyList(),
    val friendsListData: List<FriendItem> = emptyList(),
    val showWelcomeDialog: Boolean = false,
    val welcomeTitle: String = "",
    val welcomeDescription: String = "",
    val welcomeButton: String = "",
    val showPetInfoDialog: Boolean = false,
    val showUserDetailsDialog: Boolean = false,
    val showProfileCreatedDialog: Boolean = false,
    val showContinueAddPetDialog: Boolean = false,
    val showReportDialog: Boolean = false,
    val showShareContent: Boolean = false,
    val showReportToast: Boolean = false,
    val petInfoDialogCount: Int = 0,
    val deleteDialog : Boolean = false,
    val message : String = "",
    val selectedReason : String = "",
    val postId : String = "",
    val userId : String = "",
    val errorMessage : String = "",
    val isLoading: Boolean = false,          // For first page loader
    val isLoadingMore: Boolean = false,
)

