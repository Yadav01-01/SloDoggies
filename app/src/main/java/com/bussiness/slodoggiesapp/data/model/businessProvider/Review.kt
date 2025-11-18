package com.bussiness.slodoggiesapp.data.model.businessProvider

data class Review(
    val name: String,
    val rating: Int,
    val timeAgo: String,
    val review: String,
    val avatar: Int,
    var reply: com.bussiness.slodoggiesapp.data.model.businessProvider.ReviewReply? = null
)

data class ReviewReply(
    val providerName: String,
    val providerImage: String,
    val providerRole: String,
    val pTimeAgo: String,
    val pMessage: String
)
