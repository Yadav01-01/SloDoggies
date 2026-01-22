package com.bussiness.slodoggiesapp.ui.screens.commonscreens.message

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.data.model.common.AllChatListData
import com.bussiness.slodoggiesapp.data.model.main.UserType
import com.bussiness.slodoggiesapp.navigation.Routes
import com.bussiness.slodoggiesapp.ui.component.businessProvider.HeadingTextWithIcon
import com.bussiness.slodoggiesapp.ui.component.businessProvider.SearchBar
import com.bussiness.slodoggiesapp.ui.component.common.MessageItem
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor
import com.bussiness.slodoggiesapp.ui.theme.TextGrey
import com.bussiness.slodoggiesapp.util.AppConstant
import com.bussiness.slodoggiesapp.util.SessionManager
import com.bussiness.slodoggiesapp.viewModel.common.MessageViewModel
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Composable
fun MessageScreen(
    navController: NavHostController,
    viewModel: MessageViewModel = hiltViewModel()
) {

    val allChats  by viewModel.uiState.collectAsState()
    val query by viewModel.query.collectAsState()
    val sessionManager = SessionManager.getInstance(LocalContext.current)
    var canNavigate by remember { mutableStateOf(true) }

   // val uiState by viewModel.uiState.collectAsState()

    // Filtered list based on search query
    val filteredChats = allChats.data?.filter {
        it.user_name.orEmpty().contains(query, ignoreCase = true) ||
                it.description.orEmpty().contains(query, ignoreCase = true)
    } ?: emptyList()

    BackHandler(enabled = true) {
        if (canNavigate) {
            canNavigate = false
            navController.navigate(Routes.HOME_SCREEN) {
                launchSingleTop = true
                popUpTo(Routes.HOME_SCREEN) {
                    inclusive = true
                }
            }
        }
    }


/*    // Filtered list based on search query
    val filteredMessages = allMessages.filter {
        it.username.contains(query, ignoreCase = true) ||
                it.description.contains(query, ignoreCase = true)
    }*/


    Box(modifier = Modifier.fillMaxSize().background(Color.White)) {
        Column(modifier = Modifier.fillMaxSize()) {
            HeadingTextWithIcon(
                textHeading = "Messages",
                onBackClick = {  if (canNavigate) {
                    canNavigate = false
                    navController.navigate(Routes.HOME_SCREEN) {
                        launchSingleTop = true
                        popUpTo(Routes.HOME_SCREEN) {
                            inclusive = false
                        }
                    }
                } }
            )

            HorizontalDivider(thickness = 2.dp, color = PrimaryColor)

            Spacer(Modifier.height(10.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(horizontal = 5.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                item {
                        SearchBar(
                            query = query,
                            onQueryChange = { viewModel.updateQuery(it) },
                            placeholder = "Search"
                        )
                        Spacer(Modifier.height(10.dp))
                }
            /*    uiState.data?.let { chatList ->
                items(chatList) { message ->
                    MessageItem1(
                        message = message,
                        onItemClick = { navController.navigate(Routes.CHAT_SCREEN) }
                    )
                }
                }*/
                if (filteredChats.isEmpty()) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "No messages yet",
                                color = TextGrey
                            )
                        }
                    }
                } else {
                    items(filteredChats) { chat ->
                        MessageItem1(
                            message = chat,
                            onItemClick = {
                                // Navigate to chat screen with chat ID
                                if (chat.type.equals("private")){
                                    val singleUser = AppConstant.SINGLE
                                    val encodedImage = URLEncoder.encode(chat.profile_image ?: "", "UTF-8")
                                    val encodedName = URLEncoder.encode(chat.user_name ?: "", "UTF-8")
                                    navController.navigate(
                                        "${Routes.CHAT_SCREEN_FUNCTIONAL}/${chat.user_id}/${encodedImage?: ""}/${encodedName}/${singleUser}"
                                    )
                                 /*   navController.navigate(
                                        "${Routes.CHAT_SCREEN}/${chat.chat_id}"
                                    )*/
                                }else{
                                    val receiverId = chat.event_id ?: ""
                                    val chatId = chat.chat_id ?: ""
                                    val totalMembers = chat.total_members ?: ""
                                    /*
                                    postItem.media?.imageUrl
                                     */
                                    Log.d("CommunityChatScreen", "${chat.event_image ?: ""}")
                                    val receiverImage = URLEncoder.encode(
                                        chat.event_image ?: "",
                                        StandardCharsets.UTF_8.toString()
                                    )
                                    val receiverName = URLEncoder.encode(
                                        chat.event_name ?: "",
                                        StandardCharsets.UTF_8.toString()
                                    )
                                    val type = "event"
                                    navController.navigate("${Routes.COMMUNITY_CHAT_SCREEN}/$receiverId/$receiverImage/$receiverName/$chatId/$type/$totalMembers")
                                }

                            }
                        )
                    }
                }
            }
        }

        // FAB fixed at bottom-right, outside the LazyColumn
        if (sessionManager.getUserType() == UserType.Professional) {
            NewMessageButton(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 10.dp, end = 10.dp),
                onClick = { navController.navigate(Routes.NEW_MESSAGE_SCREEN) }
            )
        }
    }
}

