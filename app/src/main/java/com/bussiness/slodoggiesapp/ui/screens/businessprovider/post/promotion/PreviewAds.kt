package com.bussiness.slodoggiesapp.ui.screens.businessprovider.post.promotion

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.navigation.Routes
import com.bussiness.slodoggiesapp.ui.component.businessProvider.HeadingTextWithIcon
import com.bussiness.slodoggiesapp.ui.component.businessProvider.SubmitButton
import com.bussiness.slodoggiesapp.ui.screens.businessprovider.post.content.PreviewHeading
import com.bussiness.slodoggiesapp.ui.screens.businessprovider.post.content.PreviewSubHeading
import com.bussiness.slodoggiesapp.ui.screens.businessprovider.post.content.SponsorPostCaption
import com.bussiness.slodoggiesapp.ui.screens.businessprovider.post.content.SponsorPostHeader
import com.bussiness.slodoggiesapp.ui.screens.businessprovider.post.content.SponsorPostMedia
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor

@Composable
fun PreviewAdsScreen(navController: NavHostController) {
    Column(modifier = Modifier.fillMaxSize().background(Color.White)) {

        BackHandler {
            navController.navigate(Routes.BUDGET_SCREEN){
                launchSingleTop = true
                popUpTo(Routes.BUDGET_SCREEN){
                    inclusive = false
                }
            }
        }

        HeadingTextWithIcon(textHeading = "Preview Ad",
            onBackClick = { navController.navigate(Routes.BUDGET_SCREEN){
                launchSingleTop = true
                popUpTo(Routes.BUDGET_SCREEN){
                    inclusive = false
                }
            } })

        HorizontalDivider(modifier = Modifier.fillMaxWidth().height(2.dp).background(PrimaryColor))

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                SponsorPostHeader(userImage = " ", user = "Nobisuke", time = "2 Days", onReportClick = { })
                SponsorPostCaption(caption = "Summer Special: 20% Off Grooming!", description = "Limited Time Offer")
                SponsorPostMedia(mediaList = "post.mediaList")
                HorizontalDivider(thickness = 1.dp, color = Color(0xFFE5EFF2), modifier = Modifier.padding(vertical = 12.dp))
            }

            // --- Ad Details Section ---
            item {
                Section(
                    title = "• Ad Details",
                    items = {
                        PreviewSubHeading(stringResource(R.string.adtitle), "Free First Walk")
                        PreviewSubHeading(stringResource(R.string.description__), "Get your pet's first walk for free with Happy Paws!")
                        PreviewSubHeading(stringResource(R.string.Category_), "Grooming")
                        PreviewSubHeading(stringResource(R.string.Service_), "Service name 1")
                        PreviewSubHeading(stringResource(R.string.exp_date_time), "06/30/2025 - 05:00 PM")
                        PreviewSubHeading(stringResource(R.string.terms_con), "Offer valid for new customers only.")
                    }
                )
            }

            // --- Engagement & Location Section ---
            item {
                Section(
                    title = "• Engagement & Location",
                    items = {
                        PreviewSubHeading(stringResource(R.string.location_type), "Local (San Luis Obispo)")
                        PreviewSubHeading(stringResource(R.string.contact_info), "✅ Yes")
                        PreviewSubHeading(stringResource(R.string.mobile_no), "805 123-4567")
                    }
                )
            }

            // --- Pricing Section ---
            item {
                Section(
                    title = "• Pricing & Reach Estimates",
                    items = {
                        PreviewSubHeading(stringResource(R.string.daliy_budget), "$42")
                        PreviewSubHeading(stringResource(R.string.ads_budget), "$42 per day")
                    }
                )
            }

            // --- Submit Button ---
            item {
                Spacer(Modifier.height(15.dp))
                SubmitButton(buttonText = "Submit", onClickButton = { })
                Spacer(Modifier.height(10.dp))
            }
        }
    }
}

@Composable
fun Section(title: String, items: @Composable ColumnScope.() -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        PreviewHeading(title)
        items()
    }
}
