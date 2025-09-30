package com.bussiness.slodoggiesapp.ui.screens.petowner.service.serviceContent

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.navigation.Routes

@Composable
fun PetServiceCard(service: PetService, navController: NavHostController,onInquire: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp).clickable { // Add clickable modifier
                navController.navigate(Routes.SERVICE_PROVIDER_DETAILS)
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            // Paw Icon

            Image(
                painter = painterResource(id = service.iconRes),
                contentDescription = null,
                modifier = Modifier.height(45.dp).width(50.dp)
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Service Name
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = service.name,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black,
                    maxLines = 1,
                    fontFamily = FontFamily(Font(R.font.outfit_medium)),
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.width(4.dp))
                Image(
                    painter = painterResource(id = R.drawable.ic_check_mark),
                    contentDescription = "Verified",
                    modifier = Modifier.size(14.dp)
                )
            }

            Spacer(modifier = Modifier.height(3.dp))

            // Provider Name with verified icon
            Text(
                text = service.providerName,
                fontSize = 10.sp,
                color = Color.Black,
                fontFamily = FontFamily(Font(R.font.outfit_medium)),
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Rating
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_round_star),
                    contentDescription = "Rating",
                    tint = Color.Unspecified,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = service.rating,
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(R.font.outfit_regular)),
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.location_ic_icon),
                    contentDescription = "location",
                    tint = Color.Unspecified,
                    modifier = Modifier.wrapContentSize()
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "${service.miles} away",
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(R.font.outfit_regular)),
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )

            }

            Spacer(modifier = Modifier.height(8.dp))

            // Service Type Chip
            Surface(
                color = Color.White, // White background
                shape = RoundedCornerShape(6.dp),
                border = BorderStroke(1.dp, Color(0xFFCDCDCD)), // Light gray border
                modifier = Modifier
                    .wrapContentSize()

            ) {
                Text(
                    text = service.serviceType,
                    fontSize = 10.sp, // Slightly larger font size to match appearance
                    color = Color(0xFF8A8894), // Soft gray text color
                    fontFamily = FontFamily(Font(R.font.outfit_medium)),
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp) // Inner padding
                )
            }


            Spacer(modifier = Modifier.height(10.dp))

            // Inquire Now Button
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onInquire() }
                    .border(
                        width = 1.dp,
                        color = Color(0xFF258694),
                        shape = RoundedCornerShape(5.dp)
                    )
                    .padding( vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {

                Icon(
                    painter = painterResource(id = R.drawable.ic_inform_checked),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = Modifier.size(15.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Inquire now",
                    color = Color(0xFF258694),
                    fontFamily = FontFamily(Font(R.font.outfit_medium)),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

data class PetService(
    val name: String,
    val providerName: String,
    val rating: String,
    val serviceType: String,
    val miles : String,
    val iconRes: Int
)