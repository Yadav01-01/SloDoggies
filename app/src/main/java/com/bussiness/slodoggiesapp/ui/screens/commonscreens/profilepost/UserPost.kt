package com.bussiness.slodoggiesapp.ui.screens.commonscreens.profilepost

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.model.common.MediaItem
import com.bussiness.slodoggiesapp.model.common.MediaType
import com.bussiness.slodoggiesapp.model.common.PostItem
import com.bussiness.slodoggiesapp.navigation.Routes
import com.bussiness.slodoggiesapp.ui.component.businessProvider.HeadingTextWithIcon
import com.bussiness.slodoggiesapp.ui.dialog.DeleteChatDialog
import com.bussiness.slodoggiesapp.ui.screens.commonscreens.home.content.NormalPostItem
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor

@Composable
fun UserPost(navController: NavHostController) {
    var deleteDialog by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        HeadingTextWithIcon(
            textHeading = stringResource(R.string.posts),
            onBackClick = { navController.popBackStack() }
        )

        HorizontalDivider(thickness = 2.dp, color = PrimaryColor)

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
                        onDeleteClick = { deleteDialog = true }
                    )
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
        mediaList = listOf(
            MediaItem(R.drawable.new_dog_ic, MediaType.IMAGE),
            MediaItem(R.drawable.dog3, MediaType.IMAGE),
            MediaItem(R.drawable.new_dog_ic, MediaType.VIDEO,)
        )
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
        mediaList = listOf(
            MediaItem(R.drawable.dog1, MediaType.IMAGE),
            MediaItem(R.drawable.new_dog_ic, MediaType.IMAGE)
        )
    )
)
