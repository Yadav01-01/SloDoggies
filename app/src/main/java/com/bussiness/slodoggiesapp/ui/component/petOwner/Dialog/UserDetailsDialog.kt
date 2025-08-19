package com.bussiness.slodoggiesapp.ui.component.petOwner.Dialog

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.ui.component.businessProvider.FormHeadingText
import com.bussiness.slodoggiesapp.ui.component.common.BioField
import com.bussiness.slodoggiesapp.ui.component.common.EmailField
import com.bussiness.slodoggiesapp.ui.component.petOwner.CommonBlueButton
import com.bussiness.slodoggiesapp.ui.component.petOwner.CustomOutlinedTextField
import com.bussiness.slodoggiesapp.ui.component.petOwner.Dialog.AddPhotoSection
import com.bussiness.slodoggiesapp.ui.theme.TextGrey
import com.bussiness.slodoggiesapp.viewModel.petOwner.UserDetailsViewModel
import com.joelkanyi.jcomposecountrycodepicker.component.KomposeCountryCodePicker
import com.joelkanyi.jcomposecountrycodepicker.component.rememberKomposeCountryCodePickerState

@Composable
fun UserDetailsDialog(
    viewModel: UserDetailsViewModel = hiltViewModel(),
    onDismiss: () -> Unit,
    onSubmit: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()
    val countryCodeState = rememberKomposeCountryCodePickerState()

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
                    painter = painterResource(id = R.drawable.ic_cross_icon),
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
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    // Header
                    Text(
                        text = stringResource(R.string.add_your_details),
                        fontSize = 18.sp,
                        fontFamily = FontFamily(Font(R.font.outfit_medium)),
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.height(15.dp))
                    Divider(thickness = 1.dp, color = TextGrey)
                    Spacer(modifier = Modifier.height(12.dp))

                    // Add Photo Section
                    AddPhotoSection(
                        onPhotoSelected = { uri ->
                            viewModel.setSelectedPhoto(uri) // send Uri to ViewModel
                        }
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    // Name Field
                    CustomOutlinedTextField(
                        value = state.name,
                        onValueChange = viewModel::onNameChanged,
                        placeholder = stringResource(R.string.enter_name),
                        label = stringResource(R.string.name)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Phone Field
                    FormHeadingText(stringResource(R.string.mobile_number))

                    KomposeCountryCodePicker(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .border(1.dp, Color(0xFF949494), RoundedCornerShape(12.dp)),
                        text = state.phoneNumber,
                        onValueChange = viewModel::onPhoneChanged,
                        placeholder = {
                            Text(
                                text = stringResource(R.string.phone_number),
                                fontFamily = FontFamily(Font(R.font.outfit_regular)),
                                color = Color(0xFF949494)
                            )
                        },
                        shape = MaterialTheme.shapes.medium,
                        state = countryCodeState,
                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                        textStyle = TextStyle(color = Color.Black)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    FormHeadingText(stringResource(R.string.email))

                    Spacer(Modifier.height(5.dp))

                    // Email Field with Verify
                    EmailField(
                        email = state.email,
                        isVerified = state.isEmailVerified,
                        onEmailChange = viewModel::onEmailChanged,
                        onVerify = { viewModel.verifyEmail() }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    FormHeadingText(stringResource(R.string.bio))

                    Spacer(Modifier.height(5.dp))
                    // Bio Field
                    BioField(
                        value = state.bio,
                        onValueChange = viewModel::onBioChanged
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    // Bottom Buttons
                    CommonBlueButton(
                        text = stringResource(R.string.submit),
                        onClick = { onSubmit() },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}
