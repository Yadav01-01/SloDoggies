package com.bussiness.slodoggiesapp.ui.screens.commonscreens.discover.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bussiness.slodoggiesapp.data.newModel.discover.PetPlaceItem
import com.bussiness.slodoggiesapp.ui.component.businessProvider.PetPlaceCard

@Composable
fun PetPlacesResults(
    petPlacesData: List<PetPlaceItem>,
    onItemClick: (String) -> Unit
) {
    Spacer(Modifier.height(4.dp))

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(petPlacesData.size) { index ->
            val item = petPlacesData[index]
            PetPlaceCard(
                placeItem = item,
                onItemClick = { onItemClick(item.placeId ?: "") }
            )
        }
    }
}
