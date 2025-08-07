package com.bussiness.slodoggiesapp.ui.component.petOwner.Dialog

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.ui.component.petOwner.CommonBlueButton
import com.bussiness.slodoggiesapp.ui.component.petOwner.CommonWhiteButton

@Composable
fun RemoveFollowerDialog(
    onDismiss: () -> Unit = {},
    onSubmitClick: () -> Unit = {},
    title: String,
    description: String
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
                    .size(40.dp)
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
                        painter = painterResource(id = R.drawable.ic_user_icon_x),
                        contentDescription = "Close",
                        modifier = Modifier
                            .size(70.dp)

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
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                        fontFamily = FontFamily(Font(R.font.outfit_regular)),
                        color = Color(0xFF000000),
                        lineHeight = 15.sp
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        // Skip Button

                        CommonWhiteButton(
                            text = "Cancel",
                            onClick = {
                                //  navController.navigate(Routes.LOCATION_ALERT)  // Also changed this for consistency
                            },
                            modifier = Modifier.weight(1f),
                        )
                        CommonBlueButton(
                            text = "Remove ",
                            fontSize = 20.sp,
                            onClick = {
                                //  navController.navigate(Routes.PET_MAIN_SCREEN)  // Now using navController instead of authNavController
                            },
                            modifier = Modifier.weight(1f),
                        )

                    }

                }
            }

        }
    }
}

@Preview
@Composable
fun RemoveFollowerDialogPreview() {
    MaterialTheme {
        RemoveFollowerDialog(
            title = "Remove Follower?",
            description = "We won’t tell Zain Dorwart they were removed from your followers.",
        )
    }
}