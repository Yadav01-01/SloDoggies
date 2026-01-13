package com.bussiness.slodoggiesapp.ui.screens.petowner.post.content

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.net.Uri
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.data.newModel.petlist.Data
import com.bussiness.slodoggiesapp.ui.component.businessProvider.DescriptionBox
import com.bussiness.slodoggiesapp.ui.component.businessProvider.FormHeadingText
import com.bussiness.slodoggiesapp.ui.component.businessProvider.InputField
import com.bussiness.slodoggiesapp.ui.component.businessProvider.SubmitButton
import com.bussiness.slodoggiesapp.ui.component.common.MediaUploadSection
import com.bussiness.slodoggiesapp.ui.component.petOwner.dialog.FillPetInfoDialog
import com.bussiness.slodoggiesapp.ui.dialog.UpdatedDialogWithExternalClose
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor
import com.bussiness.slodoggiesapp.util.LocationUtils.Companion.getAddressFromLatLng
import com.bussiness.slodoggiesapp.util.SessionManager
import com.bussiness.slodoggiesapp.viewModel.common.location.LocationAction
import com.bussiness.slodoggiesapp.viewModel.common.location.LocationViewModel
import com.bussiness.slodoggiesapp.viewModel.petOwner.createpostowner.PostCreateOwnerViewModel
import com.bussiness.slodoggiesapp.viewModel.petOwner.petadd.PetAddViewModel
import com.bussiness.slodoggiesapp.viewModel.petOwner.petlist.PetListViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.Priority
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode

