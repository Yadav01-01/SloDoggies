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
import com.bussiness.slodoggiesapp.model.EventPost
import com.bussiness.slodoggiesapp.model.PetPlaceItem
import com.bussiness.slodoggiesapp.model.SearchResult
import com.bussiness.slodoggiesapp.model.SocialPost
import com.bussiness.slodoggiesapp.navigation.Routes
import com.bussiness.slodoggiesapp.ui.component.businessProvider.ActivityPostCard
import com.bussiness.slodoggiesapp.ui.component.businessProvider.FilterChipBox
import com.bussiness.slodoggiesapp.ui.component.businessProvider.HashtagChip
import com.bussiness.slodoggiesapp.ui.component.businessProvider.PetPlaceCard
import com.bussiness.slodoggiesapp.ui.component.businessProvider.SearchBar
import com.bussiness.slodoggiesapp.ui.component.businessProvider.SearchResultItem
import com.bussiness.slodoggiesapp.ui.component.businessProvider.SocialEventCard
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor
import com.bussiness.slodoggiesapp.ui.theme.TextGrey
import com.bussiness.slodoggiesapp.viewModel.businessProvider.DiscoverViewModel

@Composable
fun DiscoverScreen(navController: NavHostController) {
    val viewModel : DiscoverViewModel = hiltViewModel()
    val query by viewModel.query.collectAsState()
    val selectedCategory by viewModel.category.collectAsState()
    val categories = listOf("Pets Near You", "Events", "Pet Places", "Activities")

    val searchResults = remember {
        mutableStateListOf(
            SearchResult("Justin Bator", "Pet Dad", R.drawable.sample_user),
            SearchResult("Luna Smith", "Pet Groomer", R.drawable.sample_user),
            SearchResult("Buddy John", "Trainer", R.drawable.sample_user)
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 12.dp)
    ) {
        // Search Bar
        SearchBar(query = query, onQueryChange = { viewModel.updateQuery(it) }, placeholder = "Search")

        Spacer(modifier = Modifier.height(12.dp))

        // Hashtag Chips
        val hashtags = listOf("#DogYoga", "#PupWalk2025", "#VetPicnic", "#PetGala", "#rain")
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(hashtags) { tag ->
                HashtagChip(tag)
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Category Filters
        LazyRow(
            modifier = Modifier,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(categories) { category ->
                val isSelected = selectedCategory == category

                Box(modifier = Modifier
                    .clickable { viewModel.selectCategory(category)}
                ) {
                    FilterChipBox(
                        text = category,
                        borderColor = if (isSelected) PrimaryColor else TextGrey,
                        backgroundColor = if (isSelected) PrimaryColor else Color.White,
                        textColor = if (isSelected) Color.White else TextGrey
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(15.dp))

        when (selectedCategory) {
            "Pets Near You" -> ShowPetsNearYou(searchResults, navController)
            "Pet Places" -> PetPlacesResults()
            "Activities" -> ActivityCard()
            "Events" -> EventsResult()
            else -> ShowGeneralResults(searchResults, navController)
        }
    }
}


@Composable
fun ShowPetsNearYou(results: List<SearchResult>, controller: NavHostController) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        items(results) { result ->
            SearchResultItem(
                name = result.name,
                label = result.label,
                imageRes = result.imageRes,
                onItemClick = { controller.navigate(Routes.PERSON_DETAIL_SCREEN) },
                onRemove = {},
                labelVisibility = false
            )
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
                labelVisibility = true
            )
        }
    }
}
@Composable
fun PetPlacesResults() {
    val petDummyPlace = remember {
        listOf(
            PetPlaceItem(R.drawable.place_ic, "Highway 1 Road Trip", "San Luis Obispo", "10 km"),
            PetPlaceItem(R.drawable.place_ic, "Avila Beach", "San Luis Obispo", "10 km"),
            PetPlaceItem(R.drawable.place_ic, "El Chorro Dog Park", "San Luis Obispo", "30 km"),
            PetPlaceItem(R.drawable.place_ic, "Springdale Pet Ranch", "San Luis Obispo", "40 km"),
            PetPlaceItem(R.drawable.place_ic, "El Chorro Dog Park", "San Luis Obispo", "30 km"),
            PetPlaceItem(R.drawable.place_ic, "Springdale Pet Ranch", "San Luis Obispo", "40 km"),
            PetPlaceItem(R.drawable.place_ic, "El Chorro Dog Park", "San Luis Obispo", "30 km"),
            PetPlaceItem(R.drawable.place_ic, "Springdale Pet Ranch", "San Luis Obispo", "40 km"),
        )
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize(),

        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(petDummyPlace.size) { index ->
            PetPlaceCard(placeItem = petDummyPlace[index])
        }
    }
}

@Composable
fun ActivityCard() {
    val samplePosts = remember {
        listOf(
            SocialPost(R.drawable.user_ic, "Alena Geidt", "5 Days", "Dog Beach Adventures", "Avila Beach – Fun day with your pet at the coast!", R.drawable.dog3),
            SocialPost(R.drawable.user_ic, "Alena Geidt", "2 Days", "Pet-Friendly Hiking", "Cerro San Luis Trail - Hike with your furry friend.", R.drawable.dog3),
            SocialPost(R.drawable.user_ic, "Alena Geidt", "5 Days","Dog Beach Adventures", "Avila Beach – Fun day with your pet at the coast!", R.drawable.dog3),
            SocialPost(R.drawable.user_ic, "Alena", "8 Days", "Dog Beach Adventures", "Cerro San Luis Trail - Hike with your furry friend.", R.drawable.dog3)
        )
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(samplePosts) { post ->
            ActivityPostCard(post = post)
        }
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





@Preview(showBackground = true)
@Composable
fun DiscoverScreenPreview() {
    val navController = rememberNavController()

    DiscoverScreen(navController = navController)
}