package com.bussiness.slodoggiesapp.ui.screens.businessprovider.profile

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.bussiness.slodoggiesapp.ui.dialog.CenterToast
import com.bussiness.slodoggiesapp.ui.dialog.RemoveParticipantDialog
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor
import kotlinx.coroutines.delay


@Composable
fun FollowerScreen(navController: NavHostController, type: String) {
    var selectedOption by remember { mutableStateOf(type) }
    var query by remember { mutableStateOf("") }
    var removeDialog by remember { mutableStateOf(false) }
    var removeToast by remember { mutableStateOf(false) }
    var isNavigating by remember { mutableStateOf(false) }

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
                    onPrimaryClick = { navController.navigate(Routes.CHAT_SCREEN) },
                    onRemoveClick = { removeDialog = true },
                    onFollowBackClick = { }
                )
            }

        }
        if (removeDialog){
            RemoveParticipantDialog(
                onDismiss = { removeDialog = false},
                onClickRemove = { removeToast = true },
                text = "Remove Follower?",
                description = "We won’t tell Zain Dorwart they were removed from your followers.",
                iconResId = R.drawable.remove_ic_user
            )
        }
        if (removeToast){
            LaunchedEffect(Unit) {
                delay(1000)
                removeToast = false
            }
            CenterToast(
                onDismiss = { removeToast = false }
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
