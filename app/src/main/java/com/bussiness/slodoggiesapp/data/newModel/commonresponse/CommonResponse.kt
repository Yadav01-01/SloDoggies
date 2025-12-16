package com.bussiness.slodoggiesapp.data.newModel.commonresponse

import com.bussiness.slodoggiesapp.data.newModel.baseresponse.BaseResponse
import com.google.gson.annotations.SerializedName

data class CommonResponse(

    @SerializedName("success")
    override val success: Boolean = false,

    @SerializedName("code")
    val code: Int? = null,

    @SerializedName("message")
    override  val message: String? = null
) : BaseResponse
