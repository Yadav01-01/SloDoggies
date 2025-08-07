package com.bussiness.slodoggiesapp.ui.screens.businessprovider

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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.bussiness.slodoggiesapp.model.businessProvider.SubscriptionData
import com.bussiness.slodoggiesapp.ui.component.businessProvider.HeadingTextWithIcon
import com.bussiness.slodoggiesapp.ui.component.businessProvider.SubscriptionItem
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor

@Composable
fun SubscriptionScreen(navController: NavHostController) {
    var selectedIndex by remember { mutableStateOf(-1) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        HeadingTextWithIcon(
            textHeading = "Subscription",
            onBackClick = { navController.popBackStack() }
        )

        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp)
                .background(PrimaryColor)
        )

        Spacer(modifier = Modifier.height(10.dp))

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(horizontal = 5.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            itemsIndexed(subsDataList) { index, item ->
                SubscriptionItem(
                    subscriptionItem = item.copy(
                        isSelected = index == selectedIndex,
                        isActivated = index == selectedIndex
                    ),
                    onUpgradeClick = {
                        selectedIndex = index
                    },
                    onCancelClick = {
                        if (selectedIndex == index) {
                            selectedIndex = -1
                        }
                    }
                )
            }
        }
    }
}


val subsDataList = listOf(
    SubscriptionData(
        planName = "Standard",
        price = "$49",
        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit",
        isSelected = true,
        features = listOf("Feature 1", "Feature 2", "Feature 3"),
        isActivated = true
    ),
    SubscriptionData(
        planName = "Premium",
        price = "$99",
        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit",
        isSelected = false,
        features = listOf("Feature 1", "Feature 2", "Feature 3"),
        isActivated = false
    ),
    SubscriptionData(
        planName = "Basic",
        price = "$19",
        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit",
        isSelected = false,
        features = listOf("Feature 1", "Feature 2"),
        isActivated = false
    )
)
