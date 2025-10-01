package com.bussiness.slodoggiesapp.ui.screens.commonscreens.authFlow

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.model.main.UserType
import com.bussiness.slodoggiesapp.navigation.Routes
import com.bussiness.slodoggiesapp.ui.component.businessProvider.ContinueButton
import com.bussiness.slodoggiesapp.ui.component.businessProvider.EmailInputField
import com.bussiness.slodoggiesapp.ui.component.businessProvider.FormHeadingText
import com.bussiness.slodoggiesapp.ui.component.businessProvider.TopIndicatorBar
import com.bussiness.slodoggiesapp.ui.component.businessProvider.UserNameInputField
import com.bussiness.slodoggiesapp.ui.component.common.AuthBackButton
import com.bussiness.slodoggiesapp.ui.component.common.PasswordInput
import com.bussiness.slodoggiesapp.ui.dialog.UpdatedDialogWithExternalClose
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor
import com.bussiness.slodoggiesapp.ui.theme.TextGrey
import com.bussiness.slodoggiesapp.util.SessionManager
import com.bussiness.slodoggiesapp.viewModel.common.authFlowVM.SignUpViewModel

@Composable
fun SignUpScreen(
    navController: NavHostController,
    viewModel: SignUpViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    var isNavigating by remember { mutableStateOf(false) }
    var sessionManager = SessionManager.getInstance(context)

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
        AuthBackButton(onClick = {  if (!isNavigating) {
            isNavigating = true
            navController.popBackStack()
        } }, modifier = Modifier.align(Alignment.TopStart))

        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .imePadding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopIndicatorBar()

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = stringResource(R.string.Create_your_account),
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp
                ),
                fontFamily = FontFamily(Font(R.font.outfit_semibold)),
                textAlign = TextAlign.Center,
                color = Color.Black
            )

            FormHeadingText(stringResource(R.string.FullName), modifier = Modifier.align(Alignment.Start))
            Spacer(modifier = Modifier.height(5.dp))
            UserNameInputField(
                input = state.userName,
                placeholder = if(sessionManager.getUserType() ==  UserType.PET_OWNER) stringResource(R.string.enter_fullname) else stringResource(R.string.enter_business_name),
                onValueChange = { viewModel.onUserNameChange(it) }
            )

            Spacer(modifier = Modifier.height(10.dp))

            FormHeadingText(stringResource(R.string.email_and_phone), modifier = Modifier.align(Alignment.Start))
            Spacer(modifier = Modifier.height(5.dp))
            EmailInputField(
                email = state.contactInput,
                onValueChange = { viewModel.onContactChange(it) }
            )

            Spacer(Modifier.height(10.dp))

            FormHeadingText(stringResource(R.string.password), modifier = Modifier.align(Alignment.Start))
            Spacer(modifier = Modifier.height(5.dp))
            PasswordInput(
                password = state.password,
                onPasswordChange = { viewModel.onPasswordChange(it) }
            )

            Spacer(Modifier.height(10.dp))

            FormHeadingText(stringResource(R.string.confirm_password), modifier = Modifier.align(Alignment.Start))
            Spacer(modifier = Modifier.height(5.dp))
            PasswordInput(
                password = state.confirmPassword,
                onPasswordChange = { viewModel.onConfirmPasswordChange(it) },
                placeholder = "Re-enter Password"
            )

            Spacer(Modifier.height(20.dp))

            ContinueButton(
                onClick = {
                    viewModel.createAccount(
                        onSuccess = {
                            navController.navigate("${Routes.VERIFY_OTP}?type=signUp")
                        },
                        onError = { message ->
                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                        }
                    )
                },
                text = stringResource(R.string.create_account)
            )

            Spacer(Modifier.height(10.dp))

            val annotatedText = buildAnnotatedString {
                pushStyle(
                    SpanStyle(
                        color = TextGrey,
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.outfit_medium))
                    )
                )
                append(stringResource(R.string.already_have_account))
                pop()

                append("")

                pushStringAnnotation(tag = "LOGIN", annotation = "Log In")
                pushStyle(
                    SpanStyle(
                        color = PrimaryColor,
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.outfit_medium)),
                        textDecoration = TextDecoration.Underline
                    )
                )
                append(stringResource(R.string.login))
                pop()
            }

            ClickableText(
                text = annotatedText,
                onClick = { offset ->
                    annotatedText.getStringAnnotations(
                        tag = "LOGIN",
                        start = offset,
                        end = offset
                    ).firstOrNull()?.let {
                        navController.navigate(Routes.LOGIN_SCREEN)
                    }
                }
            )

            val termsText = buildAnnotatedString {
                // Intro text
                withStyle(
                    style = SpanStyle(
                        color = Color.Black,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.outfit_regular))
                    )
                ) {
                    append(stringResource(R.string.create_account_policy) + " ")
                }

                // Terms link
                pushStringAnnotation(tag = "TERMS", annotation = "TERMS")
                withStyle(
                    style = SpanStyle(
                        color = PrimaryColor,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.outfit_regular)),
                    )
                ) {
                    append(stringResource(R.string.terms_and_conditions))
                }
                pop()

                // " and "
                withStyle(
                    style = SpanStyle(
                        color = Color.Black,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.outfit_regular))
                    )
                ) {
                    append(" ${stringResource(R.string.and_)} ")
                }

                // Privacy link
                pushStringAnnotation(tag = "PRIVACY", annotation = "PRIVACY")
                withStyle(
                    style = SpanStyle(
                        color = PrimaryColor,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.outfit_regular)),
                    )
                ) {
                    append(stringResource(R.string.privacy_policy))
                }
                pop()
            }

            Box(
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                ClickableText(
                    text = termsText,
                    modifier = Modifier.fillMaxWidth(),
                    style = TextStyle(textAlign = TextAlign.Center),
                    onClick = { offset ->
                        termsText.getStringAnnotations(start = offset, end = offset + 1)
                            .firstOrNull()?.let { annotation ->
                                when (annotation.tag) {
                                    "TERMS" -> navController.navigate(Routes.AUTH_TERMS_AND_CONDITION_SCREEN)
                                    "PRIVACY" -> navController.navigate(Routes.AUTH_PRIVACY_POLICY_SCREEN)
                                }
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
fun PhoneAuthScreenPreview() {
    val dummyNavController = rememberNavController()
    SignUpScreen(navController = dummyNavController)
}