@SuppressLint("ContextCastToActivity")
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PetPostScreenContent(onClickPost: () -> Unit) {

    var showPetInfoDialog by remember { mutableStateOf(false) }
    var petAddedSuccessDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val activity = LocalContext.current as Activity
    val permission = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)
    val viewModelLocation: LocationViewModel = hiltViewModel()
    val viewModelUpdatePet: PetAddViewModel = hiltViewModel()
    val viewModelPetList: PetListViewModel = hiltViewModel()
    val viewModelPostCreateOwner: PostCreateOwnerViewModel = hiltViewModel()
    val uiStatePostCreateOwner by viewModelPostCreateOwner.uiState.collectAsState()
    val action by viewModelLocation.action.collectAsState()
    val locationState by viewModelLocation.locationState.collectAsState()
    val updatePetListState by viewModelPetList.uiState.collectAsState()
    var wasPermissionGranted by remember { mutableStateOf(permission.status.isGranted) }
    val sessionManager = SessionManager(context)


    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val place = Autocomplete.getPlaceFromIntent(result.data!!)
            val placeLatLong=place.latLng
            val addressResult = getAddressFromLatLng(context, placeLatLong?.latitude?:0.000, placeLatLong?.longitude?:0.000)

            Log.d("Location", "zip: ${addressResult?.zip}")
            Log.d("Location", "city: ${addressResult?.city}")
            Log.d("Location", "state: ${addressResult?.state}")
            Log.d("Location", "street: ${addressResult?.street}")
            Log.d("Location", "latitude: ${placeLatLong?.latitude ?: 0.000}")
            Log.d("Location", "longitude: ${placeLatLong?.longitude ?: 0.000}")

            locationState.address= place.address
            locationState.latitude=placeLatLong?.latitude
            locationState.longitude=placeLatLong?.longitude

            viewModelPostCreateOwner.onLocation(place.address?:"")
            viewModelPostCreateOwner.onLatitude(placeLatLong?.latitude.toString())
            viewModelPostCreateOwner.onLongitude(placeLatLong?.latitude.toString())

        }
    }

    val gpsLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult()
    ) { resultCode ->
        if (resultCode.resultCode == Activity.RESULT_OK) {
            // User clicked YES → fetch location
            viewModelLocation.fetchLocation()
        } else {
            Toast.makeText(context, "GPS is required to fetch location", Toast.LENGTH_SHORT).show()
        }
    }

    // ---------- When status change this handler work ----------
    LaunchedEffect(permission.status) {
        if (permission.status.isGranted && !wasPermissionGranted) {
            // Permission was JUST granted
            val lm = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            viewModelLocation.checkRequirements(
                hasPermission = true,
                isGPSEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER)
            )
        }
        // Update the previous state
        wasPermissionGranted = permission.status.isGranted
    }

    // ---------- ACTION handler ----------
    LaunchedEffect(action) {
        when (action) {
            is LocationAction.RequestPermission -> {
                permission.launchPermissionRequest()
                viewModelLocation.clearAction()
            }

            is LocationAction.AskToEnableGPS -> {
                askToEnableGPS(activity,gpsLauncher,viewModelLocation)
                viewModelLocation.clearAction()
            }

            is LocationAction.FetchLocation -> {
                viewModelLocation.fetchLocation()
                viewModelLocation.clearAction()
            }

            is LocationAction.FetchSuccess -> {
                viewModelLocation.clearAction()
            }
            is LocationAction.Error -> {
                Toast.makeText(context, (action as LocationAction.Error).message, Toast.LENGTH_LONG).show()
                viewModelLocation.clearAction()
            }
            null -> Unit
        }
    }

    // UI START ---------------------------------------------------------
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 16.dp, vertical = 5.dp)
            .imePadding(),
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {

        if (sessionManager.getUserType().toString().equals("Owner",true)){
            item {
                // Add the WhosThisPostAbout section at the top
                updatePetListState.data?.let {
                    WhosThisPostAbout(
                        selectedPet = viewModelPetList.selectedPet,
                        allPets = it,
                        onAddPersonClick = { showPetInfoDialog = true },
                        onPersonClick = { pet -> viewModelPetList.selectPerson(pet)
                            viewModelPostCreateOwner.onPetID(pet.id.toString())
                        }
                    )
                }
            }
        }


        item {
            FormHeadingText(stringResource(R.string.Upload_Media))
            MediaUploadSection(maxImages = 6,
                imageList=uiStatePostCreateOwner.image?: mutableListOf(),
                onMediaSelected = {
                    viewModelPostCreateOwner.addPhoto(it) } ,
                onMediaUnSelected = {  viewModelPostCreateOwner.removePhoto(it) })

        }

        item {
            FormHeadingText(stringResource(R.string.Write_Post))
            DescriptionBox(
                placeholder = stringResource(R.string.Enter_Description),
                value = uiStatePostCreateOwner.writePost?:"",
                onValueChange = { viewModelPostCreateOwner.onWritePost(it) }
            )
        }

        item {
            FormHeadingText(stringResource(R.string.Hashtags))
            InputField(
                placeholder = stringResource(R.string.Add_Hashtags),
                input = uiStatePostCreateOwner.hashTage?:"",
                onValueChange = { viewModelPostCreateOwner.onHashTagChange(it) }
            )
        }

        item {
            FormHeadingText(stringResource(R.string.current_location))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable {
                    /*onClickLocation()*/
                    when {
                        permission.status.isGranted -> {
                            // Permission granted → check GPS & fetch
                            val lm = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
                            viewModelLocation.checkRequirements(
                                hasPermission = true,
                                isGPSEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER)
                            )
                        }
                        permission.status.shouldShowRationale -> {
                            // Denied once → show permission dialog again
                            permission.launchPermissionRequest()
                        }
                        else -> {
                            // Permanently denied → guide to Settings
                            Toast.makeText(context, "Location permission permanently denied. Enable from settings.", Toast.LENGTH_LONG).show()
                            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                            intent.data = Uri.fromParts("package", context.packageName, null)
                            context.startActivity(intent)
                        }
                    }

                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.precise_loc),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = Modifier.wrapContentSize()
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = stringResource(R.string.use_my_current_location),
                    fontFamily = FontFamily(Font(R.font.poppins)),
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp,
                    color = Color.Black
                )
            }
        }

        item {
            FormHeadingText(stringResource(R.string.Enter_your_Address))
            InputField(
                placeholder = stringResource(R.string.enter_flat_address),
                input = locationState.address?:"Address".ifEmpty { "Address" } ,
                onValueChange = {},
                onClick = {
                    val fields = listOf(
                        Place.Field.ID,
                        Place.Field.NAME,
                        Place.Field.ADDRESS,
                        Place.Field.LAT_LNG
                    )
                    val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN,fields).build(context)
                    launcher.launch(intent)
                },
                readOnly = true
            )
        }

        item {
            Spacer(Modifier.height(10.dp))
            SubmitButton(
                modifier = Modifier,
                buttonText = stringResource(R.string.post),
                onClickButton = {
                    viewModelPostCreateOwner.onLocation(locationState.address?:"")
                    viewModelPostCreateOwner.onLatitude(locationState.latitude.toString())
                    viewModelPostCreateOwner.onLongitude(locationState.longitude.toString())
                    viewModelPostCreateOwner.createPostOwner(context=context,
                        onError = { msg -> Toast.makeText(context, msg, Toast.LENGTH_SHORT).show() },
                        onSuccess = {
                            onClickPost()
                        }
                    )
                }
            )
            Spacer(Modifier.height(30.dp))
        }

    }

    if (showPetInfoDialog) {
        FillPetInfoDialog(
            data=updatePetListState.data,
            "Add Your Pet",
            onDismiss = { showPetInfoDialog = false },
            onAddPet = {
                viewModelUpdatePet.updatePet(context=context,
                    onError = { msg -> Toast.makeText(context, msg, Toast.LENGTH_SHORT).show() },
                    onSuccess = {
                        showPetInfoDialog = false
                        petAddedSuccessDialog = true
                        viewModelPetList.petListRequest()
                    }
                )
            },
            onCancel = { showPetInfoDialog = false },
            onProfile = true
        )
    }

    if (petAddedSuccessDialog){
        UpdatedDialogWithExternalClose(onDismiss = { petAddedSuccessDialog = false }, iconResId = R.drawable.ic_sucess_p, text = "Pet Added Successfully!",
            description = "Thanks for keeping things up to date!")
    }

}

