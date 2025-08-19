package com.bussiness.slodoggiesapp.ui.dialog

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.bussiness.slodoggiesapp.R

@Composable
fun ServiceAdEditDialog(
    onDismiss: () -> Unit,
    @DrawableRes iconResId: Int,
    text: String,
    description: String
) {

    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .wrapContentHeight()
            ) {
                val maxWidthPx = maxWidth

                Box(
                    modifier = Modifier
                        .widthIn(max = maxWidthPx)
                        .wrapContentHeight()
                ) {
                    // Main Dialog Card
                    Column(
                        modifier = Modifier
                            .background(Color.White, shape = RoundedCornerShape(12.dp))
                            .padding(horizontal = 10.dp, vertical = 18.dp)
                            .align(Alignment.Center)
                    ) {
                        Icon(
                            painter = painterResource(id = iconResId),
                            contentDescription = null,
                            tint = Color.Unspecified,
                            modifier = Modifier
                                .wrapContentSize()
                                .align(Alignment.CenterHorizontally)
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = text,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Medium,
                                fontFamily = FontFamily(Font(R.font.outfit_medium)),
                                fontSize = 16.sp,
                                textAlign = TextAlign.Center,
                                color = Color.Black
                            ),
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = description,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.Normal,
                                fontFamily = FontFamily(Font(R.font.outfit_regular)),
                                fontSize = 14.sp,
                                textAlign = TextAlign.Center,
                                color = Color.Black
                            ),
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                    }

                    Icon(
                        painter = painterResource(R.drawable.ic_cross_icon),
                        contentDescription = "Close",
                        tint = Color.Unspecified,
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .offset(x = 0.dp, y = (-50).dp)
                            .wrapContentSize()
                            .background(Color(0xFFF0F0F0), CircleShape)
                            .clickable { onDismiss() }
                    )
                }
            }
        }
    }
}