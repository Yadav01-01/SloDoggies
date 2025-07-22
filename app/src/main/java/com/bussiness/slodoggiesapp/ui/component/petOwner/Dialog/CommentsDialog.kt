package com.bussiness.slodoggiesapp.ui.component.petOwner.Dialog

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenu
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.xr.compose.testing.toDp
import com.bussiness.slodoggiesapp.R

@Composable
fun CommentsDialog(
    onDismiss: () -> Unit = {},
    comments: List<Comment> = emptyList()
) {
    var newComment by remember { mutableStateOf("") }
    var replyingTo by remember { mutableStateOf<String?>(null) }


    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            decorFitsSystemWindows = false )
    ) {
        Box ( modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.systemBars) // Account for system bars
            .padding(top = 64.dp), // Optional top padding to not stick to very top
            contentAlignment = Alignment.BottomCenter){
            Column(
                modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.Bottom,
            ) {
                // Close button at top-right
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_cross_icon),
                        contentDescription = "Close",
                        modifier = Modifier
                            .clickable(onClick = onDismiss)
                            .align(Alignment.TopEnd)
                            .size(50.dp)
                            .clip(CircleShape)
                            .padding(8.dp)
                    )
                }
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.7f)
                    ,

                    shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp, bottomStart = 16.dp, bottomEnd = 16.dp),
                    color = Color.White
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        // Header with close button
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Text(
                                text = "Comments",
                                fontSize = 18.sp,
                                fontFamily = FontFamily(Font(R.font.outfit_medium)),
                                color = Color(0xFF212121),
                                modifier = Modifier.align(Alignment.CenterStart)
                            )


                        }

                        Divider(
                            color = Color(0xFF258694),
                            thickness = 1.dp
                        )

                        // Comments List
                        LazyColumn(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                        ) {
                            itemsIndexed(comments) { index, comment ->
                                CommentItem(
                                    comment = comment,
                                    onReply = { replyingTo = comment.userName })


                                // Replies
                                if (comment.replies.isNotEmpty()) {
                                    Column {
                                        comment.replies.forEach { reply ->
                                            Spacer(modifier = Modifier.height(8.dp))
                                            ReplyItem(
                                                reply = reply,
                                                modifier = Modifier.padding(start = 48.dp),
                                                onReply = { replyingTo = reply.userName })

                                        }
                                    }
                                }

                                // Divider
                                if (index < comments.lastIndex) {
                                    Divider(
                                        color = Color.LightGray,
                                        modifier = Modifier.padding(vertical = 8.dp)
                                    )
                                }
                            }
                        }

//
//

                        Column(
                            modifier = Modifier
                                .padding(horizontal = 20.dp, vertical = 6.dp)
                                .background(Color(0xFFE8F0F3), RoundedCornerShape(10.dp))

                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally

                        ) {
                            // Reply indicator if replying to someone
                            replyingTo?.let { userName ->
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp)
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = "Replying to $userName",
                                            fontSize = 12.sp,
                                            color = Color(0xFF258694),
                                            modifier = Modifier.weight(1f)
                                        )

                                        IconButton(
                                            onClick = { replyingTo = null },
                                            modifier = Modifier.size(24.dp)
                                        ) {
                                            Icon(
                                                painter = painterResource(id = R.drawable.black_cross_icon),
                                                contentDescription = "Cancel reply"
                                            )
                                        }
                                    }
                                }
                                Divider(color = Color(0xFF248593), thickness = 1.dp)
                            }
                            // Comment input field

                            Row(
//
                                verticalAlignment = Alignment.CenterVertically
                            ) {


                                Spacer(Modifier.width(8.dp))
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_smile_icon),
                                    //imageVector = Icons.Outlined.SentimentSatisfied,
                                    contentDescription = "Emoji",
                                    tint = Color(0xFF9DA1A3),
                                    modifier = Modifier
                                        .size(32.dp)

                                )

                                Spacer(modifier = Modifier.width(12.dp))

                                TextField(
                                    value = newComment,
                                    onValueChange = { newComment = it },
                                    placeholder = {
                                        Text(
                                            if (replyingTo != null) "Type your reply here" else "Type your comment here",
                                            color = Color(0xFF9DA1A3),
                                            fontSize = 12.sp,
                                            fontFamily = FontFamily(Font(R.font.outfit_regular))
                                        )
                                    },
                                    modifier = Modifier.weight(1f),
                                    shape = RoundedCornerShape(24.dp),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = Color(0xFFE0E0E0),
                                        unfocusedBorderColor = Color(0xFFE0E0E0),
                                    ),
                                    trailingIcon = {
//

                                        Image(
                                            painter = painterResource(id = R.drawable.ic_send_icons),
                                            contentDescription = "Send",
                                            modifier = Modifier
                                                .size(30.dp)
                                                .clickable {
                                                    if (newComment.isNotBlank()) {
                                                        // Add comment logic here
                                                        newComment = ""
                                                        replyingTo = null

                                                    }
                                                }

                                        )
                                    }
                                )
                            }
                        }

                    }
                }

            }
        }
    }
}

