package com.bussiness.slodoggiesapp.ui.screens.commonscreens.home.content

import android.widget.MediaController
import android.widget.VideoView
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.net.toUri
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.model.common.MediaItem
import com.bussiness.slodoggiesapp.model.common.MediaType
import com.bussiness.slodoggiesapp.model.common.PostItem
import com.bussiness.slodoggiesapp.ui.component.petOwner.Dialog.Comment
import com.bussiness.slodoggiesapp.ui.component.petOwner.Dialog.CommentsDialog
import com.bussiness.slodoggiesapp.ui.component.shareApp
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor

@Composable
fun NormalPostItem(modifier: Modifier,postItem: PostItem.NormalPost,onReportClick: () -> Unit,onShareClick: () -> Unit,normalPost : Boolean,onEditClick: () -> Unit,onDeleteClick: () -> Unit) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            PostHeader(user = postItem.user, role = postItem.role, time = postItem.time, onReportClick = { onReportClick()},normalPost,onEditClick = { onEditClick() },onDeleteClick = { onDeleteClick() } )
            PostCaption(caption = postItem.caption, description = postItem.description)
            PostImage(mediaList = postItem.mediaList)
            PostLikes(likes = postItem.likes, comments = postItem.comments, shares = postItem.shares, onShareClick = {onShareClick()}, onSaveClick = { })
        }
    }
}

@Composable
fun PostHeader(user: String, role: String, time: String, onReportClick: () -> Unit,normalPost: Boolean,onEditClick: () -> Unit,onDeleteClick: () -> Unit) {
    var isFollowed by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp).padding(top = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(Modifier.width(40.dp).height(40.dp)) {

                Image(
                    painter = painterResource(id = R.drawable.dummy_person_image2),
                    contentDescription = "Person",
                    modifier = Modifier
                        .size(30.dp)
                        .clip(CircleShape)
                        .align(Alignment.TopStart)
                )

                // Front image (dog in FULL COLOR with white border)
                Image(
                    painter = painterResource(id = R.drawable.dummy_person_image1),
                    contentDescription = "Dog",
                    modifier = Modifier
                        .size(30.dp)
                        .clip(CircleShape)
                        .border(2.dp, Color.White, CircleShape)
                        .align(Alignment.BottomEnd)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = user,
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(R.font.outfit_medium)),
                        color = Color.Black,
                        lineHeight = 16.sp,
                        modifier = Modifier.widthIn(max = 150.dp),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.width(8.dp))

                    val interactionSource = remember { MutableInteractionSource() }

                    if (normalPost){
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

                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = role,
                        fontSize = 8.sp,
                        color = Color(0xFF258694),
                        lineHeight = 20.sp,
                        fontFamily = FontFamily(Font(R.font.outfit_medium)),
                        modifier = Modifier
                            .background(color = Color(0xFFE5EFF2), shape = RoundedCornerShape(50))
                            .padding(horizontal = 8.dp, vertical = 0.dp)
                    )

                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = " $time",
                        fontSize = 12.sp,
                        color = Color(0xFF949494),
                        modifier = Modifier
                            .padding(vertical = 2.dp),
                        fontFamily = FontFamily(Font(R.font.outfit_regular)),
                    )
                }
            }
        }

        if (normalPost){
            PostOptionsMenu (modifier = Modifier, onReportClick = onReportClick)
        }else{
            PostContentMenu(modifier = Modifier, onEditClick = { onEditClick() }, onDeleteClick = { onDeleteClick() })
        }

    }
}

@Composable
fun PostOptionsMenu(
    modifier: Modifier,
    onReportClick: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = modifier.wrapContentSize().background(Color.White)) {
        IconButton(
            onClick = { expanded = true },
            modifier = Modifier.size(24.dp)
        ) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "More options",
                tint = Color.Black
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            containerColor = Color.White
        ) {
            DropdownMenuItem(
                text = { Text(text = "Report Post",
                    fontFamily = FontFamily(Font(R.font.outfit_regular)),
                    color = Color.Black,
                    fontSize = 16.sp)},
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_report_icon),
                        contentDescription = "Report",
                        tint = Color.Unspecified,
                        modifier = Modifier.size(18.dp)
                    )
                },
                onClick = {
                    expanded = false
                    onReportClick()
                }
            )
        }
    }
}

