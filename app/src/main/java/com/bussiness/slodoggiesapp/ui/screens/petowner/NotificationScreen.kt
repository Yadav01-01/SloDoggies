package com.bussiness.slodoggiesapp.ui.screens.petowner

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.bussiness.slodoggiesapp.R

data class NotificationItem(
    val id: Int,
    val username: String,
    val action: String,
    val timeAgo: String,
    val profileImageRes: Int,
    val postImageRes: Int? = null,
    val isFollowAction: Boolean = false
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationsScreen(navController: NavHostController) {
    val notifications = remember {
        listOf(
            NotificationItem(
                id = 1,
                username = "username",
                action = "started following you.",
                timeAgo = "1h ago",
                profileImageRes = R.drawable.dummy_person_image1,
                isFollowAction = true
            ),
            NotificationItem(
                id = 2,
                username = "username",
                action = "liked your post.",
                timeAgo = "2h ago",
                profileImageRes = R.drawable.dummy_person_image1,
                postImageRes = R.drawable.dummy_person_image3
            ),
            NotificationItem(
                id = 3,
                username = "username",
                action = "liked your post.",
                timeAgo = "3h ago",
                profileImageRes = R.drawable.dummy_person_image1,
                postImageRes = android.R.drawable.ic_menu_gallery
            ),
            NotificationItem(
                id = 4,
                username = "username",
                action = "started following you.",
                timeAgo = "5h ago",
                profileImageRes = android.R.drawable.ic_menu_camera,
                isFollowAction = true
            ),
            NotificationItem(
                id = 5,
                username = "username",
                action = "started following you.",
                timeAgo = "8h ago",
                profileImageRes = R.drawable.dummy_person_image2,
                isFollowAction = true
            ),
            NotificationItem(
                id = 6,
                username = "username",
                action = "started following you.",
                timeAgo = "12h ago",
                profileImageRes = R.drawable.dummy_person_image2,
                isFollowAction = true
            ),
            NotificationItem(
                id = 7,
                username = "username",
                action = "liked your post.",
                timeAgo = "1d ago",
                profileImageRes = android.R.drawable.ic_menu_camera,
                postImageRes = android.R.drawable.ic_menu_gallery
            ),
            NotificationItem(
                id = 8,
                username = "username",
                action = "liked your post.",
                timeAgo = "2d ago",
                profileImageRes = android.R.drawable.ic_menu_camera,
                postImageRes = R.drawable.dummy_person_image3
            )
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Top App Bar
        TopAppBar(
            title = {
                Text(
                    text = "Notifications",
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
                .height(5.dp)
                .background(Color(0xFF656565))
        )

        // Today section header
        Text(
            text = "Today",
            fontSize = 16.sp,
            fontFamily = FontFamily(Font(R.font.outfit_regular)),
            color = Color.Black,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height((1.5).dp)
                .padding(
                    horizontal = 17.dp
                )
                .background(Color(0xFF258694))
        )

        // Notifications List
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(notifications) { notification ->
                NotificationItemRow(
                    notification = notification,
                    onFollowClick = { /* Handle follow action */ },
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
        }
    }
}

@Composable
fun NotificationItemRow(
    notification: NotificationItem,
    onFollowClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Profile Image
        Image(
            painter = painterResource(id = notification.profileImageRes),
            contentDescription = "Profile picture",
            modifier = Modifier
                .size(44.dp)
                .clip(CircleShape)
                .background(Color.Gray.copy(alpha = 0.2f)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(12.dp))

        // Content
        Column(
            modifier = Modifier.weight(1f)
        ) {
            // Username and action
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = notification.username,
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.outfit_medium)),
                    color = Color.Black
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = notification.action,
                    fontSize = 12.sp,
                    color = Color(0xFF949494),
                    fontFamily = FontFamily(Font(R.font.outfit_regular)),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Spacer(modifier = Modifier.height(2.dp))

            // Time ago
            Text(
                text = notification.timeAgo,
                fontSize = 12.sp,
                fontFamily = FontFamily(Font(R.font.outfit_regular)),
                color = Color(0xFF9D9D9D)
            )
        }

        // Action button or post image
        if (notification.isFollowAction) {
            Button(
                onClick = onFollowClick,
                modifier = Modifier
                    .height(28.dp)
                    .padding(horizontal = 4.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF258694)
                ),
                shape = RoundedCornerShape(6.dp),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 0.dp)
            ) {
                Text(
                    text = "Follow back",
                    fontSize = 12.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Medium
                )
            }
        } else {
            notification.postImageRes?.let { imageRes ->
                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = "Post image",
                    modifier = Modifier
                        .size(37.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(Color.Gray.copy(alpha = 0.2f)),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NotificationsScreenPreview() {
    MaterialTheme {
        NotificationsScreen(navController)
    }
}