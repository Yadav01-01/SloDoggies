package com.bussiness.slodoggiesapp.data.model.common



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


//data class MediaItem(
//    val type: com.bussiness.slodoggiesapp.data.model.common.MediaType,
//    val imageRes: Int? = null,
//    val videoRes: Int? = null,
//    val thumbnailRes: Int? = null,
//    val imageUrl: String? = null,
//    val videoUrl: String? = null,
//)

enum class MediaType {
    IMAGE, VIDEO
}
