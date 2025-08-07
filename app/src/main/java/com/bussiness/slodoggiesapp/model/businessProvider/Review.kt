package com.bussiness.slodoggiesapp.model.businessProvider

data class Review(
    val profileImage: String,
    val userName: String,
    val rating: Int,
    val timeAgo: String,
    val text: String
)
