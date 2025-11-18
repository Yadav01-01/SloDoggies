package com.bussiness.slodoggiesapp.ui.screens.commonscreens.settings

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.data.model.main.UserType
import com.bussiness.slodoggiesapp.navigation.Routes
import com.bussiness.slodoggiesapp.ui.component.businessProvider.HeadingTextWithIcon
import com.bussiness.slodoggiesapp.ui.component.businessProvider.InputField
import com.bussiness.slodoggiesapp.ui.component.common.DisplaySupportData
import com.bussiness.slodoggiesapp.ui.component.common.SupportContactFAQTextCard
import com.bussiness.slodoggiesapp.ui.component.common.SupportContactTextCard
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor
import com.bussiness.slodoggiesapp.util.SessionManager
import com.bussiness.slodoggiesapp.viewModel.common.HelpAndSupportViewModel

@Composable
fun HelpAndSupportScreen(navController: NavHostController,viewModel: HelpAndSupportViewModel = hiltViewModel()) {

    val phone by viewModel.phone.collectAsState()
    val email by viewModel.email.collectAsState()
    val context = LocalContext.current
    val sessionManager = remember { SessionManager(context) }
    var isNavigating by remember { mutableStateOf(false) }

    BackHandler {
        if (!isNavigating) {
            isNavigating = true
            navController.popBackStack()
        }
    }

    Column (modifier = Modifier.fillMaxSize().background(Color.White) ){

        HeadingTextWithIcon(textHeading = "Help & Support", onBackClick = { if (!isNavigating) {
            isNavigating = true
            navController.popBackStack()
        }})

        HorizontalDivider(thickness = 2.dp, color = PrimaryColor)

        Spacer(Modifier.height(10.dp))

        Column  (
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ){
            Box(
                modifier = Modifier
                    .border(
                        width = 1.dp,
                        color = PrimaryColor,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.White)
                    .padding(24.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    Text(
                        text = "Need a paw? We're here for you!",
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.outfit_medium)),
                        color = Color.Black
                    )

                    Text(
                        text = "Whether you're having trouble with your profile,\n" +
                                "reporting a bug, or just need help finding features,\n" +
                                "our support team is ready to assist.",
                        fontSize = 14.sp,
                        lineHeight = 18.sp,
                        fontFamily = FontFamily(Font(R.font.outfit_regular)),
                        fontWeight = FontWeight.Normal,
                        color = Color.Black
                    )
                    
                    if (sessionManager.getUserType() == UserType.Professional){

                        InputField(input = phone, onValueChange = { viewModel.updatePhone(it)}, placeholder = "+1 (555) 123 456", height = 46.dp, fontSize = 12)

                        InputField(input = email, onValueChange = { viewModel.updateEmail(it)}, placeholder = "help@slodoggies.com", height = 46.dp, fontSize = 12 )

                    }else{
                        DisplaySupportData(icon = R.drawable.filled_call_ic, text = "(555) 123 456")

                        DisplaySupportData(icon = R.drawable.filled_mail, text = "help@slodoggies.com")
                    }


                }
            }
            Spacer(modifier = Modifier.height(15.dp))

            SupportContactFAQTextCard(heading = "Need Quick Answers?", onFaqClick = { navController.navigate(Routes.FAQ_SCREEN)})

            Spacer(modifier = Modifier.height(15.dp))

            SupportContactTextCard(heading = "Feedback & Suggestions", subHeading = "We’d love to hear from you! Help us make Slodoggies better by sharing your feedback and ideas.")
        }
    }
}


@Preview
@Composable
fun HelpAndSupportPreview(){
    HelpAndSupportScreen(navController = NavHostController(LocalContext.current))
}

