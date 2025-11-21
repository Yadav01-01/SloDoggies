package com.bussiness.slodoggiesapp.data.newModel

import com.google.gson.annotations.SerializedName

data class BusinessDetailsResponse(
    @SerializedName("success")
    override val success: Boolean,

    @SerializedName("code")
    val code: Int,

    @SerializedName("message")
    override val message: String,

    @SerializedName("data")
    val data: BusinessDetails? = null
) : BaseResponse

data class BusinessDetails(

    @SerializedName("id")
    val id: Int,

    @SerializedName("user_id")
    val userId: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("provider_name")
    val providerName: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("phone")
    val phone: String,

    @SerializedName("category")
    val category: String,

    @SerializedName("address")
    val address: String,

    @SerializedName("city")
    val city: String,

    @SerializedName("state")
    val state: String,

    @SerializedName("zip_code")
    val zipCode: String,

    @SerializedName("latitude")
    val latitude: String,

    @SerializedName("longitude")
    val longitude: String,

    @SerializedName("website_url")
    val websiteUrl: String,

    @SerializedName("business_logo")
    val businessLogo: String,

    @SerializedName("available_days")
    val availableDays: List<String> = emptyList(),

    @SerializedName("available_time")
    val availableTime: String,

    @SerializedName("verification_docs")
    val verificationDocs: List<String> = emptyList(),

    @SerializedName("user_status")
    val userStatus: Int,

    @SerializedName("created_at")
    val createdAt: String,

    @SerializedName("updated_at")
    val updatedAt: String,

    @SerializedName("deleted_at")
    val deletedAt: String? = null
)

