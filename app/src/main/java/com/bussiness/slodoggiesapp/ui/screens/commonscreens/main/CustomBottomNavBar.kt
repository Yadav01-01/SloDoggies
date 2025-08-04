package com.bussiness.slodoggiesapp.ui.screens.commonscreens.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.model.BottomNavItem
import com.bussiness.slodoggiesapp.navigation.Routes
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor


@Composable
fun CustomBottomBar(
    navController: NavController,
    items: List<BottomNavItem>,
    selectedRoute: String,
    onItemClick: (BottomNavItem) -> Unit,
    onCenterClick: () -> Unit
) {
    Box(modifier = Modifier.fillMaxWidth()) {

        // Bottom container with top teal strip and rounded corners
        Column(modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
        ) {
            // Top Teal Strip
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(28.dp)
                    .background(PrimaryColor, shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
            )

            // White base container
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(72.dp),
                color = Color.White,
                shadowElevation = 4.dp
            ) {
                Row(
                    modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp).padding(bottom = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    val splitIndex = items.size / 2
                    val firstHalf = items.take(splitIndex)
                    val secondHalf = items.drop(splitIndex)

                    firstHalf.forEach { item ->
                        BottomNavItemComposable(item, selectedRoute, onItemClick)
                    }

                    Spacer(modifier = Modifier.width(64.dp)) // Space for center button

                    secondHalf.forEach { item ->
                        BottomNavItemComposable(item, selectedRoute, onItemClick)
                    }
                }
            }
        }

        // Center Floating Paw Button
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = (8).dp)
                .wrapContentSize()
                .clip(RoundedCornerShape(32.dp))
                .background(Color.White)
                .clickable { onCenterClick() },
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(R.drawable.paw_ic_btmnav),
                contentDescription = "Center Button",
                modifier = Modifier.wrapContentSize()
            )
        }

        // Bottom Indicator Line
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 6.dp)
                .width(80.dp)
                .height(4.dp)
                .clip(RoundedCornerShape(30.dp))
                .background(PrimaryColor)
        )
    }
}


@Composable
fun BottomNavItemComposable(
    item: BottomNavItem,
    selectedRoute: String,
    onClick: (BottomNavItem) -> Unit
) {
    val selected = item.route == selectedRoute

    Column(
        modifier = Modifier
            .width(64.dp)
            .clickable { onClick(item) },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(id = if (selected) item.selectedIcon ?: item.icon else item.icon),
            contentDescription = item.label,
            tint = Color.Unspecified, // Keeps original icon color
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = item.label,
            fontSize = 11.sp,
            fontFamily = FontFamily(Font(R.font.outfit_medium)),
            color = if (selected) PrimaryColor else Color(0xFF484C52),
            textAlign = TextAlign.Center
        )
    }
}


@Preview(showBackground = true)
@Composable
fun CustomBottomBarPreview() {
    val navController = rememberNavController()
    val currentRoute = Routes.DISCOVER_SCREEN
    val bottomNavItems = listOf(
        BottomNavItem("Home", R.drawable.home_ic, Routes.HOME_SCREEN, R.drawable.out_home_ic),
        BottomNavItem("Discover", R.drawable.discover_ic, Routes.DISCOVER_SCREEN, R.drawable.out_explore_ic),
        BottomNavItem("Services", R.drawable.service_ic, Routes.SERVICES_SCREEN, R.drawable.out_service_ic),
        BottomNavItem("Profile", R.drawable.profile_ic, Routes.PROFILE_SCREEN, R.drawable.out_profile_ic)
    )



    CustomBottomBar(
        navController = navController,
        items = bottomNavItems,
        selectedRoute = currentRoute,
        onItemClick = { /* navController.navigate(it.route) */ },
        onCenterClick = { /* Open Center Action (like Add Post, etc.) */ }
    )

}