package com.bussiness.slodoggiesapp.ui.screens.businessprovider.sponsoredAds.sposoredcontent

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bussiness.slodoggiesapp.data.newModel.sponsered.BusinessAdItem
import com.bussiness.slodoggiesapp.ui.component.businessProvider.ExperienceRuleHeading
import com.bussiness.slodoggiesapp.ui.component.businessProvider.SponsoredEngagementCard

@Composable
fun ApprovedScreen(approved: List<BusinessAdItem>) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(approved) { item ->
                SponsoredEngagementCard(
                    descriptionText = item.title,
                    engagementData = item.description,
                    buttonText = item.buttonText,
                    status = false,
                    onButtonClick = { /* TODO: Handle click */ }
                )
            }
            item {
               ExperienceRuleHeading()

                Spacer(modifier = Modifier.height(8.dp))

                RulesSection()
            }
        }
    }
}