@Composable
fun PostContentMenu(
    modifier: Modifier = Modifier,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = modifier.wrapContentSize(Alignment.TopEnd)
    ) {
        IconButton(
            onClick = { expanded = true },
            modifier = Modifier.size(32.dp)
        ) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "More options",
                tint = Color.Black
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            containerColor = Color.White,
            shape = RoundedCornerShape(15.dp) // ✅ outer curve applied
        ) {
            DropdownMenuItem(
                text = {
                    Text(
                        text = "Edit",
                        fontFamily = FontFamily(Font(R.font.outfit_regular)),
                        color = Color.Black,
                        fontSize = 16.sp
                    )
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.edit_ic_p),
                        contentDescription = "Edit",
                        tint = Color.Unspecified,
                        modifier = Modifier.size(20.dp)
                    )
                },
                onClick = {
                    expanded = false
                    onEditClick()
                }
            )
            DropdownMenuItem(
                text = {
                    Text(
                        text = "Delete",
                        fontFamily = FontFamily(Font(R.font.outfit_regular)),
                        color = Color.Black,
                        fontSize = 16.sp
                    )
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.delete_mi),
                        contentDescription = "Delete",
                        tint = Color.Unspecified,
                        modifier = Modifier.size(20.dp)
                    )
                },
                onClick = {
                    expanded = false
                    onDeleteClick()
                }
            )
        }
    }
}





@Composable
private fun PostCaption(caption: String, description: String) {
    // Add state to track if the full text should be shown
    var showFullText by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.padding(horizontal = 12.dp, vertical = 0.dp)
    ) {

        Text(
            text = description,
            fontSize = 12.5.sp,
            color = Color.Black,
            fontFamily = FontFamily(Font(R.font.inter_regular)),
            lineHeight = 17.sp,
            maxLines = if (showFullText) Int.MAX_VALUE else 2,
            overflow = if (showFullText) TextOverflow.Visible else TextOverflow.Ellipsis
        )

//        Text(
//            text = hashtags,
//            fontSize = 12.5.sp,
//            color = PrimaryColor,
//            fontFamily = FontFamily(Font(R.font.inter_regular)),
//            lineHeight = 17.sp,
//            overflow = TextOverflow.Ellipsis
//        )

        // Only show "Read More" if text is truncated
        if (!showFullText) {
            Text(
                text = "Read More",
                fontSize = 12.sp,
                color = PrimaryColor,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.clickable {
                    showFullText = true
                }
            )
        } else {
            // Optional: Add "Show Less" functionality
            Text(
                text = "Show Less",
                fontSize = 12.sp,
                color = PrimaryColor,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.clickable {
                    showFullText = false
                }
            )
        }
    }
}


