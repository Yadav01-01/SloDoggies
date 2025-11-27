package com.bussiness.slodoggiesapp.ui.screens.businessprovider.services.addOrEdit


import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
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
import com.bussiness.slodoggiesapp.data.newModel.servicelist.Data
import com.bussiness.slodoggiesapp.navigation.Routes
import com.bussiness.slodoggiesapp.ui.component.businessProvider.FormHeadingText
import com.bussiness.slodoggiesapp.ui.component.businessProvider.HeadingTextWithIcon
import com.bussiness.slodoggiesapp.ui.component.businessProvider.InputField
import com.bussiness.slodoggiesapp.ui.component.businessProvider.SubmitButton
import com.bussiness.slodoggiesapp.ui.component.common.MediaUploadSectionUrlUri
import com.bussiness.slodoggiesapp.ui.dialog.ServiceAdEditDialog
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor
import com.bussiness.slodoggiesapp.viewModel.businessProvider.AddServiceViewModel
import com.google.gson.Gson

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EditAddServiceScreen(
    navController: NavHostController,
    type: String
) {
    val viewModel: AddServiceViewModel = hiltViewModel()

    var addedServiceDialog by remember { mutableStateOf(false) }

    val context = LocalContext.current

    val model: Data? = if (type.isNotBlank()) {
        val jsonString = Uri.decode(type)
        Gson().fromJson(jsonString, Data::class.java)
    } else null


    LaunchedEffect(model) {
        viewModel.updateData(model)
    }


    val uiStateServices by viewModel.uiStateServices.collectAsState()
    val selectedPhotos by viewModel.selectedPhotos.collectAsState()


    BackHandler {
        navController.navigate(Routes.SERVICES_SCREEN) {
            launchSingleTop = true
            popUpTo(Routes.SERVICES_SCREEN) {
                inclusive = false
            }
        }
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)) {

        HeadingTextWithIcon(textHeading = if (model==null) "Add Services" else " Edit Service",
            onBackClick = {navController.navigate(Routes.SERVICES_SCREEN) {
                launchSingleTop = true
                popUpTo(Routes.SERVICES_SCREEN) {
                    inclusive = false
                }
            }})

        HorizontalDivider(modifier = Modifier
            .fillMaxWidth()
            .height(2.dp)
            .background(PrimaryColor))

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

            InputField(input = uiStateServices.service_title ?:"", onValueChange = { viewModel.updateTitle(it) }, placeholder = "Enter title")

            Spacer(Modifier.height(15.dp))

            FormHeadingText("Description")

            Spacer(Modifier.height(10.dp))

            InputField(input = uiStateServices.description ?:"", onValueChange = { viewModel.updateDescription(it) }, placeholder = "Type here")

            Spacer(Modifier.height(15.dp))

            FormHeadingText("Price (\$)")

            Spacer(Modifier.height(10.dp))

            InputField(input = uiStateServices.price ?:"", onValueChange = { viewModel.updateAmount(it) }, placeholder = "Enter amount",keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done)
            )

            Spacer(Modifier.height(15.dp))

            FormHeadingText("Upload Media")

            Spacer(Modifier.height(10.dp))
            Log.d("EditService", "Images: ${uiStateServices.service_image?.size}")
            val images = uiStateServices.service_image ?: emptyList()

            MediaUploadSectionUrlUri(
                maxImages = 6,
                imageList = images.toMutableList(), // valid
                onMediaSelected = { viewModel.addPhoto(it) },
                onMediaUnSelected = { viewModel.removePhoto(it) },
                type = "image"
            )


            Spacer(Modifier.height(25.dp))

            SubmitButton(modifier = Modifier, buttonText = if (model==null) "Add Service" else "Save Changes",
                onClickButton = {
                    viewModel.addOrUpdateService(context=context,
                        onError = {},
                        onSuccess = {
                            addedServiceDialog = true
                        })
                                },
                buttonTextSize = 15)
        }

        if (addedServiceDialog) {
            if (model==null) {
                ServiceAdEditDialog(
                    onDismiss = {
                        addedServiceDialog = false
                        navController.navigate(Routes.SERVICES_SCREEN)
                                },
                    iconResId = R.drawable.ic_sucess_p,
                    text = "New Service Added Successfully",
                    description = "Your new service has been added and is now visible to customers."
                )
            }else{
                ServiceAdEditDialog(
                    onDismiss = {
                        addedServiceDialog = false
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


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun AddServiceScreenPreview() {
    val navController = rememberNavController()
    EditAddServiceScreen(navController = navController, type = "")
}