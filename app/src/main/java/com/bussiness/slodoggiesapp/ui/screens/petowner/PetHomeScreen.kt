package com.bussiness.slodoggiesapp.ui.screens.petowner

import android.net.Uri
import android.widget.MediaController
import android.widget.VideoView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.ui.component.petOwner.Dialog.Comment
import com.bussiness.slodoggiesapp.ui.component.petOwner.Dialog.CommentsDialog
import com.bussiness.slodoggiesapp.ui.component.shareApp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PetHomeScreen(authNavController: NavHostController){
    val showSuccessDialog by remember { mutableStateOf(true) }
    val showPetInfoDialog  by remember { mutableStateOf(true) }
    Column(modifier = Modifier.fillMaxSize()
        .background(Color(0xFFB9D4DB))) {
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
            )
        )

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(3.dp)
                .background(Color(0xFF258694))
        )

        // Replace SocialMediaPost() with SocialMediaFeed()
        SocialMediaFeed()

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFEAEFF1))
        ) {

            if (showSuccessDialog) {
//                WelcomeDialog(
//                    onDismiss = { showSuccessDialog = false
//                        showPetInfoDialog = true
//                    },
//                    onSubmitClick = {
//                        showSuccessDialog = false
////                        navController.navigate(Routes.MAIN_SCREEN) {
////                            popUpTo(Routes.LOGIN) { inclusive = true }
////                        }
//                    },
//                    title = "Welcome to SloDoggies!",
//                    description = "We're excited you're here!  Rather than excited to have you. Thanks",
//                    button = "Get Started"
//                )
            }}
        if (showPetInfoDialog) {
//            PetInfoDialog(
            //"Tell us about your pet!",
//                onDismiss = { showPetInfoDialog = false },
//                onSaveAndContinue = { petInfo ->
//                    // Handle pet info saving
//                }
//            )
//            UserDetailsDialog(
//                onDismiss = { showPetInfoDialog = false },
//                onSubmit = {
//                    // Handle pet info saving
//                }
//            )


        }
    }


}

// New composable to handle multiple posts
@Composable
fun SocialMediaFeed() {
    val posts = listOf(
        PostData(
            user = "Lydia Vaccaro with Wixx",
            role = "Pet Mom",
            time = "5 Min.",
            caption = "🐾 Meet Wixx - our brown bundle of joy!",
            description = "From tail wags to beach days, life with this 3-year-old",
            likes = 120,
            comments = 20,
            shares = 10,
            mediaList = listOf(
                MediaItem(R.drawable.dummy_person_image3, MediaType.IMAGE),
                MediaItem(R.drawable.dummy_person_image2, MediaType.IMAGE),
                MediaItem(R.drawable.dummy_person_image3, MediaType.VIDEO, R.raw.dummy_video_thumbnail)
            )
        ),
        PostData(
            user = "John Doe with Max",
            role = "Pet Dad",
            time = "15 Min.",
            caption = "Enjoying the sunny day!",
            description = "Max loves playing in the park with his friends",
            likes = 85,
            comments = 12,
            shares = 5,
            mediaList = listOf(
                MediaItem(R.drawable.dummy_person_image2, MediaType.IMAGE),
                MediaItem(R.drawable.dummy_person_image3, MediaType.IMAGE)
            )
        ), PostData(
            user = "Lydia Vaccaro with Wixx",
            role = "Pet Mom",
            time = "5 Min.",
            caption = "🐾 Meet Wixx - our brown bundle of joy!",
            description = "From tail wags to beach days, life with this 3-year-old",
            likes = 120,
            comments = 20,
            shares = 10,
            mediaList = listOf(
                MediaItem(R.drawable.dummy_person_image3, MediaType.IMAGE),
                MediaItem(R.drawable.dummy_person_image2, MediaType.IMAGE),
                MediaItem(R.drawable.dummy_person_image3, MediaType.VIDEO, R.raw.dummy_video_thumbnail)
            )
        ),
        PostData(
            user = "John Doe with Max",
            role = "Pet Dad",
            time = "15 Min.",
            caption = "Enjoying the sunny day!",
            description = "Max loves playing in the park with his friends",
            likes = 85,
            comments = 12,
            shares = 5,
            mediaList = listOf(
                MediaItem(R.drawable.dummy_person_image2, MediaType.IMAGE),
                MediaItem(R.drawable.dummy_person_image3, MediaType.IMAGE)
            )
        ),
        PostData(
            user = "Lydia Vaccaro with Wixx",
            role = "Pet Mom",
            time = "5 Min.",
            caption = "🐾 Meet Wixx - our brown bundle of joy!",
            description = "From tail wags to beach days, life with this 3-year-old",
            likes = 120,
            comments = 20,
            shares = 10,
            mediaList = listOf(
                MediaItem(R.drawable.dummy_person_image3, MediaType.IMAGE),
                MediaItem(R.drawable.dummy_person_image2, MediaType.IMAGE),
                MediaItem(R.drawable.dummy_person_image3, MediaType.VIDEO, R.raw.dummy_video_thumbnail)
            )
        ),
        PostData(
            user = "John Doe with Max",
            role = "Pet Dad",
            time = "15 Min.",
            caption = "Enjoying the sunny day!",
            description = "Max loves playing in the park with his friends",
            likes = 85,
            comments = 12,
            shares = 5,
            mediaList = listOf(
                MediaItem(R.drawable.dummy_person_image2, MediaType.IMAGE),
                MediaItem(R.drawable.dummy_person_image3, MediaType.IMAGE)
            )
        ),
        // Add more posts as needed
    )


    LazyColumn {
        items(posts) { post ->
            SocialMediaPost(post = post)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }


}
data class MediaItem(
    val resourceId: Int,
    val type: MediaType,
    val videoResourceId: Int? = null,
    val thumbnailResourceId: Int? = null
)

enum class MediaType {
    IMAGE, VIDEO
}
// Modified SocialMediaPost to accept PostData parameter
@Composable
fun SocialMediaPost(post: PostData) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            // Header with profile info
            PostHeader(
                user = post.user,
                role = post.role,
                time = post.time
            )
            // Caption
            PostCaption(
                caption = post.caption,
                description = post.description
            )
            // Main image
            PostImage(mediaList = post.mediaList)

            // Like count
            PostLikes(
                likes = post.likes,
                comments = post.comments,
                shares = post.shares
            )
        }
    }
}

