package com.bussiness.slodoggiesapp.ui.screens.businessprovider.post.content

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.ui.component.businessProvider.FormHeadingText
import com.bussiness.slodoggiesapp.ui.component.businessProvider.InputField
import com.bussiness.slodoggiesapp.ui.component.businessProvider.SettingToggleItem
import com.bussiness.slodoggiesapp.ui.component.businessProvider.SubmitButton
import com.bussiness.slodoggiesapp.ui.screens.businessprovider.registration.UploadPlaceholder
import com.bussiness.slodoggiesapp.viewModel.businessProvider.PostContentViewModel

@Composable
fun EventScreenContent( onClickLocation: () -> Unit,onClickSubmit: () -> Unit,viewModel: PostContentViewModel = hiltViewModel()) {

    val eventTitle by viewModel.eventTitle.collectAsState()
    val eventDescription by viewModel.eventDescription.collectAsState()
    val postalCode by viewModel.eventPostalCode.collectAsState()
    val rsvpRequired by viewModel.rsvpRequired.collectAsState()
    val enableComments by viewModel.enableComments.collectAsState()

    Column(
        modifier = Modifier.run {
            fillMaxSize()
                .background(Color.White)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        }
    ) {
        FormHeadingText("Upload Media")

        Spacer(Modifier.height(10.dp))

        UploadPlaceholder()

        Spacer(Modifier.height(15.dp))

        FormHeadingText("Event Title")

        Spacer(Modifier.height(10.dp))

        InputField(modifier = Modifier.height(106.dp), placeholder = "Enter Title", input = eventTitle, onValueChange ={ viewModel.updateEventTitle(it)})

        Spacer(Modifier.height(15.dp))

        FormHeadingText("Event Description")

        Spacer(Modifier.height(10.dp))

        InputField(placeholder = "Enter Description", input = eventDescription, onValueChange ={ viewModel.updateEventDescription(it)})

        Spacer(Modifier.height(15.dp))

        FormHeadingText("Event Date And Time")

        InputField(input = eventTitle, onValueChange = { }, placeholder = "Select Date And Time")

        Spacer(Modifier.height(15.dp))

        FormHeadingText("Event Duration")

        InputField(input = eventTitle, onValueChange = { }, placeholder = "Select Duration")

        FormHeadingText("Location")

        Spacer(Modifier.height(5.dp))

        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable { onClickLocation() }) {
            Icon(
                painter = painterResource(id = R.drawable.precise_loc),
                contentDescription = null,
                tint = Color.Gray,
                modifier = Modifier.wrapContentSize()
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = "use my current location", fontFamily = FontFamily(Font(R.font.poppins)), fontSize = 12.sp, color = Color.Black)
        }

        Spacer(Modifier.height(10.dp))

        InputField(placeholder = "Postal Code", input = postalCode, onValueChange ={ viewModel.updateEventPostalCode(it)})

        Spacer(Modifier.height(15.dp))

        FormHeadingText("Additional Settings")

        Spacer(Modifier.height(10.dp))

        SettingToggleItem(label = "RSVP Required?", checked = rsvpRequired, onToggle = { viewModel.updateRsvpRequired(it) })

        SettingToggleItem(label = "Enable Comments", checked = enableComments, onToggle = { viewModel.updateEnableComments(it) })

        Spacer(Modifier.height(15.dp))

        SubmitButton(modifier = Modifier, buttonText = "Post Event", onClickButton = { onClickSubmit() })
    }
}
