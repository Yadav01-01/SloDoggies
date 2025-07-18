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
import com.bussiness.slodoggiesapp.ui.intro.OnboardingScreen
import com.bussiness.slodoggiesapp.ui.screens.businessprovider.AddServiceScreen
import com.bussiness.slodoggiesapp.ui.screens.businessprovider.BusinessRegistrationScreen
import com.bussiness.slodoggiesapp.ui.screens.commonscreens.MainScreen
import com.bussiness.slodoggiesapp.ui.screens.commonscreens.PhoneAuthScreen
import com.bussiness.slodoggiesapp.ui.screens.commonscreens.VerifyOTPScreen
import com.bussiness.slodoggiesapp.ui.screens.petowner.LocationPermissionScreen
import com.bussiness.slodoggiesapp.ui.screens.petowner.NotificationsScreen
import com.bussiness.slodoggiesapp.ui.screens.petowner.PetMainScreen

@Composable
fun NavGraph(navController: NavHostController) {

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
        composable(Routes.BUSINESS_REGISTRATION){ BusinessRegistrationScreen(navController) }
        composable(Routes.ADD_SERVICE){ AddServiceScreen(navController) }
        composable(Routes.MAIN_SCREEN){ MainScreen(navController) }
        composable(
            route = "${Routes.VERIFY_OTP}/{type}",
            arguments = listOf(navArgument("type") { type = NavType.StringType })
        ) {
            val type = it.arguments?.getString("type") ?: "phone"
            VerifyOTPScreen(navController, type = VerificationType.valueOf(type.uppercase()))
        }

        //vipin petowner
        composable(Routes.NOTIFICATION_ALERT) { NotificationsScreen(navController) }
        composable(Routes.LOCATION_ALERT) { LocationPermissionScreen(navController) }
        composable(Routes.PET_MAIN_SCREEN) { PetMainScreen(navController) }


    }
}



