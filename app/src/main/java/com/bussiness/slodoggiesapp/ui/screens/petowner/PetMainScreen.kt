package com.bussiness.slodoggiesapp.ui.screens.petowner

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.model.BottomNavItem
import com.bussiness.slodoggiesapp.navigation.PetMainNavGraph
import com.bussiness.slodoggiesapp.navigation.Routes
import com.bussiness.slodoggiesapp.ui.screens.commonscreens.CustomBottomBar

@Composable
fun PetMainScreen(authNavController: NavHostController) {
    val navController = rememberNavController()
    val currentRoute = getCurrentRoute(navController)
    val bottomNavItems = listOf(
        BottomNavItem("Home", R.drawable.home_ic, Routes.PET_HOME_SCREEN, R.drawable.out_home_ic),
        BottomNavItem("Discover", R.drawable.discover_ic, Routes.DISCOVER_SCREEN, R.drawable.out_explore_ic),
        BottomNavItem("Services", R.drawable.service_ic, Routes.PET_SERVICES_SCREEN, R.drawable.out_service_ic),
        BottomNavItem("Profile", R.drawable.profile_ic, Routes.PET_PROFILE_SCREEN, R.drawable.out_profile_ic)
    )

    Scaffold(
        bottomBar = {
            CustomBottomBar(
                navController = navController,
                items = bottomNavItems,
                selectedRoute = currentRoute,
                onItemClick = { navItem ->
                    navController.navigate(navItem.route) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                onCenterClick = {
                    // Handle center FAB click (e.g., navigate to Add screen)
                }
            )
        }
    ){ innerPadding ->
        PetMainNavGraph(navController = navController, modifier = Modifier.padding(innerPadding))
    }
}



@Composable
fun getCurrentRoute(navController: NavController): String {
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    return navBackStackEntry.value?.destination?.route ?: ""
}
