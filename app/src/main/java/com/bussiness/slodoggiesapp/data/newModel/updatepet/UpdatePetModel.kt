package com.bussiness.slodoggiesapp.data.newModel.updatepet

import com.bussiness.slodoggiesapp.data.newModel.BaseResponse

data class UpdatePetModel(
    val code: Int,
    val `data`: Data,
    override val message: String,
    override val success: Boolean,
): BaseResponse