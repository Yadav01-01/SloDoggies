package com.bussiness.slodoggiesapp.data.newModel.faq

import com.bussiness.slodoggiesapp.data.newModel.baseresponse.BaseResponse
import com.google.gson.annotations.SerializedName

data class FaqResponse(
    @SerializedName("success")
    override val success: Boolean = false,

    @SerializedName("code")
    val code: Int = 0,

    @SerializedName("message")
    override val message: String = "",

    @SerializedName("data")
    val data: List<FaqItem> = emptyList()
) : BaseResponse

data class FaqItem(
    @SerializedName("question")
    val question: String = "",

    @SerializedName("answer")
    val answer: String = ""
)

