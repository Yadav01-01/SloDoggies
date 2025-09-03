package com.bussiness.slodoggiesapp.ui.dialog

import android.content.Context
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor

@Composable
fun CustomToast(message: String) {
    Box(
        modifier = Modifier
            .wrapContentSize()
            .padding(15.dp)
            .background(PrimaryColor, shape = RoundedCornerShape(8.dp))
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontFamily = FontFamily(Font(R.font.outfit_medium)),
                fontWeight = FontWeight.Medium,
                fontSize = 15.sp,
                color = Color.White
            )
        )
    }
}

@Composable
fun UpdatedDialogWithExternalClose(
    onDismiss: () -> Unit,
    @DrawableRes iconResId: Int,
    text: String,
    description: String
) {
    Dialog(onDismissRequest = onDismiss) {
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            contentAlignment = Alignment.Center
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
                        .padding(horizontal = 10.dp, vertical = 32.dp)
                        .align(Alignment.Center)
                ) {
                    Icon(
                        painter = painterResource(id = iconResId),
                        contentDescription = null,
                        tint = Color.Unspecified,
                        modifier = Modifier
                            .wrapContentSize()
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
                }

                // External Cross (X) Button
                Icon(
                    painter = painterResource(R.drawable.ic_cross_iconx),
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

@Composable
fun DeleteDialog(
    onDismiss: () -> Unit,
    @DrawableRes iconResId: Int,
    onClickDelete: () -> Unit,
    context: Context
) {
    var deleteInput by remember { mutableStateOf("") }

    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .wrapContentHeight()
            ) {
                val maxWidthPx = maxWidth

                Box(
                    modifier = Modifier
                        .widthIn(max = maxWidthPx)
                        .wrapContentHeight()
                ) {
                    // Main Dialog Card
                    Column(
                        modifier = Modifier
                            .background(Color.White, shape = RoundedCornerShape(12.dp))
                            .padding(horizontal = 15.dp, vertical = 25.dp)
                            .align(Alignment.Center)
                    ) {
                        // Icon
                        Icon(
                            painter = painterResource(id = iconResId),
                            contentDescription = null,
                            tint = Color.Unspecified,
                            modifier = Modifier
                                .wrapContentSize()
                                .align(Alignment.CenterHorizontally)
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Title
                        Text(
                            text = stringResource(R.string.delete_desc),
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Normal,
                                fontFamily = FontFamily(Font(R.font.outfit_regular)),
                                fontSize = 16.sp,
                                color = Color.Black,
                                textAlign = TextAlign.Center
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        // Description
                        Text(
                            text = stringResource(R.string.delete_warning),
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontFamily = FontFamily(Font(R.font.outfit_regular)),
                                fontSize = 14.sp,
                                color = Color.Black,
                                textAlign = TextAlign.Center
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        // Prompt
                        Text(
                            text = stringResource(R.string.delete_hint),
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontFamily = FontFamily(Font(R.font.outfit_regular)),
                                fontSize = 14.sp,
                                color = Color.Black,

                            ),
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        // Input Field
                        InputField(
                            input = deleteInput,
                            onValueChange = { deleteInput = it },
                            placeholder = " ",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 4.dp),
                            height = 50.dp
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Buttons
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            DialogButton(
                                text = stringResource(R.string.cancel),
                                selected = false,
                                onClick = onDismiss,
                                modifier = Modifier.weight(1f)
                            )
                            DialogButton(
                                text = stringResource(R.string.delete),
                                selected = true,
                                onClick = {
                                    if (deleteInput.trim() == "DELETE") {
                                        onClickDelete()
                                        onDismiss()
                                    }else{
                                        Toast.makeText(context, "Invalid,Please Try Again", Toast.LENGTH_SHORT).show()
                                        deleteInput = ""
                                        onDismiss()
                                    }
                                },
                                modifier = Modifier.weight(1f)
                            )
                        }


                    }

                    Icon(
                        painter = painterResource(R.drawable.ic_cross_iconx),
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
}

@Composable
fun LogoutDialog(
    onDismiss: () -> Unit,
    onClickLogout: () -> Unit
) {

    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .wrapContentHeight()
            ) {
                val maxWidthPx = maxWidth

                Box(
                    modifier = Modifier
                        .widthIn(max = maxWidthPx)
                        .wrapContentHeight()
                ) {
                    // Main Dialog Card
                    Column(
                        modifier = Modifier
                            .background(Color.White, shape = RoundedCornerShape(12.dp))
                            .padding(horizontal = 15.dp, vertical = 25.dp)
                            .align(Alignment.Center)
                    ) {
                        // Icon
                        Icon(
                            painter = painterResource(id = R.drawable.logout_ic),
                            contentDescription = null,
                            tint = Color.Unspecified,
                            modifier = Modifier
                                .wrapContentSize()
                                .align(Alignment.CenterHorizontally)
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Title
                        Text(
                            text = "Are you sure you want to log out of your account?",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Normal,
                                fontFamily = FontFamily(Font(R.font.outfit_regular)),
                                fontSize = 16.sp,
                                color = Color.Black,
                                textAlign = TextAlign.Center
                            ),
                            modifier = Modifier.fillMaxWidth()
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
                                text = "Logout",
                                selected = true,
                                onClick = {
                                    onClickLogout()
                                    onDismiss()
                                },
                                modifier = Modifier.weight(1f)
                            )
                        }

                    }

                    Icon(
                        painter = painterResource(R.drawable.ic_cross_iconx),
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
}


@Composable
fun BottomDialogWrapper(
    onDismissRequest: () -> Unit,
    onRemoveUserClick: () -> Unit = {},
    onViewProfileClick: () -> Unit = {}
) {
    Dialog(onDismissRequest = { onDismissRequest() },
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            decorFitsSystemWindows = false )
    ) {
        // Full-screen Box
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    onDismissRequest()
                },
            contentAlignment = Alignment.BottomCenter
        ) {
            // Container to match screen width
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(Color.Transparent)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) { /* consume to avoid dismiss */ }
            ) {
                BottomDialogContent(
                    onRemoveUserClick = {
                        onDismissRequest()
                        onRemoveUserClick()
                    },
                    onViewProfileClick = {
                        onDismissRequest()
                        onViewProfileClick()
                    }
                )
            }
        }
    }
}


@Composable
fun BottomDialogContent(
    onRemoveUserClick: () -> Unit = {},
    onViewProfileClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(15.dp),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.background(Color.White)) {
            RowItemDialog(
                icon = R.drawable.remove_ic_user,
                text = "Remove User",
                onClick = onRemoveUserClick
            )

            RowItemDialog(
                icon = R.drawable.view_ic,
                text = "View Profile",
                onClick = onViewProfileClick
            )
        }
    }
}

@Composable
fun RowItemDialog(
    icon: Int,
    text: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = null,
            tint = Color.Unspecified,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Medium,
                fontFamily = FontFamily(Font(R.font.outfit_medium)),
                fontSize = 14.sp,
                color = Color.Black
            )
        )
    }
}


