package com.bussiness.slodoggiesapp.ui.screens.commonscreens

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.model.VerificationType
import com.bussiness.slodoggiesapp.navigation.Routes
import com.bussiness.slodoggiesapp.ui.component.businessProvider.ContinueButton
import com.bussiness.slodoggiesapp.ui.component.businessProvider.EmailInputField
import com.bussiness.slodoggiesapp.ui.component.businessProvider.TopIndicatorBar
import com.bussiness.slodoggiesapp.ui.theme.TextGrey

@Composable
fun EmailLoginScreen(navController: NavHostController) {
    var email by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 24.dp, vertical = 32.dp)
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopIndicatorBar()

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Continue With Email",
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
                text = "Please enter your mail to verify your account",
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp),
                fontFamily = FontFamily(Font(R.font.outfit_medium)),
                textAlign = TextAlign.Center,
                color = TextGrey
            )

            Spacer(modifier = Modifier.height(15.dp))

            EmailInputField(email, onValueChange = { email = it })

            Spacer(Modifier.height(35.dp))

            ContinueButton(
                onClick = {
                    navController.navigate("${Routes.VERIFY_OTP}/${VerificationType.EMAIL.name}")
                },
                text = "Continue"
            )

            Spacer(Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth()
                    .clickable { navController.navigate(Routes.PHONE_AUTH_SCREEN) },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.phone_ic),
                    contentDescription = "mail",
                    modifier = Modifier.size(25.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "Continue with Phone",
                    color = TextGrey,
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.outfit_medium))
                )
            }

            Spacer(Modifier.height(25.dp))

            Image(
                painter = painterResource(id = R.drawable.back_arrow_ic),
                contentDescription = "backArrow",
                modifier = Modifier
                    .wrapContentSize()
                    .clickable { navController.popBackStack() }
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


@Preview(showSystemUi = true, showBackground = true)
@Composable
fun EmailAuthScreenPreview() {
    val dummyNavController = rememberNavController()
    EmailLoginScreen(navController = dummyNavController)
}