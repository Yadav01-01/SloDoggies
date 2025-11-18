package com.bussiness.slodoggiesapp.data.model.businessProvider

data class NotificationData(
    val profileImageUrl: String? = null,
    val username: String,
    val message: String,
    val time: String,
    val previewImageUrl: String? = null,
    val type: String
)
