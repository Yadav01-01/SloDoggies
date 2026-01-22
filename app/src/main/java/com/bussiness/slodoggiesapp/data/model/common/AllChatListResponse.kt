package com.bussiness.slodoggiesapp.data.model.common

import com.bussiness.slodoggiesapp.data.newModel.baseresponse.BaseResponse

data class AllChatListResponse(
    override val success: Boolean,
    val code: Int,
    override val message: String,
    val data: List<AllChatListData>?
) : BaseResponse

data class AllChatListData(
    val chat_id: String? = "",
    val type: String? = "",
    val user_id: Int? = 0,
    val event_id: Int? = 0,
    val total_members: Int? = 0,
    val user_name: String? = "",
    val event_name: String? = "",
    val profile_image: String? = "",
    val event_image: String? = "",
    val description: String? = "", // Last message text
    val time: String? = "", // Formatted time
    // Add these fields for Firebase compatibility
    val last_message: String? = "",
    val last_message_time: Long? = 0L,
    val unread_count: Int? = 0,
    val sender_id: String? = ""

)