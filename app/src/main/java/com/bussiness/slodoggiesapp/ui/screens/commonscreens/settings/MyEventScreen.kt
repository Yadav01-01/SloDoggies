package com.bussiness.slodoggiesapp.ui.screens.commonscreens.settings

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import com.bussiness.slodoggiesapp.data.model.businessProvider.Event
import com.bussiness.slodoggiesapp.navigation.Routes
import com.bussiness.slodoggiesapp.ui.component.businessProvider.HeadingTextWithIcon
import com.bussiness.slodoggiesapp.ui.component.common.EventCard
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor

@Composable
fun MyEventScreen(navController: NavHostController) {
    var isNavigating by remember { mutableStateOf(false) }

    BackHandler {
        if (!isNavigating) {
            isNavigating = true
            navController.popBackStack()
        }
    }
    Column(modifier = Modifier.fillMaxSize().background(Color.White)) {

        val sampleEvents = listOf(
            com.bussiness.slodoggiesapp.data.model.businessProvider.Event(
                id = "1",
                imageUrl = "https://via.placeholder.com/600x400.png?text=Dog+Event+1",
                title = "Puppy Party",
                description = "Bring your puppy to play",
                dateTime = "05- 25-2025 4:00 PM",
                duration = "45 Mins.",
                location = "Los Angeles County",
                buttonText = "Join Chat"
            ),
            com.bussiness.slodoggiesapp.data.model.businessProvider.Event(
                id = "2",
                imageUrl = "https://via.placeholder.com/600x400.png?text=Dog+Event+2",
                title = "Adoption Drive",
                description = "Meet adoptable dogs",
                dateTime = "05- 25-2025 4:00 PM",
                duration = "60 Mins.",
                location = "San Diego County",
                buttonText = "Community Chat"
            ), com.bussiness.slodoggiesapp.data.model.businessProvider.Event(
                id = "2",
                imageUrl = "https://via.placeholder.com/600x400.png?text=Dog+Event+2",
                title = "Adoption Drive",
                description = "Meet adoptable dogs",
                dateTime = "05- 25-2025 4:00 PM",
                duration = "60 Mins.",
                location = "San Diego County",
                buttonText = "Community Chat"
            )
        )

        HeadingTextWithIcon(textHeading = "My Events", onBackClick = {  if (!isNavigating) {
            isNavigating = true
            navController.popBackStack()
        } })

        HorizontalDivider(thickness = 2.dp, color = PrimaryColor)

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(horizontal = 8.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            items(sampleEvents) { event ->
                EventCard(event = event, selectedOption = "") { clickedEvent ->
                     navController.navigate(Routes.COMMUNITY_CHAT_SCREEN)
                }
            }
        }
    }

}