@Composable
fun CommentItem(comment: Comment, onReply: () -> Unit) {
    var showOptions by remember { mutableStateOf(false) }
    var showReportDialog by remember { mutableStateOf(false) }
    val iconButton = remember { mutableStateOf<LayoutCoordinates?>(null) }

    Column {


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {


            Image(
                painter = painterResource(id = R.drawable.dummy_person_image2),
                contentDescription = "Person",
                modifier = Modifier
                    .size(38.dp)
                    .clip(CircleShape)

            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {

                        Row {
                            Text(
                                text = comment.userName,
                                fontSize = 12.sp,
                                fontFamily = FontFamily(Font(R.font.outfit_medium)),
                                color = Color.Black
                            )
                            Text(
                                text = comment.timeAgo,
                                fontSize = 12.sp,
                                modifier = Modifier.padding(start = 10.dp),
                                fontFamily = FontFamily(Font(R.font.outfit_regular)),
                                color = Color(0xFF949494)
                            )
                        }


//
                        Text(
                            text = comment.userRole,
                            fontSize = 9.sp,
                            color = Color(0xFF258694),
                            fontFamily = FontFamily(Font(R.font.outfit_medium)),
                            modifier = Modifier
                                .background(
                                    color = Color(0xFFE5EFF2),
                                    shape = RoundedCornerShape(50)
                                )
                                .padding(horizontal = 12.dp, vertical = 4.dp)
                        )
                    }
                    Column {


                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Box {
                                IconButton(
                                    onClick = { showOptions = true },
                                    modifier = Modifier.size(21.dp)
//                                        .onGloballyPositioned { coordinates ->
//                                            iconButton.value = coordinates
//                                            coordinates.positionInWindow().run {
//                                                anchorPosition = DpOffset(x.toDp(), y.toDp())
//                                            }
                                    //    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.MoreVert, // Replace with three dots icon
                                        contentDescription = "More options",
                                        tint = Color.Black
                                    )
                                }
                                if (showOptions) {
                                    CommentOptionsPopup(
                                        showPopup = showOptions,
                                        onDismiss = { showOptions = false },
                                        onReport = {
                                            println("Reported comment: ${comment.id}")

                                            showReportDialog = true
//                                            ReportDialog(
//                                                onDismiss = { showReportDialog = false }
//                                            )
                                        },
                                        anchorPosition = iconButton.value?.positionInWindow()?.toDpOffset() ?: DpOffset.Zero
                                    )
                                }
                            }
                            if (showReportDialog) {
                                ReportDialog(
                                    onDismiss = { showReportDialog = false }
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(10.dp))


                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

            }
        }
        Row {
            Text(
                text = comment.text,
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.outfit_regular)),
                color = Color.Black,
                //modifier = Modifier.widthIn(max = 250.dp)
                modifier = Modifier.weight(1f)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                // modifier = Modifier.padding(start = 15.dp),
                horizontalArrangement = Arrangement.End
            ) {
                if (comment.isLiked) {
                    IconButton(
                        onClick = { /* Handle like */ },
                        modifier = Modifier.size(15.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_paw_like_filled_icon), // Replace with heart icon
                            contentDescription = "Like",
                            tint = Color(0xFF00ACC1)
                        )
                    }
                } else {
                    IconButton(
                        onClick = { /* Handle like */ },
                        modifier = Modifier.size(15.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_paw_like_icon), // Replace with heart icon
                            contentDescription = "Like",
                            // tint = if (comment.isLiked) Color(0xFF00ACC1) else Color(0xFF949494),

                        )
                    }
                }


                if (comment.likeCount > 0) {
                    Text(
                        text = comment.likeCount.toString(),
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(R.font.outfit_regular)),
                        color = Color(0xFF949494),
                        modifier = Modifier.padding(start = 4.dp)
                    )
                } else {
                    Text(
                        text = " ",
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(R.font.outfit_regular)),
                        color = Color(0xFF949494),
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
            }
        }

        // Spacer(modifier = Modifier.height(5.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(
                onClick = onReply,
            ) {
                Text(
                    text = "Reply",
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.outfit_regular)),
                    color = Color(0xFF258694)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

//
        }
    }
}

