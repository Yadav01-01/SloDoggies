package com.bussiness.slodoggiesapp.ui.component.petOwner.Dialog

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.bussiness.slodoggiesapp.R

@Composable
fun WelDialog(
    onDismiss: () -> Unit = {},
    onSubmitClick: () -> Unit = {},
    icon : Int,
    title: String,
    description: String,
    button: String
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = false
        )
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            // Main card content
            Image(
                painter = painterResource(id = R.drawable.ic_cross_iconx),
                contentDescription = "Close",

                modifier = Modifier
                    .clickable {
                        onDismiss()
                    }
                    .align(Alignment.TopEnd)
                    .wrapContentSize()
                    .clip(CircleShape)
                    .padding(bottom = 8.dp)
                    .align(Alignment.TopStart)
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp, top = 40.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                // Main content
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Icon (you can replace with your actual icon resource)


                    Image(
                        painter = painterResource(id = icon),
                        contentDescription = "Close",
                        modifier = Modifier
                            .size(55.dp)

                    )

                    // Title
                    Text(
                        text = title,
                        fontFamily = FontFamily(Font(R.font.outfit_medium)),
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center,
                        color = Color.Black
                    )

                    // Description
                    Text(
                        text = description,
                        fontSize = 15.sp,
                        textAlign = TextAlign.Center,
                        fontFamily = FontFamily(Font(R.font.outfit_regular)),
                        color = Color(0xFF000000),
                        lineHeight = 15.sp
                    )


                    // Get Started Button
                    Button(
                        onClick = onSubmitClick,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(42.dp),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF2E8B8B) // Teal color
                        )
                    ) {
                        Text(
                            text = button,
                            fontSize = 14.sp,
                            fontFamily = FontFamily(Font(R.font.outfit_medium)),
                            fontWeight = FontWeight.Medium,
                            color = Color.White
                        )
                    }
                }
            }

        }
    }
}


@Composable
fun WelcomeDialog(
    onDismiss: () -> Unit = {},
    onSubmitClick: () -> Unit = {},
    icon : Int,
    title: String,
    description: String,
    button: String
) {
    Dialog(onDismissRequest = onDismiss) {
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            contentAlignment = Alignment.Center
        ) {
            val maxWidthPx = maxWidth

            Box(
                modifier = Modifier
                    .widthIn(max = maxWidthPx * 0.99f) // 90% of screen width
                    .wrapContentHeight()
            ) {
                // Main Dialog Card
                Column(
                    modifier = Modifier
                        .background(Color.White, shape = RoundedCornerShape(12.dp))
                        .padding(horizontal = 10.dp, vertical = 10.dp)
                        .align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Image(
                        painter = painterResource(id = icon),
                        contentDescription = "Close",
                        modifier = Modifier
                            .size(55.dp)

                    )

                    Spacer(Modifier.height(16.dp))

                    Text(
                        text = title,
                        fontFamily = FontFamily(Font(R.font.outfit_medium)),
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center,
                        color = Color.Black
                    )

                    Spacer(Modifier.height(8.dp))

                    Text(
                        text = description,
                        fontSize = 15.sp,
                        textAlign = TextAlign.Center,
                        fontFamily = FontFamily(Font(R.font.outfit_regular)),
                        color = Color(0xFF000000),
                        lineHeight = 15.sp
                    )

                    Spacer(Modifier.height(10.dp))

                    Button(
                        onClick = onSubmitClick,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(42.dp),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF2E8B8B) // Teal color
                        )
                    ) {
                        Text(
                            text = button,
                            fontSize = 14.sp,
                            fontFamily = FontFamily(Font(R.font.outfit_medium)),
                            fontWeight = FontWeight.Medium,
                            color = Color.White
                        )
                    }
                }

                // External Cross (X) Button
                Icon(
                    painter = painterResource(R.drawable.ic_cross_iconx),
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



@Preview
@Composable
fun WelcomeDialogPreview() {
    MaterialTheme {
        WelcomeDialog(
            title = "Welcome to SloDoggies!",
            description = "We're excited you're here!  Rather than excited to have you. Thanks",
            button = "Get Started",
            icon = R.drawable.ic_party_popper_icon
        )
    }
}