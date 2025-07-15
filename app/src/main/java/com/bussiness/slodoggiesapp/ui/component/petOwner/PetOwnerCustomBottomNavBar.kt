package com.bussiness.slodoggiesapp.ui.component.petOwner

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.model.PetOwnerBottomNavItem
import com.bussiness.slodoggiesapp.navigation.Routes
import androidx.compose.material3.*
import androidx.compose.runtime.remember

val bottomNavItems = listOf(
    PetOwnerBottomNavItem("Home", R.drawable.pet_ic_home, Routes.PET_HOME_SCREEN),
    PetOwnerBottomNavItem("Discover", R.drawable.pet_ic_discover, Routes.PET_DISCOVER_SCREEN),
    PetOwnerBottomNavItem("", R.drawable.pet_ic_post, Routes.PET_POST_SCREEN),
    PetOwnerBottomNavItem("Services", R.drawable.pet_ic_sevice, Routes.PET_SERVICES_SCREEN),
    PetOwnerBottomNavItem("Profile", R.drawable.pet_ic_profile, Routes.PET_PROFILE_SCREEN)
)

//@RequiresApi(Build.VERSION_CODES.Q)
//@Composable
//fun CustomBottomBar(
//    navController: NavController,
//    items: List<PetOwnerBottomNavItem>,
//    selectedRoute: String,
//    onItemClick: (PetOwnerBottomNavItem) -> Unit
//) {
//    Box(
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(90.dp)
//    ) {
//        // White background with rounded corners and shadow effect
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(70.dp)
//                .background(
//                    color = Color.White,
//                    shape = RoundedCornerShape(
//                        topStart = 20.dp,
//                        topEnd = 20.dp
//                    )
//                )
//                .padding(top = 8.dp)
//        )
//
//        // Navigation items
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(90.dp)
//                .padding(horizontal = 20.dp, vertical = 10.dp),
//            horizontalArrangement = Arrangement.SpaceEvenly,
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            items.forEachIndexed { index, item ->
//                val selected = item.route == selectedRoute
//                val isPostButton = index == 2
//
//                Box(
//                    modifier = Modifier
//                        .weight(1f)
//                        .clickable { onItemClick(item) },
//                    contentAlignment = Alignment.Center
//                ) {
//                    if (!isPostButton) {
//                        // Regular navigation items
//                        Column(
//                            horizontalAlignment = Alignment.CenterHorizontally,
//                            verticalArrangement = Arrangement.Center
//                        ) {
//                            Icon(
//                                painter = painterResource(id = item.icon),
//                                contentDescription = item.label,
//                                tint = if (selected) Color(0xFF258694) else Color(0xFF6B7280),
//                                modifier = Modifier.size(22.dp)
//                            )
//                            Spacer(modifier = Modifier.height(4.dp))
//                            Text(
//                                text = item.label,
//                                color = if (selected) Color(0xFF258694) else Color(0xFF6B7280),
//                                fontSize = 10.sp,
//                                fontFamily = FontFamily(Font(R.font.outfit_medium)),
//                                maxLines = 1
//                            )
//                        }
//                    } else {
//                        // Centered post button - elevated above background
//                        Box(
//                            modifier = Modifier
//                                .size(60.dp)
//                                .clip(CircleShape)
//                                .background(Color(0xFF258694))
//                                .padding(2.dp),
//                            contentAlignment = Alignment.Center
//                        ) {
//                            Box(
//                                modifier = Modifier
//                                    .size(56.dp)
//                                    .clip(CircleShape)
//                                    .background(Color.White)
//                                    .padding(4.dp),
//                                contentAlignment = Alignment.Center
//                            ) {
//                                Icon(
//                                    painter = painterResource(id = R.drawable.pet_ic_post),
//                                    contentDescription = "Post",
//                                    tint = Color(0xFF258694),
//                                    modifier = Modifier.size(28.dp)
//                                )
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }
//}
@Composable
fun CustomBottomBar(
    barHeight: Dp = 70.dp,
    fabSize: Dp = 60.dp,
    fabIconSize: Dp = 28.dp,
    cardTopCornerSize: Dp = 20.dp,
    cardElevation: Dp = 8.dp,
    selectedItemColor: Color = Color(0xFF258694),
    unselectedItemColor: Color = Color(0xFF6B7280),
    backgroundColor: Color = Color.White,
    fabBackgroundColor: Color = Color(0xFF258694),
    fabBorderColor: Color = Color.White,
    fabBorderSize: Dp = 2.dp,
    itemPadding: Dp = 8.dp,
    spacingBetweenIconAndText: Dp = 4.dp,
    iconSize: Dp = 22.dp,
    labelFontSize: TextUnit = 10.sp,
    navController: NavController,
    items: List<PetOwnerBottomNavItem>,
    selectedRoute: String,
    onItemClick: (PetOwnerBottomNavItem) -> Unit
) {
            Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(170.dp)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(
                        topStart = 20.dp,
                        topEnd = 20.dp
                    )
                )
                .padding(top = 8.dp)
        ){


    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(barHeight + fabSize / 2)
    ) {
        // Background card for the bottom bar
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(barHeight)
                .align(Alignment.BottomCenter),
            colors = CardDefaults.cardColors(containerColor = backgroundColor),
            elevation = CardDefaults.cardElevation(defaultElevation = cardElevation),
            shape = RoundedCornerShape(
                topStart = cardTopCornerSize,
                topEnd = cardTopCornerSize
            )
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                // First two items
                items.take(2).forEach { item ->
                    BottomNavItem(
                        item = item,
                        selected = item.route == selectedRoute,
                        selectedItemColor = selectedItemColor,
                        unselectedItemColor = unselectedItemColor,
                        itemPadding = itemPadding,
                        spacingBetweenIconAndText = spacingBetweenIconAndText,
                        iconSize = iconSize,
                        labelFontSize = labelFontSize,
                        onClick = { onItemClick(item) }
                    )
                }

                // Spacer for the FAB
                Spacer(modifier = Modifier.size(fabSize))

                // Last two items
                items.takeLast(2).forEach { item ->
                    BottomNavItem(
                        item = item,
                        selected = item.route == selectedRoute,
                        selectedItemColor = selectedItemColor,
                        unselectedItemColor = unselectedItemColor,
                        itemPadding = itemPadding,
                        spacingBetweenIconAndText = spacingBetweenIconAndText,
                        iconSize = iconSize,
                        labelFontSize = labelFontSize,
                        onClick = { onItemClick(item) }
                    )
                }
            }
        }

        // Floating Action Button in the center
        LargeFloatingActionButton(
            modifier = Modifier
                .size(fabSize)
                .align(Alignment.TopCenter)
                .offset(y = (-fabSize / 2))
                .border(fabBorderSize, fabBorderColor, CircleShape),
            onClick = {
                items.find { it.route == Routes.PET_POST_SCREEN }?.let { onItemClick(it) }
            },
            shape = CircleShape,
            containerColor = fabBackgroundColor,
        ) {
            Box(
                modifier = Modifier
                    .size(fabSize - 8.dp)
                    .clip(CircleShape)
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.pet_ic_post),
                    contentDescription = "Post",
                    tint = fabBackgroundColor,
                    modifier = Modifier.size(fabIconSize)
                )
            }
        }
    } }
}

