package com.bussiness.slodoggiesapp.ui.screens.businessprovider.services.content

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.bussiness.slodoggiesapp.ui.component.businessProvider.FormHeadingText
import com.bussiness.slodoggiesapp.ui.component.businessProvider.ServicePackageCard
import com.bussiness.slodoggiesapp.model.businessProvider.ServicePackage
import com.bussiness.slodoggiesapp.navigation.Routes

@Composable
fun ServicePackageSection(navController: NavHostController, servicePackage: ServicePackage) {
    FormHeadingText("Available Services-")

    Spacer(modifier = Modifier.height(10.dp))

    ServicePackageCard(
        data = servicePackage,
        onEdit = {  navController.navigate("${Routes.EDIT_ADD_SERVICE_SCREEN}/${"edit"}") },
        onDelete = { /* TODO: Implement Delete Logic */ }
    )
}