@Composable
fun ReplyItem(
    reply: Comment,
    modifier: Modifier = Modifier, onReply: () -> Unit
) {
    var showOptions by remember { mutableStateOf(false) }
    var anchorPosition by remember { mutableStateOf(DpOffset.Zero) }
    val iconButton = remember { mutableStateOf<LayoutCoordinates?>(null) }
    var showReportDialog by remember { mutableStateOf(false) }
    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.dummy_person_image2),
                contentDescription = "Person",
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Row {
                            Text(
                                text = reply.userName,
                                fontSize = 12.sp,
                                fontFamily = FontFamily(Font(R.font.outfit_medium)),
                                color = Color.Black
                            )
                            Text(
                                text = reply.timeAgo,
                                fontSize = 12.sp,
                                modifier = Modifier.padding(start = 10.dp),
                                fontFamily = FontFamily(Font(R.font.outfit_regular)),
                                color = Color(0xFF949494)
                            )
                        }
                        Text(
                            text = reply.userRole,
                            fontSize = 9.sp,
                            color = Color(0xFF258694),
                            fontFamily = FontFamily(Font(R.font.outfit_medium)),
                            modifier = Modifier
                                .background(
                                    color = Color(0xFFE5EFF2),
                                    shape = RoundedCornerShape(50)
                                )
                                .padding(horizontal = 12.dp, vertical = 4.dp)
                        )
                    }
                    Box {
                        IconButton(
                            onClick = { showOptions = true  },
                            modifier = Modifier.size(21.dp)
//                            .onGloballyPositioned { coordinates ->
//                            iconButton.value = coordinates
//                            coordinates.positionInWindow().run {
//                                anchorPosition = DpOffset(x.toDp(), y.toDp())
//                            }
//                        }
                        ) {
                            Icon(
                                imageVector = Icons.Default.MoreVert,
                                contentDescription = "More options",
                                tint = Color.Black
                            )
                        }
                        if (showOptions) {
                            CommentOptionsPopup(
                                showPopup = showOptions,
                                onDismiss = { showOptions = false },
                                onReport = {
                                    println("Reported comment: ${reply.id}")

                                    showReportDialog = true
//                                    ReportDialog(
//                                        onDismiss = { showReportDialog = false }
//                                    )
                                },
                                anchorPosition = iconButton.value?.positionInWindow()?.toDpOffset() ?: DpOffset.Zero
                            )
                        }
                    }
                    if (showReportDialog) {
                        ReportDialog(
                            onDismiss = { showReportDialog = false }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                Row {
                    Text(
                        text = reply.text,
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.outfit_regular)),
                        color = Color.Black,
                        modifier = Modifier.widthIn(max = 180.dp).weight(1f)

                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(start = 15.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        if (reply.isLiked) {
                            IconButton(
                                onClick = { /* Handle like */ },
                                modifier = Modifier.size(15.dp)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_paw_like_filled_icon), // Replace with heart icon
                                    contentDescription = "Like",
                                    tint = Color(0xFF00ACC1)
                                )
                            }
                        } else {
                            IconButton(
                                onClick = { /* Handle like */ },
                                modifier = Modifier.size(15.dp)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_paw_like_icon), // Replace with heart icon
                                    contentDescription = "Like",
                                    // tint = if (comment.isLiked) Color(0xFF00ACC1) else Color(0xFF949494),

                                )
                            }
                        }


                        if (reply.likeCount > 0) {
                            Text(
                                text = reply.likeCount.toString(),
                                fontSize = 12.sp,
                                fontFamily = FontFamily(Font(R.font.outfit_regular)),
                                color = Color(0xFF949494),
                                modifier = Modifier.padding(start = 4.dp)
                            )
                        } else {
                            Text(
                                text = " ",
                                fontSize = 12.sp,
                                fontFamily = FontFamily(Font(R.font.outfit_regular)),
                                color = Color(0xFF949494),
                                modifier = Modifier.padding(start = 4.dp)
                            )
                        }
                    }
                }

                // Spacer(modifier = Modifier.height(5.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(
                        onClick = onReply,
                    ) {
                        Text(
                            text = "Reply",
                            fontSize = 14.sp,
                            fontFamily = FontFamily(Font(R.font.outfit_regular)),
                            color = Color(0xFF258694)
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

//
                }
            }
        }
        Divider(color = Color(0xFFF5F5F5), thickness = 1.dp)
    }
}

// Data class for Comment
data class Comment(
    val id: String,
    val userName: String,
    val userRole: String,
    val userAvatar: String,
    val text: String,
    val timeAgo: String,
    val likeCount: Int,
    val isLiked: Boolean,
    val replies: List<Comment> = emptyList()

)




@Composable
fun CommentOptionsPopup(
    showPopup: Boolean,
    onDismiss: () -> Unit,
    onReport:  () -> Unit,
    anchorPosition: DpOffset = DpOffset.Zero,
    modifier: Modifier = Modifier
) {
    if (showPopup) {
        DropdownMenu(
            expanded = showPopup,
            onDismissRequest = onDismiss,
            offset = DpOffset(anchorPosition.x - 160.dp, anchorPosition.y),
            modifier = modifier
                .width(160.dp)   .background(Color.White, RoundedCornerShape(8.dp))
            // .shadow(8.dp, RoundedCornerShape(8.dp))
        ) {
            DropdownMenuItem(
                onClick = {
                    onReport()
                    onDismiss()
                },
                text = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_report_icon),
                            contentDescription = "Report",
                            tint = Color(0xFF258694),
                            modifier = Modifier.size(17.dp)

                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Report Comment", fontSize = 14.sp, fontFamily = FontFamily(Font(R.font.outfit_regular)), color = Color.Black
                        )
                    }
                }
            )

        }
    }
}
fun Offset.toDpOffset(): DpOffset {
    return DpOffset(x.toDp(), y.toDp())
}
