package com.bussiness.slodoggiesapp.ui.screens.petowner.service.serviceProviderDetailsScreen

import android.util.Log
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
import androidx.compose.foundation.layout.imePadding
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
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.data.newModel.ownerService.RatingsAndReviews
import com.bussiness.slodoggiesapp.data.newModel.ownerService.ReviewItem
import com.bussiness.slodoggiesapp.ui.screens.businessprovider.services.content.ReviewItemPanel
import kotlin.math.roundToInt
import kotlin.math.roundToLong


@Composable
fun ReviewInterface(ratingsAndReviews: RatingsAndReviews?,
                    onClick : (rating:String, message:String) ->Unit) {

    var reviewText by remember { mutableStateOf("") }

    var selectedRating by remember { mutableStateOf(0) }

    val ratings = com.bussiness.slodoggiesapp.data.model.businessProvider.RatingSummaryData(
        overallRating = ratingsAndReviews?.averageRating?:0.0f,
        totalReviews = ratingsAndReviews?.totalReviews?:0,
        ratingPercentages = mapOf(
            5 to (
                    ((ratingsAndReviews?.ratingDistribution?.star5 ?: 0).toFloat() / (ratingsAndReviews?.totalReviews ?: 1)) * 100),
            4 to (((ratingsAndReviews?.ratingDistribution?.star4 ?: 0).toFloat() / (ratingsAndReviews?.totalReviews ?: 1)) * 100),
            3 to (((ratingsAndReviews?.ratingDistribution?.star3 ?: 0).toFloat() / (ratingsAndReviews?.totalReviews ?: 1)) * 100),
            2 to (((ratingsAndReviews?.ratingDistribution?.star2 ?: 0).toFloat() / (ratingsAndReviews?.totalReviews ?: 1)) * 100),
            1 to (((ratingsAndReviews?.ratingDistribution?.star1 ?: 0).toFloat() / (ratingsAndReviews?.totalReviews ?: 1)) * 100)
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
        modifier = Modifier.fillMaxSize().background(Color.White).padding(vertical = 10.dp).imePadding(),
        verticalArrangement = Arrangement.spacedBy(16.dp) // consistent spacing
    ) {
        // Rating Summary Section
        item {
            RatingSummary(ratings)
        }

//        item {
//            Spacer(modifier = Modifier.height(8.dp))
//         //   ReviewsList()
//
//            items(ratingsAndReviews?.reviews ?: mutableListOf<ReviewItem>()) { review ->
//                ReviewItemPanel(
//                    data = review,
//                    onClickMore = {},
//                    onClickReply = { onClickReply() }
//                )
//            }
//
//        }


            item {
                Spacer(modifier = Modifier.height(8.dp))
                // ReviewsList()
            }

            items(ratingsAndReviews?.reviews ?: emptyList()) { review ->
                ReviewItemPanel(
                    data = review,
                    onClickMore = {

                    },
                    onClickReply = {
                    //onClickReply()
                    }
                )
            }


        item {

            Text(
                text = "Rate & Review",
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.outfit_medium)),
                color = Color.Black,
            )

            Divider(
                modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
                color = Color(0xFFE9EAEB),
                thickness = 1.dp
            )

        }

        // Star Rating
        item {
            StarRating(
                rating = selectedRating,
                onRatingChanged = { selectedRating = it }
            )
        }

        item {
            OutlinedTextField(
                value = reviewText,
                onValueChange = { reviewText = it },
                placeholder = {
                    Text(
                        text = "Type Here.....",
                        fontFamily = FontFamily(Font(R.font.outfit_regular)),
                        color = Color(0xFF949494)
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(90.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF949494),
                    unfocusedBorderColor = Color(0xFF949494)
                ),
                shape = RoundedCornerShape(8.dp)
            )
        }

        // Submit Button
        item {
            Button(
                onClick = {

                    onClick(selectedRating.toString(),reviewText)
                          selectedRating=0
                          reviewText=""
                          },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                  colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF258694)
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "Submit",
                    fontSize = 15.sp,
                    fontFamily = FontFamily(Font(R.font.outfit_bold)),
                    color = Color.White
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

@Composable
fun RatingSummary(ratingData: com.bussiness.slodoggiesapp.data.model.businessProvider.RatingSummaryData) {
    Row(
        modifier = Modifier.fillMaxWidth().border(
            width = 1.dp,
            color = Color(0xFFE6EFF2),
            shape = RoundedCornerShape(8.dp)
        ).padding(10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        // Rating Bars
        Column(modifier = Modifier.weight(1f)) {
            for (rating in 5 downTo 1) {
                val percentage = ratingData.ratingPercentages[rating] ?: 0f
                val reviewCount = ratingData.reviewCounts[rating] ?: 0
                RatingBar(
                    rating = rating,
                    percentage = percentage,
                    reviewNumbers = reviewCount.toString()
                )
                if (rating > 1) Spacer(modifier = Modifier.height(4.dp))
            }
        }

        Spacer(modifier = Modifier.width(24.dp))

        // Overall Rating
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = ratingData.overallRating.roundToInt().toString(),
                fontSize = 48.sp,
                fontFamily = FontFamily(Font(R.font.outfit_bold)),
                color = Color.Black
            )

            // Star Rating Display
            Row {
                repeat(ratingData.overallRating.roundToInt()) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_round_star),
                        contentDescription = null,
                        tint = Color.Unspecified,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Icon(
                    painter = painterResource(id = R.drawable.ic_round_star),
                    contentDescription = null,
                    tint = Color(0xFFDDDDDD),
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = if(ratingData.totalReviews.toString().equals("0") || ratingData.totalReviews.toString().equals("1"))ratingData.totalReviews.toString() +" review" else ratingData.totalReviews.toString() +" reviews",
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.outfit_semibold)),
                color = Color.Black
            )
        }

    }
}

