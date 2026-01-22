package com.bussiness.slodoggiesapp.data.model.common

import com.google.gson.annotations.SerializedName

data class JoinCommunityRequest(
    @SerializedName("user_id") val userId: List<String>,
    @SerializedName("chat_id") val eventId: String
)
