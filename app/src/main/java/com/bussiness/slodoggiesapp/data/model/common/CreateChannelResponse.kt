package com.bussiness.slodoggiesapp.data.model.common

import com.bussiness.slodoggiesapp.data.newModel.baseresponse.BaseResponse

data class CreateChannelResponse(
    override val success: Boolean,
    val code: Int,
    override val message: String,
    val data: ChannelData
) : BaseResponse
data class ChannelData(
    val sender_id: Int,
    val receiver_id: Int?,      // nullable because API returns null
    val event_id: String?,
    val chat_id: String,
    val chat_type: String,
    val updated_at: String,
    val created_at: String,
    val id: Int
)