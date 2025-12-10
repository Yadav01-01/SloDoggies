package com.bussiness.slodoggiesapp.ui.screens.commonscreens.home.content

import android.graphics.Bitmap
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
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.runtime.mutableFloatStateOf
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.zIndex
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.data.newModel.home.PostItem
import com.bussiness.slodoggiesapp.data.newModel.home.PostMediaResponse
import com.bussiness.slodoggiesapp.ui.component.petOwner.dialog.Comment
import com.bussiness.slodoggiesapp.ui.dialog.DeleteChatDialog
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor
import com.bussiness.slodoggiesapp.ui.theme.TextGrey
import com.bussiness.slodoggiesapp.util.SessionManager
import kotlinx.coroutines.delay

@Composable
fun NormalPostItem(modifier: Modifier, postItem: PostItem.NormalPost, onReportClick: () -> Unit,
                   onShareClick: () -> Unit, normalPost : Boolean,
                   onEditClick: () -> Unit, onDeleteClick: () -> Unit,
                   onProfileClick: () -> Unit, onSelfPostEdit: () -> Unit,
                   onSelfPostDelete: () -> Unit,
                   onSaveClick:()-> Unit,
                   onLikeClick:()-> Unit,
                   onCommentClick:()-> Unit,
                   onFollowingClick:()->Unit) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {

        Column(modifier = Modifier.fillMaxWidth()) {
            PostHeader(userId = postItem.userId,user = postItem.userName,petName = postItem.petName,
                personImage =postItem.media?.parentImageUrl ?: "", dogImage = postItem.media?.petImageUrl ?: "",
                time = postItem.time, postType = postItem.type, onReportClick = { onReportClick()},normalPost,
                onEditClick = { onEditClick() },onDeleteClick = { onDeleteClick() },
                onProfileClick = { onProfileClick() },
                onSelfPostDelete = { onSelfPostDelete() },
                onSelfPostEdit = { onSelfPostEdit() },
                iAmFollowing = postItem.iAmFollowing,
                onFollowingClick = {
                    onFollowingClick()
                })
            PostCaption(caption = postItem.caption, description = postItem.description,hashTags = postItem.hashtags.orEmpty()  )
            PostImage(mediaList = postItem.mediaList)
            PostLikes(likes = postItem.likes, comments = postItem.comments,
                shares = postItem.shares,
                onShareClick = {onShareClick()}, onSaveClick = { onSaveClick() },
                onLikeClick = { onLikeClick() },
                onCommentClick = { onCommentClick() },
                isLike = postItem.isLiked,
                isSave = postItem.isSaved)
        }
    }
}

