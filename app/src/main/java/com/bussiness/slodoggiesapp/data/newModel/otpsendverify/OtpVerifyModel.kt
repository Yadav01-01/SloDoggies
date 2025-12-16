package com.bussiness.slodoggiesapp.data.newModel.otpsendverify

import com.bussiness.slodoggiesapp.data.newModel.baseresponse.BaseResponse


data class OtpVerifyModel(
    val code: Int?=null,
    val `data`: Data?=null,
    override val message: String?=null,
    override val success: Boolean?=false,
    val isLoading: Boolean = false,
    val error: String? = null
): BaseResponse