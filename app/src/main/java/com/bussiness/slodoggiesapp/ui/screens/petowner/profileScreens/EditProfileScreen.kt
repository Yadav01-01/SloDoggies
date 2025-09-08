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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.navigation.Routes
import com.bussiness.slodoggiesapp.ui.component.businessProvider.CustomDropdownBox
import com.bussiness.slodoggiesapp.ui.component.businessProvider.FormHeadingText
import com.bussiness.slodoggiesapp.ui.component.businessProvider.ScreenHeadingText
import com.bussiness.slodoggiesapp.ui.component.common.EmailTextField
import com.bussiness.slodoggiesapp.ui.component.common.PhoneNumber
import com.bussiness.slodoggiesapp.ui.component.petOwner.CommonBlueButton
import com.bussiness.slodoggiesapp.ui.component.petOwner.CustomOutlinedTextField
import com.bussiness.slodoggiesapp.ui.component.saveBitmapToCache
import com.bussiness.slodoggiesapp.ui.dialog.UpdatedDialogWithExternalClose
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor
import com.bussiness.slodoggiesapp.viewModel.petOwner.EditProfileViewModel


@Composable
fun EditProfileScreenPet(navController: NavHostController, viewModel: EditProfileViewModel = hiltViewModel(),) {

    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(navController.currentBackStackEntry) {
        navController.currentBackStackEntry
            ?.savedStateHandle
            ?.getLiveData<String>("verification_result")
            ?.observe(lifecycleOwner) { result ->
                when (result) {
                    "dialogPhone" -> viewModel.setPhoneVerified(true)
                    "dialogEmail" -> viewModel.setEmailVerified(true)
                }
            }
    }

    val launcherGallery = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uiState.profileImageUri = uri
    }

    val launcherCamera = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap: Bitmap? ->
        bitmap?.let {
            val uri = saveBitmapToCache(context, it)
            uiState.profileImageUri = uri
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            launcherCamera.launch(null) // Launch camera if permission granted
        } else {
            Toast.makeText(context, "Camera permission is required", Toast.LENGTH_SHORT).show()
        }
    }

    fun launchCameraWithPermissionCheck() {
        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(context, android.Manifest.permission.CAMERA) -> {
                launcherCamera.launch(null)
            }
            else -> {
                permissionLauncher.launch(android.Manifest.permission.CAMERA)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        ScreenHeadingText(textHeading = "My Profile", onBackClick = { navController.popBackStack() }, onSettingClick = { navController.navigate(Routes.SETTINGS_SCREEN)  })

        HorizontalDivider(modifier = Modifier
            .fillMaxWidth()
            .height(2.dp)
            .background(PrimaryColor))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Add Photo Section
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier.size(110.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(130.dp)
                            .clip(CircleShape)

                    ) {
                        AsyncImage(
                            model = uiState.profileImageUri,
                            contentDescription = "Parent Photo",
                            contentScale = ContentScale.Crop,
                            placeholder =  painterResource(id = R.drawable.ic_black_profile_icon),
                            error =  painterResource(id = R.drawable.ic_black_profile_icon),
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape)

                        )
                    }

                    // Camera icon to trigger image selection
                    Image(
                        painter = painterResource(id = R.drawable.ic_post_icon),
                        contentDescription = "Add Photo",
                        modifier = Modifier
                            .size(35.dp)
                            .align(Alignment.BottomEnd)
                            .clickable {
                                viewModel.toggleImagePickerDialog()
                            }
                    )
                }

            }

            Spacer(modifier = Modifier.height(32.dp))

            // Name Field
            CustomOutlinedTextField(
                value = uiState.name,
                onValueChange = { viewModel.updateName(it) },
                placeholder = stringResource(R.string.enter_name),
                label = stringResource(R.string.pet_parent_name)
            )

            Spacer(modifier = Modifier.height(16.dp))

            FormHeadingText(stringResource(R.string.mobile_number))

            Spacer(Modifier.height(8.dp))

            PhoneNumber(
                phone = uiState.mobileNumber,
                onPhoneChange = { viewModel.updateMobileNumber(it) },
                onVerify = { viewModel.onVerify(navController,"dialogPhone",uiState.mobileNumber) },
                isVerified = uiState.isMobileVerified
            )

            Spacer(modifier = Modifier.height(16.dp))

            FormHeadingText(stringResource(R.string.email))

            Spacer(Modifier.height(8.dp))

            EmailTextField(
                email = uiState.email,
                onEmailChange = { viewModel.updateEmail(it) },
                onVerify = { viewModel.onVerify(navController,"dialogEmail",uiState.email) },
                isVerified = uiState.isEmailVerified,
                placeholder = "merry@Slodoggies.com"
            )

            Spacer(modifier = Modifier.height(16.dp))

            FormHeadingText(stringResource(R.string.relation_to_pet))

            Spacer(Modifier.height(8.dp))

            CustomDropdownBox(
                label = uiState.relation.ifEmpty { "Select" },
                items = uiState.relationOptions,
                selectedItem = uiState.relation, // Current selected value
                onItemSelected = { selected ->
                    viewModel.updateRelation(selected)
                }
            )

            Spacer(Modifier.height(16.dp))

            // Bio Field
            CustomOutlinedTextField(
                value = uiState.bio,
                onValueChange = { viewModel.updateBio(it) },
                placeholder = stringResource(R.string.enter_bio),
                label =stringResource(R.string.bio),
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Bottom Buttons
            CommonBlueButton(
                text = stringResource(R.string.save_changes),
                fontSize = 15.sp,
                onClick = { viewModel.toggleUpdateProfileDialog() },
                modifier = Modifier.padding(horizontal = 45.dp),
            )
        }

    }
    if (uiState.updateProfileDialog){
        UpdatedDialogWithExternalClose(
            onDismiss = { viewModel.hideUpdateProfileDialog(navController) },
            iconResId = R.drawable.ic_sucess_p,
            text = "Profile Updated!",
            description = "Your information has been saved.\n" +
                    "Thanks for keeping things up to date!"
        )
    }
    if (uiState.showImagePickerDialog) {
        androidx.compose.material.AlertDialog(
            onDismissRequest = { viewModel.hideImagePickerDialog() },
            title = { Text("Select Option") },
            buttons = {
                Column(Modifier.fillMaxWidth().wrapContentHeight().padding(16.dp)) {
                    Text("Camera", Modifier
                        .clickable {
                            viewModel.hideImagePickerDialog()
                            launchCameraWithPermissionCheck()
                        }.fillMaxWidth()
                        .padding(8.dp))

                    Text("Gallery", Modifier
                        .clickable {
                            viewModel.hideImagePickerDialog()
                            launcherGallery.launch("image/*")
                        }.fillMaxWidth()
                        .padding(8.dp))
                }
            }
        )
    }

}





@Preview(showBackground = true)
@Composable
fun EditProfileScreenPreview() {
    val viewModel : EditProfileViewModel = hiltViewModel()
    val navController = NavHostController(LocalContext.current)
    MaterialTheme {
        EditProfileScreenPet(navController,viewModel)
    }
}
