package com.bussiness.slodoggiesapp.ui.component.businessProvider

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor
import com.bussiness.slodoggiesapp.ui.theme.TextGrey

@Composable
fun SponsoredEngagementCard(
    descriptionText : String,
    engagementData : String,
    buttonText : String,
    status : Boolean = true,
    onButtonClick : () -> Unit
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(width = 1.dp, color = Color(0xFFDEEBEE), shape = RoundedCornerShape(10.dp))
            .padding(top = 20.dp, bottom = 20.dp, start = 15.dp, end = 15.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(descriptionText, fontWeight = FontWeight.Medium, fontFamily = FontFamily(
                Font(R.font.poppins_semi_bold)
            ), fontSize = 14.sp)
            if (status){
                Text(
                    text = "Expired",
                    color = Color.White,
                    fontSize = 11.sp,
                    fontFamily = FontFamily(Font(R.font.outfit_semibold)),
                    modifier = Modifier
                        .background(TextGrey, RoundedCornerShape(8.dp))
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                )
            }
        }

        Spacer(Modifier.height(10.dp))

        Text(engagementData, fontSize = 12.sp, fontFamily = FontFamily(Font(R.font.poppins)), color = Color.Black, lineHeight = 18.sp)

        Spacer(Modifier.height(18.dp))

        Button(
            onClick = { onButtonClick() },
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryColor),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.fillMaxWidth().height(38.dp)
        ) {
            Text(text = buttonText
                , color = Color.White,fontWeight = FontWeight.Medium,
                fontFamily = FontFamily(Font(R.font.outfit_medium)),
                fontSize = 15.sp)
        }
    }
}

@Composable
fun ExperienceRuleHeading(){
    Text(
        text = "Search Experience Rules",
        fontFamily = FontFamily(Font(R.font.outfit_medium)),
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        color = Color.Black
    )
}