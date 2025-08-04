package com.bussiness.slodoggiesapp.ui.screens.businessprovider.services.content

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.model.Review
import com.bussiness.slodoggiesapp.ui.screens.petowner.serviceProviderDetailsScreen.RatingBar
import com.bussiness.slodoggiesapp.ui.screens.petowner.serviceProviderDetailsScreen.RatingSummary
import com.bussiness.slodoggiesapp.ui.screens.petowner.serviceProviderDetailsScreen.ReviewItem

//@Composable
//fun RatingAndReviews(
//    rating: Double,
//    reviewsCount: Int,
//    reviews: List<Review>
//) {
//    Column(modifier = Modifier.fillMaxSize()) {
//        RatingSummaryService(rating = rating, reviewsCount = reviewsCount, reviews = reviews)
//
//        Spacer(Modifier.height(10.dp))
//
//        LazyColumn(
//            modifier = Modifier.fillMaxSize()
//        ) {
//            items(reviews) { review ->
//                ReviewItem(
//                    profileImageUrl = review.profileImageUrl,
//                    userName = review.userName,
//                    rating = review.rating,
//                    timeAgo = review.timeAgo,
//                    reviewText = review.text,
//                    onReplyClick = { /* Handle reply click here */ }
//                )
//                Divider(thickness = 0.5.dp, color = Color.LightGray)
//            }
//        }
//    }
//}


@Composable
fun RatingSummaryService(
    averageRating: Double,
    totalReviews: Int,
    reviews: List<Review>
) {
    // Count ratings by stars
    val ratingDistribution = (1..5).associateWith { star ->
        reviews.count { it.rating.toInt() == star }
    }
    val maxCount = ratingDistribution.values.maxOrNull()?.coerceAtLeast(1)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color(0xFFE6EFF2), RoundedCornerShape(8.dp))
            .padding(10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        // Rating Bars
        Column(modifier = Modifier.weight(1f)) {
            for (rating in 5 downTo 1) {
                val count = ratingDistribution[rating] ?: 0
                val percentage = count.toFloat() / maxCount!!

                RatingBar(
                    rating = rating,
                    percentage = percentage
                )

                if (rating > 1) Spacer(modifier = Modifier.height(4.dp))
            }
        }

        Spacer(modifier = Modifier.width(24.dp))

        // Overall Rating Display
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = String.format("%.1f", averageRating),
                fontSize = 48.sp,
                fontFamily = FontFamily(Font(R.font.outfit_bold)),
                color = Color.Black
            )

            Row {
                val fullStars = averageRating.toInt()
                val hasHalfStar = averageRating - fullStars >= 0.5
                val emptyStars = 5 - fullStars - if (hasHalfStar) 1 else 0

                repeat(fullStars) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_round_star),
                        contentDescription = null,
                        tint = Color(0xFFFFA726),
                        modifier = Modifier.size(20.dp)
                    )
                }
                if (hasHalfStar) {
                    Icon(
                        painter = painterResource(id = R.drawable.star_ic),
                        contentDescription = null,
                        tint = Color(0xFFFFA726),
                        modifier = Modifier.size(20.dp)
                    )
                }
                repeat(emptyStars) {
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
                text = "$totalReviews Reviews",
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.outfit_semibold)),
                color = Color.Black
            )
        }
    }
}

@Preview
@Composable
fun PreviewFun(){
    RatingSummaryService(averageRating = 5.0, totalReviews = 10, reviews = emptyList())
}