package com.bussiness.slodoggiesapp.ui.component.petOwner.dialog

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavHostController
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.ui.component.businessProvider.FormHeadingText
import com.bussiness.slodoggiesapp.ui.component.common.BioField
import com.bussiness.slodoggiesapp.ui.component.common.EmailField
import com.bussiness.slodoggiesapp.ui.component.common.PhoneNumber
import com.bussiness.slodoggiesapp.ui.component.petOwner.CommonBlueButton
import com.bussiness.slodoggiesapp.ui.component.petOwner.CustomOutlinedTextField
import com.bussiness.slodoggiesapp.ui.theme.TextGrey
import com.bussiness.slodoggiesapp.viewModel.petOwner.UserDetailsViewModel

@Composable
fun UserDetailsDialog(
    navController: NavHostController,
    viewModel: UserDetailsViewModel = hiltViewModel(),
    onDismiss: () -> Unit,
    onSubmit: () -> Unit,
    onVerify: (String, String) -> Unit
) {
    val state by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(navController.currentBackStackEntry) {
        navController.currentBackStackEntry
            ?.savedStateHandle
            ?.getLiveData<String>("verification_result")
            ?.observe(lifecycleOwner) { result ->
                when (result) {
                    "dialogPhone" -> viewModel.setPhoneVerified(true)
                    "dialogEmail" -> viewModel.setEmailVerified(true)
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
                        placeholder = stringResource(R.string.merry),
                        label = stringResource(R.string.pet_name)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Phone Field
                    FormHeadingText(stringResource(R.string.mobile_number))

                    PhoneNumber(
                        phone = state.phoneNumber,
                        onPhoneChange = viewModel::onPhoneChanged,
                        onVerify = { onVerify( "dialogPhone",state.phoneNumber) },
                        isVerified = state.isPhoneVerified
                    )


                    Spacer(modifier = Modifier.height(16.dp))

                    FormHeadingText(stringResource(R.string.email))

                    Spacer(Modifier.height(5.dp))

                    // Email Field with Verify
                    EmailField(
                        email = state.email,
                        isVerified = state.isEmailVerified,
                        onEmailChange = viewModel::onEmailChanged,
                        onVerify = { onVerify("dialogEmail",state.email) }
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
                        onClick = { viewModel.submitDetails(context, onSuccess = { onSubmit()}) },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}
