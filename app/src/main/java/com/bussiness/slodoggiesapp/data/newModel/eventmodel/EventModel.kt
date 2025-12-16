package com.bussiness.slodoggiesapp.data.newModel.eventmodel

import com.bussiness.slodoggiesapp.data.newModel.baseresponse.BaseResponse

data class EventModel(
    val `data`: Data?=null,
    val code: Int?=null,
    override val message: String?=null,
    override val success: Boolean?=false,
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val error: String? = null
): BaseResponse