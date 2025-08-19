package com.bussiness.slodoggiesapp.model.common



data class PostData(
    val user: String,
    val role: String,
    val time: String,
    val caption: String,
    val description: String,
    val likes: Int,
    val comments: Int,
    val shares: Int,
    val mediaList: List<MediaItem>,

    )

data class MediaItem(
    val resourceId: Int,
    val type: MediaType,
    val videoResourceId: Int? = null,
    val thumbnailResourceId: Int? = null
)

enum class MediaType {
    IMAGE, VIDEO
}
