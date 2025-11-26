package com.bussiness.slodoggiesapp.ui.screens.businessprovider.profile

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.data.uiState.FollowerFollowingUiState
import com.bussiness.slodoggiesapp.navigation.Routes
import com.bussiness.slodoggiesapp.ui.component.LoaderOverlay
import com.bussiness.slodoggiesapp.ui.component.businessProvider.AudienceListItem
import com.bussiness.slodoggiesapp.ui.component.businessProvider.AudienceSelection
import com.bussiness.slodoggiesapp.ui.component.businessProvider.ScreenHeadingText
import com.bussiness.slodoggiesapp.ui.component.businessProvider.SearchBar
import com.bussiness.slodoggiesapp.ui.component.common.NoDataView
import com.bussiness.slodoggiesapp.ui.dialog.CenterToast
import com.bussiness.slodoggiesapp.ui.dialog.RemoveParticipantDialog
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor
import com.bussiness.slodoggiesapp.viewModel.common.FollowerFollowingViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.delay


@Composable
fun FollowerScreen(
    navController: NavHostController,
    type: String
) {
    val viewModel: FollowerFollowingViewModel = hiltViewModel()

    var screenState by remember {
        mutableStateOf(FollowerFollowingUiState(selectedOption = type))
    }
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(uiState.error) {
        uiState.error?.let { msg ->
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()

            // Clear after showing
            viewModel.consumeError()
        }
    }
    fun updateState(update: FollowerFollowingUiState.() -> FollowerFollowingUiState) {
        screenState = screenState.update()
    }
    val listToShow = if (screenState.selectedOption == "Follower")
        uiState.followers
    else
        uiState.following


    BackHandler {
        if (!screenState.isNavigating) {
            updateState { copy(isNavigating = true) }
            navController.popBackStack()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        ScreenHeadingText(
            textHeading = "My Profile",
            onBackClick = {
                if (!screenState.isNavigating) {
                    updateState { copy(isNavigating = true) }
                    navController.popBackStack()
                }
            },
            onSettingClick = { navController.navigate(Routes.SETTINGS_SCREEN) }
        )

        HorizontalDivider(thickness = 2.dp, color = PrimaryColor)

        SwipeRefresh(
            state = rememberSwipeRefreshState(uiState.isRefreshing),
            onRefresh = {
                viewModel.loadFollowers(reset = true)
            }
        ) {

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
                                text = "${uiState.totalFollower} Followers",
                                selected = screenState.selectedOption == "Follower",
                                onClick = {
                                    updateState { copy(selectedOption = "Follower") }
                                    viewModel.updateSelectedOption("Follower")
                                }
                            )

                            AudienceSelection(
                                text = "${uiState.totalFollowing} Following",
                                selected = screenState.selectedOption == "Following",
                                onClick = {
                                    updateState { copy(selectedOption = "Following") }
                                    viewModel.updateSelectedOption("Following")
                                }
                            )
                        }
                    }
                }

                item {
                    SearchBar(
                        query = uiState.query,
                        onQueryChange = { viewModel.onSearchQueryChange(it) },
                        placeholder = "Search"
                    )
                }

                if (listToShow.isEmpty()) {
                    item {
                        Box(
                            modifier = Modifier
                        ) { NoDataView(
                            if (screenState.selectedOption == "Follower")
                                "You don’t have any followers yet."
                            else
                                "You are not following anyone yet."
                        ) }
                    }
                    return@LazyColumn
                }

                items(listToShow) { data ->
                    AudienceListItem(
                        data = data,
                        isFollower = screenState.selectedOption == "Follower",
                        onPrimaryClick = {
                            navController.navigate(Routes.CHAT_SCREEN)
                        },
                        onRemoveClick = {
                            if (screenState.selectedOption == "Follower") {
                                updateState { copy(removeDialog = true, selectedUserId = data.id.toString()) }
                            } else {
                                updateState { copy(removeFollowing = true, selectedUserId = data.id.toString()) }
                            }
                        },
                        onFollowBackClick = {
                            // Follow-back logic
                            viewModel.addAndRemoveFollowers(screenState.selectedUserId)
                        }
                    )
                }


                if (!uiState.endReached && uiState.isMoreLoading) {
                    item {
//                        LoaderOverlay(true)
                    }
                }

                if (!uiState.endReached) {
                    item {
                        LaunchedEffect(Unit) {
                            viewModel.loadFollowers()
                        }
                    }
                }

            }
        }


        if (screenState.removeDialog) {
            RemoveParticipantDialog(
                onDismiss = { updateState { copy(removeDialog = false) } },
                onClickRemove = {
                    viewModel.addAndRemoveFollowers(screenState.selectedUserId)
                    updateState {
                        copy(removeToast = true, removeDialog = false)
                    }
                },
                text = "Remove Follower?",
                description = "They won't be notified.",
                iconResId = R.drawable.remove_ic_user,
                buttonText = "Remove"
            )
        }

        if (screenState.removeFollowing) {
            RemoveParticipantDialog(
                onDismiss = { updateState { copy(removeFollowing = false) } },
                onClickRemove = {
                    updateState {
                        copy(removeToast = true, removeFollowing = false)
                    }
                },
                text = "Unfollow User?",
                description = "You will stop seeing their updates.",
                iconResId = R.drawable.remove_ic_user,
                buttonText = "Unfollow"
            )
        }

        if (screenState.removeToast) {
            LaunchedEffect(Unit) {
                delay(1000)
                updateState { copy(removeToast = false) }
            }
            CenterToast(
                onDismiss = { updateState { copy(removeToast = false) } }
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
