package com.bussiness.slodoggiesapp.model.businessProvider

import com.bussiness.slodoggiesapp.model.common.MediaItem

data class SocialPost(
    val userImageRes: Int,
    val userName: String,
    val daysAgo: String,
    val title: String,
    val subtitle: String,
    val postImageRes: Int,
    val mediaList: List<MediaItem>,
    val likes: Int,
    val comments: Int,
    val shares: Int
)

