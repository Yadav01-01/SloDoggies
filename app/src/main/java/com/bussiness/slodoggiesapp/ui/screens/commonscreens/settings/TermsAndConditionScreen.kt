package com.bussiness.slodoggiesapp.ui.screens.commonscreens.settings

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.ui.component.businessProvider.HeadingTextWithIcon
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor

@Composable
fun TermsAndConditionsScreen(navController: NavHostController) {

    var isNavigating by remember { mutableStateOf(false) }

    BackHandler {
        if (!isNavigating) {
            isNavigating = true
            navController.popBackStack()
        }
    }

    Column (modifier = Modifier.fillMaxSize().background(Color.White) ){

        HeadingTextWithIcon(textHeading = "Terms & Conditions", onBackClick = {  if (!isNavigating) {
            isNavigating = true
            navController.popBackStack()
        } })

        HorizontalDivider(thickness = 2.dp, color = PrimaryColor)

        Spacer(Modifier.height(10.dp))

        Column  (
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 10.dp)
        ){
            Text(
                text = "By using this app, you agree to engage respectfully and use it solely for its intended purpose: to connect with fellow pet enthusiasts, discover local pet service providers, share pet-related experiences, and explore pet-friendly events and activities.\n" +
                        "\n" +
                        "You are responsible for the accuracy and security of your account information. All content within the app — including text, images, and media — is owned by or licensed to the platform and may not be copied, modified, or distributed without written permission.\n" +
                        "\n" +
                        "You agree not to misuse the platform, post harmful or inappropriate content, or attempt to disrupt the app’s functionality or community experience.\n" +
                        "\n" +
                        "We are committed to protecting your privacy. Your personal information will never be sold or used outside the scope of providing and improving this app experience.\n" +
                        "\n" +
                        "Use of this app constitutes your acceptance of these Terms and any future updates. If you do not agree with any part of these Terms, please discontinue use of the app.\n" +
                        "\n" +
                        "For questions or concerns, contact us at support@petsloapp.com.",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.outfit_regular)),
                    color = Color(0xFF252E32)
                )
            )
        }
    }

}

@Composable
fun AuthTermsAndConditionsScreen(navController: NavHostController) {

    var isNavigating by remember { mutableStateOf(false) }

    BackHandler {
        if (!isNavigating) {
            isNavigating = true
            navController.popBackStack()
        }
    }

    Column (modifier = Modifier.fillMaxSize().background(Color.White).statusBarsPadding() ){

        HeadingTextWithIcon(textHeading = "Terms & Conditions", onBackClick = {  if (!isNavigating) {
            isNavigating = true
            navController.popBackStack()
        } })

        HorizontalDivider(thickness = 2.dp, color = PrimaryColor)

        Spacer(Modifier.height(10.dp))

        Column  (
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ){
            Text(
                text = "By using this app, you agree to engage respectfully and use it solely for its intended purpose: to connect with fellow pet enthusiasts, discover local pet service providers, share pet-related experiences, and explore pet-friendly events and activities.\n" +
                        "\n" +
                        "You are responsible for the accuracy and security of your account information. All content within the app — including text, images, and media — is owned by or licensed to the platform and may not be copied, modified, or distributed without written permission.\n" +
                        "\n" +
                        "You agree not to misuse the platform, post harmful or inappropriate content, or attempt to disrupt the app’s functionality or community experience.\n" +
                        "\n" +
                        "We are committed to protecting your privacy. Your personal information will never be sold or used outside the scope of providing and improving this app experience.\n" +
                        "\n" +
                        "Use of this app constitutes your acceptance of these Terms and any future updates. If you do not agree with any part of these Terms, please discontinue use of the app.\n" +
                        "\n" +
                        "For questions or concerns, contact us at support@petsloapp.com.",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.outfit_regular)),
                    color = Color(0xFF252E32)
                )
            )
        }
    }

}

@Preview
@Composable
fun TAndCPreview(){
    TermsAndConditionsScreen(navController = NavHostController(LocalContext.current))
}