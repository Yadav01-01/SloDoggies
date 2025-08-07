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
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.ui.component.businessProvider.DialogButton
import com.bussiness.slodoggiesapp.ui.component.businessProvider.InputField
import com.bussiness.slodoggiesapp.ui.component.businessProvider.SubmitButton
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
                                onClick = { /* TODO: Edit */ },
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
    Dialog(onDismissRequest = onDismiss) {
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            val maxWidthPx = maxWidth

            Box(
                modifier = Modifier
                    .widthIn(max = maxWidthPx * 0.9f) // 90% of screen width
                    .wrapContentHeight()
            ) {
                // Main Dialog Card
                Column(
                    modifier = Modifier
                        .background(Color.White, shape = RoundedCornerShape(12.dp))
                        .padding(horizontal = 10.dp, vertical = 10.dp)
                        .align(Alignment.Center)
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
                        DialogButton(
                            text = "Cancel",
                            selected = false,
                            onClick = onDismiss,
                            modifier = Modifier.weight(1f)
                        )
                        DialogButton(
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

                // External Cross (X) Button
                Icon(
                    painter = painterResource(R.drawable.ic_cross_icon),
                    contentDescription = "Close",
                    tint = Color.Unspecified,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .offset(x = 0.dp, y = (-50).dp)
                        .wrapContentSize()
                        .background(Color(0xFFF0F0F0), CircleShape)
                        .clickable { onDismiss() }
                )
            }
        }
    }
}