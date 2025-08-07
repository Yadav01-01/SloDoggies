package com.bussiness.slodoggiesapp.model.businessProvider

data class NotificationData(
    val profileImageUrl: String,
    val username: String,
    val message: String,
    val time: String,
    val previewImageUrl: String? = null
)
