package com.bussiness.slodoggiesapp.ui.screens.businessprovider.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.ui.component.businessProvider.HeadingTextWithIcon
import com.bussiness.slodoggiesapp.ui.component.businessProvider.SponsorButton
import com.bussiness.slodoggiesapp.ui.component.businessProvider.SubmitButton
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor
import com.bussiness.slodoggiesapp.ui.theme.TextGrey

@Composable
fun SponsoredAdsScreen(navController: NavHostController) {

    var selected by remember { mutableStateOf("Active") }

    Column(modifier = Modifier.fillMaxSize().background(Color.White)) {

        HeadingTextWithIcon("Sponsored Ads Dashboard", onBackClick = { navController.popBackStack()})

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
                "Expired" -> ExpiredScreen()
            }


        }


    }
}

@Composable
fun ActiveScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize().verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {

        // Ad Control Section
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFE5EFF2), shape = RoundedCornerShape(12.dp))
                .padding(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Free First Walk", fontWeight = FontWeight.Medium, fontFamily = FontFamily(Font(R.font.outfit_medium)), fontSize = 14.sp)
                Text(
                    text = "Live",
                    color = PrimaryColor,
                    fontSize = 11.sp,
                    fontFamily = FontFamily(Font(R.font.outfit_semibold)),
                    modifier = Modifier
                        .background(Color(0xFFCEE1E6), RoundedCornerShape(8.dp))
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row (verticalAlignment = Alignment.CenterVertically){
                Icon(
                    painter = painterResource(R.drawable.msg_ic),
                    contentDescription = "message icon",
                    tint = Color.Unspecified,
                    modifier = Modifier.wrapContentSize()
                )
                Spacer(Modifier.width(10.dp))
                Text("Message Leads : 25", fontSize = 12.sp, fontFamily = FontFamily(Font(R.font.outfit_regular)), color = Color.Black)
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(
                    onClick = { /* TODO: Edit */ },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE5EFF2)),
                    shape = RoundedCornerShape(10.dp),
                ) {
                    Text("Edit Ad", color = PrimaryColor, fontSize = 15.sp, fontFamily = FontFamily(Font(R.font.outfit_medium)))
                }

                SubmitButton(
                    modifier = Modifier.weight(1f),
                    buttonText = "Stop",
                    onClickButton = { /* TODO */ }
                )
            }

        }

        Text("Billing History & Invoices", fontFamily = FontFamily(Font(R.font.outfit_medium)),
            fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color.Black)

        // Billing Section
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFE5EFF2), shape = RoundedCornerShape(12.dp))
                .padding(10.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Text("June 4, 2025", fontSize = 12.sp, color = Color.Black, fontFamily = FontFamily(Font(R.font.outfit_regular)))
            Spacer(modifier = Modifier.height(8.dp))
            Text("50 Leads In \$100", fontSize = 12.sp, fontFamily = FontFamily(Font(R.font.outfit_bold)), color = Color.Black)
            Spacer(modifier = Modifier.height(12.dp))

            SubmitButton(
                modifier = Modifier.fillMaxWidth(),
                buttonText = "Download Invoice",
                onClickButton = { /* TODO */ }
            )
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

            rules.forEach {
                Text("• $it", fontSize = 14.sp, color = Color.Black)
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }
}

@Composable
fun ExpiredScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize().verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {

        // Ad Control Section
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFE5EFF2), shape = RoundedCornerShape(12.dp))
                .padding(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Cat Food Discount", fontWeight = FontWeight.Medium, fontFamily = FontFamily(Font(R.font.outfit_medium)), fontSize = 14.sp)
                Text(
                    text = "Expired",
                    color = Color.White,
                    fontSize = 11.sp,
                    fontFamily = FontFamily(Font(R.font.outfit_semibold)),
                    modifier = Modifier
                        .background(TextGrey, RoundedCornerShape(8.dp))
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text("No engagement data available", fontSize = 12.sp, fontFamily = FontFamily(Font(R.font.outfit_regular)), color = Color.Black)
        }

        Text("Billing History & Invoices", fontFamily = FontFamily(Font(R.font.outfit_medium)),
            fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color.Black)

        // Billing Section
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFE0F7FA), shape = RoundedCornerShape(12.dp))
                .padding(10.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Text("June 4, 2025", fontSize = 12.sp, color = Color.Black, fontFamily = FontFamily(Font(R.font.outfit_regular)))
            Spacer(modifier = Modifier.height(8.dp))
            Text("50 Leads In \$100", fontSize = 12.sp, fontFamily = FontFamily(Font(R.font.outfit_bold)), color = Color.Black)
            Spacer(modifier = Modifier.height(12.dp))

            SubmitButton(
                modifier = Modifier.fillMaxWidth(),
                buttonText = "Download Invoice",
                onClickButton = { /* TODO */ }
            )
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

            rules.forEach {
                Text("• $it", fontSize = 14.sp, color = Color.Gray)
                Spacer(modifier = Modifier.height(4.dp))
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