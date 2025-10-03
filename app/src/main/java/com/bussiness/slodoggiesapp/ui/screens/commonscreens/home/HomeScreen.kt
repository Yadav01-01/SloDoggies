package com.bussiness.slodoggiesapp.ui.screens.commonscreens.home

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.bussiness.slodoggiesapp.model.common.MediaItem
import com.bussiness.slodoggiesapp.model.common.MediaType
import com.bussiness.slodoggiesapp.model.common.PostItem
import com.bussiness.slodoggiesapp.model.main.UserType
import com.bussiness.slodoggiesapp.navigation.Routes
import com.bussiness.slodoggiesapp.ui.component.common.HomeTopBar
import com.bussiness.slodoggiesapp.ui.component.petOwner.dialog.PetInfoDialog
import com.bussiness.slodoggiesapp.ui.component.petOwner.dialog.ReportDialog
import com.bussiness.slodoggiesapp.ui.component.petOwner.dialog.UserDetailsDialog
import com.bussiness.slodoggiesapp.ui.component.petOwner.dialog.WelcomeDialog
import com.bussiness.slodoggiesapp.ui.dialog.ReportBottomToast
import com.bussiness.slodoggiesapp.ui.screens.commonscreens.home.content.CommunityPostItem
import com.bussiness.slodoggiesapp.ui.screens.commonscreens.home.content.NormalPostItem
import com.bussiness.slodoggiesapp.ui.screens.commonscreens.home.content.ShareContentDialog
import com.bussiness.slodoggiesapp.ui.screens.commonscreens.home.content.SponsoredPostItem
import com.bussiness.slodoggiesapp.util.SessionManager
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
    val continueAddPet by viewModel.showContinueAddPetDialog.collectAsState()
    val showReportDialog by viewModel.showReportDialog.collectAsState()
    val showReportToast by viewModel.showReportToast.collectAsState()
    val dialogCount by viewModel.petInfoDialogCount.collectAsState()
    val shareContentDialog by viewModel.showShareContent.collectAsState()
    var message by remember { mutableStateOf("") }
    var selectedReason by remember { mutableStateOf("") }
    val context =  LocalContext.current
    val activity = context as? Activity
    val sessionManager = SessionManager.getInstance(context)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0XFFB9D4DB))
    ) {

        BackHandler {
            activity?.finish()  // closes the app
        }

        HomeTopBar(
            onNotificationClick = { if (sessionManager.getUserType() == UserType.BUSINESS_PROVIDER)navController.navigate(Routes.NOTIFICATION_SCREEN) else navController.navigate(Routes.PET_NOTIFICATION_SCREEN) },
            onMessageClick = { navController.navigate(Routes.MESSAGE_SCREEN) }
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 3.dp)
        ) {
            items(posts) { post ->
                when (post) {
                    is PostItem.CommunityPost -> CommunityPostItem(post, onReportClick = { viewModel.showReportDialog() }, onShareClick = { viewModel.showShareContent()}, onJoinedCommunity = { navController.navigate(Routes.COMMUNITY_CHAT_SCREEN) }, onProfileClick = { navController.navigate(Routes.PERSON_DETAIL_SCREEN) })
                    is PostItem.SponsoredPost -> SponsoredPostItem(post, onReportClick = { viewModel.showReportDialog() }, onShareClick = { viewModel.showShareContent()}, onProfileClick = { navController.navigate(Routes.CLICKED_PROFILE_SCREEN) })
                    is PostItem.NormalPost -> NormalPostItem(modifier = Modifier.padding(12.dp),post, onReportClick = { viewModel.showReportDialog() }, onShareClick = { viewModel.showShareContent() },normalPost = true, onEditClick = {}, onDeleteClick = {}, onProfileClick = { navController.navigate(Routes.PERSON_DETAIL_SCREEN) })
                }
            }
        }
    }

    // --- Dialogs ---
    if (welcomeUiState.showDialog) {
        WelcomeDialog(
            onDismiss = { viewModel.dismissWelcomeDialog() },
            onSubmitClick = { viewModel.onWelcomeSubmit() },
            icon = R.drawable.ic_party_popper_icon,
            title = welcomeUiState.title,
            description = welcomeUiState.description,
            button = welcomeUiState.button
        )
    }

    if (showUserDetailsDialog) {
        UserDetailsDialog(
            navController = navController,
            onDismiss = { viewModel.dismissUserDetailsDialog() },
            onSubmit = {
                viewModel.onUserDetailsSubmit()
            },
            onVerify = { data,type ->
                viewModel.onVerify(navController,data,type)
            }
        )
    }

    if (profileCreatedUiState) {
        WelcomeDialog(
            onDismiss = { viewModel.dismissProfileCreatedDialog() },
            onSubmitClick = { viewModel.dismissProfileCreatedDialog() },
            icon = R.drawable.ic_party_popper_icon,
            title = stringResource(R.string.profileCreateTitle),
            description = stringResource(R.string.profileCreateDes),
            button = stringResource(R.string.explore_now),
        )
    }

    if (continueAddPet) {
        WelcomeDialog(
            onDismiss = { viewModel.dismissContinueAddPetDialog() },
            onSubmitClick = { viewModel.dismissContinueAddPetDialog() },
            icon = R.drawable.paw_print,
            title = stringResource(R.string.add_pet),
            description = stringResource(R.string.paw_description),
            button = stringResource(R.string.paw_button),
        )
    }

    if (showPetInfoDialog) {
        if (dialogCount < 2) {
            PetInfoDialog(
                title = stringResource(R.string.tell_us_about_your_pet),
                onDismiss = { viewModel.dismissPetInfoDialog() },
                addPet = { petInfo ->
                    viewModel.incrementPetInfoDialogCount()
                    viewModel.showPetInfoDialog()
                },
                onContinueClick = {
                    viewModel.dismissPetInfoDialog()
                }
            )
        } else {
            LaunchedEffect(Unit) {
                viewModel.showContinueAddPetDialog()
            }
        }
    }

    if (showReportDialog) {
        ReportDialog(
            onDismiss = { viewModel.dismissReportDialog() },
            onCancel = { viewModel.dismissReportDialog() },
            onSendReport = { viewModel.showReportToast() },
            reasons = listOf("Bullying or unwanted contact",
                             "Violence, hate or exploitation",
                             "False Information",
                             "Scam, fraud or spam"),
            selectedReason = selectedReason,
            message = message,
            onReasonSelected = {  reason -> selectedReason = reason },
            onMessageChange = { message = it },
            title = "Report Post"
        )
    }

    if (showReportToast){
        ReportBottomToast(
            onDismiss = {viewModel.dismissReportToast()}
        )
    }

    if (shareContentDialog) {
        ShareContentDialog(onDismiss = { viewModel.dismissShareContent() }, onSendClick = { viewModel.dismissShareContent() })
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
                MediaItem(R.drawable.dummy_social_media_post, MediaType.IMAGE),
                MediaItem(R.drawable.dummy_person_image2, MediaType.IMAGE),
                MediaItem(R.drawable.dummy_social_media_post, MediaType.VIDEO,)
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
            eventStartDate = "May 25, 4:00 PM",
            eventEndDate = "June 16, 7:00 PM",
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
            description = "Max loves playing in the park with his friends Lorem ipsum dolor sit amet," +
                    " consectetur adipiscing Lorem ipsum dolor sit amet, " +
                    " consectetur adipiscing Lorem ipsum dolor sit amet, consectetur adipiscing",
            likes = 85,
            comments = 12,
            shares = 5,
            mediaList = listOf(
                MediaItem(R.drawable.dummy_social_media_post, MediaType.IMAGE),
                MediaItem(R.drawable.dummy_person_image3, MediaType.IMAGE)
            )
        )
    )
}
