package com.bussiness.slodoggiesapp.model.common

import android.net.Uri

data class ChatMessage(
    val text: String,
    val isUser: Boolean,
    val attachmentUri: Uri? = null,   // file URI
    val attachmentType: String? = null
)

