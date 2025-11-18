package com.bussiness.slodoggiesapp.ui.screens.commonscreens.permissionScreen

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.navigation.Routes
import com.bussiness.slodoggiesapp.ui.component.petOwner.CommonBlueButton
import com.bussiness.slodoggiesapp.ui.component.petOwner.CommonWhiteButton


@Composable
fun NotificationPermissionScreen(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            //  Bell with ripple effect
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.size(160.dp) // parent container
            ) {
                // Ripple behind
                RippleAnimation(icon = R.drawable.notification_icc)

            }


            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Turn on Notifications",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontFamily = FontFamily(Font(R.font.outfit_semibold)),
                    color = Color.Black
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Please enable notifications to receive\nupdates and reminders.",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontFamily = FontFamily(Font(R.font.outfit_regular)),
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    lineHeight = 20.sp
                )
            )

            Spacer(modifier = Modifier.height(32.dp))

            CommonBlueButton(
                text = "Turn On",
                fontSize = 18.sp,
                onClick = { navController.navigate(Routes.LOCATION_PERMISSION_SCREEN) }
            )

            Spacer(modifier = Modifier.height(16.dp))

            CommonWhiteButton(
                text = "NOT NOW",
                onClick = { navController.navigate(Routes.LOCATION_PERMISSION_SCREEN) }
            )
        }

        // Paw image at bottom right
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 56.dp, end = 10.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_paws),
                contentDescription = "Paw Icon",
                modifier = Modifier
                    .padding(16.dp)
                    .size(100.dp)
            )
        }
    }
}





@Preview(showBackground = true)
@Composable
fun NotificationPermissionScreenPreview() {
    val navController = rememberNavController()
    NotificationPermissionScreen(navController = navController)  // Updated parameter name
}