package com.bussiness.slodoggiesapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bussiness.slodoggiesapp.ui.screens.businessprovider.discover.DiscoverScreen
import com.bussiness.slodoggiesapp.ui.screens.businessprovider.HomeScreen
import com.bussiness.slodoggiesapp.ui.screens.businessprovider.profile.ProfileScreen
import com.bussiness.slodoggiesapp.ui.screens.businessprovider.services.ServiceScreen
import com.bussiness.slodoggiesapp.ui.screens.businessprovider.discover.PersonDetailScreen
import com.bussiness.slodoggiesapp.ui.screens.businessprovider.post.PostScreen
import com.bussiness.slodoggiesapp.ui.screens.businessprovider.profile.EditProfileScreen
import com.bussiness.slodoggiesapp.ui.screens.businessprovider.profile.FollowerScreen
import com.bussiness.slodoggiesapp.ui.screens.businessprovider.profile.SponsoredAdsScreen

@Composable
fun MainNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Routes.HOME_SCREEN,
        modifier = modifier
    ) {
        composable(Routes.HOME_SCREEN) { HomeScreen(navController) }
        composable(Routes.DISCOVER_SCREEN) { DiscoverScreen(navController) }
        composable(Routes.SERVICES_SCREEN) { ServiceScreen(navController) }
        composable(Routes.PROFILE_SCREEN) { ProfileScreen(navController) }
        composable(Routes.PERSON_DETAIL_SCREEN) { PersonDetailScreen(navController) }
        composable(Routes.FOLLOWER_SCREEN) { FollowerScreen(navController) }
        composable(Routes.SPONSORED_ADS_SCREEN) { SponsoredAdsScreen(navController) }
        composable(Routes.EDIT_PROFILE_SCREEN) { EditProfileScreen(navController) }
        composable(Routes.POST_SCREEN) { PostScreen(navController) }
        composable(Routes.POST_SCREEN) { PostScreen(navController) }
    }
}
