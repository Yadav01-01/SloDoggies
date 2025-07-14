package com.bussiness.slodoggiesapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.bussiness.slodoggiesapp.model.VerificationType
import com.bussiness.slodoggiesapp.ui.intro.SplashScreen
import com.bussiness.slodoggiesapp.ui.screens.commonscreens.EmailLoginScreen
import com.bussiness.slodoggiesapp.ui.screens.commonscreens.JoinThePackScreen
import com.bussiness.slodoggiesapp.ui.screens.commonscreens.OnboardingScreen
import com.bussiness.slodoggiesapp.ui.screens.commonscreens.PhoneAuthScreen
import com.bussiness.slodoggiesapp.ui.screens.commonscreens.VerifyOTPScreen

@Composable
fun NavGraph(navController: NavHostController, authNavController: NavHostController) {
    // Use both as needed
    NavHost(navController = navController, startDestination = Routes.SPLASH) {
        composable(Routes.SPLASH) {
            SplashScreen(onNavigateToNext = { navController.navigate(Routes.ONBOARDING) {
                        popUpTo(Routes.SPLASH) { inclusive = true }
                    }
                }
            )
        }
        composable(Routes.ONBOARDING) { OnboardingScreen(navController, onFinish = {}) }
        composable(Routes.JOIN_THE_PACK) { JoinThePackScreen(navController) }
        composable(Routes.PHONE_AUTH_SCREEN) { PhoneAuthScreen(navController) }
        composable(Routes.EMAIL_AUTH_SCREEN) { EmailLoginScreen(navController) }
        composable(
            route = "${Routes.VERIFY_OTP}/{type}",
            arguments = listOf(navArgument("type") { type = NavType.StringType })
        ) {
            val type = it.arguments?.getString("type") ?: "phone"
            VerifyOTPScreen(navController, type = VerificationType.valueOf(type.uppercase()))
        }


    }
}



