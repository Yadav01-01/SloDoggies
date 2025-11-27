package com.bussiness.slodoggiesapp.ui.component.common


import android.os.Build
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import coil.compose.SubcomposeAsyncImage
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.data.newModel.eventmodel.DataX
import com.bussiness.slodoggiesapp.data.newModel.eventmodel.GetEventImage
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor
import com.bussiness.slodoggiesapp.ui.theme.TextGrey
import com.bussiness.slodoggiesapp.util.LocationUtils.Companion.convertDateMMDDYYYY
import com.bussiness.slodoggiesapp.util.LocationUtils.Companion.isVideoFile


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EventCard(
    event: DataX,
    selectedOption: String,
    onButtonClick: (DataX) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White // Light gray background
        ),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column {
            // Network image
           /* SubcomposeAsyncImage(
                model = event.get_event_image?.get(0)?.media_path ?: "",
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .padding(10.dp)
                    .clip(RoundedCornerShape(8.dp)),
                loading = {
                    // Show loader
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                },
                error = {
                    Image(
                        painterResource(R.drawable.no_image),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            )*/
            event.get_event_image?.let { PostMediaSlider(it,modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .clip(RoundedCornerShape(8.dp))) }

            // Text Content
            Column(modifier = Modifier.padding(12.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ){
                    Text(
                        text = event.event_title?:"",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Medium,
                        fontFamily = FontFamily(Font(R.font.outfit_medium)),
                        fontSize = 12.sp,
                        color = Color.Black
                    )

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(id = R.drawable.cal_ic),
                            contentDescription = "Calendar",
                            tint = Color.Unspecified,
                            modifier = Modifier.wrapContentSize()
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = (convertDateMMDDYYYY(event.event_start_date ?: "") +" "+ event.event_start_time) ?: "",
                            style = MaterialTheme.typography.bodySmall,
                            fontFamily = FontFamily(Font(R.font.outfit_regular)),
                            fontSize = 12.sp,
                            color = Color.Black
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Text(
                        text = event.event_description?:"",
                        style = MaterialTheme.typography.bodySmall,
                        fontFamily = FontFamily(Font(R.font.outfit_regular)),
                        fontSize = 12.sp,
                        color = TextGrey,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.widthIn(max = 150.dp)
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(id = R.drawable.cal_ic),
                            contentDescription = "Calendar",
                            tint = Color.Unspecified,
                            modifier = Modifier.wrapContentSize()
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = (convertDateMMDDYYYY(event.event_end_date ?: "") +" " + event.event_end_time) ?: "",
                            style = MaterialTheme.typography.bodySmall,
                            fontFamily = FontFamily(Font(R.font.outfit_regular)),
                            fontSize = 12.sp,
                            color = Color.Black
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(R.drawable.location_ic_icon),
                        contentDescription = null,
                        tint = Color.Unspecified,
                        modifier = Modifier.wrapContentSize()
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = event.address?:"",
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Medium,
                        fontFamily = FontFamily(Font(R.font.outfit_medium)),
                        fontSize = 12.sp,
                        color = Color.Black
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = { onButtonClick(event) },
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryColor),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth().height(48.dp)
                ) {
                    Text(text = if (selectedOption == "My Events") "View Community Chats" else "Join Community Chats"
                        , color = Color.White,fontWeight = FontWeight.Medium,
                        fontFamily = FontFamily(Font(R.font.outfit_medium)),
                        fontSize = 14.sp)
                }
            }
        }
    }
}

