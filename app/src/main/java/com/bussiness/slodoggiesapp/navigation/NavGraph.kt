package com.bussiness.slodoggiesapp.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.bussiness.slodoggiesapp.model.VerificationType
import com.bussiness.slodoggiesapp.ui.intro.OnboardingScreen
import com.bussiness.slodoggiesapp.ui.intro.SplashScreen
import com.bussiness.slodoggiesapp.ui.screens.businessprovider.AddServiceScreen
import com.bussiness.slodoggiesapp.ui.screens.businessprovider.BusinessRegistrationScreen
import com.bussiness.slodoggiesapp.ui.screens.commonscreens.*
import com.bussiness.slodoggiesapp.ui.screens.petowner.LocationPermissionScreen
import com.bussiness.slodoggiesapp.ui.screens.petowner.NotificationPermissionScreen
import com.bussiness.slodoggiesapp.ui.screens.petowner.NotificationsScreen
import com.bussiness.slodoggiesapp.ui.screens.petowner.PetHomeScreen
import com.bussiness.slodoggiesapp.ui.screens.petowner.PetMainScreen
import com.bussiness.slodoggiesapp.ui.screens.petowner.PetServicesScreen
import com.bussiness.slodoggiesapp.ui.screens.petowner.serviceProviderDetailsScreen.ServiceProviderDetailsScreen

@Composable
fun NavGraph(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding() // Global nav bar padding
    ) {
        NavHost(navController = navController, startDestination = Routes.SPLASH) {
            composable(Routes.SPLASH) {
                SplashScreen(onNavigateToNext = {
                    navController.navigate(Routes.ONBOARDING) {
                        popUpTo(Routes.SPLASH) { inclusive = true }
                    }
                })
            }
            composable(Routes.ONBOARDING) { OnboardingScreen(navController, onFinish = {}) }
            composable(Routes.JOIN_THE_PACK) { JoinThePackScreen(navController) }
            composable(Routes.PHONE_AUTH_SCREEN) { PhoneAuthScreen(navController) }
            composable(Routes.EMAIL_AUTH_SCREEN) { EmailLoginScreen(navController) }
            composable(Routes.BUSINESS_REGISTRATION) { BusinessRegistrationScreen(navController) }
            composable(Routes.ADD_SERVICE) { AddServiceScreen(navController) }
            composable(Routes.MAIN_SCREEN) { MainScreen(navController) }

            composable(
                route = "${Routes.VERIFY_OTP}/{type}",
                arguments = listOf(navArgument("type") { type = NavType.StringType })
            ) {
                val type = it.arguments?.getString("type") ?: "phone"
                VerifyOTPScreen(navController, type = VerificationType.valueOf(type.uppercase()))
            }

            // petOwner
            composable(Routes.NOTIFICATION_PERMISSION_SCREEN) {
                NotificationPermissionScreen(
                    navController
                )
            }
            composable(Routes.LOCATION_PERMISSION_SCREEN) { LocationPermissionScreen(navController) }
            composable(Routes.PET_MAIN_SCREEN) { PetMainScreen(navController) }
            composable(Routes.PET_MAIN_SCREEN) {
                PetMainScreen(navController)  // Keep authNavController here if PetMainScreen needs it
            }
            composable(Routes.PET_HOME_SCREEN) {
                PetHomeScreen(navController)  // Keep authNavController here if PetMainScreen needs it
            }

            composable(Routes.PET_SERVICES_SCREEN) {
                PetServicesScreen(navController)
            }

            composable(Routes.SERVICE_PROVIDER_DETAILS) {
                ServiceProviderDetailsScreen(navController)
            }
        }
    }
    }