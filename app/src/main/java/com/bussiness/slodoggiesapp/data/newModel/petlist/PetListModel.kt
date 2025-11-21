package com.bussiness.slodoggiesapp.data.newModel.petlist

import com.bussiness.slodoggiesapp.data.newModel.BaseResponse

data class PetListModel(
    val code: Int?=null,
    val `data`: MutableList<Data>?=null,
    override val message: String?=null,
    override val success: Boolean?=false,
    val isLoading: Boolean = false,
    val error: String? = null
) : BaseResponse