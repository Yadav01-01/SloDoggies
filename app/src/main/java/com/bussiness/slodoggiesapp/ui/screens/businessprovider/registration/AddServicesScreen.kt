package com.bussiness.slodoggiesapp.ui.screens.businessprovider.registration

import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.navigation.Routes
import com.bussiness.slodoggiesapp.ui.component.businessProvider.FormHeadingText
import com.bussiness.slodoggiesapp.ui.component.businessProvider.InputField
import com.bussiness.slodoggiesapp.ui.component.businessProvider.SubmitButton
import com.bussiness.slodoggiesapp.ui.component.businessProvider.TopHeadingTextWithSkip
import com.bussiness.slodoggiesapp.ui.component.businessProvider.TopStepProgressBar
import com.bussiness.slodoggiesapp.ui.component.common.MediaUploadSectionUrlUri
import com.bussiness.slodoggiesapp.ui.dialog.AddedServiceDialog
import com.bussiness.slodoggiesapp.ui.dialog.SubscriptionWarningDialog
import com.bussiness.slodoggiesapp.viewModel.businessProvider.AddServiceViewModel


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddServiceScreen(navController: NavHostController) {

    val viewModel: AddServiceViewModel = hiltViewModel()
    val state by viewModel.uiStateServices.collectAsState()
    var addedServiceDialog by remember { mutableStateOf(false) }
    var subscribeDisclaimer by remember { mutableStateOf(true) }
    val context = LocalContext.current
    var isNavigating by remember { mutableStateOf(false) }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        TopHeadingTextWithSkip(
            textHeading = "Add Services",
            onBackClick = {
                if (!isNavigating) {
                    isNavigating = true
                    navController.popBackStack()
                }
            },
            onSkipClick = {
                navController.navigate(Routes.NOTIFICATION_PERMISSION_SCREEN)
            }
        )

        TopStepProgressBar(
            currentStep = 2,
            totalSteps = 3,
            modifier = Modifier.fillMaxWidth()
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .background(Color.White),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            // Title
            item { FormHeadingText("Service Title") }
            item {
                InputField(
                    input = state.service_title?:"",
                    onValueChange = viewModel::updateTitle,
                    placeholder = "Enter title"
                )
            }

            // Description
            item { FormHeadingText("Description") }
            item {
                InputField(
                    input = state.description?:"",
                    onValueChange = viewModel::updateDescription,
                    placeholder = "Type here"
                )
            }

            // Price
            item { FormHeadingText("Price (\$)") }
            item {
                InputField(
                    input = state.price?:"",
                    onValueChange = viewModel::updateAmount,
                    placeholder = "Enter amount"
                )
            }

            // Media Upload
            item { FormHeadingText("Additional Media") }
            item {
                Log.d("EditService", "Images: ${state.service_image?.size}")
                val images = state.service_image ?: emptyList()

                MediaUploadSectionUrlUri(
                    maxImages = 6,
                    imageList = images.toMutableList(), // valid
                    onMediaSelected = { viewModel.addPhoto(it) },
                    onMediaUnSelected = { viewModel.removePhoto(it) },
                    type = "image"
                )
            }

            item { Spacer(modifier = Modifier.height(25.dp)) }

            // Submit Button
            item {
                SubmitButton(
                    modifier = Modifier.fillMaxWidth(),
                    buttonText = "Add Service",
                    buttonTextSize = 15,
                    onClickButton = {
                        viewModel.addOrUpdateService(context=context,
                            onError = {
                            Toast.makeText(context,it,Toast.LENGTH_SHORT).show()
                        }, onSuccess = {
                            addedServiceDialog = true
                        })
                    }
                )
            }
        }
    }

    // Added Service Dialog
    if (addedServiceDialog) {
        AddedServiceDialog(
            onDismiss = { addedServiceDialog = false },
            onAddAnotherClick = {
                viewModel.refresh()
                addedServiceDialog = false
            },
            onGoToHomeClick = {
                navController.navigate(Routes.NOTIFICATION_PERMISSION_SCREEN)
            }
        )
    }

    // Subscription Dialog
    if (subscribeDisclaimer) {
        SubscriptionWarningDialog(
            icon = R.drawable.caution_ic,
            heading = "Subscription Alert!",
            desc1 = stringResource(R.string.subscription_desc),
            buttonText = "Proceed",
            onDismiss = { subscribeDisclaimer=false },
            onClick = { subscribeDisclaimer=false }
        )
    }

}



@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun AddServiceScreenPreview() {
    val navController = rememberNavController()

    AddServiceScreen(navController = navController)
}