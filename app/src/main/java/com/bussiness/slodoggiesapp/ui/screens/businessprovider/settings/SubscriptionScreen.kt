package com.bussiness.slodoggiesapp.ui.screens.businessprovider.settings

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.bussiness.slodoggiesapp.data.model.businessProvider.SubscriptionData
import com.bussiness.slodoggiesapp.ui.component.businessProvider.HeadingTextWithIcon
import com.bussiness.slodoggiesapp.ui.component.businessProvider.SubscriptionItem
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor
import com.bussiness.slodoggiesapp.viewModel.subscriptionViewmodel.SubscriptionViewModel

@Composable
fun SubscriptionScreen(navController: NavHostController,
                       viewModel: SubscriptionViewModel = hiltViewModel()) {

    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(uiState.errorMessage) {
        uiState.errorMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            viewModel.clearError()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        HeadingTextWithIcon(
            textHeading = "Subscription",
            onBackClick = { navController.popBackStack() }
        )

        HorizontalDivider(thickness = 2.dp, color = PrimaryColor)

        Spacer(modifier = Modifier.height(10.dp))

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(horizontal = 5.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            itemsIndexed(uiState.plans) { index, item ->
                SubscriptionItem(
                    plan = item,
                    isSelected = uiState.selectedPlanId == item.id,
                    isActivated = uiState.activatedPlanId == item.id,
                    onUpgradeClick = {
                        viewModel.onPlanActivated(item.id)
                    },
                    onCancelClick = {
                        viewModel.onPlanCancelled(item.id)
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
