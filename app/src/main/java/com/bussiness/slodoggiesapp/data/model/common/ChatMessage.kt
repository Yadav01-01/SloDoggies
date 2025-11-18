package com.bussiness.slodoggiesapp.data.model.common

import android.net.Uri

data class ChatMessage(
    val text: String,
    val isUser: Boolean,
    val timestamp: Long,
    val attachmentUri: Uri? = null,   // file URI
    val attachmentType: String? = null
)

