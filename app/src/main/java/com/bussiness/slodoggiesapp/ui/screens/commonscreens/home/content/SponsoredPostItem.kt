package com.bussiness.slodoggiesapp.ui.screens.commonscreens.home.content


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.data.newModel.home.PostItem
import com.bussiness.slodoggiesapp.data.newModel.home.PostMediaResponse
import com.bussiness.slodoggiesapp.ui.component.petOwner.dialog.Comment
import com.bussiness.slodoggiesapp.ui.component.petOwner.dialog.CommentsDialog
import com.bussiness.slodoggiesapp.ui.dialog.DeleteChatDialog
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor

@Composable
fun SponsoredPostItem(
    post: PostItem.SponsoredPost,
    onProfileClick: () -> Unit,
    onReportClick: () -> Unit,
    onShareClick: () -> Unit,
    onSponsoredClick: () -> Unit,
    onLikeClick:()-> Unit,
    onCommentClick:()-> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth().clickable { onSponsoredClick() }) {
            PostHeader(user = post.userName, time = post.time,
                onReportClick = { onReportClick()}, onProfileClick = { onProfileClick() })
            PostCaption(caption = post.caption, description = post.description)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(353.dp)
            ) {

                PostImage(mediaList = post.mediaList)

                Box(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .fillMaxWidth()
                        .height(50.dp)
                        .background(PrimaryColor)
                ) {
                    Text(
                        text = "Sponsored",
                        modifier = Modifier.padding(start = 12.dp).align(Alignment.CenterStart),
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.outfit_medium)),
                        color = Color.White
                    )
                }
            }
            PostActions(likes = post.likes, comments = post.comments,
                shares = post.shares,onShareClick = onShareClick,onLikeClick =onLikeClick,
                onCommentClick = onCommentClick,
                isLike = post.isLiked)
        }
    }
}

@Composable
private fun PostHeader(user: String, time: String, onReportClick: () -> Unit,onProfileClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 0.dp).padding(start = 15.dp, top = 15.dp ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable(
            indication = null,   // removes ripple
            interactionSource = remember { MutableInteractionSource() }
        ) { onProfileClick() }) {
            AsyncImage(
                model = "painterResource(id = R.drawable.dummy_person_image1)",
                placeholder = painterResource(R.drawable.ic_person_icon),
                error =  painterResource(R.drawable.ic_person_icon),
                contentDescription = "Profile",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(   modifier = Modifier.wrapContentHeight(),
                verticalArrangement = Arrangement.spacedBy(0.dp)) {
                Text(
                    text = user,
                    fontSize = 12.sp,
                    color = Color.Black,
                    lineHeight = 17.sp,
                    fontFamily = FontFamily(Font(R.font.outfit_medium))
                )

                Text(
                    text = time,
                    fontSize = 12.sp,
                    lineHeight = 17.sp,
                    color = Color(0xFF949494),
                    fontFamily = FontFamily(Font(R.font.outfit_regular))
                )
            }
        }

        PostOptionsMenu(modifier = Modifier.padding(end = 15.dp), onReportClick = onReportClick)
    }
}


@Composable
private fun PostCaption(caption: String, description: String,) {
    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 5.dp)) {
        Text(
            text = caption,
            fontSize = 13.sp,
            lineHeight = 17.sp,
            fontFamily = FontFamily(Font(R.font.outfit_bold)),
            color = Color.Black
        )
        if (description.isNotBlank()) {
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = description,
                fontSize = 12.sp,
                lineHeight = 17.sp,
                fontFamily = FontFamily(Font(R.font.outfit_regular)),
                color = Color.Black
            )
        }
    }
}


@Composable
private fun PostMedia(mediaList: List<PostMediaResponse>) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(353.dp)
    ) {

        AsyncImage(
            model = R.drawable.dog1,
            contentDescription = "Post image",
            contentScale = ContentScale.Crop,
            modifier =  Modifier.fillMaxWidth().height(350.dp)
        )


        Box(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .fillMaxWidth()
                .height(50.dp)
                .background(PrimaryColor)
        ) {
            Text(
                text = "Sponsored",
                modifier = Modifier.padding(start = 12.dp).align(Alignment.CenterStart),
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.outfit_medium)),
                color = Color.White
            )
        }
    }
}


@Composable
private fun PostActions(likes: Int, comments: Int, shares: Int,onShareClick: () -> Unit,
                        onLikeClick: () -> Unit, onCommentClick: () -> Unit, isLike:Boolean,) {
    var isLiked by remember { mutableStateOf(isLike) }
    var showCommentsDialog  by remember { mutableStateOf(false) }
    var deleteComment by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            // Like
            IconTextButton(
                iconRes = if (isLiked) R.drawable.ic_paw_like_filled_icon else R.drawable.ic_paw_like_icon,
                text = likes.toString()/*(if (isLiked) likes + 1 else likes).toString()*/,
                tint = if (isLiked) Color(0xFF00A6B8) else Color.Black
            ) {
                onLikeClick()
                isLiked = !isLiked
            }

            Spacer(modifier = Modifier.width(14.dp))

            // Comment
            IconTextButton(
                iconRes = R.drawable.ic_chat_bubble_icon,
                text = comments.toString(),
                onClick = {
                    onCommentClick()
                    //showCommentsDialog = true
                }
            )

            Spacer(modifier = Modifier.width(14.dp))

            // Share
            IconTextButton(
                iconRes = R.drawable.ic_share_icons,
                text = shares.toString(),
                onClick = { onShareClick() }
            )
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
//            ,onDeleteClick = { deleteComment = true }
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

@Composable
fun IconTextButton(
    iconRes: Int,
    text: String,
    tint: Color = Color.Black,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .width(IntrinsicSize.Min) // keep consistent width for the pair
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) { onClick() }
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            tint = tint,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = text,
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(R.font.outfit_regular)),
            fontWeight = FontWeight.Normal,
            color = tint,
            modifier = Modifier.width(36.dp), // fixed width so "9", "99", "999" don't shift
            textAlign = TextAlign.Start
        )
    }
}




@Preview(showBackground = true, backgroundColor = 0xFFF5F5F5)
@Composable
fun SponsoredPostPreview() {
/*    val dummyPost = PostItem.SponsoredPost(
        userDetail = "Aisuke",
        role = "Pet Lover",
        time = "2 Days",
        title = "Summer Special: 20% Off Grooming!",
        description = "Limited Time Offer",
        mediaList = listOf(
            MediaItem(
                MediaType.IMAGE,
                imageUrl = "https://picsum.photos/400"
            ),
        ),
        likes = 200,
        comments = 100,
        shares = 10
    )*/

    /*Column(modifier = Modifier.background(Color(0xFFF5F5F5)).fillMaxSize()) {
        SponsoredPostItem(post = dummyPost, onReportClick = { }, onShareClick = { }, onProfileClick = { }, onSponsoredClick = {})
    }*/
}
