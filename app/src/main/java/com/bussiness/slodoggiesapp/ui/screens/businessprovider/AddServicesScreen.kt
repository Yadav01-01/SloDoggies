package com.bussiness.slodoggiesapp.ui.screens.businessprovider

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bussiness.slodoggiesapp.navigation.Routes
import com.bussiness.slodoggiesapp.ui.component.businessProvider.FormHeadingText
import com.bussiness.slodoggiesapp.ui.component.businessProvider.InputField
import com.bussiness.slodoggiesapp.ui.component.businessProvider.SubmitButton
import com.bussiness.slodoggiesapp.ui.component.businessProvider.TopHeadingText
import com.bussiness.slodoggiesapp.ui.component.businessProvider.TopStepProgressBar

@Composable
fun AddServiceScreen(navController: NavHostController) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize()) {

        TopHeadingText(textHeading = "Add Services", onBackClick = { navController.popBackStack() })

        TopStepProgressBar(currentStep = 2, totalSteps = 3, modifier = Modifier.fillMaxWidth().height(6.dp))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {

            Spacer(Modifier.height(10.dp))

            FormHeadingText("Service Title")

            Spacer(Modifier.height(10.dp))

            InputField(input = title, onValueChange = { title = it }, placeholder = "Enter title")

            Spacer(Modifier.height(15.dp))

            FormHeadingText("Description")

            Spacer(Modifier.height(10.dp))

            InputField(input = description, onValueChange = { description = it }, placeholder = "Type here")

            Spacer(Modifier.height(15.dp))

            FormHeadingText("Price (\$)")

            Spacer(Modifier.height(10.dp))

            InputField(input = amount, onValueChange = { amount = it }, placeholder = "Enter amount")

            Spacer(Modifier.height(15.dp))

            FormHeadingText("Additional Media")
            
            Spacer(Modifier.height(10.dp))

            UploadPlaceholder()

            Spacer(Modifier.height(25.dp))

        SubmitButton(modifier = Modifier, buttonText = "Add Service", onClickButton = { navController.navigate(Routes.MAIN_SCREEN) }, buttonTextSize = 15)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun AddServiceScreenPreview() {
    val navController = rememberNavController()

    AddServiceScreen(navController = navController)
}