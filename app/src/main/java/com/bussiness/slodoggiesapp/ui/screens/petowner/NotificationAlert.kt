package com.bussiness.slodoggiesapp.ui.screens.petowner

//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.font.Font
//import androidx.compose.ui.text.font.FontFamily
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.navigation.NavHostController
//import androidx.navigation.compose.rememberNavController
//import com.bussiness.slodoggiesapp.R
//import com.bussiness.slodoggiesapp.navigation.Routes
//import com.bussiness.slodoggiesapp.ui.component.CommonBlueButton
//import com.bussiness.slodoggiesapp.ui.component.CommonWhiteButton
//
//@Composable
//fun NotificationPermissionScreen(
//    navController: NavHostController
//) {
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color.White)
//    ) {
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(horizontal = 24.dp),
//            verticalArrangement = Arrangement.Center,
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            // Bell Icon
//            Image(
//                painter = painterResource(id = R.drawable.notification_icon),
//                contentDescription = "Notification Icon",
//                modifier = Modifier.size(120.dp)
//            )
//
//            Spacer(modifier = Modifier.height(32.dp))
//
//            Text(
//                text = "Turn on Notifications",
//                style = MaterialTheme.typography.titleLarge.copy(
//                    fontFamily = FontFamily(Font(R.font.outfit_semibold)),
//                    color = Color.Black
//                )
//            )
//
//            Spacer(modifier = Modifier.height(8.dp))
//
//            Text(
//                text = "Please enable notifications to receive\nupdates and reminders",
//                style = MaterialTheme.typography.bodyMedium.copy(
//                    fontFamily = FontFamily(Font(R.font.outfit_regular)),
//                    color = Color.Black,
//                    textAlign = TextAlign.Center,
//                    lineHeight = 20.sp
//                )
//            )
//
//            Spacer(modifier = Modifier.height(32.dp))
//
//            CommonBlueButton(
//                text = "Turn On",
//                fontSize = 22.sp,
//                onClick = {
//                    navController.navigate(Routes.PetMainScreen)
//                }
//            )
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            CommonWhiteButton(
//                text = "NOT NOW",
//                onClick = {
//                    navController.navigate(Routes.LocationAlert)
//                }
//            )
//        }
//
//        // Paw image at bottom right
//        Box(
//            modifier = Modifier
//                .align(Alignment.BottomEnd)
//                .padding(bottom = 56.dp, end = 10.dp) // This creates the space below the paw
//        ) {
//            Image(
//                painter = painterResource(id = R.drawable.ic_paws),
//                contentDescription = "Paw Icon",
//                modifier = Modifier
//                    .padding(16.dp)
//                    .size(100.dp)
//            )
//        }
//    }
//}
//
//
//@Preview(showBackground = true)
//@Composable
//fun NotificationPermissionScreenPreview() {
//    val navController = rememberNavController()
//    NotificationPermissionScreen(navController = navController)
//}




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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.bussiness.slodoggiesapp.ui.component.CommonBlueButton
import com.bussiness.slodoggiesapp.ui.component.CommonWhiteButton

@Composable
fun NotificationPermissionScreen(
    navController: NavHostController  // Changed parameter name from authNavController to navController
) {
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
            // Bell Icon
            Image(
                painter = painterResource(id = R.drawable.notification_icon),
                contentDescription = "Notification Icon",
                modifier = Modifier.size(120.dp)
            )

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
                text = "Please enable notifications to receive\nupdates and reminders",
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
                fontSize = 22.sp,
                onClick = {
                    navController.navigate(Routes.PetMainScreen)  // Now using navController instead of authNavController
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            CommonWhiteButton(
                text = "NOT NOW",
                onClick = {
                    navController.navigate(Routes.LocationAlert)  // Also changed this for consistency
                }
            )
        }

        // Paw image at bottom right
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 56.dp, end = 10.dp) // This creates the space below the paw
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