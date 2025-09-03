package com.bussiness.slodoggiesapp.ui.screens.businessprovider

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.ui.component.businessProvider.CategoryInputField
import com.bussiness.slodoggiesapp.ui.component.businessProvider.FormHeadingText
import com.bussiness.slodoggiesapp.ui.component.businessProvider.InputField
import com.bussiness.slodoggiesapp.ui.component.businessProvider.ProfileImageWithUpload
import com.bussiness.slodoggiesapp.ui.component.businessProvider.SubmitButton
import com.bussiness.slodoggiesapp.ui.component.businessProvider.TopHeadingText
import com.bussiness.slodoggiesapp.ui.component.common.MediaUploadSection
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor
import com.bussiness.slodoggiesapp.viewModel.businessProvider.EditBusinessViewModel

@Composable
fun EditBusinessScreen(navController: NavHostController,viewModel: EditBusinessViewModel = hiltViewModel()){

    val businessName by viewModel.businessName.collectAsState()
    val email by viewModel.email.collectAsState()
    val location by viewModel.location.collectAsState()
    val url by viewModel.url.collectAsState()
    val contact by viewModel.contact.collectAsState()

    Column(modifier = Modifier.fillMaxSize().background(Color.White)) {

        TopHeadingText(textHeading = stringResource(R.string.Edit_Business), onBackClick = { navController.popBackStack()})

        HorizontalDivider(modifier = Modifier.fillMaxWidth().height(2.dp).background(PrimaryColor))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
                .background(Color.White)
        ) {

            Spacer(Modifier.height(5.dp))

            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                ProfileImageWithUpload( onPhotoSelected = { uri ->
                    viewModel.selectImage(uri)
                })
            }

            FormHeadingText("Business name")

            Spacer(Modifier.height(10.dp))

            InputField(input = businessName, onValueChange = { viewModel.updateBusinessName(it)}, placeholder = "Enter name")

            Spacer(Modifier.height(15.dp))

            FormHeadingText("Email")

            Spacer(Modifier.height(10.dp))

            InputField(input = email, onValueChange = { viewModel.updateEmail(it)}, placeholder = "Enter Email")

            Spacer(Modifier.height(15.dp))

            FormHeadingText("Category")

            Spacer(Modifier.height(10.dp))

            CategoryInputField()

            Spacer(Modifier.height(15.dp))

            FormHeadingText("Location")

            Spacer(Modifier.height(10.dp))

            InputField(input = location, onValueChange = { viewModel.updateLocation(it)}, placeholder = "Enter Location")

            Spacer(Modifier.height(15.dp))

            FormHeadingText("Website")

            Spacer(Modifier.height(10.dp))

            InputField(input =url, onValueChange = { viewModel.updateUrl(it)}, placeholder = "URL")

            Spacer(Modifier.height(15.dp))

            FormHeadingText("Contact Number")

            Spacer(Modifier.height(10.dp))

            InputField(input = contact, onValueChange = { viewModel.updateContact(it)}, placeholder = "Enter Contact")

            Spacer(Modifier.height(15.dp))

            FormHeadingText("Available Days/Hours")

            Spacer(Modifier.height(10.dp))

//        InputField()

            Spacer(Modifier.height(15.dp))

            FormHeadingText("Upload Verification Docs. (Optional)")

            Spacer(Modifier.height(10.dp))

            MediaUploadSection()

            Spacer(Modifier.height(20.dp))

            SubmitButton(modifier = Modifier,buttonText = "Save Changes", onClickButton = { }, buttonTextSize = 16)

        }
    }
}