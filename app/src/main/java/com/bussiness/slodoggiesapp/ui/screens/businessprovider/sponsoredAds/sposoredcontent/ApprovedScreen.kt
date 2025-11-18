package com.bussiness.slodoggiesapp.ui.screens.businessprovider.sponsoredAds.sposoredcontent

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.bussiness.slodoggiesapp.data.model.businessProvider.SponsoredEngagement
import com.bussiness.slodoggiesapp.ui.component.businessProvider.ExperienceRuleHeading
import com.bussiness.slodoggiesapp.ui.component.businessProvider.SponsoredEngagementCard


private val dummySponsoredList = listOf(
    com.bussiness.slodoggiesapp.data.model.businessProvider.SponsoredEngagement(
        descriptionText = "Cat Food Discount",
        engagementData = "No engagement data available",
        buttonText = "Pay Now"
    ),
    com.bussiness.slodoggiesapp.data.model.businessProvider.SponsoredEngagement(
        descriptionText = "Dog Grooming Offer",
        engagementData = "Engaged 134 users this week",
        buttonText = "Pay Now"
    )
)

@Composable
fun ApprovedScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(dummySponsoredList) { item ->
                SponsoredEngagementCard(
                    descriptionText = item.descriptionText,
                    engagementData = item.engagementData,
                    buttonText = item.buttonText,
                    status = false,
                    onButtonClick = { /* TODO: Handle click */ }
                )
            }
            item {
               ExperienceRuleHeading()

                Spacer(modifier = Modifier.height(8.dp))

                val rules = listOf(
                    "Top 3 results reserved for sponsored ads (marked as \"Sponsored\").",
                    "Based on category relevance & rotation/fixed purchase order.",
                    "Ads appear only if live, approved, and within budget.",
                    "Hidden once expired or budget is exhausted.",
                    "Real-time Tap to Call and Message options."
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFE5EFF2), shape = RoundedCornerShape(12.dp))
                        .padding(12.dp)
                ) {
                    BulletList(rules = rules)
                }
            }
        }
    }
}