@Composable
 fun PostLikes(likes: Int, comments: Int, shares: Int,onShareClick: () -> Unit,onSaveClick: () -> Unit) {
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
                modifier = Modifier.size(25.dp).clickable(
                    indication = null,
                    interactionSource =  remember { MutableInteractionSource() }
                ) {
                    isLiked = !isLiked // Toggle the liked state
                },
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = (if (isLiked) likes + 1 else likes).toString(),
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.outfit_regular)),
                fontWeight = FontWeight.Normal,
                color = if (isLiked) PrimaryColor else Color.Black
            )
            Spacer(modifier = Modifier.width(16.dp))

            // Comments section
            Icon(
                painter = painterResource(id = R.drawable.ic_chat_bubble_icon),
                contentDescription = "Comments",
                modifier = Modifier.size(24.dp).clickable(
                    indication = null,
                    interactionSource =  remember { MutableInteractionSource() }
                ) {
                    // isLiked = !isLiked // Toggle the liked state
                    showCommentsDialog = true
                }
            )

            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = comments.toString(),
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.outfit_regular)),
                color = Color.Black
            )
            Spacer(modifier = Modifier.width(16.dp))

            // Shares section
            Icon(
                painter = painterResource(id = R.drawable.ic_share_icons),
                contentDescription = "Shares",
                modifier = Modifier.size(25.dp).clickable (
                    indication = null,
                    interactionSource =  remember { MutableInteractionSource() }
                ){
                   onShareClick()
                }
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = shares.toString(),
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.outfit_regular)),
                color = Color.Black
            )
        }

        Row (verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable {  isBookmarked = !isBookmarked  }){
            Text(text = stringResource(R.string.save),
                fontFamily = FontFamily(Font(R.font.outfit_regular)),
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                color = if (isBookmarked) PrimaryColor else Color.Black
            )
            // Bookmark icon aligned to end
            IconButton(
                onClick = { isBookmarked = !isBookmarked
                    if (isBookmarked){
                        onSaveClick()
                    } },
                modifier = Modifier.size(25.dp)
            ) {
                Icon(
                    painter = if (isBookmarked) painterResource(id = R.drawable.ic_bookmark_selected ) else painterResource(id = R.drawable.ic_bookmark_icon),
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
                user = true,
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

@Composable
fun PostImage(
    mediaList: List<MediaItem> = listOf(
        MediaItem(R.drawable.dummy_person_image3, MediaType.IMAGE),
        MediaItem(R.drawable.dummy_person_image2, MediaType.IMAGE),
        MediaItem(R.drawable.dummy_person_image3, MediaType.VIDEO, R.raw.dummy_video_thumbnail)
    )
) {
    val pagerState = rememberPagerState(pageCount = { mediaList.size })
    var currentIndex by remember { mutableIntStateOf(0) }
    var isVideoPlaying by remember { mutableStateOf(false) }
    var currentVideoPage by remember { mutableIntStateOf(-1) }

    // Sync current index with pager state
    LaunchedEffect(pagerState.currentPage) {
        currentIndex = pagerState.currentPage
        if (mediaList[pagerState.currentPage].type != MediaType.VIDEO) {
            isVideoPlaying = false
            currentVideoPage = -1
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
    ) {
        // HorizontalPager for swipe functionality
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            val mediaItem = mediaList[page]

            Box(modifier = Modifier.fillMaxSize()) {
                when (mediaItem.type) {
                    MediaType.IMAGE -> {
                        Image(
                            painter = painterResource(id = mediaItem.resourceId),
                            contentDescription = "Post image ${page + 1}",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                    MediaType.VIDEO -> {
                        if (isVideoPlaying && currentVideoPage == page) {
                            // Video player
                            AndroidView(
                                factory = { context ->
                                    VideoView(context).apply {
                                        setMediaController(MediaController(context))
                                        setVideoURI(
                                            "android.resource://${context.packageName}/${mediaItem.videoResourceId}".toUri()
                                        )
                                        setOnPreparedListener { mp ->
                                            mp.isLooping = true
                                            start()
                                        }
                                        setOnCompletionListener { mp ->
                                            mp.seekTo(0)
                                            mp.start()
                                        }
                                    }
                                },
                                modifier = Modifier.fillMaxSize()
                            )
                        } else {
                            // Video thumbnail
                            Image(
                                painter = painterResource(id = mediaItem.thumbnailResourceId ?: mediaItem.resourceId),
                                contentDescription = "Video thumbnail ${page + 1}",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )

                            // Play button overlay for video
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Color.Black.copy(alpha = 0.3f)),
                                contentAlignment = Alignment.Center
                            ) {
                                IconButton(
                                    onClick = {
                                        isVideoPlaying = true
                                        currentVideoPage = page
                                    },
                                    modifier = Modifier
                                        .size(60.dp)
                                        .background(
                                            Color.Black.copy(alpha = 0.6f),
                                            CircleShape
                                        )
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.PlayArrow,
                                        contentDescription = "Play video",
                                        tint = Color.White,
                                        modifier = Modifier.size(32.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // Show indicators only if there are multiple items
        if (mediaList.size > 1 && !(isVideoPlaying && currentVideoPage == currentIndex)) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 16.dp)
                    .background(
                       color = Color.Transparent
                    )
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    repeat(mediaList.size) { index ->
                        // Dot indicator
                        if (index == currentIndex) {
                            // Active indicator (elongated)
                            Box(
                                modifier = Modifier
                                    .size(width = 18.dp, height = 6.dp)
                                    .clip(RoundedCornerShape(3.dp))
                                    .background(Color(0xFF258694))
                            )
                        } else {
                            // Inactive indicator (circular)
                            Box(
                                modifier = Modifier
                                    .size(6.dp)
                                    .clip(CircleShape)
                                    .background(Color.White.copy(alpha = 0.6f))
                            )
                        }

                        // Add spacing between dots except the last one
                        if (index < mediaList.size - 1) {
                            Spacer(modifier = Modifier.width(6.dp))
                        }
                    }
                }
            }
        }

        // Media type indicator (shows for video when not playing)
        if (mediaList.getOrNull(currentIndex)?.type == MediaType.VIDEO &&
            !(isVideoPlaying && currentVideoPage == currentIndex)) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(12.dp)
                    .background(
                        Color.Black.copy(alpha = 0.6f),
                        RoundedCornerShape(4.dp)
                    )
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = "Video",
                        tint = Color.White,
                        modifier = Modifier.size(12.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Video",
                        color = Color.White,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }

        // Close button for video player
        if (isVideoPlaying && currentVideoPage == currentIndex) {
            IconButton(
                onClick = {
                    isVideoPlaying = false
                    currentVideoPage = -1
                },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(12.dp)
                    .background(
                        Color.Black.copy(alpha = 0.6f),
                        CircleShape
                    )
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_cross_icon),
                    contentDescription = "Close video",
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}