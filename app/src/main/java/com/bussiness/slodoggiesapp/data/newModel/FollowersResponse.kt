package com.bussiness.slodoggiesapp.data.newModel

import com.google.gson.annotations.SerializedName

data class FollowersResponse(
    @SerializedName("success")
    override val success: Boolean,

    @SerializedName("code")
    val code: Int,

    @SerializedName("message")
    override val message: String,

    @SerializedName("data")
    val data: List<Follow> = emptyList()
) : BaseResponse


data class Follow(

    @SerializedName("id")
    val id: Int = 0,

    @SerializedName("name")
    val name: String = "",

    @SerializedName("profile_pic")
    val profilePic: String = "",

    // Followers API only
    @SerializedName("is_following")
    val isFollowing: Boolean = false,

    // Followers API only (not in your sample — keep as safe default)
    @SerializedName("is_verified")
    val isVerified: Boolean = false,

)
