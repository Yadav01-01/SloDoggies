package com.bussiness.slodoggiesapp.ui.screens.businessprovider.post.content


import android.app.Activity
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.ui.component.businessProvider.CategoryInputField
import com.bussiness.slodoggiesapp.ui.component.businessProvider.CustomDropdownBox
import com.bussiness.slodoggiesapp.ui.component.businessProvider.DescriptionBox
import com.bussiness.slodoggiesapp.ui.component.businessProvider.FormHeadingText
import com.bussiness.slodoggiesapp.ui.component.businessProvider.InputField
import com.bussiness.slodoggiesapp.ui.component.businessProvider.SubmitButton
import com.bussiness.slodoggiesapp.ui.component.common.MediaUploadSection
import com.bussiness.slodoggiesapp.ui.screens.petowner.post.content.DateTimePickerScreen
import com.bussiness.slodoggiesapp.util.LocationUtils.Companion.getAddressFromLatLng
import com.bussiness.slodoggiesapp.viewModel.businessProvider.PostContentViewModel
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PromotionScreenContent(
    onClickLocation: () -> Unit,
    onClickSave:  () -> Unit,
    viewModel: PostContentViewModel = hiltViewModel()
) {
    val uiStateAddCreate by viewModel.uiState.collectAsState()
    val uiStateService by viewModel.uiStateService.collectAsState()
    var selectedServices by remember { mutableStateOf("") }
    var isContactDisplayEnabled by remember { mutableStateOf(true) }
    val context = LocalContext.current


    LaunchedEffect(Unit) {
        viewModel.servicesList()
    }

    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val place = Autocomplete.getPlaceFromIntent(result.data!!)
            val placeLatLong=place.latLng
            val addressResult = getAddressFromLatLng(context,
                placeLatLong?.latitude?:0.000, placeLatLong?.longitude?:0.000)
            viewModel.onLocation(addressResult?.street?:"")
            viewModel.onLatitude((placeLatLong?.latitude ?: 0.000).toString())
            viewModel.onLongitude((placeLatLong?.longitude ?: 0.000).toString())

        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
            .imePadding(),
        verticalArrangement = Arrangement.spacedBy(15.dp)

    ) {
        item {
            FormHeadingText(stringResource(R.string.upload_media))
            Spacer(Modifier.height(10.dp))
            MediaUploadSection(maxImages = 6,
                imageList=uiStateAddCreate.image?: mutableListOf(),
                onMediaSelected = {
                    viewModel.addPhoto(it) } ,
                onMediaUnSelected = {  viewModel.removePhoto(it)},
                type="video")
        }

        item {
            FormHeadingText(stringResource(R.string.ad_title))
            Spacer(Modifier.height(10.dp))
            InputField(
                placeholder = stringResource(R.string.enter_title),
                input = uiStateAddCreate.adTitle?:"",
                onValueChange = { viewModel.updateTitle(it) }
            )
        }

        item {
            FormHeadingText(stringResource(R.string.ad_desc))
            Spacer(Modifier.height(10.dp))
            DescriptionBox(
                placeholder = stringResource(R.string.enter_des),
                value = uiStateAddCreate.adDescription?:"",
                onValueChange = { viewModel.updateAdDescription(it) }
            )
        }

        item {
            FormHeadingText(stringResource(R.string.category))
            Spacer(Modifier.height(10.dp))
            CategoryInputField(
                categories = uiStateAddCreate.category?.toMutableList(),
                onCategoryAdded = { viewModel.addCategory(it) },
                onCategoryRemoved = { viewModel.removeCategory(it) }
            )
        }

        item {
            FormHeadingText(stringResource(R.string.service))
            Spacer(Modifier.height(10.dp))
            CustomDropdownBox(
                label = if (selectedServices.isEmpty()) "Select Service" else selectedServices,
                items = uiStateService.data?.map { it.service_title ?: "" } ?: emptyList(),
                selectedItem = selectedServices,
                onItemSelected = {
                    Log.d("******",it)
                    selectedServices = it
                    viewModel.addService(it)
                }
            )
        }

        item {
           // DateTimePickerScreen(textHeading = stringResource(R.string.expiry_date_time))
            DateTimePickerScreen(textHeading = stringResource(R.string.event_edate),
                date = uiStateAddCreate.expiryDate,
                time=uiStateAddCreate.expiryTime,
                timeSelect = { time->
                    viewModel.updateExpireTime(time)
                },
                dateSelect = { date->
                    viewModel.updateExpireDate(date)
                } )

        }

        item {
            FormHeadingText(stringResource(R.string.enter_terms_condition))
            Spacer(Modifier.height(10.dp))
            DescriptionBox(
                value = uiStateAddCreate.termAndConditions?:"",
                onValueChange = { viewModel.updateTermsAndConditions(it) },
                placeholder = "Enter here"
            )
        }

        item {
            FormHeadingText(stringResource(R.string.Service_Location))
            Spacer(Modifier.height(10.dp))
            InputField(
                placeholder = stringResource(R.string.dummy_loc),
                input = uiStateAddCreate.serviceLocation?:"",
                onValueChange = { viewModel.updateServiceLocation(it) },
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
            ToggleRow(title = stringResource(R.string.contact_info_display), isEnabled = uiStateAddCreate.mobile_visual == "1",
                onToggle = {
                    isContactDisplayEnabled = it
                    viewModel.updateMobileVisible(if (it) "1" else "0")
                })
            InputField(
                input = uiStateAddCreate.contactNumber?:"",
                onValueChange = { viewModel.updateContactInfo(it) },
                placeholder = stringResource(R.string.enter_mobile_no)
            )
        }

        item {
            SubmitButton(
                modifier = Modifier,
                buttonText = "Save & Next",
                onClickButton = {
                   //onClickSave()
                    val (isValid, message) = viewModel.validateAdData(uiStateAddCreate)
                    if (!isValid) {
                        // Show toast/snackbar
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                    } else {
                        onClickSave()
                    }

                }
            )
        }
    }
}

@Composable
private fun ToggleRow(
    title: String,
    isEnabled: Boolean,
    onToggle: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onToggle(!isEnabled) }
            .padding(end = 5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        FormHeadingText(title)

        Spacer(modifier = Modifier.weight(1f)) // pushes switch to right

        Switch(
            checked = isEnabled,
            onCheckedChange = { onToggle(it) },
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = Color.Black,
                uncheckedThumbColor = Color.White,
                uncheckedTrackColor = Color.Gray
            ),
            modifier = Modifier
                .scale(0.6f)
        )
    }
}
