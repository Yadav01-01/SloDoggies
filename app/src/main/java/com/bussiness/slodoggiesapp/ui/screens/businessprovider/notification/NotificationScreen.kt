package com.bussiness.slodoggiesapp.ui.screens.businessprovider.notification

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.bussiness.slodoggiesapp.model.businessProvider.NotificationData
import com.bussiness.slodoggiesapp.ui.component.businessProvider.HeadingTextWithIcon
import com.bussiness.slodoggiesapp.ui.component.businessProvider.NotificationItem
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor

@Composable
fun NotificationScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        HeadingTextWithIcon(
            textHeading = "Notifications",
            onBackClick = { navController.popBackStack() }
        )

        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp)
                .background(PrimaryColor)
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(horizontal = 5.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            // Today Section
            item {
                Text(
                    "Today",
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    style = MaterialTheme.typography.titleMedium
                )

                HorizontalDivider(modifier = Modifier.fillMaxWidth().height(1.dp).background(Color.Black))
            }

            // Notifications
            items(dummyNotifications) { notification ->
                NotificationItem(
                    profileImageUrl = notification.profileImageUrl ?: "",
                    username = notification.username,
                    message = notification.message,
                    time = notification.time,
                    previewImageUrl = notification.previewImageUrl,
                    type = notification.type,
                    onJoinChatClick = {
                        // Handle join chat button click
                    }
                )
            }
        }
    }
}

// Dummy data
val dummyNotifications = listOf(
    NotificationData(
        username = "Event Saved",
        message = "You’ve marked this event as Interested — we’ll keep you updated! \uD83D\uDC3E",
        time = "Just Now",
        previewImageUrl = null,
        type = "event"
    ),
    NotificationData(
        profileImageUrl = "https://example.com/user1.jpg",
        username = "_username",
        message = "⭐️⭐️⭐️⭐️⭐️ \"Great service!\"",
        time = "17 Min.",
        previewImageUrl = null,
        type = "post"
    ),
    NotificationData(
        profileImageUrl = "https://example.com/user2.jpg",
        username = "_username",
        message = "liked your post.",
        time = "17 Min.",
        previewImageUrl = "https://example.com/post1.jpg",
        type = "post"
    ),
    NotificationData(
        profileImageUrl = "https://example.com/user3.jpg",
        username = "_username",
        message = "started following you.",
        time = "17 Min.",
        previewImageUrl = null,
        type = "post"
    )
)

