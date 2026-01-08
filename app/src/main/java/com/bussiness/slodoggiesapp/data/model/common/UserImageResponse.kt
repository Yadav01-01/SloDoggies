package com.bussiness.slodoggiesapp.data.model.common

import com.bussiness.slodoggiesapp.data.newModel.baseresponse.BaseResponse

data class UserImageResponse(
    override val success: Boolean,
    val code: Int,
    override val message: String,
    val data: String? // nullable in case data is missing
) : BaseResponse