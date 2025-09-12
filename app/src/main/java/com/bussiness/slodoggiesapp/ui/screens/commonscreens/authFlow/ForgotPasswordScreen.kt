package com.bussiness.slodoggiesapp.ui.screens.commonscreens.authFlow

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.model.main.VerificationType
import com.bussiness.slodoggiesapp.navigation.Routes
import com.bussiness.slodoggiesapp.ui.component.businessProvider.ContinueButton
import com.bussiness.slodoggiesapp.ui.component.businessProvider.EmailInputField
import com.bussiness.slodoggiesapp.ui.component.businessProvider.TopIndicatorBar
import com.bussiness.slodoggiesapp.ui.component.common.AuthBackButton
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor
import com.bussiness.slodoggiesapp.ui.theme.TextGrey
import com.bussiness.slodoggiesapp.viewModel.common.authFlowVM.ForgotPasswordViewModel

@Composable
fun ForgotPasswordScreen(
    navController: NavHostController,
    type : String,
    viewModel: ForgotPasswordViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 24.dp, vertical = 32.dp)
    ) {

        AuthBackButton(navController, modifier = Modifier.align(Alignment.TopStart))

        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopIndicatorBar()

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = if (type == "changePass") "Change Password" else "Forgot Password?",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                ),
                fontFamily = FontFamily(Font(R.font.outfit_semibold)),
                textAlign = TextAlign.Center,
                color = Color.Black
            )


            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Please enter your Email to get a verification code",
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp),
                fontFamily = FontFamily(Font(R.font.outfit_medium)),
                textAlign = TextAlign.Center,
                color = TextGrey
            )

            Spacer(modifier = Modifier.height(15.dp))

            EmailInputField(
                email = state.email,
                onValueChange = { viewModel.onEmailChange(it) }
            )

            Spacer(Modifier.height(35.dp))

            ContinueButton(
                onClick = {
                    viewModel.sendCode(
                        onSuccess = {
                            navController.navigate("${Routes.VERIFY_OTP}?type=forgotPass")
                        },
                        onError = { message ->
                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                        }
                    )
                },
                text = "Send Code"
            )

            Spacer(Modifier.height(25.dp))


            if (type != "changePass"){
                val annotatedText = buildAnnotatedString {
                    pushStyle(
                        SpanStyle(
                            color = Color(0xFF9C9C9C),
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp,
                            fontFamily = FontFamily(Font(R.font.outfit_medium))
                        )
                    )
                    append("Remembered your password? ")
                    pop()

                    pushStringAnnotation(tag = "LOG_IN", annotation = "log_in")
                    pushStyle(
                        SpanStyle(
                            color = PrimaryColor,
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp,
                            textDecoration = TextDecoration.Underline,
                            fontFamily = FontFamily(Font(R.font.outfit_medium))
                        )
                    )
                    append("LogIn")
                    pop()
                }

                ClickableText(
                    text = annotatedText,
                    onClick = { offset ->
                        annotatedText.getStringAnnotations(
                            tag = "LOG_IN",
                            start = offset,
                            end = offset
                        ).firstOrNull()?.let {
                            navController.navigate(Routes.LOGIN_SCREEN)
                        }
                    }
                )
            }

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
}



@Preview(showSystemUi = true, showBackground = true)
@Composable
fun EmailAuthScreenPreview() {
    val dummyNavController = rememberNavController()
    ForgotPasswordScreen(navController = dummyNavController, type = " ")
}