fun askToEnableGPS(
    activity: Activity,
    launcher: ManagedActivityResultLauncher<IntentSenderRequest, ActivityResult>,
    viewModelLocation: LocationViewModel
) {
    val locationRequest = com.google.android.gms.location.LocationRequest.Builder(
        Priority.PRIORITY_HIGH_ACCURACY, 1000
    ).build()

    val builder = LocationSettingsRequest.Builder()
        .addLocationRequest(locationRequest)
        .setAlwaysShow(true)

    val client = LocationServices.getSettingsClient(activity)
    val task = client.checkLocationSettings(builder.build())

    task.addOnSuccessListener {
        // GPS already enabled
        viewModelLocation.fetchLocation()
    }

    task.addOnFailureListener { e ->
        if (e is ResolvableApiException) {
            try {
                launcher.launch(IntentSenderRequest.Builder(e.resolution).build())
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }
}


@Composable
fun WhosThisPostAbout(
    selectedPet: Data? = null,
    allPets: MutableList<Data> = mutableListOf(),
    onAddPersonClick: () -> Unit = {},
    onPersonClick: (Data) -> Unit = {}
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = stringResource(R.string.whos_this_post_about),
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(R.font.outfit_medium)),
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Reorder pets: selected first, others after
        val reorderedPets = if (selectedPet != null) {
            listOf(selectedPet) + allPets.filter { it.id != selectedPet.id }
        } else {
            allPets
        }

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            item {
                AddPersonButton(onClick = onAddPersonClick)
            }
            items(reorderedPets) { pet ->
                PersonItem(
                    person = pet,
                    selected = pet.id == selectedPet?.id,
                    onClick = { onPersonClick(pet) }
                )
            }
        }
    }
}

@Composable
fun AddPersonButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .width(65.dp)
            .height(78.dp)
            .border(
                width = 1.dp,
                color = Color(0xFFE5EFF2),
                shape = RoundedCornerShape(12.dp)
            )
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                onClick()
            }
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .background(Color.White)
                .border(
                    width = 1.dp,
                    color = PrimaryColor,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add Person",
                tint = PrimaryColor,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}



@Composable
fun PersonItem(
    person: Data,
    selected: Boolean = false,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .width(60.dp)
            .height(78.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(if (selected) PrimaryColor else Color.White)
            .border(
                width = 1.dp,
                color = Color(0xFFE5EFF2),
                shape = RoundedCornerShape(12.dp)
            )
            .clickable { onClick() }
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AsyncImage(
            model = person.pet_image?:"",
            contentDescription = person.pet_name?:"",
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop,
            placeholder = painterResource(id = R.drawable.dummy_baby_pic),
            error = painterResource(id = R.drawable.dummy_baby_pic)
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = person.pet_name?:"",
            fontSize = 12.sp,
            color = if (selected) Color.White else Color.Black,
            maxLines = 1,
            fontFamily = FontFamily(Font(R.font.outfit_medium)),
            overflow = TextOverflow.Ellipsis
        )
    }
}

// Data class for Person
data class Person(
    val id: String,
    val name: String,
    val imageUrl: String
)

// Preview with empty state
@Preview
@Composable
fun WhosThisPostAboutEmptyPreview() {
    WhosThisPostAbout()
}

// Preview with selected people
@Preview
@Composable
fun WhosThisPostAboutWithPeoplePreview() {
    listOf(
        Person("1", "Jimmy", "https://example.com/jimmy.jpg"),
        Person("2", "Barry", "https://example.com/barry.jpg"),
        Person("3", "Bill", "https://example.com/bill.jpg"),
        Person("4", "Julia", "https://example.com/julia.jpg")
    )


}
@Preview(showBackground = true)
@Composable
fun PetPostScreenContentPreview() {
    PetPostScreenContent(
        onClickPost = { /* Handle post click */ },
    )
}