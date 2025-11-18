package com.bussiness.slodoggiesapp.ui.screens.commonscreens.home.content

import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.OptIn
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
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PlayArrow
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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
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
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.datasource.RawResourceDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.media3.ui.PlayerView
import coil.compose.rememberAsyncImagePainter
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.data.model.common.MediaItem
import com.bussiness.slodoggiesapp.data.model.common.MediaType
import com.bussiness.slodoggiesapp.data.model.common.PostItem
import com.bussiness.slodoggiesapp.data.model.main.UserType
import com.bussiness.slodoggiesapp.ui.component.petOwner.dialog.Comment
import com.bussiness.slodoggiesapp.ui.component.petOwner.dialog.CommentsDialog
import com.bussiness.slodoggiesapp.ui.dialog.DeleteChatDialog
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor
import com.bussiness.slodoggiesapp.util.SessionManager

@Composable
fun NormalPostItem(modifier: Modifier, postItem: com.bussiness.slodoggiesapp.data.model.common.PostItem.NormalPost, onReportClick: () -> Unit, onShareClick: () -> Unit, normalPost : Boolean, onEditClick: () -> Unit, onDeleteClick: () -> Unit, onProfileClick: () -> Unit, onSelfPostEdit: () -> Unit, onSelfPostDelete: () -> Unit) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            PostHeader(user = postItem.user, role = postItem.role, time = postItem.time, postType = postItem.postType, onReportClick = { onReportClick()},normalPost,onEditClick = { onEditClick() },onDeleteClick = { onDeleteClick() },onProfileClick = { onProfileClick() }, onSelfPostDelete = { onSelfPostDelete() }, onSelfPostEdit = { onSelfPostEdit() } )
            PostCaption(caption = postItem.caption, description = postItem.description)
            PostImage(mediaList = postItem.mediaList)
            PostLikes(likes = postItem.likes, comments = postItem.comments, shares = postItem.shares, onShareClick = {onShareClick()}, onSaveClick = { })
        }
    }
}

@Composable
fun PostHeader(user: String, role: String, time: String,postType : String, onReportClick: () -> Unit,normalPost: Boolean,onEditClick: () -> Unit,onDeleteClick: () -> Unit,onProfileClick: () -> Unit,onSelfPostEdit: () -> Unit,onSelfPostDelete: () -> Unit) {
    var isFollowed by remember { mutableStateOf(false) }
    val sessionManager = SessionManager.getInstance(LocalContext.current)
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
            if (normalPost){
                Box(Modifier.width(40.dp).height(40.dp).clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) { onProfileClick() }) {

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
            }else{
                Image(
                    painter = painterResource(id = R.drawable.dummy_person_image2),
                    contentDescription = "Person",
                    modifier = Modifier
                        .size(35.dp)
                        .clip(CircleShape)
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
                      if (postType == "other"){
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

                }
                if (normalPost){
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
                            text = time,
                            fontSize = 12.sp,
                            color = Color(0xFF949494),
                            modifier = Modifier
                                .padding(vertical = 2.dp),
                            fontFamily = FontFamily(Font(R.font.outfit_regular)),
                        )
                    }
                }else{
                    if (sessionManager.getUserType() == UserType.Professional){
                        Text(
                            text = " $time",
                            fontSize = 12.sp,
                            color = Color(0xFF949494),
                            modifier = Modifier
                                .padding(vertical = 2.dp),
                            fontFamily = FontFamily(Font(R.font.outfit_regular)),
                        )
                    }else{
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
                                text = time,
                                fontSize = 12.sp,
                                color = Color(0xFF949494),
                                modifier = Modifier
                                    .padding(vertical = 2.dp),
                                fontFamily = FontFamily(Font(R.font.outfit_regular)),
                            )
                        }
                    }
                }
            }
        }

        if (normalPost){
            if (postType == "other"){
                PostOptionsMenu (modifier = Modifier, onReportClick = onReportClick)
            }else{
                PostContentMenu(modifier = Modifier, onEditClick = { onSelfPostEdit() }, onDeleteClick = { onSelfPostDelete() })
            }

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
            shape = RoundedCornerShape(15.dp) //  outer curve applied
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
            text = caption,
            fontSize = 12.5.sp,
            color = Color.Black,
            fontFamily = FontFamily(Font(R.font.inter_regular)),
            lineHeight = 17.sp,
            maxLines = 1,
        )
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
                text = "Read Less",
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
    var deleteComment by remember { mutableStateOf(false) }
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
            // Like section
            Icon(
                painter = painterResource(
                    id = if (isLiked) R.drawable.ic_paw_like_filled_icon
                    else R.drawable.ic_paw_like_icon
                ),
                contentDescription = "Paw",
                modifier = Modifier
                    .size(24.dp)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
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
                color = if (isLiked) PrimaryColor else Color.Black,
                modifier = Modifier.widthIn(min = 24.dp) //  reserve width
            )
            Spacer(modifier = Modifier.width(14.dp))

            // Comments section
            Icon(
                painter = painterResource(id = R.drawable.ic_chat_bubble_icon),
                contentDescription = "Comments",
                modifier = Modifier
                    .size(24.dp)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) {
                        showCommentsDialog = true
                    }
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = comments.toString(),
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.outfit_regular)),
                color = Color.Black,
                modifier = Modifier.widthIn(min = 24.dp) //  reserve width
            )
            Spacer(modifier = Modifier.width(14.dp))

            // Shares section
            Icon(
                painter = painterResource(id = R.drawable.ic_share_icons),
                contentDescription = "Shares",
                modifier = Modifier
                    .size(24.dp)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) {
                        onShareClick()
                    }
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = shares.toString(),
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.outfit_regular)),
                color = Color.Black,
                modifier = Modifier.widthIn(min = 24.dp) //  reserve width
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
            Icon(
                painter = if (isBookmarked) painterResource(id = R.drawable.ic_bookmark_selected ) else painterResource(id = R.drawable.ic_bookmark_icon),
                contentDescription = "Bookmark",
                tint = Color.Unspecified,
                modifier = Modifier.wrapContentSize()
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {  isBookmarked = !isBookmarked
                        if (isBookmarked){
                            onSaveClick()
                        } }
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
            onDismiss = { showCommentsDialog = false },
            onDeleteClick = { deleteComment = true }
        )
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


