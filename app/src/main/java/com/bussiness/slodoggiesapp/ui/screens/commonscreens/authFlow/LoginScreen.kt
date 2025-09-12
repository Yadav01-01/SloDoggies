package com.bussiness.slodoggiesapp.ui.screens.commonscreens.authFlow

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.navigation.Routes
import com.bussiness.slodoggiesapp.ui.component.businessProvider.ContinueButton
import com.bussiness.slodoggiesapp.ui.component.businessProvider.EmailInputField
import com.bussiness.slodoggiesapp.ui.component.businessProvider.FormHeadingText
import com.bussiness.slodoggiesapp.ui.component.businessProvider.TopIndicatorBar
import com.bussiness.slodoggiesapp.ui.component.common.AuthBackButton
import com.bussiness.slodoggiesapp.ui.component.common.PasswordInput
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor
import com.bussiness.slodoggiesapp.ui.theme.TextGrey
import com.bussiness.slodoggiesapp.util.SessionManager
import com.bussiness.slodoggiesapp.viewModel.common.authFlowVM.LoginViewModel

@Composable
fun LoginScreen(
    navController: NavHostController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val sessionManager = SessionManager.getInstance(context)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 24.dp, vertical = 32.dp)
    ) {
        // Back button at top-left
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
                text = "Login",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                ),
                fontFamily = FontFamily(Font(R.font.outfit_semibold)),
                textAlign = TextAlign.Center,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(12.dp))

            FormHeadingText("Email", modifier = Modifier.align(Alignment.Start))
            Spacer(modifier = Modifier.height(10.dp))
            EmailInputField(
                email = state.email,
                onValueChange = { viewModel.onEmailChange(it) }
            )

            Spacer(Modifier.height(10.dp))

            FormHeadingText("Password", modifier = Modifier.align(Alignment.Start))
            Spacer(modifier = Modifier.height(10.dp))
            PasswordInput(
                password = state.password,
                onPasswordChange = { viewModel.onPasswordChange(it) }
            )

            Spacer(Modifier.height(10.dp))

            Text(
                text = "Forgot Password?",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                ),
                fontFamily = FontFamily(Font(R.font.outfit_medium)),
                color = Color.Black,
                modifier = Modifier
                    .align(Alignment.End)
                    .clickable { navController.navigate("${Routes.FORGOT_PASSWORD_SCREEN}/") }
            )

            Spacer(Modifier.height(20.dp))

            ContinueButton(
                onClick = {
                    viewModel.login(
                        onSuccess = {
                            sessionManager.setLogin(true)
                            navController.navigate(Routes.MAIN_SCREEN)
                        },
                        onError = { message ->
                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                        }
                    )
                },
                text = "Login"
            )

            Spacer(Modifier.height(20.dp))

            // Clickable "New here? Create an Account"
            val annotatedText = buildAnnotatedString {
                pushStyle(
                    SpanStyle(
                        color = TextGrey,
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.outfit_medium))
                    )
                )
                append("New here? ")
                pop()

                pushStringAnnotation(tag = "CREATE_ACCOUNT", annotation = "create_account")
                pushStyle(
                    SpanStyle(
                        color = PrimaryColor,
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.outfit_medium)),
                        textDecoration = TextDecoration.Underline
                    )
                )
                append("Create an Account")
                pop()
            }

            ClickableText(
                text = annotatedText,
                onClick = { offset ->
                    annotatedText.getStringAnnotations(
                        tag = "CREATE_ACCOUNT",
                        start = offset,
                        end = offset
                    ).firstOrNull()?.let {
                        navController.navigate(Routes.SIGNUP_SCREEN)
                    }
                }
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
}

