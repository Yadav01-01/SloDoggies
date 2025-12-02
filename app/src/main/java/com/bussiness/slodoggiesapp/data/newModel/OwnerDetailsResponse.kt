package com.bussiness.slodoggiesapp.data.newModel

import com.google.gson.annotations.SerializedName

data class OwnerDetailsResponse(
    @SerializedName("success")
    override val success: Boolean,

    @SerializedName("code")
    val code: Int,

    @SerializedName("message")
    override val message: String,

    @SerializedName("data")
    val data: OwnerData?
) : BaseResponse

data class OwnerData(
    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("user_type")
    val userType: String? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("email")
    val email: String? = null,

    @SerializedName("phone")
    val phone: String? = null,

    @SerializedName("forgot_otp")
    val forgotOtp: String? = null,

    @SerializedName("email_verified_at")
    val emailVerifiedAt: String? = null,

    @SerializedName("device_type")
    val deviceType: String? = null,

    @SerializedName("fcm_token")
    val fcmToken: String? = null,

    @SerializedName("user_status")
    val userStatus: Int? = null,

    @SerializedName("created_at")
    val createdAt: String? = null,

    @SerializedName("updated_at")
    val updatedAt: String? = null,

    @SerializedName("deleted_at")
    val deletedAt: String? = null,

    @SerializedName("bio")
    val bio: String? = null,

    @SerializedName("image")
    val image: String? = null,

    @SerializedName("parent_type")
    val parent_type: String? = null
)
