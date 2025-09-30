package com.bussiness.slodoggiesapp.ui.component.petOwner.dialog

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenu
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.xr.compose.testing.toDp
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.ui.component.common.ParentLabel
import com.bussiness.slodoggiesapp.ui.dialog.ReportBottomToast
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor

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
        Box(
            modifier = Modifier
                .fillMaxSize()
                .imePadding(), // pushes content above keyboard
            contentAlignment = Alignment.BottomCenter
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
            ) {
                // Close button above the surface
                Box(
                    Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_cross_iconx),
                        contentDescription = null,
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .clip(CircleShape)
                            .clickable { onDismiss() }
                    )
                }

                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(
                            min = Dp.Unspecified,              // let content shrink
                            max = LocalConfiguration.current.screenHeightDp.dp * 0.7f // cap at 70%
                        ),
                    shape = RoundedCornerShape(16.dp),
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
                                fontSize = 20.sp,
                                fontFamily = FontFamily(Font(R.font.outfit_medium)),
                                color = Color.Black,
                                fontWeight = FontWeight.Medium,
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
                                    onReply = { replyingTo = comment.userName },
                                    onEditClick = {},
                                    onDeleteClick = {},
                                )


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
                                        .padding(12.dp)
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = "Replying to $userName",
                                            fontSize = 16.sp,
                                            color = PrimaryColor,
                                            fontFamily = FontFamily(Font(R.font.outfit_medium)),
                                            fontWeight = FontWeight.Medium,
                                            modifier = Modifier.weight(1f)
                                        )

                                        IconButton(
                                            onClick = { replyingTo = null },
                                            modifier = Modifier.size(20.dp)
                                        ) {
                                            Icon(
                                                painter = painterResource(id = R.drawable.black_cross_icon),
                                                contentDescription = "Cancel reply"
                                            )
                                        }
                                    }
                                }

                                HorizontalDivider(thickness = 1.dp, color = PrimaryColor)

                            }
                            // Comment input field

                            CommentInputBox(
                                newComment = newComment,
                                onCommentChange = { newComment = it },
                                onSendClick = {
                                    // Add comment logic
                                    newComment = ""
                                }
                            )


                        }

                    }
                }
            }
        }
    }
}



@Composable
fun CommentInputBox(
    newComment: String,
    onCommentChange: (String) -> Unit,
    onSendClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFE6EFF2), RoundedCornerShape(50.dp))
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_smile_icon),
            contentDescription = "Emoji",
            tint = Color.Gray,
            modifier = Modifier.size(30.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Box(modifier = Modifier.weight(1f)) {
            if (newComment.isEmpty()) {
                Text(
                    text = "Type your comment here",
                    color = Color(0xFF9C9C9C),
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.outfit_regular))
                )
            }
            BasicTextField(
                value = newComment,
                onValueChange = onCommentChange,
                textStyle = TextStyle(
                    color = Color.Black,
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.outfit_regular))
                ),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Image(
            painter = painterResource(R.drawable.ic_send_icons),
            contentDescription = "send",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .size(34.dp)
                .clickable {
                    if (newComment.isNotBlank()) {
                        onSendClick()
                    }
                }
        )

    }
}

@Composable
fun CommentItem(comment: Comment, onReply: () -> Unit, onEditClick : () -> Unit, onDeleteClick : () -> Unit,user: Boolean = false) {
    var showOptions by remember { mutableStateOf(false) }
    var showReportDialog by remember { mutableStateOf(false) }

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.dummy_person_image2),
                contentDescription = "Person",
                modifier = Modifier
                    .size(35.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {

                        Row {
                            Text(
                                text = comment.userName,
                                fontSize = 14.sp,
                                fontFamily = FontFamily(Font(R.font.outfit_medium)),
                                color = Color.Black
                            )
                            Text(
                                text = comment.timeAgo,
                                fontSize = 14.sp,
                                modifier = Modifier.padding(start = 10.dp),
                                fontFamily = FontFamily(Font(R.font.outfit_regular)),
                                color = Color(0xFF949494)
                            )
                        }

                        ParentLabel(comment.userRole)
                    }
                    if (comment.user){
                        Row(verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.End) {

                            Icon(
                                painter = painterResource(id = R.drawable.edit_ic_p),
                                contentDescription = stringResource(R.string.edit),
                                tint = Color.Unspecified,
                                modifier = Modifier.wrapContentSize().clickable { onEditClick() }
                            )
                            Spacer(Modifier.width(15.dp))
                            Icon(
                                painter = painterResource(id = R.drawable.delete_mi),
                                contentDescription = stringResource(R.string.delete),
                                tint = Color.Unspecified,
                                modifier = Modifier.wrapContentSize().clickable { onDeleteClick() }
                            )
                        }
                    }else{
                        IconButton(
                            onClick = { showOptions = true  },
                            modifier = Modifier.size(21.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.MoreVert,
                                contentDescription = "More options",
                                tint = Color.Black
                            )
                        }
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
                lineHeight = 18.sp,
                modifier = Modifier.weight(1f)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
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
                            tint = Color.Unspecified,
                            modifier = Modifier.wrapContentSize()
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


        Text(
            text = "Reply",
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(R.font.outfit_regular)),
            color = PrimaryColor,
            modifier = Modifier.clickable { onReply() }
        )
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
    var showReportToast by remember { mutableStateOf(false) }
    var selectedReason by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }

    Column(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
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
                                color = Color.Black,
                                fontWeight = FontWeight.Medium
                            )
                            Text(
                                text = reply.timeAgo,
                                fontSize = 12.sp,
                                modifier = Modifier.padding(start = 10.dp),
                                fontFamily = FontFamily(Font(R.font.outfit_regular)),
                                color = Color(0xFF949494),
                                fontWeight = FontWeight.Normal
                            )
                        }

                        ParentLabel(reply.userRole)
                    }
                    Box {
                        IconButton(
                            onClick = { showOptions = true  },
                            modifier = Modifier.size(21.dp)
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
                                },
                                anchorPosition = iconButton.value?.positionInWindow()?.toDpOffset() ?: DpOffset.Zero
                            )
                        }
                    }
                    if (showReportDialog) {
                        ReportDialog(
                            onDismiss = { showReportDialog = false },
                            onCancel = { showReportDialog = false },
                            onSendReport = { showReportToast = true },
                            reasons = listOf("Bullying or unwanted contact",
                                    "Violence, hate or exploitation",
                                    "False Information",
                                    "Scam, fraud or spam"),
                            selectedReason = selectedReason,
                            message = message,
                            onReasonSelected = { reason -> selectedReason = reason },
                            onMessageChange = { message = it },
                            title = "Report Comment"
                        )
                    }
                    if (showReportToast){
                        ReportBottomToast(onDismiss = { showReportToast = false })
                    }
                }
            }
        }
        Row {
            Text(
                text = reply.text,
                fontSize = 12.sp,
                fontFamily = FontFamily(Font(R.font.outfit_regular)),
                color = Color.Black,
                lineHeight = 18.sp,
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

        Text(
            text = "Reply",
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(R.font.outfit_regular)),
            color = PrimaryColor,
            modifier = Modifier.clickable { onReply() }
        )

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
    val replies: List<Comment> = emptyList(),
    var user: Boolean = false

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


@Preview(showBackground = true)
@Composable
fun CommentsDialogPreview() {
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
                    userAvatar = "https://via.placeholder.com/40x40",
                    text = "I agree! My poodle had so much fun too!",
                    timeAgo = "15 min",
                    likeCount = 12,
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
        onDismiss = {  }
    )
}
