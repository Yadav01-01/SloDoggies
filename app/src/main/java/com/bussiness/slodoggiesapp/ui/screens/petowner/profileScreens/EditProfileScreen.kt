package com.bussiness.slodoggiesapp.ui.screens.petowner.profileScreens

import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.ui.component.PetOwner.CommonBlueButton
import com.bussiness.slodoggiesapp.ui.component.PetOwner.CommonTopAppBar
import com.bussiness.slodoggiesapp.ui.component.PetOwner.CommonTopAppBarProfile
import com.bussiness.slodoggiesapp.ui.component.PetOwner.CommonWhiteButton
import com.bussiness.slodoggiesapp.ui.component.PetOwner.CustomOutlinedTextField
import com.bussiness.slodoggiesapp.ui.component.PetOwner.Dialog.CustomDropdownMenuUpdated
import com.bussiness.slodoggiesapp.ui.component.saveBitmapToCache
import com.joelkanyi.jcomposecountrycodepicker.component.KomposeCountryCodePicker
import com.joelkanyi.jcomposecountrycodepicker.component.rememberKomposeCountryCodePickerState
import java.io.File


@Composable
fun EditProfileScreen(navController: NavController = rememberNavController()){
    var name by remember { mutableStateOf("") }
    var mobileNumber by remember { mutableStateOf("+1 (555) 123 456") }
    var email by remember { mutableStateOf("merrysglobalogales.com") }
    var bio by remember { mutableStateOf("") }
    var relation by remember { mutableStateOf("") }
    var showRelationDropdown by remember { mutableStateOf(false) }
    val relationOptions =
        listOf("Father", "Mother", "Partner", "Etc")
    var phoneNumber by rememberSaveable { mutableStateOf("") }
    var isMobileVerified by remember { mutableStateOf(false) }
    var isEmailVerified by remember { mutableStateOf(false) }
    val state = rememberKomposeCountryCodePickerState(
//            limitedCountries = listOf("KE", "UG", "TZ", "RW", "SS", "Togo", "+260", "250", "+211", "Mali", "Malawi"),
//            priorityCountries = listOf("SA", "KW", "BH", "QA"),
//            showCountryCode = true,
//            showCountryFlag = true,
//            defaultCountryCode = "KE",
    )
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
            title = "Edit Profile",
            onBackClick = { navController.popBackStack() },
            settingsIconTint= Color.Black,
            onSettingsClick= { },
            dividerColor = Color(0xFF656565),
        )
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

            }

            Spacer(modifier = Modifier.height(32.dp))

            // Name Field
            CustomOutlinedTextField(
                value = name,
                onValueChange = { name = it },
                placeholder = "Enter name",
                label = "Pet Parent Name"
            )

            Spacer(modifier = Modifier.height(16.dp))

            Column {
                Text(
                    text = "Mobile Number",
                    fontSize = 15.sp,
                    fontFamily = FontFamily(Font(R.font.outfit_medium)),
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                KomposeCountryCodePicker(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            width = 1.dp,
                            color = Color(0xFF949494), // Border color
                            shape = RoundedCornerShape(12.dp) // Rounded corners
                        ),
                    text = phoneNumber,

                    onValueChange = { phoneNumber = it },
                    placeholder = {
                        Text(
                            text = "Phone Number",
                            fontFamily = FontFamily(Font(R.font.outfit_regular)),
                            color = Color(0xFF949494)

                        )
                    },
                    shape = MaterialTheme.shapes.medium,
                    colors =
                        OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF00ACC1),
                            unfocusedBorderColor = Color(0xFF949494),
                            focusedContainerColor = Color(0xFFE5EFF2),
                            unfocusedContainerColor = Color.White,

                            ),
                    state = state,

                    interactionSource = remember { MutableInteractionSource() }.also { interactionSource ->
                        LaunchedEffect(interactionSource) {
                            interactionSource.interactions.collect {
                                if (it is PressInteraction.Release) {
                                    Log.e(
                                        "CountryCodePicker",
                                        "clicked",
                                    )
                                }
                            }
                        }
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next,
                    ),
                    textStyle = TextStyle(
                        color = Color(0xFF949494) // Teal color for country code
                    ), trailingIcon = {

                        if (isMobileVerified) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_verified_icon), // Replace with your check icon
                                contentDescription = "Verified",
                                tint = Color(0xFF258694), // Green color for verified
                                modifier = Modifier
                                    .size(30.dp)
                                    .padding(end = 5.dp)
                            )
                        } else {

                            TextButton(
                                onClick = {
                                    isMobileVerified = true // Set to verified when clicked

                                },
                                modifier = Modifier.padding(end = 4.dp)
                            ) {
                                Text(
                                    text = "verify", // lowercase as shown in image
                                    color = Color(0xFF258694),
                                    fontFamily = FontFamily(Font(R.font.outfit_regular)),
                                    fontSize = 14.sp
                                )
                            }
                        }
                    }
                )
            }


            Spacer(modifier = Modifier.height(16.dp))

            // Email Field (read-only with verify button)
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Email",
                        fontSize = 15.sp,
                        fontFamily = FontFamily(Font(R.font.outfit_medium)),
                        color = Color.Black
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = "merry@slodoggies.com",
                    onValueChange = {},
                    readOnly = true,
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = TextStyle(
                        color = Color(0xFF949494),
                        fontFamily = FontFamily(Font(R.font.outfit_regular))
                    ),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF00ACC1),
                        unfocusedBorderColor = Color(0xFF949494)
                    ),
                    shape = RoundedCornerShape(12.dp),
                    trailingIcon = {

                        if (isEmailVerified) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_verified_icon), // Replace with your check icon
                                contentDescription = "Verified",
                                tint = Color(0xFF258694), // Green color for verified
                                modifier = Modifier
                                    .size(30.dp)
                                    .padding(end = 5.dp)
                            )
                        } else {

                            TextButton(
                                onClick = {
                                    isEmailVerified = true // Set to verified when clicked

                                },
                                modifier = Modifier.padding(end = 4.dp)
                            ) {
                                Text(
                                    text = "verify", // lowercase as shown in image
                                    color = Color(0xFF258694),
                                    fontFamily = FontFamily(Font(R.font.outfit_regular)),
                                    fontSize = 14.sp
                                )
                            }
                        }
                    }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))


            CustomDropdownMenuUpdated(
                value = relation,
                onValueChange = { relation = it },
                options = relationOptions,
                label = "Relation to Pet",
                placeholder = "Select",
                isExpanded = showRelationDropdown,
                onExpandedChange = { showRelationDropdown = it },
            )
            // Bio Field
            CustomOutlinedTextField(
                value = bio,
                onValueChange = { bio = it },
                placeholder = "Enter Bio",
                label = "Bio"
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Bottom Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
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
fun EditProfileScreenPreview() {
    MaterialTheme {
        EditProfileScreen()
    }
}
