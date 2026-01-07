package com.bussiness.slodoggiesapp.data.newModel.home

object HomeFeedMapper {

    fun map(items: List<HomeFeedItemResponse>?): List<PostItem> {
        if (items.isNullOrEmpty()) return emptyList()
        return items.mapNotNull { mapItem(it) }
    }

    private fun mapItem(item: HomeFeedItemResponse?): PostItem? {
        if (item == null) return null

        val author = item.author
        val content = item.content
        val engagement = item.engagement
        val itemSuccess = item.itemSuccess

        val mediaList = item.postMedia ?: emptyList()
        val media = item.media

        val likes = toIntSafe(engagement?.likes)
        val comments = toIntSafe(engagement?.comments)
        val shares = toIntSafe(engagement?.shares)

        return when (item.type?.lowercase()) {

            "normal" -> PostItem.NormalPost(
                postId = item.postId.orEmpty(),
                userId = author?.userId.orEmpty(),
                userName = author?.name.orEmpty(),
                petName = author?.petName.orEmpty(),
                userBadge = author?.badge,
                userType = item.userType.toString(),
                userPost = item.userPost?:false,
                time = author?.time.orEmpty(),
                caption = content?.title.orEmpty(),
                description = content?.description.orEmpty(),
                hashtags = content?.hashtags,
                media = media,                      // MediaResponse?
                mediaList = mediaList,              // List<PostMediaResponse>
                type = "normal",
                likes = likes,
                comments = comments,
                shares = shares,
                isLiked = itemSuccess?.isLiked ?: false,
                isSaved = itemSuccess?.isSave ?: false,
                iAmFollowing = itemSuccess?.iAmFollowing ?: false,
                authorType = author?.author_type
            )

            "community" -> PostItem.CommunityPost(
                postId = item.postId.orEmpty(),
                userId = author?.userId.orEmpty(),
                userName = author?.name.orEmpty(),
                userImage = media?.parentImageUrl,
                userType = item.userType.toString(),
                userPost = item.userPost?:false,
                time = author?.time.orEmpty(),
                eventTitle = content?.title.orEmpty(),
                eventStartDate = content?.startTime,
                eventEndDate = content?.endTime,
                eventDescription = content?.description,
                location = content?.location,
                alreadyJoined = content?.alreadyJoined,
                media = media,
                mediaList = mediaList,
                likes = likes,
                comments = comments,
                shares = shares,
                isLiked = itemSuccess?.isLiked ?: false,
                isSaved = itemSuccess?.isSave ?: false,
                iAmFollowing = itemSuccess?.iAmFollowing ?: false
            )

            "sponsored" -> PostItem.SponsoredPost(
                postId = item.postId.orEmpty(),
                userId = author?.userId.orEmpty(),
                userName = author?.name.orEmpty(),
                userPost = item.userPost?:false,
                userBadge = author?.badge,
                time = author?.time.orEmpty(),
                caption = content?.title.orEmpty(),
                description = content?.description.orEmpty(),
                media = media,
                mediaList = mediaList,
                likes = likes,
                comments = comments,
                shares = shares,
                isLiked = itemSuccess?.isLiked ?: false,
                isSaved = itemSuccess?.isSave ?: false
            )

            else -> null
        }
    }

    // Convert likes/comments/shares safely (backend: any type)
    private fun toIntSafe(value: Any?): Int {
        return when (value) {
            is Int -> value
            is String -> value.toIntOrNull() ?: 0
            is Double -> value.toInt()
            else -> 0
        }
    }
}
