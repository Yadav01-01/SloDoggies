package com.bussiness.slodoggiesapp.ui.component.businessProvider

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor
import com.bussiness.slodoggiesapp.ui.theme.TextGrey

@Composable
fun SearchResultItem(
    name: String,
    label: String,
    imageRes: Int,
    onRemove: () -> Unit,
    onItemClick: () -> Unit,
    labelVisibility : Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick() }
            .padding(horizontal = 0.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Profile Image
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = "$name profile image",
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(12.dp))

        // Name and Label
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = name,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.outfit_regular)),
                color = Color.Black
            )

            if(labelVisibility){
                Text(
                    text = label,
                    fontSize = 11.sp,
                    fontFamily = FontFamily(Font(R.font.outfit_medium)),
                    color = PrimaryColor,
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .background(
                            color = Color(0xFFE5EFF2),
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 0.dp)
                )
            }

        }

        Icon(
            painter = painterResource(id = R.drawable.close_ic),
            contentDescription = "Remove",
            tint = Color.Unspecified,
            modifier = Modifier
                .wrapContentSize()
                .clickable { onRemove() }
        )
    }
    Divider(Modifier.height(1.dp).background(color = Color(0xFFE5EFF2)))
}

@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "Search"
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(42.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(Color(0xFFF4F4F4)) // light grey background
            .padding(top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()+10.dp,
                bottom = 15.dp, start = 15.dp, end = 15.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(R.drawable.search_ic),
                contentDescription = "Search",
                modifier = Modifier.wrapContentSize()
            )

            Spacer(modifier = Modifier.width(8.dp))

            BasicTextField(
                value = query,
                onValueChange = onQueryChange,
                singleLine = true,
                textStyle = TextStyle(
                    color = TextGrey,
                    fontSize = 16.sp
                ),
                decorationBox = { innerTextField ->
                    if (query.isEmpty()) {
                        Text(
                            text = placeholder,
                            color = Color.Gray,
                            fontSize = 16.sp
                        )
                    }
                    innerTextField()
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}


@Composable
fun HashtagChip(tag: String) {
    Box(
        modifier = Modifier
            .background(
                color = Color(0xFFE5EFF2), // Light blue
                shape = RoundedCornerShape(4.dp)
            )
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Text(
            text = tag,
            color = PrimaryColor,
            fontSize = 11.sp,
            fontFamily = FontFamily(Font(R.font.outfit_regular))
        )
    }
}

@Composable
fun DetailText(label: String,backgroundColor: Color,textColor: Color){
    Text(
        text = label,
        fontSize = 12.sp,
        fontFamily = FontFamily(Font(R.font.outfit_medium)),
        color = textColor,
        modifier = Modifier
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(20.dp)
            )
            .padding(horizontal = 10.dp, vertical = 2.dp)
            .wrapContentHeight().wrapContentWidth()
    )
}

@Composable
fun ProfileDetail(label: String, value: String) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = label,
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(R.font.outfit_medium)),
            color = PrimaryColor,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = value,
            fontSize = 15.sp,
            fontFamily = FontFamily(Font(R.font.outfit_medium)),
            color = Color.Black,
            textAlign = TextAlign.Center
        )
    }
}


@Composable
fun PetOwnerDetail(
    name: String,
    label: String,
    imageRes: Int,
    description: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 0.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Profile Image
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = "$name profile image",
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(12.dp))

        // Name and Label
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = name,
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp,
                fontFamily = FontFamily(Font(R.font.outfit_medium)),
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = label,
                fontSize = 11.sp,
                fontFamily = FontFamily(Font(R.font.outfit_medium)),
                color = PrimaryColor,
                modifier = Modifier
                    .padding(top = 4.dp)
                    .background(
                        color = Color(0xFFE5EFF2),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(horizontal = 8.dp, vertical = 0.dp)
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = description,
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp,
                fontFamily = FontFamily(Font(R.font.outfit_regular)),
                color = Color.Black
            )

        }

    }
}
