package com.bussiness.slodoggiesapp.data.model.common

import com.bussiness.slodoggiesapp.data.newModel.baseresponse.BaseResponse

data class JoinCommunityResponse(
    override val success: Boolean,
    val code: Int,
    override val message: String,
    val data: TotalMembersData
) : BaseResponse


data class TotalMembersData(
    var total_members: Int,
)