@Composable
private fun BottomNavItem(
    item: PetOwnerBottomNavItem,
    selected: Boolean,
    selectedItemColor: Color,
    unselectedItemColor: Color,
    itemPadding: Dp,
    spacingBetweenIconAndText: Dp,
    iconSize: Dp,
    labelFontSize: TextUnit,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(horizontal = itemPadding)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) { onClick() }
    ) {
        Icon(
            painter = painterResource(id = item.icon),
            contentDescription = item.label,
            tint = if (selected) selectedItemColor else unselectedItemColor,
            modifier = Modifier.size(iconSize)
        )
        Spacer(modifier = Modifier.height(spacingBetweenIconAndText))

        if (item.label.isNotEmpty()) {
            Text(
                text = item.label,
                color = if (selected) selectedItemColor else unselectedItemColor,
                fontSize = labelFontSize,
                fontFamily = FontFamily(Font(R.font.outfit_medium)),
                maxLines = 1
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewCustomBottomBar() {
    val mockNavController = rememberNavController()
    MaterialTheme {
        Surface {
            Column {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .background(Color.LightGray),
                    contentAlignment = Alignment.Center
                ) {
                    Text("App Content")
                }
                CustomBottomBar(
                    navController = mockNavController,
                    items = bottomNavItems,
                    selectedRoute = Routes.PET_HOME_SCREEN,
                    onItemClick = { println("Clicked on ${it.label}") }
                )
            }
        }
    }
}



//import android.os.Build
//import androidx.annotation.RequiresApi
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material3.Icon
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Surface
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.font.Font
//import androidx.compose.ui.text.font.FontFamily
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.navigation.NavController
//import androidx.navigation.compose.rememberNavController
//import com.bussiness.slodoggiesapp.R
//import com.bussiness.slodoggiesapp.model.PetOwnerBottomNavItem
//import com.bussiness.slodoggiesapp.navigation.Routes
//
//val bottomNavItems = listOf(
//    PetOwnerBottomNavItem("Home", R.drawable.pet_ic_home, Routes.PetHomeScreen),
//    PetOwnerBottomNavItem("Uncover", R.drawable.pet_ic_discover, Routes.PetDiscoverScreen), // Changed from "Discover"
//    PetOwnerBottomNavItem("", R.drawable.pet_ic_post, Routes.PetPostScreen),
//    PetOwnerBottomNavItem("Services", R.drawable.pet_ic_sevice, Routes.PetServicesScreen),
//    PetOwnerBottomNavItem("Profile", R.drawable.pet_ic_profile, Routes.PetProfileScreen)
//)
//
//@RequiresApi(Build.VERSION_CODES.Q)
//@Composable
//fun CustomBottomBar(
//    navController: NavController,
//    items: List<PetOwnerBottomNavItem>,
//    selectedRoute: String,
//    onItemClick: (PetOwnerBottomNavItem) -> Unit
//) {
//    Box(modifier = Modifier.fillMaxWidth()) {
//        // Blue curved background
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(40.dp)
//                .background(
//                    color = Color(0xFF258694),
//                    shape = RoundedCornerShape(
//                        topStart = 16.dp,
//                        topEnd = 16.dp
//                    )
//                )
//        )
//
//        // Navigation items
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(120.dp)
//                .padding(vertical = 8.dp),
//            horizontalArrangement = Arrangement.SpaceEvenly,
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            items.forEachIndexed { index, item ->
//                val selected = item.route == selectedRoute
//                val isPostButton = index == 2
//
//                Column(
//                    modifier = Modifier
//                        .padding( top = if (isPostButton) (-10).dp else 20.dp),
//                    horizontalAlignment = Alignment.CenterHorizontally
//                ) {
//                    if (!isPostButton) {
//                        // Regular navigation items
//                        Icon(
//                            painter = painterResource(id = item.icon),
//                            contentDescription = item.label,
//                            tint = if (selected) Color(0xFF258694) else Color(0xFF484C52),
//                            modifier = Modifier.size(24.dp)
//                        )
//                        Spacer(modifier = Modifier.height(4.dp))
//                        Text(
//                            text = item.label,
//                            color = if (selected) Color(0xFF258694) else Color(0xFF484C52),
//                            fontSize = 12.sp,
//                            fontFamily = FontFamily(Font(R.font.outfit_medium)),
//                            maxLines = 1
//                        )
//                    } else {
//                        // Centered post button
//                        Box(
//                            modifier = Modifier
//                                .size(50.dp)
//                                .clip(CircleShape)
//                                .background(Color.White),
//                            contentAlignment = Alignment.Center
//                        ) {
//                            Icon(
//                                painter = painterResource(id = R.drawable.pet_ic_post),
//                                contentDescription = item.label,
//                                modifier = Modifier.size(40.dp)
//                            )
//                        }
//                    }
//                }
//            }
//        }
//    }
//}
//
//@RequiresApi(Build.VERSION_CODES.Q)
//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun PreviewCustomBottomBar() {
//    val mockNavController = rememberNavController()
//    MaterialTheme {
//        Surface {
//            Column {
//                Box(
//                    modifier = Modifier
//                        .weight(1f)
//                        .background(Color.LightGray),
//                    contentAlignment = Alignment.Center
//                ) {
//                    Text("App Content")
//                }
//                CustomBottomBar(
//                    navController = mockNavController,
//                    items = bottomNavItems,
//                    selectedRoute = Routes.PetHomeScreen,
//                    onItemClick = { println("Clicked on ${it.label}") }
//                )
//            }
//        }
//    }
//}
