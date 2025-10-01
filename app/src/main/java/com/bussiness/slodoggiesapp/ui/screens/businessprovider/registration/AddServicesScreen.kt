package com.bussiness.slodoggiesapp.ui.screens.businessprovider.registration

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.navigation.Routes
import com.bussiness.slodoggiesapp.ui.component.businessProvider.FormHeadingText
import com.bussiness.slodoggiesapp.ui.component.businessProvider.InputField
import com.bussiness.slodoggiesapp.ui.component.businessProvider.SubmitButton
import com.bussiness.slodoggiesapp.ui.component.businessProvider.TopHeadingText
import com.bussiness.slodoggiesapp.ui.component.businessProvider.TopHeadingTextWithSkip
import com.bussiness.slodoggiesapp.ui.component.businessProvider.TopStepProgressBar
import com.bussiness.slodoggiesapp.ui.component.common.MediaUploadSection
import com.bussiness.slodoggiesapp.ui.dialog.AddedServiceDialog
import com.bussiness.slodoggiesapp.ui.dialog.SubscriptionWarningDialog
import com.bussiness.slodoggiesapp.viewModel.businessProvider.AddServiceViewModel

@Composable
fun AddServiceScreen(
    navController: NavHostController,
    viewModel: AddServiceViewModel = hiltViewModel()
) {

    val title by viewModel.title.collectAsState()
    val description by viewModel.description.collectAsState()
    val amount by viewModel.amount.collectAsState()
    var isNavigating by remember { mutableStateOf(false) }
    var addedServiceDialog by remember { mutableStateOf(false) }
    var subscribeDisclaimer by remember { mutableStateOf(true) }

    Column(modifier = Modifier.fillMaxSize().background(color = Color.White)) {

        TopHeadingTextWithSkip(textHeading = "Add Services", onBackClick = {
            if (!isNavigating) {
                isNavigating = true
                navController.popBackStack() }
        },
            onSkipClick = { navController.navigate(Routes.NOTIFICATION_PERMISSION_SCREEN) } )

        TopStepProgressBar(currentStep = 2, totalSteps = 3, modifier = Modifier.fillMaxWidth())

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .background(Color.White),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            // Service Title
            item { FormHeadingText("Service Title") }
            item {
                InputField(
                    input = title,
                    onValueChange = { viewModel.updateTitle(it) },
                    placeholder = "Enter title"
                )
            }

            // Description
            item { FormHeadingText("Description") }
            item {
                InputField(
                    input = description,
                    onValueChange = { viewModel.updateDescription(it) },
                    placeholder = "Type here"
                )
            }

            // Price
            item { FormHeadingText("Price (\$)") }
            item {
                InputField(
                    input = amount,
                    onValueChange = { viewModel.updateAmount(it) },
                    placeholder = "Enter amount"
                )
            }

            // Additional Media
            item { FormHeadingText("Additional Media") }
            item {
                MediaUploadSection(maxImages = 10) { uri ->
                    // viewModel.addPetImage(uri)
                }
            }

            // Bottom Spacer
            item { Spacer(modifier = Modifier.height(25.dp)) }

            // Submit Button
            item {
                SubmitButton(
                    modifier = Modifier.fillMaxWidth(),
                    buttonText = "Add Service",
                    onClickButton = {
                       addedServiceDialog = true
                    },
                    buttonTextSize = 15
                )
            }
        }

    }
    if (addedServiceDialog){
        AddedServiceDialog(
            onDismiss = { addedServiceDialog = false },
            onAddAnotherClick = {
                viewModel.updateTitle("")
                viewModel.updateDescription("")
                viewModel.updateAmount("")
                addedServiceDialog = false
                                },
            onGoToHomeClick = {   navController.navigate(Routes.NOTIFICATION_PERMISSION_SCREEN) }
        )
    }
    if (subscribeDisclaimer){
        SubscriptionWarningDialog(
            onDismiss = { subscribeDisclaimer = false },
            icon = R.drawable.caution_ic,
            heading = "Subscription Alert!",
            desc1 = stringResource(R.string.subscription_desc),
            buttonText = "Proceed",
            onClick = { subscribeDisclaimer = false }
        )
    }

}


@Preview(showBackground = true)
@Composable
fun AddServiceScreenPreview() {
    val navController = rememberNavController()

    AddServiceScreen(navController = navController)
}