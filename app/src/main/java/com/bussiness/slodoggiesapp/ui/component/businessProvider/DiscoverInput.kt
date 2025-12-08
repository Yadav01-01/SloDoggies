package com.bussiness.slodoggiesapp.ui.component.businessProvider

import android.graphics.Bitmap
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.data.model.businessProvider.EventPost
import com.bussiness.slodoggiesapp.data.model.businessProvider.GalleryItem
import com.bussiness.slodoggiesapp.data.model.petOwner.PetPlaceItem
import com.bussiness.slodoggiesapp.data.newModel.ownerProfile.OwnerPostItem
import com.bussiness.slodoggiesapp.ui.component.common.getVideoThumbnail
import com.bussiness.slodoggiesapp.ui.screens.commonscreens.home.content.PostLikes
import com.bussiness.slodoggiesapp.ui.screens.commonscreens.home.content.PostOptionsMenu
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor
import com.bussiness.slodoggiesapp.ui.theme.TextGrey

@Composable
fun SearchResultItem(
    name: String,
    label: String,
    imageRes: Int,
    onRemove: () -> Unit,
    onItemClick: () -> Unit,
    labelVisibility : Boolean,
    crossVisibility : Boolean
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

        if (crossVisibility){
            Icon(
                painter = painterResource(id = R.drawable.close_ic),
                contentDescription = "Remove",
                tint = Color.Unspecified,
                modifier = Modifier
                    .wrapContentSize()
                    .clickable { onRemove() }
            )
            Spacer(Modifier.width(15.dp))
        }
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
            .height(50.dp) // Standard height
            .clip(RoundedCornerShape(10.dp))
            .background(Color(0xFFF4F4F4)) // Light grey background
            .padding(horizontal = 16.dp), // Horizontal padding only
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.search_ic),
                contentDescription = "Search",
                modifier = Modifier
                    .size(20.dp)
            )

            Spacer(modifier = Modifier.width(10.dp))

            BasicTextField(
                value = query,
                onValueChange = onQueryChange,
                singleLine = true,
                textStyle = TextStyle(
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.outfit_regular))
                ),
                modifier = Modifier.fillMaxWidth(),
                decorationBox = { innerTextField ->
                    if (query.isEmpty()) {
                        Text(
                            text = placeholder,
                            color = Color.Gray,
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(R.font.outfit_regular))
                        )
                    }
                    innerTextField()
                }
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
fun FilterChipBox(
    text: String,
    borderColor: Color,
    backgroundColor: Color,
    textColor: Color
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .height(42.dp)
            .wrapContentWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(backgroundColor)
            .border(1.dp, borderColor, RoundedCornerShape(10.dp))
            .padding(horizontal = 12.dp)
    ) {
        Text(
            text = text,
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(R.font.outfit_regular)),
            fontWeight = FontWeight.Normal,
            color = textColor,
            textAlign = TextAlign.Center
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
            .padding(horizontal = 10.dp, vertical = 0.dp)
            .wrapContentHeight().wrapContentWidth()
    )
}

