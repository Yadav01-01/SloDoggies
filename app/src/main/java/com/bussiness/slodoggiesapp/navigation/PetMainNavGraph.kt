package com.bussiness.slodoggiesapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bussiness.slodoggiesapp.ui.screens.businessprovider.DiscoverScreen
import com.bussiness.slodoggiesapp.ui.screens.petowner.PetHomeScreen
import com.bussiness.slodoggiesapp.ui.screens.petowner.PetServicesScreen
import com.bussiness.slodoggiesapp.ui.screens.petowner.profileScreens.EditPetProfileScreen
import com.bussiness.slodoggiesapp.ui.screens.petowner.profileScreens.EditProfileScreen
import com.bussiness.slodoggiesapp.ui.screens.petowner.profileScreens.PetProfileScreen
import com.bussiness.slodoggiesapp.ui.screens.petowner.profileScreens.ProfileFollowerAndFollowingScreen
import com.bussiness.slodoggiesapp.ui.screens.petowner.serviceProviderDetailsScreen.ServiceProviderDetailsScreen
import com.bussiness.slodoggiesapp.ui.screens.settingScreens.PetSavedScreen
import com.bussiness.slodoggiesapp.ui.screens.settingScreens.PetSettingsScreen

@Composable
fun PetMainNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Routes.PET_HOME_SCREEN,
        modifier = modifier
    ) {
        composable(Routes.PET_HOME_SCREEN) { PetHomeScreen(navController) }
        composable(Routes.DISCOVER_SCREEN) { DiscoverScreen(navController) }
        composable(Routes.PET_SERVICES_SCREEN) { PetServicesScreen(navController) }
        composable(Routes.PET_PROFILE_SCREEN) { PetProfileScreen(navController) }
        composable(Routes.SERVICE_PROVIDER_DETAILS) {
            ServiceProviderDetailsScreen(navController)
        }
        composable(Routes.PROFILE_FOLLOWER_FOLLOWING) {
            ProfileFollowerAndFollowingScreen(navController)
        }
        composable(Routes.EDIT_PET_PROFILE_SCREEN) {
            EditPetProfileScreen(navController)
        }
        composable(Routes.EDIT_PROFILE_SCREEN) {
            EditProfileScreen(navController)
        }
        composable(Routes.PET_SETTINGS_SCREEN) {
            PetSettingsScreen(navController)
        }
        composable(Routes.PET_SAVED_SCREEN) {
            PetSavedScreen(navController)
        }

    }
}

