package com.bussiness.slodoggiesapp.ui.screens.petowner.service.serviceContent

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bussiness.slodoggiesapp.R

@Composable
fun ServiceTypeChip(
    serviceType: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        color = if (isSelected) Color(0xFF258694) else Color.White,
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, Color(0xFFCDCDCD)),
        modifier = Modifier
            .wrapContentSize()
            .clip(RoundedCornerShape(8.dp))
    ) {
        Text(
            text = serviceType,
            fontSize = 12.sp,
            color = if (isSelected) Color.White else Color(0xFF8A8894),
            fontFamily = FontFamily(Font(R.font.outfit_medium)),
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .clickable { onClick() }
        )
    }
}