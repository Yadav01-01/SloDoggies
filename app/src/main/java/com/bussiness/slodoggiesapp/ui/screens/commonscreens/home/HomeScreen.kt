package com.bussiness.slodoggiesapp.ui.screens.commonscreens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.model.common.PostItem
import com.bussiness.slodoggiesapp.navigation.Routes
import com.bussiness.slodoggiesapp.ui.component.common.HomeTopBar
import com.bussiness.slodoggiesapp.ui.component.petOwner.Dialog.PetInfoDialog
import com.bussiness.slodoggiesapp.ui.component.petOwner.Dialog.WelcomeDialog
import com.bussiness.slodoggiesapp.ui.component.petOwner.Dialog.UserDetailsDialog
import com.bussiness.slodoggiesapp.ui.screens.commonscreens.home.content.CommunityPostItem
import com.bussiness.slodoggiesapp.ui.screens.commonscreens.home.content.NormalPostItem
import com.bussiness.slodoggiesapp.ui.screens.commonscreens.home.content.SponsoredPostItem
import com.bussiness.slodoggiesapp.ui.screens.petowner.MediaItem
import com.bussiness.slodoggiesapp.ui.screens.petowner.MediaType
import com.bussiness.slodoggiesapp.viewModel.common.HomeViewModel

@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val posts = getSamplePosts()
//    val posts by viewModel.posts.collectAsState()
    val welcomeUiState by viewModel.welcomeUiState.collectAsState()
    val showPetInfoDialog by viewModel.showPetInfoDialog.collectAsState()
    val showUserDetailsDialog by viewModel.showUserDetailsDialog.collectAsState()
    val profileCreatedUiState by viewModel.profileCreatedUiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0XFFB9D4DB))
    ) {
        HomeTopBar(
            onNotificationClick = { navController.navigate(Routes.NOTIFICATION_SCREEN) },
            onMessageClick = { navController.navigate(Routes.MESSAGE_SCREEN) }
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 5.dp)
        ) {
            items(posts) { post ->
                when (post) {
                    is PostItem.CommunityPost -> CommunityPostItem(post)
                    is PostItem.SponsoredPost -> SponsoredPostItem(post)
                    is PostItem.NormalPost -> NormalPostItem(post)
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }

    // --- Dialogs ---
    if (welcomeUiState.showDialog) {
        WelcomeDialog(
            onDismiss = { viewModel.dismissWelcomeDialog() },
            onSubmitClick = { viewModel.onWelcomeSubmit() },
            title = welcomeUiState.title,
            description = welcomeUiState.description,
            button = welcomeUiState.button
        )
    }

    if (showPetInfoDialog) {
        PetInfoDialog(
            title = "Tell us about your pet!",
            onDismiss = { viewModel.dismissPetInfoDialog() },
            onSaveAndContinue = { petInfo ->
                // Save pet info to repository
                viewModel.dismissPetInfoDialog()
            }
        )
    }

    if (showUserDetailsDialog) {
        UserDetailsDialog(
            onDismiss = { viewModel.dismissUserDetailsDialog() },
            onSubmit = {
                viewModel.onUserDetailsSubmit()
            }
        )
    }

    if (profileCreatedUiState) {
        WelcomeDialog(
            onDismiss = { viewModel.dismissProfileCreatedDialog() },
            onSubmitClick = { viewModel.dismissProfileCreatedDialog() },
            title = stringResource(R.string.profileCreateTitle),
            description = stringResource(R.string.profileCreateDes),
            button = stringResource(R.string.explore_now),
        )
    }

}


@Composable
fun getSamplePosts(): List<PostItem> {
    return listOf(
        PostItem.NormalPost(
            user = "Lydia Vaccaro with Wixx",
            role = "Pet Mom",
            time = "5 Min.",
            caption = "🐾 Meet Wixx - our brown bundle of joy!",
            description = "From tail wags to beach days, life with this 3-year-old",
            likes = 120,
            comments = 20,
            shares = 10,
            mediaList = listOf(
                MediaItem(R.drawable.dummy_person_image3, MediaType.IMAGE),
                MediaItem(R.drawable.dummy_person_image2, MediaType.IMAGE),
                MediaItem(
                    R.drawable.dummy_person_image3,
                    MediaType.VIDEO,
                    R.raw.dummy_video_thumbnail
                )
            )
        ),
        PostItem.CommunityPost(
            userName = "Lydia Vaccaro with Wixx",
            userImage = R.drawable.user_ic,
            postImage = R.drawable.post_img,
            label = "Pet Mom",
            time = "5 Min.",
            eventTitle = "Event Title",
            eventDescription = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor",
            eventDuration = "30 Mins.",
            location = "San Luis Obispo County",
            onClickFollow = {},
            onClickMore = {},
            eventDate = "May 25, 4:00 PM",
            likes = 200,
            comments = 100,
            shares = 10
        ),
        PostItem.SponsoredPost(
            user = "Aisuke",
            role = "Pet Lover",
            time = "2 Days",
            caption = "Summer Special: 20% Off Grooming!",
            description = "Limited Time Offer",
            mediaList = listOf(
                MediaItem(R.drawable.dummy_baby_pic, MediaType.IMAGE)
            ),
            likes = 200,
            comments = 100,
            shares = 10
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
                MediaItem(R.drawable.dummy_person_image2, MediaType.IMAGE),
                MediaItem(R.drawable.dummy_person_image3, MediaType.IMAGE)
            )
        )
    )
}
