package com.bussiness.slodoggiesapp.ui.screens.petowner.settingScreens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.model.petOwner.EventData
import com.bussiness.slodoggiesapp.navigation.Routes
import com.bussiness.slodoggiesapp.ui.component.businessProvider.ChoosePostTypeButton
import com.bussiness.slodoggiesapp.ui.component.petOwner.CommonBlueButton
import com.bussiness.slodoggiesapp.ui.component.petOwner.CommonTopAppBar

@Composable
fun MyEventsScreen(navController: NavController = rememberNavController()) {
    var selected by remember { mutableStateOf("My Events") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        CommonTopAppBar(
            title = "My Events",
            titleFontSize = 19.sp,
            onBackClick = { navController.popBackStack() },
            dividerColor = Color(0xFF258694),
        )
        Spacer(Modifier.height(15.dp))
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp, horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf("My Events", "Saved").forEach { label ->
                    ChoosePostTypeButton(
                        modifier = Modifier.weight(1f),
                        text = label,
                        isSelected = selected == label,
                        onClick = { selected = label }
                    )
                }
            }


            val myEvents = listOf(
                EventData(
                    id = 1,
                    title = "Event Title",
                    description = "Lorem ipsum dolor sit a...",
                    date = "May 25",
                    time = "4:00 PM",
                    duration = "30 Mins.",
                    location = "San Luis Obispo County",
                    hasImage = true,
                    buttonName = "View Community Chats"
                ),
                EventData(
                    id = 2,
                    title = "Event Title",
                    description = "Lorem ipsum dolor sit a...",
                    date = "May 25",
                    time = "4:00 PM",
                    duration = "30 Mins.",
                    location = "San Luis Obispo County",
                    hasImage = true,
                    buttonName = "View Community Chats"
                ),
                EventData(
                    id = 3,
                    title = "Event Title",
                    description = "Lorem ipsum dolor sit a...",
                    date = "May 25",
                    time = "4:00 PM",
                    duration = "30 Mins.",
                    location = "San Luis Obispo County",
                    hasImage = true,
                    buttonName = "View Community Chats"
                ),
                EventData(
                    id = 4,
                    title = "Event Title",
                    description = "Lorem ipsum dolor sit a...",
                    date = "May 25",
                    time = "4:00 PM",
                    duration = "30 Mins.",
                    location = "San Luis Obispo County",
                    hasImage = true,
                    buttonName = "View Community Chats"
                )
            )

            val savedEvents = listOf(
                EventData(
                    id = 5,
                    title = "Event Title",
                    description = "Lorem ipsum dolor sit a...",
                    date = "May 25",
                    time = "4:00 PM",
                    duration = "30 Mins.",
                    location = "San Luis Obispo County",
                    hasImage = true,
                    buttonName = "Join Community Chats"
                ),
                EventData(
                    id = 6,
                    title = "Event Title",
                    description = "Lorem ipsum dolor sit a...",
                    date = "May 25",
                    time = "4:00 PM",
                    duration = "30 Mins.",
                    location = "San Luis Obispo County",
                    hasImage = true,
                    buttonName = "Join Community Chats"
                ),
                EventData(
                    id = 7,
                    title = "Event Title",
                    description = "Lorem ipsum dolor sit a...",
                    date = "May 25",
                    time = "4:00 PM",
                    duration = "30 Mins.",
                    location = "San Luis Obispo County",
                    hasImage = true,
                    buttonName = "Join Community Chats"
                ),
                EventData(
                    id = 8,
                    title = "Event Title",
                    description = "Lorem ipsum dolor sit a...",
                    date = "May 25",
                    time = "4:00 PM",
                    duration = "30 Mins.",
                    location = "San Luis Obispo County",
                    hasImage = true,
                    buttonName = "Join Community Chats"
                )
            )
            val currentEvents = if (selected == "My Events") myEvents else savedEvents

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(
                    currentEvents) { event ->
                    EventCard(event = event,navController)
                }
            }

        }
    }
}

@Composable
fun EventCard(event: EventData,navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp)),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(Modifier.padding(10.dp)) {
            // Image section
            if (event.hasImage) {

                // Placeholder for dog image

                Image(
                    painter = painterResource(id = R.drawable.ic_dog_dummy_image),
                    contentDescription = "Image",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Fit
                )

            }

            // Date and Time Row
            Box (
                Modifier.fillMaxWidth()
            ) {

                Text(
                    text = event.title,
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.outfit_medium)),
                    color = Color.Black,
                    modifier = Modifier.align(Alignment.CenterStart)
                )
                Row(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.cal_ic),
                        contentDescription = "Date",
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = "${event.date},${event.time}",
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(R.font.outfit_regular)),
                        color = Color.Black
                    )

                }


            }

            Spacer(modifier = Modifier.height(4.dp))

            Box(Modifier.fillMaxWidth()) {
                Text(
                    text = event.description,
                    fontSize = 13.sp,
                    color = Color(0xFF949494),
                    maxLines = 1,
                    fontFamily = FontFamily(Font(R.font.outfit_regular)),
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.align(Alignment.CenterStart)
                )
                Row(Modifier.align(Alignment.CenterEnd)) {


                    Image(
                        painter = painterResource(id = R.drawable.time_ic),
                        contentDescription = "Date",
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = event.duration,
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(R.font.outfit_regular)),
                        color = Color.Black
                    )
                }

            }
            // Description

            Spacer(modifier = Modifier.height(8.dp))

            // Location Row
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.location_ic),
                    contentDescription = "Location",
                    modifier = Modifier
                        .width(20.dp)
                        .height(25.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = event.location,
                    fontSize = 13.sp,
                    fontFamily = FontFamily(Font(R.font.outfit_medium)),
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(16.dp))


            CommonBlueButton(
                text = event.buttonName,
                fontSize = 13.sp,
                onClick = { navController.navigate(Routes.PET_GROOMING_CHAT_SCREEN)},
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MyEventsScreenPreview() {
    MaterialTheme {
        MyEventsScreen()
    }
}