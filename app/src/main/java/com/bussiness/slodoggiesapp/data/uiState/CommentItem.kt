package com.bussiness.slodoggiesapp.data.uiState

import com.google.gson.annotations.SerializedName

data class CommentItem( val id: Int,
                        val content: String,
                        val createdAt: String,
                        val user: CommentUser,
                        val likeCount: Int,
                        val isLikedByCurrentUser: Boolean,
                        val replies: List<CommentReply>)

data class CommentUser(
    val id: Int,
    val name: String,
    val image: String?
)

data class CommentReply(
    val id: Int? = null,
    val content: String? = null,
    val createdAt: String? = null,
    val user: CommentUser? = null,
    val likeCount: Int? = null,
    val isLikedByCurrentUser: Boolean? = null
)
