package com.bussiness.slodoggiesapp.ui.screens.businessprovider.services.content

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.model.businessProvider.RatingSummaryData
import com.bussiness.slodoggiesapp.model.businessProvider.Review
import com.bussiness.slodoggiesapp.model.businessProvider.ReviewReply
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor
import com.bussiness.slodoggiesapp.ui.theme.TextGrey

@Composable
fun ReviewContent(reviewList: List<Review>,onClickReply: () -> Unit) {
    val ratingData = RatingSummaryData(
        overallRating = 4.0f,
        totalReviews = 52,
        ratingPercentages = mapOf(
            5 to 0.8f,
            4 to 0.6f,
            3 to 0.3f,
            2 to 0.1f,
            1 to 0.05f
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

        items(reviewList) { review ->
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
        Column(modifier = Modifier.weight(1f)) {
            for (rating in 5 downTo 1) {
                RatingBar(
                    rating = rating,
                    percentage = ratingData.ratingPercentages[rating] ?: 0f
                )
                if (rating > 1) Spacer(modifier = Modifier.height(4.dp))
            }
        }

        Spacer(modifier = Modifier.width(24.dp))

        // Overall Rating
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
fun RatingBar(rating: Int, percentage: Float) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "$rating",
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(R.font.outfit_medium)),
            color = Color.Black,
            modifier = Modifier.width(16.dp)
        )

        Icon(
            painter = painterResource(id = R.drawable.ic_round_star),
            contentDescription = null,
            tint = Color.Unspecified,
            modifier = Modifier.size(20.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Box(
            modifier = Modifier
                .height(8.dp)
                .width(120.dp)
                .background(Color(0xFFF8F9FA), RoundedCornerShape(4.dp))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(percentage)
                    .background(Color(0xFF258694), RoundedCornerShape(4.dp))
            )
        }
    }
}



@Composable
fun ReviewItemPanel(
    data : Review ,
    onClickMore: () -> Unit,
    onClickReply: () -> Unit
) {
    Column {
        Row(modifier = Modifier.fillMaxWidth()) {
            AsyncImage(
                model = data.avatar,
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
                        text = data.name,
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
                            text = data.timeAgo,
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
            text = data.review,
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

        data.reply?.let { ReviewReplyItem(reply = it) }

        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            color = Color(0xFFE6EFF2),
            thickness = 1.dp
        )
    }
}


@Composable
fun ReviewReplyItem(
    reply: ReviewReply,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        // User Image
        AsyncImage(
            model = reply.providerImage,
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
                    text = reply.providerName,
                    fontFamily = FontFamily(Font(R.font.outfit_medium)),
                    fontWeight = FontWeight.Medium,
                    fontSize = 13.sp,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.width(10.dp))

                Text(
                    text = reply.pTimeAgo,
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
                    text = reply.providerRole,
                    fontSize = 9.sp,
                    fontFamily = FontFamily(Font(R.font.outfit_medium)),
                    fontWeight = FontWeight.Medium,
                    color = PrimaryColor
                )
            }

            Spacer(Modifier.height(10.dp))

            Text(
                text = reply.pMessage,
                fontSize = 12.sp,
                fontFamily = FontFamily(Font(R.font.outfit_regular)),
                fontWeight = FontWeight.Normal,
                color = PrimaryColor
            )
        }
    }
}


