package com.bussiness.slodoggiesapp.ui.screens.petowner.event

import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.data.model.businessProvider.Event
import com.bussiness.slodoggiesapp.navigation.Routes
import com.bussiness.slodoggiesapp.ui.component.businessProvider.ChoosePostTypeButton
import com.bussiness.slodoggiesapp.ui.component.businessProvider.HeadingTextWithIcon
import com.bussiness.slodoggiesapp.ui.component.common.EventCard
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor
import com.bussiness.slodoggiesapp.util.Messages
import com.bussiness.slodoggiesapp.viewModel.event.EventListViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter

@OptIn(ExperimentalMaterialApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EventScreen(navController: NavHostController) {


    val viewModel: EventListViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()
    val selectedOption by viewModel.selectedOption
    val isRefreshing = uiState.isRefreshing

    val pullRefreshState = rememberPullRefreshState(refreshing = isRefreshing, onRefresh = {
        viewModel.refreshList()
    })

    val listState = rememberLazyListState()

    // Pagination: Load next page when scroll to bottom
    LaunchedEffect(listState) {
        snapshotFlow {
            val lastVisibleItem = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            val totalItems = listState.layoutInfo.totalItemsCount
            lastVisibleItem to totalItems
        }
            .distinctUntilChanged()
            .filter { (lastVisible, total) -> lastVisible >= total - 1 }
            .collect {
                viewModel.loadNextPage()
            }
    }



    BackHandler {
        navController.navigate(Routes.HOME_SCREEN){
            launchSingleTop = true
            popUpTo(Routes.HOME_SCREEN){
                inclusive = true
            }
        }
    }

    Column(modifier = Modifier
            .fillMaxSize()
            .background(Color.White)) {
        HeadingTextWithIcon(
            textHeading = "My Events",
            onBackClick = { navController.navigate(Routes.HOME_SCREEN){
                launchSingleTop = true
                popUpTo(Routes.HOME_SCREEN){
                    inclusive = true
                }
            }}
        )
        HorizontalDivider(thickness = 2.dp, color = PrimaryColor)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp, horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            listOf("My Events", "Saved").forEach { label ->
                ChoosePostTypeButton(
                    modifier = Modifier.weight(1f),
                    text = label,
                    isSelected = selectedOption == label,
                    onClick = { viewModel.selectOption(label) }
                )
            }
        }

        Spacer(Modifier.height(5.dp))

        Box(modifier = Modifier.fillMaxSize().pullRefresh(pullRefreshState)) {
            val events = uiState.data?.data.orEmpty()
            if (events.isEmpty() && !isRefreshing) {
                // Centered "No event found" message when list is empty
                // Make it scrollable so pull-to-refresh works
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = Messages.EVENT_NO,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Medium,
                            fontSize = 20.sp
                        ),
                        fontFamily = FontFamily(Font(R.font.outfit_medium)),
                        color = Color(0xFF221B22)
                    )
                }
            } else {
                LazyColumn(
                    state = listState,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                        .padding(horizontal = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    items(events, key = { it.id }) { event ->
                        EventCard(event = event, selectedOption = selectedOption) {
                            navController.navigate(Routes.COMMUNITY_CHAT_SCREEN)
                        }
                    }
                    // Show bottom loader while next page is loading
                    if (viewModel.isLoadingPage) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                    }
                }
            }
            PullRefreshIndicator(
                refreshing = isRefreshing,
                state = pullRefreshState,
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }
    }
}
