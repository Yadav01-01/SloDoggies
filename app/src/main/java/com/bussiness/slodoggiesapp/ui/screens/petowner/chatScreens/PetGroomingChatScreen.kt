package com.bussiness.slodoggiesapp.ui.screens.petowner.chatScreens

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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.model.petOwner.ChatMessage
import com.bussiness.slodoggiesapp.navigation.Routes
import com.bussiness.slodoggiesapp.ui.component.petOwner.PetGroomingChatScreenHeader


@Composable
fun PetGroomingChatScreen(navController: NavController = rememberNavController()) {
    var messageText by remember { mutableStateOf("") }

    val messages = listOf(
        ChatMessage("1", "Hi! Thank you for reaching out. How can I assist you today?", false, R.drawable.dummy_baby_pic),
        ChatMessage("2", "Hello! I am interested in your grooming service.", true),
        ChatMessage("3", "Sure! We have basic, premium, and deluxe grooming packages. Would you like details?", false, R.drawable.dummy_person_image3),
        ChatMessage("4", "Can you tell me about the available packages?", true),
        ChatMessage("5", "Sure! We have basic, premium, and deluxe grooming packages. Would you like details?", false, R.drawable.dummy_person_image2)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFDFD))
    ) {


        PetGroomingChatScreenHeader(
            onBackClick= {
                navController.popBackStack()
            },
            onMenuClick={
                navController.navigate(Routes.PET_EVENT_COMMUNITY_SCREEN)
            },
            communityName = "Event Community 1",
            memberCount = "20 members"
        )

       Divider( color = Color(0xFFE4E4E4D4), thickness = 1.dp)

        // Messages
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            items(messages) { message ->
                ChatMessageItem(message = message)
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .background(
                        color = Color(0xFFE8F4F8), // Light blue-gray background
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                       painter = painterResource(id= R.drawable.ic_paper_clip), // Paper clip icon
                        contentDescription = "Attach",
                        tint = Color(0xFF258694), // Gray color
                        modifier = Modifier.size(20.dp)
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    BasicTextField(
                        value = messageText,
                        onValueChange = { messageText = it },
                        modifier = Modifier.weight(1f),
                        singleLine = true,
                        textStyle = TextStyle(
                            fontSize = 16.sp,
                            color = Color.Black
                        ),
                        decorationBox = { innerTextField ->
                            if (messageText.isEmpty()) {
                                Text(
                                    text = "Type something",
                                    color = Color(0xFF949494),
                                    fontFamily = FontFamily(Font(R.font.outfit_regular)),
                                    fontSize = 16.sp
                                )
                            }
                            innerTextField()
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            FloatingActionButton(
                onClick = {
                    messageText = ""
                },
                modifier = Modifier.size(48.dp),
                containerColor = Color(0xFF258694),
                shape = RoundedCornerShape(10.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_send_icon),
                    contentDescription = "Send",
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Composable
fun ChatMessageItem(message: ChatMessage) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (message.isFromUser) {
            Arrangement.End
        } else {
            Arrangement.Start
        },
                verticalAlignment = Alignment.Bottom
    ) {
        if (!message.isFromUser) {
            // Avatar for received messages


                message.avatarEmoji?.let { painterResource(it) }?.let {
                    Image(
                        painter = it,
                        contentDescription = "Community Icon", modifier = Modifier
                            .size(42.dp)
                            .clip(CircleShape),contentScale = ContentScale.Crop)

                }

            Spacer(modifier = Modifier.width(8.dp))
        }

        // Message bubble
        Card(
            modifier = Modifier.widthIn(max = 280.dp),
            colors = CardDefaults.cardColors(
                containerColor = if (message.isFromUser) {
                    Color(0xFF258694)
                } else {
                    Color(0xFFE5EFF2)
                }
            ),
            shape = RoundedCornerShape(
                topStart = 13.dp ,
                topEnd =  13.dp,
                bottomStart = if (message.isFromUser) 13.dp else 0.dp,
                bottomEnd = if (message.isFromUser) 0.dp else 13.dp,
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
        ) {
            Text(
                text = message.text,
                modifier = Modifier.padding(12.dp),
                color = if (message.isFromUser) Color.White else Color.Black,
                fontFamily = FontFamily(Font(R.font.outfit_regular)),
                fontSize = 13.sp,
                lineHeight = 18.sp
            )
        }

        if (message.isFromUser) {
            Spacer(modifier = Modifier.width(8.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PetGroomingChatScreenPreview() {
    MaterialTheme {
        PetGroomingChatScreen()
    }
}