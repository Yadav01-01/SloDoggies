package com.bussiness.slodoggiesapp.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.bussiness.slodoggiesapp.model.main.VerificationType
import com.bussiness.slodoggiesapp.ui.intro.*
import com.bussiness.slodoggiesapp.ui.screens.businessprovider.registration.AddServiceScreen
import com.bussiness.slodoggiesapp.ui.screens.businessprovider.registration.BusinessRegistrationScreen
import com.bussiness.slodoggiesapp.ui.screens.commonscreens.authFlow.EmailLoginScreen
import com.bussiness.slodoggiesapp.ui.screens.commonscreens.authFlow.PhoneAuthScreen
import com.bussiness.slodoggiesapp.ui.screens.commonscreens.authFlow.VerifyOTPScreen
import com.bussiness.slodoggiesapp.ui.screens.commonscreens.main.JoinThePackScreen
import com.bussiness.slodoggiesapp.ui.screens.commonscreens.main.MainScreen
import com.bussiness.slodoggiesapp.ui.screens.petowner.*
import com.bussiness.slodoggiesapp.ui.screens.petowner.serviceProviderDetailsScreen.ServiceProviderDetailsScreen

@Composable
fun NavGraph(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding() // Handles system navigation bar
    ) {
        NavHost(
            navController = navController,
            startDestination = Routes.SPLASH
        ) {

            // --- Intro / Auth Screens ---
            composable(Routes.SPLASH) {
                SplashScreen(onNavigateToNext = {
                    navController.navigate(Routes.ONBOARDING) {
                        popUpTo(Routes.SPLASH) { inclusive = true }
                    }
                })
            }

            composable(Routes.ONBOARDING) {
                OnboardingScreen(navController, onFinish = { /* TODO: handle finish */ })
            }

            composable(Routes.JOIN_THE_PACK) { JoinThePackScreen(navController) }
            composable(Routes.PHONE_AUTH_SCREEN) { PhoneAuthScreen(navController) }
            composable(Routes.EMAIL_AUTH_SCREEN) { EmailLoginScreen(navController) }

            composable(
                route = "${Routes.VERIFY_OTP}/{type}",
                arguments = listOf(navArgument("type") { type = NavType.StringType })
            ) { backStackEntry ->
                val type = backStackEntry.arguments?.getString("type") ?: "phone"
                VerifyOTPScreen(navController, type = VerificationType.valueOf(type.uppercase()))
            }

            // --- Business Provider Screens ---
            composable(Routes.BUSINESS_REGISTRATION) { BusinessRegistrationScreen(navController) }
            composable(Routes.ADD_SERVICE) { AddServiceScreen(navController) }
            composable(Routes.MAIN_SCREEN) { MainScreen(navController) }

            // --- Pet Owner Screens ---
            composable(Routes.NOTIFICATION_PERMISSION_SCREEN) { NotificationPermissionScreen(navController) }
            composable(Routes.LOCATION_PERMISSION_SCREEN) { LocationPermissionScreen(navController) }
            composable(Routes.PET_MAIN_SCREEN) { PetMainScreen(navController) }
            composable(Routes.PET_HOME_SCREEN) { PetHomeScreen(navController) }
            composable(Routes.PET_SERVICES_SCREEN) { PetServicesScreen(navController) }
            composable(Routes.SERVICE_PROVIDER_DETAILS) { ServiceProviderDetailsScreen(navController) }
        }
    }
}
