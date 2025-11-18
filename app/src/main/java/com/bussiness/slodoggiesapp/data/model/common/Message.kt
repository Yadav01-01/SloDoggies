package com.bussiness.slodoggiesapp.data.model.common

data class Message(
    val profileImageUrl: String,
    val username: String,
    val time: String,
    val description: String,
    val count: String? = null,
)

