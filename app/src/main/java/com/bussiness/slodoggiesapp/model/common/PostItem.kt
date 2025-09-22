package com.bussiness.slodoggiesapp.model.common

sealed class PostItem {
    data class NormalPost(   val user: String,
                             val role: String,
                             val time: String,
                             val caption: String,
//                             val hashtag: String,
                             val description: String,
                             val mediaList: List<MediaItem>,
                             val likes: Int,
                             val comments: Int,
                             val shares: Int) : PostItem()

    data class CommunityPost(val userName: String,
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
                             val onClickFollow: () -> Unit,
                             val onClickMore: () -> Unit,
                             val likes: Int,
                             val comments: Int,
                             val shares: Int) : PostItem()

    data class SponsoredPost(val user: String,
                             val role: String = "",
                             val time: String,
                             val caption: String,
                             val description: String,
                             val mediaList: List<MediaItem>,
                             val likes: Int,
                             val comments: Int,
                             val shares: Int) : PostItem()
}

data class Comment(
    val id: String,
    val userName: String,
    val userRole: String,
    val userAvatar: String,
    val text: String,
    val timeAgo: String,
    val likeCount: Int,
    val isLiked: Boolean,
    val replies: List<Comment> = emptyList()
)