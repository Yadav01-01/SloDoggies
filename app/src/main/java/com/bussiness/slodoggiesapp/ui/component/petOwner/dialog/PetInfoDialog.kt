package com.bussiness.slodoggiesapp.ui.component.petOwner.dialog

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.model.petOwner.PetInfo
import com.bussiness.slodoggiesapp.ui.component.businessProvider.FormHeadingText
import com.bussiness.slodoggiesapp.ui.component.businessProvider.ScrollableDropdownBox
import com.bussiness.slodoggiesapp.ui.component.petOwner.CommonBlueButton
import com.bussiness.slodoggiesapp.ui.component.petOwner.CommonWhiteButton
import com.bussiness.slodoggiesapp.ui.component.petOwner.CustomOutlinedTextField
import com.bussiness.slodoggiesapp.ui.screens.petowner.post.content.Person
import com.bussiness.slodoggiesapp.ui.screens.petowner.post.content.PersonItem
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor
import com.bussiness.slodoggiesapp.ui.theme.TextGrey
import com.bussiness.slodoggiesapp.viewModel.petOwner.PetInfoViewModel
import com.bussiness.slodoggiesapp.viewModel.petOwner.UiEvent
import java.io.File


@Composable
fun PetInfoDialog(
    title: String,
    onDismiss: () -> Unit = {},
    addPet: (PetInfo) -> Unit = {},
    onContinueClick: (PetInfo) -> Unit,
    onProfile : Boolean = false
) {
    val viewModel: PetInfoViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()
    val context =  LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.ShowToast -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
                is UiEvent.ContinueSuccess -> {
                    onContinueClick(event.petInfo)
                }
                is UiEvent.AddPetSuccess -> {
                    addPet(event.petInfo)
                }
                is UiEvent.CloseDialog -> {
                    onDismiss()
                }
            }
        }
    }


    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            decorFitsSystemWindows = false
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .imePadding(), // pushes content above keyboard
            contentAlignment = Alignment.BottomCenter
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
            ) {
                // Close button above the surface
                Box(
                    Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_cross_iconx),
                        contentDescription = null,
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .clip(CircleShape)
                            .clickable { onDismiss() }
                    )
                }

                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp),
                    shape = RoundedCornerShape(12.dp),
                    color = Color.White
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp)
                            .imePadding(),
                        verticalArrangement = Arrangement.spacedBy(16.dp) // common spacing
                    ) {
                        item {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = title,
                                    style = MaterialTheme.typography.titleMedium,
                                    color = Color.Black,
                                    fontFamily = FontFamily(Font(R.font.outfit_medium)),
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Medium,
                                    modifier = Modifier.weight(1f)
                                )

                                if (!onProfile) {
                                    Text(
                                        text = stringResource(id = R.string.skip),
                                        style = MaterialTheme.typography.titleMedium,
                                        color = PrimaryColor,
                                        fontFamily = FontFamily(Font(R.font.outfit_medium)),
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Medium,
                                        textAlign = TextAlign.End,
                                        modifier = Modifier.clickable { onDismiss() }
                                    )
                                }
                            }
                        }

                        item {
                            HorizontalDivider(thickness = 1.dp, color = TextGrey)
                        }

