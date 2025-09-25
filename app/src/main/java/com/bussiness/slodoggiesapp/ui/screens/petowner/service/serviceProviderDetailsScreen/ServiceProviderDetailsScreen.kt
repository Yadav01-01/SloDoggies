package com.bussiness.slodoggiesapp.ui.screens.petowner.service.serviceProviderDetailsScreen

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.navigation.Routes
import com.bussiness.slodoggiesapp.ui.component.businessProvider.HeadingTextWithIcon
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor
import com.bussiness.slodoggiesapp.viewModel.petOwner.servicesVM.ServiceDetailViewModel

@Composable
fun ServiceProviderDetailsScreen(navController: NavHostController,viewModel: ServiceDetailViewModel = hiltViewModel()) {
    var selectedOption by remember { mutableStateOf("Services") }
    val serviceDetail by viewModel.serviceDetail.collectAsState()
    Column(modifier = Modifier.fillMaxSize().background(color = Color.White)) {

        BackHandler {
            navController.navigate(Routes.PET_SERVICES_SCREEN) {
                popUpTo(Routes.PET_SERVICES_SCREEN) { inclusive = false }
                launchSingleTop = true
                restoreState = true
            }
        }


        HeadingTextWithIcon(textHeading = stringResource(R.string.services), onBackClick = {
            navController.navigate(Routes.PET_SERVICES_SCREEN) {
                popUpTo(Routes.PET_SERVICES_SCREEN) { inclusive = false }
                launchSingleTop = true       // Avoid duplicates
                restoreState = true          // Restore scroll/input state
            }
        })

        HorizontalDivider(thickness = 2.dp, color = PrimaryColor)

        Column(modifier = Modifier.weight(1f).background(Color.White).padding(horizontal = 15.dp)) {

            Spacer(modifier = Modifier.height(10.dp))

            ProviderDetails(
                serviceType = "Grooming",
                cardRating = "4",
                personImage = " "  ,
                providerName = "John Doe",
                userVerified = true,
                businessDescription = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad ",
                phoneNumber = "(805) 123 4567",
                website = "pawfectpets.com",
                address = "123 Pet Lane, San Luis Obispo, CA",
                onClickInquire = {navController.navigate(Routes.CHAT_SCREEN)}
            )

            Spacer(Modifier.height(20.dp))

            SwitchButton(
                selectedOption = selectedOption, // Pass the current state
                onOptionSelected = { newOption ->
                    selectedOption = newOption // Update the state when clicked
                }
            )
            when (selectedOption) {
                "Services" -> { ServicesContent() }
                "Rating & Reviews" -> { ReviewInterface() }
            }
        }
    }
}

@Composable
fun ProviderDetails(
    serviceType : String,
    cardRating : String,
    personImage : String,
    providerName : String,
    userVerified : Boolean,
    businessDescription: String,
    phoneNumber: String,
    website: String,
    address: String,
    onClickInquire: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(10.dp)) {

            // Paw Icon
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.paw_icon),
                    contentDescription = null,
                    modifier = Modifier
                        .height(50.dp)
                        .width(50.dp)
                        .align(Alignment.TopStart),

                    )
                Surface(
                    color = Color.White, // White background
                    shape = RoundedCornerShape(6.dp),
                    border = BorderStroke(1.dp, Color(0xFFCDCDCD)), // Light gray border
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(vertical = 8.dp)
                        .align(Alignment.TopEnd), // Outer spacing,
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .width(71.dp)
                            .height(25.dp)
                    ) {
                        Text(
                            text = serviceType,
                            fontSize = 10.sp,
                            color = Color(0xFF8A8894),
                            textAlign = TextAlign.Center,
                            fontFamily = FontFamily(Font(R.font.outfit_medium)),
                        )
                    }
                }

            }

            Spacer(modifier = Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Column(modifier = Modifier.align(Alignment.TopStart)) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "Pawfect Pet Care",
                            fontSize = 14.sp,
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
                            text = "$cardRating/5",
                            fontSize = 12.sp,
                            fontFamily = FontFamily(Font(R.font.outfit_regular)),
                            fontWeight = FontWeight.Medium,
                            color = Color.Black
                        )
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    modifier = Modifier
                        .width(121.dp).align(Alignment.TopEnd)
                        .clickable { onClickInquire() }
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
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Inquire now",
                        color = PrimaryColor,
                        fontFamily = FontFamily(Font(R.font.outfit_semibold)),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
            // Service Name


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp), // same vertical spacing for both
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = providerName,
                    fontSize = 14.sp,
                    color = Color.Black,
                    fontFamily = FontFamily(Font(R.font.outfit_medium)),
                    modifier = Modifier.weight(1f) // push Surface to the end
                )

                Surface(
                    color = PrimaryColor,
                    shape = RoundedCornerShape(5.dp),
                    modifier = Modifier.width(121.dp)
                ) {
                    Text(
                        text = stringResource(R.string.follow),
                        fontSize = 12.sp,
                        color = Color.White,
                        fontFamily = FontFamily(Font(R.font.outfit_medium)),
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                }
            }


            EnhancedExpandableInfoSpinner(phoneNumber = phoneNumber, website = website, address = address, businessDescription = businessDescription)
        }


    }

}


