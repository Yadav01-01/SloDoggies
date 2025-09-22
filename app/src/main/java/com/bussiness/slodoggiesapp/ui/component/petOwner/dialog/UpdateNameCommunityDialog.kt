package com.bussiness.slodoggiesapp.ui.component.petOwner.dialog

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.ui.component.petOwner.CommonBlueButton
import com.bussiness.slodoggiesapp.ui.component.petOwner.CommonWhiteButton

@Composable
fun UpdateNameCommunityDialog( currentName: String = "Event Community",
                               onDismiss: () -> Unit = {},
                               onRename: (String) -> Unit = {},
                               onCancel: () -> Unit = {}){

    var textFieldValue by remember { mutableStateOf(TextFieldValue(currentName)) }

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
                            .wrapContentSize()
                            .clip(CircleShape)
                            .padding(8.dp)
                    )
                }
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.45f).padding(10.dp)
                    ,

                    shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp, bottomStart = 16.dp, bottomEnd = 16.dp),
                    color = Color.White
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp)
                    ) {
                        // Title
                        Text(
                            text = "Name Community",
                            fontSize = 18.sp,
                            fontFamily = FontFamily(Font(R.font.outfit_medium)),
                            color = Color.Black,
                            modifier = Modifier.padding(bottom = 20.dp)
                        )

                        // Divider line
                        HorizontalDivider(
                            modifier = Modifier.padding(bottom = 15.dp),
                            thickness = 1.5.dp,
                            color = Color(0xFF949494)
                        )

                        // Text input field
                        OutlinedTextField(
                            value = textFieldValue,
                            onValueChange = { textFieldValue = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 32.dp),
                            placeholder = {
                                Text(
                                    text = "Event Community",
                                    fontSize = 15.sp,
                                    fontFamily = FontFamily(Font(R.font.outfit_regular)),
                                    color = Color(0xFF949494)
                                )
                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color(0xFF4A9B8E),
                                unfocusedBorderColor = Color(0xFF949494),
                                focusedTextColor = Color.Black,
                                unfocusedTextColor = Color.Black
                            ),
                            shape = RoundedCornerShape(12.dp),
                            singleLine = true
                        )

                        // Buttons row
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {

                            CommonWhiteButton(
                                text = "Cancel",
                                onClick = {
                                    // Handle skip action
                                },
                                fontSize = 16.sp,
                                modifier = Modifier.weight(1f),
                            )
                            CommonBlueButton(
                                text = "Rename",
                                onClick = {
                                    // Handle save action
                                },
                                fontSize = 15.sp,
                                modifier = Modifier.weight(1f),
                            )
                        }
                    }
                }

            }
        }
    }
}

@Preview
@Composable
fun UpdateNameCommunityDialogPreview() {
    MaterialTheme {
        UpdateNameCommunityDialog(
            currentName = "Event Community"
        )
    }
}
