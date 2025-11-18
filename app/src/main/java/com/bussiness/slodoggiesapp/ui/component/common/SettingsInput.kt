package com.bussiness.slodoggiesapp.ui.component.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowRight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor

@Composable
fun SettingsItemArrow(
    icon: Int,
    title: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = title,
            tint = Color.Unspecified,
            modifier = Modifier.wrapContentSize()
        )

        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Normal,
                fontFamily = FontFamily(Font(R.font.outfit_regular)),
                fontSize = 16.sp,
                color = Color.Black
            ),
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp)
        )

        Icon(
            imageVector = Icons.AutoMirrored.Outlined.KeyboardArrowRight,
            contentDescription = "Arrow",
            tint = Color.Black,
            modifier = Modifier.size(20.dp)
        )
    }
    HorizontalDivider(modifier = Modifier.fillMaxWidth().height(1.dp).background(Color(0xFFDBDBDB)))
}

@Composable
fun SettingsItem(
    icon: Int,
    title: String,
    onClick: () -> Unit,
    textColor: Color = Color.Black
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = title,
            tint = Color.Unspecified,
            modifier = Modifier.size(20.dp)
        )

        Text(
            text = title,
            fontSize = 16.sp,
            color = textColor,
            fontFamily = FontFamily(Font(R.font.outfit_regular)),
            modifier = Modifier.padding(start = 16.dp)
        )
    }
    HorizontalDivider(modifier = Modifier.fillMaxWidth().height(1.dp).background(Color(0xFFDBDBDB)))

}

@Composable
fun ToggleItem(
    icon: Int,
    text: String,
    isEnabled: Boolean,
    onToggle: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onToggle(!isEnabled) } // toggle when row is clicked
            .padding(horizontal = 16.dp, vertical = 5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = "Notifications",
            tint = Color.Black,
            modifier = Modifier.size(24.dp)
        )

        Text(
            text = text,
            fontSize = 16.sp,
            color = Color.Black,
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp)
        )

        Switch(
            checked = isEnabled,
            onCheckedChange = { onToggle(it) },
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = PrimaryColor,
                uncheckedThumbColor = Color.White,
                uncheckedTrackColor = Color.Gray
            ), modifier = Modifier
                .scale(0.7f)
        )
    }
    HorizontalDivider(modifier = Modifier.fillMaxWidth().height(1.dp).background(Color(0xFFDBDBDB)))

}
