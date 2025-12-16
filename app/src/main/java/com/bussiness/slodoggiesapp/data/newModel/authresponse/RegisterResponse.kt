package com.bussiness.slodoggiesapp.data.newModel.authresponse

import com.bussiness.slodoggiesapp.data.newModel.baseresponse.BaseResponse
import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    @SerializedName("success")
    override  val success: Boolean,

    @SerializedName("code")
    val code: Int,

    @SerializedName("message")
    override  val message: String,

    @SerializedName("data")
    val data: RegisterData?
) : BaseResponse

data class RegisterData(
    @SerializedName("user")
    val user: RegisteredUser?,

    @SerializedName("token")
    val token: String?
)

data class RegisteredUser(
    @SerializedName("id")
    val id: Int?,

    @SerializedName("name")
    val name: String?,

    @SerializedName("email")
    val email: String?,

    @SerializedName("phone")
    val phone: String?,

    @SerializedName("device_type")
    val deviceType: String?,

    @SerializedName("fcm_token")
    val fcmToken: String?,

    @SerializedName("user_type")
    val userType: String?,

    @SerializedName("created_at")
    val createdAt: String?,

    @SerializedName("updated_at")
    val updatedAt: String?
)
