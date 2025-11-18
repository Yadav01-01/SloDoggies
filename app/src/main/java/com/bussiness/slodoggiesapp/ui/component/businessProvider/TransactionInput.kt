package com.bussiness.slodoggiesapp.ui.component.businessProvider

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor

@Composable
fun StatusBox(
    modifier: Modifier = Modifier,
    title: String = "Total Spent",
    value: String = "$165.00",
) {
    Box(
        modifier = modifier
            .height(59.dp)
            .border(1.dp, Color(0xFFE5EFF2), RoundedCornerShape(10.dp))
            .background(Color.White, RoundedCornerShape(10.dp))
            .padding(horizontal = 12.dp, vertical = 10.dp)
    ) {
        Column {
            Text(
                text = title,
                fontFamily = FontFamily(Font(R.font.outfit_medium)),
                fontWeight = FontWeight.Medium,
                lineHeight = 20.sp,
                fontSize = 12.sp,
                color = Color.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )


            Text(
                text = value,
                fontFamily = FontFamily(Font(R.font.outfit_medium)),
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                color = PrimaryColor,
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun AdTopUpBtn(modifier: Modifier = Modifier,onClick: () -> Unit,text:String){
    Button(
        onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(containerColor = PrimaryColor),
        shape = RoundedCornerShape(10.dp),
        modifier = modifier.fillMaxWidth().height(44.dp)
    ) {
        Text(text = text
            , color = Color.White,fontWeight = FontWeight.Medium,
            fontFamily = FontFamily(Font(R.font.outfit_medium)),
            fontSize = 16.sp)
    }
}

@Composable
fun UpgradeBtn(modifier: Modifier = Modifier,onClick: () -> Unit,text:String){
    Button(
        onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(width = 1.dp, color = PrimaryColor),
        modifier = modifier.fillMaxWidth().height(44.dp)
    ) {
        Text(text = text
            , color = PrimaryColor,fontWeight = FontWeight.Medium,
            fontFamily = FontFamily(Font(R.font.outfit_medium)),
            fontSize = 16.sp)
    }
}