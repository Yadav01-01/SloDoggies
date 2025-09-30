package com.bussiness.slodoggiesapp.ui.screens.petowner.event

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.bussiness.slodoggiesapp.model.businessProvider.Event
import com.bussiness.slodoggiesapp.navigation.Routes
import com.bussiness.slodoggiesapp.ui.component.businessProvider.ChoosePostTypeButton
import com.bussiness.slodoggiesapp.ui.component.businessProvider.HeadingTextWithIcon
import com.bussiness.slodoggiesapp.ui.component.common.EventCard
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor

@Composable
fun EventScreen(navController: NavHostController) {

    var selectedOption by remember { mutableStateOf("My Events") }

    val sampleEvents = listOf(
        Event(
            id = "1",
            imageUrl = "https://via.placeholder.com/600x400.png?text=Dog+Event+1",
            title = "Puppy Party",
            description = "Bring your puppy in a party ",
            dateTime = "05- 25-2025 4:00 PM",
            duration = "45 Mins.",
            location = "Los Angeles County",
        )
    )

    val sampleEvents2 = listOf(
        Event(
            id = "2",
            imageUrl = "https://via.placeholder.com/600x400.png?text=Dog+Event+2",
            title = "Adoption Drive",
            description = "Meet adoptable dogs",
            dateTime = "05- 25-2025 4:00 PM",
            duration = "60 Mins.",
            location = "San Diego County"
        ),
        Event(
            id = "3",
            imageUrl = "https://via.placeholder.com/600x400.png?text=Dog+Event+3",
            title = "Training Session",
            description = "Learn tricks with",
            dateTime = "05- 25-2025 4:00 PM",
            duration = "90 Mins.",
            location = "Orange County"
        )
    )

    val eventsToDisplay = if (selectedOption == "My Events") sampleEvents else sampleEvents2

    BackHandler {
        navController.navigate(Routes.HOME_SCREEN){
            launchSingleTop = true
            popUpTo(Routes.HOME_SCREEN){
                inclusive = true
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        HeadingTextWithIcon(
            textHeading = "My Events",
            onBackClick = { navController.navigate(Routes.HOME_SCREEN){
                launchSingleTop = true
                popUpTo(Routes.HOME_SCREEN){
                    inclusive = true
                }
            }}
        )

        HorizontalDivider(thickness = 2.dp, color = PrimaryColor)

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(horizontal = 8.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp, horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    listOf("My Events", "Saved").forEach { label ->
                        ChoosePostTypeButton(
                            modifier = Modifier.weight(1f),
                            text = label,
                            isSelected = selectedOption == label,
                            onClick = { selectedOption = label }
                        )
                    }
                }
            }

            items(eventsToDisplay) { event ->
                EventCard(event = event,selectedOption) { clickedEvent ->
                    navController.navigate(Routes.COMMUNITY_CHAT_SCREEN)
                }
            }
        }
    }
}
