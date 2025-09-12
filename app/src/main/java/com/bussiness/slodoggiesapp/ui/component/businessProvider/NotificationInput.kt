package com.bussiness.slodoggiesapp.ui.component.businessProvider

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.ui.theme.TextGrey

@Composable
fun NotificationItem(
    profileImageUrl: String,
    username: String,
    message: String,
    time: String,
    previewImageUrl: String? = null,
    type: String,
    onJoinChatClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Profile or event image
        if (type == "post") {
            AsyncImage(
                model = profileImageUrl,
                contentDescription = "Profile Image",
                modifier = Modifier
                    .size(45.dp)
                    .clip(CircleShape),
                placeholder = painterResource(id = R.drawable.dmy_ic),
                error = painterResource(id = R.drawable.dmy_ic),
                contentScale = ContentScale.Crop
            )
        } else {
            Image(
                painter = painterResource(id = R.drawable.msg_event),
                contentDescription = "Event Image",
                modifier = Modifier.size(45.dp), // keep consistent size
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.width(10.dp))

        // Message content
        if (type == "post") {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "$username $message",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontFamily = FontFamily(Font(R.font.outfit_regular)),
                        fontSize = 14.sp,
                        color = Color.Black
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(6.dp))

                Text(
                    text = time,
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontFamily = FontFamily(Font(R.font.outfit_regular)),
                        fontSize = 10.sp,
                        color = TextGrey
                    ),
                    maxLines = 1
                )
            }
        } else {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = username,
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.outfit_medium)),
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = message,
                    fontSize = 14.sp,
                    color = TextGrey,
                    lineHeight = 18.sp,
                    fontFamily = FontFamily(Font(R.font.outfit_regular)),
                    fontWeight = FontWeight.Normal,
                    maxLines = 4,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = time,
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(R.font.outfit_regular)),
                    color = Color(0xFF9D9D9D)
                )
            }
        }

        // Preview image / Join button
        if (type == "post") {
            previewImageUrl?.let {
                Spacer(modifier = Modifier.width(10.dp))

                AsyncImage(
                    model = it,
                    contentDescription = "Preview Image",
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(6.dp)),
                    placeholder = painterResource(id = R.drawable.place_ic),
                    error = painterResource(id = R.drawable.place_ic),
                    contentScale = ContentScale.Crop
                )
            }
        } else {
            Spacer(modifier = Modifier.width(10.dp))

            Button(
                onClick = onJoinChatClick,
                modifier = Modifier.height(28.dp).align(Alignment.Top),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF258694)
                ),
                shape = RoundedCornerShape(6.dp),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 0.dp)
            ) {
                Text(
                    text = "Join Chat",
                    fontSize = 12.sp,
                    color = Color.White,
                    fontFamily = FontFamily(Font(R.font.outfit_regular)),
                    fontWeight = FontWeight.Normal
                )
            }
        }
    }
}


