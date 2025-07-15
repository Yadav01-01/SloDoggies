package com.bussiness.slodoggiesapp.ui.screens.petowner

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.packInts
import com.bussiness.slodoggiesapp.R

data class  MessageItem(
    val id: Int,
    val username: String,
    val description: String,
    val timeAgo: String,
    val profileImageRes: Int
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun   MessageScreen(){
    val messages = remember {
        listOf(
            MessageItem(id = 1,
                username = "Jane Cooper",
                description = "I hope it goes well.",
                timeAgo = "14:41",
                profileImageRes = R.drawable.dummy_person_image1)
        ,
        MessageItem(id = 2,
            username = "Event Community 1",
            description = "So, what's your plan this weekend?",
            timeAgo = "15:41",
            profileImageRes = R.drawable.dummy_person_image2)
        ,
        MessageItem(id = 1,
            username = "Jane Cooper",
            description = "I hope it goes well.",
            timeAgo = "14:41",
            profileImageRes = R.drawable.dummy_person_image1),
            MessageItem(id = 1,
                username = "Jane Cooper",
                description = "I hope it goes well.",
                timeAgo = "14:41",
                profileImageRes = R.drawable.dummy_person_image1)

        )
    }

    Column(modifier = Modifier.fillMaxSize()
        .background(Color.White))
    {
        TopAppBar(
            title = {
                androidx.compose.material3.Text(
                    text = "Messages",
                    fontSize = 18.sp,
                    fontFamily = FontFamily(Font(R.font.outfit_medium)),
                    color = Color(0xFF3F393F)
                )
            },
            navigationIcon = {
                IconButton(onClick = { /* Handle back navigation */ }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color(0xFF258694)
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.White
            )
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height((2).dp)
                .background(Color(0xFF656565))
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)

        )
    LazyColumn (modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(messages) {message ->
            MessageItemRow(
                message = message,
                modifier = Modifier.padding(vertical = 12.dp, horizontal = 5.dp)
            )
        }

    }

    }
}

@Composable
fun MessageItemRow(
    message: MessageItem,
    modifier: Modifier = Modifier
){
    Row(modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = painterResource(id = message.profileImageRes),
            contentDescription = "Profile picture",
            modifier = Modifier.size(44.dp)
                .clip(CircleShape),
               // .background(Color.Gray.copy(alpha = 0.2f)),
                    contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {

                Text(   text = message.username,
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.outfit_regular)),
                    color = Color.Black )
                Spacer(modifier = Modifier.width(4.dp))
            Spacer(modifier = Modifier.height(2.dp))

            // Time ago
            androidx.compose.material3.Text(
                text = message.description,
                fontSize = 12.sp,
                fontFamily = FontFamily(Font(R.font.outfit_regular)),
                color = Color(0xFF9D9D9D)
            )
        }
        Text(   text = message.timeAgo,
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(R.font.outfit_regular)),
            color = Color(0xFF949494))
    }

}

@Preview(showBackground = true)
@Composable
fun MessagesScreenPreview() {
    MaterialTheme {
        MessageScreen()
    }
}