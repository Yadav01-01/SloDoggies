package com.bussiness.slodoggiesapp.ui.component.petOwner.Dialog

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Surface
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.model.petOwner.PetInfo
import com.bussiness.slodoggiesapp.ui.component.petOwner.CommonBlueButton
import com.bussiness.slodoggiesapp.ui.component.petOwner.CommonWhiteButton
import com.bussiness.slodoggiesapp.ui.component.petOwner.CustomOutlinedTextField


@Composable
fun PetInfoDialog(title : String,
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

    val ageOptions =
        listOf("Puppy (0-1 year)", "Young (1-3 years)", "Adult (3-7 years)", "Senior (7+ years)")
    val managedByOptions = listOf("Pet Mom", "Pet Dad", "Family Member", "Caregiver")

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            decorFitsSystemWindows = false )
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp)

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
                    .padding(top = 5.dp),
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
                            text = title,
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
                                modifier = Modifier
                                    .size(25.dp)
                                    .align(Alignment.BottomEnd),

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





@Preview(showBackground = true)
@Composable
fun PetInfoDialogPreview() {
    MaterialTheme {
        PetInfoDialog("Tell us about your pet!")
    }
}
