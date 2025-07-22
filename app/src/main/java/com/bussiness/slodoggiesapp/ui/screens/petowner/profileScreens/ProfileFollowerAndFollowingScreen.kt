package com.bussiness.slodoggiesapp.ui.screens.petowner.profileScreens

import android.annotation.SuppressLint
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.ui.component.PetOwner.CommonTopAppBar
import com.bussiness.slodoggiesapp.ui.screens.petowner.serviceProviderDetailsScreen.ReviewInterface
import com.bussiness.slodoggiesapp.ui.screens.petowner.serviceProviderDetailsScreen.ServicesContent
import com.bussiness.slodoggiesapp.ui.screens.petowner.serviceProviderDetailsScreen.SwitchButton

@Composable
fun ProfileFollowerAndFollowingScreen(
    navController: NavController = rememberNavController()
) {
    var selectedOption by remember { mutableStateOf("27.7 M Followers") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Header with stats
        CommonTopAppBar(
            title = "My Profile",
            onBackClick = { navController.popBackStack() },
            dividerColor = Color(0xFF258694),
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                SwitchButton1(
                    selectedOption = selectedOption, // Pass the current state
                    onOptionSelected = { newOption ->
                        selectedOption = newOption // Update the state when clicked
                    }
                )
                when (selectedOption) {
                    "27.7 M Followers" -> {

                    }

                    "219 Following" -> {

                    }
                }
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "21",
                    fontFamily = FontFamily(Font(R.font.outfit_medium)),
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Following",
                    fontFamily = FontFamily(Font(R.font.outfit_medium)),
                    color = Color.Gray
                )
            }
        }

        // Search bar
        TextField(
            value = "",
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            placeholder = { Text("Search") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search"
                )
            },
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.LightGray.copy(alpha = 0.2f),
                focusedContainerColor = Color.LightGray.copy(alpha = 0.2f),
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(8.dp),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        // List of users
        LazyColumn {
            items(getFollowList()) { user ->
                UserListItem(user = user)
                Divider(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    thickness = 0.5.dp,
                    color = Color.LightGray
                )
            }
        }
    }
}

@Composable
fun UserListItem(user: User) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // User avatar
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(Color.LightGray),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = user.name.first().toString(),
                fontFamily = FontFamily(Font(R.font.outfit_medium)),
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        // User name
        Text(
            text = user.name,
            fontFamily = FontFamily(Font(R.font.outfit_medium)),
            modifier = Modifier.weight(1f)
        )

        // Action button
        when (user.relation) {
            UserRelation.FOLLOW_BACK -> {
                Button(
                    onClick = { /* Handle follow */ },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Blue,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.width(120.dp)
                ) {
                    Text("Follow Back")
                }
            }
            UserRelation.MESSAGE -> {
                OutlinedButton(
                    onClick = { /* Handle message */ },
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.width(120.dp)
                ) {
                    Text("Message")
                }
            }
        }
    }
}

// Data classes
enum class UserRelation { FOLLOW_BACK, MESSAGE }

data class User(
    val name: String,
    val relation: UserRelation
)

fun getFollowList(): List<User> {
    return listOf(
        User("Adison Dias", UserRelation.FOLLOW_BACK),
        User("Ryan Dias", UserRelation.FOLLOW_BACK),
        User("Anika Torff", UserRelation.FOLLOW_BACK),
        User("Zain Dorwart", UserRelation.MESSAGE),
        User("Marcus Culhane", UserRelation.FOLLOW_BACK),
        User("Cristofer Torff", UserRelation.MESSAGE),
        User("Kierra Westervelt", UserRelation.FOLLOW_BACK),
        User("Lydia Vaccaro", UserRelation.MESSAGE),
        User("Adison Dias", UserRelation.FOLLOW_BACK),
        User("Adison Dias", UserRelation.MESSAGE)
    )
}

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun SwitchButton1(
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val options = listOf("27.7 M Followers", "219 Following")

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(3.dp)
            .padding(horizontal = 15.dp, vertical = 4.dp)


    ) {
        // Animated indicator
        val animatedOffset by animateFloatAsState(
            targetValue = if (selectedOption == "27.7 M Followers") 0f else 1f,
            animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing),
            label = "switch_animation"
        )

        BoxWithConstraints {
            val containerWidth = maxWidth
            val indicatorWidth = (containerWidth - 8.dp) / 2

            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(indicatorWidth)
                    .offset(x = animatedOffset * indicatorWidth)
                    .padding(vertical = 10.dp, horizontal = 5.dp)
                    .background(
                        color = Color(0xFF258694),
                        shape = RoundedCornerShape(8.dp)
                    )

            )
        }


        // Option buttons
        Row(
            modifier = Modifier.fillMaxSize()
        ) {
            options.forEachIndexed { index, option ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ) {
                            onOptionSelected(option)
                        },
                    contentAlignment = Alignment.Center
                ) {
                    androidx.compose.material.Text(
                        text = option,
                        color = if (selectedOption == option) Color.White else Color.Black,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }

}

@Preview
@Composable
fun FollowListPreview() {
    MaterialTheme {
        ProfileFollowerAndFollowingScreen()
    }
}