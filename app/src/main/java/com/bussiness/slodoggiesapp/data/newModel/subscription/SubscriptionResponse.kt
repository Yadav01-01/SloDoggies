package com.bussiness.slodoggiesapp.data.newModel.subscription

import com.bussiness.slodoggiesapp.data.newModel.baseresponse.BaseResponse
import com.google.gson.annotations.SerializedName

data class SubscriptionResponse(
    @SerializedName("success")
    override val success: Boolean = false,

    @SerializedName("code")
    val code: Int = 0,

    @SerializedName("message")
    override val message: String = "",

    @SerializedName("data")
    val data: List<SubscriptionPlan> = emptyList()
) : BaseResponse



data class SubscriptionPlan(
    @SerializedName("id")
    val id: Int = 0,

    @SerializedName("type")
    val type: String = "",

    @SerializedName("price")
    val price: String = "",

    @SerializedName("description")
    val description: String = "",

    @SerializedName("texts")
    val texts: List<String> = emptyList(),
)