@OptIn(UnstableApi::class)
@Composable
fun PostImage(
    mediaList: List<com.bussiness.slodoggiesapp.data.model.common.MediaItem>,
    modifier: Modifier = Modifier,
    onVideoPlay: (() -> Unit)? = null,
) {
    val context = LocalContext.current
    val pagerState = rememberPagerState { mediaList.size }
    var currentPage by rememberSaveable{ mutableIntStateOf(0) }

    // Create and remember ExoPlayers for each video
    val players = remember {
        mediaList.map { item ->
            if (item.type == com.bussiness.slodoggiesapp.data.model.common.MediaType.VIDEO) {
                ExoPlayer.Builder(context).build().apply {
                    val uri = when {
                        item.videoUrl != null -> item.videoUrl.toUri()
                        item.videoRes != null -> RawResourceDataSource.buildRawResourceUri(item.videoRes)
                        else -> null
                    }
                    uri?.let {
                        val dataSourceFactory = DefaultDataSource.Factory(context)
                        val mediaItem = androidx.media3.common.MediaItem.fromUri(it)
                        val mediaSource = ProgressiveMediaSource.Factory(dataSourceFactory)
                            .createMediaSource(mediaItem)
                        setMediaSource(mediaSource)
                        prepare()
                    }
                    playWhenReady = false
                    repeatMode = Player.REPEAT_MODE_ONE
                }
            } else null
        }
    }

    // Pause other videos when changing pages
    LaunchedEffect(pagerState.currentPage) {
        currentPage = pagerState.currentPage
        players.forEach { it?.pause() }
    }

    // Release players when composable leaves composition
    DisposableEffect(Unit) {
        onDispose { players.forEach { it?.release() } }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(4 / 3f)
            .background(Color.LightGray)
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            val item = mediaList[page]
            val player = players.getOrNull(page)

            when (item.type) {
                com.bussiness.slodoggiesapp.data.model.common.MediaType.IMAGE -> {
                    val painter = when {
                        item.imageUrl != null -> rememberAsyncImagePainter(item.imageUrl)
                        item.imageRes != null -> painterResource(item.imageRes)
                        else -> painterResource(R.drawable.dog_ic)
                    }
                    Image(
                        painter = painter,
                        contentDescription = "Post Image ${page + 1}",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                com.bussiness.slodoggiesapp.data.model.common.MediaType.VIDEO -> {
                    // Show thumbnail if video is paused
                    if (player != null && !player.isPlaying) {
                        val thumbnail = when {
                            item.thumbnailRes != null -> painterResource(item.thumbnailRes)
                            item.imageUrl != null -> rememberAsyncImagePainter(item.imageUrl)
                            else -> painterResource(R.drawable.dog2)
                        }
                        Image(
                            painter = thumbnail,
                            contentDescription = "Video Thumbnail",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    // Player view
                    if (player != null) {
                        AndroidView(
                            factory = {
                                PlayerView(it).apply {
                                    this.player = player
                                    useController = false
                                    layoutParams = FrameLayout.LayoutParams(
                                        ViewGroup.LayoutParams.MATCH_PARENT,
                                        ViewGroup.LayoutParams.MATCH_PARENT
                                    )
                                }
                            },
                            modifier = Modifier.fillMaxSize()
                        )

                        // Overlay play button
                        if (!player.isPlaying) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Color.Black.copy(alpha = 0.3f)),
                                contentAlignment = Alignment.Center
                            ) {
                                IconButton(
                                    onClick = {
                                        player.play()
                                        onVideoPlay?.invoke()
                                    },
                                    modifier = Modifier
                                        .size(60.dp)
                                        .background(
                                            Color.White.copy(alpha = 0.9f),
                                            CircleShape
                                        )
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.PlayArrow,
                                        contentDescription = "Play Video",
                                        tint = Color(0xFF0A3D62),
                                        modifier = Modifier.size(36.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // Page Indicator
        if (mediaList.size > 1) {
            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 10.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(mediaList.size) { index ->
                    val active = index == currentPage
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 3.dp)
                            .height(4.dp)
                            .width(if (active) 18.dp else 6.dp)
                            .clip(RoundedCornerShape(2.dp))
                            .background(
                                if (active) Color(0xFF258694)
                                else Color.White.copy(alpha = 0.6f)
                            )
                    )
                }
            }
        }
    }
}




// Extension to check if ExoPlayer is playing
val ExoPlayer.isPlaying: Boolean
    get() = this.isPlaying
