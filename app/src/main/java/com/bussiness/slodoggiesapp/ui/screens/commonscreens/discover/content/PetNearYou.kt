package com.bussiness.slodoggiesapp.ui.screens.commonscreens.discover.content

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.data.newModel.discover.PetItem
import com.bussiness.slodoggiesapp.navigation.Routes
import com.bussiness.slodoggiesapp.ui.component.businessProvider.SearchResultItem
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor

@Composable
fun ShowPetsNearYou(results: List<PetItem>, controller: NavHostController) {
    if (results.isEmpty()){
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
                    text = "OOps!,No services found",
                    style = MaterialTheme.typography.titleMedium,
                    color = PrimaryColor,
                    fontFamily = FontFamily(Font(R.font.outfit_medium)),
                    fontSize = 18.sp
                )
            }
        }
    }else{
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            items(results) { result ->
                SearchResultItem(
                    name = result.name,
                    label = "",
                    imageRes = result.safeImage,
                    onItemClick = { controller.navigate("${Routes.PERSON_DETAIL_SCREEN}/${result.pet_owner_id}") },
                    onRemove = {},
                    labelVisibility = false,
                    crossVisibility = true
                )
            }
        }
    }
}