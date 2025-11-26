package com.bussiness.slodoggiesapp.data.newModel

import com.google.gson.annotations.SerializedName

/**
 * Server response wrapper for followers API.
 */
data class FollowersResponse(

    @SerializedName("success")
    override val success: Boolean,

    @SerializedName("code")
    val code: Int,

    @SerializedName("message")
    override val message: String,

    @SerializedName("data")
    val data: FollowersData? = null
) : BaseResponse

/**
 * Paginated follower data.
 */
data class FollowersData(

    @SerializedName("page")
    val page: String? = null,

    @SerializedName("limit")
    val limit: String? = null,

    @SerializedName("total_followers")
    val totalFollowers: Int? = null,

    @SerializedName("total_following")
    val totalFollowing: Int? = null,

    @SerializedName("total_page")
    val totalPage: Int? = null,

    @SerializedName("data")
    val followers: List<FollowerItem> = emptyList()
)

/**
 * Single follower record.
 */
data class FollowerItem(

    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("profile_pic")
    val profilePic: String? = null,

    @SerializedName("is_following")
    val isFollowing: Boolean? = null,

    @SerializedName("is_verified")
    val isVerified: Boolean? = null
)

