package com.bussiness.slodoggiesapp.data.newModel.businessdetails

import com.bussiness.slodoggiesapp.data.newModel.BaseResponse

data class BusinessDetailsModel(
    val code: Int?=null,
    override val message: String?=null,
    val `data`: Data?=null,
    override val success: Boolean?=false,
    val isLoading: Boolean = false,
    val error: String? = null
): BaseResponse