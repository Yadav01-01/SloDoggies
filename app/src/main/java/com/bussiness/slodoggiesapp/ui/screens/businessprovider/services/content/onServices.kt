package com.bussiness.slodoggiesapp.ui.screens.businessprovider.services.content

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bussiness.slodoggiesapp.ui.component.businessProvider.FormHeadingText
import com.bussiness.slodoggiesapp.ui.component.businessProvider.ServicePackageCard
import com.bussiness.slodoggiesapp.model.ServicePackage

@Composable
fun ServicePackageSection(servicePackage: ServicePackage) {
    FormHeadingText("Available Services-")

    Spacer(modifier = Modifier.height(10.dp))

    ServicePackageCard(
        data = servicePackage,
        onEdit = { /* TODO: Implement Edit Logic */ },
        onDelete = { /* TODO: Implement Delete Logic */ }
    )
}

