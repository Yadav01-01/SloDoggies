package com.bussiness.slodoggiesapp.ui.screens.petowner.post.content

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.ui.component.businessProvider.DescriptionBox
import com.bussiness.slodoggiesapp.ui.component.businessProvider.FormHeadingText
import com.bussiness.slodoggiesapp.ui.component.businessProvider.InputField
import com.bussiness.slodoggiesapp.ui.component.businessProvider.SubmitButton
import com.bussiness.slodoggiesapp.ui.component.common.MediaUploadSection
import com.bussiness.slodoggiesapp.ui.dialog.DateTimePickerDialog
import com.bussiness.slodoggiesapp.util.LocationUtils.Companion.getAddressFromLatLng
import com.bussiness.slodoggiesapp.viewModel.businessProvider.PostContentViewModel
import com.bussiness.slodoggiesapp.viewModel.petOwner.createeventowner.EventCreateOwnerViewModel
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode


@SuppressLint("ContextCastToActivity")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PetEventScreenContent( onClickLocation: () -> Unit,onClickSubmit: () -> Unit,viewModel: PostContentViewModel = hiltViewModel()) {


    var showDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val viewModelEventCreateOwner: EventCreateOwnerViewModel = hiltViewModel()
    val uiStatePostCreateOwner by viewModelEventCreateOwner.uiState.collectAsState()


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
            viewModelEventCreateOwner.onLocation(addressResult?.street?:"")
            viewModelEventCreateOwner.onState(addressResult?.state?:"")
            viewModelEventCreateOwner.onCity(addressResult?.city?:"")
            viewModelEventCreateOwner.onZipCode(addressResult?.zip?:"")
            viewModelEventCreateOwner.onLatitude((placeLatLong?.latitude ?: 0.000).toString())
            viewModelEventCreateOwner.onLongitude((placeLatLong?.longitude ?: 0.000).toString())

        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 16.dp, vertical = 5.dp)
            .imePadding(),
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        item {
            FormHeadingText(stringResource(R.string.upload_media))
            Spacer(Modifier.height(10.dp))
            MediaUploadSection(maxImages = 6,
                imageList=uiStatePostCreateOwner.image?: mutableListOf(),
                onMediaSelected = {
                viewModelEventCreateOwner.addPhoto(it) } ,
                onMediaUnSelected = {  viewModelEventCreateOwner.removePhoto(it)},
                type="video")
        }

        item {
            FormHeadingText(stringResource(R.string.event_title))
            Spacer(Modifier.height(10.dp))
            InputField(
                placeholder = stringResource(R.string.event_title),
                input = uiStatePostCreateOwner.title?:"",
                onValueChange = { viewModelEventCreateOwner.onTitlePost(it) }
            )
        }

        item {
            FormHeadingText(stringResource(R.string.event_des))
            Spacer(Modifier.height(10.dp))
            DescriptionBox(
                placeholder = stringResource(R.string.enter_des),
                value = uiStatePostCreateOwner.description?:"",
                onValueChange = { viewModelEventCreateOwner.onDescriptionChange(it) }
            )
        }

        item {

            DateTimePickerScreen(textHeading = stringResource(R.string.event_sdate) ,
                date = uiStatePostCreateOwner.startDate,
                time=uiStatePostCreateOwner.startTime,
                timeSelect = { time->
                    viewModelEventCreateOwner.onStartTimeChange(time)
                    Log.d("Select time","******"+uiStatePostCreateOwner.startTime)
                },
                dateSelect = { date->
                    viewModelEventCreateOwner.onStartDateChange(date)
                    Log.d("Select time","******"+uiStatePostCreateOwner.startTime)
                } )

            Spacer(Modifier.height(15.dp))

            DateTimePickerScreen(textHeading = stringResource(R.string.event_edate),
                date = uiStatePostCreateOwner.endDate,
                time=uiStatePostCreateOwner.endTime,
                timeSelect = { time->
                    viewModelEventCreateOwner.onEndTimeChange(time)
                },
                dateSelect = { date->
                    viewModelEventCreateOwner.onEndDateChange(date)
                    Log.d("Select time","******"+uiStatePostCreateOwner.startTime)
                } )

        }

        item {
            FormHeadingText(stringResource(R.string.flat_address))
            InputField(
                placeholder = stringResource(R.string.enter_flat_address),
                input = uiStatePostCreateOwner.location?:"",
                onValueChange = { viewModelEventCreateOwner.onLocation(it) },
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
                input = uiStatePostCreateOwner.city?:"",
                onValueChange = { viewModelEventCreateOwner.onCity(it) }
            )
        }

        item {
            FormHeadingText(stringResource(R.string.landmark))
            InputField(
                placeholder = stringResource(R.string.enter_landmark),
                input = uiStatePostCreateOwner.state?:"",
                onValueChange = { viewModelEventCreateOwner.onState(it) }
            )
        }

        item {
            FormHeadingText("Zip Code")
            InputField(
                placeholder = "Enter your Zip Code",
                input = uiStatePostCreateOwner.zipcode?:"",
                onValueChange = { viewModelEventCreateOwner.onZipCode(it) }
            )
        }

        item {
            Spacer(Modifier.height(25.dp))
            SubmitButton(
                modifier = Modifier,
                buttonText = "Post Event",
                onClickButton = {
                    viewModelEventCreateOwner.createEventOwner(context=context,
                        onError = { msg -> Toast.makeText(context, msg, Toast.LENGTH_SHORT).show() },
                        onSuccess = {
                            onClickSubmit()
                        }
                    )
                }
            )
            Spacer(Modifier.height(30.dp))
        }

    }
    if (showDialog){
        DateTimePickerDialog(
            showDialog = true,
            onDismissRequest = { showDialog = false },
            onDateTimeSelected = { dateTime ->
                // Handle selected date and time
                println("Selected DateTime: $dateTime")
                showDialog = false
            }
        )
    }

}
