package com.bussiness.slodoggiesapp.model

data class Review(
    val profileImage: String,
    val userName: String,
    val rating: Int,
    val timeAgo: String,
    val text: String
)
