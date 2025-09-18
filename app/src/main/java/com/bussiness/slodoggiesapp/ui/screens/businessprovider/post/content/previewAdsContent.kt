package com.bussiness.slodoggiesapp.ui.screens.businessprovider.post.content

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.ui.screens.commonscreens.home.content.PostOptionsMenu
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor

@Composable
 fun SponsorPostHeader(userImage: String ,user: String, time: String, onReportClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 0.dp).padding(start = 15.dp, top = 15.dp ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = userImage,
                placeholder = painterResource(R.drawable.ic_person_icon),
                error =  painterResource(R.drawable.ic_person_icon),
                contentDescription = "Profile",
                modifier = Modifier
                    .size(35.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(   modifier = Modifier.wrapContentHeight(),
                verticalArrangement = Arrangement.spacedBy(0.dp)) {
                Text(
                    text = user,
                    fontSize = 12.sp,
                    color = Color.Black,
                    lineHeight = 17.sp,
                    fontFamily = FontFamily(Font(R.font.outfit_medium))
                )

                Text(
                    text = time,
                    fontSize = 12.sp,
                    lineHeight = 17.sp,
                    color = Color(0xFF949494),
                    fontFamily = FontFamily(Font(R.font.outfit_regular))
                )
            }
        }

        PostOptionsMenu(modifier = Modifier.padding(end = 15.dp), onReportClick = onReportClick)
    }
}


@Composable
fun SponsorPostCaption(caption: String, description: String) {
    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 5.dp)) {
        Text(
            text = caption,
            fontSize = 13.sp,
            lineHeight = 17.sp,
            fontFamily = FontFamily(Font(R.font.outfit_bold)),
            color = Color.Black
        )
        if (description.isNotBlank()) {
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = description,
                fontSize = 12.sp,
                lineHeight = 17.sp,
                fontFamily = FontFamily(Font(R.font.outfit_regular)),
                color = Color.Black
            )
        }
    }
}


@Composable
fun SponsorPostMedia(mediaList: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(353.dp)
    ) {

        AsyncImage(
            model = R.drawable.dog1,
            contentDescription = "Post image",
            contentScale = ContentScale.Crop,
            modifier =  Modifier.fillMaxWidth().height(350.dp)
        )


        Box(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .fillMaxWidth()
                .height(50.dp)
                .background(PrimaryColor)
        ) {
            Text(
                text = "Sponsored",
                modifier = Modifier.padding(start = 12.dp).align(Alignment.CenterStart),
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.outfit_medium)),
                color = Color.White
            )
        }
    }
}

@Composable
fun PreviewHeading(heading : String){
    Text(
        text = heading,
        fontSize = 18.sp,
        lineHeight = 17.sp,
        fontFamily = FontFamily(Font(R.font.outfit_semibold)),
        color = PrimaryColor
    )
}

@Composable
fun PreviewSubHeading(heading : String,subHeading : String){
    Row {
        Text(
            text = heading,
            fontSize = 14.sp,
            lineHeight = 17.sp,
            fontFamily = FontFamily(Font(R.font.outfit_bold)),
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Spacer(modifier = Modifier.width(5.dp))
        Text(
            text = subHeading,
            fontSize = 14.sp,
            lineHeight = 17.sp,
            fontFamily = FontFamily(Font(R.font.outfit_regular)),
            fontWeight = FontWeight.Normal,
            color = Color.Black
        )

    }
}