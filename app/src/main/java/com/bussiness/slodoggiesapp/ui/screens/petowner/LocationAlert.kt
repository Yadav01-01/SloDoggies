package com.bussiness.slodoggiesapp.ui.screens.petowner

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.ui.theme.OutFit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationPermissionScreen(
    onTurnOnClick: () -> Unit = {},
    onNotNowClick: () -> Unit = {}
){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Bell Icon
            Image(
                painter = painterResource(id = R.drawable.location_icon),
                contentDescription = "Notification Icon",
                modifier = Modifier.size(120.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Turn on Location",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontFamily = OutFit,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Allow maps to access your\n" +
                        "location while you use the app?",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontFamily = OutFit,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    lineHeight = 20.sp
                )
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = onTurnOnClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF258694)
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "Turn On",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontFamily = OutFit,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "NOT NOW",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontFamily = OutFit,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                ),
                modifier = Modifier.clickable { onNotNowClick() }
            )
        }

        // Paw image at bottom right
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 56.dp, end = 10.dp) // This creates the space below the paw
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_paws),
                contentDescription = "Paw Icon",
                modifier = Modifier
                    .padding(16.dp)
                    .size(100.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun  LocationPreview(){
    LocationPermissionScreen()
}

