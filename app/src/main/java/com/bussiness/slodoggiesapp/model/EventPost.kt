package com.bussiness.slodoggiesapp.model

data class EventPost(
    val userName: String,
    val userImage: Int,
    val postImage: Int,
    val label: String,
    val time: String,
    val eventTitle: String,
    val eventDate: String,
    val eventDescription: String,
    val eventDuration: String,
    val location: String,
    val onClickFollow: () -> Unit,
    val onClickMore: () -> Unit,
    val onClickLike: () -> Unit,
    val onClickComment: () -> Unit,
    val onClickShare: () -> Unit
)

