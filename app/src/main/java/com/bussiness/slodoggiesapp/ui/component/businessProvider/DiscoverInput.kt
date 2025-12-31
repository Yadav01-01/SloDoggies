package com.bussiness.slodoggiesapp.ui.component.businessProvider

import android.util.Log
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
import com.bussiness.slodoggiesapp.data.model.businessProvider.GalleryItem
import com.bussiness.slodoggiesapp.data.newModel.discover.HashtagItem
import com.bussiness.slodoggiesapp.data.newModel.discover.PetPlaceItem
import com.bussiness.slodoggiesapp.data.newModel.home.PostItem
import com.bussiness.slodoggiesapp.data.newModel.ownerProfile.OwnerPostItem
import com.bussiness.slodoggiesapp.ui.screens.commonscreens.home.content.CommunityPostLikes
import com.bussiness.slodoggiesapp.ui.screens.commonscreens.home.content.PostImage
import com.bussiness.slodoggiesapp.ui.screens.commonscreens.home.content.PostOptionsMenu
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor
import com.bussiness.slodoggiesapp.ui.theme.TextGrey

//@Composable
//fun SearchResultItem(
//    name: String,
//    label: String,
//    imageRes: String?,
//    onRemove: () -> Unit,
//    onItemClick: () -> Unit,
//    labelVisibility : Boolean,
//    crossVisibility : Boolean
//) {
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .clickable { onItemClick() }
//            .padding(horizontal = 0.dp, vertical = 12.dp),
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//
//        val imageModel: Any = imageRes
//            ?.takeIf { it.isNotBlank() }
//            ?: R.drawable.ic_dog_face_icon
//        if (imageRes.isNullOrBlank()) {
//          Log.d("IMAGE_DEBUG","i am here in the imageModel")
//            Image(
//                painter = painterResource(id = R.drawable.ic_dog_face_icon),
//                contentDescription = "$name profile image",
//                modifier = Modifier
//                    .size(48.dp)
//                    .clip(CircleShape), // Matches the AsyncImage look
//                contentScale = ContentScale.Crop
//            )
//
//        } else {
//            // Remote image
//            AsyncImage(
//                model = imageModel,
//                placeholder = painterResource(R.drawable.ic_dog_face_icon),
//                error = painterResource(R.drawable.ic_dog_face_icon),
//                contentDescription = "$name profile image",
//                modifier = Modifier
//                    .size(48.dp)
//                    .clip(CircleShape),
//                contentScale = ContentScale.Crop
//            )
//        }
//
//        Spacer(modifier = Modifier.width(12.dp))
//
//        // Name and Label
//        Column(modifier = Modifier.weight(1f)) {
//            Text(
//                text = name,
//                fontWeight = FontWeight.SemiBold,
//                fontSize = 14.sp,
//                fontFamily = FontFamily(Font(R.font.outfit_regular)),
//                color = Color.Black
//            )
//
//            if(labelVisibility){
//                Text(
//                    text = label,
//                    fontSize = 11.sp,
//                    fontFamily = FontFamily(Font(R.font.outfit_medium)),
//                    color = PrimaryColor,
//                    modifier = Modifier
//                        .padding(top = 4.dp)
//                        .background(
//                            color = Color(0xFFE5EFF2),
//                            shape = RoundedCornerShape(16.dp)
//                        )
//                        .padding(horizontal = 8.dp, vertical = 0.dp)
//                )
//            }
//
//        }
//
//        if (crossVisibility){
//            Icon(
//                painter = painterResource(id = R.drawable.close_ic),
//                contentDescription = "Remove",
//                tint = Color.Unspecified,
//                modifier = Modifier
//                    .wrapContentSize()
//                    .clickable { onRemove() }
//            )
//            Spacer(Modifier.width(15.dp))
//        }
//    }
//    Divider(Modifier.height(1.dp).background(color = Color(0xFFE5EFF2)))
//}


