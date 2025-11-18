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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.navigation.Routes
import com.bussiness.slodoggiesapp.ui.component.businessProvider.CategoryInputField
import com.bussiness.slodoggiesapp.ui.component.businessProvider.DayTimeSelector
import com.bussiness.slodoggiesapp.ui.component.businessProvider.FormHeadingText
import com.bussiness.slodoggiesapp.ui.component.businessProvider.InputField
import com.bussiness.slodoggiesapp.ui.component.businessProvider.SubmitButton
import com.bussiness.slodoggiesapp.ui.component.businessProvider.TopHeadingText
import com.bussiness.slodoggiesapp.ui.component.businessProvider.TopStepProgressBar
import com.bussiness.slodoggiesapp.ui.component.common.MediaUploadSection
import com.bussiness.slodoggiesapp.viewModel.businessProvider.BusinessRegistrationViewModel

@Composable
fun BusinessRegistrationScreen(navController: NavHostController,viewModel: BusinessRegistrationViewModel = hiltViewModel()){

    val state by viewModel.uiState.collectAsState()
    var isNavigating by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize().background(Color.White)) {

        TopHeadingText(textHeading = stringResource(R.string.business_registration), onBackClick = {
            if (!isNavigating) {
                isNavigating = true
                navController.popBackStack() }
        })

        TopStepProgressBar(currentStep = 1, totalSteps = 3, modifier = Modifier)

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .background(Color.White),
            verticalArrangement = Arrangement.spacedBy(12.dp) // Handles spacing between items
        ) {
            item {
                FormHeadingText(stringResource(R.string.business_name))
                InputField(
                    input = state.name,
                    onValueChange = { viewModel.updateName(it) },
                    placeholder = stringResource(R.string.enter_name)
                )
            }

            item {
                FormHeadingText(stringResource(R.string.email))
                InputField(
                    input = state.email,
                    onValueChange = { viewModel.updateEmail(it) },
                    placeholder = stringResource(R.string.enter_email)
                )
            }

            item {
                FormHeadingText(stringResource(R.string.Upload_business_logo))
                Spacer(Modifier.height(5.dp))
                MediaUploadSection(maxImages = 1) { uri ->
                    // viewModel.addPetImage(uri)
                }
            }

            item {
                FormHeadingText(stringResource(R.string.Category))
                CategoryInputField(
                    categories = state.categories,
                    onCategoryAdded = { viewModel.addCategory(it) },
                    onCategoryRemoved = { viewModel.removeCategory(it) }
                )
            }

            item {
                FormHeadingText(stringResource(R.string.Business_address))
                InputField(
                    input = state.businessAddress,
                    onValueChange = { viewModel.updateBusinessAddress(it) },
                    placeholder = stringResource(R.string.Enter_Business_Address)
                )
            }

            item {
                FormHeadingText(stringResource(R.string.area_sector))
                InputField(
                    placeholder = stringResource(R.string.enter_area_sector),
                    input = state.city,
                    onValueChange = { viewModel.updateCity(it) }
                )
            }

            item {
                FormHeadingText(stringResource(R.string.landmark))
                InputField(
                    placeholder = stringResource(R.string.enter_landmark),
                    input = state.state,
                    onValueChange = { viewModel.updateState(it) }
                )
            }

            item {
                FormHeadingText(stringResource(R.string.zip_code))
                InputField(
                    placeholder = stringResource(R.string.enter_zip_code),
                    input = state.zipCode ,
                    onValueChange = { viewModel.updateZip(it) }
                )
            }

            item {
                FormHeadingText(stringResource(R.string.website_))
                InputField(
                    input = state.website,
                    onValueChange = { viewModel.updateWebsite(it) },
                    placeholder = "URL"
                )
            }

            item {
                FormHeadingText(stringResource(R.string.contact_number))
                InputField(
                    input = state.contact,
                    onValueChange = { viewModel.updateContact(it) },
                    placeholder = "Enter Contact"
                )
            }

            item {
                FormHeadingText(stringResource(R.string.available_days))
                // Add your custom available days UI here
                DayTimeSelector(
                    onDone = { selectedDays, from, to ->
                        println("Selected Days: $selectedDays")
                        println("From: $from")
                        println("To: $to")
                    }
                )
            }

            item {
                FormHeadingText(stringResource(R.string.Upload_verification_docs))
                MediaUploadSection(maxImages = 6) { uri ->
                    // viewModel.addPetImage(uri)
                }
            }

            item {
                SubmitButton(
                    modifier = Modifier.fillMaxWidth(),
                    buttonText = stringResource(R.string.submit_n_next),
                    onClickButton = { navController.navigate(Routes.ADD_SERVICE) },
                    buttonTextSize = 15
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewBusinessRegistrationScreen() {
    // Use a dummy NavController for preview purposes
    val navController = rememberNavController()

    BusinessRegistrationScreen(navController = navController)
}
