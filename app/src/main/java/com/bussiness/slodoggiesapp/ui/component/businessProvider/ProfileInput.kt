package com.bussiness.slodoggiesapp.ui.component.businessProvider

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.model.businessProvider.AudienceData
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor
import com.bussiness.slodoggiesapp.ui.theme.TextGrey

@Composable
fun AudienceSelection(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Text(
        text = text,
        fontFamily = if (selected) FontFamily(Font(R.font.outfit_semibold)) else FontFamily(Font(R.font.outfit_regular)),
        fontSize = 14.sp,
        color = if (selected) Color.White else Color.Black,
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(if (selected) PrimaryColor else Color.White)
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 8.dp)
    )
}

@Composable
fun AudienceListItem(
    data: AudienceData,
    isFollower: Boolean,
    onPrimaryClick: () -> Unit,
    onRemoveClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 0.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = data.profileImage),
            contentDescription = "Profile Image",
            modifier = Modifier
                .size(44.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Fit
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = data.name,
                    fontFamily = FontFamily(Font(R.font.outfit_regular)),
                    fontSize = 14.sp,
                    color = Color.Black
                )
                if (data.isVerified) {
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        painter = painterResource(R.drawable.ic_verified),
                        contentDescription = "Verified",
                        tint = Color.Unspecified,
                        modifier = Modifier.wrapContentSize()
                    )
                }
            }
        }

        OutlinedButton(
            onClick = onPrimaryClick,
            shape = RoundedCornerShape(10),
            border = BorderStroke(1.dp, PrimaryColor),
            modifier = Modifier.wrapContentWidth().height(30.dp),
            contentPadding = PaddingValues(horizontal = 18.dp, vertical = 0.dp)
        ) {
            Text(
                text = if (isFollower) "Follow Back" else "Message",
                fontFamily = FontFamily(Font(R.font.outfit_regular)),
                fontSize = 12.sp,
                color = PrimaryColor
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Icon(
            painter = painterResource(R.drawable.bold_cross_ic),
            contentDescription = "Remove",
            modifier = Modifier
                .wrapContentSize()
                .clickable { onRemoveClick() },
            tint = Color.Unspecified
        )
    }
    Spacer(modifier = Modifier.height(15.dp))
    HorizontalDivider(modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(Color(0xFFE5EFF2)))
}

@Composable
fun ProfileImageWithUpload(
    imagePainter: Painter,
    onUploadClick: () -> Unit
) {
    Box(
        modifier = Modifier.size(120.dp),
        contentAlignment = Alignment.BottomEnd
    ) {
        // Circular image with border
        Box(
            modifier = Modifier
                .size(140.dp)
                .clip(CircleShape)
                .border(4.dp, Color(0xFFE5EFF2), CircleShape)
                .background(Color.LightGray),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = imagePainter,
                contentDescription = "Profile Image",
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        }

        // Upload button
        Icon(
            painter = painterResource(id = R.drawable.upload_i),
            contentDescription = "Upload",
            tint = Color.Unspecified,
            modifier = Modifier.wrapContentSize().clickable { onUploadClick() }

        )
    }
}

@Composable
fun ProfileHeadingText(text: String,modifier: Modifier){
    Text(
        text = text,
        style = MaterialTheme.typography.titleLarge.copy(
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        ),
        fontFamily = FontFamily(Font(R.font.poppins)),
        color = Color.Black,
        modifier = modifier
    )
}

@Composable
fun SponsorButton(
    modifier: Modifier = Modifier,
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier
            .height(36.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp)),
        border = BorderStroke(1.dp, if (isSelected) PrimaryColor else TextGrey),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = Color.White,
            contentColor = if (isSelected) PrimaryColor else TextGrey
        ),
        shape = RoundedCornerShape(10.dp)
    ) {
        Text(
            text = text,
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(R.font.outfit_regular)),
            color = if (isSelected) PrimaryColor else TextGrey
        )
    }
}


@Composable
fun HeadingTextWithIcon(textHeading: String, onBackClick: () -> Unit) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(
            start = 15.dp,
            end = 15.dp,
            top = 15.dp,
            bottom = 18.dp
        ) .background(Color.White)

    ) {
        Icon(
            painter = painterResource(R.drawable.back_ic),
            contentDescription = "back",
            tint = PrimaryColor,
            modifier = Modifier
                .clickable { onBackClick() }
                .wrapContentSize()
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = textHeading,
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Medium,
                fontSize = 20.sp
            ),
            fontFamily = FontFamily(Font(R.font.outfit_medium)),
            color = Color(0xFF221B22)
        )
    }
}

@Composable
fun DialogButton(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier
) {
    Text(
        text = text,
        fontFamily = if (selected) FontFamily(Font(R.font.outfit_bold)) else FontFamily(Font(R.font.outfit_medium)),
        fontSize = 16.sp,
        textAlign = TextAlign.Center,
        color = if (selected) Color.White else Color.Black,
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(if (selected) PrimaryColor else Color.White)
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 8.dp)
    )
}