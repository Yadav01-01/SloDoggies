package com.bussiness.slodoggiesapp.ui.screens.commonscreens.discover.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.model.petOwner.PetPlaceItem
import com.bussiness.slodoggiesapp.ui.component.businessProvider.PetPlaceCard

@Composable
fun PetPlacesResults(onItemClick: () -> Unit) {
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
    Spacer(Modifier.height(4.dp))
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize(),

        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(petDummyPlace.size) { index ->
            PetPlaceCard(placeItem = petDummyPlace[index], onItemClick = { onItemClick() })
        }
    }
}