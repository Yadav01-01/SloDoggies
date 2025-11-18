package com.bussiness.slodoggiesapp.data.model.petOwner

data class ChatMessage(
    val id: String,
    val text: String,
    val isFromUser: Boolean,
    val avatarEmoji: Int? = null
)
