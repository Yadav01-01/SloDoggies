package com.bussiness.slodoggiesapp.ui.screens.businessprovider.post.promotion

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
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
import com.bussiness.slodoggiesapp.navigation.Routes
import com.bussiness.slodoggiesapp.ui.component.businessProvider.CustomDropdownBox
import com.bussiness.slodoggiesapp.ui.component.businessProvider.FormHeadingText
import com.bussiness.slodoggiesapp.ui.component.businessProvider.HeadingTextWithIcon
import com.bussiness.slodoggiesapp.ui.component.businessProvider.SubmitButton
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor
import com.bussiness.slodoggiesapp.util.Messages
import com.bussiness.slodoggiesapp.viewModel.businessProvider.PostContentViewModel

@Composable
fun BudgetScreen(navController: NavHostController
) {

    val viewModel: PostContentViewModel = hiltViewModel(
        navController.getBackStackEntry(Routes.POST_SCREEN)
    )
    val uiStateAddCreate by viewModel.uiState.collectAsState()
    var budget by remember { mutableStateOf(uiStateAddCreate.budget ?: "0") }
    val context = LocalContext.current
    Column ( modifier = Modifier.fillMaxSize().background(Color.White)) {

       // var budget by remember { mutableStateOf(0) }

        BackHandler {
            navController.navigate(Routes.POST_SCREEN){
                launchSingleTop = true
                popUpTo(Routes.POST_SCREEN){
                    inclusive = false
                }
            }
        }

        HeadingTextWithIcon(textHeading = "Budget",
            onBackClick = { navController.navigate(Routes.POST_SCREEN){
                launchSingleTop = true
                popUpTo(Routes.POST_SCREEN){
                    inclusive = false
                }
            } })

        HorizontalDivider(modifier = Modifier.fillMaxWidth().height(2.dp).background(PrimaryColor))

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(16.dp), // optional padding
            verticalArrangement = Arrangement.spacedBy(10.dp) // auto spacing between items
        ) {
            item {
                FormHeadingText("Select Budget")
            }

            item {
                val budgetOptions = listOf(
                    "Daily $5" to 5,
                    "Daily $10" to 10,
                    "Daily $15" to 15,
                    "Daily $20" to 20
                )

                CustomDropdownBox(
                    label = if (budget == "0") "Select Budget" else "Daily $$budget",
                    items = budgetOptions.map { it.first }, // show only labels
                    selectedItem = budgetOptions.find { it.second.toString() == budget }?.first ?: "",
                    onItemSelected = { selectedLabel ->
                        val selectedNumber = budgetOptions.find { it.first == selectedLabel }?.second ?: 0
                        budget = selectedNumber.toString() // store as string
                        viewModel.updatebudget(selectedNumber.toString()) // update ViewModel
                    }
                )
            }

            item {
                Row(modifier = Modifier.fillMaxWidth()) {
                    FormHeadingText("Ad Budget", color = PrimaryColor)
                    Spacer(Modifier.weight(1f))
                    FormHeadingText(
                        if (budget == "0") "$0 Daily" else "$$budget Daily",
                        color = PrimaryColor
                    )

                }
            }

            item {
                SubmitButton(
                    modifier = Modifier,
                    buttonText = "Save & Next",
                    onClickButton = {
                       // navController.navigate(Routes.PREVIEW_ADS_SCREEN)
                        if (budget == "0") {
                            Toast.makeText(context, Messages.BUDGET, Toast.LENGTH_SHORT).show()
                        }else{
                            navController.navigate(Routes.PREVIEW_ADS_SCREEN)
                        }

                    }
                )
            }
        }

    }
}