@Composable
fun PostMediaSlider(
    mediaList: List<GetEventImage>,
    modifier: Modifier = Modifier,
    onVideoPlay: (() -> Unit)? = null,
) {

    val context = LocalContext.current
    val pagerState = rememberPagerState { mediaList.size }
    var currentPage by rememberSaveable { mutableIntStateOf(0) }

    // --- VIDEO PLAYERS ---
    val players = remember {
        mediaList.map { item ->
            if (item.media_path.isVideoFile()) {
                ExoPlayer.Builder(context).build().apply {
                    val mediaItem = MediaItem.fromUri(item.media_path)
                    setMediaItem(mediaItem)
                    prepare()
                    playWhenReady = false
                    repeatMode = Player.REPEAT_MODE_ONE
                }
            } else null
        }
    }

    // --- Pause when slide ---
    LaunchedEffect(pagerState.currentPage) {
        currentPage = pagerState.currentPage
        players.forEach { it?.pause() }
    }

    // --- Cleanup ---
    DisposableEffect(Unit) {
        onDispose { players.forEach { it?.release() } }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(4 / 3f)
            .background(Color.Black.copy(.05f))
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->

            val item = mediaList[page]
            val isVideo = item.media_path.isVideoFile()
            var player = players.getOrNull(page)

            /** =====================
             * IMAGE CONTENT
            ====================== */
            if (!isVideo) {
                SubcomposeAsyncImage(
                    model = item.media_path,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize(),
                    loading = {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    },
                    error = {
                        Image(
                            painter = painterResource(R.drawable.no_image),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                )
            }

            /** =====================
             * VIDEO CONTENT
            ====================== */
            if (isVideo && player != null) {

                // --- LOCAL STATES ---
                var isBuffering by remember { mutableStateOf(true) }
                var hasError by remember { mutableStateOf(false) }

                LaunchedEffect(player) {
                    player?.addListener(object : Player.Listener {
                        override fun onPlaybackStateChanged(state: Int) {
                            isBuffering = state == Player.STATE_BUFFERING
                            if (state == Player.STATE_READY) {
                                isBuffering = false
                            }
                        }

                        override fun onPlayerError(error: PlaybackException) {
                            hasError = true
                        }
                    })
                }

                AndroidView(
                    factory = {
                        PlayerView(it).apply {
                            useController = false
                            player = player
                            layoutParams = FrameLayout.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT
                            )
                        }
                    },
                    modifier = Modifier.fillMaxSize()
                )

                /** --- Loader during buffering --- */
                if (isBuffering && !hasError) {
                    Box(
                        Modifier
                            .fillMaxSize()
                            .background(Color.Black.copy(alpha = .35f)),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                /** --- Error screen --- */
                if (hasError) {
                    Box(
                        Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            /*Icon(
                                Icons.Default.Error,
                                contentDescription = null,
                                tint = Color.Red,
                                modifier = Modifier.size(60.dp)
                            )*/
                            Text("Video playback error")
                        }
                    }
                }

                /** --- Play overlay when paused --- */
                if (!player!!.isPlaying && !hasError) {
                    Box(
                        Modifier
                            .fillMaxSize()
                            .background(Color.Black.copy(.35f)),
                        Alignment.Center
                    ) {
                        IconButton(
                            onClick = {
                                player?.play()
                                onVideoPlay?.invoke()
                            },
                            modifier = Modifier
                                .size(60.dp)
                                .background(Color.White.copy(.9f), CircleShape)
                        ) {
                            Icon(
                                Icons.Default.PlayArrow,
                                contentDescription = "Play Video",
                                tint = Color(0xFF0A3D62),
                                modifier = Modifier.size(36.dp)
                            )
                        }
                    }
                }

            }
        }

        /** --- PAGE INDICATORS --- */
        if (mediaList.size > 1) {
            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(10.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(mediaList.size) { index ->
                    val active = index == currentPage
                    Box(
                        Modifier
                            .padding(horizontal = 3.dp)
                            .height(5.dp)
                            .width(if (active) 20.dp else 8.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(
                                if (active) Color(0xFF258694)
                                else Color.White.copy(.5f)
                            )
                    )
                }
            }
        }
    }
}



@Preview
@Composable
fun PreviewEventCard() {
   /* val sampleEvent = com.bussiness.slodoggiesapp.data.model.businessProvider.Event(
        id = "1",
        imageUrl = "https://via.placeholder.com/600x400.png?text=Event+Image",
        title = "Event Title",
        description = "Lorem ipsum dolor sit a...",
        dateTime = "May 25, 4:00 PM",
        duration = "30 Mins.",
        location = "San Luis Obispo County",
    )
    EventCard(event = sampleEvent, selectedOption = "My Events", onButtonClick = {})*/
}
