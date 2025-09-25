package com.bussiness.slodoggiesapp.ui.dialog

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.ui.component.businessProvider.DialogButton
import com.bussiness.slodoggiesapp.ui.component.businessProvider.InputField
import com.bussiness.slodoggiesapp.ui.component.businessProvider.RemoveButton
import com.bussiness.slodoggiesapp.ui.component.businessProvider.SubmitButton
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor
import com.bussiness.slodoggiesapp.ui.theme.TextGrey

@Composable
fun EditCommunityName(
    communityName: String,
    onDismiss: () -> Unit = {},
    onClickSubmit: () -> Unit,
    onNameChange: (String) -> Unit
) {
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
                        painter = painterResource(id = R.drawable.ic_cross_iconx),
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
                        .wrapContentHeight(),
                    shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp, bottomStart = 16.dp, bottomEnd = 16.dp),
                    color = Color.White
                ) {
                    Column(
                        modifier = Modifier.wrapContentSize()
                    ) {
                        // Header with close button
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Text(
                                text = "Name Community",
                                fontSize = 18.sp,
                                fontFamily = FontFamily(Font(R.font.outfit_medium)),
                                color = Color.Black,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.align(Alignment.CenterStart)
                            )
                        }

                        Divider(color = TextGrey, thickness = 1.dp)

                        Spacer(Modifier.height(10.dp))

                        InputField(input = communityName, onValueChange = onNameChange, placeholder = "Event Community", modifier = Modifier.padding(horizontal = 10.dp))

                        Spacer(Modifier.height(10.dp))

                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.padding(10.dp)) {
                            Button(
                                onClick = { onDismiss() },
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                                shape = RoundedCornerShape(10.dp),
                            ) {
                                Text("Cancel", color = Color.Black, fontSize = 15.sp, fontFamily = FontFamily(Font(R.font.outfit_medium)))
                            }

                            SubmitButton(
                                modifier = Modifier.weight(1f),
                                buttonText = "Rename",
                                onClickButton = { onClickSubmit() }
                            )
                        }
                    }
                }

            }
        }
    }
}


@Composable
fun RemoveParticipantDialog(
    onDismiss: () -> Unit,
    onClickRemove: () -> Unit,
    @DrawableRes iconResId: Int,
    text: String,
    description: String
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)

        ) {
            Spacer(Modifier.height(45.dp))

            // Close button
            Box(Modifier.fillMaxWidth()) {
                Image(
                    painter = painterResource(id = R.drawable.ic_cross_iconx),
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .clip(CircleShape)
                        .clickable { onDismiss() }
                )
            }

            androidx.compose.material3.Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp),
                shape = RoundedCornerShape(12.dp),
                color = Color.White
            ) {
                Column(
                    modifier = Modifier
                        .background(Color.White, shape = RoundedCornerShape(12.dp))
                        .padding(horizontal = 10.dp, vertical = 10.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        painter = painterResource(id = iconResId),
                        contentDescription = null,
                        tint = Color.Unspecified,
                        modifier = Modifier
                            .size(50.dp)
                            .align(Alignment.CenterHorizontally)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = text,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Medium,
                            fontFamily = FontFamily(Font(R.font.outfit_medium)),
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center,
                            color = Color.Black
                        ),
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = description,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Normal,
                            fontFamily = FontFamily(Font(R.font.outfit_regular)),
                            fontSize = 14.sp,
                            textAlign = TextAlign.Center,
                            color = Color.Black
                        ),
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Buttons
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        RemoveButton(
                            text = "Cancel",
                            selected = false,
                            onClick = onDismiss,
                            modifier = Modifier.weight(1f)
                        )
                        RemoveButton(
                            text = "Remove",
                            selected = true,
                            onClick = {
                                onClickRemove()
                                onDismiss()
                            },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun DeleteChatDialog(
    onDismiss: () -> Unit,
    onClickRemove: () -> Unit,
    @DrawableRes iconResId: Int,
    text: String,
    description: String
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)

        ) {
            Spacer(Modifier.height(45.dp))

            // Close button
            Box(Modifier.fillMaxWidth()) {
                Image(
                    painter = painterResource(id = R.drawable.ic_cross_iconx),
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .clip(CircleShape)
                        .clickable { onDismiss() }
                )
            }

            androidx.compose.material3.Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp),
                shape = RoundedCornerShape(12.dp),
                color = Color.White
            ) {
                Column(
                    modifier = Modifier
                        .background(Color.White, shape = RoundedCornerShape(12.dp))
                        .padding(horizontal = 10.dp, vertical = 10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        painter = painterResource(id = iconResId),
                        contentDescription = null,
                        tint = Color.Unspecified,
                        modifier = Modifier
                            .size(50.dp)
                            .align(Alignment.CenterHorizontally)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = text,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Medium,
                            fontFamily = FontFamily(Font(R.font.outfit_medium)),
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center,
                            color = Color.Black
                        ),
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = description,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Normal,
                            fontFamily = FontFamily(Font(R.font.outfit_regular)),
                            fontSize = 14.sp,
                            textAlign = TextAlign.Center,
                            color = Color.Black
                        ),
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Buttons
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        RemoveButton(
                            text = "Delete",
                            selected = true,
                            onClick = {
                                onClickRemove()
                                onDismiss()
                            },
                            modifier = Modifier.weight(1f)
                        )
                        RemoveButton(
                            text = "Cancel",
                            selected = false,
                            onClick = {
                                onDismiss()
                            },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun BottomToast(
    isBlocked: Boolean,
    onToggle: () -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false, decorFitsSystemWindows = false)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.systemBars)
                .padding(bottom = 15.dp),
            contentAlignment = Alignment.BottomCenter // Centered horizontally
        ) {
            Surface(
                modifier = Modifier
                    .wrapContentSize()
                    .clickable { onToggle() }, // Toggle on click
                shape = RoundedCornerShape(10.dp),
                color = PrimaryColor
            ) {
                Text(
                    text = if (isBlocked) "Unblock Jane" else "Block Jane",
                    color = Color.White,
                    fontFamily = FontFamily(Font(R.font.outfit_medium)),
                    fontSize = 15.sp,
                    modifier = Modifier.padding(12.dp)
                )
            }
        }
    }
}

@Composable
fun ReportBottomToast(
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false, decorFitsSystemWindows = false)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.systemBars)
                .padding(bottom = 15.dp),
            contentAlignment = Alignment.BottomCenter // Centered horizontally
        ) {
            Surface(
                modifier = Modifier
                    .wrapContentSize(),
                shape = RoundedCornerShape(10.dp),
                color = Color.White
            ) {

                Row {
                    Text(
                        text = "Report Sent!| Admin will get back to you.",
                        color = Color.Black,
                        fontFamily = FontFamily(Font(R.font.outfit_regular)),
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(12.dp)
                    )

                    IconButton(onClick = { onDismiss() }) {
                        Icon(
                            imageVector = androidx.compose.material.icons.Icons.Default.Close,
                            contentDescription = "Close",
                            tint = PrimaryColor,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

            }
        }
    }
}