@Composable
fun RatingBar(rating: Int, percentage: Float,reviewNumbers : String) {
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
            modifier = Modifier.size(16.dp)
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
fun ReviewsList() {



//    Column {
//        ReviewItem(
//            name = "Courtney Henry",
//            rating = 5,
//            timeAgo = "2 mins ago",
//            review = "Consequat velit qui adipisicing sunt do rependerit ad laborum tempor ullamco exercitation. Ullamco tempor adipisicing et voluptate duis sit commodo aliqua",
//            avatarColor = Color(0xFF4A9B8E)
//        )
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        ReviewItem(
//            name = "Cameron Williamson",
//            rating = 4,
//            timeAgo = "2 mins ago",
//            review = "Consequat velit qui adipisicing sunt do rependerit ad laborum tempor ullamco.",
//            avatarColor = Color(0xFFE8B4A0)
//        )
//    }
}

@Composable
fun ReviewItem(
    name: String,
    rating: Int,
    timeAgo: String,
    review: String,
    avatarColor: Color
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            // Avatar
            Image(    painter = painterResource(id = R.drawable.dummy_baby_pic),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .size(38.dp) // Use size() instead of separate height/width for square dimensions
                    .background(
                        color = Color.Transparent, // Add color parameter
                        shape = CircleShape // Use CircleShape directly instead of RoundedCornerShape
                    )
                    .clip(CircleShape))

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = name,
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.outfit_medium)),
                        color = Color.Black
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Row {
                    Row {
                        repeat(rating) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_round_star),

                                // imageVector = Icons.Filled.Star,
                                contentDescription = null,
                                tint = Color(0xFFFFA726),
                                modifier = Modifier.size(16.dp)
                            )
                        }
                        repeat(5 - rating) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_round_star),

                                //  imageVector = Icons.Outlined.Star,
                                contentDescription = null,
                                tint = Color(0xFFDDDDDD),
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                    Spacer(Modifier.width(10.dp))
                    Text(
                        text = timeAgo,
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.outfit_regular)),
                        color = Color(0xFF949494)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        Text(
            text = review,
            fontSize = 12.sp,
            fontFamily = FontFamily(Font(R.font.outfit_regular)),
            color = Color.Black,
            lineHeight = 20.sp
        )

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
fun StarRating(
    rating: Int,
    onRatingChanged: (Int) -> Unit
) {
    Row {
        for (i in 1..5) {
            Image(painter = painterResource(id = if (i <= rating)  R.drawable.filled_new_ic else R.drawable.ic_black_outlined_star ),
                contentDescription = "Rate $i stars",
                modifier = Modifier
                    .size(25.dp)
                    .clickable { onRatingChanged(i) }
                    .padding(2.dp))
        }
    }
}