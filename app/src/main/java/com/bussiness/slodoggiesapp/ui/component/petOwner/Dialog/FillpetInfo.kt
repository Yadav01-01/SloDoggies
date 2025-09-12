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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import com.bussiness.slodoggiesapp.model.petOwner.PetInfo
import com.bussiness.slodoggiesapp.ui.component.businessProvider.CustomDropdownBox
import com.bussiness.slodoggiesapp.ui.component.businessProvider.FormHeadingText
import com.bussiness.slodoggiesapp.ui.component.petOwner.CommonBlueButton
import com.bussiness.slodoggiesapp.ui.component.petOwner.CommonWhiteButton
import com.bussiness.slodoggiesapp.ui.component.petOwner.CustomOutlinedTextField
import com.bussiness.slodoggiesapp.ui.theme.TextGrey
import com.bussiness.slodoggiesapp.viewModel.petOwner.PetInfoViewModel

@Composable
fun FillPetInfoDialog(
    title: String,
    onDismiss: () -> Unit = {},
    onAddPet: () -> Unit = {},
    onCancel : () -> Unit,
    onProfile : Boolean = false
) {
    val context =  LocalContext.current
    val viewModel: PetInfoViewModel = hiltViewModel()
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
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
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


                    Spacer(modifier = Modifier.height(15.dp))

                    HorizontalDivider(thickness = 1.dp, color = TextGrey)

                    Spacer(modifier = Modifier.height(12.dp))

                    // Add Photo
                    AddPhotoSection(
                        onPhotoSelected = { uri ->

                        }
                    )


                    Spacer(modifier = Modifier.height(32.dp))

                    CustomOutlinedTextField(
                        value = uiState.petName,
                        onValueChange = { viewModel.updatePetName(it) },
                        placeholder = stringResource(R.string.placeholder_pet_name),
                        label = stringResource(R.string.label_pet_name),
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    CustomOutlinedTextField(
                        value = uiState.petBreed,
                        onValueChange = { viewModel.updatePetBreed(it) },
                        placeholder = stringResource(R.string.placeholder_pet_breed),
                        label = stringResource(R.string.label_pet_breed)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    FormHeadingText("Pet Age")

                    Spacer(Modifier.height(8.dp))

                    CustomDropdownBox(
                        label = uiState.petAge.ifEmpty { "Enter pet age" },
                        items = viewModel.ageOptions,
                        selectedItem = uiState.petAge,
                        onItemSelected = { viewModel.updatePetAge(it) }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    CustomOutlinedTextField(
                        value = uiState.petBio,
                        onValueChange = { viewModel.updatePetBio(it) },
                        placeholder = stringResource(R.string.placeholder_pet_bio),
                        label = stringResource(R.string.label_pet_bio)
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        CommonWhiteButton(
                            text = if (onProfile) stringResource(R.string.cancel)  else stringResource(
                                R.string.continue_btn),
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

                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}