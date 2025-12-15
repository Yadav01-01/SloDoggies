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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.data.newModel.sponsered.BusinessAdItem
import com.bussiness.slodoggiesapp.ui.component.businessProvider.ExperienceRuleHeading

@Composable
fun PendingScreen(
    pending: List<BusinessAdItem>
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {

        // -------- Pending Ads --------
        items(
            items = pending,
            key = { it.id }
        ) { ad ->
            PendingAdItem(ad)
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
fun PendingAdItem(
    data: BusinessAdItem
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = Color(0xFFDEEBEE),
                shape = RoundedCornerShape(10.dp)
            )
            .padding(16.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = data.title,
                fontWeight = FontWeight.Medium,
                fontFamily = FontFamily(Font(R.font.poppins_semi_bold)),
                fontSize = 14.sp
            )

            Text(
                text = "Under Review",
                color = Color.Black,
                fontSize = 11.sp,
                fontFamily = FontFamily(Font(R.font.outfit_semibold)),
                modifier = Modifier
                    .background(Color(0xFFDEEBEE), RoundedCornerShape(8.dp))
                    .padding(horizontal = 10.dp, vertical = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = data.description,
            fontSize = 12.sp,
            fontFamily = FontFamily(Font(R.font.poppins)),
            color = Color.Black
        )
    }
}
