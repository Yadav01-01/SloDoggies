package com.bussiness.slodoggiesapp.ui.component.petOwner

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter.State.Empty.painter
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor

@Composable
fun IconHeadingText(textHeading: String, onBackClick: () -> Unit,onIconClick: () -> Unit,rightSideIcon : Int,iconColor : Color = Color(0xFF656565),  dividerColor: Color = Color(0xFF656565),displayRightIcon : Boolean = false) {
    Column(modifier = Modifier.background(Color(0xFFFFFFFF))) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(
            start = 15.dp,
            end = 15.dp,
            top = 15.dp,
            bottom = 18.dp
        )

    ) {
        Row(
            modifier = Modifier.weight(1f).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.back_ic),
                contentDescription = "back",
                tint = PrimaryColor,
                modifier = Modifier
                    .clickable { onBackClick() }
                    .wrapContentSize()
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = textHeading,
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(R.font.outfit_medium)),
                color = Color(0xFF221B22)
            )
        }

        if (displayRightIcon){
            Icon(
                painter = painterResource(rightSideIcon),
                contentDescription = "",
                tint =iconColor,
             //   tint = Color.Unspecified,
                modifier = Modifier
                    .wrapContentSize()
                    .clickable { onIconClick() }
            )
        }

    }
        HorizontalDivider(modifier = Modifier.fillMaxWidth().height(2.dp).background(dividerColor))

}
}


/*
 TopAppBar(
            title = {
                Text(
                    text = "SloDooggies",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF212121)
                )
            },
            actions = {
                // Notification icon
                IconButton(onClick = { /* Handle notification click */ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_notification_bell),
                        contentDescription = "Notifications",
                        tint = Color.Black
                    )
                }

                // Chat/Message icon
                IconButton(onClick = { /* Handle chat click */ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_chat_icon),
                        contentDescription = "Messages",
                        tint = Color.Black
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color(0xFFFFFFFF),
                titleContentColor = Color.Black,
                actionIconContentColor = Color.Black
            ),
            windowInsets = WindowInsets(0.dp)
        )

 */
@Composable
fun HomeHeader(
    textHeading: String,
    onNotificationClick: () -> Unit = {},
    onChatClick: () -> Unit = {},
    notificationIcon: Int = R.drawable.ic_notification_bell,
    chatIcon: Int = R.drawable.ic_chat_icon)

    {
        Column (modifier = Modifier.background(Color(0xFFFFFFFF))){
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(
                    start = 15.dp,
                    end = 15.dp,
                    top = 15.dp,
                    bottom = 18.dp
                )
            ) {
                // Title
                Text(
                    text = textHeading,
                    fontSize = 20.sp,
                    fontFamily = FontFamily(Font(R.font.outfit_medium)),
                    color = Color(0xFF221B22),
                    modifier = Modifier.weight(1f))


                Icon(
                    painter = painterResource(notificationIcon),
                    contentDescription = "Notifications",
                    tint = Color.Black,
                    modifier = Modifier.wrapContentSize()
                        .clickable { onNotificationClick() }
                        .padding(end = 16.dp)
                )

                // Chat icon
                Icon(
                    painter = painterResource(chatIcon),
                    contentDescription = "Messages",
                    tint = Color.Black,
                    modifier = Modifier.size(28.dp)
                        .clickable { onChatClick() }
                )
            }

        }
    }



@Composable
fun EventCommunityScreenHeader(textHeading: String, onBackClick: () -> Unit,onIconClick: () -> Unit,rightSideIcon : Int,iconColor : Color = Color(0xFF656565),  displayRightIcon : Boolean = false) {
    Column(modifier = Modifier.background(Color(0xFFFFFFFF))) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(
                start = 15.dp,
                end = 15.dp,
                top = 15.dp,
                bottom = 18.dp
            )

        ) {
            Row(
                modifier = Modifier.weight(1f).fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(R.drawable.back_ic),
                    contentDescription = "back",
                    tint = PrimaryColor,
                    modifier = Modifier
                        .clickable { onBackClick() }
                        .wrapContentSize()
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = textHeading,
                    fontSize = 20.sp,
                    fontFamily = FontFamily(Font(R.font.outfit_medium)),
                    color = Color(0xFF221B22)
                )
            }

            if (displayRightIcon){
                Icon(
                    painter = painterResource(rightSideIcon),
                    contentDescription = "",

                       tint = Color.Unspecified,
                    modifier = Modifier
                        .wrapContentSize()
                        .clickable { onIconClick() }
                )
            }

        }

    }
}


@Composable
fun SettingIconHeader(textHeading: String, onBackClick: () -> Unit,onSettingClick: () -> Unit) {
    Column(modifier = Modifier.background(Color(0xFFFFFFFF))) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(
                start = 15.dp,
                end = 15.dp,
                top = 15.dp,
                bottom = 18.dp
            )

        ) {
            Row(
                modifier = Modifier.weight(1f).fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(R.drawable.back_ic),
                    contentDescription = "back",
                    tint = PrimaryColor,
                    modifier = Modifier
                        .clickable { onBackClick() }
                        .wrapContentSize()
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = textHeading,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Medium,
                        fontSize = 20.sp
                    ),
                    fontFamily = FontFamily(Font(R.font.outfit_medium)),
                    color = Color(0xFF221B22)
                )
            }

            Icon(
                painter = painterResource(R.drawable.setting_ic),
                contentDescription = "settings",
                tint = Color.Unspecified,
                modifier = Modifier
                    .wrapContentSize()
                    .clickable { onSettingClick() }
            )
        }
    }
}

@Composable
fun PetGroomingChatScreenHeader(
    onBackClick: () -> Unit={},
    onMenuClick: () -> Unit={},
    communityName: String = "Event Community 1",
    memberCount: String = "20 members"
) {
    Column(modifier = Modifier.background(Color.White)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(
                start = 15.dp,
                end = 15.dp,
                top = 15.dp,
                bottom = 18.dp
            )
        ) {
            // Back button
            Icon(
                painter = painterResource(R.drawable.back_ic),
                contentDescription = "back",
                tint = PrimaryColor,
                modifier = Modifier
                    .clickable { onBackClick() }
                    .wrapContentSize()
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Community info
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_community_icons),
                    contentDescription = "Community Icon",
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                )

                Spacer(Modifier.width(15.dp))

                Column {
                    Text(
                        text = communityName,
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.outfit_regular)),
                        color = Color.Black,
                        modifier = Modifier.height(24.dp)
                    )

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(R.drawable.ic_users_group),
                            contentDescription = "Members",
                            modifier = Modifier.size(24.dp)
                        )

                        Spacer(Modifier.width(7.dp))

                        Text(
                            text = memberCount,
                            fontFamily = FontFamily(Font(R.font.outfit_regular)),
                            color = Color.Black,
                            fontSize = 16.sp
                        )
                    }
                }
            }

            // Menu button

                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "More options",
                    tint = Color.Black,
                    modifier = Modifier
                        .wrapContentSize()
                        .clickable { onMenuClick() }
                )

        }
    }
}


@Preview(showBackground = true)
@Composable
fun PetGroomingChatScreenHeaderPreview(){

    PetGroomingChatScreenHeader(

        communityName = "Event Community 1",
        memberCount = "20 members"
    )
}


