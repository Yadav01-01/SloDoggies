package com.bussiness.slodoggiesapp.ui.screens.businessprovider.post.content


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
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
import com.bussiness.slodoggiesapp.viewModel.businessProvider.PostContentViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PromotionScreenContent(
    onClickLocation: () -> Unit,
    onClickSave: () -> Unit,
    viewModel: PostContentViewModel = hiltViewModel()
) {
    val title by viewModel.title.collectAsState()
    val adDescription by viewModel.adDescription.collectAsState()
    val postalCode by viewModel.promoPostalCode.collectAsState()
    val termsAndConditions by viewModel.termsAndConditions.collectAsState()
    val serviceLocation by viewModel.serviceLocation.collectAsState()
    var selectedServices by remember { mutableStateOf("") }
    var isContactDisplayEnabled by remember { mutableStateOf(true) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp)

    ) {
        item {
            FormHeadingText(stringResource(R.string.upload_media))
            Spacer(Modifier.height(10.dp))
            MediaUploadSection(maxImages = 10) { uri ->
//                viewModel.addPetImage(uri)
            }
        }

        item {
            FormHeadingText(stringResource(R.string.ad_title))
            Spacer(Modifier.height(10.dp))
            InputField(
                placeholder = stringResource(R.string.enter_title),
                input = title,
                onValueChange = { viewModel.updateTitle(it) }
            )
        }

        item {
            FormHeadingText(stringResource(R.string.ad_desc))
            Spacer(Modifier.height(10.dp))
            DescriptionBox(
                placeholder = stringResource(R.string.enter_des),
                value = adDescription,
                onValueChange = { viewModel.updateAdDescription(it) }
            )
        }

        item {
            FormHeadingText(stringResource(R.string.category))
            Spacer(Modifier.height(10.dp))
            CategoryInputField()
        }

        item {
            FormHeadingText(stringResource(R.string.service))
            Spacer(Modifier.height(10.dp))
            CustomDropdownBox(
                label = selectedServices.ifEmpty { "Select Service" }.toString(),
                items = listOf("1", "2", "3","4"),
                selectedItem = selectedServices,
                onItemSelected = { selectedServices = it }
            )
        }

        item {
            DateTimePickerScreen(textHeading = stringResource(R.string.expiry_date_time))
        }

        item {
            FormHeadingText(stringResource(R.string.enter_terms_condition))
            Spacer(Modifier.height(10.dp))
            DescriptionBox(
                value = termsAndConditions,
                onValueChange = { viewModel.updateTermsAndConditions(it) },
                placeholder = "Enter here"
            )
        }

        item {
            FormHeadingText(stringResource(R.string.Service_Location))
            Spacer(Modifier.height(10.dp))
            InputField(
                placeholder = stringResource(R.string.dummy_loc),
                input = serviceLocation,
                onValueChange = { viewModel.updateServiceLocation(it) }
            )
        }

        item {
            ToggleRow(title = stringResource(R.string.contact_info_display), isEnabled = isContactDisplayEnabled, onToggle = { isContactDisplayEnabled = it })
            InputField(
                input = termsAndConditions,
                onValueChange = { viewModel.updateTermsAndConditions(it) },
                placeholder = stringResource(R.string.enter_mobile_no)
            )
        }

        item {
            SubmitButton(
                modifier = Modifier,
                buttonText = "Save & Next",
                onClickButton = { onClickSave() }
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
