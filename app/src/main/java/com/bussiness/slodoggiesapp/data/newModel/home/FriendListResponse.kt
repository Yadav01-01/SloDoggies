package com.bussiness.slodoggiesapp.data.newModel.home

import com.bussiness.slodoggiesapp.data.newModel.baseresponse.BaseResponse
import com.google.gson.annotations.SerializedName

data class FriendListResponse(
    @SerializedName("success")
    override val success: Boolean = false,

    @SerializedName("code")
    val code: Int = 0,

    @SerializedName("message")
    override val message: String = "",

    @SerializedName("data")
    val data: List<FriendItem> = emptyList()
) : BaseResponse

data class FriendItem(
    @SerializedName("user_type")
    val userType: String = "",

    @SerializedName("user_id")
    val userId: Int = 0,

    @SerializedName("image")
    val image: String? = null,

    @SerializedName("name")
    val name: String = ""
)

