package com.bussiness.slodoggiesapp.ui.component


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Arrangement.Vertical
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.model.PetOwnerBottomNavItem
import com.bussiness.slodoggiesapp.navigation.Routes

val bottomNavItems = listOf(
    PetOwnerBottomNavItem("Home", R.drawable.pet_ic_home, Routes.PetHomeScreen),
    PetOwnerBottomNavItem("Uncover", R.drawable.pet_ic_discover, Routes.PetDiscoverScreen), // Changed from "Discover"
    PetOwnerBottomNavItem("", R.drawable.pet_ic_post, Routes.PetPostScreen),
    PetOwnerBottomNavItem("Services", R.drawable.pet_ic_sevice, Routes.PetServicesScreen),
    PetOwnerBottomNavItem("Profile", R.drawable.pet_ic_profile, Routes.PetProfileScreen)
)

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun CustomBottomBar(
    navController: NavController,
    items: List<PetOwnerBottomNavItem>,
    selectedRoute: String,
    onItemClick: (PetOwnerBottomNavItem) -> Unit
) {
    Box(modifier = Modifier.fillMaxWidth()) {
        // Blue curved background
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .background(
                    color = Color(0xFF258694),
                    shape = RoundedCornerShape(
                        topStart = 16.dp,
                        topEnd = 16.dp
                    )
                )
        )

        // Navigation items
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEachIndexed { index, item ->
                val selected = item.route == selectedRoute
                val isPostButton = index == 2

                Column(
                    modifier = Modifier
                        .padding( top = if (isPostButton) (-10).dp else 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (!isPostButton) {
                        // Regular navigation items
                        Icon(
                            painter = painterResource(id = item.icon),
                            contentDescription = item.label,
                            tint = if (selected) Color(0xFF258694) else Color(0xFF484C52),
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = item.label,
                            color = if (selected) Color(0xFF258694) else Color(0xFF484C52),
                            fontSize = 12.sp,
                            fontFamily = FontFamily(Font(R.font.outfit_medium)),
                            maxLines = 1
                        )
                    } else {
                        // Centered post button
                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .clip(CircleShape)
                                .background(Color.White),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.pet_ic_post),
                                contentDescription = item.label,
                                modifier = Modifier.size(40.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.Q)
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
                    selectedRoute = Routes.PetHomeScreen,
                    onItemClick = { println("Clicked on ${it.label}") }
                )
            }
        }
    }
}

//val bottomNavItems = listOf(
//    PetOwnerBottomNavItem("Home", R.drawable.pet_ic_home, Routes.PetHomeScreen),
//    PetOwnerBottomNavItem("Discover", R.drawable.pet_ic_discover, Routes.PetDiscoverScreen),
//    PetOwnerBottomNavItem("", R.drawable.pet_ic_post, Routes.PetPostScreen),
//    PetOwnerBottomNavItem("Services", R.drawable.pet_ic_sevice, Routes.PetServicesScreen),
//    PetOwnerBottomNavItem("Profile", R.drawable.pet_ic_profile, Routes.PetProfileScreen)
//)
//
//
//@RequiresApi(Build.VERSION_CODES.Q)
//@Composable
//fun CustomBottomBar(
//    navController: NavController,
//    items: List<PetOwnerBottomNavItem>,
//    selectedRoute: String,
//    onItemClick: (PetOwnerBottomNavItem) -> Unit
//) {
//    Surface(
//        color = Color.White,
//        shadowElevation = 8.dp
//    ) {
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(vertical = 8.dp),
//            horizontalArrangement = Arrangement.SpaceAround,
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            items.forEach { item ->
//                val selected = item.route == selectedRoute
//                Column(
//                    modifier = Modifier
//                        .weight(1f)
//                        .padding(vertical = 8.dp),
//                    horizontalAlignment = Alignment.CenterHorizontally
//                ) {
//                    Box(
//                        modifier = Modifier
//                            .size(40.dp)
//                            .clip(RoundedCornerShape(8.dp))
//                            .background(if (selected) Color(0xFF258694) else Color.Transparent),
//                        contentAlignment = Alignment.Center
//                    ) {
//                        Icon(
//                            painter = painterResource(id = item.icon),
//                            contentDescription = item.label,
//                            tint = if (selected) Color.White else Color(0xFF303030),
//                            modifier = Modifier.size(24.dp)
//                        )
//
//                    }
//                    if (item.label.isNotEmpty()) { // Only show text if label exists
//                        Spacer(modifier = Modifier.height(4.dp))
//                        Text(
//                            text = item.label,
//                            color = if (selected) Color(0xFF258694) else Color(0xFF303030),
//                            fontSize = 10.sp,
//                            fontFamily = FontFamily(Font(R.font.outfit_regular)),
//                            maxLines = 1
//                        )
//                    }
//                }
//
//            }
//        }
//
//    }
//
//}
//
//@RequiresApi(Build.VERSION_CODES.Q)
//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun PreviewCustomBottomBar() {
//    // Create a mock NavController for preview
//    val mockNavController = rememberNavController()
//
//    // Use MaterialTheme as wrapper for consistent theming
//    MaterialTheme {
//        Surface {
//            Column {
//                // Add some content to demonstrate the bottom bar position
//                Box(
//                    modifier = Modifier
//                        .weight(1f)
//                        .background(Color.LightGray),
//                    contentAlignment = Alignment.Center
//                ) {
//                    Text("App Content")
//                }
//
//                // The bottom bar preview
//                CustomBottomBar(
//                    navController = mockNavController,
//                    items = bottomNavItems,
//                    selectedRoute = Routes.PetHomeScreen, // Default selected item
//                    onItemClick = { item ->
//                        // Preview click handler
//                        println("Clicked on ${item.label}")
//                    }
//                )
//            }
//        }
//    }
//}
