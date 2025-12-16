package com.bussiness.slodoggiesapp.data.newModel.servicelist

import com.bussiness.slodoggiesapp.data.newModel.baseresponse.BaseResponse

data class ServicesListModel(
    val code: Int?=null,
    val `data`: MutableList<Data>?=null,
    override val message: String?=null,
    override val success: Boolean?=false,
    val isLoading: Boolean = false,
    val error: String? = null
) : BaseResponse