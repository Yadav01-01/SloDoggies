package com.bussiness.slodoggiesapp.ui.screens.petowner.service.serviceProviderDetailsScreen

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bussiness.slodoggiesapp.R


@Composable
fun ReviewInterface() {
    var reviewText by remember { mutableStateOf("") }
    var selectedRating by remember { mutableStateOf(0) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(vertical = 10.dp)
            .imePadding(),
        verticalArrangement = Arrangement.spacedBy(16.dp) // consistent spacing
    ) {
        // Rating Summary Section
        item {
            RatingSummary()
        }

        item {
            Spacer(modifier = Modifier.height(8.dp))
            ReviewsList()
        }

        // Rate & Review Section
        item {
            Text(
                text = "Rate & Review",
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.outfit_medium)),
                color = Color.Black,
            )
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
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

        // Review Text Field
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
                onClick = { /* Handle submit */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
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
fun RatingSummary() {
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
                RatingBar(
                    rating = rating,
                    percentage = when (rating) {
                        5 -> 0.8f
                        4 -> 0.6f
                        3 -> 0.3f
                        2 -> 0.1f
                        1 -> 0.05f
                        else -> 0f
                    }
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
                text = "4.0",
                fontSize = 48.sp,
                fontFamily = FontFamily(Font(R.font.outfit_bold)),
                color = Color.Black
            )

            // Star Rating Display
            Row {
                repeat(4) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_round_star),
                        // imageVector = Icons.Filled.Star,
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
                text = "52 Reviews",
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
    }
}

@Composable
fun ReviewsList() {
    Column {
        ReviewItem(
            name = "Courtney Henry",
            rating = 5,
            timeAgo = "2 mins ago",
            review = "Consequat velit qui adipisicing sunt do rependerit ad laborum tempor ullamco exercitation. Ullamco tempor adipisicing et voluptate duis sit commodo aliqua",
            avatarColor = Color(0xFF4A9B8E)
        )

        Spacer(modifier = Modifier.height(16.dp))

        ReviewItem(
            name = "Cameron Williamson",
            rating = 4,
            timeAgo = "2 mins ago",
            review = "Consequat velit qui adipisicing sunt do rependerit ad laborum tempor ullamco.",
            avatarColor = Color(0xFFE8B4A0)
        )
    }
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
                //  tint = Color(0xFFFF8C00),
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


                    // Star Rating
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
//                    .size(if (i <= rating) 32.dp else 25.dp)
                    .size(25.dp)
                    .clickable { onRatingChanged(i) }
                    .padding(2.dp))
        }
    }
}


//@Preview(showBackground = true)
//@Composable
//fun ReviewInterfacePreview() {
//    ReviewInterface(serviceDetail.ratingReviews)
//
//}