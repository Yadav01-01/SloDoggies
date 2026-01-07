package com.bussiness.slodoggiesapp.data.model.businessProvider

import com.google.gson.annotations.SerializedName

data class BusinessData(
    @SerializedName("businessName")
    val businessName: String = "",

    @SerializedName("user_type")
    val userType: String = "",

    @SerializedName("profileImage")
    val profileImage: String = "",

    @SerializedName("rating")
    val rating: Int = 0,

    @SerializedName("milesAway")
    val milesAway: String = "",

    @SerializedName("businessDescription")
    val businessDescription: String = "",

    @SerializedName("category")
    val category: List<String> = emptyList(),

    @SerializedName("phone")
    val phone: String = "",

    @SerializedName("provider_name")
    val providerName: String = "",

    @SerializedName("website")
    val website: String = "",

    @SerializedName("address")
    val address: String = "",

    @SerializedName("verificationstatus")
    val verificationStatus: Boolean = false,

    @SerializedName("services")
    val services: List<Service> = emptyList(),

    @SerializedName("ratingsAndReviews")
    val ratingsAndReviews: RatingsAndReviews? = null
)
