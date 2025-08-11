package com.bussiness.slodoggiesapp.ui.screens.petowner

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.model.common.MediaItem
import com.bussiness.slodoggiesapp.model.common.MediaType
import com.bussiness.slodoggiesapp.model.common.PostData


@Composable
fun SocialMediaPostSponsored(post: PostData) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            // Header with profile info
            PostHeaderSponsored(
                user = post.user,
                time = post.time
            )

            // Caption
            PostCaptionSponsored(
                caption = post.caption,
                description = post.description
            )

            // Main image with sponsored overlay
            PostImageSponsored(mediaList = post.mediaList)

            // Like count and interactions
            PostLikesSponsored(
                likes = post.likes,
                comments = post.comments,
                shares = post.shares
            )
        }
    }
}

@Composable
private fun PostHeaderSponsored(user: String, time: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Profile image
            Image(
                painter = painterResource(id = R.drawable.dummy_person_image1), // Replace with your image
                contentDescription = "Profile",
                modifier = Modifier
                    .size(30.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(
                    text = user,
                    fontSize = 12.sp,
                    color = Color.Black,
                    fontFamily = FontFamily(Font(R.font.outfit_medium))
                )

                Text(
                    text = time,
                    fontSize = 12.sp,
                    color = Color(0xFF949494),
                    fontFamily = FontFamily(Font(R.font.outfit_regular))
                )
            }
        }

        // More options menu
        IconButton(
            onClick = { /* Handle menu */ },
            modifier = Modifier.size(24.dp)
        ) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "More options",
                tint = Color.Black
            )
        }
    }
}

@Composable
private fun PostCaptionSponsored(caption: String, description: String) {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = caption,
            fontSize = (12.24).sp,
            lineHeight = 17.sp,
            color = Color.Black,
            fontFamily = FontFamily(Font(R.font.outfit_bold))
        )

        if (description.isNotEmpty()) {
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = description,
                fontSize = (12.24).sp,
                lineHeight = 17.sp,
                color = Color.Black,
                fontFamily = FontFamily(Font(R.font.outfit_regular))
            )
        }
    }
}

@Composable
private fun PostImageSponsored(mediaList: List<MediaItem>) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(353.dp)
    ) {
        // Main image
        Image(
            painter = painterResource(
                id = mediaList.firstOrNull()?.resourceId ?: R.drawable.dummy_baby_pic
            ), // Replace with puppies image
            contentDescription = "Post image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Sponsored overlay at the bottom
        Box(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .fillMaxWidth().height(50.dp)
                .background(Color(0xFF258694)
                )

        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "Sponsored",
                        fontSize = 16.sp,
                        color = Color.White,
                       fontFamily = FontFamily(Font(R.font.outfit_medium))
                    )



            }
        }
    }
}

@Composable
private fun PostLikesSponsored(likes: Int, comments: Int, shares: Int) {
    var isLiked by remember { mutableStateOf(false) }
    var isBookmarked by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Like button with paw icon
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable { isLiked = !isLiked }
            ) {
                Icon(
                    painter = painterResource(id = if (isLiked) R.drawable.ic_paw_like_filled_icon else R.drawable.ic_paw_like_icon), // Replace with paw icon
                    contentDescription = "Like",
                    modifier = Modifier.size(24.dp),
                    tint = if (isLiked) Color(0xFF00A6B8) else Color.Black
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = (if (isLiked) likes + 1 else likes).toString(),
                    fontSize = 14.sp,
                    color = if (isLiked) Color(0xFF00A6B8) else Color.Black,
                    fontFamily = FontFamily(Font(R.font.outfit_regular))
                )
            }

            Spacer(modifier = Modifier.width(20.dp))

            // Comments
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_chat_bubble_icon), // Replace with comment icon
                    contentDescription = "Comments",
                    modifier = Modifier.size(24.dp),
                    tint = Color.Black
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = comments.toString(),
                    fontSize = 14.sp,
                    color = Color.Black,
                    fontFamily = FontFamily(Font(R.font.outfit_regular))
                )
            }

            Spacer(modifier = Modifier.width(20.dp))

            // Shares
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_share_icons), // Replace with share icon
                    contentDescription = "Share",
                    modifier = Modifier.size(24.dp),
                    tint = Color.Black
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = shares.toString(),
                    fontSize = 14.sp,
                    color = Color.Black,
                    fontFamily = FontFamily(Font(R.font.outfit_regular))
                )
            }
        }

        // Bookmark
        IconButton(
            onClick = { isBookmarked = !isBookmarked },
            modifier = Modifier.size(32.dp)
        ) {
            Icon(
                painter = painterResource(id = if (isBookmarked) R.drawable.ic_bookmark_selected else R.drawable.ic_bookmark_icon), // Replace with bookmark icon
                contentDescription = "Bookmark",
                modifier = Modifier.size(24.dp),
                tint = if (isBookmarked) Color(0xFF00A6B8) else Color.Black
            )
        }
    }
}


// Preview function
@Preview(showBackground = true, backgroundColor = 0xFFF5F5F5)
@Composable
fun SocialMediaPostSponsoredPreview() {
    val samplePost = PostData(
        user = "Nobiaule",
        role = "Pet Dad", // Not used in sponsored version but keeping for consistency
        time = "2 Days",
        caption = "Summer Special: 20% Off Grooming!",
        description = "Limited Time Offer",
        mediaList = listOf(
            MediaItem(
                resourceId = R.drawable.dummy_baby_pic,
                type = MediaType.IMAGE
            )
        ),
        likes = 200,
        comments = 100,
        shares = 10
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
    ) {
        SocialMediaPostSponsored(post = samplePost)
    }
}
