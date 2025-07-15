package com.bussiness.slodoggiesapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bussiness.slodoggiesapp.ui.screens.businessprovider.DiscoverScreen
import com.bussiness.slodoggiesapp.ui.screens.businessprovider.HomeScreen
import com.bussiness.slodoggiesapp.ui.screens.businessprovider.ProfileScreen
import com.bussiness.slodoggiesapp.ui.screens.businessprovider.ServiceScreen

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
    }
}
