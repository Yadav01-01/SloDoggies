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
import androidx.compose.foundation.layout.imePadding
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
import androidx.compose.ui.res.stringResource
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
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.data.model.main.UserType
import com.bussiness.slodoggiesapp.navigation.Routes
import com.bussiness.slodoggiesapp.ui.component.businessProvider.ContinueButton
import com.bussiness.slodoggiesapp.ui.component.businessProvider.EmailInputField
import com.bussiness.slodoggiesapp.ui.component.businessProvider.FormHeadingText
import com.bussiness.slodoggiesapp.ui.component.businessProvider.TopIndicatorBar
import com.bussiness.slodoggiesapp.ui.component.common.AuthBackButton
import com.bussiness.slodoggiesapp.ui.component.common.PasswordInput
import com.bussiness.slodoggiesapp.ui.dialog.DisclaimerDialog
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor
import com.bussiness.slodoggiesapp.ui.theme.TextGrey
import com.bussiness.slodoggiesapp.util.SessionManager
import com.bussiness.slodoggiesapp.viewModel.common.authFlowVM.LoginViewModel

@Composable
fun LoginScreen(navController: NavHostController, viewModel: LoginViewModel = hiltViewModel()) {
    val state by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val sessionManager = SessionManager.getInstance(context)



    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 20.dp, vertical = 32.dp)
    ) {
        // Back button at top-left
        AuthBackButton(onClick = {navController.popBackStack()}, modifier = Modifier.align(Alignment.TopStart))

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

            FormHeadingText(stringResource(R.string.Email_Phone), modifier = Modifier.align(Alignment.Start))

            Spacer(modifier = Modifier.height(10.dp))

            EmailInputField(
                email = state.contactInput,
                onValueChange = { viewModel.onContactChange(it) }
            )

            Spacer(Modifier.height(10.dp))

            FormHeadingText(stringResource(R.string.password), modifier = Modifier.align(Alignment.Start))

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
                        onError = { msg -> Toast.makeText(context, msg, Toast.LENGTH_SHORT).show() },
                        onSuccess = {
                            navController.navigate(Routes.MAIN_SCREEN)
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

                append("")

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
    if (sessionManager.getUserType() == UserType.Professional){
        if (state.disclaimerDialog){
            DisclaimerDialog(
                onDismiss = { viewModel.dismissDisclaimerDialog() },
                icon = R.drawable.caution_ic,
                heading = "Disclaimer ",
                desc1 = "Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quao. Nemo enim ipsam voluptatem quia voluptas sit."
            )
        }
    }
    

}

@Preview(showBackground = true)
@Composable
fun LoginPreview(){
    LoginScreen(navController = NavHostController(LocalContext.current))
}