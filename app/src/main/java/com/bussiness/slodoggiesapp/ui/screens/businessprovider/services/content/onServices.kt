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
import com.bussiness.slodoggiesapp.data.newModel.servicelist.Data
import com.bussiness.slodoggiesapp.navigation.Routes
import com.bussiness.slodoggiesapp.ui.component.businessProvider.AddServiceButton
import com.bussiness.slodoggiesapp.ui.component.businessProvider.FormHeadingText
import com.bussiness.slodoggiesapp.ui.component.businessProvider.ServicePackageCard

@Composable
fun ServicePackageSection(
    uiStateService: MutableList<Data>?,
    navController: NavHostController,
    onClickDelete: () -> Unit
) {
    FormHeadingText("Available Services-")
      LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
          uiStateService?.let {
                items(it) { servicePackage ->
                    ServicePackageCard(
                        data = servicePackage,
                        onEdit = { navController.navigate("${Routes.EDIT_ADD_SERVICE_SCREEN}/${"edit"}") },
                        onDelete = { onClickDelete() }
                    )
                }
            }
            item {
                Spacer(modifier = Modifier.height(12.dp))
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    AddServiceButton(modifier = Modifier, onClickButton = {
                        navController.navigate("${Routes.EDIT_ADD_SERVICE_SCREEN}/${"add"}") }
                    )
                }
            }

        }
}

