package com.bussiness.slodoggiesapp.data.newModel.home

import com.bussiness.slodoggiesapp.data.newModel.BaseResponse
import com.google.gson.annotations.SerializedName

// ------------------------------------------------------
// Main API Response
// ------------------------------------------------------
data class HomeFeedResponse(
    @SerializedName("success") override val success: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") override val message: String,
    @SerializedName("data") val data: HomeFeedData?
) : BaseResponse

// ------------------------------------------------------
// Pagination + Items
// ------------------------------------------------------
data class HomeFeedData(
    @SerializedName("page") val page: String?,
    @SerializedName("limit") val limit: Int?,
    @SerializedName("totalPage") val totalPage: Int?,
    @SerializedName("items") val items: List<HomeFeedItemResponse>?
)

// ------------------------------------------------------
// Feed Item (post, group post, sponsored, etc.)
// ------------------------------------------------------
data class HomeFeedItemResponse(
    @SerializedName("userId") val userId: String?,
    @SerializedName("postId") val postId: String?,
    @SerializedName("groupId") val groupId: String? = null,
    @SerializedName("alreadyJoined") val alreadyJoined: Boolean? = null,
    @SerializedName("type") val type: String?, // normal, sponsored, event, group
    @SerializedName("author") val author: AuthorResponse? = null,
    @SerializedName("content") val content: ContentResponse? = null,
    @SerializedName("media") val media: MediaResponse? = null,
    @SerializedName("postMedia") val postMedia: List<PostMediaResponse>? = null,
    @SerializedName("engagement") val engagement: EngagementResponse? = null,
    @SerializedName("itemsuccess") val itemSuccess: ItemSuccessResponse? = null,
    @SerializedName("createdAt") val createdAt: String? = null
)

// ------------------------------------------------------
// Author
// ------------------------------------------------------
data class AuthorResponse(
    @SerializedName("userId") val userId: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("petName") val petName: String? = null,
    @SerializedName("badge") val badge: String? = null,
    @SerializedName("time") val time: String? = null
)

// ------------------------------------------------------
// Content (title + description + event timings + location)
// ------------------------------------------------------
data class ContentResponse(
    @SerializedName("title") val title: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("hashtags") val hashtags: String? = null,
    @SerializedName("location") val location: String? = null,
    @SerializedName("startTime") val startTime: String? = null,
    @SerializedName("endTime") val endTime: String? = null,
    @SerializedName("startDate") val startDate: String? = null,
    @SerializedName("endDate") val endDate: String? = null,

)

// ------------------------------------------------------
// Optional Media (Profile images / pet images)
// ------------------------------------------------------
data class MediaResponse(
    @SerializedName("petImageUrl") val petImageUrl: String? = null,
    @SerializedName("parentImageUrl") val parentImageUrl: String? = null
)

// ------------------------------------------------------
// Post Media List (Images + Videos)
// ------------------------------------------------------
data class PostMediaResponse(
    @SerializedName("mediaUrl") val mediaUrl: String?,
    @SerializedName("type") val type: String?, // image / video
    @SerializedName("thumbnailUrl") val thumbnailUrl: String? = null
)

// ------------------------------------------------------
// Likes, Comments, Shares (any because backend uses int / object)
// ------------------------------------------------------
data class EngagementResponse(
    @SerializedName("likes") val likes: Any? = null,
    @SerializedName("comments") val comments: Any? = null,
    @SerializedName("shares") val shares: Any? = null
)

// ------------------------------------------------------
// Boolean flags for like, follow, save
// ------------------------------------------------------
data class ItemSuccessResponse(
    @SerializedName("intrested") val intrested: Boolean? = null,
    @SerializedName("isLiked") val isLiked: Boolean? = null,
    @SerializedName("isSave") val isSave: Boolean? = null,
    @SerializedName("userFollowMe") val userFollowMe: Boolean? = null,
    @SerializedName("iAmFollowing") val iAmFollowing: Boolean? = null
)
