package com.bussiness.slodoggiesapp.ui.screens.businessprovider.services.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.model.businessProvider.ServicePackage
import com.bussiness.slodoggiesapp.navigation.Routes
import com.bussiness.slodoggiesapp.ui.component.businessProvider.AddServiceButton
import com.bussiness.slodoggiesapp.ui.component.businessProvider.FormHeadingText
import com.bussiness.slodoggiesapp.ui.component.businessProvider.ServicePackageCard

@Composable
fun ServicePackageSection(navController: NavHostController, onClickDelete: () -> Unit) {

    FormHeadingText("Available Services-")

    val apiDataList = listOf(
        ServicePackage(
            title = "Full Grooming Package",
            description = "Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et.",
            amount = "$100",
            photos = listOf(R.drawable.dog1, R.drawable.dog2, R.drawable.dog3,R.drawable.dog1, R.drawable.dog2, R.drawable.dog3)
        ),
        ServicePackage(
            title = "Basic Grooming Package",
            description = "Includes bath, brushing, and nail trimming. Perfect for regular care.",
            amount = "$70",
            photos = listOf(R.drawable.dog1, R.drawable.dog2, R.drawable.dog3)
        ),
        ServicePackage(
            title = "Premium Grooming Package",
            description = "Full grooming plus spa treatment, teeth cleaning, and massage.",
            amount = "$150",
            photos = listOf(R.drawable.dog2, R.drawable.dog3, R.drawable.dog1,R.drawable.dog1, R.drawable.dog3)
        )
    )

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(apiDataList) { servicePackage ->
            ServicePackageCard(
                data = servicePackage,
                onEdit = { navController.navigate("${Routes.EDIT_ADD_SERVICE_SCREEN}/${"edit"}") },
                onDelete = { onClickDelete() }
            )
        }
        item {
            Spacer(modifier = Modifier.height(12.dp))
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                AddServiceButton(modifier = Modifier, onClickButton = { navController.navigate("${Routes.EDIT_ADD_SERVICE_SCREEN}/${"add"}") })
            }
        }

    }
}

