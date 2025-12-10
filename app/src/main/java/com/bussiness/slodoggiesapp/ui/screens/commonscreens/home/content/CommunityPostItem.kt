package com.bussiness.slodoggiesapp.ui.screens.commonscreens.home.content

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.data.newModel.home.PostItem
import com.bussiness.slodoggiesapp.ui.component.petOwner.dialog.Comment
import com.bussiness.slodoggiesapp.ui.dialog.DeleteChatDialog
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor
import com.bussiness.slodoggiesapp.ui.theme.TextGrey


@Composable
fun CommunityPostItem(postItem: PostItem.CommunityPost, onJoinedCommunity: () -> Unit, onReportClick:  () -> Unit, onShareClick: () -> Unit, onProfileClick: () -> Unit,
                      onLikeClick:()-> Unit, onClickInterested: () -> Unit){
    var isFollowed by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(vertical = 12.dp)) {

            // User Info Row
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = postItem.media?.parentImageUrl,
                    placeholder = painterResource(R.drawable.ic_person_icon),
                    error = painterResource(R.drawable.ic_person_icon),
                    contentDescription = "User Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) { onProfileClick() }
                )

                Spacer(modifier = Modifier.width(8.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically){
                        Text(
                            text = postItem.userName,
                            fontWeight = FontWeight.Medium,
                            fontSize = 12.sp,
                            fontFamily = FontFamily(Font(R.font.outfit_medium)),
                            color = Color.Black
                        )

                        Spacer(Modifier.width(8.dp))

                        val interactionSource = remember { MutableInteractionSource() }

                        OutlinedButton(
                            onClick = { isFollowed = !isFollowed },
                            modifier = Modifier
                                .height(24.dp)
                                .padding(horizontal = 10.dp),
                            shape = RoundedCornerShape(6.dp),
                            border = if (isFollowed) BorderStroke(1.dp, PrimaryColor) else null,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isFollowed) Color.White else PrimaryColor,
                                contentColor = if (isFollowed) PrimaryColor else Color.White
                            ),
                            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 0.dp),
                            interactionSource = interactionSource
                        ) {
                            Text(
                                text = if (isFollowed) "Following" else "Follow",
                                fontFamily = FontFamily(Font(R.font.outfit_regular)),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Normal
                            )
                        }
                    }

                    Text(
                        text = postItem.time,
                        fontSize = 12.sp,
                        color = TextGrey,
                        fontFamily = FontFamily(Font(R.font.outfit_regular)),
                        lineHeight = 15.sp
                    )
                }

                PostOptionsMenu(modifier = Modifier, onReportClick = onReportClick )
            }

            Spacer(modifier = Modifier.height(2.dp))

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
                    postItem.eventStartDate?.let {
                        Text(
                            text = it,
                            fontSize = 14.sp,
                            color = Color.Black,
                            fontFamily = FontFamily(Font(R.font.outfit_regular))
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Description and Duration
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                postItem.eventDescription?.let {
                    Text(
                        text = it,
                        fontSize = 14.sp,
                        color = TextGrey,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        lineHeight = 15.sp,
                        fontFamily = FontFamily(Font(R.font.outfit_regular)),
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.cal_ic),
                        contentDescription = "Calendar",
                        tint = Color.Unspecified
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    postItem.eventEndDate?.let {
                        Text(
                            text = it,
                            fontSize = 14.sp,
                            color = Color.Black,
                            fontFamily = FontFamily(Font(R.font.outfit_regular))
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Location
            Row(
                verticalAlignment = Alignment.Top,
                modifier = Modifier.padding(horizontal = 12.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.location_ic_icon),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = Modifier.align(Alignment.Top)
                )

                Spacer(modifier = Modifier.width(4.dp))

                postItem.location?.let {
                    Text(
                        text = it,
                        fontSize = 14.sp,
                        lineHeight = 18.sp,
                        fontFamily = FontFamily(Font(R.font.outfit_medium)),
                        color = Color.Black,
                        modifier = Modifier.align(Alignment.Top)
                    )
                }
            }


            Spacer(modifier = Modifier.height(12.dp))

            PostImage(mediaList = postItem.mediaList)

            // CTA Button
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black)
                    .padding(horizontal = 12.dp, vertical = 8.dp)
                    .clickable { onJoinedCommunity() },
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
            CommunityPostLikes(likes = postItem.likes, comments = postItem.comments,
                shares = postItem.shares, onShareClick = { onShareClick()},
                onLikeClick = { onLikeClick() },onClickIntrested = { onClickInterested() })


        }
    }

}@Composable

fun CommunityPostLikes(likes: Int, comments: Int, shares: Int,onShareClick: () -> Unit,
                       onLikeClick: () -> Unit,onClickIntrested : () -> Unit) {
    var isLiked by remember { mutableStateOf(false) }
    var isBookmarked by remember { mutableStateOf(false) }
    var showCommentsDialog  by remember { mutableStateOf(false) }
    var deleteComment by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .padding(horizontal = 12.dp, vertical = 2.dp)
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
                modifier = Modifier.size(25.dp).clickable(
                    indication = null,
                    interactionSource =  remember { MutableInteractionSource() }
                ) {
                    onLikeClick()
                    isLiked = !isLiked // Toggle the liked state
                },
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = likes.toString()/*(if (isLiked) likes + 1 else likes).toString()*/,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = if (isLiked) Color(0xFF258694) else Color.Black
            )
//
            Spacer(modifier = Modifier.width(16.dp))

            // Shares section
            Icon(
                painter = painterResource(id = R.drawable.ic_share_icons),
                contentDescription = "Shares",
                modifier = Modifier.size(25.dp).clickable(
                    indication = null,
                    interactionSource =  remember { MutableInteractionSource() }
                ) { onShareClick() }
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
                onClick = {
                    isBookmarked = !isBookmarked
                          onClickIntrested()},
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
//        CommentsDialog(
//            comments = sampleComments,
//            onDismiss = { showCommentsDialog = false }
//            , onDeleteClick = { deleteComment = true }
//        )
    }
    if (deleteComment){
        DeleteChatDialog(
            onDismiss = { deleteComment = false },
            onClickRemove = { deleteComment = false  },
            iconResId = R.drawable.delete_mi,
            text = "Delete Comment",
            description = stringResource(R.string.delete_Comment)
        )
    }
}
