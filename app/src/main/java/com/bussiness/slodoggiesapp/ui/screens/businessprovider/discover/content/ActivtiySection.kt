package com.bussiness.slodoggiesapp.ui.screens.businessprovider.discover.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.model.businessProvider.SocialPost
import com.bussiness.slodoggiesapp.model.common.MediaItem
import com.bussiness.slodoggiesapp.model.common.MediaType
import com.bussiness.slodoggiesapp.ui.component.businessProvider.ActivityPostCard

@Composable
fun ActivityCard(onShareClick: () -> Unit) {
    val samplePosts = remember {
        listOf(
            SocialPost(R.drawable.user_ic, "Alena Geidt", "5 Days", "Dog Beach Adventures", "Avila Beach – Fun day with your pet at the coast!", R.drawable.dog3, likes = 120,
                comments = 20,
                shares = 10,
                mediaList = listOf(
                    MediaItem(R.drawable.dummy_person_image3, MediaType.IMAGE),
                    MediaItem(R.drawable.dummy_person_image2, MediaType.IMAGE),
                    MediaItem(R.drawable.dummy_person_image3, MediaType.VIDEO, R.raw.dummy_video_thumbnail)
                )),
            SocialPost(R.drawable.user_ic, "Alena Geidt", "2 Days", "Pet-Friendly Hiking", "Cerro San Luis Trail - Hike with your furry friend.", R.drawable.dog3,likes = 120,
                comments = 20,
                shares = 10,
                mediaList = listOf(
                    MediaItem(R.drawable.dummy_person_image3, MediaType.IMAGE),
                    MediaItem(R.drawable.dummy_person_image2, MediaType.IMAGE),
                    MediaItem(R.drawable.dummy_person_image3, MediaType.VIDEO, R.raw.dummy_video_thumbnail)
                )),
            SocialPost(R.drawable.user_ic, "Alena Geidt", "5 Days","Dog Beach Adventures", "Avila Beach – Fun day with your pet at the coast!", R.drawable.dog3,likes = 120,
                comments = 20,
                shares = 10,
                mediaList = listOf(
                    MediaItem(R.drawable.dummy_person_image3, MediaType.IMAGE),
                    MediaItem(R.drawable.dummy_person_image2, MediaType.IMAGE),
                    MediaItem(R.drawable.dummy_person_image3, MediaType.VIDEO, R.raw.dummy_video_thumbnail)
                )),
            SocialPost(R.drawable.user_ic, "Alena", "8 Days", "Dog Beach Adventures", "Cerro San Luis Trail - Hike with your furry friend.", R.drawable.dog3,likes = 120,
                comments = 20,
                shares = 10,
                mediaList = listOf(
                    MediaItem(R.drawable.dummy_person_image3, MediaType.IMAGE),
                    MediaItem(R.drawable.dummy_person_image2, MediaType.IMAGE),
                    MediaItem(R.drawable.dummy_person_image3, MediaType.VIDEO, R.raw.dummy_video_thumbnail)
                ))
        )
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(samplePosts) { post ->
            ActivityPostCard(post = post, onShareClick = { onShareClick()})
        }
    }
}