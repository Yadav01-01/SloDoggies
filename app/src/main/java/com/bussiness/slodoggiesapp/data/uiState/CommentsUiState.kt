package com.bussiness.slodoggiesapp.data.uiState

data class CommentsUiState( val comments: List<CommentItem> = emptyList(),
                            val currentPage: Int = 1,
                            val isLastPage: Boolean = false,
                            val isLoading: Boolean = false,
                            val commentsId:Int = 1,
                            val isLoadingMore: Boolean = false)
