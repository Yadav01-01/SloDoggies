package com.bussiness.slodoggiesapp.data.newModel.ownerService

import com.bussiness.slodoggiesapp.data.newModel.BaseResponse
import com.google.gson.annotations.SerializedName


data class ServicesResponse(
    @SerializedName("success")
    override val success: Boolean = false,

    @SerializedName("code")
    val code: Int = 0,

    @SerializedName("message")
    override val message: String = "",

    @SerializedName("data")
    val data: List<ServiceItem> = emptyList()
) :  BaseResponse


data class ServiceItem(

    @SerializedName("serviceId")
    val serviceId: Int = 0,

    @SerializedName("serviceName")
    val serviceName: String = "Unknown",

    @SerializedName("isBusinessVerified")
    val isBusinessVerified: Boolean = false,

    @SerializedName("providerId")
    val providerId: Int = 0,

    @SerializedName("providerName")
    val providerName: String = "",

    @SerializedName("image")
    val image: String = "",

    @SerializedName("averageRating")
    val averageRating: String = "0.0",

    @SerializedName("location")
    val location: String = "",

    @SerializedName("categoryName")
    val categoryName: List<String> = emptyList()

)

