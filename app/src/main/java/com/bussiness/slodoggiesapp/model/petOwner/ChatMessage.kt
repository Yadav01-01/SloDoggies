package com.bussiness.slodoggiesapp.model.petOwner

data class ChatMessage(
    val id: String,
    val text: String,
    val isFromUser: Boolean,
    val avatarEmoji: Int? = null
)
