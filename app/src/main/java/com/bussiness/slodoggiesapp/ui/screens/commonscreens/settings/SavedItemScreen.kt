package com.bussiness.slodoggiesapp.ui.screens.commonscreens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.model.GalleryItem
import com.bussiness.slodoggiesapp.ui.component.businessProvider.GalleryItemCard
import com.bussiness.slodoggiesapp.ui.component.businessProvider.HeadingTextWithIcon
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor

@Composable
fun SavedItemScreen(navController: NavHostController) {

    val dataList = listOf(
        GalleryItem(R.drawable.dog1),
        GalleryItem(R.drawable.dog2, isVideo = true),
        GalleryItem(R.drawable.dog1),
        GalleryItem(R.drawable.dog2),
        GalleryItem(R.drawable.dog1),
        GalleryItem(R.drawable.dog2)
    )

    Column (modifier = Modifier.fillMaxSize().background(Color.White) ){
        HeadingTextWithIcon(textHeading = "Saved Post", onBackClick = { navController.popBackStack() })

        HorizontalDivider(modifier = Modifier.fillMaxWidth().height(2.dp).background(PrimaryColor))

        Spacer(Modifier.height(10.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(dataList.size) { index ->
                GalleryItemCard(item = dataList[index], height =125 )
            }
        }

    }
}

@Preview
@Composable
fun SavedItemPreview(){
    val navController = rememberNavController()
    SavedItemScreen(navController)
}