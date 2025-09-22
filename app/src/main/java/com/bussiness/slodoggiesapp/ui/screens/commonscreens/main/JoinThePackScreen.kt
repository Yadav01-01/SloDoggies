package com.bussiness.slodoggiesapp.ui.screens.commonscreens.main

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
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
import com.bussiness.slodoggiesapp.model.main.UserType
import com.bussiness.slodoggiesapp.navigation.Routes
import com.bussiness.slodoggiesapp.ui.component.businessProvider.TopIndicatorBar
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor
import com.bussiness.slodoggiesapp.util.SessionManager

@Composable
fun JoinThePackScreen(
    navController: NavHostController
) {
    var selectedOption by remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current
    val sessionManager = remember { SessionManager(context) }

    BackHandler {
        (context as? Activity)?.finishAffinity() // closes all activities and exits app
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 24.dp, vertical = 32.dp)
    ) {

        IconButton(
            onClick = { (context as? Activity)?.finishAffinity() },
            modifier = Modifier.padding( top = 20.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = PrimaryColor
            )
        }

        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopIndicatorBar()

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Join the Pack",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                ),
                fontFamily = FontFamily(Font(R.font.outfit_bold)),
                textAlign = TextAlign.Center,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Pick your path—because every star needs a stage, and every service has a story.",
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 18.sp),
                fontFamily = FontFamily(Font(R.font.outfit_regular)),
                textAlign = TextAlign.Center,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(40.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                // Pet Professional
                OptionCard(
                    imageRes = R.drawable.ns_petpro,
                    onChangeImage = R.drawable.petprofessional,
                    label = "Pet Professional",
                    isSelected = selectedOption == "Pet Professional",
                    onClick = {
                        selectedOption = "Pet Professional"
                        sessionManager.setUserType(UserType.BUSINESS_PROVIDER)
                        navController.navigate(Routes.LOGIN_SCREEN)
                    }
                )

                // Pet Owner
                OptionCard(
                    imageRes = R.drawable.pet_owner_black,
                    onChangeImage = R.drawable.petowner,
                    label = "Pet Owner",
                    isSelected = selectedOption == "Pet Owner",
                    onClick = {
                        selectedOption = "Pet Owner"
                        sessionManager.setUserType(UserType.PET_OWNER)
                        navController.navigate(Routes.LOGIN_SCREEN)
                    }
                )
            }
        }

        // Paw icon
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





@Composable
fun OptionCard(
    imageRes: Int,
    onChangeImage: Int,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val borderColor = if (isSelected) PrimaryColor else Color.White

    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            shape = RoundedCornerShape(14.dp),
            border = BorderStroke(1.dp, borderColor),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            modifier = Modifier.size(80.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(15.dp) // inner padding inside the card
            ) {
                Image(
                    painter = painterResource(id = if (isSelected) onChangeImage else imageRes),
                    contentDescription = label,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Fit
                )
            }
        }


        Spacer(modifier = Modifier.height(25.dp))

        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontSize = 18.sp,
                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                color = if (isSelected) (PrimaryColor) else Color.Black
            ),
            fontFamily = FontFamily(Font(R.font.outfit_medium)),
            textAlign = TextAlign.Center
        )
    }
}


@Preview(showSystemUi = true, showBackground = true)
@Composable
fun JoinThePackScreenPreview() {
    val dummyNavController = rememberNavController()

    JoinThePackScreen(navController = dummyNavController)
}
