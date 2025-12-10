package com.bussiness.slodoggiesapp.data.newModel.discover

import com.bussiness.slodoggiesapp.data.newModel.BaseResponse

data class TrendingHashtagsResponse(
    override val success: Boolean,
    val code: Int,
    override val message: String,
    val data: List<HashtagItem>
) : BaseResponse

data class HashtagItem(
    val hashtag: String,
    val count: Int
)
