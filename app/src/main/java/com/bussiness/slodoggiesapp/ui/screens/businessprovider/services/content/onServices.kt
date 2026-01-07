package com.bussiness.slodoggiesapp.ui.screens.businessprovider.services.content

import android.net.Uri
import android.util.Log
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
import com.bussiness.slodoggiesapp.data.newModel.ownerService.ServiceItemDetails
import com.bussiness.slodoggiesapp.data.newModel.servicelist.Data
import com.bussiness.slodoggiesapp.navigation.Routes
import com.bussiness.slodoggiesapp.ui.component.businessProvider.AddServiceButton
import com.bussiness.slodoggiesapp.ui.component.businessProvider.FormHeadingText
import com.bussiness.slodoggiesapp.ui.component.businessProvider.ServicePackageCard
import com.google.gson.Gson


@Composable
fun ServicePackageSection(
    uiStateService: MutableList<ServiceItemDetails>,
    navController: NavHostController,
    onClickDelete: (id:Int) -> Unit
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
                        onEdit = { data->
//                            Log.d("TESTING_DATA","data is "+data)
//                            val jsonString = Gson().toJson(data)
//                            val encodedJson = Uri.encode(jsonString)
//                            navController.navigate("${Routes.EDIT_ADD_SERVICE_SCREEN}/""/$encodedJson)

                            val type = "edit" // or pass dynamically

                            val encodedJson = Uri.encode(Gson().toJson(data))

                            navController.navigate(
                                "${Routes.EDIT_ADD_SERVICE_SCREEN}/$type/$encodedJson"
                            )
                                 },
                        onDelete = { onClickDelete(servicePackage.serviceId) }
                    )
                }
            }
            item {
                Spacer(modifier = Modifier.height(12.dp))
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    AddServiceButton(modifier = Modifier, onClickButton = {
                        navController.navigate(Routes.ADD_SERVICE_NEW)
                      }
                    )
                }
            }
        }
}