@Composable
fun ProfileDetail(
    label: String,
    value: String,
    onDetailClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.clickable { onDetailClick() },
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
        Spacer(modifier = Modifier.height(2.dp))
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
                .size(65.dp)
                .clip(CircleShape)
                .border(3.dp, Color(0xFFE5EFF2), CircleShape),
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
                color = Color.Black,
                lineHeight = 10.sp,
            )

            Spacer(modifier = Modifier.height(0.dp))

            Text(
                text = label,
                fontSize = 10.sp,
                fontFamily = FontFamily(Font(R.font.outfit_medium)),
                color = PrimaryColor,
                lineHeight = 20.sp,
                modifier = Modifier
                    .padding(top = 4.dp)
                    .background(
                        color = Color(0xFFE5EFF2),
                        shape = RoundedCornerShape(20.dp)
                    )
                    .padding(horizontal = 8.dp, vertical = 0.dp)
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = description,
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp,
                fontFamily = FontFamily(Font(R.font.outfit_regular)),
                color = Color.Black,
                lineHeight = 15.sp
            )

        }

    }
}
@Composable
fun GalleryItemCardProfile(
    item: OwnerPostItem,
    height: Int = 150,
    onClickItem: () -> Unit
) {

    val media = item.mediaPath?.firstOrNull()
    val url = media?.url ?: ""
    val context = LocalContext.current

    // Thumbnail state
    var thumbnail by remember { mutableStateOf<Bitmap?>(null) }

    // If video → generate thumbnail using your function
    LaunchedEffect(url) {
        if (media?.type == "video") {
            thumbnail = getVideoThumbnail( url)
        }
    }

    Box(
        modifier = Modifier
            .height(height.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(Color.LightGray)
            .clickable { onClickItem() }
    ) {

        when {
            // Show generated thumbnail if video
            media?.type == "video" && thumbnail != null -> {
                Image(
                    bitmap = thumbnail!!.asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            // Otherwise load normal image
            else -> {
                AsyncImage(
                    model = url,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(R.drawable.no_image),
                    error = painterResource(R.drawable.no_image),
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

        // Video icon
        if (media?.type == "video") {
            Icon(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = "Video",
                tint = Color.White,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
                    .size(22.dp)
                    .background(Color.Black.copy(alpha = 0.4f), CircleShape)
                    .padding(3.dp)
            )
        }
    }
}



@Composable
fun GalleryItemCard(item: GalleryItem, height: Int = 150, onClickItem: () -> Unit) {
    Box(
        modifier = Modifier
            .height(height.dp) // You can adjust height here
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(Color.LightGray)
            .clickable { onClickItem() }
    ) {
        Image(
            painter = painterResource(id = item.imageResId),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        if (item.isVideo) {
            Icon(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = "Video",
                tint = Color.Gray,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(horizontal = 6.dp, vertical = 10.dp)
                    .size(18.dp)
                    .background(Color.White.copy(alpha = 0.6f), RectangleShape)
                    .padding(2.dp)
            )
        }
    }
}

@Composable
fun PetPlaceCard(placeItem: PetPlaceItem, onItemClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(RoundedCornerShape(10.dp))
            .background(Color.White)
            .border(1.dp, Color(0xFFE6F0F2), RoundedCornerShape(10.dp))
            .padding(10.dp)
            .clickable { onItemClick() }
    ) {
        Image(
            painter = painterResource(id = placeItem.image),
            contentDescription = "Pet Place Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(110.dp)
                .clip(RoundedCornerShape(5.dp))
                .background(TextGrey)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = placeItem.name,
            fontFamily = FontFamily(Font(R.font.outfit_regular)),
            fontSize = 14.sp,
            color = Color.Black,
            fontWeight = FontWeight.Medium,
            lineHeight = 18.sp
        )

        Spacer(modifier = Modifier.height(2.dp))

        Text(
            text = placeItem.location,
            fontFamily = FontFamily(Font(R.font.outfit_regular)),
            fontSize = 12.sp,
            color = TextGrey,
            fontWeight = FontWeight.Normal,
            lineHeight = 18.sp
        )

        Spacer(modifier = Modifier.height(2.dp))

        Text(
            text = placeItem.distance,
            fontFamily = FontFamily(Font(R.font.outfit_regular)),
            fontSize = 12.sp,
            color = Color.Black,
            fontWeight = FontWeight.Normal,
            lineHeight = 18.sp
        )
    }
}

@Composable
fun SocialEventCard(event: com.bussiness.slodoggiesapp.data.model.businessProvider.EventPost, onJoinCommunity: () -> Unit, onReportClick: () -> Unit, onShareClick: () -> Unit, onSaveClick : () -> Unit, onProfileClick: () -> Unit) {
    var isFollowed by remember { mutableStateOf(false) }
    Column(modifier = Modifier
        .background(Color.White)
        .border(1.dp, Color(0xFFE5EFF2), RoundedCornerShape(10.dp))
        .clip(RoundedCornerShape(10.dp))
    ) {

        // Top Row: User Info
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = event.userImage) ,
                contentDescription = "User Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .clickable { onProfileClick() }
            )

            Spacer(modifier = Modifier.width(8.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically){
                    Text(
                        text = event.userName,
                        fontWeight = FontWeight.Medium,
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(R.font.outfit_medium)),
                        color = Color.Black
                    )

                    Spacer(Modifier.width(8.dp))

                    val interactionSource = remember { MutableInteractionSource() }

                    OutlinedButton(
                        onClick = { isFollowed = !isFollowed },
                        modifier = Modifier
                            .height(24.dp)
                            .padding(horizontal = 10.dp),
                        shape = RoundedCornerShape(6.dp),
                        border = if (isFollowed) BorderStroke(1.dp, PrimaryColor) else null,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isFollowed) Color.White else PrimaryColor,
                            contentColor = if (isFollowed) PrimaryColor else Color.White
                        ),
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 0.dp),
                        interactionSource = interactionSource
                    ) {
                        Text(
                            text = if (isFollowed) "Following" else "Follow",
                            fontFamily = FontFamily(Font(R.font.outfit_regular)),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Normal
                        )
                    }
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = event.label,
                        fontSize = 8.sp,
                        fontFamily = FontFamily(Font(R.font.outfit_medium)),
                        color = PrimaryColor,
                        lineHeight = 20.sp,
                        modifier = Modifier
                            .padding(top = 4.dp)
                            .background(
                                color = Color(0xFFE5EFF2),
                                shape = RoundedCornerShape(16.dp)
                            )
                            .padding(horizontal = 8.dp, vertical = 0.dp)
                    )

                    Spacer(Modifier.width(8.dp))

                    Text(
                        text = event.time,
                        fontSize = 12.sp,
                        color = TextGrey,
                        fontFamily = FontFamily(Font(R.font.outfit_regular)),
                    )
                }
            }



            PostOptionsMenu (modifier = Modifier, onReportClick = onReportClick)
        }

        // Title & Details
        Column(modifier = Modifier.padding(horizontal = 12.dp)) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = event.eventTitle,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.outfit_medium)),
                    color = Color.Black,
                    modifier = Modifier.weight(1f)
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.cal_ic), // your calendar icon
                        contentDescription = "Calendar",
                        tint = Color.Unspecified,
                        modifier = Modifier.wrapContentSize()
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = event.eventStartDate,
                        fontSize = 14.sp,
                        color = Color.Black,
                        fontFamily = FontFamily(Font(R.font.outfit_regular)),
                        fontWeight = FontWeight.Normal
                    )
                }
            }


            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = event.eventDescription,
                    fontSize = 14.sp,
                    color = TextGrey,
                    maxLines = 8,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 18.sp,
                    fontFamily = FontFamily(Font(R.font.outfit_regular)),
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.weight(1f) // allows flexible width
                )

                Spacer(modifier = Modifier.width(8.dp))

