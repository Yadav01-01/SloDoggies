package com.bussiness.slodoggiesapp.ui.intro

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.navigation.Routes
import com.bussiness.slodoggiesapp.util.SessionManager
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavHostController) {

    val context = LocalContext.current
    val sessionManager = SessionManager.getInstance(context)

    LaunchedEffect(true) {
        delay(3000)
        navigateToNext(navController,sessionManager)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
    ) {

        Image(painter = painterResource(R.drawable.new_logo),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop)
    }
}

private fun navigateToNext(navController: NavHostController, sessionManager: SessionManager) {
    if (sessionManager.isLoggedIn()){
        navController.navigate(Routes.MAIN_SCREEN)
    }else{
        navController.navigate(Routes.ONBOARDING) {
            popUpTo(Routes.SPLASH) { inclusive = true }
        }
    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SplashScreenPreview() {
    SplashScreen(navController = NavHostController(LocalContext.current))
}