//                    item {
//                        PersonItem(
//                            person = samplePeople,
//                            selected = false,
//                            onClick = { }
//                        )
//                    }

                        item {
                            AddPhotoSection(
                                onPhotoSelected = { uri ->
                                    viewModel.setSelectedPhoto(uri)
                                }
                            )
                        }

                        item {
                            CustomOutlinedTextField(
                                value = uiState.petName,
                                onValueChange = { viewModel.updatePetName(it) },
                                placeholder = stringResource(R.string.placeholder_pet_name),
                                label = stringResource(R.string.label_pet_name),
                            )
                        }

                        item {
                            CustomOutlinedTextField(
                                value = uiState.petBreed,
                                onValueChange = { viewModel.updatePetBreed(it) },
                                placeholder = stringResource(R.string.placeholder_pet_breed),
                                label = stringResource(R.string.label_pet_breed)
                            )
                        }

                        item {
                            FormHeadingText("Pet Age")
                            Spacer(Modifier.height(8.dp))
                            ScrollableDropdownBox(
                                label = uiState.petAge.ifEmpty { "Enter pet age" },
                                items = viewModel.ageOptions,
                                selectedItem = uiState.petAge,
                                onItemSelected = { viewModel.updatePetAge(it) }
                            )
                        }

                        item {
                            CustomOutlinedTextField(
                                value = uiState.petBio,
                                onValueChange = { viewModel.updatePetBio(it) },
                                placeholder = stringResource(R.string.placeholder_pet_bio),
                                label = stringResource(R.string.label_pet_bio)
                            )
                        }

                        item {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                CommonWhiteButton(
                                    text = if (onProfile) stringResource(R.string.cancel) else stringResource(R.string.continue_btn),
                                    onClick = { viewModel.onContinue(onProfile) },
                                    modifier = Modifier.weight(1f)
                                )
                                CommonBlueButton(
                                    text = stringResource(R.string.add_pet),
                                    fontSize = 16.sp,
                                    onClick = { viewModel.onAddPet() },
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                    }

                }
            }
        }
    }
}



val samplePeople = Person("1", "Jimmy", "https://example.com/jimmy.jpg")


@Composable
fun AddPhotoSection(
    modifier: Modifier = Modifier,
    onPhotoSelected: (Uri?) -> Unit
) {
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current

    // Camera launcher
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        bitmap?.let {
            val uri = saveBitmapToCache(context, it)
            selectedImageUri = uri
            onPhotoSelected(uri)
        }
    }

    // Permission launcher
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            cameraLauncher.launch(null)
        } else {
            Toast.makeText(context, "Camera permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    // Gallery launcher
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        selectedImageUri = uri
        onPhotoSelected(uri)
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .clickable {
                    showPhotoPickerDialog(
                        context,
                        onCameraClick = {
                            if (ContextCompat.checkSelfPermission(
                                    context, Manifest.permission.CAMERA
                                ) == PackageManager.PERMISSION_GRANTED
                            ) {
                                cameraLauncher.launch(null)
                            } else {
                                permissionLauncher.launch(Manifest.permission.CAMERA)
                            }
                        },
                        onGalleryClick = { galleryLauncher.launch("image/*") }
                    )
                },
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = selectedImageUri,
                contentDescription = stringResource(R.string.cd_pet_photo),
                placeholder = painterResource(id = R.drawable.ic_black_profile_icon),
                error = painterResource(id = R.drawable.ic_black_profile_icon),
                modifier = Modifier
                    .size(70.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Image(
                painter = painterResource(id = R.drawable.ic_post_icon),
                contentDescription = stringResource(R.string.cd_add_photo),
                modifier = Modifier
                    .size(25.dp)
                    .align(Alignment.BottomEnd)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(R.string.add_photo),
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Black,
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(R.font.outfit_medium))
        )
    }
}


fun saveBitmapToCache(context: Context, bitmap: Bitmap): Uri {
    val file = File(context.cacheDir, "temp_${System.currentTimeMillis()}.jpg")
    file.outputStream().use { out ->
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
    }
    return FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
}

fun showPhotoPickerDialog(
    context: Context,
    onCameraClick: () -> Unit,
    onGalleryClick: () -> Unit
) {
    AlertDialog.Builder(context)
        .setTitle("Select Option")
        .setItems(arrayOf("Camera", "Gallery")) { dialog, which ->
            when (which) {
                0 -> onCameraClick()
                1 -> onGalleryClick()
            }
            dialog.dismiss()
        }
        .show()
}



@Preview(showBackground = true)
@Composable
fun PetInfoDialogPreview() {
    PetInfoDialog(
        title = "Tell us about your pet!",
        onDismiss = {},
        addPet = {},
        onContinueClick = {},
        onProfile = false
    )
}

