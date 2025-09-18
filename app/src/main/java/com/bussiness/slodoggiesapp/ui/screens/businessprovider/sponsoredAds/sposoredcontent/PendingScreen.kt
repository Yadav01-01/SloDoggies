package com.bussiness.slodoggiesapp.ui.screens.businessprovider.sponsoredAds.sposoredcontent

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor
import com.bussiness.slodoggiesapp.ui.theme.TextGrey

@Composable
fun PendingScreen(){
    Column(
        modifier = Modifier
            .fillMaxSize().verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .border(width = 1.dp, color = Color(0xFFDEEBEE), shape = RoundedCornerShape(10.dp))
                .padding(top = 20.dp, bottom = 20.dp, start = 15.dp, end = 15.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Free First Walk", fontWeight = FontWeight.Medium, fontFamily = FontFamily(
                    Font(R.font.poppins_semi_bold)
                ), fontSize = 14.sp)
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

            Text("No engagement data available", fontSize = 12.sp, fontFamily = FontFamily(Font(R.font.poppins)), color = Color.Black)

        }

        Text("Search Experience Rules", fontFamily = FontFamily(Font(R.font.outfit_medium)),
            fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color.Black)

        // Rules Section
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFE5EFF2), shape = RoundedCornerShape(12.dp))
                .padding(8.dp)
        ) {

            Spacer(modifier = Modifier.height(8.dp))

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
}