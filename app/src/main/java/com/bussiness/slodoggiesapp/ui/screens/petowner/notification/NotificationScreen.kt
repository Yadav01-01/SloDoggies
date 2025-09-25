package com.bussiness.slodoggiesapp.ui.screens.petowner.notification

import androidx.activity.compose.BackHandler
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.navigation.Routes
import com.bussiness.slodoggiesapp.ui.component.businessProvider.HeadingTextWithIcon
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor
import com.bussiness.slodoggiesapp.ui.theme.TextGrey

@Composable
fun PetNotificationsScreen(navController: NavHostController) {
    val notifications = remember {
        listOf(
            NotificationItem(
                id = 11,
                username = "Event Saved",
                action = "You’ve marked this event as Interested — we’ll keep you updated! \uD83D\uDC3E",
                timeAgo = "1h ago",
                profileImageRes = R.drawable.dummy_person_image1,
                isFollowAction = true,
                type = "event"
            ),
            NotificationItem(
                id = 1,
                username = "username",
                action = "started following you.",
                timeAgo = "1h ago",
                profileImageRes = R.drawable.dummy_person_image1,
                isFollowAction = true,
                type = "post"
            ),
            NotificationItem(
                id = 2,
                username = "username",
                action = "liked your post.",
                timeAgo = "2h ago",
                profileImageRes = R.drawable.dummy_person_image1,
                postImageRes = R.drawable.dummy_person_image3,
                type = "post"
            ),
            NotificationItem(
                id = 3,
                username = "username",
                action = "liked your post.",
                timeAgo = "3h ago",
                profileImageRes = R.drawable.dummy_person_image1,
                postImageRes = android.R.drawable.ic_menu_gallery,
                type = "post"
            ),
            NotificationItem(
                id = 4,
                username = "username",
                action = "started following you.",
                timeAgo = "5h ago",
                profileImageRes = android.R.drawable.ic_menu_camera,
                isFollowAction = true,
                type = "post"
            ),
            NotificationItem(
                id = 5,
                username = "username",
                action = "started following you.",
                timeAgo = "8h ago",
                profileImageRes = R.drawable.dummy_person_image2,
                isFollowAction = true,
                type = "post"
            ),
            NotificationItem(
                id = 6,
                username = "username",
                action = "started following you.",
                timeAgo = "12h ago",
                profileImageRes = R.drawable.dummy_person_image2,
                isFollowAction = true,
                type = "post"
            ),
            NotificationItem(
                id = 7,
                username = "username",
                action = "liked your post.",
                timeAgo = "1d ago",
                profileImageRes = android.R.drawable.ic_menu_camera,
                postImageRes = android.R.drawable.ic_menu_gallery,
                type = "post"
            ),
            NotificationItem(
                id = 8,
                username = "username",
                action = "liked your post.",
                timeAgo = "2d ago",
                profileImageRes = android.R.drawable.ic_menu_camera,
                postImageRes = R.drawable.dummy_person_image3,
                type = "post"
            )
        )
    }

    BackHandler {
        if (!navController.popBackStack(Routes.HOME_SCREEN,
                false)) {
            navController.navigate(Routes.HOME_SCREEN) {
                launchSingleTop = true
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        HeadingTextWithIcon(textHeading = "Notifications",
            onBackClick = {  if (!navController.popBackStack(Routes.HOME_SCREEN, false)) {
                navController.navigate(Routes.HOME_SCREEN) {
                    launchSingleTop = true
                }
            } })

        HorizontalDivider(thickness = 2.dp, color = PrimaryColor)

        // Notifications List
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            item {
                Text(
                    "Today",
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    style = MaterialTheme.typography.titleMedium
                )
                HorizontalDivider(thickness = 1.dp, color = PrimaryColor)

            }

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
        if (notification.type == "post"){
            AsyncImage(
                model = notification.profileImageRes,
                contentDescription = "Profile picture",
                placeholder = painterResource(id = R.drawable.ic_person_icon1),
                error = painterResource(id = R.drawable.ic_person_icon1),
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(Color.Gray.copy(alpha = 0.2f)),
                contentScale = ContentScale.Crop
            )
        }else{
            Image(
                painter = painterResource(id = R.drawable.msg_event),
                contentDescription = "event Image",
                modifier = Modifier
                    .wrapContentSize(),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        // Content
        Column(
            modifier = Modifier.weight(1f)
        ) {
            // Username and action
            if (notification.type == "post"){
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = notification.username,
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.outfit_medium)),
                        fontWeight = FontWeight.Medium,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = notification.action,
                        fontSize = 14.sp,
                        color = TextGrey,
                        fontFamily = FontFamily(Font(R.font.outfit_regular)),
                        fontWeight = FontWeight.Normal,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }else{
                Text(
                    text = notification.username,
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.outfit_medium)),
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = notification.action,
                    fontSize = 14.sp,
                    color = TextGrey,
                    lineHeight = 18.sp,
                    fontFamily = FontFamily(Font(R.font.outfit_regular)),
                    fontWeight = FontWeight.Normal,
                    maxLines = 4,
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
        if (notification.type == "post"){
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
                        fontFamily = FontFamily(Font(R.font.outfit_regular)),
                        fontWeight = FontWeight.Normal
                    )
                }
            } else {
                notification.postImageRes?.let { imageRes ->    //event
                    AsyncImage(
                        model = imageRes, // Can be String (URL) or Int (resId)
                        contentDescription = "Post image",
                        placeholder = painterResource(id = android.R.drawable.ic_menu_gallery),
                        error = painterResource(id = android.R.drawable.ic_menu_gallery),
                        modifier = Modifier
                            .size(37.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(Color.Gray.copy(alpha = 0.2f)),
                        contentScale = ContentScale.Crop
                    )
                }

            }
        }else{
            Button(
                onClick = onFollowClick,
                modifier = Modifier
                    .height(28.dp)
                    .padding(horizontal = 4.dp).align(Alignment.Top),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF258694)
                ),
                shape = RoundedCornerShape(6.dp),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 0.dp)
            ) {
                Text(
                    text = "Join Chat",
                    fontSize = 12.sp,
                    color = Color.White,
                    fontFamily = FontFamily(Font(R.font.outfit_regular)),
                    fontWeight = FontWeight.Normal
                )
            }
        }
    }
}

data class NotificationItem(
    val id: Int,
    val username: String,
    val action: String,
    val timeAgo: String,
    val profileImageRes: Int,
    val postImageRes: Int? = null,
    val type: String,
    val isFollowAction: Boolean = false
)

@Preview(showBackground = true)
@Composable
fun NotificationsScreenPreview() {
    val navController = rememberNavController()
    MaterialTheme {
    PetNotificationsScreen(navController)
    }
}