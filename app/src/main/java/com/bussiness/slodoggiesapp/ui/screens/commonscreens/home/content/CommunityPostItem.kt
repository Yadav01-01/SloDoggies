package com.bussiness.slodoggiesapp.ui.screens.commonscreens.home.content

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.model.common.PostItem
import com.bussiness.slodoggiesapp.ui.component.petOwner.Dialog.Comment
import com.bussiness.slodoggiesapp.ui.component.petOwner.Dialog.CommentsDialog
import com.bussiness.slodoggiesapp.ui.component.shareApp
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor
import com.bussiness.slodoggiesapp.ui.theme.TextGrey


@Composable
fun CommunityPostItem(postItem: PostItem.CommunityPost,onReportClick: () -> Unit,onShareClick: () -> Unit){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(vertical = 12.dp)) {

            // User Info Row
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = postItem.userImage),
                    contentDescription = "User Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = postItem.userName,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 13.sp,
                        fontFamily = FontFamily(Font(R.font.outfit_regular)),
                        color = Color.Black
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = postItem.label,
                            fontSize = 8.sp,
                            fontFamily = FontFamily(Font(R.font.outfit_medium)),
                            color = PrimaryColor,
                            modifier = Modifier
                                .padding(top = 4.dp)
                                .background(
                                    color = Color(0xFFE5EFF2),
                                    shape = RoundedCornerShape(16.dp)
                                )
                                .padding(horizontal = 8.dp)
                        )

                        Spacer(Modifier.width(8.dp))

                        Text(
                            text = postItem.time,
                            fontSize = 12.sp,
                            color = TextGrey,
                            fontFamily = FontFamily(Font(R.font.outfit_regular)),
                        )
                    }
                }

                Button(
                    onClick = { postItem.onClickFollow() },
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryColor),
                    shape = RoundedCornerShape(6.dp),
                    contentPadding = PaddingValues(horizontal = 14.dp),
                    modifier = Modifier.height(25.dp)
                ) {
                    Text(
                        "Follow",
                        color = Color.White,
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(R.font.outfit_regular))
                    )
                }

                PostOptionsMenu(onReportClick = onReportClick )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Event Title and Date
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = postItem.eventTitle,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = FontFamily(Font(R.font.outfit_medium)),
                    color = Color.Black,
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(12.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.cal_ic),
                        contentDescription = "Calendar",
                        tint = Color.Unspecified
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = postItem.eventDate,
                        fontSize = 14.sp,
                        color = Color.Black,
                        fontFamily = FontFamily(Font(R.font.outfit_regular))
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Description and Duration
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = postItem.eventDescription,
                    fontSize = 14.sp,
                    color = TextGrey,
                    maxLines = 8,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 18.sp,
                    fontFamily = FontFamily(Font(R.font.outfit_regular)),
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.time_ic),
                        contentDescription = "Duration",
                        tint = Color.Unspecified
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = postItem.eventDuration,
                        fontSize = 12.sp,
                        color = Color.Gray,
                        fontFamily = FontFamily(Font(R.font.outfit_regular))
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Location
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(horizontal = 12.dp)) {
                Icon(
                    painter = painterResource(R.drawable.location_ic),
                    contentDescription = null,
                    tint = Color.Unspecified
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = postItem.location,
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.outfit_medium)),
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Image(
                painter = painterResource(id = postItem.postImage),
                contentDescription = "Event Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(230.dp)
                    .clip(RoundedCornerShape(0.dp))
            )

            // CTA Button
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black)
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                Text("Join Community", modifier = Modifier.padding(end = 35.dp),
                    color = Color.White, fontSize = 16.sp, fontFamily = FontFamily(Font(R.font.outfit_medium)))
                Icon(
                    painter = painterResource(R.drawable.ic_chat_icon),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .wrapContentSize()
                )
            }


            Spacer(modifier = Modifier.height(10.dp))

            // Actions
            CommunityPostLikes(likes = postItem.likes, comments = postItem.comments, shares = postItem.shares, onShareClick = { onShareClick()})
        }
    }

}@Composable
fun CommunityPostLikes(likes: Int, comments: Int, shares: Int,onShareClick: () -> Unit) {
    var isLiked by remember { mutableStateOf(false) }
    var isBookmarked by remember { mutableStateOf(false) }
    var showCommentsDialog  by remember { mutableStateOf(false) }
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .padding(horizontal = 12.dp, vertical = 10.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Like section
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
        ) {
            Icon(
                painter = painterResource(  id = if (isLiked) R.drawable.ic_paw_like_filled_icon else R.drawable.ic_paw_like_icon),
                contentDescription = "Paw",
                modifier = Modifier.size(25.dp).clickable {
                    isLiked = !isLiked // Toggle the liked state
                },
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = (if (isLiked) likes + 1 else likes).toString(),
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = if (isLiked) Color(0xFF258694) else Color.Black
            )
            Spacer(modifier = Modifier.width(16.dp))

            // Comments section
            Icon(
                painter = painterResource(id = R.drawable.ic_chat_bubble_icon),
                contentDescription = "Comments",
                modifier = Modifier.size(25.dp).clickable {
                    // isLiked = !isLiked // Toggle the liked state
                    showCommentsDialog = true
                }
            )

            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = comments.toString(),
                fontSize = 16.sp,
            )
            Spacer(modifier = Modifier.width(16.dp))

            // Shares section
            Icon(
                painter = painterResource(id = R.drawable.ic_share_icons),
                contentDescription = "Shares",
                modifier = Modifier.size(25.dp).clickable { onShareClick() }
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = shares.toString(),
                fontSize = 16.sp
            )
        }

        Row (verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable {  isBookmarked = !isBookmarked  }){
            Text(text = stringResource(R.string.intrested),
                fontFamily = FontFamily(Font(R.font.outfit_regular)),
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                color = if (isBookmarked) PrimaryColor else Color.Black
            )
            // Bookmark icon aligned to end
            IconButton(
                onClick = { isBookmarked = !isBookmarked },
                modifier = Modifier.size(25.dp)
            ) {
                Icon(
                    painter = if (isBookmarked) painterResource(id = R.drawable.filled_ic ) else painterResource(id = R.drawable.intrested_ic),
                    contentDescription = "Bookmark",
                    tint = Color.Unspecified,
                    modifier = Modifier.size(24.dp)
                )
            }
        }

    }
    if (showCommentsDialog) {


        val sampleComments = listOf(
            Comment(
                id = "1",
                userName = "Dianne Russell",
                userRole = "Pet Dad",
                userAvatar = "https://via.placeholder.com/40x40",
                text = "This place is amazing! My dog loved it",
                timeAgo = "24 min",
                likeCount = 2,
                isLiked = true,
                replies = listOf(
                    Comment(
                        id = "1-1",
                        userName = "Alex Johnson",
                        userRole = "Pet Owner",
                        userAvatar = "",
                        text = "I agree! My poodle had so much fun too!",
                        timeAgo = "15 min",
                        likeCount = 1,
                        isLiked = false
                    )
                )
            ),
            Comment(
                id = "2",
                userName = "Jack Roger",
                userRole = "Pet Dad",
                userAvatar = "https://via.placeholder.com/40x40",
                text = "Took my pup here last weekend — 10/10 would recommend! 🐕",
                timeAgo = "1 day ago",
                likeCount = 0,
                isLiked = false
            )
        )
        CommentsDialog(
            comments = sampleComments,
            onDismiss = { showCommentsDialog = false }
        )
    }
}
