package com.bussiness.slodoggiesapp.ui.screens.commonscreens.settings

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.data.model.businessProvider.GalleryItem
import com.bussiness.slodoggiesapp.data.newModel.home.PostItem
import com.bussiness.slodoggiesapp.data.newModel.ownerProfile.MediaItem
import com.bussiness.slodoggiesapp.data.newModel.ownerProfile.OwnerPostItem
import com.bussiness.slodoggiesapp.navigation.Routes
import com.bussiness.slodoggiesapp.ui.component.businessProvider.GalleryItemCard
import com.bussiness.slodoggiesapp.ui.component.businessProvider.GalleryItemCardProfile
import com.bussiness.slodoggiesapp.ui.component.businessProvider.HeadingTextWithIcon
import com.bussiness.slodoggiesapp.ui.screens.commonscreens.home.content.NormalPostItem
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor
import com.bussiness.slodoggiesapp.viewModel.common.HomeViewModel
import com.bussiness.slodoggiesapp.viewModel.servicebusiness.BusinessServicesViewModel
import com.google.gson.Gson

@Composable
fun SavedItemScreen(navController: NavHostController) {
    val viewModel: HomeViewModel = hiltViewModel()
    val state by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val posts = state.posts
    val dataList = listOf(
        com.bussiness.slodoggiesapp.data.model.businessProvider.GalleryItem(R.drawable.dog1),
        com.bussiness.slodoggiesapp.data.model.businessProvider.GalleryItem(
            R.drawable.dog2,
            isVideo = true
        ),
        com.bussiness.slodoggiesapp.data.model.businessProvider.GalleryItem(R.drawable.dog1),
        com.bussiness.slodoggiesapp.data.model.businessProvider.GalleryItem(R.drawable.dog2),
        com.bussiness.slodoggiesapp.data.model.businessProvider.GalleryItem(R.drawable.dog1),
        com.bussiness.slodoggiesapp.data.model.businessProvider.GalleryItem(R.drawable.dog2)
    )
    var isNavigating by remember { mutableStateOf(false) }


    // First API Call
    LaunchedEffect(Unit) {
        viewModel.loadSaveNextPage()
    }

    BackHandler {
        if (!isNavigating) {
            isNavigating = true
            navController.popBackStack()
        }
    }

    Column (modifier = Modifier.fillMaxSize().background(Color.White) ){
        HeadingTextWithIcon(textHeading = "Saved Post", onBackClick = {  if (!isNavigating) {
            isNavigating = true
            navController.popBackStack()
        } })

        HorizontalDivider(thickness = 2.dp, color = PrimaryColor)

        Spacer(Modifier.height(10.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier.fillMaxWidth().padding(horizontal = 5.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
//            items(dataList.size) { index ->
//                GalleryItemCard(item = dataList[index], height =125 ,
//                    onClickItem = {
//                       // navController.navigate(Routes.USER_POST_SCREEN)
//                        Log.d("********","Run")
//                        navController.navigate(Routes.USER_POST_SCREEN + "/${1}")
//                    }
//                )
//            }
            itemsIndexed(
                items = posts,
                key = { _, post -> post.stableKey }
            ) { index, post ->

                if (index == posts.lastIndex - 2) {
                    viewModel.loadPostNextPage()
                }

                when (post) {
                    is PostItem.NormalPost ->{
                        val item = posts[index]
                        val ownerItem = item.toOwner()
                        GalleryItemCardProfile(
                            item = ownerItem,
                            onClickItem = {
                                navController.navigate(Routes.USER_POST_SCREEN + "/${ownerItem.id}/SavePost")
                            }
                        )
                    }
                    is PostItem.CommunityPost -> {
                        // TODO: handle or skip
                    }

                    is PostItem.SponsoredPost -> {
                        // TODO: handle or skip
                    }
                }
            }
        }

    }
}
fun  PostItem.toOwner(): OwnerPostItem {
    return when (this) {
        is PostItem.NormalPost -> {
            OwnerPostItem(
                id = this.postId.toIntOrNull(),
                userId = this.userId.toIntOrNull(),
                postTitle = this.caption,
                postType = this.type,
                createdAt = this.time,
                mediaPath = this.mediaList.map {
                    MediaItem(
                        url = it.mediaUrl,
                        type = it.type,
                        thumbnailUrl = it.thumbnailUrl
                    )
                }
            )
        }
        else -> {
            // fallback for other Post types
            OwnerPostItem(
                id = null,
                userId = null,
                postTitle = "",
                postType = "",
                address = "",
                createdAt = "",
                mediaPath = emptyList()
            )
        }
    }
}


@Preview
@Composable
fun SavedItemPreview(){
    val navController = rememberNavController()
    SavedItemScreen(navController)
}