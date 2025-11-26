package com.bussiness.slodoggiesapp.data.newModel

import com.google.gson.annotations.SerializedName

data class FollowingResponse(
    @SerializedName("success")
    override val success: Boolean,

    @SerializedName("code")
    val code: Int,

    @SerializedName("message")
    override val message: String?,

    @SerializedName("data")
    val data: FollowingData?
) : BaseResponse

data class FollowingData(
    @SerializedName("page")
    val page: Int?,

    @SerializedName("limit")
    val limit: Int?,

    @SerializedName("total_followers")
    val totalFollower: Int?,

    @SerializedName("total_following")
    val totalFollowing: Int?,

    @SerializedName("total_page")
    val totalPage: Int?,

    @SerializedName("data")
    val data: List<FollowingItem>?
)

data class FollowingItem(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String?,

    @SerializedName("profile_pic")
    val profilePic: String?,

    @SerializedName("is_following_me")
    val isFollowingMe: Boolean?,

    @SerializedName("is_verified")
    val isVerified: Boolean? = null
)

