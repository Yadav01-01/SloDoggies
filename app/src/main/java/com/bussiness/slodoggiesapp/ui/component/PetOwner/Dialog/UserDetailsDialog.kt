package com.bussiness.slodoggiesapp.ui.component.PetOwner.Dialog

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material.Surface
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.ui.component.PetOwner.CommonBlueButton
import com.bussiness.slodoggiesapp.ui.component.PetOwner.CommonWhiteButton
import com.bussiness.slodoggiesapp.ui.component.PetOwner.CustomOutlinedTextField
import com.bussiness.slodoggiesapp.ui.screens.petowner.profileScreens.PetProfileScreen
import com.joelkanyi.jcomposecountrycodepicker.component.KomposeCountryCodePicker
import com.joelkanyi.jcomposecountrycodepicker.component.rememberKomposeCountryCodePickerState

@Composable
fun UserDetailsDialog(
    onDismiss: () -> Unit = {},
    onSubmit: () -> Unit = {}
) {
    var name by remember { mutableStateOf("") }
    var mobileNumber by remember { mutableStateOf("+1 (555) 123 456") }
    var email by remember { mutableStateOf("merrysglobalogales.com") }
    var bio by remember { mutableStateOf("") }
    var phoneNumber by rememberSaveable { mutableStateOf("") }
    var isEmailVerified by remember { mutableStateOf(false) }
    val state = rememberKomposeCountryCodePickerState(
//            limitedCountries = listOf("KE", "UG", "TZ", "RW", "SS", "Togo", "+260", "250", "+211", "Mali", "Malawi"),
//            priorityCountries = listOf("SA", "KW", "BH", "QA"),
//            showCountryCode = true,
//            showCountryFlag = true,
//            defaultCountryCode = "KE",
    )
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
                            )
//                    countrySelectionDialogContainerColor = MaterialTheme.colorScheme.background,
//                    countrySelectionDialogContentColor = MaterialTheme.colorScheme.onBackground,
//                    countrySelectionDialogTitle = {
//                        Text(
//                            text = "Select Joel",
//                            style = MaterialTheme.typography.titleMedium.copy(
//                                fontWeight = FontWeight.Bold,
//                            ),
//                        )
//                    },
//                    countrySelectionDialogSearchIcon = {
//                        Icon(
//                            imageVector = Icons.Default.Send,
//                            contentDescription = "Search",
//                            tint = MaterialTheme.colorScheme.onBackground,
//                        )
//                    },
//                    countrySelectionDialogBackIcon = {
//                        Icon(
//                            imageVector = Icons.Default.Close,
//                            contentDescription = "Back",
//                            tint = MaterialTheme.colorScheme.onBackground,
//                        )
//                    },
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
        UserDetailsDialog()
    }
}