package com.bussiness.slodoggiesapp.ui.screens.businessprovider.sponsoredAds

import android.util.Log
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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

@Composable
fun SponsoredAdsScreen(navController: NavHostController) {

    var selected by remember { mutableStateOf("Approved") }
    var statusDialog by remember { mutableStateOf(true) }
    var approvedDialog by remember { mutableStateOf(false) }
    var isNavigating by remember { mutableStateOf(false) }
    val fromPreview = navController.currentBackStackEntry
        ?.arguments
        ?.getBoolean("fromPreview")?: false
    Log.d("******","$fromPreview")
    BackHandler {

        if (!isNavigating) {
            isNavigating = true
            // check where this SponsoredAdsScreen came from
            if (fromPreview) {
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
            onBackClick = { if (!isNavigating) {
                isNavigating = true
                if (fromPreview) {
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
                Log.d("******","Back")
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
                        isSelected = selected == label,
                        onClick = { selected = label }
                    )
                }
            }

            when (selected) {
                "Approved" -> ApprovedScreen()
                "Active"   -> ActiveScreen()
                "Pending"  -> PendingScreen()
                "Expired"  -> ExpiredScreen()
            }
        }
    }
    if (selected == "Pending"){
        if (statusDialog){
            AdsStatusDialog(
                onDismiss = { statusDialog = false },
                icon = R.drawable.time_clock,
                heading = "Your ad is under review!",
                desc1 = "Your ad is now under review by the admin team.",
                desc2 = "You will receive a notification once it is approved and live.",
                buttonText = "Go to Dashboard",
                onClick = {
                    statusDialog = false
                    approvedDialog = true
                }
            )
        }
//        if (approvedDialog){
//            AdsStatusDialog(
//                onDismiss = { approvedDialog = false },
//                icon = R.drawable.icon_park_outline_success,
//                heading = "Your ad has been approved!",
//                desc1 = "Congratulations! Your ad is now live and visible to users on the platform.",
//                desc2 = "You can:\n" +
//                        "- View performance in the dashboard\n" +
//                        "- Edit or stop your ad anytime",
//                buttonText = "Proceed to pay.",
//                onClick = { approvedDialog = false }
//            )
//        }
    }
}

@Preview(showBackground = true)
@Composable
fun SponsoredAdsScreenPreview() {
    val navController = rememberNavController()
    SponsoredAdsScreen(navController = navController)
}