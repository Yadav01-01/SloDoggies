package com.bussiness.slodoggiesapp.ui.screens.businessprovider.services.addOrEdit


import android.widget.Toast
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
import com.bussiness.slodoggiesapp.ui.component.common.createMultipartList
import com.bussiness.slodoggiesapp.ui.dialog.ServiceAdEditDialog
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor
import com.bussiness.slodoggiesapp.viewModel.businessProvider.AddServiceViewModel
import com.bussiness.slodoggiesapp.viewModel.businessProvider.ServiceEvent

@Composable
fun EditAddServiceScreen(
    navController: NavHostController,
    type: String,
    viewModel: AddServiceViewModel = hiltViewModel()
) {

    val state by viewModel.uiState.collectAsState()
    val selectedPhoto by viewModel.selectedPhoto.collectAsState()
    val context = LocalContext.current

    // Listen to ViewModel events
    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is ServiceEvent.ShowToast ->
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()

                ServiceEvent.Success -> {
                    viewModel.openAddedServiceDialog()
                }
            }
        }
    }

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

            InputField(input = state.title, onValueChange = { viewModel.updateTitle(it) }, placeholder = "Enter title")

            Spacer(Modifier.height(15.dp))

            FormHeadingText("Description")

            Spacer(Modifier.height(10.dp))

            InputField(input = state.description, onValueChange = { viewModel.updateDescription(it) }, placeholder = "Type here")

            Spacer(Modifier.height(15.dp))

            FormHeadingText("Price (\$)")

            Spacer(Modifier.height(10.dp))

            InputField(input = state.amount, onValueChange = { viewModel.updateAmount(it) }, placeholder = "Enter amount",keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done)
            )

            Spacer(Modifier.height(15.dp))

            FormHeadingText("Upload Media")

            Spacer(Modifier.height(10.dp))

            MediaUploadSection(maxImages = 6) { uri ->
                viewModel.setSelectedPhoto(uri)
            }

            Spacer(Modifier.height(25.dp))

            val images = createMultipartList(
                context = context,
                uris = listOfNotNull(selectedPhoto),
                keyName = "images[]"
            )

            SubmitButton(modifier = Modifier, buttonText = if (type ==  "add") "Add Service" else "Save Changes",
                onClickButton = { viewModel.addOrUpdateService(serviceId = "",type = "updateService",images) }, buttonTextSize = 15)
        }

        if (state.addedServiceDialog) {
            if (type == "add") {
                ServiceAdEditDialog(
                    onDismiss = {
                        viewModel.closeAddedServiceDialog()
                        navController.navigate(Routes.SERVICES_SCREEN)
                                },
                    iconResId = R.drawable.ic_sucess_p,
                    text = "New Service Added Successfully",
                    description = "Your new service has been added and is now visible to customers."
                )
            }else{
                ServiceAdEditDialog(
                    onDismiss = {
                        viewModel.closeAddedServiceDialog()
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