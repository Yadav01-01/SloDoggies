package com.bussiness.slodoggiesapp.ui.screens.businessprovider.services.content

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.data.model.businessProvider.RatingSummaryData
import com.bussiness.slodoggiesapp.data.model.businessProvider.Review
import com.bussiness.slodoggiesapp.data.model.businessProvider.ReviewReply
import com.bussiness.slodoggiesapp.data.newModel.ownerService.RatingsAndReviews
import com.bussiness.slodoggiesapp.data.newModel.ownerService.ReviewItem
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor
import com.bussiness.slodoggiesapp.ui.theme.TextGrey

@Composable
fun ReviewContent(ratingsAndReviews: RatingsAndReviews?, onClickReply: () -> Unit) {
//    val ratingData = RatingSummaryData(
//        overallRating = 4.0f,
//        totalReviews = 52,
//        ratingPercentages = mapOf(
//            5 to 0.8f,
//            4 to 0.6f,
//            3 to 0.3f,
//            2 to 0.1f,
//            1 to 0.05f
//        ),
//        reviewCounts = mapOf(
//            5 to 10,
//            4 to 500,
//            3 to 28,
//            2 to 18,
//            1 to 15
//        )
//    )

    val ratingData = com.bussiness.slodoggiesapp.data.model.businessProvider.RatingSummaryData(
        overallRating = ratingsAndReviews?.averageRating?.toFloat()?:0.0f,
        totalReviews = ratingsAndReviews?.totalReviews?:0,
        ratingPercentages = mapOf(
            5 to (
                    ((ratingsAndReviews?.ratingDistribution?.star5 ?: 0).toFloat()
                            / (ratingsAndReviews?.totalReviews ?: 1)) * 100
                    ),
            4 to (
                    ((ratingsAndReviews?.ratingDistribution?.star4 ?: 0).toFloat()
                            / (ratingsAndReviews?.totalReviews ?: 1)) * 100
                    ),
            3 to (
                    ((ratingsAndReviews?.ratingDistribution?.star3 ?: 0).toFloat()
                            / (ratingsAndReviews?.totalReviews ?: 1)) * 100
                    ),
            2 to (
                    ((ratingsAndReviews?.ratingDistribution?.star2 ?: 0).toFloat()
                            / (ratingsAndReviews?.totalReviews ?: 1)) * 100
                    ),
            1 to (
                    ((ratingsAndReviews?.ratingDistribution?.star1 ?: 0).toFloat()
                            / (ratingsAndReviews?.totalReviews ?: 1)) * 100
                    )
        ),
        reviewCounts = mapOf(
            5 to (ratingsAndReviews?.ratingDistribution?.star5 ?: 0),
            4 to (ratingsAndReviews?.ratingDistribution?.star4 ?: 0),
            3 to (ratingsAndReviews?.ratingDistribution?.star3 ?: 0),
            2 to (ratingsAndReviews?.ratingDistribution?.star2 ?: 0),
            1 to (ratingsAndReviews?.ratingDistribution?.star1 ?: 0)
        )
    )



    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(vertical = 10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            RatingSummary(ratingData)
        }

        items(ratingsAndReviews?.reviews ?: mutableListOf<ReviewItem>()) { review ->
            ReviewItemPanel(
                data = review,
                onClickMore = {},
                onClickReply = { onClickReply() }
            )
        }
    }
}


@Composable
fun RatingSummary(ratingData: RatingSummaryData) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = Color(0xFFE6EFF2),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        // Rating Bars
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            for (rating in 5 downTo 1) {
                val percentage = ratingData.ratingPercentages[rating] ?: 0f
                val reviewCount = ratingData.reviewCounts[rating] ?: 0

                RatingBar(
                    rating = rating,
                    percentage = percentage,
                    reviewNumbers = reviewCount.toString()
                )
            }
        }


        Spacer(modifier = Modifier.width(24.dp))


        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = String.format("%.1f", ratingData.overallRating),
                fontSize = 48.sp,
                fontFamily = FontFamily(Font(R.font.outfit_bold)),
                color = Color.Black
            )

            // Star Rating Display
            Row {
                val fullStars = ratingData.overallRating.toInt()
                val hasHalfStar = ratingData.overallRating % 1 >= 0.5

                repeat(fullStars) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_round_star),
                        contentDescription = null,
                        tint = Color.Unspecified,
                        modifier = Modifier.size(20.dp)
                    )
                }
                if (hasHalfStar) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_round_star),
                        contentDescription = null,
                        tint = Color.Unspecified,
                        modifier = Modifier.size(20.dp)
                    )
                }
                repeat(5 - fullStars - if (hasHalfStar) 1 else 0) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_round_star),
                        contentDescription = null,
                        tint = Color(0xFFDDDDDD),
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "${ratingData.totalReviews} Reviews",
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.outfit_semibold)),
                color = Color.Black
            )
        }
    }
}