@Composable
fun PostHeader(userId : String ,user: String,petName : String, personImage : String ,
               dogImage: String , time: String,postType : String,
               onReportClick: () -> Unit,normalPost: Boolean,onEditClick: () ->
    Unit,onDeleteClick: () -> Unit,onProfileClick: () -> Unit,onSelfPostEdit: () ->
    Unit,onSelfPostDelete: () -> Unit,
               iAmFollowing:Boolean,
               onFollowingClick: () -> Unit) {
    var isFollowed by remember { mutableStateOf(iAmFollowing) }
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

                    AsyncImage(
                        model = personImage ,
                        placeholder = painterResource(R.drawable.ic_person_icon),
                        error = painterResource(R.drawable.ic_person_icon),
                        contentDescription = "Person",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(30.dp)
                            .clip(CircleShape)
                            .background(TextGrey)
                            .align(Alignment.TopStart)
                    )

                    // Front image (dog in FULL COLOR with white border)
                    AsyncImage(
                        model = dogImage,
                        placeholder = painterResource(R.drawable.ic_dog_face_icon),
                        error = painterResource(R.drawable.ic_dog_face_icon),
                        contentScale = ContentScale.Crop,
                        contentDescription = "Dog",
                        modifier = Modifier
                            .size(30.dp)
                            .clip(CircleShape)
                            .background(TextGrey)
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
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle( fontFamily = FontFamily(Font(R.font.outfit_medium)),fontWeight = FontWeight.Medium)) {
                                append(user)
                            }
                            withStyle(style = SpanStyle( fontFamily = FontFamily(Font(R.font.outfit_regular)),fontWeight = FontWeight.Normal)) {
                                append(" with ")
                            }
                            withStyle(style = SpanStyle( fontFamily = FontFamily(Font(R.font.outfit_medium)),fontWeight = FontWeight.Medium)) {
                                append(petName)
                            }
                        },
                        fontSize = 12.sp,
                        color = Color.Black,
                        lineHeight = 16.sp,
                        modifier = Modifier.widthIn(max = 150.dp),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    val interactionSource = remember { MutableInteractionSource() }

                    if (normalPost){
                      if (sessionManager.getUserId() != userId){
                          OutlinedButton(
                              onClick = {
                                  onFollowingClick()
                                  isFollowed = !isFollowed
                                        },
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

        if (normalPost){
            if (sessionManager.getUserId() != userId){
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
private fun PostCaption(caption: String, description: String, hashTags: String) {

    var showFullText by remember { mutableStateOf(false) }
    var isTextOverflowing by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp)
    ) {

        // Caption
        Text(
            text = caption,
            fontSize = 12.5.sp,
            color = Color.Black,
            fontFamily = FontFamily(Font(R.font.inter_regular)),
            lineHeight = 17.sp,
            maxLines = 1
        )

        // Description with measuring overflow
        Text(
            text = description,
            fontSize = 12.5.sp,
            color = Color.Black,
            fontFamily = FontFamily(Font(R.font.inter_regular)),
            lineHeight = 17.sp,
            modifier = Modifier.fillMaxWidth(),
            maxLines = if (showFullText) Int.MAX_VALUE else 2,
            overflow = TextOverflow.Ellipsis,
            onTextLayout = { textLayoutResult ->
                isTextOverflowing = textLayoutResult.lineCount > 2
            }
        )

        // Hashtags
        Text(
            text = hashTags,
            fontSize = 12.5.sp,
            color = PrimaryColor,
            fontFamily = FontFamily(Font(R.font.inter_regular)),
            lineHeight = 17.sp
        )
        Spacer(Modifier.height(5.dp))

        // Show "Read More" only if overflowing
        if (isTextOverflowing && !showFullText) {
            Text(
                text = "Read More",
                fontSize = 12.sp,
                color = PrimaryColor,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.clickable { showFullText = true }
            )
        }

        if (showFullText) {
            Text(
                text = "Show Less",
                fontSize = 12.sp,
                color = PrimaryColor,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.clickable { showFullText = false }
            )
        }
    }
}



@Composable
 fun PostLikes(likes: Int, comments: Int,
               isLike:Boolean,isSave:Boolean, shares: Int,
               onShareClick: () -> Unit,onSaveClick: () -> Unit,onLikeClick: () -> Unit,
               onCommentClick: () -> Unit) {
    var isLiked by remember { mutableStateOf(isLike) }
    var isBookmarked by remember { mutableStateOf(isSave) }
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
                        onLikeClick()
                        isLiked = !isLiked // Toggle the liked state
                    },
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = likes.toString()/*(if (isLiked) likes + 1 else likes).toString()*/,
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
                        onCommentClick()
                        //showCommentsDialog = true
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
                    ) {
                        isBookmarked = !isBookmarked

                        onSaveClick()
//                        if (isBookmarked){
//                            onSaveClick()
//                        }
                    }
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
//        CommentsDialog(
//            comments = sampleComments,
//            onDismiss = { showCommentsDialog = false },
//            onDeleteClick = { deleteComment = true }
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


@OptIn(UnstableApi::class)
@Composable
fun PostImage(
    mediaList: List<PostMediaResponse>,
    modifier: Modifier = Modifier,
    onVideoPlay: (() -> Unit)? = null,
) {
    val context = LocalContext.current
    val pagerState = rememberPagerState { mediaList.size }
    var currentPage by rememberSaveable { mutableIntStateOf(0) }

    val imageLoader = remember {
        ImageLoader.Builder(context)
            .crossfade(true)
            .bitmapConfig(Bitmap.Config.RGB_565)
            .build()
    }

    fun isVideo(url: String) = url.endsWith(".mp4") || url.endsWith(".mov") || url.endsWith(".webm")

    val players = remember { mutableMapOf<Int, ExoPlayer>() }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(350.dp)
    ) {

        /** ---------------- PAGE VIEWER ---------------- **/
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->

            val media = mediaList[page]
            val url = media.mediaUrl.orEmpty()

            if (!isVideo(url)) {

                AsyncImage(
                    model = ImageRequest.Builder(context).data(url).crossfade(true).build(),
                    contentDescription = null,
                    imageLoader = imageLoader,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

            }
            else
            {

                val player = remember(url) {
                    players[page] ?: ExoPlayer.Builder(context).build().apply {
                        setMediaItem(MediaItem.fromUri(url))
                        prepare()
                        playWhenReady = false
                        repeatMode = Player.REPEAT_MODE_OFF
                    }.also { players[page] = it }
                }

                val thumbnailUrl = media.thumbnailUrl
                Log.d("*******",thumbnailUrl.toString())

                var showThumb by remember { mutableStateOf(true) }
                var progress by remember { mutableFloatStateOf(0f) }

                DisposableEffect(player) {
                    val listener = object : Player.Listener {
                        override fun onPlaybackStateChanged(state: Int) {
                            if (state == Player.STATE_ENDED) {
                                showThumb = true
                                player.seekTo(0)
                                player.playWhenReady = false   // Auto play off
                                player.pause()                 // Stop the player
                            }
                        }
                    }
                    player.addListener(listener)
                    onDispose { player.removeListener(listener) }
                }

                LaunchedEffect(player) {
                    while (true) {
                        if (player.isPlaying) {
                            val current = player.currentPosition
                            val total = player.duration.takeIf { it > 0 } ?: 1L
                            progress = (current.toFloat() / total.toFloat())
                        }
                        delay(200)
                    }
                }

                /** ---- VIDEO UI LAYER ---- **/
                Box(modifier = Modifier.fillMaxSize()) {

                    AndroidView(
                        modifier = Modifier.fillMaxSize(),
                        factory = {
                            PlayerView(it).apply {
                                this.player = player
                                useController = false
                            }
                        }
                    )

                    if (showThumb) {
                        AsyncImage(
                            model = thumbnailUrl,
                            contentDescription = null,
                            placeholder = painterResource(R.drawable.no_image),
                            error = painterResource(R.drawable.no_image),
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop,

                        )
                        Box(
                            Modifier.fillMaxSize()
                                .background(Color.Black.copy(alpha = 0.25f))
                        )
                    }

                    if (showThumb) {
                        IconButton(
                            onClick = {
                                showThumb = false
                                player.play()
                            },
                            modifier = Modifier
                                .align(Alignment.Center)
                                .size(58.dp)
                                .background(Color.White, CircleShape)
                        ) {
                            Icon(
                                Icons.Default.PlayArrow,
                                contentDescription = null,
                                tint = Color.Black,
                                modifier = Modifier.size(38.dp)
                            )
                        }
                    }

                    if (!showThumb) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(4.dp)
                                .align(Alignment.BottomCenter)
                                .background(Color.White.copy(alpha = 0.3f))
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .fillMaxWidth(progress)
                                    .background(Color(0xFF258694))
                            )
                        }
                    }
                }
            }
        }

        /** ---------- FIXED PAGE INDICATOR (Always visible) ---------- **/
        // ---------- PAGE INDICATOR (CENTER MIDDLE) ----------
        if (mediaList.size > 1) {
            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)   // 👈 center of image
                    .padding(bottom = 10.dp)
                    .zIndex(999f),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(mediaList.size) { index ->
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 4.dp)
                            .height(5.dp)
                            .width(if (index == currentPage) 15.dp else 5.dp)
                            .clip(RoundedCornerShape(50))
                            .background(
                                if (index == currentPage) Color(0xFF258694)  // blue like sample
                                else Color.White.copy(alpha = 0.9f)         // white dots
                            )
                    )
                }
            }
        }


        /** Pause other videos **/
        LaunchedEffect(pagerState.currentPage) {
            currentPage = pagerState.currentPage
            players.forEach { (index, player) ->
                if (index != currentPage) player.pause()
            }
        }

        /** Cleanup **/
        DisposableEffect(Unit) {
            onDispose { players.values.forEach { it.release() } }
        }
    }
}

