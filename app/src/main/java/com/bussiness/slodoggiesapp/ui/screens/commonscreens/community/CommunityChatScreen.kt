package com.bussiness.slodoggiesapp.ui.screens.commonscreens.community

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.bussiness.slodoggiesapp.model.businessProvider.Community
import com.bussiness.slodoggiesapp.navigation.Routes
import com.bussiness.slodoggiesapp.ui.component.common.CommunityHeader
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor

@Composable
fun CommunityChatScreen(navController: NavHostController) {

    Column(
        modifier = Modifier.fillMaxSize().background(Color.White)
    ) {
        // Header with back and menu actions
        CommunityHeader(
            community = comData,
            onBackClick = { navController.popBackStack() },
            onViewProfileClick = { navController.navigate(Routes.COMMUNITY_PROFILE_SCREEN) }
        )

        // Divider line below header
        HorizontalDivider(thickness = 1.5.dp, color = PrimaryColor)

        Spacer(modifier = Modifier.height(10.dp))

        // Scrollable chat content placeholder
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {

        }
    }
}
 var comData = Community(id = "1", name = "Event Community 1", membersCount = 22, imageUrl = " ")