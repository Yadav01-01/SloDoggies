package com.bussiness.slodoggiesapp.ui.screens.businessprovider.services

import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.AlertDialog
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.navigation.Routes
import com.bussiness.slodoggiesapp.ui.component.businessProvider.CategoryInputField
import com.bussiness.slodoggiesapp.ui.component.businessProvider.DayTimeSelector
import com.bussiness.slodoggiesapp.ui.component.businessProvider.DescriptionBox
import com.bussiness.slodoggiesapp.ui.component.businessProvider.FormHeadingText
import com.bussiness.slodoggiesapp.ui.component.businessProvider.HeadingTextWithIcon
import com.bussiness.slodoggiesapp.ui.component.businessProvider.InputField
import com.bussiness.slodoggiesapp.ui.component.businessProvider.SubmitButton
import com.bussiness.slodoggiesapp.ui.component.common.MediaUploadSection
import com.bussiness.slodoggiesapp.ui.component.saveBitmapToCache
import com.bussiness.slodoggiesapp.ui.dialog.ServiceAdEditDialog
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor
import com.bussiness.slodoggiesapp.viewModel.businessProvider.EditBusinessViewModel

@Composable
fun EditBusinessScreen(
    navController: NavHostController,
    viewModel: EditBusinessViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    val launcherGallery = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { viewModel.updateLogoImage(it) }
    }

    val launcherCamera = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap: Bitmap? ->
        bitmap?.let {
            val uri = saveBitmapToCache(context, it)
            viewModel.updateLogoImage(uri)
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            launcherCamera.launch(null)
        } else {
            Toast.makeText(context, "Camera permission is required", Toast.LENGTH_SHORT).show()
        }
    }

    fun launchCameraWithPermissionCheck() {
        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(context, android.Manifest.permission.CAMERA) -> {
                launcherCamera.launch(null)
            }
            else -> {
                permissionLauncher.launch(android.Manifest.permission.CAMERA)
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .imePadding()
    ) {
        HeadingTextWithIcon(
            textHeading = stringResource(R.string.Edit_Business),
            onBackClick = { navController.navigate(Routes.SERVICES_SCREEN) {
                launchSingleTop = true
                popUpTo(Routes.SERVICES_SCREEN) {
                    inclusive = false
                }
            } }
        )

        HorizontalDivider(thickness = 2.dp, color = PrimaryColor)

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Profile Image Section
            item {
                Spacer(Modifier.height(25.dp))
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier.size(160.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        AsyncImage(
                            model = uiState.businessLogo,
                            contentDescription = "Business Logo",
                            contentScale = ContentScale.Crop,
                            placeholder = painterResource(id = R.drawable.fluent_color_paw),
                            error = painterResource(id = R.drawable.fluent_color_paw),
                            modifier = Modifier
                                .size(160.dp)
                                .border(4.dp, Color(0xFFE5EFF2), CircleShape)
                                .clip(CircleShape)
                        )

                        Image(
                            painter = painterResource(id = R.drawable.ic_post_icon),
                            contentDescription = "Add Photo",
                            modifier = Modifier
                                .size(35.dp)
                                .align(Alignment.BottomEnd)
                                .clickable { viewModel.toggleImagePickerDialog() }
                        )
                    }
                }
            }

            // Business Name
            item {
                FormHeadingText(stringResource(R.string.business_name))
                InputField(
                    input = uiState.businessName,
                    onValueChange = { viewModel.updateBusinessName(it) },
                    placeholder = stringResource(R.string.enter_name)
                )
            }

            // Provider Name
            item {
                FormHeadingText(stringResource(R.string.provider_name))
                InputField(
                    input = uiState.providerName,
                    onValueChange = { viewModel.updateProviderName(it) },
                    placeholder = stringResource(R.string.enter_name)
                )
            }

            // Email
            item {
                FormHeadingText(stringResource(R.string.email))
                InputField(
                    input = uiState.email,
                    onValueChange = { viewModel.updateEmail(it) },
                    placeholder = stringResource(R.string.enter_email)
                )
            }

            // Bio
            item {
                FormHeadingText(stringResource(R.string.bio))
                DescriptionBox(  placeholder = stringResource(R.string.enter_bio), value =  uiState.bio, onValueChange ={ viewModel.updateBio(it)})

            }

            // Category
            item {
                FormHeadingText(stringResource(R.string.Category))
//                CategoryInputField()
            }

            // Business Address
            item {
                FormHeadingText(stringResource(R.string.business_address))
                InputField(
                    input = uiState.businessAddress,
                    onValueChange = { viewModel.updateBusinessAddress(it) },
                    placeholder = stringResource(R.string.enter_bus_address)
                )
            }

            // Area/Sector
            item {
                FormHeadingText(stringResource(R.string.area_sector))
                InputField(
                    input = uiState.city,
                    onValueChange = { viewModel.updateCity(it) },
                    placeholder = stringResource(R.string.enter_area_sector)
                )
            }

            // Landmark
            item {
                FormHeadingText(stringResource(R.string.landmark))
                InputField(
                    input = uiState.landmark,
                    onValueChange = { viewModel.updateLandmark(it) },
                    placeholder = stringResource(R.string.enter_landmark)
                )
            }

            item {
                FormHeadingText(stringResource(R.string.zip_code))
                InputField(
                    input = uiState.zipCode,
                    onValueChange = { viewModel.updateZipCode(it) },
                    placeholder = stringResource(R.string.enter_zip_code)
                )
            }

            // Website
            item {
                FormHeadingText(stringResource(R.string.website_))
                InputField(
                    input = uiState.url,
                    onValueChange = { viewModel.updateUrl(it) },
                    placeholder = "URL"
                )
            }

            // Contact
            item {
                FormHeadingText(stringResource(R.string.contact_number))
                InputField(
                    input = uiState.contact,
                    onValueChange = { viewModel.updateContact(it) },
                    placeholder = "Enter Contact"
                )
            }

            // Available Days
            item {
                FormHeadingText(stringResource(R.string.available_days))
                DayTimeSelector { selectedDays, from, to ->
                    viewModel.updateAvailableDays(selectedDays, from, to)
                }
            }

            // Verification Docs Upload
            item {
                FormHeadingText(stringResource(R.string.Upload_verification_docs))
                MediaUploadSection(maxImages = 6) { uri -> viewModel.addVerificationDoc(uri) }
            }

            // Submit Button
            item {
                SubmitButton(
                    modifier = Modifier.fillMaxWidth(),
                    buttonText = stringResource(R.string.Save_Changes),
                    onClickButton = { viewModel.toggleUpdateDialog() },
                    buttonTextSize = 15
                )
                Spacer(Modifier.height(15.dp))
            }
        }
    }
    if (uiState.showUpdatedDialog){
        ServiceAdEditDialog(
            onDismiss = {
                viewModel.hideUpdateDialog()
                navController.navigate(Routes.SERVICES_SCREEN)
            },
            iconResId = R.drawable.ic_sucess_p,
            text = "Profile Updated!",
            description = "Your information has been saved.\n" +
                    "Thanks for keeping things up to date!"
        )
    }

    if (uiState.showImagePickerDialog) {
        AlertDialog(
            onDismissRequest = { viewModel.hideImagePickerDialog() },
            title = { Text("Select Option") },
            text = {
                Column {
                    Text("Camera", modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            viewModel.hideImagePickerDialog()
                            launchCameraWithPermissionCheck()
                        }
                        .padding(8.dp))

                    Text("Gallery", modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            viewModel.hideImagePickerDialog()
                            launcherGallery.launch("image/*")
                        }
                        .padding(8.dp))
                }
            },
            confirmButton = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewEditBusiness(){
    var navController = NavHostController(LocalContext.current)
    EditBusinessScreen(navController = navController)
}