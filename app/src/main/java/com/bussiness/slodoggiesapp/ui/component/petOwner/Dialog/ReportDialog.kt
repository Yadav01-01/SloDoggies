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
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.ui.component.petOwner.CommonBlueButton
import com.bussiness.slodoggiesapp.ui.component.petOwner.CommonWhiteButton
import com.bussiness.slodoggiesapp.ui.component.petOwner.sheet.ReportReasonOption


@Composable
fun ReportDialog(
    onDismiss: () -> Unit = {}
) {
    var selectedReason by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }

    val reportReasons = listOf(
        "Bullying or unwanted contact",
        "Violence, hate or exploitation",
        "False Information",
        "Scam, fraud or spam"
    )

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            decorFitsSystemWindows = false
        )
    ) {
        Box(
            modifier = Modifier
                .wrapContentSize()
           .windowInsetsPadding(WindowInsets.systemBars)
                .padding(top = 64.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Bottom,
            ) {
                // Close button at top-right
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_cross_iconx),
                        contentDescription = "Close",
                        modifier = Modifier
                            .clickable(onClick = onDismiss)
                            .align(Alignment.TopEnd)
                            .wrapContentSize()
                            .clip(CircleShape)
                            .padding(8.dp)
                    )
                }

                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight().padding(10.dp),
                    shape = RoundedCornerShape(
                        topStart = 16.dp,
                        topEnd = 16.dp,
                        bottomStart = 16.dp,
                        bottomEnd = 16.dp
                    ),
                    color = Color.White
                ) {
                    Column(
                        modifier = Modifier.wrapContentSize()
                    ) {
                        // Header
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Text(
                                text = "Report Comment",
                                fontSize = 20.sp,
                                fontFamily = FontFamily(Font(R.font.outfit_medium)),
                                color = Color(0xFF212121),
                                modifier = Modifier.align(Alignment.CenterStart)
                            )
                        }

                        Divider(
                            color = Color(0xFF258694),
                            thickness = 1.dp
                        )

                        // Content
                        Column(
                            modifier = Modifier
                                .wrapContentSize()
                                .padding(16.dp)
                        ) {
                            // Question
                            Text(
                                text = "Why are you reporting this comment?",
                                fontSize = 14.sp,
                                fontFamily = FontFamily(Font(R.font.outfit_medium)),
                                color = Color.Black,
                                modifier = Modifier.padding(bottom = 16.dp)
                            )

                            // Report reasons
                            reportReasons.forEach { reason ->
                                ReportReasonOption(
                                    text = reason,
                                    isSelected = selectedReason == reason,
                                    onClick = { selectedReason = reason }
                                )
                            }

                            Spacer(modifier = Modifier.height(20.dp))

                            // Message section
                            Text(
                                text = "Message (Optional)",
                                fontSize = 14.sp,
                                fontFamily = FontFamily(Font(R.font.outfit_medium)),
                                color = Color.Black,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )

                            // Text field
                            OutlinedTextField(
                                value = message,
                                onValueChange = { message = it },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(93.dp),
                                placeholder = {
                                    Text(
                                        text = "Write something here....",
                                        color = Color(0xFF949494),
                                        fontFamily = FontFamily(Font(R.font.outfit_regular))
                                    )
                                },
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = Color(0xFF258694),
                                    unfocusedBorderColor = Color(0xFF949494)
                                ),
                                shape = RoundedCornerShape(8.dp)
                            )

                            Spacer(modifier = Modifier.height(30.dp))

                            // Buttons
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                //       .padding(vertical = 16.dp),
                                horizontalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                CommonWhiteButton(
                                    text = "Cancel",
                                    fontSize = 16.sp,
                                    onClick = {
                                        // Handle skip action
                                    },
                                    modifier = Modifier.weight(1f),
                                )
                                CommonBlueButton(
                                    text = "Send report",
                                    fontSize = 15.sp,
                                    onClick = {
                                        // Handle save action
                                    },
                                    modifier = Modifier.weight(1f),
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
fun ReportReasonOption(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Text(
        text = text,
        fontSize = 14.sp,
        fontFamily = FontFamily(Font(R.font.outfit_regular)),
        color = if (isSelected) Color.White else Color.Black,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .background(
                color = if (isSelected) Color(0xFF258694) else Color.Transparent,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(horizontal = 16.dp, vertical = 12.dp)
    )
}


@Preview
@Composable
fun ReportCommentDialogPreview() {
//    ReportCommentDialog(
//        onDismiss = {},
//        onReport = { reason, message ->
//            println("Report submitted: $reason - $message")
//        }
//    )
    var showReportDialog by remember { mutableStateOf(false) }
    ReportDialog(
        onDismiss = { showReportDialog = false }
    )
}