// Data class for post information
data class PostData(
    val user: String,
    val role: String,
    val time: String,
    val caption: String,
    val description: String,
    val likes: Int,
    val comments: Int,
    val shares: Int,
    val mediaList: List<MediaItem>
)

// Modified PostHeader to accept parameters
@Composable
private fun PostHeader(user: String, role: String, time: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {



            Box(Modifier.width(65.dp).height(52.dp)) {
                // Back image (woman with greenery)
                Image(
                    painter = painterResource(id = R.drawable.dummy_person_image2),
                    contentDescription = "Person",
                    modifier = Modifier
                        .size(43.dp)
                        .clip(CircleShape)
                        .align(Alignment.TopStart)
                )

                // Front image (dog in FULL COLOR with white border)
                Image(
                    painter = painterResource(id = R.drawable.dummy_person_image1),
                    contentDescription = "Dog",
                    modifier = Modifier
                        .size(43.dp)
                        .clip(CircleShape)

                        .border(4.dp, Color.White, CircleShape)
                        .align(Alignment.BottomEnd)

                    // REMOVED: colorFilter = ColorFilter.colorMatrix(grayMatrix)
                )
            }




            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
//                    Text(
//                        text = user,
//                        fontSize = 14.sp,
//                        fontFamily = FontFamily(Font(R.font.outfit_medium)),
//                        color = Color.Black
//                    )
                    Text(
                        text = user,
                        fontSize = 13.sp,
                        fontFamily = FontFamily(Font(R.font.outfit_medium)),
                        color = Color.Black,
                        modifier = Modifier.widthIn(max = 150.dp), // Adjust the max width as needed
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.width(8.dp))

                    Button(
                        onClick = { },
                        modifier = Modifier
                            .height(24.dp)
                            .padding(horizontal = 10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF258694)
                        ),
                        shape = RoundedCornerShape(6.dp),
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 0.dp)
                    ) {
                        Text(
                            text = "Follow",
                            fontSize = 11.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
                Row {
                    Text(
                        text = role,
                        fontSize = 9.sp,
                        color = Color(0xFF258694),
                        fontFamily = FontFamily(Font(R.font.outfit_medium)),
                        modifier = Modifier
                            .background(color = Color(0xFFE5EFF2), shape = RoundedCornerShape(50))
                            .padding(horizontal = 12.dp, vertical = 4.dp)
                    )

                    Text(
                        text = " $time",
                        fontSize = 13.sp,
                        color = Color(0xFF949494),
                        modifier = Modifier
                            .padding(vertical = 2.dp),
                        fontFamily = FontFamily(Font(R.font.outfit_regular)),
                    )
                }
            }
        }

        IconButton(
            onClick = { /* Handle menu */ },
            modifier = Modifier.size(24.dp)
        ) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "More options",
                tint = Color.Gray
            )
        }
    }
}


@Composable
private fun PostCaption(caption: String, description: String) {
    // Add state to track if the full text should be shown
    var showFullText by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Text(
            text = caption,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black
        )

        Text(
            text = description,
            fontSize = 14.sp,
            color = Color.Black,
            maxLines = if (showFullText) Int.MAX_VALUE else 1,
            overflow = if (showFullText) TextOverflow.Visible else TextOverflow.Ellipsis
        )

        // Only show "Read More" if text is truncated
        if (!showFullText) {
            Text(
                text = "Read More",
                fontSize = 14.sp,
                color = Color(0xFF0077B5),
                fontWeight = FontWeight.Medium,
                modifier = Modifier.clickable {
                    showFullText = true
                }
            )
        } else {
            // Optional: Add "Show Less" functionality
            Text(
                text = "Show Less",
                fontSize = 14.sp,
                color = Color(0xFF0077B5),
                fontWeight = FontWeight.Medium,
                modifier = Modifier.clickable {
                    showFullText = false
                }
            )
        }
    }
}

// Modified PostLikes to accept parameters
@Composable
private fun PostLikes(likes: Int, comments: Int, shares: Int) {
    var isLiked by remember { mutableStateOf(false) }
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
                }
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
                modifier = Modifier.size(25.dp).clickable {
                    shareApp(context)
                }
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = shares.toString(),
                fontSize = 16.sp
            )
        }

        // Bookmark icon aligned to end
        IconButton(
            onClick = { /* Handle bookmark */ },
            modifier = Modifier.size(32.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_bookmark_icon),
                contentDescription = "Bookmark",
                modifier = Modifier.size(24.dp)
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
        CommentsDialog(
            comments = sampleComments,
            onDismiss = { showCommentsDialog = false }
        )
    }
}


@Preview(showBackground = true)
@Composable
fun PetHomeScreenPreview() {
    val navController = rememberNavController()
    PetHomeScreen(authNavController = navController)
}


@Composable
private fun PostImage(
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
                                            Uri.parse("android.resource://${context.packageName}/${mediaItem.videoResourceId}")
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
                        Color.Black.copy(alpha = 0.3f),
                        RoundedCornerShape(12.dp)
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

