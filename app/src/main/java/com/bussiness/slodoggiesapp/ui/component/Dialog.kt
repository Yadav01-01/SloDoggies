package com.bussiness.slodoggiesapp.ui.component


import android.util.EventLogTags.Description
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Surface

import androidx.compose.material3.OutlinedTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.navigation.Routes

@Composable
fun WelcomeDialog(
    onDismiss: () -> Unit = {},
    onSubmitClick: () -> Unit = {},
    title : String,
    description : String,
    button : String
) {
    Dialog(
        onDismissRequest =   onDismiss,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = false
        )
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            // Main card content
            Image(
                painter = painterResource(id = R.drawable.ic_cross_icon),
                contentDescription = "Close",

                modifier = Modifier
                    .clickable {
                        onDismiss()
                    }
                    .align(Alignment.TopEnd)
                    .size(40.dp)
                    .clip(CircleShape)
                    .padding(bottom = 8.dp)
                    .align(Alignment.TopStart)
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp, top = 40.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                // Main content
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Icon (you can replace with your actual icon resource)


                    Image(
                        painter = painterResource(id = R.drawable.ic_party_popper_icon),
                        contentDescription = "Close",
                        modifier = Modifier
                            .size(70.dp)

                    )

                    // Title
                    Text(
                        text = title,
                        fontFamily = FontFamily(Font(R.font.outfit_medium)),
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center,
                        color = Color.Black
                    )

                    // Description
                    Text(
                        text = description,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                        fontFamily = FontFamily(Font(R.font.outfit_regular)),
                        color = Color(0xFF000000),
                        lineHeight = 15.sp
                    )


                    // Get Started Button
                    Button(
                        onClick = onSubmitClick,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF2E8B8B) // Teal color
                        )
                    ) {
                        Text(
                            text = button,
                            fontSize = 13.sp,
                            fontFamily = FontFamily(Font(R.font.outfit_medium)),
                            fontWeight = FontWeight.Medium,
                            color = Color.White
                        )
                    }
                }
            }

        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PetInfoDialog(
    onDismiss: () -> Unit = {},
    onSaveAndContinue: (PetInfo) -> Unit = {}
) {
    var petName by remember { mutableStateOf("") }
    var petBreed by remember { mutableStateOf("") }
    var petAge by remember { mutableStateOf("") }
    var petBio by remember { mutableStateOf("") }
    var managedBy by remember { mutableStateOf("Pet Mom") }
    var showAgeDropdown by remember { mutableStateOf(false) }
    var showManagedByDropdown by remember { mutableStateOf(false) }

    val ageOptions = listOf("Puppy (0-1 year)", "Young (1-3 years)", "Adult (3-7 years)", "Senior (7+ years)")
    val managedByOptions = listOf("Pet Mom", "Pet Dad", "Family Member", "Caregiver")

    Dialog(
        onDismissRequest = onDismiss
    ) {
        Column (
            modifier = Modifier.fillMaxWidth()
        ) {
            // Main card content
            Spacer(Modifier.height(45.dp))

                // This Box will contain our close button aligned to top-end
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_cross_icon),
                        contentDescription = "Close",
                        modifier = Modifier
                            .clickable { onDismiss() }
                            .align(Alignment.TopEnd)  // Align to top-end within the Box
                            .size(40.dp)
                            .clip(CircleShape)
                            .padding(4.dp)  // Consistent padding all around
                    )
                }
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding( top = 5.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
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
                            text = "Tell us about your pet!",
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
                            modifier = Modifier
                                .size(80.dp),


                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_black_profile_icon),
                                contentDescription = "Add Photo",

                                modifier = Modifier.size(70.dp)
                            )


                            Image(
                                painter = painterResource(id = R.drawable.ic_post_icon),
                                contentDescription = "Add",
                                modifier = Modifier.size(25.dp) .align(Alignment.BottomEnd),

                            )

                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Add Photo",
                            fontSize = 15.sp,
                            color = Color.Black,
                            fontFamily = FontFamily(Font(R.font.outfit_medium)),
                        )
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

                    CustomDropdownMenu(
                        value = petAge,
                        onValueChange = { petAge = it },
                        options = ageOptions,
                        label = "Pet Age",
                        placeholder = "Enter pet age",
                        isExpanded = showAgeDropdown,
                        onExpandedChange = { showAgeDropdown = it }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Pet Bio Field
                    Column {
                        Text(
                            text = "Pet Bio",
                            fontSize = 15.sp,
                            fontFamily = FontFamily(Font(R.font.outfit_medium)),
                            color = Color.Black,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        OutlinedTextField(
                            value = petBio,
                            onValueChange = { petBio = it },
                            placeholder = { Text("Enter Bio", fontSize = 14.sp, fontFamily = FontFamily(Font(R.font.outfit_regular)), color = Color(0xFF949494)) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp),
                            maxLines = 4,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color(0xFF00ACC1),
                                unfocusedBorderColor = Color(0xFF949494)
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Managed By Dropdown

                    CustomDropdownMenu(
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
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        // Skip Button

                        CommonWhiteButton(
                            text = "Skip",
                            onClick = {
                              //  navController.navigate(Routes.LOCATION_ALERT)  // Also changed this for consistency
                            },
                            modifier = Modifier.weight(1f),
                        )
                        CommonBlueButton(
                            text = "Save & Continue ",
                            fontSize = 22.sp,
                            onClick = {
                              //  navController.navigate(Routes.PET_MAIN_SCREEN)  // Now using navController instead of authNavController
                            },
                            modifier = Modifier.weight(1f),
                        )

                    }

                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun PetInfoDialogPreview() {
//    MaterialTheme {
//        PetInfoDialog(
//            onDismiss = {},
//            onSaveAndContinue = {}
//        )
//    }
//}

// Data class to hold pet information
data class PetInfo(
    val name: String,
    val breed: String,
    val age: String,
    val bio: String,
    val managedBy: String
)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDetailsDialog(
    onDismiss: () -> Unit = {},
    onSubmit: () -> Unit = {}
) {
    var name by remember { mutableStateOf("") }
    var mobileNumber by remember { mutableStateOf("+1 (555) 123 456") }
    var email by remember { mutableStateOf("merrysglobalogales.com") }
    var bio by remember { mutableStateOf("") }

    Dialog(
        onDismissRequest = onDismiss
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            // Close button at top-right
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_cross_icon),
                    contentDescription = "Close",
                    modifier = Modifier
                        .clickable(onClick = onDismiss)
                        .align(Alignment.TopEnd)
                        .size(40.dp)
                        .clip(CircleShape)
                        .padding(8.dp)
                )
            }

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    // Header
                    Text(
                        text = "Add Your Details",
                        fontSize = 18.sp,
                        fontFamily = FontFamily(Font(R.font.outfit_medium)),
                        color = Color.Black,
                        modifier = Modifier.padding(bottom = 24.dp)
                    )

                    // Add Photo Section
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Box(
                            modifier = Modifier.size(80.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_black_profile_icon),
                                contentDescription = "Add Photo",
                                modifier = Modifier.size(70.dp)
                            )

                            Image(
                                painter = painterResource(id = R.drawable.ic_post_icon),
                                contentDescription = "Add",
                                modifier = Modifier
                                    .size(25.dp)
                                    .align(Alignment.BottomEnd)
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Add Photo",
                            fontSize = 15.sp,
                            color = Color.Black,
                            fontFamily = FontFamily(Font(R.font.outfit_medium)),
                        )
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    // Name Field
                    CustomOutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        placeholder = "Enter name",
                        label = "Name"
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Mobile Number (read-only)
                    Column {
                        Text(
                            text = "Mobile Number",
                            fontSize = 15.sp,
                            fontFamily = FontFamily(Font(R.font.outfit_medium)),
                            color = Color.Black,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        OutlinedTextField(
                            value = mobileNumber,
                            onValueChange = {},
                            readOnly = true,
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color(0xFF00ACC1),
                                unfocusedBorderColor = Color(0xFF949494)
                            ),
                            shape = RoundedCornerShape(12.dp)
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

                            TextButton(onClick = { /* Verify email */ }) {
                                Text(
                                    text = "Verify",
                                    color = Color(0xFF00ACC1),
                                    fontFamily = FontFamily(Font(R.font.outfit_medium))
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

//                        OutlinedTextField(
//                            value = email,
//                            onValueChange = {},
//                            readOnly = true,
//                            modifier = Modifier.fillMaxWidth(),
//                            colors = OutlinedTextFieldDefaults.colors(
//                                focusedBorderColor = Color(0xFF00ACC1),
//                                unfocusedBorderColor = Color(0xFF949494)
//                            ),
//                            shape = RoundedCornerShape(12.dp)
//                        )
                        OutlinedTextField(
                            value = "merry@slodoggies.com",
                            onValueChange = {},
                            readOnly = true,
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color(0xFF00ACC1),
                                unfocusedBorderColor = Color(0xFF949494)
                            ),
                            shape = RoundedCornerShape(12.dp),
                            trailingIcon = {
                                TextButton(
                                    onClick = { /* Verify email */ },
                                    modifier = Modifier.padding(end = 4.dp)
                                ) {
                                    Text(
                                        text = "verify", // lowercase as shown in image
                                        color = Color(0xFF00ACC1),
                                        fontFamily = FontFamily(Font(R.font.outfit_medium)),
                                        fontSize = 14.sp
                                    )
                                }
                            }
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Bio Field
                    Column {
                        Text(
                            text = "Bio",
                            fontSize = 15.sp,
                            fontFamily = FontFamily(Font(R.font.outfit_medium)),
                            color = Color.Black,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        OutlinedTextField(
                            value = bio,
                            onValueChange = { bio = it },
                            placeholder = { Text("Enter Bio...", color = Color(0xFF949494)) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp),
                            maxLines = 4,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color(0xFF00ACC1),
                                unfocusedBorderColor = Color(0xFF949494)
                            ),
                            shape = RoundedCornerShape(12.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    // Bottom Buttons
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        CommonWhiteButton(
                            text = "Skip",
                            onClick = onDismiss,
                            modifier = Modifier.weight(1f)
                        )

                        Spacer(modifier = Modifier.width(16.dp))

                        CommonBlueButton(
                            text = "Submit",
                            onClick = onSubmit,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UserDetailsDialogPreview() {
    MaterialTheme {
        UserDetailsDialog(
            onDismiss = {},
            onSubmit = {}
        )
    }
}

//@Preview(showBackground = true)
//@Composable
//fun WelcomeDialogPreview() {
//    val sheetState = rememberModalBottomSheetState(
//        initialValue = ModalBottomSheetValue.Hidden,
//        skipHalfExpanded = true
//    )
//    WelcomeDialog(title = "", description = "", button = "")
//
//
//}

