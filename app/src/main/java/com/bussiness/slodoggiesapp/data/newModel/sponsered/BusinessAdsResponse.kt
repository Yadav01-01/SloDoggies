package com.bussiness.slodoggiesapp.data.newModel.sponsered

import com.bussiness.slodoggiesapp.data.newModel.baseresponse.BaseResponse
import com.google.gson.annotations.SerializedName

data class BusinessAdsResponse(
    @SerializedName("success")
    override val success: Boolean = false,

    @SerializedName("code")
    val code: Int = 0,

    @SerializedName("message")
    override val message: String = "",

    @SerializedName("data")
    val data: BusinessAdsData = BusinessAdsData()
): BaseResponse

data class BusinessAdsData(
    @SerializedName("approved")
    val approved: List<BusinessAdItem> = emptyList(),

    @SerializedName("pending")
    val pending: List<BusinessAdItem> = emptyList(),

    @SerializedName("active")
    val active: List<BusinessAdItem> = emptyList(),

    @SerializedName("expired")
    val expired: List<BusinessAdItem> = emptyList()
)

data class BusinessAdItem(
    @SerializedName("id")
    val id: Int = 0,

    @SerializedName("title")
    val title: String = "",

    @SerializedName("description")
    val description: String = "No engagement data available",

    @SerializedName("category")
    val category: List<String> = emptyList(),

    @SerializedName("service")
    val service: String = "",

    @SerializedName("expiry_date")
    val expiryDate: String = "",

    @SerializedName("expiry_time")
    val expiryTime: String = "",

    @SerializedName("terms_conditions")
    val termsConditions: String = "",

    @SerializedName("service_location")
    val serviceLocation: String = "",

    @SerializedName("latitude")
    val latitude: String = "",

    @SerializedName("longitude")
    val longitude: String = "",

    @SerializedName("contact_number")
    val contactNumber: String = "",

    @SerializedName("budget")
    val budget: String = "",

    @SerializedName("mobile_visual")
    val mobileVisual: String = "",

    @SerializedName("status")
    val status: String = "",

    @SerializedName("clicks")
    val clicks: Int = 0,

    @SerializedName("likes_count")
    val likesCount: Int = 0,

    val buttonText: String = "Pay Now",

    @SerializedName("media")
    val media: List<AdMediaItem> = emptyList()
)

data class AdMediaItem(
    @SerializedName("id")
    val id: Int = 0,

    @SerializedName("media_path")
    val mediaPath: String = ""
)
