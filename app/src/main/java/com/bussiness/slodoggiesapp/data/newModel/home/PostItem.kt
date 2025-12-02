package com.bussiness.slodoggiesapp.data.newModel.home

sealed class PostItem {

    // ------------------------------------------------------
    // Normal Feed Post
    // ------------------------------------------------------
    data class NormalPost(
        val userId: String,
        val userName: String,
        val userBadge: String?,
        val time: String,
        val caption: String,
        val description: String,
        val hashtags: String?,
        val media: MediaResponse?,                     // pet/parent images
        val mediaList: List<PostMediaResponse>,        // post images & videos
        val type: String,                              // "normal"
        val likes: Int,
        val comments: Int,
        val shares: Int,
        val isLiked: Boolean,
        val isSaved: Boolean
    ) : PostItem()

    // ------------------------------------------------------
    // Community / Event Post
    // ------------------------------------------------------
    data class CommunityPost(
        val userId: String,
        val userName: String,
        val userImage: String?,
        val time: String,

        // Event / Community Data
        val eventTitle: String,
        val eventStartDate: String?,
        val eventEndDate: String?,
        val eventDescription: String?,
        val location: String?,

        val media: MediaResponse?,                     // pet/parent images
        val mediaList: List<PostMediaResponse>,        // post images or videos

        val likes: Int,
        val comments: Int,
        val shares: Int,
        val isLiked: Boolean,
        val isSaved: Boolean
    ) : PostItem()

    // ------------------------------------------------------
    // Sponsored Post
    // ------------------------------------------------------
    data class SponsoredPost(
        val userId: String,
        val userName: String,
        val userBadge: String?,
        val time: String,
        val caption: String,
        val description: String,
        val media: MediaResponse?,
        val mediaList: List<PostMediaResponse>,
        val likes: Int,
        val comments: Int,
        val shares: Int,
        val isLiked: Boolean,
        val isSaved: Boolean
    ) : PostItem()
}