@Composable
fun SearchResultItem(
    name: String,
    label: String,
    imageRes: String?,
    onRemove: () -> Unit,
    onItemClick: () -> Unit,
    labelVisibility: Boolean,
    crossVisibility: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick() }
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // --- IMAGE SECTION ---
        if (imageRes.isNullOrBlank()) {
            Log.d("IMAGE_DEBUG", "Showing local drawable: $name")
            // Change 1: Use Image instead of Icon
            // Change 2: Temporarily remove clip/crop to see if it appears
            Image(
                painter = painterResource(id = R.drawable.ic_dog_face_icon),
                contentDescription = "Local Image",
                modifier = Modifier
                    .size(48.dp)
                    .background(Color.LightGray, CircleShape), // Add background to see the area
                contentScale = ContentScale.Fit // Fit ensures the whole icon is visible
            )
        } else {
            AsyncImage(
                model = imageRes,
                placeholder = painterResource(R.drawable.ic_dog_face_icon),
                error = painterResource(R.drawable.ic_dog_face_icon),
                contentDescription = "Remote Image",
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        }
        // -----------------------

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(text = name, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
            if (labelVisibility) {
                Text(
                    text = label,
                    fontSize = 11.sp,
                    color = Color.Blue, // Use a standard color for testing
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .background(Color(0xFFE5EFF2), RoundedCornerShape(16.dp))
                        .padding(horizontal = 8.dp)
                )
            }
        }

        if (crossVisibility) {
            Icon(
                painter = painterResource(id = R.drawable.close_ic),
                contentDescription = "Remove",
                modifier = Modifier.clickable { onRemove() }.padding(8.dp)
            )
        }
    }
    Divider(modifier = Modifier.fillMaxWidth().height(1.dp), color = Color(0xFFE5EFF2))
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
fun HashtagChip(
    tag: HashtagItem,
    onHashClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .background(
                color = Color(0xFFE5EFF2),
                shape = RoundedCornerShape(4.dp)
            )
            .clickable { onHashClick() }
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Text(
            text = tag.hashtag,
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
    imageRes: String?,
    description: String?
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 0.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        AsyncImage(
            model = imageRes?:"",
            placeholder = painterResource(R.drawable.ic_person_icon1),
            error = painterResource(R.drawable.ic_person_icon1),
            contentDescription = "$name profile image",
            modifier = Modifier
                .size(65.dp)
                .clip(CircleShape)
                .border(3.dp, Color(0xFFE5EFF2), CircleShape),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = name,
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp,
                fontFamily = FontFamily(Font(R.font.outfit_medium)),
                color = Color.Black,
                lineHeight = 10.sp,
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = description.orEmpty(),
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
 //   var thumbnail by remember { mutableStateOf<Bitmap?>(null) }
    var thumbnailUrl =""
    // If video → generate thumbnail using your function
    LaunchedEffect(url) {
        if (media?.type == "video") {
            thumbnailUrl = media?.thumbnailUrl?:""//getVideoThumbnail( url)
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
            media?.type == "video" && thumbnailUrl != null -> {
//                Image(
//                    bitmap = thumbnail!!.asImageBitmap(),
//                    contentDescription = null,
//                    modifier = Modifier.fillMaxSize(),
//                    contentScale = ContentScale.Crop
//                )
                AsyncImage(
                    model = thumbnailUrl,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    placeholder = painterResource(R.drawable.no_image),
                    error = painterResource(R.drawable.no_image),
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
fun PetPlaceCard(placeItem: PetPlaceItem,
                 onItemClick: () -> Unit) {
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

        Log.d("TESTING_image",""+placeItem.image)

        AsyncImage(
            model  = placeItem.image,
            placeholder = painterResource(R.drawable.no_image),
            error = painterResource(R.drawable.no_image),
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
            text = placeItem.title?:"Unknown",// title of the place
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
fun SocialEventCard(postItem: PostItem.CommunityPost,onClickFollowing: () -> Unit,
                    onJoinedCommunity: () -> Unit, onReportClick: () -> Unit, onShareClick: () -> Unit, onLikeClick : () -> Unit, onProfileClick: () -> Unit,onInterested : () -> Unit,isFollowing: Boolean) {

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
            AsyncImage(
                model = postItem.media?.parentImageUrl ,
                placeholder = painterResource(R.drawable.ic_person_icon),
                error = painterResource(R.drawable.ic_person_icon),
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
                        text = postItem.userName,
                        fontWeight = FontWeight.Medium,
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(R.font.outfit_medium)),
                        color = Color.Black
                    )

                    Spacer(Modifier.width(8.dp))

                    val interactionSource = remember { MutableInteractionSource() }

                    OutlinedButton(
                        onClick = { onClickFollowing() },
                        modifier = Modifier
                            .height(24.dp)
                            .padding(horizontal = 10.dp),
                        shape = RoundedCornerShape(6.dp),
                        border = if (postItem.iAmFollowing) BorderStroke(1.dp, PrimaryColor) else null,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (postItem.iAmFollowing) Color.White else PrimaryColor,
                            contentColor = if (postItem.iAmFollowing) PrimaryColor else Color.White
                        ),
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 0.dp),
                        interactionSource = interactionSource
                    ) {
                        Text(
                            text = if (postItem.iAmFollowing) "Following" else "Follow",
                            fontFamily = FontFamily(Font(R.font.outfit_regular)),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Normal
                        )
                    }
                }
                Text(
                    text = postItem.time,
                    fontSize = 12.sp,
                    color = TextGrey,
                    fontFamily = FontFamily(Font(R.font.outfit_regular)),
                )
            }



            PostOptionsMenu (modifier = Modifier, onReportClick = onReportClick)
        }

        // Title & Details
        Column(modifier = Modifier.padding(horizontal = 12.dp)) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                // LEFT: Title
                Text(
                    text = postItem.eventTitle,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.outfit_medium)),
                    color = Color.Black,
                    modifier = Modifier.weight(1f)
                )

                // RIGHT: START DATE (FIXED WIDTH)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.width(180.dp)   // FIX: same width for both top & bottom
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.cal_ic),
                        contentDescription = "Calendar",
                        tint = Color.Unspecified
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = postItem.eventStartDate ?: "",
                        fontSize = 14.sp,
                        color = Color.Black,
                        fontFamily = FontFamily(Font(R.font.outfit_regular))
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ) {


                Text(
                    text = postItem.eventDescription ?: "No Description",
                    fontSize = 14.sp,
                    color = TextGrey,
                    maxLines = 5,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 18.sp,
                    fontFamily = FontFamily(Font(R.font.outfit_regular)),
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(8.dp))

                // RIGHT: END DATE (SAME FIXED WIDTH)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.width(180.dp)   // FIXED → matches the first one
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.cal_ic),
                        contentDescription = "Calendar",
                        tint = Color.Unspecified
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = postItem.eventEndDate ?: "",
                        fontSize = 14.sp,
                        color = Color.Black,
                        fontFamily = FontFamily(Font(R.font.outfit_regular))
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(R.drawable.location_ic_icon),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = Modifier.padding(top = 4.dp).align(Alignment.Top)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    postItem.location ?: "Undisclosed",
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.outfit_medium)),
                    color = Color.Black
                )
            }
        }


        Spacer(modifier = Modifier.height(12.dp))

        PostImage(mediaList = postItem.mediaList)

        // CTA Button
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Black)
                .padding(horizontal = 12.dp, vertical = 8.dp)
                .clickable { onJoinedCommunity() },
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
        Spacer(modifier = Modifier.height(10.dp))

        // Actions
        CommunityPostLikes(
            likes = postItem.likes, comments = postItem.comments,
            shares = postItem.shares, onShareClick = { onShareClick() },
            onLikeClick = { onLikeClick() },
            isLike = postItem.isLiked,
            isSaved = postItem.isSaved,
            onSaveClick = { },
            onClickInterested = { onInterested() }
        )

        Spacer(modifier = Modifier.height(10.dp))
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
fun HashtagSection(
    hashtags: List<HashtagItem>,
    onHashtagClick: (String) -> Unit
) {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        items(hashtags) { tag ->
            HashtagChip(
                tag = tag,
                onHashClick = {
                    onHashtagClick(tag.hashtag)
                }
            )
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
