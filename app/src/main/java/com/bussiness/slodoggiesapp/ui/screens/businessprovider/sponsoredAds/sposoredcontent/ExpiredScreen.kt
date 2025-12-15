package com.bussiness.slodoggiesapp.ui.screens.businessprovider.sponsoredAds.sposoredcontent

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.data.newModel.sponsered.BusinessAdItem
import com.bussiness.slodoggiesapp.ui.component.businessProvider.ExperienceRuleHeading
import com.bussiness.slodoggiesapp.ui.component.businessProvider.SponsoredEngagementCard

@Composable
fun ExpiredScreen(
    expired: List<BusinessAdItem>,
    onAdRunAgain: () -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {

        // -------- Expired Ads --------
        items(
            items = expired,
            key = { it.id }
        ) { ad ->
            ExpiredAdItem(ad, onAdRunAgain = { onAdRunAgain() })
        }

        // -------- Experience Heading --------
        item {
            ExperienceRuleHeading()
        }

        // -------- Rules Section --------
        item {
            RulesSection()
        }
    }
}


@Composable
fun ExpiredAdItem(
    data: BusinessAdItem,
    onAdRunAgain: () -> Unit
) {
    SponsoredEngagementCard(
        descriptionText = data.title,
        engagementData = data.description,
        buttonText = "Ad Run Again",
        onButtonClick = {
            onAdRunAgain()
        }
    )
}


@Composable
fun BulletList(rules: List<String>) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        rules.forEach { rule ->
            Row(
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = "•",
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.poppins)),
                    color = Color.Black,
                    modifier = Modifier.padding(end = 6.dp)
                )
                Text(
                    text = rule,
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(R.font.poppins)),
                    color = Color.Black,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ExpiredScreenPreview() {
    /*ExpiredScreen(uiState.data.expired)*/
}