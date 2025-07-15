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
import com.bussiness.slodoggiesapp.ui.component.businessProvider.OtpInputField
import com.bussiness.slodoggiesapp.ui.component.businessProvider.TopIndicatorBar
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor
import com.bussiness.slodoggiesapp.ui.theme.TextGrey

@Composable
fun VerifyOTPScreen(navController: NavHostController,type: VerificationType) {

    var otp by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 24.dp, vertical = 32.dp)
    ) {
        // Main content centered
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopIndicatorBar()

            Spacer(modifier = Modifier.height(24.dp))

            Text(

                text = if(type == VerificationType.PHONE) "Verify Your Phone Number" else "Verify Your Email Address",
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
                text = if(type == VerificationType.PHONE) "Please enter the 4 digit code sent to \n" +
                        "+1 555 123 456" else "Please enter the 4 digit code sent to \n" +
                        "user@slodoggies.com",
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp),
                fontFamily = FontFamily(Font(R.font.outfit_medium)),
                textAlign = TextAlign.Center,
                color = TextGrey
            )

            Spacer(modifier = Modifier.height(15.dp))

            OtpInputField(otp, onOtpTextChange = { otp = it })

            Spacer(Modifier.height(10.dp))

            Text(text = "00:24",Modifier.fillMaxWidth(), textAlign = TextAlign.Center, fontSize = 14.sp, fontFamily = FontFamily(Font(R.font.outfit_bold)), color = Color.Black)

            Spacer(Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Didn’t receive the code? ",
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.outfit_medium)),
                    color = TextGrey
                )

                Text(
                    text = "Resend",
                    modifier = Modifier
                        .clickable {  },
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.outfit_medium)),
                    color = PrimaryColor
    )
}

            Spacer(Modifier.height(22.dp))

            ContinueButton ( onClick = { navController.navigate(Routes.BUSINESS_REGISTRATION) }, text = "Verify",backgroundColor = if (otp.length == 4) PrimaryColor else Color(0xFFD9D9D9),
                textColor = if (otp.length == 4) Color.White else Color(0xFF686868), iconColor = if (otp.length == 4) Color.White else Color(0xFF686868) )

            Spacer(Modifier.height(25.dp))

            Image(
                painter = painterResource(id = R.drawable.back_arrow_ic),
                contentDescription = "backArrow",
                modifier = Modifier.wrapContentSize().clickable {  }
            )

        }

        // Paw icon positioned bottom-end
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
fun VerifyOTPScreenPreview() {
    val dummyNavController = rememberNavController()
    VerifyOTPScreen(navController = dummyNavController, VerificationType.PHONE)
}
