package com.bussiness.slodoggiesapp.ui.screens.commonscreens.message

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.model.main.UserType
import com.bussiness.slodoggiesapp.navigation.Routes
import com.bussiness.slodoggiesapp.ui.component.businessProvider.HeadingTextWithIcon
import com.bussiness.slodoggiesapp.ui.component.businessProvider.SearchBar
import com.bussiness.slodoggiesapp.ui.component.common.MessageItem
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor
import com.bussiness.slodoggiesapp.util.SessionManager
import com.bussiness.slodoggiesapp.viewModel.common.MessageViewModel

@Composable
fun MessageScreen(
    navController: NavHostController,
    viewModel: MessageViewModel = hiltViewModel()
) {
    val allMessages by viewModel.messages.collectAsState()
    val query by viewModel.query.collectAsState()
    val sessionManager = SessionManager.getInstance(LocalContext.current)
    var canNavigate by remember { mutableStateOf(true) }

    BackHandler(enabled = true) {
        if (canNavigate) {
            canNavigate = false
            navController.navigate(Routes.HOME_SCREEN) {
                launchSingleTop = true
                popUpTo(Routes.HOME_SCREEN) {
                    inclusive = true
                }
            }
        }
    }


    // Filtered list based on search query
    val filteredMessages = allMessages.filter {
        it.username.contains(query, ignoreCase = true) ||
                it.description.contains(query, ignoreCase = true)
    }


    Box(modifier = Modifier.fillMaxSize().background(Color.White)) {
        Column(modifier = Modifier.fillMaxSize()) {
            HeadingTextWithIcon(
                textHeading = "Message",
                onBackClick = {  if (canNavigate) {
                    canNavigate = false
                    navController.navigate(Routes.HOME_SCREEN) {
                        launchSingleTop = true
                        popUpTo(Routes.HOME_SCREEN) {
                            inclusive = false
                        }
                    }
                } }
            )

            HorizontalDivider(thickness = 2.dp, color = PrimaryColor)

            Spacer(Modifier.height(10.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(horizontal = 5.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                item {
                        SearchBar(
                            query = query,
                            onQueryChange = { viewModel.updateQuery(it) },
                            placeholder = "Search"
                        )
                        Spacer(Modifier.height(10.dp))
                }

                items(filteredMessages) { message ->
                    MessageItem(
                        message = message,
                        onItemClick = { navController.navigate(Routes.CHAT_SCREEN) }
                    )
                }
            }
        }

        // FAB fixed at bottom-right, outside the LazyColumn
        if (sessionManager.getUserType() == UserType.BUSINESS_PROVIDER) {
            NewMessageButton(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 10.dp, end = 10.dp),
                onClick = { navController.navigate(Routes.NEW_MESSAGE_SCREEN) }
            )
        }
    }
}


@Composable
fun NewMessageButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    FloatingActionButton(
        onClick = onClick,
        modifier = modifier.size(51.dp),
        containerColor = Color.White,
        contentColor = Color.White
    ) {
        Image(
            painter = painterResource(R.drawable.new_msg_ic),
            contentDescription = "New Message",
            modifier = Modifier.size(51.dp),
        )
    }
}
