package com.bussiness.slodoggiesapp.ui.screens.businessprovider.profile

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.model.businessProvider.AudienceData
import com.bussiness.slodoggiesapp.navigation.Routes
import com.bussiness.slodoggiesapp.ui.component.businessProvider.AudienceListItem
import com.bussiness.slodoggiesapp.ui.component.businessProvider.AudienceSelection
import com.bussiness.slodoggiesapp.ui.component.businessProvider.ScreenHeadingText
import com.bussiness.slodoggiesapp.ui.component.businessProvider.SearchBar
import com.bussiness.slodoggiesapp.ui.dialog.RemoveParticipantDialog
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor


@Composable
fun FollowerScreen(navController: NavHostController, type: String) {
    var selectedOption by remember { mutableStateOf(type) }
    var query by remember { mutableStateOf("") }
    var removeDialog by remember { mutableStateOf(false) }
    var isNavigating by remember { mutableStateOf(false) }
    val context =  LocalContext.current

    val followersList = listOf(
        AudienceData(R.drawable.profile1, "Adison Dias", true),
        AudienceData(R.drawable.profile2, "Ryan Dias", false),
        AudienceData(R.drawable.profile3, "Anika Torff", false)
    )

    val followingList = listOf(
        AudienceData(R.drawable.profile2, "Zain Dorwart", false),
        AudienceData(R.drawable.profile1, "Marcus Culhane", true),
        AudienceData(R.drawable.profile3, "Cristofer Torff", false),
        AudienceData(R.drawable.profile1, "Lydia Vaccaro", false)
    )

    val listToShow = if (selectedOption == "Follower") followersList else followingList

    BackHandler {
        if (!isNavigating) {
            isNavigating = true
            navController.popBackStack()
        }
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)) {

        ScreenHeadingText(
            textHeading = "My Profile",
            onBackClick = {  if (!isNavigating) {
                isNavigating = true
                navController.popBackStack()
            } },
            onSettingClick = { navController.navigate(Routes.SETTINGS_SCREEN) }
        )

        HorizontalDivider(thickness = 2.dp, color = PrimaryColor)

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(horizontal = 12.dp, vertical = 10.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        AudienceSelection(
                            text = "27.7 M Followers",
                            selected = selectedOption == "Follower",
                            onClick = { selectedOption = "Follower" }
                        )

                        AudienceSelection(
                            text = "219 Following",
                            selected = selectedOption == "Following",
                            onClick = { selectedOption = "Following" }
                        )
                    }
                }
            }

            item {
                SearchBar(query = query, onQueryChange = { query = it }, placeholder = "Search")
            }

            items(listToShow) { data ->
                AudienceListItem(
                    data = data,
                    isFollower = selectedOption == "Follower",
                    onPrimaryClick = { /* Handle follow/message click */ },
                    onRemoveClick = { removeDialog = true }
                )
            }

        }
        if (removeDialog){
            RemoveParticipantDialog(
                onDismiss = { removeDialog = false},
                onClickRemove = { Toast.makeText(context    , "Removed", Toast.LENGTH_SHORT).show() },
                text = "Remove Follower?",
                description = "We won’t tell Zain Dorwart they were removed from your followers.",
                iconResId = R.drawable.remove_ic_user
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FollowerScreenPreview() {
    val navController = rememberNavController()
    FollowerScreen(navController, "Follower")
}
