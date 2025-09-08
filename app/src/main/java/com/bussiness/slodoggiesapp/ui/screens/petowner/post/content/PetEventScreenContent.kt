package com.bussiness.slodoggiesapp.ui.screens.petowner.post.content

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.ui.component.businessProvider.CustomDropdownBox
import com.bussiness.slodoggiesapp.ui.component.businessProvider.FormHeadingText
import com.bussiness.slodoggiesapp.ui.component.businessProvider.InputField
import com.bussiness.slodoggiesapp.ui.component.businessProvider.SubmitButton
import com.bussiness.slodoggiesapp.ui.component.common.MediaUploadSection
import com.bussiness.slodoggiesapp.ui.dialog.DateTimePickerDialog
import com.bussiness.slodoggiesapp.viewModel.businessProvider.PostContentViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PetEventScreenContent( onClickLocation: () -> Unit,onClickSubmit: () -> Unit,viewModel: PostContentViewModel = hiltViewModel()) {

    val eventTitle by viewModel.eventTitle.collectAsState()
    val eventDescription by viewModel.eventDescription.collectAsState()
    val postalCode by viewModel.postalCode.collectAsState()
    var selectedDuration by remember { mutableStateOf("") }
    val durations = listOf("30 min", "1 hour", "2 hours") // Example list
    var showDialog by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 16.dp, vertical = 5.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        item {
            FormHeadingText(stringResource(R.string.upload_media))
            Spacer(Modifier.height(10.dp))
            MediaUploadSection()
        }

        item {
            FormHeadingText(stringResource(R.string.event_title))
            Spacer(Modifier.height(10.dp))
            InputField(
                placeholder = stringResource(R.string.event_title),
                input = eventTitle,
                onValueChange = { viewModel.updateEventTitle(it) }
            )
        }

        item {
            FormHeadingText(stringResource(R.string.event_des))
            Spacer(Modifier.height(10.dp))
            InputField(
                modifier = Modifier.height(110.dp),
                placeholder = stringResource(R.string.event_des),
                input = eventDescription,
                onValueChange = { viewModel.updateEventDescription(it) }
            )
        }

        item {
            DateTimePickerScreen(textHeading = stringResource(R.string.event_sdate))
            DateTimePickerScreen(textHeading = stringResource(R.string.event_edate))
        }

        item {
            FormHeadingText(stringResource(R.string.event_duration))
            CustomDropdownBox(
                label = selectedDuration.ifEmpty { "Select Option" },
                items = durations,
                selectedItem = selectedDuration,
                onItemSelected = { selectedDuration = it }
            )
        }

        item {
            FormHeadingText("Zip Code")
            Spacer(Modifier.height(5.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable { onClickLocation() }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.precise_loc),
                    contentDescription = null,
                    tint = Color.Gray,
                    modifier = Modifier.wrapContentSize()
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "use my current location",
                    fontFamily = FontFamily(Font(R.font.poppins)),
                    fontSize = 12.sp,
                    color = Color.Black
                )
            }
            Spacer(Modifier.height(10.dp))
            InputField(
                placeholder = "Enter your Zip Code",
                input = postalCode,
                onValueChange = { viewModel.updatePostalCode(it) }
            )
        }

        item {
            Spacer(Modifier.height(25.dp))
            SubmitButton(
                modifier = Modifier,
                buttonText = "Post Event",
                onClickButton = { onClickSubmit() }
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
