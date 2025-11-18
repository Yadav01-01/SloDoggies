package com.bussiness.slodoggiesapp.ui.screens.commonscreens.discover

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.data.model.businessProvider.EventPost
import com.bussiness.slodoggiesapp.data.model.businessProvider.SearchResult
import com.bussiness.slodoggiesapp.data.model.common.MediaItem
import com.bussiness.slodoggiesapp.data.model.common.MediaType
import com.bussiness.slodoggiesapp.data.model.common.PostItem
import com.bussiness.slodoggiesapp.navigation.Routes
import com.bussiness.slodoggiesapp.ui.component.businessProvider.CategorySection
import com.bussiness.slodoggiesapp.ui.component.businessProvider.HashtagSection
import com.bussiness.slodoggiesapp.ui.component.businessProvider.SearchBar
import com.bussiness.slodoggiesapp.ui.component.businessProvider.SearchResultItem
import com.bussiness.slodoggiesapp.ui.component.businessProvider.SocialEventCard
import com.bussiness.slodoggiesapp.ui.component.petOwner.dialog.ReportDialog
import com.bussiness.slodoggiesapp.ui.dialog.PetPlaceDialog
import com.bussiness.slodoggiesapp.ui.dialog.ReportBottomToast
import com.bussiness.slodoggiesapp.ui.dialog.SavedDialog
import com.bussiness.slodoggiesapp.ui.screens.commonscreens.discover.content.ActivitiesPostsList
import com.bussiness.slodoggiesapp.ui.screens.commonscreens.discover.content.PetPlacesResults
import com.bussiness.slodoggiesapp.ui.screens.commonscreens.discover.content.ShowPetsNearYou
import com.bussiness.slodoggiesapp.ui.screens.commonscreens.home.content.ShareContentDialog
import com.bussiness.slodoggiesapp.viewModel.businessProvider.DiscoverViewModel

