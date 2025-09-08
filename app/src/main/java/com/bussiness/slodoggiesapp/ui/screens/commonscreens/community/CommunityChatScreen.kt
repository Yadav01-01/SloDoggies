package com.bussiness.slodoggiesapp.ui.screens.commonscreens.community

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.model.businessProvider.Community
import com.bussiness.slodoggiesapp.model.common.ChatMessage
import com.bussiness.slodoggiesapp.navigation.Routes
import com.bussiness.slodoggiesapp.ui.component.common.BottomMessageBar
import com.bussiness.slodoggiesapp.ui.component.common.CommunityHeader
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor
import com.bussiness.slodoggiesapp.viewModel.common.communityVM.CommunityChatViewModel

@Composable
fun CommunityChatScreen(
    navController: NavHostController,
    viewModel: CommunityChatViewModel = hiltViewModel()
) {
    val messages by viewModel.messages.collectAsState()
    val currentMessage by viewModel.currentMessage.collectAsState()

    val listState = rememberLazyListState() // LazyColumn scroll state

    // Scroll to bottom when messages change
    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.size - 1)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)

    ) {
        CommunityHeader(
            community = Community(id = "1", name = "Event Community 1", membersCount = 22, imageUrl = ""),
            onBackClick = { navController.popBackStack() },
            onViewProfileClick = { navController.navigate(Routes.COMMUNITY_PROFILE_SCREEN) }
        )

        HorizontalDivider(thickness = 1.5.dp, color = PrimaryColor)

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            CommunityChatSection(
                messages = messages,
                listState = listState,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 75.dp)
            )


            BottomMessageBar(
                modifier = Modifier.align(Alignment.BottomCenter),
                viewModel = viewModel
            )
        }
    }
}


@Composable
fun CommunityChatSection(
    messages: List<ChatMessage>,
    listState: LazyListState,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        state = listState,
        modifier = modifier,
        verticalArrangement = Arrangement.Bottom
    ) {
        items(messages) { message ->
            ChatBubble(message)
        }
    }
}


@Composable
fun ChatBubble(message: ChatMessage) {
    val bubbleColor = if (message.isUser) PrimaryColor else Color(0xFFE5EFF2)
    val alignment = if (message.isUser) Arrangement.End else Arrangement.Start

    val bubbleShape = if (message.isUser) {
        RoundedCornerShape(
            topStart = 16.dp,
            topEnd = 16.dp,
            bottomStart = 16.dp,
            bottomEnd = 0.dp
        )
    } else {
        RoundedCornerShape(
            topStart = 16.dp,
            topEnd = 16.dp,
            bottomStart = 0.dp,
            bottomEnd = 16.dp
        )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        horizontalArrangement = alignment,
        verticalAlignment = Alignment.Top
    ) {
        if (!message.isUser) {
            Image(
                painter = painterResource(id = R.drawable.dummy_person_image2),
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(Color.Gray)
                    .align(Alignment.Bottom)
            )
            Spacer(modifier = Modifier.width(8.dp))
        }

        Box(
            modifier = Modifier
                .clip(bubbleShape)
                .background(bubbleColor)
                .widthIn(max = if (message.isUser) LocalConfiguration.current.screenWidthDp.dp * 0.8f else LocalConfiguration.current.screenWidthDp.dp * 0.7f)
        ) {
            Text(
                text = message.text,
                modifier = Modifier.padding(12.dp),
                color = if (message.isUser) Color.White else Color.Black,
                fontSize = 14.sp
            )
        }
    }
}










