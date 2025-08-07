package com.bussiness.slodoggiesapp.ui.screens.petowner.settingScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.model.petOwner.MediaItem
//import com.bussiness.slodoggiesapp.ui.component.petOwner.CommonTopAppBar

private val photos = listOf(
    R.drawable.dummy_person_image3,  // Woman with dog on beach
    R.drawable.dummy_person_image3, // Man feeding dog on beach
    R.drawable.dummy_person_image3, // Person petting dog on beach
    R.drawable.dummy_person_image3, // Boy with dog in water
    R.drawable.dummy_person_image3, // Dog jumping on beach
    R.drawable.dummy_person_image3, // Black dog with people in background
    R.drawable.dummy_person_image3, // Boy with dog in water (duplicate)
    R.drawable.dummy_person_image3,
    R.drawable.dummy_person_image3, // Dog jumping on beach
    R.drawable.dummy_person_image3, // Black dog with people in background
    R.drawable.dummy_person_image3, // Boy with dog in water (duplicate)
    R.drawable.dummy_person_image3,
    R.drawable.dummy_person_image3, // Dog jumping on beach
    R.drawable.dummy_person_image3, // Black dog with people in background
    R.drawable.dummy_person_image3, // Boy with dog in water (duplicate)
    R.drawable.dummy_person_image3,
    R.drawable.dummy_person_image3, // Dog jumping on beach
    R.drawable.dummy_person_image3, // Black dog with people in background
    R.drawable.dummy_person_image3, // Boy with dog in water (duplicate)
    R.drawable.dummy_person_image3  // Black dog with people (duplicate)
)
private val mediaItems = listOf(
    MediaItem(R.drawable.dummy_person_image3, isVideo = false),
    MediaItem(R.drawable.dummy_person_image3, isVideo = false),
    MediaItem(R.raw.dummy_video_thumbnail, isVideo = true), // A video
    MediaItem(R.drawable.dummy_person_image3, isVideo = false),
    MediaItem(R.raw.dummy_video_thumbnail, isVideo = true), // A video
    MediaItem(R.drawable.dummy_person_image3, isVideo = false),
    MediaItem(R.drawable.dummy_person_image3, isVideo = false),
    MediaItem(R.raw.dummy_video_thumbnail, isVideo = true), // A video
    MediaItem(R.drawable.dummy_person_image3, isVideo = false),
    MediaItem(R.raw.dummy_video_thumbnail, isVideo = true), // A video
    MediaItem(R.drawable.dummy_person_image3, isVideo = false),
    MediaItem(R.drawable.dummy_person_image3, isVideo = false),
    MediaItem(R.raw.dummy_video_thumbnail, isVideo = true), // A video
    MediaItem(R.drawable.dummy_person_image3, isVideo = false),
    MediaItem(R.raw.dummy_video_thumbnail, isVideo = true), // A video
    MediaItem(R.drawable.dummy_person_image3, isVideo = false)

)

@Composable
fun PetSavedScreen(navController: NavController = rememberNavController()){
    Column(modifier = Modifier.fillMaxSize().background(Color.White)) {

//        CommonTopAppBar(
//            title = "Saved",
//            titleFontSize = 19.sp,
//            onBackClick = { navController.popBackStack() },
//            dividerColor = Color(0xFF258694),
//        )
        Spacer(Modifier.height(20.dp))
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 15.dp)
        ) {
            BeachPhotoGrid()

        }

    }


}

@Composable
fun BeachPhotoGrid(
    onMediaClick: (MediaItem) -> Unit = {}
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(mediaItems) { item ->
            Box(
                modifier = Modifier
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(12.dp))
                    .clickable { onMediaClick(item) }
            ) {
                AsyncImage(
                    model = item.resourceId,
                    contentDescription = "Media thumbnail",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                if (item.isVideo) {
                    // Overlay play icon
                    Icon(
                        painter = painterResource(id = R.drawable.ic_play_circle), // ⬅️ your play icon
                        contentDescription = "Play",
                        tint = Color.White,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(36.dp)
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PetSavedScreenPreview() {
    MaterialTheme {
        PetSavedScreen()
    }
}