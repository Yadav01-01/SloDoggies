package com.bussiness.slodoggiesapp.ui.screens.commonscreens.authFlow

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.navigation.Routes
import com.bussiness.slodoggiesapp.ui.component.businessProvider.ContinueButton
import com.bussiness.slodoggiesapp.ui.component.businessProvider.OtpInputField
import com.bussiness.slodoggiesapp.ui.component.businessProvider.OtpTimer
import com.bussiness.slodoggiesapp.ui.component.businessProvider.TopIndicatorBar
import com.bussiness.slodoggiesapp.ui.component.common.AuthBackButton
import com.bussiness.slodoggiesapp.ui.dialog.UpdatedDialogWithExternalClose
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor
import com.bussiness.slodoggiesapp.ui.theme.TextGrey
import com.bussiness.slodoggiesapp.viewModel.common.VerifyOTPViewModel
import kotlinx.coroutines.android.awaitFrame
import kotlinx.coroutines.flow.collectLatest

@Composable
fun VerifyOTPScreen(
    navController: NavHostController,
    type: String,
    name: String,
    emailOrPhone: String,
    password: String,
    viewModel: VerifyOTPViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    var isNavigating by remember { mutableStateOf(false) }

    // Focus OTP field when screen opens
    LaunchedEffect(Unit) {
        awaitFrame()
        focusRequester.requestFocus()
        keyboardController?.show()
    }

    //  Handle one-time ViewModel events (toast, navigation)
    LaunchedEffect(Unit) {
        viewModel.events.collectLatest { event ->
            when (event) {
                is VerifyOTPViewModel.UiEvent.ShowToast -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
                is VerifyOTPViewModel.UiEvent.NavigateToNext -> {
                    if (type == "forgot") {
                        navController.navigate("${Routes.NEW_PASSWORD_SCREEN}/${emailOrPhone}") {
                            popUpTo(Routes.VERIFY_OTP) { inclusive = true }
                            launchSingleTop = true
                        }

                    } else {
                        viewModel.dismissSuccessDialog(navController)
                    }
                }
            }
        }
    }

    BackHandler {
        if (!isNavigating) {
            isNavigating = true
            navController.popBackStack()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 24.dp, vertical = 32.dp)
    ) {
        AuthBackButton(
            onClick = {
                if (!isNavigating) {
                    isNavigating = true
                    navController.popBackStack()
                }
            },
            modifier = Modifier.align(Alignment.TopStart)
        )

        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth()
                .imePadding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopIndicatorBar()
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Verify Your Account",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp
                ),
                fontFamily = FontFamily(Font(R.font.outfit_semibold)),
                textAlign = TextAlign.Center,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = buildAnnotatedString {
                    append("Please enter the 4 digit code sent to \n")
                    withStyle(style = SpanStyle(color = PrimaryColor)) {
                        append(emailOrPhone)
                    }
                },
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp),
                fontFamily = FontFamily(Font(R.font.outfit_medium)),
                textAlign = TextAlign.Center,
                color = TextGrey
            )

            Spacer(modifier = Modifier.height(15.dp))

            OtpInputField(
                state.otp,
                onOtpTextChange = { viewModel.updateOtp(it) },
                keyboardController,
                focusRequester
            )

            Spacer(Modifier.height(10.dp))

            OtpTimer(totalTime = 60) {
                viewModel.onTimerFinish()
            }

            Spacer(Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(R.string.didnt_receive_code),
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.outfit_medium)),
                    color = TextGrey
                )
                Spacer(Modifier.width(2.dp))
                Text(
                    text = stringResource(R.string.resend),
                    modifier = Modifier
                        .alpha(if (state.isTimerFinished) 1f else 0.4f)
                        .clickable(enabled = state.isTimerFinished) {
                            viewModel.resendOtp(emailOrPhone, type)
                            viewModel.resetTimer()
                        },
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.outfit_medium)),
                    color = PrimaryColor
                )
            }

            Spacer(Modifier.height(22.dp))

            ContinueButton(
                onClick = { viewModel.onVerifyClick(type,name, emailOrPhone, password) },
                text = stringResource(R.string.verify_otp),
                backgroundColor = if (viewModel.isOtpValid()) PrimaryColor else Color(0xFFD9D9D9),
                textColor = if (viewModel.isOtpValid()) Color.White else Color(0xFF686868),
                iconColor = if (viewModel.isOtpValid()) Color.White else Color(0xFF686868)
            )
        }

        Image(
            painter = painterResource(id = R.drawable.paw_ic),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 16.dp, bottom = 16.dp)
                .height(66.dp)
                .wrapContentWidth()
        )
    }

    if (state.successDialogVisible) {
        UpdatedDialogWithExternalClose(
            onDismiss = { viewModel.dismissSuccessDialog(navController) },
            iconResId = R.drawable.ic_party_popper_icon,
            text = stringResource(R.string.account_created_sucessfully),
            description = ""
        )
    }

}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun VerifyOTPScreenPreview() {
    val dummyNavController = rememberNavController()
    VerifyOTPScreen(navController = dummyNavController, "","","","")
}
