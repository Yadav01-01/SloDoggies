package com.bussiness.slodoggiesapp.data.model.businessProvider

import com.bussiness.slodoggiesapp.data.newModel.baseresponse.BaseResponse
import com.google.gson.annotations.SerializedName

data class DeleteResponse(
    @SerializedName("code")
    val code: Int,

    @SerializedName("success")
    override val success: Boolean,

    @SerializedName("message")
    override val message: String
) : BaseResponse
