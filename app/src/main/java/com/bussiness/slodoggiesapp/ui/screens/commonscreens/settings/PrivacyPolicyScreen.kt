package com.bussiness.slodoggiesapp.ui.screens.commonscreens.settings

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
fun PrivacyPolicyScreen(navController: NavHostController) {

    var isNavigating by remember { mutableStateOf(false) }

    BackHandler {
        if (!isNavigating) {
            isNavigating = true
            navController.popBackStack()
        }
    }
    Column (modifier = Modifier.fillMaxSize().background(Color.White) ){

        HeadingTextWithIcon(textHeading = "Privacy Policy", onBackClick = {  if (!isNavigating) {
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
                text = " We value your privacy and are committed to protecting your personal information. When you use our app, we may collect basic details such as your name, location, email, pet information, and activity within the app to provide a personalized experience and improve our services. Your information is never sold or shared with third parties without your consent, except as required by law or to ensure the safety and integrity of our platform. We use secure methods to store and manage your data and take reasonable steps to prevent unauthorized access. By using the app, you consent to the collection and use of your information as outlined in this policy. If you have any questions or concerns, please contact us at support@petsloapp.com.",
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
fun AuthPrivacyPolicyScreen(navController: NavHostController) {

    var isNavigating by remember { mutableStateOf(false) }

    BackHandler {
        if (!isNavigating) {
            isNavigating = true
            navController.popBackStack()
        }
    }
    Column (modifier = Modifier.fillMaxSize().background(Color.White).statusBarsPadding() ){

        HeadingTextWithIcon(textHeading = "Privacy Policy", onBackClick = {  if (!isNavigating) {
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
                text = " We value your privacy and are committed to protecting your personal information. When you use our app, we may collect basic details such as your name, location, email, pet information, and activity within the app to provide a personalized experience and improve our services. Your information is never sold or shared with third parties without your consent, except as required by law or to ensure the safety and integrity of our platform. We use secure methods to store and manage your data and take reasonable steps to prevent unauthorized access. By using the app, you consent to the collection and use of your information as outlined in this policy. If you have any questions or concerns, please contact us at support@petsloapp.com.",
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
fun PrivacyPreview(){
    PrivacyPolicyScreen(navController = NavHostController(LocalContext.current))
}