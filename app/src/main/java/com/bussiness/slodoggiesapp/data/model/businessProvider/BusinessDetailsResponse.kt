package com.bussiness.slodoggiesapp.data.model.businessProvider

import com.google.gson.annotations.SerializedName

data class BusinessDetailsResponse(
    @SerializedName("data")
    val data: BusinessData? = null
)
