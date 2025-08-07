package com.bussiness.slodoggiesapp.ui.component.businessProvider

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
    previewImageUrl: String? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Profile Image
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

        Spacer(modifier = Modifier.width(10.dp))

        // Message content (username + message + time)
        Row(
            modifier = Modifier
                .weight(1f),
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

        // Preview image if available
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
    }
}

