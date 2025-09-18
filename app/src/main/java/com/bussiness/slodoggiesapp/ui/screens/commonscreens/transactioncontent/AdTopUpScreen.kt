package com.bussiness.slodoggiesapp.ui.screens.commonscreens.transactioncontent

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor


@Composable
fun AdTopUpScreen(
    title: String,
    amount: String,
    id: String,
    date: String,
    statusText: String,
    onReceiptClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp, vertical = 8.dp),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Top row: title (left) and amount (right)
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black,
                    fontFamily = FontFamily(Font(R.font.outfit_semibold)),
                    modifier = Modifier.weight(1f)
                )

                Text(
                    text = amount,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black,
                    fontFamily = FontFamily(Font(R.font.outfit_semibold)),
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Middle row: ID + date (left) and status pill (right)
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "ID: $id",
                            fontSize = 12.sp,
                            color = Color.Black,
                            fontFamily = FontFamily(Font(R.font.outfit_regular)),
                            fontWeight = FontWeight.Normal,
                        )

                        Spacer(modifier = Modifier.width(12.dp))

                        Text(
                            text = date,
                            fontSize = 12.sp,
                            color = Color.Black,
                            fontFamily = FontFamily(Font(R.font.outfit_regular)),
                            fontWeight = FontWeight.Normal,
                        )
                    }
                }

                // Status pill
                val color = if (statusText == "Failed") Color(0xFFDD0B00) else Color(0xFF2ECC71)
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = color,
                    tonalElevation = 0.dp,
                    modifier = Modifier
                        .wrapContentWidth()
                        .height(25.dp)
                ) {
                    Box(
                        modifier = Modifier.padding(horizontal = 12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = statusText,
                            color = Color.White,
                            fontSize = 12.sp,
                            fontFamily = FontFamily(Font(R.font.outfit_regular)),
                            fontWeight = FontWeight.Normal,
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Receipt button — full width, rounded, with light border
            OutlinedButton(
                onClick = onReceiptClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp),
                shape = RoundedCornerShape(10.dp),
                border = BorderStroke(1.dp, Color(0xFFE5EFF2)),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = Color.Transparent,
                    contentColor = PrimaryColor
                ),
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {
                Text(
                    text = "Receipt",
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.outfit_medium)),
                    fontWeight = FontWeight.Medium,
                )
            }
        }
    }
}
