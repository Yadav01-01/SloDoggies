package com.bussiness.slodoggiesapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bussiness.slodoggiesapp.ui.intro.SplashScreen
import com.bussiness.slodoggiesapp.ui.screens.petowner.LocationPermissionScreen
import com.bussiness.slodoggiesapp.ui.screens.petowner.NotificationPermissionScreen
import com.bussiness.slodoggiesapp.ui.screens.petowner.PetMainScreen

@Composable
fun NavGraph(navController: NavHostController, authNavController: NavHostController) {
    // Use both as needed
    NavHost(
        navController = navController,
        startDestination = Routes.SPLASH
    ) {
//        composable(Routes.SPLASH) {
//            SplashScreen(onNavigateToNext = { navController.navigate(Routes.ONBOARDING) {
//                        popUpTo(Routes.SPLASH) { inclusive = true }
//                    }
//                }
//            )
//        }
        composable(Routes.SPLASH) {
            SplashScreen(
                onNavigateToNext = {
                    navController.navigate(Routes.NOTIFICATION_ALERT) {
                        popUpTo(Routes.SPLASH) { inclusive = true }
                    }
                }
            )
        }
       // composable(Routes.ONBOARDING) { OnboardingScreen(authNavController, onFinish = {}) }
//        composable(Routes.NotificationAlert) {
//            NotificationPermissionScreen(authNavController)
//        }
//        composable(Routes.LocationAlert) {
//            LocationPermissionScreen(authNavController)
//        }
//        composable(Routes.PetMainScreen) {
//            PetMainScreen(authNavController)
//        }
        // composable(Routes.ONBOARDING) { OnboardingScreen(authNavController, onFinish = {}) }
        composable(Routes.NOTIFICATION_ALERT) {
            NotificationPermissionScreen(navController)  // Pass navController instead of authNavController
        }
        composable(Routes.LOCATION_ALERT) {
            LocationPermissionScreen(navController)  // Also changed this for consistency
        }
        composable(Routes.PET_MAIN_SCREEN) {
            PetMainScreen(authNavController)  // Keep authNavController here if PetMainScreen needs it
        }
    }
}



