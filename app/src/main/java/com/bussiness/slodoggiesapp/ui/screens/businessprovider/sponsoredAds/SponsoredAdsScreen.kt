package com.bussiness.slodoggiesapp.ui.screens.businessprovider.sponsoredAds

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.navigation.Routes
import com.bussiness.slodoggiesapp.ui.component.businessProvider.HeadingTextWithIcon
import com.bussiness.slodoggiesapp.ui.component.businessProvider.SponsorButton
import com.bussiness.slodoggiesapp.ui.dialog.AdsStatusDialog
import com.bussiness.slodoggiesapp.ui.screens.businessprovider.sponsoredAds.sposoredcontent.ActiveScreen
import com.bussiness.slodoggiesapp.ui.screens.businessprovider.sponsoredAds.sposoredcontent.ApprovedScreen
import com.bussiness.slodoggiesapp.ui.screens.businessprovider.sponsoredAds.sposoredcontent.ExpiredScreen
import com.bussiness.slodoggiesapp.ui.screens.businessprovider.sponsoredAds.sposoredcontent.PendingScreen
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor
import com.bussiness.slodoggiesapp.viewModel.sponsoredAds.SponsoredAdsViewmodel

@Composable
fun SponsoredAdsScreen(navController: NavHostController, viewModel: SponsoredAdsViewmodel = hiltViewModel()) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current


    LaunchedEffect(uiState.errorMessage) {
        uiState.errorMessage?.let { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            viewModel.clearError()
        }
    }


    BackHandler {
        if (!uiState.isNavigating) {
            viewModel.setNavigating(true)
            // check where this SponsoredAdsScreen came from
            if (uiState.fromPreview) {
                // Came from PreviewAdsScreen → go to home
                navController.navigate(Routes.HOME_SCREEN) {
                    launchSingleTop = true
                    popUpTo(Routes.HOME_SCREEN) {
                        inclusive = false
                    }
                }
            }else{
                // Came from anywhere else → normal back
                navController.popBackStack()
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize().background(Color.White)) {

        HeadingTextWithIcon("Sponsored Ads Dashboard",
            onBackClick = {    if (!uiState.isNavigating) {
                viewModel.setNavigating(true)
                // check where this SponsoredAdsScreen came from
                if (uiState.fromPreview) {
                    // Came from PreviewAdsScreen → go to home
                    navController.navigate(Routes.HOME_SCREEN) {
                        launchSingleTop = true
                        popUpTo(Routes.HOME_SCREEN) {
                            inclusive = false
                        }
                    }
                }else{
                    // Came from anywhere else → normal back
                    navController.popBackStack()
                }
            }})

        HorizontalDivider(modifier = Modifier.fillMaxWidth().height(2.dp).background(PrimaryColor))

        Column(modifier = Modifier.fillMaxSize().background(Color.White).padding(horizontal = 15.dp, vertical = 5.dp)) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf("Approved","Active", "Pending", "Expired").forEach { label ->
                    SponsorButton(
                        modifier = Modifier.weight(1f),
                        text = label,
                        isSelected = uiState.selectedStatus == label,
                        onClick =  { viewModel.updateSelectedStatus(label)}
                    )
                }
            }

            when (uiState.selectedStatus) {
                "Approved" -> ApprovedScreen(uiState.data.approved)
                "Active"   -> ActiveScreen(uiState.data.active, onStopClick = { })
                "Pending"  -> PendingScreen(uiState.data.pending)
                "Expired"  -> ExpiredScreen(uiState.data.expired, onAdRunAgain = { })
            }
        }
    }
    if (uiState.selectedStatus == "Pending"){
        if (uiState.showStatusDialog){
            AdsStatusDialog(
                onDismiss = { viewModel.showStatusDialog(false) },
                icon = R.drawable.time_clock,
                heading = "Your ad is under review!",
                desc1 = "Your ad is now under review by the admin team.",
                desc2 = "You will receive a notification once it is approved and live.",
                buttonText = "Go to Dashboard",
                onClick = {
                    viewModel.showStatusDialog(false)
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SponsoredAdsScreenPreview() {
    val navController = rememberNavController()
    /*SponsoredAdsScreen(navController = navController)*/
}