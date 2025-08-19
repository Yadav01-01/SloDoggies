package com.bussiness.slodoggiesapp.ui.screens.commonscreens.community

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bussiness.slodoggiesapp.model.businessProvider.Community
import com.bussiness.slodoggiesapp.model.common.ChatMessage
import com.bussiness.slodoggiesapp.navigation.Routes
import com.bussiness.slodoggiesapp.ui.component.common.BottomMessageBar
import com.bussiness.slodoggiesapp.ui.component.common.ChatBubble
import com.bussiness.slodoggiesapp.ui.component.common.CommunityHeader
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor

@Composable
fun CommunityChatScreen(
    navController: NavHostController,
    initialMessages: List<ChatMessage> = emptyList()
) {
    var currentMessage by remember { mutableStateOf("") }
    val messages = remember { mutableStateListOf<ChatMessage>().apply { addAll(initialMessages) } }

    // Create your Community object here (static for now)
    val community = Community(
        id = "1",
        name = "Event Community 1",
        membersCount = 22,
        imageUrl = " "
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Header
        CommunityHeader(
            community = community,
            onBackClick = { navController.popBackStack() },
            onViewProfileClick = { navController.navigate(Routes.COMMUNITY_PROFILE_SCREEN) }
        )

        // Divider
        HorizontalDivider(thickness = 1.5.dp, color = PrimaryColor)

        Spacer(modifier = Modifier.height(10.dp))

        // Chat messages list
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(messages) { message ->
                ChatBubble(message)
            }

        }

        // Bottom input bar
        BottomMessageBar(
            message = currentMessage,
            onMessageChange = { currentMessage = it },
            onAttachmentClick = { },
            onSendClick = {
                if (currentMessage.isNotBlank()) {
                    val newMessage = ChatMessage(
                        text = currentMessage.trim(),
                        isUser = true
                    )
                    messages.add(newMessage) // add to local list
                    currentMessage = "" // clear input
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CommunityChatScreenPreview() {
    val navController = rememberNavController()
    CommunityChatScreen(
        navController = navController,
        initialMessages = listOf(
            ChatMessage(
                "Hello! I am interested in your grooming service.",
                isUser = false
            ),
            ChatMessage(
                "Hi! Thank you for reaching out. How can I assist you today?",
                isUser = true
            )
        )
    )
}

