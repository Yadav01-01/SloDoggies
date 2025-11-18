package com.bussiness.slodoggiesapp.ui.component.common

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.data.model.common.ChatWindow
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor
import com.bussiness.slodoggiesapp.viewModel.common.communityVM.CommunityChatViewModel

@Composable
fun BottomMessageBar(
    modifier: Modifier = Modifier,
    viewModel: CommunityChatViewModel
) {
    var expanded by remember { mutableStateOf(false) }

    val message by viewModel.currentMessage.collectAsState()
    val attachments by viewModel.pendingAttachments.collectAsState()

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { viewModel.addAttachment(it, "image") }
    }

    val pdfPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { viewModel.addAttachment(it, "pdf") }
    }

    Column(modifier = modifier) {

        // 🔹 Preview attachments 
        if (attachments.isNotEmpty()) {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                items(attachments) { (uri, type) ->
                    Box(
                        modifier = Modifier
                            .size(70.dp)
                            .padding(4.dp)
                    ) {
                        if (type == "image") {
                            AsyncImage(
                                model = uri,
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(RoundedCornerShape(8.dp)),
                                contentScale = ContentScale.Crop
                            )
                        } else {
                            // PDF placeholder thumbnail
                            Icon(
                                painter = painterResource(id = R.drawable.dummy_social_media_post),
                                contentDescription = "PDF",
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Color(0xFFE0E0E0))
                                    .padding(12.dp),
                                tint = Color.Red
                            )
                        }

                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Remove",
                            tint = Color.White,
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .background(Color.Black.copy(alpha = 0.6f), CircleShape)
                                .clickable { viewModel.removeAttachment(uri) }
                                .padding(2.dp)
                        )
                    }
                }
            }
        }

        // Input Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier
                    .weight(1f)
                    .background(Color(0xFFE8F0F2), shape = RoundedCornerShape(12.dp))
                    .padding(horizontal = 12.dp, vertical = 0.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box {
                    Icon(
                        painter = painterResource(id = R.drawable.attach_ic),
                        contentDescription = "Attachment",
                        tint = Color.Unspecified,
                        modifier = Modifier
                            .wrapContentSize()
                            .clickable { expanded = true }
                    )

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier.background(Color.White)
                    ) {
                        DropdownMenuItem(
                            text = { Text("Image") },
                            onClick = {
                                expanded = false
                                imagePickerLauncher.launch("image/*")
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("PDF") },
                            onClick = {
                                expanded = false
                                pdfPickerLauncher.launch("application/pdf")
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.width(8.dp))

                TextField(
                    value = message,
                    onValueChange = { viewModel.onMessageChange(it) },
                    placeholder = { Text("Type something") },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 8.dp),
                    maxLines = 3
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Send Button
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(PrimaryColor)
                    .clickable { viewModel.sendMessage() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.send_ic),
                    contentDescription = "Send",
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Composable
fun FeedbackBubble(message: com.bussiness.slodoggiesapp.data.model.common.ChatWindow.Feedback) {
    var feedbackText by remember { mutableStateOf(message.feedbackText) }
    var ratings by remember { mutableStateOf(message.userRatings.toMutableList()) }

    // Bubble background
    val bubbleColor = PrimaryColor // or any darker color to make white text readable
    val bubbleShape = RoundedCornerShape(16.dp)

    Column(
        modifier = Modifier
            .padding(horizontal = 12.dp, vertical = 8.dp)
            .fillMaxWidth()
            .clip(bubbleShape)
            .background(bubbleColor)
            .padding(12.dp) // inner padding
    ) {
        Text(
            text = "⭐ Rate Your Experience with Business Provider",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(Modifier.height(8.dp))

        message.serviceFeedback.forEachIndexed { index, service ->
            Text(
                text = service,
                color = Color.White,
                style = MaterialTheme.typography.bodyMedium
            )
            Row {
                for (i in 1..5) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = if (ratings[index] >= i) Color.Yellow else Color.Gray,
                        modifier = Modifier
                            .size(24.dp)
                            .clickable { ratings[index] = i }
                    )
                }
            }
            Spacer(Modifier.height(8.dp))
        }

        OutlinedTextField(
            value = feedbackText,
            onValueChange = { feedbackText = it },
            placeholder = { Text("Write something here...", color = Color.White.copy(alpha = 0.7f)) },
            colors = OutlinedTextFieldDefaults.colors(
//                focusedBorderColor = Color.White,
//                unfocusedBorderColor = Color.White,
//                textColor = Color.White,
//                cursorColor = Color.White,
//                placeholderColor = Color.White.copy(alpha = 0.7f)
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        Button(
            onClick = {
                println("Feedback submitted: $feedbackText, ratings: $ratings")
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
        ) {
            Text("Submit Feedback", color = Color.White)
        }
    }
}
