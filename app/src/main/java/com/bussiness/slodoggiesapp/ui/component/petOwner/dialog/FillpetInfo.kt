package com.bussiness.slodoggiesapp.ui.component.petOwner.dialog

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Surface
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.data.newModel.petlist.Data
import com.bussiness.slodoggiesapp.ui.component.businessProvider.CustomDropdownBox
import com.bussiness.slodoggiesapp.ui.component.businessProvider.FormHeadingText
import com.bussiness.slodoggiesapp.ui.component.petOwner.CommonBlueButton
import com.bussiness.slodoggiesapp.ui.component.petOwner.CommonWhiteButton
import com.bussiness.slodoggiesapp.ui.component.petOwner.CustomOutlinedTextField
import com.bussiness.slodoggiesapp.ui.screens.petowner.post.content.Person
import com.bussiness.slodoggiesapp.ui.screens.petowner.post.content.PersonItem
import com.bussiness.slodoggiesapp.ui.theme.TextGrey
import com.bussiness.slodoggiesapp.viewModel.petOwner.PetInfoViewModel
import com.bussiness.slodoggiesapp.viewModel.petOwner.petadd.PetAddViewModel

@Composable
fun FillPetInfoDialog(
    data: MutableList<Data>? = null,
    title: String,
    onDismiss: () -> Unit = {},
    onAddPet: () -> Unit = {},
    onCancel : () -> Unit,
    onProfile : Boolean = false
) {
    val context =  LocalContext.current
  /*  val viewModel: PetInfoViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()  */

    val viewModel: PetAddViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            decorFitsSystemWindows = false
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)

        ) {
            Spacer(Modifier.height(45.dp))

            // Close button
            Box(Modifier.fillMaxWidth()) {
                Image(
                    painter = painterResource(id = R.drawable.ic_cross_iconx),
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .wrapContentSize()
                        .clip(CircleShape)
                        .clickable { onDismiss() }
                        .padding(4.dp)
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
                        .padding(horizontal = 14.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp) // optional: adds spacing between items
                ) {
                    // Title Row
                    item {
                        Spacer(Modifier.height(10.dp))
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
                        }
                    }

                    // Divider
                    item {
                        HorizontalDivider(thickness = 1.dp, color = TextGrey)
                    }

                    item {
                        LazyRow(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            contentPadding = PaddingValues(horizontal = 0.dp)
                        ) {
                            data?.let {
                                items(
                                    items = it,
                                    key = { pet -> pet.id?:0 }   // ← stable key
                                ) { pet ->
                                    PersonItem(
                                        person = pet,
                                        selected = false,
                                        onClick = { }
                                    )
                                }
                            }


                        }
                    }

                    // Add Photo Section
                    item {
                        AddPhotoSection(
                            uriImage =uiState.image,
                            onPhotoSelected = { uri ->
                                if (uri != null) {
                                    viewModel.onPetImageChange(uri)
                                }
                            }
                        )
                    }

                    // Pet Name
                    item {
                        Spacer(modifier = Modifier.height(5.dp))
                        CustomOutlinedTextField(
                            value = uiState.name ?:"",
                            onValueChange = { viewModel.onPetNameChange(it) },
                            placeholder = stringResource(R.string.placeholder_pet_name),
                            label = stringResource(R.string.label_pet_name),
                        )
                    }

                    // Pet Breed
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                        CustomOutlinedTextField(
                            value = uiState.breed ?:"",
                            onValueChange = { viewModel.onPetBreedChange(it) },
                            placeholder = stringResource(R.string.placeholder_pet_breed),
                            label = stringResource(R.string.label_pet_breed)
                        )
                    }

                    // Pet Age
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                        FormHeadingText("Pet Age")
                        Spacer(Modifier.height(8.dp))
                        CustomDropdownBox(
                            label = uiState.age ?:"".ifEmpty { "Enter pet age" },
                            items = viewModel.ageOptions,
                            selectedItem = uiState.age ?:"",
                            onItemSelected = { viewModel.onPetAgeChange(it) }
                        )
                    }

                    // Pet Bio
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                        CustomOutlinedTextField(
                            value = uiState.bio ?:"",
                            onValueChange = { viewModel.onPetBioChange(it) },
                            placeholder = stringResource(R.string.placeholder_pet_bio),
                            label = stringResource(R.string.label_pet_bio)
                        )
                    }

                    // Buttons
                    item {
                        Spacer(modifier = Modifier.height(32.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            CommonWhiteButton(
                                text = if (onProfile) stringResource(R.string.cancel) else stringResource(R.string.continue_btn),
                                onClick = { onCancel() },
                                modifier = Modifier.weight(1f)
                            )
                            CommonBlueButton(
                                text = stringResource(R.string.add_pet),
                                fontSize = 16.sp,
                                onClick = { onAddPet() },
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }

                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }

            }
        }
    }
}

val pets = listOf(
    Person("1", "Jimmy", "https://example.com/jimmy.jpg"),
    Person("2", "Barry", "https://example.com/barry.jpg"),
    Person("3", "Bill", "https://example.com/bill.jpg"),
    Person("4", "Julia", "https://example.com/julia.jpg"),
    Person("5", "velit", "https://example.com/julia.jpg"),
    Person("6", "Julia", "https://example.com/julia.jpg"),
    Person("7", "velit", "https://example.com/julia.jpg"),
    Person("8", "Bill", "https://example.com/julia.jpg"),
    Person("9", "Julia", "https://example.com/julia.jpg"),
)