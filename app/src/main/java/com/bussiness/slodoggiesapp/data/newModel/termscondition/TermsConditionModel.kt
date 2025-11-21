package com.bussiness.slodoggiesapp.data.newModel.termscondition

import com.bussiness.slodoggiesapp.data.newModel.BaseResponse

data class TermsConditionModel(
    val code: Int?=null,
    val `data`: Data?=null,
    override val message: String?=null,
    override val success: Boolean?=false,
    val isLoading: Boolean = false,
    val error: String? = null
): BaseResponse