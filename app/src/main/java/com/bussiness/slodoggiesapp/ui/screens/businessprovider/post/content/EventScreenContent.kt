package com.bussiness.slodoggiesapp.ui.screens.businessprovider.post.content

import android.os.Build
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
import com.bussiness.slodoggiesapp.ui.screens.petowner.post.content.DateTimePickerScreen
import com.bussiness.slodoggiesapp.viewModel.businessProvider.PostContentViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EventScreenContent(
    onClickLocation: () -> Unit,
    onClickSubmit: () -> Unit,
    viewModel: PostContentViewModel = hiltViewModel()
) {
    val eventTitle by viewModel.eventTitle.collectAsState()
    val eventDescription by viewModel.eventDescription.collectAsState()
    val postalCode by viewModel.eventPostalCode.collectAsState()
    val streetAddress by viewModel.streetAddress.collectAsState()
    val areaSector by viewModel.areaSector.collectAsState()
    val landmark by viewModel.landmark.collectAsState()
    var showDialog by remember { mutableStateOf(false) }

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
            MediaUploadSection(maxImages = 6) { uri ->
//                viewModel.addPetImage(uri)
            }
        }

        item {
            FormHeadingText(stringResource(R.string.event_title))
            Spacer(Modifier.height(10.dp))
            InputField(placeholder = stringResource(R.string.event_title), input = eventTitle, onValueChange = { viewModel.updateEventTitle(it) })
        }

        item {
            FormHeadingText(stringResource(R.string.event_des))
            Spacer(Modifier.height(10.dp))
            DescriptionBox( placeholder = stringResource(R.string.enter_des), value = eventDescription, onValueChange = { viewModel.updateEventDescription(it) })
        }

        item {
            DateTimePickerScreen(textHeading = stringResource(R.string.event_sdate))
            Spacer(Modifier.height(15.dp))
            DateTimePickerScreen(textHeading = stringResource(R.string.event_edate))
        }

        item {
            FormHeadingText(stringResource(R.string.Business_address))
            InputField(
                placeholder = stringResource(R.string.Enter_Business_Address),
                input = streetAddress ,
                onValueChange = { viewModel.updateStreetAddress(it) }
            )
        }

        item {
            FormHeadingText(stringResource(R.string.area_sector))
            InputField(
                placeholder = stringResource(R.string.enter_area_sector),
                input = areaSector,
                onValueChange = { viewModel.updateAreaSector(it) }
            )
        }

        item {
            FormHeadingText(stringResource(R.string.landmark))
            InputField(
                placeholder = stringResource(R.string.enter_landmark),
                input = landmark,
                onValueChange = { viewModel.updateLandmark(it) }
            )
        }


        item {
            FormHeadingText(stringResource(R.string.zip_code))
            InputField(placeholder = stringResource(R.string.enter_zip_code), input = postalCode, onValueChange = { viewModel.updateEventPostalCode(it) })
        }

        item {
            Spacer(Modifier.height(25.dp))
            SubmitButton(modifier = Modifier, buttonText = "Post Event", onClickButton = { onClickSubmit() })
            Spacer(Modifier.height(30.dp))
        }
    }

    if (showDialog) {
        DateTimePickerDialog(
            showDialog = true,
            onDismissRequest = { showDialog = false },
            onDateTimeSelected = { dateTime ->
                println("Selected DateTime: $dateTime")
                showDialog = false
            }
        )
    }
}