@Composable
fun RatingBar(
    rating: Int,
    percentage: Float,
    reviewNumbers: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        // Rating Number (1–5)
        Text(
            text = rating.toString(),
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(R.font.outfit_medium)),
            color = Color.Black
        )

        // Star Icon
        Icon(
            painter = painterResource(id = R.drawable.ic_round_star),
            contentDescription = null,
            tint = Color.Unspecified,
            modifier = Modifier.size(18.dp)
        )

        // Progress Bar
        Box(
            modifier = Modifier
                .height(8.dp)
                .weight(1f)
                .background(Color(0xFFF1F3F4), RoundedCornerShape(4.dp))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(percentage.coerceIn(0f, 1f))
                    .background(Color(0xFF258694), RoundedCornerShape(4.dp))
            )
        }

        // Review count text
        Text(
            text = "$reviewNumbers",
            fontSize = 12.sp,
            fontFamily = FontFamily(Font(R.font.outfit_regular)),
            color = Color.Gray,
            textAlign = TextAlign.Start,
            modifier = Modifier.widthIn(min = 32.dp)
        )
    }
}




@Composable
fun ReviewItemPanel(
    data : ReviewItem,
    onClickMore: () -> Unit,
    onClickReply: () -> Unit
) {
    Column {
        Row(modifier = Modifier.fillMaxWidth()) {
            AsyncImage(
                model = data.reviewerImage,
                contentDescription = null,
                placeholder = painterResource(id = R.drawable.ic_person_icon),
                error = painterResource(id = R.drawable.ic_person_icon),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(38.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        text = data.user?.name?:"",
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.outfit_medium)),
                        color = Color.Black
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        // Star Rating
                        repeat(data.rating) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_round_star),
                                contentDescription = null,
                                tint = Color(0xFFFFA726),
                                modifier = Modifier.size(16.dp)
                            )
                        }
                        repeat(5 - data.rating) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_round_star),
                                contentDescription = null,
                                tint = Color(0xFFDDDDDD),
                                modifier = Modifier.size(16.dp)
                            )
                        }

                        Spacer(Modifier.width(10.dp))

                        Text(
                            text = data.timeAgo?:"",
                            fontSize = 14.sp,
                            fontFamily = FontFamily(Font(R.font.outfit_regular)),
                            color = Color(0xFF949494)
                        )
                    }

                    // MoreVert Icon
                    Icon(
                        imageVector = androidx.compose.material.icons.Icons.Default.MoreVert,
                        contentDescription = "More options",
                        tint = Color.Black,
                        modifier = Modifier.size(24.dp).clickable { onClickMore() })
                }
            }
        }

        Spacer(Modifier.height(8.dp))

        Text(
            text = data.comment?:"",
            fontSize = 13.sp,
            fontFamily = FontFamily(Font(R.font.outfit_regular)),
            color = Color.Black,
            lineHeight = 18.sp
        )

        Spacer(Modifier.height(8.dp))

        Text(
            text = stringResource(R.string.reply),
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(R.font.outfit_regular)),
            color = PrimaryColor,
            modifier = Modifier.clickable { onClickReply() })

//        data.replies?.let {
//
//
//            ReviewReplyItem(reply = it)
//
//        }
//        LazyColumn {
//            items(
//                items = data.replies ?: emptyList(),
//                key = { it.replyId }
//            ) { reply ->
//                ReviewReplyItem(reply = reply)
//                Divider(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(vertical = 16.dp),
//                    color = Color(0xFFE6EFF2),
//                    thickness = 1.dp
//                )
//            }
//        }

//        Divider(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(vertical = 16.dp),
//            color = Color(0xFFE6EFF2),
//            thickness = 1.dp
//        )
    }
}


@Composable
fun ReviewReplyItem(
  //  reply: com.bussiness.slodoggiesapp.data.model.businessProvider.ReviewReply,
   reply: com.bussiness.slodoggiesapp.data.newModel.ownerService.ReviewReply,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        // User Image
        AsyncImage(
            model = reply.user.profileImage,
            placeholder = painterResource(R.drawable.ic_person_icon),
            error = painterResource(R.drawable.ic_person_icon),
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column(modifier = Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = reply.user.name,
                    fontFamily = FontFamily(Font(R.font.outfit_medium)),
                    fontWeight = FontWeight.Medium,
                    fontSize = 13.sp,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.width(10.dp))

                Text(
                    text = reply.timeAgo,
                    fontSize = 12.sp,
                    color = TextGrey,
                    fontFamily = FontFamily(Font(R.font.outfit_regular)),
                    fontWeight = FontWeight.Normal,
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Box(
                modifier = Modifier
                    .background(Color(0xFFE5EFF2), RoundedCornerShape(12.dp))
                    .padding(horizontal = 6.dp, vertical = 2.dp)
            ) {
                Text(
                //    text = reply.providerRole,
                    text = "",
                    fontSize = 9.sp,
                    fontFamily = FontFamily(Font(R.font.outfit_medium)),
                    fontWeight = FontWeight.Medium,
                    color = PrimaryColor
                )
            }

            Spacer(Modifier.height(10.dp))

            Text(
                text = reply.comment,
                fontSize = 12.sp,
                fontFamily = FontFamily(Font(R.font.outfit_regular)),
                fontWeight = FontWeight.Normal,
                color = PrimaryColor
            )
        }
    }
}


