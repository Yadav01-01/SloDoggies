package com.bussiness.slodoggiesapp.ui.screens.businessprovider.post.promotion

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.bussiness.slodoggiesapp.navigation.Routes
import com.bussiness.slodoggiesapp.ui.component.businessProvider.CustomDropdownBox
import com.bussiness.slodoggiesapp.ui.component.businessProvider.FormHeadingText
import com.bussiness.slodoggiesapp.ui.component.businessProvider.HeadingTextWithIcon
import com.bussiness.slodoggiesapp.ui.component.businessProvider.SubmitButton
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor

@Composable
fun BudgetScreen(navController: NavHostController) {

    Column ( modifier = Modifier.fillMaxSize().background(Color.White)) {

        var budget by remember { mutableStateOf("") }

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
                CustomDropdownBox(
                    label = budget.ifEmpty { "Select Budget" },
                    items = listOf("$42 Daily", "$52 Daily", "$62 Daily", "$72 Daily"),
                    selectedItem = budget,
                    onItemSelected = { budget = it }
                )
            }

            item {
                Row(modifier = Modifier.fillMaxWidth()) {
                    FormHeadingText("Ad Budget", color = PrimaryColor)
                    Spacer(Modifier.weight(1f))
                    FormHeadingText(budget.ifEmpty { "\$00 Daily" }, color = PrimaryColor)
                }
            }

            item {
                SubmitButton(
                    modifier = Modifier,
                    buttonText = "Save & Next",
                    onClickButton = { navController.navigate(Routes.PREVIEW_ADS_SCREEN) }
                )
            }
        }

    }
}