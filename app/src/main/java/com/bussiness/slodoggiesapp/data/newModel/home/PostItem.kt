package com.bussiness.slodoggiesapp.data.newModel.home

import androidx.compose.runtime.Immutable

@Immutable
sealed class PostItem(open val stableKey: String) {

    // ------------------------------------------------------
    // Normal Feed Post
    // ------------------------------------------------------
    @Immutable
    data class NormalPost(
        val postId: String,
        val userId: String,
        val userName: String,
        val petName: String,
        val userType: String,
        val userPost: Boolean,
        val userBadge: String?,
        val time: String,
        val caption: String,
        val description: String,
        val hashtags: String?,
        val media: MediaResponse?,                  // pet/parent images
        val mediaList: List<PostMediaResponse>,     // post images & videos
        val type: String,
        val likes: Int,
        val comments: Int,
        val shares: Int,
        val isLiked: Boolean,
        val isSaved: Boolean,
        val iAmFollowing: Boolean,
        val isFollowLoading: Boolean = false
    ) : PostItem("normal_$postId")

    // ------------------------------------------------------
    // Community / Event Post
    // ------------------------------------------------------
    @Immutable
    data class CommunityPost(
        val postId: String,
        val userId: String,
        val userName: String,
        val userImage: String?,
        val userType : String,
        val userPost: Boolean,
        val time: String,

        // Event / Community Data
        val eventTitle: String,
        val eventStartDate: String?,
        val eventEndDate: String?,
        val eventDescription: String?,
        val location: String?,
        val alreadyJoined: Boolean?,


        val media: MediaResponse?,
        val mediaList: List<PostMediaResponse>,

        val likes: Int,
        val comments: Int,
        val shares: Int,
        val isLiked: Boolean,
        val isSaved: Boolean,
        val iAmFollowing: Boolean,
        val isFollowLoading: Boolean = false
    ) : PostItem("community_$postId")   // FIXED

    // ------------------------------------------------------
    // Sponsored Post
    // ------------------------------------------------------
    @Immutable
    data class SponsoredPost(
        val postId: String,
        val userId: String,
        val userName: String,
        val userPost: Boolean,
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
    ) : PostItem("sponsored_$postId")  // FIXED
}
