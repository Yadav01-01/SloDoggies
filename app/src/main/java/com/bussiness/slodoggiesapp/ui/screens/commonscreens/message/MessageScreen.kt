package com.bussiness.slodoggiesapp.ui.screens.commonscreens.message

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.bussiness.slodoggiesapp.model.main.UserType
import com.bussiness.slodoggiesapp.navigation.Routes
import com.bussiness.slodoggiesapp.ui.component.businessProvider.HeadingTextWithIcon
import com.bussiness.slodoggiesapp.ui.component.businessProvider.SearchBar
import com.bussiness.slodoggiesapp.ui.component.common.MessageItem
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor
import com.bussiness.slodoggiesapp.util.SessionManager
import com.bussiness.slodoggiesapp.viewModel.common.MessageViewModel

@Composable
fun MessageScreen(navController: NavHostController, viewModel: MessageViewModel = hiltViewModel()) {

    val allMessages by viewModel.messages.collectAsState()
    val query by viewModel.query.collectAsState()
    val sessionManager = SessionManager.getInstance(LocalContext.current)
    // Filtered list based on search query
    val filteredMessages = allMessages.filter {
        it.username.contains(query, ignoreCase = true) || it.description.contains(query, ignoreCase = true)
    }

    Column(modifier = Modifier.fillMaxSize().background(Color.White)) {

        HeadingTextWithIcon(textHeading = "Messages", onBackClick = { navController.popBackStack() })

        HorizontalDivider(modifier = Modifier.fillMaxWidth().height(2.dp).background(PrimaryColor))

        Spacer(Modifier.height(10.dp))

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(horizontal = 5.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            item {
                if (sessionManager.getUserType() == UserType.BUSINESS_PROVIDER){
                    SearchBar(query = query, onQueryChange = { viewModel.updateQuery(it) }, placeholder = "Search")
                    Spacer(Modifier.height(10.dp))
                }
            }

            items(filteredMessages) { message ->
                MessageItem(message = message, onItemClick = { navController.navigate(Routes.CHAT_SCREEN) })
            }
        }

    }
}