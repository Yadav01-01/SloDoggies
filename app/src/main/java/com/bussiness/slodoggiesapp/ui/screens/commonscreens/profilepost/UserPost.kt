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
import com.bussiness.slodoggiesapp.data.newModel.home.PostItem
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

        /*when{
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
                            onReportClick = { *//* TODO *//* },
                            onShareClick = { *//* TODO *//* },
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
        }*/
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
/*fun getSamplePosts(): List<PostItem> {
    return listOf(
        PostItem.NormalPost(
            id = 1,
            userId = 10,
            postTitle = "🐾 Meet Wixx - our brown bundle of joy!",
            address = "Mumbai",
            postType = "other",
            time = "5 Min.",
            likeCount = 120,
            commentCount = 20,
            shareCount = 10,
            hashtags = listOf("#dog", "#petlover"),
            userDetail = UserDetail(
                name = "Lydia Vaccaro",
                image = "lydia_vaccaro",
                id = 1
            ),
            petDetail = PetDetail(
                petName = "Wixx",
                petImage = "Mixed",
                id = 2
            ),
            postMedia = listOf(
                MediaItem(
                    id = 1,
                    mediaPath = "R.drawable.dog1"
                ),
                MediaItem(
                    id = 2,
                    mediaPath = "R.drawable.dog1"
                ),
                MediaItem(
                    id = 3,
                    mediaPath = "R.drawable.dog1"
                )
            )
        ),

        PostItem.NormalPost(
            id = 2,
            userId = 11,
            postTitle = "Enjoying the sunny day!",
            address = "Delhi",
            postType = "other",
            time = "15 Min.",
            likeCount = 85,
            commentCount = 12,
            shareCount = 5,
            hashtags = listOf("#sunnyday", "#doglife"),
            userDetail = UserDetail(
                name = "Lydia Vaccaro",
                image = "lydia_vaccaro",
                id = 1
            ),
            petDetail = PetDetail(
                petName = "Wixx",
                petImage = "Mixed",
                id = 2
            ),
            postMedia = listOf(
                MediaItem(
                    id = 1,
                    mediaPath = "R.drawable.dog1"
                ),
                MediaItem(
                    id = 2,
                    mediaPath = "R.drawable.dog1"
                ),
                MediaItem(
                    id = 3,
                    mediaPath = "R.drawable.dog1"
                )
            )
        )
    )
}*/

