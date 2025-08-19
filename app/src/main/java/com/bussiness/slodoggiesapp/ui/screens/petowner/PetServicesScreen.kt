package com.bussiness.slodoggiesapp.ui.screens.petowner

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.navigation.Routes
import com.bussiness.slodoggiesapp.ui.component.petOwner.IconHeadingText
import com.bussiness.slodoggiesapp.ui.component.petOwner.SearchBar

@Composable
fun PetServicesScreen(navController: NavHostController) {
    var searchText by remember { mutableStateOf("") }
    var selectedServiceType by remember { mutableStateOf<String?>(null) }

    val serviceTypes = listOf("Search", "Walking", "Grooming", "Sitting / Boarding", "Veterinary")

    Column(){
        IconHeadingText(
            textHeading = "Services",
            onBackClick = {
                navController.popBackStack()
            },
            onIconClick = {

            },
            rightSideIcon = R.drawable.ic_check_icon_blue,
            iconColor = Color(0xFF258694),
            dividerColor = Color(0xFF258694),
            displayRightIcon = false
        )

        Spacer(modifier = Modifier.height(20.dp))
        SearchBar(
            searchText = searchText,
            onSearchTextChange = { searchText = it },
            placeholder = "Search",
            backgroundColor = Color(0xFFF4F4F4),
            cornerRadius = 12.dp,

            modifier = Modifier.padding(horizontal = 15.dp).shadow(2.dp, RoundedCornerShape(12.dp))

        )
        Spacer(modifier = Modifier.height(12.dp))

        // Service Type Filter Chips
        LazyRow(
            modifier = Modifier.padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(serviceTypes) { serviceType ->
                ServiceTypeChip(
                    serviceType = serviceType,
                    isSelected = selectedServiceType == serviceType,
                    onClick = {
                        selectedServiceType = if (selectedServiceType == serviceType) null else serviceType
                    }
                )
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        PetServicesGrid(selectedServiceType = selectedServiceType, searchText = searchText,navController= navController)
    }
}
@Composable
fun ServiceTypeChip(
    serviceType: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        color = if (isSelected) Color(0xFF258694) else Color.White,
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, Color(0xFFCDCDCD)),
        modifier = Modifier
            .wrapContentSize()
            .clip(RoundedCornerShape(8.dp))
    ) {
        Text(
            text = serviceType,
            fontSize = 12.sp,
            color = if (isSelected) Color.White else Color(0xFF8A8894),
            fontFamily = FontFamily(Font(R.font.outfit_medium)),
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .clickable { onClick() }
        )
    }
}

@Composable
fun PetServicesGrid(selectedServiceType: String?, searchText: String,navController: NavHostController) {

    val allPetServices = listOf(
        PetService("Pawfect Pet Care", "Provider Name", 4.8f, "Grooming", R.drawable.paw_icon),
        PetService("SLO Pet Centre", "Provider Name", 4.8f, "Walking", R.drawable.paw_icon),
        PetService("Pawfect Pet Sitters", "Provider Name", 4.8f, "Boarding", R.drawable.paw_icon),
        PetService("Dozy Pet Sitters", "Provider Name", 4.8f, "Boarding", R.drawable.paw_icon),
        PetService("Grooming Pet Sitters", "Provider Name", 4.8f, "Grooming", R.drawable.paw_icon),
        PetService("SLODOG Centre", "Provider Name", 4.8f, "Veterinary", R.drawable.paw_icon)
    )
    val filteredServices = allPetServices.filter { service ->
        (selectedServiceType == null ||
                (selectedServiceType == "Search" && service.name.contains(searchText, ignoreCase = true)) ||
                (selectedServiceType != "Search" && service.serviceType.equals(selectedServiceType, ignoreCase = true))) &&
                (searchText.isEmpty() || service.name.contains(searchText, ignoreCase = true))
    }

    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        verticalItemSpacing = 8.dp,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(16.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(filteredServices) { service ->
            PetServiceCard(service = service,navController = navController)
        }
    }
}

@Composable
fun PetServiceCard(service: PetService,navController: NavHostController) {
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
                //  tint = Color(0xFFFF8C00),
                modifier = Modifier.height(45.dp).width(50.dp)
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Service Name
            Text(
                text = service.name,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                maxLines = 1,
                fontFamily = FontFamily(Font(R.font.outfit_medium)),
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(3.dp))

            // Provider Name with verified icon
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = service.providerName,
                    fontSize = 10.sp,
                    color = Color.Black,
                    fontFamily = FontFamily(Font(R.font.outfit_medium)),
                    //modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Image(
                    painter = painterResource(id = R.drawable.ic_check_mark),
                    contentDescription = "Verified",
                    modifier = Modifier.size(14.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Rating
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_round_star),
                    contentDescription = "Rating",
                    tint = Color(0xFFFFD700),
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = service.rating.toString(),
                    fontSize = 14.sp,
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
                    fontSize = 11.sp, // Slightly larger font size to match appearance
                    color = Color(0xFF8A8894), // Soft gray text color
                    fontFamily = FontFamily(Font(R.font.outfit_medium)),
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp) // Inner padding
                )
            }


            Spacer(modifier = Modifier.height(10.dp))

            // Inquire Now Button
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { /* Handle inquiry */ }
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
    val rating: Float,
    val serviceType: String,
    val iconRes: Int
)

@Preview(showBackground = true)
@Composable
fun PetServicesScreenPreview() {
    val navController = rememberNavController()
    PetServicesScreen(navController = navController)
}