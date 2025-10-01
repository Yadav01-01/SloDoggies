package com.bussiness.slodoggiesapp.navigation

import android.os.Build
import androidx.annotation.RequiresApi
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
import com.bussiness.slodoggiesapp.ui.intro.OnboardingScreen
import com.bussiness.slodoggiesapp.ui.intro.SplashScreen
import com.bussiness.slodoggiesapp.ui.screens.businessprovider.registration.AddServiceScreen
import com.bussiness.slodoggiesapp.ui.screens.businessprovider.registration.BusinessRegistrationScreen
import com.bussiness.slodoggiesapp.ui.screens.businessprovider.settings.SubscriptionScreen
import com.bussiness.slodoggiesapp.ui.screens.commonscreens.authFlow.ForgotPasswordScreen
import com.bussiness.slodoggiesapp.ui.screens.commonscreens.authFlow.LoginScreen
import com.bussiness.slodoggiesapp.ui.screens.commonscreens.authFlow.NewPasswordScreen
import com.bussiness.slodoggiesapp.ui.screens.commonscreens.authFlow.SignUpScreen
import com.bussiness.slodoggiesapp.ui.screens.commonscreens.authFlow.VerifyOTPScreen
import com.bussiness.slodoggiesapp.ui.screens.commonscreens.main.JoinThePackScreen
import com.bussiness.slodoggiesapp.ui.screens.commonscreens.main.MainScreen
import com.bussiness.slodoggiesapp.ui.screens.commonscreens.permissionScreen.LocationPermissionScreen
import com.bussiness.slodoggiesapp.ui.screens.commonscreens.permissionScreen.NotificationPermissionScreen
import com.bussiness.slodoggiesapp.ui.screens.commonscreens.settings.AuthPrivacyPolicyScreen
import com.bussiness.slodoggiesapp.ui.screens.commonscreens.settings.AuthTermsAndConditionsScreen
import com.bussiness.slodoggiesapp.ui.screens.petowner.service.serviceProviderDetailsScreen.ServiceProviderDetailsScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavGraph(navController: NavHostController) {
    Box(
        modifier = Modifier.fillMaxSize().navigationBarsPadding()
    ) {
        NavHost(navController = navController, startDestination = Routes.SPLASH) {

            // --- Intro / Auth Screens ---
            composable(Routes.SPLASH) { SplashScreen(navController) }
            composable(Routes.ONBOARDING) { OnboardingScreen(navController) }
            composable(Routes.JOIN_THE_PACK) { JoinThePackScreen(navController) }
            composable(Routes.LOGIN_SCREEN) { LoginScreen(navController) }
            composable(Routes.SIGNUP_SCREEN) { SignUpScreen(navController) }
            composable(Routes.NEW_PASSWORD_SCREEN) { NewPasswordScreen(navController) }
            composable(Routes.AUTH_TERMS_AND_CONDITION_SCREEN) { AuthTermsAndConditionsScreen(navController) }
            composable(Routes.AUTH_PRIVACY_POLICY_SCREEN) { AuthPrivacyPolicyScreen(navController) }

            // --- Business Provider Screens ---
            composable(Routes.BUSINESS_REGISTRATION) { BusinessRegistrationScreen(navController) }
            composable(Routes.ADD_SERVICE) { AddServiceScreen(navController) }
            composable(Routes.MAIN_SCREEN) { MainScreen(navController) }

            // --- Pet Owner Screens ---
            composable(Routes.NOTIFICATION_PERMISSION_SCREEN) { NotificationPermissionScreen(navController) }
            composable(Routes.LOCATION_PERMISSION_SCREEN) { LocationPermissionScreen(navController) }
            composable(Routes.SERVICE_PROVIDER_DETAILS) { ServiceProviderDetailsScreen(navController) }

            composable(
                route = "${Routes.FORGOT_PASSWORD_SCREEN}/{type}",
                arguments = listOf(
                    navArgument("type") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val type = backStackEntry.arguments?.getString("type") ?: "default"
                ForgotPasswordScreen(navController, type)
            }

            composable(
                route = "${Routes.VERIFY_OTP}?type={type}&data={data}",
                arguments = listOf(
                    navArgument("type") { type = NavType.StringType },
                    navArgument("data") { type = NavType.StringType; defaultValue = "" }
                )
            ) { backStackEntry ->
                val type = backStackEntry.arguments?.getString("type") ?: "default"
                VerifyOTPScreen(navController, type = type)
            }
        }
    }
}
