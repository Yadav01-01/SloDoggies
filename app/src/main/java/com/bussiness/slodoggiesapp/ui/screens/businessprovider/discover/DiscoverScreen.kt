package com.bussiness.slodoggiesapp.ui.screens.businessprovider.discover

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.model.businessProvider.EventPost
import com.bussiness.slodoggiesapp.model.petOwner.PetPlaceItem
import com.bussiness.slodoggiesapp.model.businessProvider.SearchResult
import com.bussiness.slodoggiesapp.model.businessProvider.SocialPost
import com.bussiness.slodoggiesapp.navigation.Routes
import com.bussiness.slodoggiesapp.ui.component.businessProvider.ActivityPostCard
import com.bussiness.slodoggiesapp.ui.component.businessProvider.CategorySection
import com.bussiness.slodoggiesapp.ui.component.businessProvider.FilterChipBox
import com.bussiness.slodoggiesapp.ui.component.businessProvider.HashtagChip
import com.bussiness.slodoggiesapp.ui.component.businessProvider.HashtagSection
import com.bussiness.slodoggiesapp.ui.component.businessProvider.PetPlaceCard
import com.bussiness.slodoggiesapp.ui.component.businessProvider.SearchBar
import com.bussiness.slodoggiesapp.ui.component.businessProvider.SearchResultItem
import com.bussiness.slodoggiesapp.ui.component.businessProvider.SocialEventCard
import com.bussiness.slodoggiesapp.ui.component.petOwner.Dialog.WelcomeDialog
import com.bussiness.slodoggiesapp.ui.dialog.PetPlaceDialog
import com.bussiness.slodoggiesapp.ui.screens.businessprovider.discover.content.ActivityCard
import com.bussiness.slodoggiesapp.ui.screens.businessprovider.discover.content.PetPlacesResults
import com.bussiness.slodoggiesapp.ui.screens.businessprovider.discover.content.ShowPetsNearYou
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor
import com.bussiness.slodoggiesapp.ui.theme.TextGrey
import com.bussiness.slodoggiesapp.viewModel.businessProvider.DiscoverViewModel

@Composable
fun DiscoverScreen(navController: NavHostController, viewModel: DiscoverViewModel = hiltViewModel()) {
    val query by viewModel.query.collectAsState()
    val selectedCategory by viewModel.category.collectAsState()
    val petPlaceDialog by viewModel.petPlaceDialog.collectAsState()
    val searchResults by viewModel.searchResults.collectAsState()
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

        Spacer(modifier = Modifier.height(15.dp))
        
        when (selectedCategory) {
            "Pets Near You" -> ShowPetsNearYou(searchResults, navController)
            "Pet Places" -> PetPlacesResults( onItemClick = { viewModel.showPetPlaceDialog()})
            "Activities" -> ActivityCard()
            "Events" -> EventsResult()
            else -> ShowGeneralResults(searchResults, navController)
        }
    }

    if (petPlaceDialog) {
        PetPlaceDialog(
            onDismiss = { viewModel.dismissPetPlaceDialog() }
        )
    }
}


@Composable
fun EventsResult() {

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
            eventDate = "May 25, 4:00 PM",
            onClickLike = {},
            onClickComment = {},
            onClickShare = {}
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
            eventDate = "May 25, 4:00 PM",
            onClickLike = {},
            onClickComment = {},
            onClickShare = {}
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
            eventDate = "May 25, 4:00 PM",
            onClickLike = {},
            onClickComment = {},
            onClickShare = {}
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
            eventDate = "May 25, 4:00 PM",
            onClickLike = {},
            onClickComment = {},
            onClickShare = {}
        ),

        )

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(sampleEvents) { event ->
            SocialEventCard( event = event )
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

@Preview(showBackground = true)
@Composable
fun DiscoverScreenPreview() {
    val navController = rememberNavController()

    DiscoverScreen(navController = navController)
}