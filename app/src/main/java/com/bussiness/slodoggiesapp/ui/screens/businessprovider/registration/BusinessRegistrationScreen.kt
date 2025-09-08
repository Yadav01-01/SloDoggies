package com.bussiness.slodoggiesapp.ui.screens.businessprovider.registration

import android.annotation.SuppressLint
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.navigation.Routes
import com.bussiness.slodoggiesapp.ui.component.businessProvider.CategoryInputField
import com.bussiness.slodoggiesapp.ui.component.businessProvider.FormHeadingText
import com.bussiness.slodoggiesapp.ui.component.businessProvider.InputField
import com.bussiness.slodoggiesapp.ui.component.businessProvider.SubmitButton
import com.bussiness.slodoggiesapp.ui.component.businessProvider.TopHeadingText
import com.bussiness.slodoggiesapp.ui.component.businessProvider.TopStepProgressBar
import com.bussiness.slodoggiesapp.ui.component.common.MediaUploadSection
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor
import com.bussiness.slodoggiesapp.viewModel.businessProvider.BusinessRegistrationViewModel
import com.google.accompanist.flowlayout.FlowRow

@Composable
fun BusinessRegistrationScreen(navController: NavHostController,viewModel: BusinessRegistrationViewModel = hiltViewModel()){

    val name by viewModel.name.collectAsState()
    val email by viewModel.email.collectAsState()
    val location by viewModel.location.collectAsState()
    val url by viewModel.url.collectAsState()
    val contact by viewModel.contact.collectAsState()


    Column(modifier = Modifier.fillMaxSize()) {

        TopHeadingText(textHeading = stringResource(R.string.business_registration), onBackClick = { navController.popBackStack()})

        TopStepProgressBar(currentStep = 1, totalSteps = 3, modifier = Modifier)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {

            Spacer(Modifier.height(5.dp))

            FormHeadingText(stringResource(R.string.business_name))

            Spacer(Modifier.height(10.dp))

            InputField(input = name, onValueChange = { viewModel.updateName(it)}, placeholder = stringResource(R.string.enter_name))

            Spacer(Modifier.height(15.dp))

            FormHeadingText(stringResource(R.string.email))

            Spacer(Modifier.height(10.dp))

            InputField(input = email, onValueChange = { viewModel.updateEmail(it)}, placeholder = stringResource(R.string.enter_email))

            Spacer(Modifier.height(15.dp))

            FormHeadingText(stringResource(R.string.Upload_business_logo))

            Spacer(Modifier.height(10.dp))

            MediaUploadSection()

            Spacer(Modifier.height(8.dp))

            FormHeadingText(stringResource(R.string.Category))

            Spacer(Modifier.height(10.dp))

            CategoryInputField()

            Spacer(Modifier.height(15.dp))

            FormHeadingText(stringResource(R.string.location))

            Spacer(Modifier.height(10.dp))

            InputField(input = location, onValueChange = { viewModel.updateLocation(it)}, placeholder = stringResource(R.string.Enter_Location))

            Spacer(Modifier.height(15.dp))

            FormHeadingText(stringResource(R.string.website_))

            Spacer(Modifier.height(10.dp))

            InputField(input =url, onValueChange = { viewModel.updateUrl(it)}, placeholder = "URL")

            Spacer(Modifier.height(15.dp))

            FormHeadingText(stringResource(R.string.contact_number))

            Spacer(Modifier.height(10.dp))

            InputField(input = contact, onValueChange = { viewModel.updateContact(it)}, placeholder = "Enter Contact")

            Spacer(Modifier.height(15.dp))

            FormHeadingText(stringResource(R.string.available_days))

            Spacer(Modifier.height(10.dp))

//        InputField()

            Spacer(Modifier.height(15.dp))

            FormHeadingText(stringResource(R.string.Upload_verification_docs))

            Spacer(Modifier.height(10.dp))

            MediaUploadSection()

            Spacer(Modifier.height(20.dp))

            SubmitButton(modifier = Modifier,buttonText = stringResource(R.string.submit), onClickButton = { navController.navigate(Routes.ADD_SERVICE) }, buttonTextSize = 15)

        }
    }
}



@Preview(showBackground = true)
@Composable
fun PreviewBusinessRegistrationScreen() {
    // Use a dummy NavController for preview purposes
    val navController = rememberNavController()

    BusinessRegistrationScreen(navController = navController)
}
