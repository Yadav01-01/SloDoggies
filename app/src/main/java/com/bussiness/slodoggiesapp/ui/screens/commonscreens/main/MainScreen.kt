package com.bussiness.slodoggiesapp.ui.screens.commonscreens.main

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.model.common.BottomNavItem
import com.bussiness.slodoggiesapp.model.main.UserType
import com.bussiness.slodoggiesapp.navigation.MainNavGraph
import com.bussiness.slodoggiesapp.navigation.Routes
import com.bussiness.slodoggiesapp.util.SessionManager

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen(authNavController: NavHostController) {
    val navController = rememberNavController()
    val currentRoute = getCurrentRoute(navController)
    val context = LocalContext.current
    val sessionManager = SessionManager.getInstance(context)

    val bottomNavItems = listOf(
        BottomNavItem("Home", R.drawable.home_ic, Routes.HOME_SCREEN, R.drawable.out_home_ic),
        BottomNavItem("Discover", R.drawable.discover_ic, Routes.DISCOVER_SCREEN, R.drawable.out_explore_ic),
        BottomNavItem("Services", R.drawable.service_ic,if (sessionManager.getUserType() == UserType.BUSINESS_PROVIDER) Routes.SERVICES_SCREEN else Routes.PET_SERVICES_SCREEN, R.drawable.out_service_ic),
        BottomNavItem("Profile", R.drawable.profile_ic, if (sessionManager.getUserType() == UserType.BUSINESS_PROVIDER) Routes.PROFILE_SCREEN else Routes.PET_PROFILE_SCREEN, R.drawable.out_profile_ic)
    )

    val bottomNavRoutes = bottomNavItems.map { it.route }


    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        containerColor = Color.White,
        bottomBar = {
            AnimatedVisibility(
                visible = currentRoute in bottomNavRoutes,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
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
                        val destination = if (sessionManager.getUserType() == UserType.BUSINESS_PROVIDER) {
                            Routes.POST_SCREEN
                        } else {
                            Routes.PET_NEW_POST_SCREEN
                        }

                        navController.navigate(destination) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        MainNavGraph(
            navController = navController,
            authNavController = authNavController,
            modifier = Modifier.padding(innerPadding)
        )
    }


}


@Composable
fun getCurrentRoute(navController: NavController): String {
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    return navBackStackEntry.value?.destination?.route ?: ""
}


