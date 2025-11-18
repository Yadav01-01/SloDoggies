package com.bussiness.slodoggiesapp.ui.screens.commonscreens.community

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
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.bussiness.slodoggiesapp.data.model.businessProvider.Community
import com.bussiness.slodoggiesapp.data.model.common.ChatMessage
import com.bussiness.slodoggiesapp.navigation.Routes
import com.bussiness.slodoggiesapp.ui.component.common.BottomMessageBar
import com.bussiness.slodoggiesapp.ui.component.common.CommunityHeader
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor
import com.bussiness.slodoggiesapp.viewModel.common.communityVM.CommunityChatViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Composable
fun CommunityChatScreen(
    navController: NavHostController,
    viewModel: CommunityChatViewModel = hiltViewModel()
) {
    val messages by viewModel.messages.collectAsState()
    val currentMessage by viewModel.currentMessage.collectAsState()
    var isNavigating by remember { mutableStateOf(false) }

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
            community = com.bussiness.slodoggiesapp.data.model.businessProvider.Community(
                id = "1",
                name = "Event Community 1",
                membersCount = 22,
                imageUrl = ""
            ),
            onBackClick = {
                if (!isNavigating) {
                    isNavigating = true
                    navController.popBackStack()
                }
            },
            onViewProfileClick = { navController.navigate(Routes.COMMUNITY_PROFILE_SCREEN) }
        )

        HorizontalDivider(thickness = 2.dp, color = PrimaryColor)

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .imePadding()
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
    messages: List<com.bussiness.slodoggiesapp.data.model.common.ChatMessage>,
    listState: LazyListState,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        state = listState,
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        var lastDate: String? = null

        items(messages) { message ->
            val currentDate = formatDate(message.timestamp)

            // Show date separator when new date starts
            if (currentDate != lastDate) {
                DateSeparator(dateText = currentDate)
                lastDate = currentDate
            }

            ChatBubble(message)
        }
    }
}



@Composable
fun ChatBubble(message: com.bussiness.slodoggiesapp.data.model.common.ChatMessage) {
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
    val maxBubbleWidth = LocalConfiguration.current.screenWidthDp.dp * 0.8f
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
                .padding(top = 4.dp, bottom = 4.dp)
                .clip(bubbleShape)
                .background(bubbleColor)
                .widthIn(
                    max = if (message.isUser)
                        LocalConfiguration.current.screenWidthDp.dp * 0.8f
                    else
                        LocalConfiguration.current.screenWidthDp.dp * 0.7f
                )
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 10.dp, vertical = 6.dp)
            ) {
                Box(
                    modifier = Modifier.wrapContentSize()
                ) {
                    Text(
                        text = message.text,
                        color = if (message.isUser) Color.White else Color.Black,
                        fontSize = 14.sp,
                        modifier = Modifier
                            .padding(end = 50.dp) // leave space for timestamp inside bubble
                    )
                }
            }
            Text(
                text = formatTime(message.timestamp),
                fontSize = 11.sp,
                color = if (message.isUser) Color(0xFFE0E0E0) else Color.Gray,
                modifier = Modifier
                    .padding(end = 6.dp)
                    .align(Alignment.BottomEnd)
            )

        }
    }
}

fun formatTime(timestamp: Long): String {
    val sdf = SimpleDateFormat("hh:mm a", Locale.getDefault())
    return sdf.format(Date(timestamp))
}

fun formatDate(timestamp: Long): String {
    val cal = Calendar.getInstance()
    val today = Calendar.getInstance()

    cal.timeInMillis = timestamp

    return when {
        cal.get(Calendar.YEAR) == today.get(Calendar.YEAR)
                && cal.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR) -> "Today"

        cal.get(Calendar.YEAR) == today.get(Calendar.YEAR)
                && cal.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR) - 1 -> "Yesterday"

        else -> SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date(timestamp))
    }
}

@Composable
fun DateSeparator(dateText: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .background(Color(0xFFDDE5E8), RoundedCornerShape(12.dp))
                .padding(horizontal = 12.dp, vertical = 4.dp)
        ) {
            Text(
                text = dateText,
                fontSize = 12.sp,
                color = Color.Black.copy(alpha = 0.7f)
            )
        }
    }
}











