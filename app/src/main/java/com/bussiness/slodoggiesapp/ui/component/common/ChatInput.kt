package com.bussiness.slodoggiesapp.ui.component.common

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.model.common.ChatMessage
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor
import com.bussiness.slodoggiesapp.viewModel.common.communityVM.CommunityChatViewModel

//@Composable
//fun BottomMessageBar(
//    modifier: Modifier = Modifier,
//    onAttachmentPicked: (Uri, String) -> Unit ,
//    onSendClick: () -> Unit ,
//    message: String,
//    onMessageChange: (String) -> Unit
//) {
//    var expanded by remember { mutableStateOf(false) }
//
//    val imagePickerLauncher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.GetContent()
//    ) { uri: Uri? ->
//        uri?.let { onAttachmentPicked(it, "image") }
//    }
//
//    // PDF picker launcher
//    val pdfPickerLauncher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.GetContent()
//    ) { uri: Uri? ->
//        uri?.let { onAttachmentPicked(it, "pdf") }
//    }
//
//    Box(
//        modifier = modifier
//            .wrapContentSize()
//    ) {
//        Row(
//            modifier = Modifier
//                .align(Alignment.BottomCenter)
//                .fillMaxWidth()
//                .background(Color.White)
//                .padding(8.dp),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            // Message input area
//            Row(
//                modifier = Modifier
//                    .weight(1f)
//                    .background(Color(0xFFE8F0F2), shape = RoundedCornerShape(12.dp))
//                    .padding(horizontal = 12.dp, vertical = 0.dp),
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Box {
//                    Icon(
//                        painter = painterResource(id = R.drawable.attach_ic),
//                        contentDescription = "Attachment",
//                        tint = Color.Unspecified,
//                        modifier = Modifier
//                            .wrapContentSize().clickable { expanded = true }
//                    )
//
//                    DropdownMenu(
//                        expanded = expanded,
//                        onDismissRequest = { expanded = false }
//                    ) {
//                        DropdownMenuItem(
//                            text = { Text("Image") },
//                            onClick = {
//                                expanded = false
//                                imagePickerLauncher.launch("image/*")
//                            }
//                        )
//                        DropdownMenuItem(
//                            text = { Text("PDF") },
//                            onClick = {
//                                expanded = false
//                                pdfPickerLauncher.launch("application/pdf")
//                            }
//                        )
//                    }
//                }
//
//                Spacer(modifier = Modifier.width(8.dp))
//
//                TextField(
//                    value = message,
//                    onValueChange = onMessageChange,
//                    placeholder = { Text("Type something") },
//                    colors = TextFieldDefaults.colors(
//                        focusedContainerColor = Color.Transparent,
//                        unfocusedContainerColor = Color.Transparent,
//                        disabledContainerColor = Color.Transparent,
//                        focusedIndicatorColor = Color.Transparent,
//                        unfocusedIndicatorColor = Color.Transparent
//                    ),
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(end = 8.dp),
//                    maxLines = 3
//                )
//
//            }
//
//            Spacer(modifier = Modifier.width(8.dp))
//
//            // Send Button
//            Box(
//                modifier = Modifier
//                    .size(50.dp)
//                    .clip(RoundedCornerShape(10.dp))
//                    .background(PrimaryColor)
//                    .clickable { onSendClick() },
//                contentAlignment = Alignment.Center
//            ) {
//                Icon(
//                    painter = painterResource(id = R.drawable.send_ic),
//                    contentDescription = "Send",
//                    tint = Color.White,
//                    modifier = Modifier.size(20.dp)
//                )
//            }
//        }
//    }
//}

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

        // 🔹 Input Row
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

            // 🔹 Send Button
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
