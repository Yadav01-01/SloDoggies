package com.bussiness.slodoggiesapp.ui.screens.commonscreens.settings

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.bussiness.slodoggiesapp.model.businessProvider.AdTopUpTransaction
import com.bussiness.slodoggiesapp.ui.component.businessProvider.AdTopUpBtn
import com.bussiness.slodoggiesapp.ui.component.businessProvider.ChoosePostTypeButton
import com.bussiness.slodoggiesapp.ui.component.businessProvider.HeadingTextWithIcon
import com.bussiness.slodoggiesapp.ui.component.businessProvider.StatusBox
import com.bussiness.slodoggiesapp.ui.component.businessProvider.UpgradeBtn
import com.bussiness.slodoggiesapp.ui.dialog.ReceiptDialog
import com.bussiness.slodoggiesapp.ui.screens.commonscreens.transactioncontent.AdTopUpScreen
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor

@Composable
fun TransactionScreen(navController: NavHostController) {
    val sample = listOf(
        AdTopUpTransaction("Ad Top-Up", "$25.00", "ATU-82341", "08/10/2025", "Successful"),
        AdTopUpTransaction("Ad Top-Up", "$10.00", "ATU-82342", "07/10/2025", "Pending"),
        AdTopUpTransaction("Ad Top-Up", "$50.00", "ATU-82343", "06/10/2025", "Failed")
    )

    val subscriptionsSample = listOf(
        AdTopUpTransaction("Standard Plan", "$25.00", "SUB-82341", "09/15/2025", "Renewed"),
    )
    var isNavigating by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf("Ad Top-Ups") }
    var selectedTransaction by remember { mutableStateOf<AdTopUpTransaction?>(null) }

    BackHandler {
        if (!isNavigating) {
            isNavigating = true
            navController.popBackStack()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        HeadingTextWithIcon(textHeading = "Transactions", onBackClick = {  if (!isNavigating) {
            isNavigating = true
            navController.popBackStack()
        } })

        HorizontalDivider(thickness = 2.dp, color = PrimaryColor)

        // Make LazyColumn take the remaining space
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(Color.White)
                .padding(horizontal = 4.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp, horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    listOf("Ad Top-Ups", "Subscriptions").forEach { label ->
                        ChoosePostTypeButton(
                            modifier = Modifier.weight(1f),
                            text = label,
                            isSelected = selectedOption == label,
                            onClick = { selectedOption = label }
                        )
                    }
                }
            }
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    StatusBox(title = "Total Spent", value = "$165.00", modifier = Modifier.weight(1f))
                    StatusBox(title = "Current Plan", value = "Standard", modifier = Modifier.weight(1f))
                    StatusBox(title = if (selectedOption == "Ad Top-Ups")  "End Date" else "Next Renewal", value = "10/12/2025", modifier = Modifier.weight(1f))
                }
            }
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    StatusBox(title = "Successful", value = "8", modifier = Modifier.weight(1f))
                    StatusBox(title = "Pending/Failed", value = "2", modifier = Modifier.weight(1f))
                }
            }

            when (selectedOption) {
                "Ad Top-Ups" -> {
                    items(sample, key = { it.id }) { tx ->
                        AdTopUpScreen(
                            title = tx.title,
                            amount = tx.amount,
                            id = tx.id,
                            date = tx.date,
                            statusText = tx.statusText,
                            onReceiptClick = { selectedTransaction = tx },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                "Subscriptions" -> {
                    // replace with your actual subscriptions list
                    items(subscriptionsSample, key = { it.id }) { tx ->
                        AdTopUpScreen(
                            title = tx.title,
                            amount = tx.amount,
                            id = tx.id,
                            date = tx.date,
                            statusText = tx.statusText,
                            onReceiptClick = { selectedTransaction = tx },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }


        }

        // This Row will now stay at the bottom because LazyColumn used weight(1f)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            AdTopUpBtn(text = "Ad Top-Up", onClick = {  }, modifier = Modifier.weight(1f))
            UpgradeBtn(text = "Upgrade Plan", onClick = {  }, modifier = Modifier.weight(1f))
        }
    }
    selectedTransaction?.let { tx ->
        ReceiptDialog(
            onDismiss = { selectedTransaction = null },
            data = tx
        )
    }
}
