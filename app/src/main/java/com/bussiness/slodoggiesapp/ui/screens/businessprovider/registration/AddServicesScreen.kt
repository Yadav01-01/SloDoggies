package com.bussiness.slodoggiesapp.ui.screens.businessprovider.registration

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bussiness.slodoggiesapp.navigation.Routes
import com.bussiness.slodoggiesapp.ui.component.businessProvider.FormHeadingText
import com.bussiness.slodoggiesapp.ui.component.businessProvider.InputField
import com.bussiness.slodoggiesapp.ui.component.businessProvider.SubmitButton
import com.bussiness.slodoggiesapp.ui.component.businessProvider.TopHeadingText
import com.bussiness.slodoggiesapp.ui.component.businessProvider.TopStepProgressBar
import com.bussiness.slodoggiesapp.ui.component.common.MediaUploadSection
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

    Column(modifier = Modifier.fillMaxSize().background(color = Color.White)) {

        TopHeadingText(textHeading = "Add Services", onBackClick = {
            if (!isNavigating) {
                isNavigating = true
                navController.popBackStack() }
        })

        TopStepProgressBar(currentStep = 2, totalSteps = 3, modifier = Modifier.fillMaxWidth())

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
                .background(Color.White)
        ) {

            Spacer(Modifier.height(10.dp))

            FormHeadingText("Service Title")

            Spacer(Modifier.height(10.dp))

            InputField(input = title, onValueChange = { viewModel.updateTitle(it) }, placeholder = "Enter title")

            Spacer(Modifier.height(15.dp))

            FormHeadingText("Description")

            Spacer(Modifier.height(10.dp))

            InputField(input = description, onValueChange = { viewModel.updateDescription(it) }, placeholder = "Type here")

            Spacer(Modifier.height(15.dp))

            FormHeadingText("Price (\$)")

            Spacer(Modifier.height(10.dp))

            InputField(input = amount, onValueChange = { viewModel.updateAmount(it) }, placeholder = "Enter amount")

            Spacer(Modifier.height(15.dp))

            FormHeadingText("Additional Media")
            
            Spacer(Modifier.height(10.dp))

            MediaUploadSection(maxImages = 10) { uri ->
//                viewModel.addPetImage(uri)
            }

            Spacer(Modifier.height(25.dp))

        SubmitButton(modifier = Modifier, buttonText = "Add Service", onClickButton = { navController.navigate(Routes.NOTIFICATION_PERMISSION_SCREEN) }, buttonTextSize = 15)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun AddServiceScreenPreview() {
    val navController = rememberNavController()

    AddServiceScreen(navController = navController)
}