@Composable
fun DiscoverScreen(navController: NavHostController, viewModel: DiscoverViewModel = hiltViewModel()) {
    val query by viewModel.query.collectAsState()
    val selectedCategory by viewModel.category.collectAsState()
    val petPlaceDialog by viewModel.petPlaceDialog.collectAsState()
    val searchResults by viewModel.searchResults.collectAsState()
    val showSavedDialog by viewModel.showSavedDialog.collectAsState()
    val shareContentDialog by viewModel.showShareContent.collectAsState()
    val showReportDialog by viewModel.showReportDialog.collectAsState()
    val showReportToast by viewModel.showReportToast.collectAsState()
    var message by remember { mutableStateOf("") }
    var selectedReason by remember { mutableStateOf("") }
    val categories = listOf("Pets Near You", "Events", "Pet Places", "Activities")

    BackHandler {
        if (!navController.popBackStack(Routes.HOME_SCREEN, false)) {
            navController.navigate(Routes.HOME_SCREEN) {
                launchSingleTop = true
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 12.dp)
    ) {
        Spacer(Modifier.height(8.dp))
        //  Search Bar
        SearchBar(query = query, onQueryChange = { viewModel.updateQuery(it) },placeholder = "Search")

        Spacer(modifier = Modifier.height(12.dp))

        // Hashtags Row
        HashtagSection()

        Spacer(modifier = Modifier.height(12.dp))
        // Categories
        CategorySection(categories = categories, selectedCategory = selectedCategory, onCategorySelected = { viewModel.selectCategory(it) })

        Spacer(modifier = Modifier.height(10.dp))
        
        when (selectedCategory) {
            "Pets Near You" -> ShowPetsNearYou(searchResults, navController)
            "Pet Places"    -> PetPlacesResults( onItemClick = { viewModel.showPetPlaceDialog()})
            "Activities"    -> { ActivitiesPostsList(posts = getSamplePosts(), onReportClick = { viewModel.showReportDialog() }, onShareClick = { viewModel.showShareContent() }, onProfileClick = { navController.navigate(Routes.PERSON_DETAIL_SCREEN)}) }
            "Events"        -> EventsResult(onClickMore = { viewModel.showReportDialog() }, onShareClick = { viewModel.showShareContent() }, onSavedClick = { viewModel.showSavedDialog() }, onJoinClick = { navController.navigate(Routes.COMMUNITY_CHAT_SCREEN) }, onProfileClick = { navController.navigate(Routes.PERSON_DETAIL_SCREEN)})
            else            -> ShowGeneralResults(searchResults, navController)
        }
    }

    if (petPlaceDialog) {
        PetPlaceDialog(onDismiss = { viewModel.dismissPetPlaceDialog() })
    }
    if (shareContentDialog) {
        ShareContentDialog( onDismiss = { viewModel.dismissShareContent() }, onSendClick = { viewModel.dismissShareContent() })
    }
    if (showSavedDialog){
        SavedDialog(
            icon = R.drawable.icon_park_outline_success,
            title = stringResource(R.string.Event_Saved),
            description = stringResource(R.string.saved_description),
            onDismiss = { viewModel.dismissSavedDialog() }
        )
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
}


@Composable
fun EventsResult(onClickMore: () -> Unit,onJoinClick : () -> Unit,onShareClick: () -> Unit,onSavedClick: () -> Unit,onProfileClick: () -> Unit) {

    val sampleEvents = listOf(
        com.bussiness.slodoggiesapp.data.model.businessProvider.EventPost(
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
            eventEndDate = "June 15, 5:00 PM",
            onClickLike = {},
            onClickComment = {},
            onClickShare = {},
            likes = 120,
            comments = 20,
            shares = 10
        ),
        com.bussiness.slodoggiesapp.data.model.businessProvider.EventPost(
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
            eventEndDate = "June 15, 5:00 PM",
            onClickLike = {},
            onClickComment = {},
            onClickShare = {},
            likes = 120,
            comments = 20,
            shares = 10
        ),
        com.bussiness.slodoggiesapp.data.model.businessProvider.EventPost(
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
            eventEndDate = "June 15, 5:00 PM",
            onClickLike = {},
            onClickComment = {},
            onClickShare = {},
            likes = 120,
            comments = 20,
            shares = 10
        ),
        com.bussiness.slodoggiesapp.data.model.businessProvider.EventPost(
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
            eventEndDate = "June 15, 5:00 PM",
            onClickLike = {},
            onClickComment = {},
            onClickShare = {},
            likes = 120,
            comments = 20,
            shares = 10
        ),

        )

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(sampleEvents) { event ->
            SocialEventCard( event = event , onReportClick = { onClickMore() },
                onShareClick = { onShareClick() }, onSaveClick = { onSavedClick() },
                onJoinCommunity = { onJoinClick()}, onProfileClick = { onProfileClick() })
        }
    }
}

@Composable
fun ShowGeneralResults(results: List<com.bussiness.slodoggiesapp.data.model.businessProvider.SearchResult>, controller: NavHostController) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        items(results) { result ->
            SearchResultItem(
                name = result.name,
                label = result.label,
                imageRes = result.imageRes,
                onItemClick = { controller.navigate(Routes.PERSON_DETAIL_SCREEN)},
                onRemove = {},
                labelVisibility = true,
                crossVisibility = true
            )
        }
    }
}

fun getSamplePosts(): List<com.bussiness.slodoggiesapp.data.model.common.PostItem> {
    return listOf(
        com.bussiness.slodoggiesapp.data.model.common.PostItem.NormalPost(
            user = "Lydia Vaccaro with Wixx",
            role = "Pet Mom",
            time = "5 Min.",
            caption = "🐾 Meet Wixx - our brown bundle of joy!",
            description = "From tail wags to beach days, life with this 3-year-old",
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
//                MediaItem(
//                    MediaType.VIDEO,
//                    videoRes = R.raw.reel,
//                    thumbnailRes = R.drawable.dummy_social_media_post
//                )
            ),
        ),
        com.bussiness.slodoggiesapp.data.model.common.PostItem.NormalPost(
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
}


@Preview(showBackground = true)
@Composable
fun DiscoverScreenPreview() {
    val navController = rememberNavController()
    DiscoverScreen(navController = navController)
}