package com.bussiness.slodoggiesapp.ui.screens.commonscreens.settings
import android.os.Build
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
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
import com.bussiness.slodoggiesapp.navigation.Routes
import com.bussiness.slodoggiesapp.ui.component.businessProvider.HeadingTextWithIcon
import com.bussiness.slodoggiesapp.ui.component.common.EventCard
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor
import com.bussiness.slodoggiesapp.util.Messages
import com.bussiness.slodoggiesapp.viewModel.event.EventListViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import java.net.URLEncoder
import java.nio.charset.StandardCharsets


@OptIn(ExperimentalMaterialApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MyEventScreen(navController: NavHostController) {

    val viewModel: EventListViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()
    var isNavigating by remember { mutableStateOf(false) }
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
        if (!isNavigating) {
            isNavigating = true
            navController.popBackStack()
        }
    }

    Column(modifier = Modifier.fillMaxSize().background(Color.White)) {
        HeadingTextWithIcon(textHeading = "My Events", onBackClick = {
            if (!isNavigating) {
                isNavigating = true
                navController.popBackStack()
            }
        })
        HorizontalDivider(thickness = 2.dp, color = PrimaryColor)

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
                        EventCard(event = event, selectedOption = "") {eventData->

                            // navController.navigate(Routes.COMMUNITY_CHAT_SCREEN)
                          eventData?.let {
                                val receiverId = it.user_id.toString() ?: ""
                              val chatId = it.id ?: ""
                                // Get the first image from get_event_image list if available
                                val firstImageUrl = it.get_event_image?.firstOrNull()?.media_path ?: ""
                                Log.d("CommunityChatScreen", "Image URL: $firstImageUrl")
                                val receiverImage = URLEncoder.encode(firstImageUrl, StandardCharsets.UTF_8.toString())
                                val receiverName = URLEncoder.encode(it.event_title ?: "", StandardCharsets.UTF_8.toString())
                                val type = "event"
                                navController.navigate("${Routes.COMMUNITY_CHAT_SCREEN}/$receiverId/$receiverImage/$receiverName/$chatId/$type")
                            }
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
