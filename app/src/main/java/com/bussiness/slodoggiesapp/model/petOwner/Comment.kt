package com.bussiness.slodoggiesapp.model.petOwner

data class Comment(
    val id: String,
    val userName: String,
    val userRole: String,
    val userAvatar: String,
    val text: String,
    val timeAgo: String,
    val likeCount: Int,
    val isLiked: Boolean,
    val replies: List<Comment> = emptyList(),
    val myComment : Boolean

)
