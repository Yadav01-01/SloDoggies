package com.bussiness.slodoggiesapp.data.newModel.businessprofile

import com.bussiness.slodoggiesapp.data.newModel.baseresponse.BaseResponse

data class BusinessProfileModel(
    val code: Int?=null,
    val `data`: Data?=null,
    override val message: String?=null,
    override val success: Boolean?=false,
    val isLoading: Boolean = false,
    val error: String? = null
): BaseResponse