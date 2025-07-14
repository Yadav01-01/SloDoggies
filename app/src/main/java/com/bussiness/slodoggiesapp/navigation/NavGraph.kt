package com.bussiness.slodoggiesapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bussiness.slodoggiesapp.ui.intro.SplashScreen
import com.bussiness.slodoggiesapp.ui.screens.commonscreens.OnboardingScreen
import com.bussiness.slodoggiesapp.ui.screens.petowner.NotificationPermissionScreen

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
                    navController.navigate(Routes.NotificationAlert) {
                        popUpTo(Routes.SPLASH) { inclusive = true }
                    }
                }
            )
        }
       // composable(Routes.ONBOARDING) { OnboardingScreen(authNavController, onFinish = {}) }
        composable(Routes.NotificationAlert) {
            NotificationPermissionScreen(
                onTurnOnClick = {
                    // Handle turn on notifications action
                    // You might want to navigate somewhere after enabling notifications
                    authNavController.navigate(Routes.SPLASH)  // Example navigation
                },
                onNotNowClick = {
                    // Handle "not now" action
                    authNavController.navigate(Routes.SPLASH)  // Example navigation
                }
            )
        }
    }
}



