package com.bussiness.slodoggiesapp.ui.dialog

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.ui.component.businessProvider.DialogButton
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor
import com.bussiness.slodoggiesapp.ui.theme.TextGrey

@Composable
fun CommentReplyDialog(
    onDismiss: () -> Unit,
    onClickSubmit : () -> Unit,
    message: String,
    onMessageChange: (String) -> Unit,
){

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

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                shape = RoundedCornerShape(12.dp),
                color = Color.White
            ) {
                Column(
                    modifier = Modifier
                        .background(Color.White, shape = RoundedCornerShape(12.dp))
                        .padding(horizontal = 10.dp, vertical = 10.dp)
                ) {

                    Text(
                        text = "Reply",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Medium,
                            fontFamily = FontFamily(Font(R.font.outfit_medium)),
                            fontSize = 14.sp,
                            color = Color.Black
                        ),
                        modifier = Modifier.align(Alignment.Start)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = message,
                        onValueChange ={ onMessageChange(it) }, // <-- Use external state handler
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 90.dp),
                        placeholder = {
                            Text(
                                text ="Write your reply....",
                                color = TextGrey,
                                fontFamily = FontFamily(Font(R.font.outfit_regular)),
                                fontSize = 15.sp
                            )
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = PrimaryColor,
                            unfocusedBorderColor = TextGrey
                        ),
                        shape = RoundedCornerShape(8.dp),
                        singleLine = false,
                        maxLines = 4
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
                        SubmitButton(
                            text = "Submit Reply",
                            onClick = {
                                onClickSubmit()
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
fun SubmitButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background( PrimaryColor)
            .clickable(
                onClick = onClick,
            )
            .padding(horizontal = 16.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontFamily = FontFamily(Font(R.font.outfit_medium)),
            fontSize = 15.sp,
            textAlign = TextAlign.Center,
            color = Color.White
        )
    }
}