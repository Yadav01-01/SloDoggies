package com.bussiness.slodoggiesapp.ui.screens.businessprovider.sponsoredAds.sposoredcontent

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.data.newModel.sponsered.BusinessAdItem
import com.bussiness.slodoggiesapp.ui.component.businessProvider.ExperienceRuleHeading
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor

@Composable
fun ActiveScreen(
    ads: List<BusinessAdItem>,
    onStopClick: () -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        // -------- Ads List --------
        items(
            items = ads,
            key = { it.id }
        ) { ad ->
            BusinessAdItemView(
                data = ad,
                onStopClick = {
                    onStopClick()
                }
            )
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
fun RulesSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFE5EFF2), shape = RoundedCornerShape(12.dp))
            .padding(12.dp)
    ) {

        val rules = listOf(
            "Top 3 results reserved for sponsored ads (marked as \"Sponsored\").",
            "Based on category relevance & rotation/fixed purchase order.",
            "Ads appear only if live, approved, and within budget.",
            "Hidden once expired or budget is exhausted.",
            "Real-time Tap to Call and Message options."
        )

        BulletList(rules = rules)
    }
}


@Composable
fun BusinessAdItemView(
    data: BusinessAdItem,
    onStopClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = Color(0xFFDEEBEE),
                shape = RoundedCornerShape(10.dp)
            )
            .padding(top = 20.dp, bottom = 20.dp, start = 15.dp, end = 15.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = data.title,
                fontWeight = FontWeight.Medium,
                fontFamily = FontFamily(Font(R.font.outfit_medium)),
                fontSize = 18.sp
            )

            Text(
                text = "Live",
                color = Color.White,
                fontSize = 11.sp,
                fontFamily = FontFamily(Font(R.font.outfit_semibold)),
                modifier = Modifier
                    .background(Color(0xFF44ED11), RoundedCornerShape(8.dp))
                    .padding(horizontal = 10.dp, vertical = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row {
            Text(
                text = "Clicks : ${data.clicks}",
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.outfit_regular))
            )
            Text(
                text = " | ",
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.outfit_regular))
            )
            Text(
                text = "Impression : ${data.likesCount}",
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.outfit_regular))
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(R.drawable.msg_ic),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier.size(19.dp)
            )
            Spacer(Modifier.width(10.dp))
            Text(
                text = "Message Leads : 25",
                fontSize = 12.sp,
                fontFamily = FontFamily(Font(R.font.outfit_regular))
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Expiry Date : ${data.expiryDate}",
                color = PrimaryColor,
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.outfit_medium))
            )

            Button(
                onClick = onStopClick,
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryColor),
                shape = RoundedCornerShape(10.dp),
                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 6.dp),
                modifier = Modifier.height(38.dp)
            ) {
                Text(
                    text = "Stop",
                    color = Color.White,
                    fontFamily = FontFamily(Font(R.font.outfit_medium)),
                    fontSize = 15.sp
                )
            }
        }
    }
}
