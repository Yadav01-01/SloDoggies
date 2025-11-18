package com.bussiness.slodoggiesapp.data.model.businessProvider

import com.bussiness.slodoggiesapp.data.model.common.MediaItem

data class SocialPost(
    val userImageRes: Int,
    val userName: String,
    val daysAgo: String,
    val title: String,
    val subtitle: String,
    val postImageRes: Int,
    val mediaList: List<com.bussiness.slodoggiesapp.data.model.common.MediaItem>,
    val likes: Int,
    val comments: Int,
    val shares: Int
)

