package com.bussiness.slodoggiesapp.ui.screens.commonscreens.permissionScreen

import android.Manifest
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.DrawableRes
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
import androidx.compose.ui.platform.LocalContext
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
fun LocationPermissionScreen(navController: NavHostController) {

    val context = LocalContext.current

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val fineGranted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: false
        val coarseGranted = permissions[Manifest.permission.ACCESS_COARSE_LOCATION] ?: false

        if (fineGranted || coarseGranted) {
            navController.navigate(Routes.MAIN_SCREEN) {
                popUpTo(Routes.LOGIN_SCREEN) { inclusive = true }
                launchSingleTop = true
            }
        } else {
            Toast.makeText(context, "Location Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }


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
            RippleAnimation(icon = R.drawable.location_iccv)

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Turn on Location",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontFamily = FontFamily(Font(R.font.outfit_semibold)),
                    color = Color.Black
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Allow maps to access your\n" +
                        "location while you use the app?",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontFamily = FontFamily(Font(R.font.outfit_regular)),
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    lineHeight = 20.sp
                )
            )

            Spacer(modifier = Modifier.height(32.dp))

            CommonBlueButton(text = "Turn On", fontSize = 18.sp, onClick = {

                permissionLauncher.launch(
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                )

            })

            Spacer(modifier = Modifier.height(16.dp))

            CommonWhiteButton(text = "NOT NOW", onClick = {
                navController.navigate(Routes.MAIN_SCREEN)
            })
        }

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


@Composable
fun RippleAnimation(@DrawableRes icon : Int) {
    val infiniteTransition = rememberInfiniteTransition()

    val scale by infiniteTransition.animateFloat(
        initialValue = 0.6f,
        targetValue = 1.4f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    // Ripple + Bell Icon
    Box(
        modifier = Modifier.size(160.dp), // fixed container
        contentAlignment = Alignment.Center
    ) {
        // Ripple expanding
        Box(
            modifier = Modifier
                .size(160.dp * scale)
                .background(Color(0xFFA8CFD4).copy(alpha = alpha), CircleShape)
        )

        // Bell Icon always in center
        Image(
            painter = painterResource(id = icon),
            contentDescription = "Notification Icon",
            modifier = Modifier.size(100.dp),
        )
    }
}


@Preview(showBackground = true)
@Composable
fun  LocationPreview(){
    val navController = rememberNavController()
    LocationPermissionScreen(navController)
}

