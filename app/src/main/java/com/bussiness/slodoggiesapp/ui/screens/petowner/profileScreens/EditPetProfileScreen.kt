package com.bussiness.slodoggiesapp.ui.screens.petowner.profileScreens

import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.navigation.Routes
import com.bussiness.slodoggiesapp.ui.component.businessProvider.CustomDropdownBox
import com.bussiness.slodoggiesapp.ui.component.businessProvider.FormHeadingText
import com.bussiness.slodoggiesapp.ui.component.businessProvider.ScreenHeadingText
import com.bussiness.slodoggiesapp.ui.component.petOwner.CommonBlueButton
import com.bussiness.slodoggiesapp.ui.component.petOwner.CustomOutlinedTextField
import com.bussiness.slodoggiesapp.ui.component.petOwner.Dialog.CustomDropdownMenuUpdated
import com.bussiness.slodoggiesapp.ui.component.petOwner.SettingIconHeader
import com.bussiness.slodoggiesapp.ui.dialog.UpdatedDialogWithExternalClose
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor
import com.bussiness.slodoggiesapp.viewModel.petOwner.PetProfileViewModel

@Composable
fun EditPetProfileScreen(navController: NavHostController, viewModel: PetProfileViewModel = hiltViewModel()) {

    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    var selectedAge by remember { mutableStateOf("") }
    var managedBy by remember { mutableStateOf("") }

    // Gallery launcher
    val launcherGallery = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        viewModel.setImageUri(uri)
    }

    // Camera launcher
    val launcherCamera = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap: Bitmap? ->
        bitmap?.let { viewModel.saveBitmapAndSetUri(it) }
    }

    // Permission launcher
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            launcherCamera.launch(null)
        } else {
            Toast.makeText(context, "Camera permission is required", Toast.LENGTH_SHORT).show()
        }
    }

    fun launchCameraWithPermissionCheck() {
        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(context, android.Manifest.permission.CAMERA) -> {
                launcherCamera.launch(null)
            }
            else -> permissionLauncher.launch(android.Manifest.permission.CAMERA)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        ScreenHeadingText(stringResource(R.string.edit_pet_profile), onBackClick = { navController.popBackStack() }, onSettingClick = { navController.navigate(Routes.SETTINGS_SCREEN) })

        HorizontalDivider(modifier = Modifier.fillMaxWidth().height(2.dp).background(PrimaryColor))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp)
                .verticalScroll(rememberScrollState())
        ) {

            Spacer(modifier = Modifier.height(24.dp))

            // Profile Image
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                Box(modifier = Modifier.size(110.dp), contentAlignment = Alignment.Center) {
                    Box(
                        modifier = Modifier
                            .size(130.dp)
                            .clip(CircleShape)
                            .border(3.dp, Color(0xFF949494), CircleShape)
                    ) {
                        AsyncImage(
                            model = uiState.profileImageUri,
                            contentDescription = "Pet Photo",
                            contentScale = ContentScale.Crop,
                            placeholder = painterResource(id = R.drawable.ic_black_profile_icon),
                            error = painterResource(id = R.drawable.ic_black_profile_icon),
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape)
                        )
                    }

                    Image(
                        painter = painterResource(id = R.drawable.ic_post_icon),
                        contentDescription = "Add Photo",
                        modifier = Modifier
                            .size(35.dp)
                            .align(Alignment.BottomEnd)
                            .clickable { viewModel.toggleImagePicker(true) }
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Fields
            CustomOutlinedTextField(
                value = uiState.petName,
                onValueChange = viewModel::onPetNameChange,
                placeholder = "Enter pet name",
                label = "Pet Name"
            )
            Spacer(modifier = Modifier.height(16.dp))

            CustomOutlinedTextField(
                value = uiState.petBreed,
                onValueChange = viewModel::onPetBreedChange,
                placeholder = "Enter Breed",
                label = "Pet Breed"
            )
            Spacer(modifier = Modifier.height(16.dp))

            FormHeadingText("Pet Age")

            Spacer(Modifier.height(5.dp))

            CustomDropdownBox(
                label = selectedAge.ifEmpty { "Enter pet age" },
                items = listOf("0-5 Years", "5-10 Years", "10-20 Years"),
                selectedItem = selectedAge,
                onItemSelected = { selectedAge = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomOutlinedTextField(
                value = uiState.petBio,
                onValueChange = viewModel::onPetBioChange,
                placeholder = "Enter Bio",
                label = "Pet Bio"
            )

            Spacer(modifier = Modifier.height(16.dp))

            FormHeadingText("Managed by")

            Spacer(Modifier.height(5.dp))

            CustomDropdownBox(
                label = managedBy.ifEmpty { "Pet Mom" },
                items = listOf("Pet Mom", "Pet Dad"),
                selectedItem = managedBy,
                onItemSelected = { managedBy = it }
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Save Button
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                CommonBlueButton(
                    text = "Save Changes",
                    fontSize = 15.sp,
                    onClick = { viewModel.showConfirmationDialog(true) },
                    modifier = Modifier.padding(horizontal = 45.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }

    if(uiState.showConfirmationDialog){
        UpdatedDialogWithExternalClose(onDismiss = { viewModel.showConfirmationDialog(false)
            viewModel.saveChanges()}, iconResId = R.drawable.ic_sucess_p, text = "Pet Profile Updated!",
            description = "Your pet information has been saved. Thanks for keeping things up to date!")
    }

    // Image Picker Dialog
    if (uiState.showImagePickerDialog) {
        androidx.compose.material.AlertDialog(
            onDismissRequest = { viewModel.toggleImagePicker(false) },
            title = { Text("Select Option") },
            buttons = {
                Column(Modifier.padding(16.dp).fillMaxWidth()) {
                    Text("Camera", Modifier
                        .clickable {
                            viewModel.toggleImagePicker(false)
                            launchCameraWithPermissionCheck()
                        }.fillMaxWidth()
                        .padding(8.dp)
                    )
                    Text("Gallery", Modifier
                        .clickable {
                            viewModel.toggleImagePicker(false)
                            launcherGallery.launch("image/*")
                        }.fillMaxWidth()
                        .padding(8.dp)
                    )
                }
            }
        )
    }

    // Navigation
    if (uiState.navigateToProfile) {
        LaunchedEffect(Unit) {
            navController.navigate(Routes.PET_PROFILE_SCREEN)
            viewModel.resetNavigation()
        }
    }
}


@Preview(showBackground = true)
@Composable
fun EditPetProfileScreenPreview() {
    val navController = NavHostController(LocalContext.current)
    MaterialTheme {
        EditPetProfileScreen(navController)
    }
}

