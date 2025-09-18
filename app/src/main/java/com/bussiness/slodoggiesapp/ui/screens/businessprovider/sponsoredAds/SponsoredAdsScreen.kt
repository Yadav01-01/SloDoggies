package com.bussiness.slodoggiesapp.ui.screens.businessprovider.sponsoredAds

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.navigation.Routes
import com.bussiness.slodoggiesapp.ui.component.businessProvider.HeadingTextWithIcon
import com.bussiness.slodoggiesapp.ui.component.businessProvider.SponsorButton
import com.bussiness.slodoggiesapp.ui.component.businessProvider.SubmitButton
import com.bussiness.slodoggiesapp.ui.screens.businessprovider.sponsoredAds.sposoredcontent.ActiveScreen
import com.bussiness.slodoggiesapp.ui.screens.businessprovider.sponsoredAds.sposoredcontent.ExpiredScreen
import com.bussiness.slodoggiesapp.ui.screens.businessprovider.sponsoredAds.sposoredcontent.PendingScreen
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor
import com.bussiness.slodoggiesapp.ui.theme.TextGrey

@Composable
fun SponsoredAdsScreen(navController: NavHostController) {

    var selected by remember { mutableStateOf("Active") }

    BackHandler {
        navController.navigate(Routes.PROFILE_SCREEN) {
            launchSingleTop = true
            popUpTo(Routes.PROFILE_SCREEN) {
                inclusive = false
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize().background(Color.White)) {

        HeadingTextWithIcon("Sponsored Ads Dashboard",
            onBackClick = {navController.navigate(Routes.PROFILE_SCREEN) {
                launchSingleTop = true
                popUpTo(Routes.PROFILE_SCREEN) {
                    inclusive = false
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
                listOf("Active", "Pending", "Expired").forEach { label ->
                    SponsorButton(
                        modifier = Modifier.weight(1f),
                        text = label,
                        isSelected = selected == label,
                        onClick = { selected = label }
                    )
                }
            }

            when (selected) {
                "Active" -> ActiveScreen()
                "Pending" -> PendingScreen()
                "Expired" -> ExpiredScreen()
            }
        }
    }
}




@Preview(showBackground = true)
@Composable
fun SponsoredAdsScreenPreview() {
    val navController = rememberNavController()
    SponsoredAdsScreen(navController = navController)
}