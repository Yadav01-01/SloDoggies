package com.bussiness.slodoggiesapp.ui.screens.businessprovider.registration

import android.app.Activity
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.runtime.LaunchedEffect
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
import androidx.lifecycle.compose.LocalLifecycleOwner
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
import com.bussiness.slodoggiesapp.ui.component.common.EmailField
import com.bussiness.slodoggiesapp.ui.component.common.MediaUploadSection
import com.bussiness.slodoggiesapp.ui.component.common.MediaUploadSectionUrlUri
import com.bussiness.slodoggiesapp.ui.component.common.PhoneNumber
import com.bussiness.slodoggiesapp.util.LocationUtils.Companion.getAddressFromLatLng
import com.bussiness.slodoggiesapp.viewModel.businessprofile.BusinessProfileViewModel
import com.bussiness.slodoggiesapp.viewModel.common.VerifyAccountViewModel
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BusinessRegistrationScreen(navController: NavHostController,viewModel: BusinessProfileViewModel = hiltViewModel()){

    val state by viewModel.uiState.collectAsState()
    val viewModelOtp: VerifyAccountViewModel = hiltViewModel()
    val stateOtp by viewModelOtp.uiState.collectAsState()
    var isNavigating by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(navController.currentBackStackEntry) {
        navController.currentBackStackEntry
            ?.savedStateHandle
            ?.getLiveData<String>("verification_result")
            ?.observe(lifecycleOwner) { result ->
                when (result) {
                    "dialogPhone" -> viewModel.setInitialPhone(state.data?.phone.toString(),true)
                    "dialogEmail" -> viewModel.setInitialEmail(state.data?.email.toString(),true)
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


    Column(modifier = Modifier.fillMaxSize().background(Color.White)) {

        TopHeadingText(textHeading = stringResource(R.string.business_registration),onBackClick = {
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
                    input = state.data?.business_name?:"",
                    onValueChange = { viewModel.updateBusinessName(it) },
                    placeholder = stringResource(R.string.enter_business_name)
                )
            }

            item {
                FormHeadingText(stringResource(R.string.email))

                EmailField(
                    email = state.data?.email?:"",
                    isVerified = state.data?.emailVerify?:false,
                    onEmailChange = viewModel::updateEmail,
                    onVerify = {
                        state.data?.email?.let { it1 -> viewModelOtp.userEmailPhone(it1) }
                        viewModelOtp.sendOtpRequest(
                            type ="dialogEmail" ,
                            onError = {
                                Toast.makeText(context,it,Toast.LENGTH_SHORT).show()
                            },
                            onSuccess = {
                                val type="dialogEmail"
                                navController.navigate("${Routes.VERIFY_ACCOUNT_SCREEN}?type=$type&data=${state.data?.email}")
                            })
                    }
                )

            }

            item {
                FormHeadingText(stringResource(R.string.Upload_business_logo))
                Spacer(Modifier.height(5.dp))
                val logoList: MutableList<String> = viewModel.businessLogo.collectAsState().value.toMutableList()
                MediaUploadSectionUrlUri(maxImages = 1,
                    imageList= logoList ,
                    onMediaSelected = {
                        viewModel.addBusinessLogo(it)
                                      }
                    ,
                    onMediaUnSelected = {
                        viewModel.removeBusinessLogo(it)
                                        },
                    type="image")
            }

            item {
                FormHeadingText(stringResource(R.string.Category))
                CategoryInputField(
                    categories = state.data?.category,
                    onCategoryAdded = { viewModel.addCategory(it) },
                    onCategoryRemoved = { viewModel.removeCategory(it) }
                )
            }

            item {

                FormHeadingText(stringResource(R.string.Business_address))

                InputField(
                    placeholder = stringResource(R.string.Enter_Business_Address),
                    input = state.data?.address?:"",
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

            item {
                FormHeadingText(stringResource(R.string.area_sector))
                InputField(
                    placeholder = stringResource(R.string.enter_area_sector),
                    input = state.data?.city?:"",
                    onValueChange = { viewModel.updateCity(it) }
                )
            }

            item {
                FormHeadingText(stringResource(R.string.landmark))
                InputField(
                    placeholder = stringResource(R.string.enter_landmark),
                    input = state.data?.state?:"",
                    onValueChange = { viewModel.updateState(it) }
                )
            }

            item {
                FormHeadingText(stringResource(R.string.zip_code))
                InputField(
                    placeholder = stringResource(R.string.enter_zip_code),
                    input = state.data?.zip_code?:"" ,
                    onValueChange = { viewModel.updateZip(it) }
                )
            }

            item {
                FormHeadingText(stringResource(R.string.website_))
                InputField(
                    input = state.data?.website_url?:"",
                    onValueChange = { viewModel.updateWebsite(it) },
                    placeholder = "URL"
                )
            }

            item {
                FormHeadingText(stringResource(R.string.contact_number))

                PhoneNumber(
                    phone = state.data?.phone?:"",
                    onPhoneChange = { viewModel.updateContact(it) },
                    onVerify = {
                        state.data?.phone?.let { it1 -> viewModelOtp.userEmailPhone(it1) }
                        viewModelOtp.sendOtpRequest(
                            onError = {},
                            type ="dialogPhone" ,
                            onSuccess = {
                                val type="dialogPhone"
                                navController.navigate("${Routes.VERIFY_ACCOUNT_SCREEN}?type=$type&data=${state.data?.phone}")
                            })
                               },
                    isVerified = state.data?.phoneVerify?:false
                )
            }

            item {

                FormHeadingText(stringResource(R.string.available_days))
                // Add your custom available days UI here
                DayTimeSelector(
                    selectTime=state.data?.available_time,
                    selectList=state.data?.available_days,
                    onDone = { selectedDays, from, to ->
                        println("Selected Days: $selectedDays")
                        println("From: $from")
                        println("To: $to")
                        viewModel.updateAvailableDays(selectedDays)
                        viewModel.updateFromToTime("$from-$to")
                    }
                )
            }

            item {

                FormHeadingText(stringResource(R.string.Upload_verification_docs))
                MediaUploadSectionUrlUri(maxImages = 6,
                    imageList=state.data?.verification_docs?: mutableListOf(),
                    onMediaSelected = {
                        viewModel.addPhoto(it.toString())
                    },
                    onMediaUnSelected = {
                        viewModel.removePhoto(it.toString())
                    },
                    type="image")
            }

            item {
                SubmitButton(
                    modifier = Modifier.fillMaxWidth(),
                    buttonText = stringResource(R.string.submit_n_next),
                    onClickButton = {
                        viewModel.upDateRegistration(context = context,
                            onError = { msg -> Toast.makeText(context, msg, Toast.LENGTH_SHORT).show() },
                            onSuccess = {
                                navController.navigate(Routes.ADD_SERVICE)
                            }) },
                    buttonTextSize = 15
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun PreviewBusinessRegistrationScreen() {
    // Use a dummy NavController for preview purposes
    val navController = rememberNavController()

    BusinessRegistrationScreen(navController = navController)
}
