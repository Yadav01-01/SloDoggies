package com.bussiness.slodoggiesapp.ui.screens.commonscreens.discover

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.data.newModel.home.PostItem
import com.bussiness.slodoggiesapp.navigation.Routes
import com.bussiness.slodoggiesapp.ui.component.businessProvider.CategorySection
import com.bussiness.slodoggiesapp.ui.component.businessProvider.HashtagSection
import com.bussiness.slodoggiesapp.ui.component.businessProvider.SearchBar
import com.bussiness.slodoggiesapp.ui.component.businessProvider.SocialEventCard
import com.bussiness.slodoggiesapp.ui.component.petOwner.dialog.ReportDialog
import com.bussiness.slodoggiesapp.ui.dialog.PetPlaceDialog
import com.bussiness.slodoggiesapp.ui.dialog.ReportBottomToast
import com.bussiness.slodoggiesapp.ui.dialog.SavedDialog
import com.bussiness.slodoggiesapp.ui.screens.commonscreens.discover.content.ActivitiesPostsList
import com.bussiness.slodoggiesapp.ui.screens.commonscreens.discover.content.PetPlacesResults
import com.bussiness.slodoggiesapp.ui.screens.commonscreens.discover.content.ShowPetsNearYou
import com.bussiness.slodoggiesapp.ui.screens.commonscreens.home.content.ShareContentDialog
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor
import com.bussiness.slodoggiesapp.viewModel.businessProvider.DiscoverViewModel

@Composable
fun DiscoverScreen(navController: NavHostController, viewModel: DiscoverViewModel = hiltViewModel()) {

    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    var message by remember { mutableStateOf("") }
    var selectedReason by remember { mutableStateOf("") }

    LaunchedEffect(uiState.error) {
        val msg = uiState.error
        if (!msg.isNullOrBlank()) {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        }
    }

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
        SearchBar(query = uiState.query, onQueryChange = { viewModel.updateQuery(it) },placeholder = "Search")

        Spacer(modifier = Modifier.height(12.dp))
        // Hashtags Row
        HashtagSection(uiState.hashtags)

        Spacer(modifier = Modifier.height(12.dp))
        // Categories
        CategorySection(categories = uiState.categories, selectedCategory = uiState.selectedCategory, onCategorySelected = { viewModel.selectCategory(it) })

        Spacer(modifier = Modifier.height(10.dp))
        
        when (uiState.selectedCategory) {
            "Pets Near You" -> ShowPetsNearYou(uiState.pets, navController)
            "Pet Places"    -> PetPlacesResults( uiState.petPlaces,
                onItemClick = { viewModel.showPetPlaceDialog(true)}
            )
            "Activities"    -> ActivitiesPostsList(
                posts = uiState.posts,
                onReportClick = { viewModel.showReportDialog(true) },
                onShareClick = { viewModel.showShareContent(true) },
                onProfileClick = { navController.navigate(Routes.PERSON_DETAIL_SCREEN)}
            )
            "Events"        -> EventsResult(
                posts = uiState.posts,
                onClickMore = { viewModel.showReportDialog(true) },
                onShareClick = { viewModel.showShareContent(true) },
                onLikeClick = { postId -> viewModel.postLikeUnlike(postId) },
                onJoinClick = { navController.navigate(Routes.COMMUNITY_CHAT_SCREEN) },
                onProfileClick = { navController.navigate(Routes.PERSON_DETAIL_SCREEN) },
                onInterested = { postId -> viewModel.savePost(postId,
                    onSuccess = { viewModel.showSavedDialog(true) }
                ) },
                onClickFollowing = { postId -> viewModel.addAndRemoveFollowers(postId) }
            )
            else            -> ShowPetsNearYou(uiState.pets, navController)
        }
    }

    if (uiState.showPetPlaceDialog) {
        PetPlaceDialog(onDismiss = { viewModel.dismissPetPlaceDialog() })
    }
    if (uiState.showShareContentDialog) {
        ShareContentDialog( onDismiss = { viewModel.showShareContent(false) }, onSendClick = { viewModel.showShareContent(true) })
    }
    if (uiState.showSavedDialog){
        SavedDialog(
            icon = R.drawable.icon_park_outline_success,
            title = stringResource(R.string.Event_Saved),
            description = stringResource(R.string.saved_description),
            onDismiss = { viewModel.dismissSavedDialog() }
        )
    }
    if (uiState.showReportDialog) {
        ReportDialog(
            onDismiss = { viewModel.showReportDialog(false) },
            onCancel = { viewModel.showReportDialog(false) },
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
    if (uiState.showReportDialog){
        ReportBottomToast(
            onDismiss = {viewModel.dismissReportToast()}
        )
    }
}


@Composable
fun EventsResult(
    posts: List<PostItem>,
    onClickMore: () -> Unit,
    onJoinClick: () -> Unit,
    onShareClick: () -> Unit,
    onLikeClick: (String) -> Unit,
    onProfileClick: () -> Unit,
    onInterested: (String) -> Unit,
    onClickFollowing: (String) -> Unit,
) {
    if (posts.isEmpty()){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    painter = painterResource(R.drawable.ic_pet_post_icon),
                    contentDescription = null,
                    tint = Color.Gray,
                    modifier = Modifier.size(100.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "OOps! No Event found",
                    style = MaterialTheme.typography.titleMedium,
                    color = PrimaryColor,
                    fontFamily = FontFamily(Font(R.font.outfit_medium)),
                    fontSize = 18.sp
                )
            }
        }
    }else{
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(posts) { event ->
                // Check each item
                if (event is PostItem.CommunityPost) {

                    SocialEventCard(
                        postItem = event,
                        onReportClick = { onClickMore() },
                        onShareClick = { onShareClick() },
                        onLikeClick = { onLikeClick(event.postId) },
                        onJoinedCommunity = { onJoinClick() },
                        onProfileClick = { onProfileClick() },
                        onInterested = { onInterested(event.postId) },
                        onClickFollowing = { onClickFollowing(event.postId) },
                        isFollowing = event.iAmFollowing
                    )
                }
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun DiscoverScreenPreview() {
    val navController = rememberNavController()
    DiscoverScreen(navController = navController)
}