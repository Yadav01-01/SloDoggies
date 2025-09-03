package com.bussiness.slodoggiesapp.ui.screens.businessprovider.profile

import android.app.AlertDialog
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.navigation.Routes
import com.bussiness.slodoggiesapp.ui.component.businessProvider.InputField
import com.bussiness.slodoggiesapp.ui.component.businessProvider.ProfileHeadingText
import com.bussiness.slodoggiesapp.ui.component.businessProvider.ProfileImageWithUpload
import com.bussiness.slodoggiesapp.ui.component.businessProvider.ScreenHeadingText
import com.bussiness.slodoggiesapp.ui.component.businessProvider.SubmitButton
import com.bussiness.slodoggiesapp.ui.component.petOwner.Dialog.saveBitmapToCache
import com.bussiness.slodoggiesapp.ui.dialog.UpdatedDialogWithExternalClose
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor
import com.bussiness.slodoggiesapp.viewModel.businessProvider.ProfileViewModel
import java.io.File

@Composable
fun EditProfileScreen(navController: NavHostController) {

    val viewModel: ProfileViewModel = hiltViewModel()
    val providerName by viewModel.providerName.collectAsState()
    val bio by viewModel.bio.collectAsState()
    val selectedImageUri by viewModel.imageUri.collectAsState()
    var showDialog by remember { mutableStateOf(false) }

    Column (modifier = Modifier.fillMaxSize().background(Color.White).padding( vertical = 8.dp)) {

        ScreenHeadingText(stringResource(R.string.edit_profile), onBackClick = { navController.popBackStack() }, onSettingClick = { navController.navigate(Routes.SETTINGS_SCREEN) })

        HorizontalDivider(modifier = Modifier.fillMaxWidth().height(2.dp).background(PrimaryColor))

        Spacer(Modifier.height(20.dp))

        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            ProfileImageWithUpload( onPhotoSelected = { uri ->
                viewModel.selectImage(uri)
            })
        }

        Spacer(Modifier.height(25.dp))

        ProfileHeadingText(stringResource(R.string.provide_name), modifier = Modifier.padding(horizontal = 15.dp))

        Spacer(Modifier.height(8.dp))

        InputField(input = providerName, onValueChange = { viewModel.updateProviderName(it) }, placeholder = stringResource(R.string.enter_name), modifier = Modifier.padding(horizontal = 15.dp))

        Spacer(Modifier.height(15.dp))

        ProfileHeadingText(stringResource(R.string.bio), modifier = Modifier.padding(horizontal = 15.dp))

        Spacer(Modifier.height(8.dp))

        InputField(input = bio, onValueChange = { viewModel.updateBio(it) }, placeholder = stringResource(R.string.Enter_Bio), modifier = Modifier.padding(horizontal = 15.dp))

        Spacer(Modifier.height(35.dp))

        SubmitButton(modifier = Modifier.padding(horizontal = 20.dp), buttonText = stringResource(R.string.Save_Changes), onClickButton = { showDialog = true }, buttonTextSize = 16)
    }
    if (showDialog) {
        UpdatedDialogWithExternalClose(onDismiss = { showDialog = false }, iconResId = R.drawable.ic_sucess_p, text = stringResource(R.string.Profile_Updated), description = stringResource(R.string.profile_update_description))
    }
}


@Preview(showBackground = true)
@Composable
fun EditProfileScreenPreview() {
    val navController = rememberNavController()
    EditProfileScreen(navController)
}
