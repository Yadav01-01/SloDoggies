package com.bussiness.slodoggiesapp.data.newModel.ownerService

import com.bussiness.slodoggiesapp.data.newModel.baseresponse.BaseResponse
import com.google.gson.annotations.SerializedName


data class CategoryResponse(
    @SerializedName("success")
    override val success: Boolean? = false,

    @SerializedName("code")
    val code: Int? = 0,

    @SerializedName("message")
    override val message: String? = "",

    @SerializedName("data")
    val data: List<CategoryItem> = emptyList()
) : BaseResponse

data class CategoryItem(

    @SerializedName("categoryId")
    val categoryId: String? = "",

    @SerializedName("categoryName")
    val categoryName: String? = ""
)

