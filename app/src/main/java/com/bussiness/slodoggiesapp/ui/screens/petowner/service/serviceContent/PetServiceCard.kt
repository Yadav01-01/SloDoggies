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
import androidx.compose.foundation.layout.widthIn
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.data.newModel.ownerService.ServiceItem
import com.bussiness.slodoggiesapp.navigation.Routes

@Composable
fun PetServiceCard(
    service: ServiceItem,
    navController: NavHostController,
    onInquire: () -> Unit
) {

    val serviceName = service.serviceName
    val providerName = service.providerName.orEmpty()
    val location = service.location.orEmpty()
    val rating = service.averageRating.orEmpty()
    val imageUrl = service.image.orEmpty()
    val category = service.categoryName.firstOrNull().orEmpty()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .clickable { navController.navigate(Routes.SERVICE_PROVIDER_DETAILS) },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(10.dp)
    ) {

        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.Start
        ) {

            AsyncImage(
                model = imageUrl,
                placeholder = painterResource(R.drawable.fluent_color_paw),
                error = painterResource(R.drawable.fluent_color_paw),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.height(45.dp).width(50.dp).clip(RoundedCornerShape(10.dp))
            )

            Spacer(modifier = Modifier.height(6.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {

                Text(
                    text = serviceName ?: "",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontFamily = FontFamily(Font(R.font.outfit_medium)),
                    modifier = Modifier.widthIn(max = 100.dp)
                )

                Spacer(modifier = Modifier.width(4.dp))

                if (service.isBusinessVerified){
                    Image(
                        painter = painterResource(id = R.drawable.ic_check_mark),
                        contentDescription = "Verified",
                        modifier = Modifier.size(14.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(3.dp))

            Text(
                text = providerName,
                fontSize = 10.sp,
                color = Color.Black,
                fontFamily = FontFamily(Font(R.font.outfit_medium)),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_round_star),
                    contentDescription = "Rating",
                    tint = Color.Unspecified,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "$rating /5",
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(R.font.outfit_regular)),
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.location_ic_icon),
                    contentDescription = "location",
                    tint = Color.Unspecified
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "${location.ifEmpty { "Unknown" }} away",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = FontFamily(Font(R.font.outfit_regular)),
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Surface(
                color = Color.White,
                shape = RoundedCornerShape(6.dp),
                border = BorderStroke(1.dp, Color(0xFFCDCDCD))
            ) {
                Text(
                    text = category.ifEmpty { "N/A" },
                    fontSize = 10.sp,
                    color = Color(0xFF8A8894),
                    fontFamily = FontFamily(Font(R.font.outfit_medium)),
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onInquire() }
                    .border(1.dp, Color(0xFF258694), RoundedCornerShape(5.dp))
                    .padding(vertical = 8.dp),
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