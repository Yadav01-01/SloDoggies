package com.bussiness.slodoggiesapp.ui.component.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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

@Composable
fun BottomMessageBar(
    modifier: Modifier = Modifier,
    onAttachmentClick: () -> Unit ,
    onSendClick: () -> Unit ,
    message: String,
    onMessageChange: (String) -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(Color.White)
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Message input area
            Row(
                modifier = Modifier
                    .weight(1f)
                    .background(Color(0xFFE8F0F2), shape = RoundedCornerShape(12.dp))
                    .padding(horizontal = 12.dp, vertical = 0.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.attach_ic),
                    contentDescription = "Attachment",
                    tint = Color.Unspecified,
                    modifier = Modifier
                        .wrapContentSize()
                        .clickable { onAttachmentClick() }
                )

                Spacer(modifier = Modifier.width(8.dp))

                TextField(
                    value = message,
                    onValueChange = onMessageChange,
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
                    .clickable { onSendClick() },
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
fun ChatBubble(message: ChatMessage) {

    val bubbleShape = if (message.isUser) {
        RoundedCornerShape(topStart = 10.dp, topEnd = 0.dp, bottomEnd = 10.dp, bottomStart = 10.dp)
    } else {
        RoundedCornerShape(topStart = 0.dp, topEnd = 10.dp, bottomEnd = 10.dp, bottomStart = 10.dp)
    }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (message.isUser) Arrangement.End else Arrangement.Start
    ) {
        if (!message.isUser) {
            AsyncImage(
                model = "",
                contentDescription = "User Image",
                modifier = Modifier
                    .size(45.dp)
                    .clip(RoundedCornerShape(10.dp))
            )
        }

        Box(
            modifier = Modifier
                .clip(bubbleShape)
                .background(
                    color = if (message.isUser) Color(0xFF00BFA6) else Color(0xFFF2F2F2)
                )
                .padding(horizontal = 12.dp, vertical = 8.dp)
                .widthIn(max = 250.dp)
        ) {
            Text(
                text = message.text,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = if (message.isUser) Color.White else Color.Black,
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(R.font.outfit_regular)),
                    fontWeight = FontWeight.Normal
                )
            )
        }

    }
}
