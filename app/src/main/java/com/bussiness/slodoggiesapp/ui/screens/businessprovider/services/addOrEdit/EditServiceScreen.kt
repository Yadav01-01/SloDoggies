package com.bussiness.slodoggiesapp.ui.screens.businessprovider.services.addOrEdit


import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.navigation.Routes
import com.bussiness.slodoggiesapp.ui.component.businessProvider.FormHeadingText
import com.bussiness.slodoggiesapp.ui.component.businessProvider.HeadingTextWithIcon
import com.bussiness.slodoggiesapp.ui.component.businessProvider.InputField
import com.bussiness.slodoggiesapp.ui.component.businessProvider.SubmitButton
import com.bussiness.slodoggiesapp.ui.component.common.MediaUploadSection
import com.bussiness.slodoggiesapp.ui.dialog.ServiceAdEditDialog
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor
import com.bussiness.slodoggiesapp.viewModel.businessProvider.AddServiceViewModel

@Composable
fun EditAddServiceScreen(
    navController: NavHostController,
    type: String,
    viewModel: AddServiceViewModel = hiltViewModel()
) {

    val title by viewModel.title.collectAsState()
    val description by viewModel.description.collectAsState()
    val amount by viewModel.amount.collectAsState()
    var updateDialog by remember { mutableStateOf(false) }

    BackHandler {
        navController.navigate(Routes.SERVICES_SCREEN) {
            launchSingleTop = true
            popUpTo(Routes.SERVICES_SCREEN) {
                inclusive = false
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize().background(Color.White)) {

        HeadingTextWithIcon(textHeading = if (type == "add") "Add Services" else " Edit Service",
            onBackClick = {navController.navigate(Routes.SERVICES_SCREEN) {
                launchSingleTop = true
                popUpTo(Routes.SERVICES_SCREEN) {
                    inclusive = false
                }
            }})

        HorizontalDivider(modifier = Modifier.fillMaxWidth().height(2.dp).background(PrimaryColor))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp).
            background(Color.White)
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

            InputField(input = amount, onValueChange = { viewModel.updateAmount(it) }, placeholder = "Enter amount",keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done)
            )

            Spacer(Modifier.height(15.dp))

            FormHeadingText("Upload Media")

            Spacer(Modifier.height(10.dp))

            MediaUploadSection(maxImages = 10) { uri ->
//                viewModel.addPetImage(uri)
            }

            Spacer(Modifier.height(25.dp))

            SubmitButton(modifier = Modifier, buttonText = if (type ==  "add") "Add Service" else "Save Changes", onClickButton = { updateDialog = true }, buttonTextSize = 15)
        }
        if (updateDialog) {
            if (type == "add") {
                ServiceAdEditDialog(
                    onDismiss = {
                        updateDialog = false
                        navController.navigate(Routes.SERVICES_SCREEN)
                                },
                    iconResId = R.drawable.ic_sucess_p,
                    text = "New Service Added Successfully",
                    description = "Your new service has been added and is now visible to customers."
                )
            }else{
                ServiceAdEditDialog(
                    onDismiss = {
                        updateDialog = false
                        navController.navigate(Routes.SERVICES_SCREEN)
                    },
                    iconResId = R.drawable.ic_sucess_p,
                    text = "Service Details Updated Successfully",
                    description = "Your changes have been saved and are now live."
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun AddServiceScreenPreview() {
    val navController = rememberNavController()

    EditAddServiceScreen(navController = navController, type = "")
}