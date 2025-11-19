package com.bussiness.slodoggiesapp.data.model.common

data class AudienceItem(
    val id: Int = 0,
    val name: String = "",
    val profilePic: String = "",
    val isVerified: Boolean = false,
    val isFollowing: Boolean = false,      // I follow them?
    val isFollowingMe: Boolean = false     // They follow me?
)

