package com.bussiness.slodoggiesapp.ui.screens.petowner.serviceProviderDetailsScreen

import android.annotation.SuppressLint
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Surface
import androidx.compose.material.Text
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.ui.component.petOwner.CommonTopAppBar


@Composable
fun ServiceProviderDetailsScreen(navController: NavHostController) {
    var selectedOption by remember { mutableStateOf("Services") }
    Column(modifier = Modifier.fillMaxSize()) {
        CommonTopAppBar(
            title = "Services",
            onBackClick = { navController.popBackStack() },
            dividerColor = Color(0xFF258694),
        )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
            )
            {
                Spacer(modifier = Modifier.height(20.dp))
                ProviderDetails()

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    SwitchButton(
                        selectedOption = selectedOption, // Pass the current state
                        onOptionSelected = { newOption ->
                            selectedOption = newOption // Update the state when clicked
                        }
                    )
                    when (selectedOption) {
                        "Services" -> {
                            ServicesContent()
                        }

                        "Rating & Reviews" -> {
                            ReviewInterface()
                        }
                    }

                }
            }
    }
}

@Composable
fun ProviderDetails() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(10.dp)
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
                    Text(
                        text = "Grooming",
                        fontSize = 9.sp, // Slightly larger font size to match appearance
                        color = Color(0xFF8A8894), // Soft gray text color
                        fontFamily = FontFamily(Font(R.font.outfit_medium)),
                        modifier = Modifier.padding(
                            horizontal = 6.dp,
                            vertical = 4.dp
                        ) // Inner padding
                    )
                }

            }


            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Column(modifier = Modifier.align(Alignment.TopStart)) {
                    Text(
                        text = "Pawfect Pet Care",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        maxLines = 1,
                        fontFamily = FontFamily(Font(R.font.outfit_medium)),
                        overflow = TextOverflow.Ellipsis
                    )

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
                            text = "4.8/5",
                            fontSize = 14.sp,
                            fontFamily = FontFamily(Font(R.font.outfit_regular)),
                            fontWeight = FontWeight.Medium,
                            color = Color.Black
                        )
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))

                Surface(
                    shape = RoundedCornerShape(6.dp),
                    border = BorderStroke(1.dp, Color(0xFF258694)),
                    modifier = Modifier
                        .width(130.dp)
                        .padding(
                            vertical = 10.dp,
                            horizontal = 4.dp
                        )
                        .align(Alignment.TopEnd) // Optional: add padding if needed
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.padding(
                            horizontal = 14.dp,
                            vertical = 8.dp
                        ) // Add internal padding
                    ) {
                        // Chat bubble icon
                        Icon(
                            painter = painterResource(id = R.drawable.ic_inform_checked),
                            contentDescription = null,
                            tint = Color(0xFF0891B2),
                            modifier = Modifier.size(16.dp)
                        )

                        Text(
                            text = "Inquire now",
                            color = Color(0xFF0891B2),
                            fontSize = 12.sp,
                            fontFamily = FontFamily(Font(R.font.outfit_semibold)),
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
            // Service Name


            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .align(Alignment.CenterStart),

                    ) {
                    Image(
                        painter = painterResource(id = R.drawable.dummy_person_image2),
                        contentDescription = "Person",
                        modifier = Modifier
                            .size(26.dp)
                            .clip(CircleShape)

                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = "Provider Name",
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
                Surface(
                    color = Color(0xFF258694), // White background
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(1.dp, Color(0xFFCDCDCD)), // Light gray border
                    modifier = Modifier
                        .width(130.dp)
                        .padding(vertical = 10.dp, horizontal = 4.dp)
                        .align(Alignment.CenterEnd) // Outer spacing
                ) {

                    Text(
                        text = "Follow",
                        fontSize = 12.sp,
                        color = Color(0xFFFFFFFF),
                        fontFamily = FontFamily(Font(R.font.outfit_medium)),
                        textAlign = TextAlign.Center, // This aligns the text within the Text composable
                        modifier = Modifier
                            .fillMaxWidth() // Make the text take full width
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                }
            }

            EnhancedExpandableInfoSpinner()
        }


    }

}


@Composable
fun EnhancedExpandableInfoSpinner() {
    var expanded by remember { mutableStateOf(false) }

    Surface(
        shape = RoundedCornerShape(6.dp),
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
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = !expanded }
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Additional Info.",
                    color = Color(0xFF0891B2),
                    fontSize = 15.sp,
                    fontFamily = FontFamily(Font(R.font.outfit_medium)),
                )

                Image(
                    painter = painterResource(id = if (expanded) R.drawable.ic_dropdown_open_icon else R.drawable.ic_dropdown_close_icon),
                    contentDescription = null,
                    //  tint = Color(0xFFFF8C00),
                    modifier = Modifier
                        .height(25.dp)
                        .width(25.dp)
                )
            }

            // Content
            if (expanded) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                ) {
                    Text(
                        text = "Business Description: Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad",
                        fontFamily = FontFamily(Font(R.font.outfit_regular)),
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(), // Required for Alignment.CenterEnd to work
                        verticalAlignment = Alignment.CenterVertically // Optional: Vertically centers all children
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_phone_icon),
                            contentDescription = null,
                            modifier = Modifier
                                .height(25.dp)
                                .width(25.dp)
                        )
                        Text(
                            text = "Phone:",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            maxLines = 1,
                            fontFamily = FontFamily(Font(R.font.outfit_medium)),
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = "(805) 123 4567",
                            color = Color.Black,
                            maxLines = 1,
                            fontFamily = FontFamily(Font(R.font.outfit_regular)),
                            // Aligns to the end (right) of the Row
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
                            modifier = Modifier
                                .height(25.dp)
                                .width(25.dp)
                        )
                        Text(
                            text = "Website:",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            maxLines = 1,
                            fontFamily = FontFamily(Font(R.font.outfit_medium)),
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = "pawfectpets.com",

                            color = Color.Black,
                            maxLines = 1,
                            fontFamily = FontFamily(Font(R.font.outfit_regular))
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
                            modifier = Modifier
                                .height(25.dp)
                                .width(25.dp)
                        )
                        Text(
                            text = "Address:",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            maxLines = 1,
                            fontFamily = FontFamily(Font(R.font.outfit_medium)),
                            modifier = Modifier.weight(0.5f)
                        )
                        Text(
                            text = "123 Pet Lane, San Luis Obispo, CA",

                            color = Color.Black,
                            maxLines = 1,
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
            .padding(horizontal = 15.dp, vertical = 4.dp)
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
            val indicatorWidth = (containerWidth - 8.dp) / 2

            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(indicatorWidth)
                    .offset(x = animatedOffset * indicatorWidth)
                    .padding(vertical = 10.dp, horizontal = 5.dp)
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
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
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

