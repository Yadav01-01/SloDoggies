package com.bussiness.slodoggiesapp.data.uiState

import com.bussiness.slodoggiesapp.data.model.common.AllChatListData

data class ChatListUiState(
    val isLoading: Boolean = false,
    val data: MutableList<AllChatListData>? = null,
    val error: String? = null
)
