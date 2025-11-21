package com.bussiness.slodoggiesapp.ui.screens.commonscreens.profilepost

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.data.model.common.MediaItem
import com.bussiness.slodoggiesapp.data.model.common.MediaType
import com.bussiness.slodoggiesapp.data.model.common.PostItem
import com.bussiness.slodoggiesapp.navigation.Routes
import com.bussiness.slodoggiesapp.ui.component.businessProvider.HeadingTextWithIcon
import com.bussiness.slodoggiesapp.ui.component.common.NoDataView
import com.bussiness.slodoggiesapp.ui.dialog.DeleteChatDialog
import com.bussiness.slodoggiesapp.ui.screens.commonscreens.home.content.NormalPostItem
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor
import com.bussiness.slodoggiesapp.viewModel.common.userPost.UserPostViewModel

@Composable
fun UserPost(navController: NavHostController) {
    val viewModel: UserPostViewModel = hiltViewModel()
    var deleteDialog by remember { mutableStateOf(false) }
    var isNavigating by remember { mutableStateOf(false) }
    val state by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(state.message) {
        state.message?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }


    BackHandler {
        if (!isNavigating) {
            isNavigating = true
            navController.popBackStack()
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        HeadingTextWithIcon(
            textHeading = stringResource(R.string.posts),
            onBackClick = {  if (!isNavigating) {
                isNavigating = true
                navController.popBackStack()
            } }
        )

        HorizontalDivider(thickness = 2.dp, color = PrimaryColor)

        when{
            state.showNoData -> {
                NoDataView("Oops ,No posts available at the moment.")
            }else -> {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFCEE1E6)),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(getSamplePosts()) { post ->
                    if (post is PostItem.NormalPost) {
                        NormalPostItem(
                            postItem = post,
                            modifier = Modifier,
                            onReportClick = { /* TODO */ },
                            onShareClick = { /* TODO */ },
                            normalPost = false,
                            onEditClick = { navController.navigate(Routes.EDIT_POST_SCREEN) },
                            onDeleteClick = { deleteDialog = true },
                            onProfileClick = {  },
                            onSelfPostEdit = {  },
                            onSelfPostDelete = {  }
                        )
                    }
                }
            }
            }
        }
    }
    if (deleteDialog) {
        DeleteChatDialog(
            onDismiss = { deleteDialog = false },
            onClickRemove = { deleteDialog = false  },
            iconResId = R.drawable.delete_mi,
            text = stringResource(R.string.Delete_Post),
            description = stringResource(R.string.Delete_desc)
        )
    }
}

//  Sample data (can move to Preview-only file later)
fun getSamplePosts(): List<PostItem> = listOf(
    PostItem.NormalPost(
        user = "Lydia Vaccaro with Wixx",
        role = "Pet Mom",
        time = "5 Min.",
        caption = "🐾 Meet Wixx - our brown bundle of joy!",
        description = "\uD83D\uDC3E Meet From tail wags to beach days, life with this 3-year-old",
        likes = 120,
        comments = 20,
        shares = 10,
        postType = "other",
        mediaList = listOf(
            MediaItem(
                MediaType.IMAGE,
                imageRes = R.drawable.dog1
            ),
            MediaItem(
                MediaType.IMAGE,
                imageUrl = "https://picsum.photos/400"
            ),
            MediaItem(
                MediaType.VIDEO,
//                videoRes = R.raw.reel,
                thumbnailRes = R.drawable.dummy_social_media_post
            )
        ),
    ),
    PostItem.NormalPost(
        user = "John Doe with Max",
        role = "Pet Dad",
        time = "15 Min.",
        caption = "Enjoying the sunny day!",
        description = "Max loves playing in the park with his friends",
        likes = 85,
        comments = 12,
        shares = 5,
        postType = "other",
        mediaList = listOf(
            MediaItem(
                MediaType.IMAGE,
                imageRes = R.drawable.dog1
            ),
            MediaItem(
                MediaType.IMAGE,
                imageUrl = "https://picsum.photos/400"
            ),
        ),
    )
)
