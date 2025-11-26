package com.bussiness.slodoggiesapp.ui.screens.businessprovider.services

import android.app.Activity
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.lifecycle.compose.LocalLifecycleOwner
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
import com.bussiness.slodoggiesapp.ui.component.businessProvider.saveBitmapToCache
import com.bussiness.slodoggiesapp.ui.component.common.EmailField
import com.bussiness.slodoggiesapp.ui.component.common.MediaUploadSection
import com.bussiness.slodoggiesapp.ui.component.common.MediaUploadSectionUrlUri
import com.bussiness.slodoggiesapp.ui.component.common.PhoneNumber
import com.bussiness.slodoggiesapp.ui.dialog.ServiceAdEditDialog
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor
import com.bussiness.slodoggiesapp.util.LocationUtils.Companion.getAddressFromLatLng
import com.bussiness.slodoggiesapp.util.LocationUtils.Companion.getImageModel
import com.bussiness.slodoggiesapp.viewModel.businessprofile.BusinessProfileViewModel
import com.bussiness.slodoggiesapp.viewModel.common.VerifyAccountViewModel
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EditBusinessScreen(
    navController: NavHostController,
//    viewModel: EditBusinessViewModel = hiltViewModel()
) {
    val viewModel: BusinessProfileViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()
    val viewModelOtp: VerifyAccountViewModel = hiltViewModel()
    val stateOtp by viewModelOtp.uiState.collectAsState()
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    var showImagePickerDialog by remember { mutableStateOf(false) }
    var showUpdatedDialog by remember { mutableStateOf(false) }



    LaunchedEffect(navController.currentBackStackEntry) {
        navController.currentBackStackEntry
            ?.savedStateHandle
            ?.getLiveData<String>("verification_result")
            ?.observe(lifecycleOwner) { result ->
                when (result) {
                    "dialogPhone" -> viewModel.setInitialPhone(uiState.data?.phone.toString(),true)
                    "dialogEmail" -> viewModel.setInitialEmail(uiState.data?.email.toString(),true)
                }
            }
    }


    // Launcher to pick an image from the gallery
    // Uses ActivityResultContracts.GetContent to let the user select an image
    // When an image is selected, its Uri is passed to the ViewModel
    val launcherGallery = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { viewModel.addBusinessLogo(it.toString()) }
    }

    // Launcher to take a picture using the camera
    // Uses ActivityResultContracts.TakePicturePreview to get a Bitmap from the camera
    // Converts the Bitmap to a Uri and passes it to the ViewModel
    val launcherCamera = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap: Bitmap? ->
        bitmap?.let {
            val uri = saveBitmapToCache(context, it)
            viewModel.addBusinessLogo(uri.toString())
        }
    }


    // Launcher to request a runtime permission
    // In this case, CAMERA permission
    // If granted, it launches the camera; otherwise, shows a Toast message
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            launcherCamera.launch(null)
        } else {
            Toast.makeText(context, "Camera permission is required", Toast.LENGTH_SHORT).show()
        }
    }


    // Function to launch the camera with permission check
    // 1. Checks if CAMERA permission is already granted
    // 2. If granted, launches the camera directly
    // 3. If not granted, requests permission first via permissionLauncher
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


    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val place = Autocomplete.getPlaceFromIntent(result.data!!)
            val placeLatLong=place.latLng
            val addressResult = getAddressFromLatLng(context, placeLatLong?.latitude?:0.000, placeLatLong?.longitude?:0.000)
            Log.d("Location", "zip: ${addressResult?.zip}")
            Log.d("Location", "city: ${addressResult?.city}")
            Log.d("Location", "state: ${addressResult?.state}")
            Log.d("Location", "street: ${addressResult?.street}")
            Log.d("Location", "latitude: ${placeLatLong?.latitude ?: 0.000}")
            Log.d("Location", "longitude: ${placeLatLong?.longitude ?: 0.000}")
            viewModel.updateBusinessAddress(addressResult?.street?:"")
            viewModel.updateState(addressResult?.state?:"")
            viewModel.updateCity(addressResult?.city?:"")
            viewModel.updateZip(addressResult?.zip?:"")
            viewModel.updateLatitude((placeLatLong?.latitude ?: 0.000).toString())
            viewModel.updateLongitude((placeLatLong?.longitude ?: 0.000).toString())
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
                            model = getImageModel(uiState.data?.business_logo),
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
                                .clickable { showImagePickerDialog = true }
                        )
                    }
                }
            }

            // Business Name
            item {
                FormHeadingText(stringResource(R.string.business_name))
                InputField(
                    input = uiState.data?.business_name?:"",
                    onValueChange = { viewModel.updateBusinessName(it) },
                    placeholder = stringResource(R.string.enter_name)
                )
            }

            // Provider Name
            item {
                FormHeadingText(stringResource(R.string.provider_name))
                InputField(
                    input = uiState.data?.provider_name?:"",
                    onValueChange = { viewModel.updateProviderName(it) },
                    placeholder = stringResource(R.string.enter_name)
                )
            }

            // Email
            item {
                FormHeadingText(stringResource(R.string.email))
                EmailField(
                    email = uiState.data?.email?:"",
                    isVerified = uiState.data?.emailVerify?:false,
                    onEmailChange = viewModel::updateEmail,
                    onVerify = {
                        uiState.data?.email?.let { it1 -> viewModelOtp.userEmailPhone(it1) }
                        viewModelOtp.sendOtpRequest(
                            type ="dialogEmail" ,
                            onError = {
                                Toast.makeText(context,it,Toast.LENGTH_SHORT).show()
                            },
                            onSuccess = {
                                val type="dialogEmail"
                                navController.navigate("${Routes.VERIFY_ACCOUNT_SCREEN}?type=$type&data=${uiState.data?.email}")
                            })
                    }
                )
            }

            // Bio
            item {
                FormHeadingText(stringResource(R.string.bio))
                DescriptionBox(  placeholder = stringResource(R.string.enter_bio),
                    value =  uiState.data?.bio?:"",
                    onValueChange ={ viewModel.updateBio(it)})

            }

            // Category
            item {
                FormHeadingText(stringResource(R.string.Category))
                CategoryInputField(
                    categories = uiState.data?.category,
                    onCategoryAdded = { viewModel.addCategory(it) },
                    onCategoryRemoved = { viewModel.removeCategory(it) }
                )
            }

            // Business Address
            item {
                FormHeadingText(stringResource(R.string.business_address))
                InputField(
                    placeholder = stringResource(R.string.Enter_Business_Address),
                    input = uiState.data?.address?:"",
                    onValueChange = { viewModel.updateBusinessAddress(it) },
                    readOnly = true,
                    onClick = {
                        val fields = listOf(
                            Place.Field.ID,
                            Place.Field.NAME,
                            Place.Field.ADDRESS,
                            Place.Field.LAT_LNG
                        )
                        val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN,fields).build(context)
                        launcher.launch(intent)
                    }
                )
            }

            // Area/Sector
            item {
                FormHeadingText(stringResource(R.string.area_sector))
                InputField(
                    input = uiState.data?.city?:"",
                    onValueChange = { viewModel.updateCity(it) },
                    placeholder = stringResource(R.string.enter_area_sector)
                )
            }

            // Landmark
            item {
                FormHeadingText(stringResource(R.string.landmark))
                InputField(
                    input = uiState.data?.state?:"",
                    onValueChange = { viewModel.updateState(it) },
                    placeholder = stringResource(R.string.enter_landmark)
                )
            }

            item {
                FormHeadingText(stringResource(R.string.zip_code))
                InputField(
                    input = uiState.data?.zip_code?:"",
                    onValueChange = { viewModel.updateZip(it) },
                    placeholder = stringResource(R.string.enter_zip_code)
                )
            }

            // Website
            item {
                FormHeadingText(stringResource(R.string.website_))
                InputField(
                    input = uiState.data?.website_url?:"",
                    onValueChange = { viewModel.updateWebsite(it) },
                    placeholder = "URL"
                )
            }

            // Contact
            item {
                FormHeadingText(stringResource(R.string.contact_number))
                PhoneNumber(
                    phone = uiState.data?.phone?:"",
                    onPhoneChange = { viewModel.updateContact(it) },
                    onVerify = {
                        uiState.data?.phone?.let { it1 -> viewModelOtp.userEmailPhone(it1) }
                        viewModelOtp.sendOtpRequest(
                            onError = {},
                            type ="dialogPhone" ,
                            onSuccess = {
                                val type="dialogPhone"
                                navController.navigate("${Routes.VERIFY_ACCOUNT_SCREEN}?type=$type&data=${uiState.data?.phone}")
                            })
                    },
                    isVerified = uiState.data?.phoneVerify?:false
                )
            }

            // Available Days
            item {
                FormHeadingText(stringResource(R.string.available_days))
                DayTimeSelector(
                    selectTime=uiState.data?.available_time,
                    selectList=uiState.data?.available_days,
                    onDone = { selectedDays, from, to ->
                        println("Selected Days: $selectedDays")
                        println("From: $from")
                        println("To: $to")
                        viewModel.updateAvailableDays(selectedDays)
                        viewModel.updateFromToTime("$from-$to")
                    }
                )
            }

            // Verification Docs Upload
            item {
                FormHeadingText(stringResource(R.string.Upload_verification_docs))
                MediaUploadSectionUrlUri(maxImages = 6,
                    imageList=uiState.data?.verification_docs?: mutableListOf(),
                    onMediaSelected = {
                        viewModel.addPhoto(it)
                    },
                    onMediaUnSelected = {
                        viewModel.removePhoto(it)
                    },
                    type="image")
            }

            // Submit Button
            item {
                SubmitButton(
                    modifier = Modifier.fillMaxWidth(),
                    buttonText = stringResource(R.string.Save_Changes),
                    onClickButton = { /*viewModel.toggleUpdateDialog()*/
                        viewModel.upDateRegistration(context = context,
                            onError = { msg -> Toast.makeText(context, msg, Toast.LENGTH_SHORT).show() },
                            onSuccess = {
                                showUpdatedDialog=true
                            })
                    },
                    buttonTextSize = 15
                )
                Spacer(Modifier.height(15.dp))
            }
        }
    }

    if (showUpdatedDialog){
        ServiceAdEditDialog(
            onDismiss = {
                showUpdatedDialog=false
                navController.navigate(Routes.SERVICES_SCREEN)
            },
            iconResId = R.drawable.ic_sucess_p,
            text = "Profile Updated!",
            description = "Your information has been saved.\n" +
                    "Thanks for keeping things up to date!"
        )
    }

    if (showImagePickerDialog) {
        AlertDialog(
            onDismissRequest = { showImagePickerDialog =false},
            title = { Text("Select Option") },
            text = {
                Column {
                    Text("Camera", modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            showImagePickerDialog = false
                            launchCameraWithPermissionCheck()
                        }
                        .padding(8.dp))

                    Text("Gallery", modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            showImagePickerDialog = false
                            launcherGallery.launch("image/*")
                        }
                        .padding(8.dp))
                }
            },
            confirmButton = {}
        )
    }

}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun PreviewEditBusiness(){
    val navController = NavHostController(LocalContext.current)
    EditBusinessScreen(navController = navController)
}