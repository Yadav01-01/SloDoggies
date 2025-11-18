package com.bussiness.slodoggiesapp.data.model.businessProvider

data class EventPost(
    val userName: String,
    val userImage: Int,
    val postImage: Int,
    val label: String,
    val time: String,
    val eventTitle: String,
    val eventStartDate: String,
    val eventEndDate: String,
    val eventDescription: String,
    val eventDuration: String,
    val location: String,
    val likes: Int,
    val comments: Int,
    val shares: Int,
    val onClickFollow: () -> Unit,
    val onClickMore: () -> Unit,
    val onClickLike: () -> Unit,
    val onClickComment: () -> Unit,
    val onClickShare: () -> Unit
)

