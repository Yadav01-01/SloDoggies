package com.bussiness.slodoggiesapp.ui.screens.commonscreens.discover.content

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.bussiness.slodoggiesapp.data.newModel.discover.PetItem
import com.bussiness.slodoggiesapp.navigation.Routes
import com.bussiness.slodoggiesapp.ui.component.businessProvider.SearchResultItem

@Composable
fun ShowPetsNearYou(results: List<PetItem>, controller: NavHostController) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        items(results) { result ->
            SearchResultItem(
                name = result.name,
                label = "",
                imageRes = result.safeImage,
                onItemClick = { controller.navigate(Routes.PERSON_DETAIL_SCREEN) },
                onRemove = {},
                labelVisibility = false,
                crossVisibility = true
            )
        }
    }
}