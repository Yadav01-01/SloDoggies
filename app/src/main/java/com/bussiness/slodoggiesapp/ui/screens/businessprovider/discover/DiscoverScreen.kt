package com.bussiness.slodoggiesapp.ui.screens.businessprovider.discover

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.model.SearchResult
import com.bussiness.slodoggiesapp.navigation.Routes
import com.bussiness.slodoggiesapp.ui.component.businessProvider.HashtagChip
import com.bussiness.slodoggiesapp.ui.component.businessProvider.SearchBar
import com.bussiness.slodoggiesapp.ui.component.businessProvider.SearchResultItem
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor
import com.bussiness.slodoggiesapp.ui.theme.TextGrey

@Composable
fun DiscoverScreen(navController: NavHostController) {
    var query by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("Pets Near You") }

    val searchResults = remember {
        mutableStateListOf(
            SearchResult("Justin Bator", "Pet Dad", R.drawable.sample_user),
            SearchResult("Luna Smith", "Pet Groomer", R.drawable.sample_user),
            SearchResult("Buddy John", "Trainer", R.drawable.sample_user)
        )
    }
    var controller = navController

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        // Search Bar
        SearchBar(query = query, onQueryChange = { query = it }, placeholder = "Search")

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
        val categories = listOf("Pets Near You", "Events", "Pet Places", "Activities")
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(categories) { category ->
                val isSelected = selectedCategory == category

                FilterChip(
                    selected = isSelected,
                    onClick = { selectedCategory = category },
                    label = {
                        Text(
                            text = category,
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Medium,
                                fontSize = 14.sp,
                                color = if (isSelected) Color.White else TextGrey,
                                fontFamily = FontFamily(Font(R.font.outfit_regular))
                            )
                        )
                    },
                    modifier = Modifier
                        .height(40.dp)
                        .border(
                            width = 1.dp,
                            color = if (isSelected) PrimaryColor else TextGrey,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .clip(RoundedCornerShape(11.dp)),
                    colors = FilterChipDefaults.filterChipColors(
                        containerColor = Color.White,
                        selectedContainerColor = PrimaryColor,
                        labelColor = if (isSelected) Color.White else TextGrey,
                        selectedLabelColor = Color.White
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(15.dp))

        when (selectedCategory) {
            "Pets Near You" -> ShowPetsNearYou(searchResults,controller)
            else -> ShowGeneralResults(searchResults,controller)
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



@Preview(showBackground = true)
@Composable
fun DiscoverScreenPreview() {
    val navController = rememberNavController()

    DiscoverScreen(navController = navController)
}