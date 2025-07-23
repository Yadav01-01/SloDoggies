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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.navigation.Routes
import com.bussiness.slodoggiesapp.ui.component.petOwner.CommonBlueButton
import com.bussiness.slodoggiesapp.ui.component.petOwner.CommonTopAppBarProfile
import com.bussiness.slodoggiesapp.ui.component.petOwner.CustomOutlinedTextField
import com.bussiness.slodoggiesapp.ui.component.petOwner.Dialog.CustomDropdownMenuUpdated
import com.bussiness.slodoggiesapp.ui.component.saveBitmapToCache

@Composable
fun EditPetProfileScreen(navController: NavController = rememberNavController()) {

    var petName by remember { mutableStateOf("") }
    var petBreed by remember { mutableStateOf("") }
    var petAge by remember { mutableStateOf("") }
    var petBio by remember { mutableStateOf("") }
    var managedBy by remember { mutableStateOf("Pet Mom") }
    var showAgeDropdown by remember { mutableStateOf(false) }
    var showManagedByDropdown by remember { mutableStateOf(false) }


    val ageOptions =
        listOf("Puppy (0-1 year)", "Young (1-3 years)", "Adult (3-7 years)", "Senior (7+ years)")
    val managedByOptions = listOf("Pet Mom", "Pet Dad", "Family Member", "Caregiver")

    var profileImageUri by remember { mutableStateOf<Uri?>(null) }
    var showImagePickerDialog by remember { mutableStateOf(false) }

    val context = LocalContext.current

    val launcherGallery = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        profileImageUri = uri
    }

    val launcherCamera = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap: Bitmap? ->
        bitmap?.let {
            val uri = saveBitmapToCache(context, it)
            profileImageUri = uri
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
        CommonTopAppBarProfile(
            title = "Edit Pet Profile",
            onBackClick = { navController.popBackStack() },
            settingsIconTint= Color.Black,
            onSettingsClick= {   navController.navigate(Routes.PET_SETTINGS_SCREEN)},
            dividerColor = Color(0xFF656565),
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Header with close button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "",
                    fontSize = 18.sp,
                    fontFamily = FontFamily(Font(R.font.outfit_medium)),
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

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
                            .border(width = 3.dp,  color = Color(0xFF949494),shape = CircleShape)
                    ) {
                        profileImageUri?.let { uri ->
                            Image(
                                painter = rememberImagePainter(uri),
                                contentDescription = "Pet Photo",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(CircleShape)

                            )
                        } ?: Icon(
                            painter = painterResource(id = R.drawable.ic_black_profile_icon),
                            contentDescription = "Add Photo",
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
                                showImagePickerDialog = true
                            }
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Pet Name Field
            CustomOutlinedTextField(
                value = petName,
                onValueChange = { petName = it },
                placeholder = "Enter pet name",
                label = "Pet Name"
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Pet Breed Field
            CustomOutlinedTextField(
                value = petBreed,
                onValueChange = { petBreed = it },
                placeholder = "Enter Breed",
                label = "Pet Breed"
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Pet Age Dropdown
            CustomDropdownMenuUpdated(
                value = petAge,
                onValueChange = { petAge = it },
                options = ageOptions,
                label = "Pet Age",
                placeholder = "Enter pet age",
                isExpanded = showAgeDropdown,
                onExpandedChange = { showAgeDropdown = it },
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Pet Bio Field
            CustomOutlinedTextField(
                value = petBio,
                onValueChange = { petBio = it },
                placeholder = "Enter Bio",
                label = "Pet Bio"
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Managed By Dropdown
            CustomDropdownMenuUpdated(
                value = managedBy,
                onValueChange = { managedBy = it },
                options = managedByOptions,
                label = "Managed By",
                placeholder = "Select relationship",
                isExpanded = showManagedByDropdown,
                onExpandedChange = { showManagedByDropdown = it }
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Bottom Buttons
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                CommonBlueButton(
                    text = "Save Changes",
                    fontSize = 22.sp,
                    onClick = {
                        // navController.navigate(Routes.PET_MAIN_SCREEN)
                    },
                    modifier = Modifier.padding(horizontal = 45.dp),
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }



    if (showImagePickerDialog) {
        androidx.compose.material.AlertDialog(
            onDismissRequest = { showImagePickerDialog = false },
            title = { Text("Select Option") },
            buttons = {
                Column(Modifier.padding(16.dp)) {
                    Text("Camera", Modifier.clickable {
                        showImagePickerDialog = false
                        launchCameraWithPermissionCheck()
                    }.padding(8.dp))

                    Text("Gallery", Modifier.clickable {
                        showImagePickerDialog = false
                        launcherGallery.launch("image/*")
                    }.padding(8.dp))
                }
            }
        )
    }

}


@Preview(showBackground = true)
@Composable
fun EditPetProfileScreenPreview() {
    MaterialTheme {
        EditPetProfileScreen()
    }
}