/*
@Composable
fun MessageScreen(
    navController: NavHostController,
    viewModel: MessageViewModel = hiltViewModel()
) {
    val allChats by viewModel.uiState.collectAsState()
    val query by viewModel.query.collectAsState()
    val sessionManager = SessionManager.getInstance(LocalContext.current)
    var canNavigate by remember { mutableStateOf(true) }

    // Filtered list based on search query
    val filteredChats = allChats.data?.filter {
        it.user_name.orEmpty().contains(query, ignoreCase = true) ||
                it.description.orEmpty().contains(query, ignoreCase = true)
    } ?: emptyList()

    Box(modifier = Modifier.fillMaxSize().background(Color.White)) {
        Column(modifier = Modifier.fillMaxSize()) {
            HeadingTextWithIcon(
                textHeading = "Messages",
                onBackClick = {
                    if (canNavigate) {
                        canNavigate = false
                        navController.navigate(Routes.HOME_SCREEN) {
                            launchSingleTop = true
                            popUpTo(Routes.HOME_SCREEN) {
                                inclusive = false
                            }
                        }
                    }
                }
            )

            HorizontalDivider(thickness = 2.dp, color = PrimaryColor)
            Spacer(Modifier.height(10.dp))

            if (allChats.isLoading) {
                // Show loading indicator
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    // Add your loading indicator here
                    Text(text = "Loading chats...")
                }
            } else if (allChats.error != null) {
                // Show error message
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Error: ${allChats.error}")
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                        .padding(horizontal = 5.dp),
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    item {
                        SearchBar(
                            query = query,
                            onQueryChange = { viewModel.updateQuery(it) },
                            placeholder = "Search"
                        )
                        Spacer(Modifier.height(10.dp))
                    }

                    if (filteredChats.isEmpty()) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "No messages yet",
                                    color = TextGrey
                                )
                            }
                        }
                    } else {
                        items(filteredChats) { chat ->
                            MessageItem1(
                                message = chat,
                                onItemClick = {
                                    // Navigate to chat screen with chat ID
                                    navController.navigate(
                                        "${Routes.CHAT_SCREEN}/${chat.chat_id}"
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }

        // FAB for new message
        if (sessionManager.getUserType() == UserType.Professional) {
            NewMessageButton(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 10.dp, end = 10.dp),
                onClick = { navController.navigate(Routes.NEW_MESSAGE_SCREEN) }
            )
        }
    }
}
*/


@Composable
fun NewMessageButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    FloatingActionButton(
        onClick = onClick,
        modifier = modifier.size(51.dp),
        containerColor = Color.White,
        contentColor = Color.Unspecified,
    ) {
        Image(
            painter = painterResource(R.drawable.new_msg_ic),
            contentDescription = "New Message",
            modifier = Modifier.fillMaxSize(),
        )
    }
}


@Composable
fun MessageItem1(message: AllChatListData, onItemClick: () -> Unit) {
    val displayMessage = if (message.last_message?.isNotEmpty() == true) {
        message.last_message
    } else {
        message.description ?: ""
    }

    val displayTime = if (message.time?.isNotEmpty() == true) {
        message.time
    } else {
        // Format timestamp if available
        message.last_message_time?.let { timestamp ->
            formatTimestamp(timestamp)
        } ?: ""
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 10.dp).clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) { onItemClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = if (message.type.equals("group")) message.event_image  else message.profile_image,
            contentDescription = "Profile",
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(Color.LightGray),
            placeholder = painterResource(id = R.drawable.grp_ic_error),
            error = painterResource(id = R.drawable.grp_ic_error)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column(modifier = Modifier.weight(1f)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (message.type.equals("group")) message.event_name?:"" else message.user_name?: "",
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
                    fontFamily = FontFamily(Font(R.font.outfit_medium)),
                    fontSize = 16.sp,
                    color = Color.Black,
                    maxLines = 1
                )

                Text(
                    text = message.time?:"",
                    style = MaterialTheme.typography.bodySmall,
                    fontFamily = FontFamily(Font(R.font.outfit_regular)),
                    fontSize = 10.sp,
                    color = TextGrey,
                    maxLines = 1
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text = message.last_message?:"",
                    style = MaterialTheme.typography.bodySmall,
                    fontFamily = FontFamily(Font(R.font.outfit_regular)),
                    fontSize = 14.sp,
                    color = TextGrey,
                    maxLines = 1
                )
                Box(
                    modifier = Modifier
                        .size(18.dp)
                        .clip(CircleShape)
                        .background( PrimaryColor),
                    contentAlignment = Alignment.Center
                ) {
                   /* message.unread_count?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontFamily = FontFamily(Font(R.font.outfit_regular)),
                                fontSize = 9.sp,
                                color = Color.White
                            ),
                            maxLines = 1,
                            textAlign = TextAlign.Center
                        )
                    }*/
                    val unreadCount = message.unread_count ?: 0
                    Log.d("unreadCount","$unreadCount")
//                    message.unread_count?.let { count ->
                        if (unreadCount > 0) {
                            Box(
                                modifier = Modifier
                                    .size(18.dp)
                                    .clip(CircleShape)
                                    .background(PrimaryColor),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = if (unreadCount  > 99) "99+" else unreadCount .toString(),
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        fontFamily = FontFamily(Font(R.font.outfit_regular)),
                                        fontSize = 9.sp,
                                        color = Color.White
                                    ),
                                    maxLines = 1,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                   // }
                }
            }
        }
    }
    HorizontalDivider(thickness = 1.dp, color = Color(0xFFF4F4F4))
}


// Helper function to format timestamp
fun formatTimestamp(timestamp: Long): String {
    val now = System.currentTimeMillis()
    val diff = now - timestamp

    return when {
        diff < 60000 -> "Just now"
        diff < 3600000 -> "${diff / 60000} min ago"
        diff < 86400000 -> "${diff / 3600000} hr ago"
        diff < 172800000 -> "Yesterday"
        else -> {
            val sdf = SimpleDateFormat("dd/MM/yy", Locale.getDefault())
            sdf.format(Date(timestamp))
        }
    }
}

