package com.bussiness.slodoggiesapp.data.model.common

import android.net.Uri

data class ChatMessage(
    val text: String="",
    val isUser: Boolean= false,
    val attachmentUri: Uri? = null,
    val attachmentType: String? = null,
    val senderId: String = "",
    val receiverId: String = "",
    val message: String = "",
    val imageUrl: String? = null,
    val timestamp: Long = System.currentTimeMillis(),
    var date:String? = null,
    var time:String? = null,
    var seen :Boolean= false,
    var senderImage:String =""
)

