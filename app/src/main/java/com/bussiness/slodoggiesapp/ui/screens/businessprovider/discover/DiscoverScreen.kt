package com.bussiness.slodoggiesapp.ui.screens.businessprovider.discover

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
import com.bussiness.slodoggiesapp.model.businessProvider.EventPost
import com.bussiness.slodoggiesapp.model.businessProvider.SearchResult
import com.bussiness.slodoggiesapp.model.common.MediaItem
import com.bussiness.slodoggiesapp.model.common.MediaType
import com.bussiness.slodoggiesapp.model.common.PostItem
import com.bussiness.slodoggiesapp.navigation.Routes
import com.bussiness.slodoggiesapp.ui.component.businessProvider.CategorySection
import com.bussiness.slodoggiesapp.ui.component.businessProvider.HashtagSection
import com.bussiness.slodoggiesapp.ui.component.businessProvider.SearchBar
import com.bussiness.slodoggiesapp.ui.component.businessProvider.SearchResultItem
import com.bussiness.slodoggiesapp.ui.component.businessProvider.SocialEventCard
import com.bussiness.slodoggiesapp.ui.component.petOwner.Dialog.ReportDialog
import com.bussiness.slodoggiesapp.ui.dialog.PetPlaceDialog
import com.bussiness.slodoggiesapp.ui.dialog.ReportBottomToast
import com.bussiness.slodoggiesapp.ui.dialog.SavedDialog
import com.bussiness.slodoggiesapp.ui.screens.businessprovider.discover.content.ActivitiesPostsList
import com.bussiness.slodoggiesapp.ui.screens.businessprovider.discover.content.PetPlacesResults
import com.bussiness.slodoggiesapp.ui.screens.businessprovider.discover.content.ShowPetsNearYou
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 12.dp)
    ) {
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
            "Activities"    -> { ActivitiesPostsList(posts = getSamplePosts(), onReportClick = { viewModel.showReportDialog() }, onShareClick = { viewModel.showShareContent() }) }
            "Events"        -> EventsResult(onClickMore = { }, onShareClick = { viewModel.showShareContent() }, onSavedClick = { viewModel.showSavedDialog() })
            else            -> ShowGeneralResults(searchResults, navController)
        }
    }

    if (petPlaceDialog) {
        PetPlaceDialog(
            onDismiss = { viewModel.dismissPetPlaceDialog() }
        )
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
fun EventsResult(onClickMore: () -> Unit,onShareClick: () -> Unit,onSavedClick: () -> Unit) {

    val sampleEvents = listOf(
        EventPost(
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
        EventPost(
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
        EventPost(
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
        EventPost(
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
            SocialEventCard( event = event , onReportClick = { onClickMore() }, onShareClick = { onShareClick() }, onSaveClick = { onSavedClick() })
        }
    }
}


@Composable
fun ShowGeneralResults(results: List<SearchResult>, controller: NavHostController) {
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
                MediaItem(R.drawable.dummy_person_image3, MediaType.VIDEO, )
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
                MediaItem(R.drawable.dummy_person_image2, MediaType.IMAGE),
                MediaItem(R.drawable.dummy_person_image3, MediaType.IMAGE)
            )
        )
    )
}


@Preview(showBackground = true)
@Composable
fun DiscoverScreenPreview() {
    val navController = rememberNavController()

    DiscoverScreen(navController = navController)
}