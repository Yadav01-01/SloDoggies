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
import androidx.compose.foundation.layout.wrapContentWidth
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
import com.bussiness.slodoggiesapp.ui.component.businessProvider.SubmitButton
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor

@Composable
fun ActiveScreen(clicks: Int = 1200, impressions: Int = 1300) {
    Column(
        modifier = Modifier
            .fillMaxSize().verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {

        // Ad Control Section
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
                    Font(
                        R.font.outfit_medium)
                ), fontSize = 18.sp)
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

            Row(
                modifier = Modifier
            ) {
                Text(
                    text = "Clicks : $clicks",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.outfit_regular)),
                        fontWeight = FontWeight.Normal,
                        color = Color.Black
                    )
                )
                Text(
                    text = " | ",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.outfit_regular)),
                        fontWeight = FontWeight.Normal,
                        color = Color.Black
                    )
                )
                Text(
                    text = "Impression : $impressions",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.outfit_regular)),
                        fontWeight = FontWeight.Normal,
                        color = Color.Black
                    )
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row (verticalAlignment = Alignment.CenterVertically){
                Icon(
                    painter = painterResource(R.drawable.msg_ic),
                    contentDescription = "message icon",
                    tint = Color.Unspecified,
                    modifier = Modifier.size(19.dp)
                )
                Spacer(Modifier.width(10.dp))
                Text("Message Leads : 25", fontSize = 12.sp, fontFamily = FontFamily(Font(R.font.outfit_regular)), color = Color.Black)
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Expiry Date : 06/30/2025",
                    color = PrimaryColor,
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.outfit_medium))
                )

                Button(
                    onClick = { },
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryColor),
                    shape = RoundedCornerShape(10.dp),
                    contentPadding = PaddingValues(horizontal = 20.dp, vertical = 6.dp),
                    modifier = Modifier.height(38.dp) // height fixed, width wrap content
                ) {
                    Text(
                        text = "Stop",
                        color = Color.White,
                        fontWeight = FontWeight.Medium,
                        fontFamily = FontFamily(Font(R.font.outfit_medium)),
                        fontSize = 15.sp
                    )
                }
            }

        }


        // Billing Section
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
                Text("Ad Title", fontWeight = FontWeight.Medium, fontFamily = FontFamily(Font(R.font.outfit_medium)), fontSize = 18.sp)
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

            Row(
                modifier = Modifier
            ) {
                Text(
                    text = "Clicks : $clicks",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.outfit_regular)),
                        fontWeight = FontWeight.Normal,
                        color = Color.Black
                    )
                )
                Text(
                    text = " | ",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.outfit_regular)),
                        fontWeight = FontWeight.Normal,
                        color = Color.Black
                    )
                )
                Text(
                    text = "Impression : $impressions",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.outfit_regular)),
                        fontWeight = FontWeight.Normal,
                        color = Color.Black
                    )
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row (verticalAlignment = Alignment.CenterVertically){
                Icon(
                    painter = painterResource(R.drawable.msg_ic),
                    contentDescription = "message icon",
                    tint = Color.Unspecified,
                    modifier = Modifier.size(19.dp)
                )
                Spacer(Modifier.width(10.dp))
                Text("Message Leads : 25", fontSize = 12.sp, fontFamily = FontFamily(Font(R.font.outfit_regular)), color = Color.Black)
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Expiry Date : 06/30/2025",
                    color = PrimaryColor,
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.outfit_medium))
                )

                Button(
                    onClick = { },
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryColor),
                    shape = RoundedCornerShape(10.dp),
                    contentPadding = PaddingValues(horizontal = 20.dp, vertical = 6.dp),
                    modifier = Modifier.height(38.dp) // height fixed, width wrap content
                ) {
                    Text(
                        text = "Stop",
                        color = Color.White,
                        fontWeight = FontWeight.Medium,
                        fontFamily = FontFamily(Font(R.font.outfit_medium)),
                        fontSize = 15.sp
                    )
                }
            }


        }

        Text("Search Experience Rules", fontFamily = FontFamily(Font(R.font.outfit_medium)),
            fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.Black)

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