@Composable
fun EnhancedExpandableInfoSpinner(
    businessDescription: String,
    phoneNumber: String,
    website: String,
    address: String
) {
    var expanded by remember { mutableStateOf(false) }

    Surface(
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(1.dp, Color(0xFF258694)),
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                vertical = 10.dp,
                horizontal = 4.dp
            )
    ) {
        Column(
            modifier = Modifier
                .animateContentSize()
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = !expanded }
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = stringResource(R.string.additional_info),
                    color = PrimaryColor,
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.outfit_medium)),
                )

                Image(
                    painter = painterResource(id = if (expanded) R.drawable.ic_dropdown_open_icon else R.drawable.ic_dropdown_close_icon),
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                )
            }

            // Content
            if (expanded) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 12.dp, end = 16.dp, bottom = 16.dp)
                ) {
                    Text(
                        text = "$ Business Description:$businessDescription",
                        fontFamily = FontFamily(Font(R.font.outfit_medium)),
                        fontSize = 12.sp,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(), // Required for Alignment.CenterEnd to work
                        verticalAlignment = Alignment.CenterVertically // Optional: Vertically centers all children
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_phone_icon),
                            contentDescription = null,
                            modifier = Modifier.wrapContentSize()
                        )
                        Spacer(Modifier.width(4.dp))
                        Text(
                            text = stringResource(R.string.phone),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Black,
                            maxLines = 1,
                            fontFamily = FontFamily(Font(R.font.outfit_medium)),
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = phoneNumber,
                            color = Color.Black,
                            maxLines = 1,
                            fontFamily = FontFamily(Font(R.font.outfit_regular)),
                            fontSize = 12.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(), // Required for Alignment.CenterEnd to work
                        verticalAlignment = Alignment.CenterVertically // Optional: Vertically centers all children
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_web_icon),
                            contentDescription = null,
                            modifier = Modifier.wrapContentSize()
                        )
                        Spacer(Modifier.width(4.dp))
                        Text(
                            text = stringResource(R.string.website),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Black,
                            maxLines = 1,
                            fontFamily = FontFamily(Font(R.font.outfit_medium)),
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = website,
                            color = PrimaryColor,
                            maxLines = 1,
                            fontSize = 12.sp,
                            fontFamily = FontFamily(Font(R.font.outfit_regular)),
                            textDecoration =  TextDecoration.Underline
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(), // Required for Alignment.CenterEnd to work
                        verticalAlignment = Alignment.CenterVertically // Optional: Vertically centers all children
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_location_icon),
                            contentDescription = null,
                            modifier = Modifier.wrapContentSize()
                        )
                        Spacer(Modifier.width(4.dp))
                        Text(
                            text = stringResource(R.string.address),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Black,
                            maxLines = 1,
                            fontFamily = FontFamily(Font(R.font.outfit_medium)),
                            modifier = Modifier.weight(0.5f)
                        )
                        Text(
                            text = address,
                            color = Color.Black,
                            maxLines = 1,
                            fontSize = 12.sp,
                            fontFamily = FontFamily(Font(R.font.outfit_regular))
                        )
                    }
                }
            }
        }
    }
}

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun SwitchButton(
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val options = listOf("Services", "Rating & Reviews")

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(
                color = Color(0xFFE5EFF2),
                shape = RoundedCornerShape(12.dp)
            )

    ) {
        // Animated indicator
        val animatedOffset by animateFloatAsState(
            targetValue = if (selectedOption == "Services") 0f else 1f,
            animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing),
            label = "switch_animation"
        )

        BoxWithConstraints {
            val containerWidth = maxWidth
            val indicatorWidth = (containerWidth - 4.dp) / 2

            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(indicatorWidth)
                    .offset(x = animatedOffset * indicatorWidth)
                    .padding(vertical = 10.dp, horizontal = 10.dp)
                    .background(
                        color = Color(0xFF258694),
                        shape = RoundedCornerShape(8.dp)
                    )

            )
        }


        // Option buttons
        Row(
            modifier = Modifier.fillMaxSize()
        ) {
            options.forEachIndexed { index, option ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ) {
                            onOptionSelected(option)
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = option,
                        color = if (selectedOption == option) Color.White else Color.Black,
                        fontSize = 12.sp,
                        fontFamily = if (selectedOption == option) FontFamily(Font(R.font.outfit_medium)) else FontFamily(Font(R.font.outfit_regular)),
                    )
                }
            }
        }
    }

}



@Preview(showBackground = true)
@Composable
fun ServiceProviderDetailsScreenPreview() {
    val navController = rememberNavController()
    ServiceProviderDetailsScreen(navController = navController)
}