//                Row(
//                    verticalAlignment = Alignment.CenterVertically,
//                    horizontalArrangement = Arrangement.End
//                ) {
//                    Icon(
//                        painter = painterResource(id = R.drawable.time_ic), // your duration icon
//                        contentDescription = "Duration",
//                        tint = Color.Unspecified,
//                        modifier = Modifier.wrapContentSize()
//                    )
//
//                    Spacer(modifier = Modifier.width(4.dp))
//
//                    Text(
//                        text = event.eventDuration,
//                        fontSize = 12.sp,
//                        color = Color.Gray,
//                        fontFamily = FontFamily(Font(R.font.outfit_regular))
//                    )
//                }
                Row(verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End) {
                    Icon(
                        painter = painterResource(id = R.drawable.cal_ic), // your calendar icon
                        contentDescription = "Calendar",
                        tint = Color.Unspecified,
                        modifier = Modifier.wrapContentSize()
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = event.eventEndDate,
                        fontSize = 14.sp,
                        color = Color.Black,
                        fontFamily = FontFamily(Font(R.font.outfit_regular)),
                        fontWeight = FontWeight.Normal
                    )
                }
            }


            Spacer(modifier = Modifier.height(4.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(painter = painterResource(R.drawable.location_ic_icon), contentDescription = null, tint = Color.Unspecified, modifier = Modifier.wrapContentSize())
                Spacer(modifier = Modifier.width(4.dp))
                Text(event.location, fontSize = 14.sp, fontFamily = FontFamily(Font(R.font.outfit_medium)), color = Color.Black)
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Post Image
        Image(
            painter = painterResource(id = event.postImage),
            contentDescription = "Event Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(230.dp)
                .clip(RoundedCornerShape(0.dp))
        )

        // CTA Button
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Black)
                .padding(horizontal = 12.dp, vertical = 8.dp)
                .clickable { onJoinCommunity() },
            contentAlignment = Alignment.CenterEnd
        ) {
            Text("Join Community", modifier = Modifier.padding(end = 35.dp),
                color = Color.White, fontSize = 16.sp, fontFamily = FontFamily(Font(R.font.outfit_medium)))
            Icon(
                painter = painterResource(R.drawable.ic_chat_icon),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .wrapContentSize()
            )
        }

        // Bottom Row: Likes, Comments, Shares, Bookmark
        PostLikes(likes = event.likes, comments = event.comments, shares = event.shares, onShareClick = { onShareClick() }, onSaveClick = { onSaveClick() }
            , onLikeClick = {

            },
            onCommentClick = {

            },
            isLike = false,
            isSave = false
        )
    }
}

@Composable
fun IconWithCount(imageRes: Int, count: String,onItemClick: () -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(painter = painterResource(id = imageRes), contentDescription = null, modifier = Modifier.wrapContentSize().clickable { onItemClick() })
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = count, fontSize = 14.sp, fontFamily = FontFamily(Font(R.font.outfit_regular)), color = Color.Black)
    }
}



@Composable
fun HashtagSection() {
    val hashtags = listOf("#DogYoga", "#PupWalk2025", "#VetPicnic", "#PetGala", "#rain")

    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        items(hashtags) { tag ->
            HashtagChip(tag)
        }
    }
}

@Composable
fun CategorySection(
    categories: List<String>,
    selectedCategory: String,
    onCategorySelected: (String) -> Unit
) {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        items(categories) { category ->
            val isSelected = selectedCategory == category
            Box(
                modifier = Modifier.clickable { onCategorySelected(category) }
            ) {
                FilterChipBox(
                    text = category,
                    borderColor = if (isSelected) PrimaryColor else TextGrey,
                    backgroundColor = if (isSelected) PrimaryColor else Color.White,
                    textColor = if (isSelected) Color.White else TextGrey
                )
            }
        }
    }
}
