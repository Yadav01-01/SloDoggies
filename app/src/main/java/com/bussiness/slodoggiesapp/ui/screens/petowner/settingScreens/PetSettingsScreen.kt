package com.bussiness.slodoggiesapp.ui.screens.petowner.settingScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.navigation.Routes
import com.bussiness.slodoggiesapp.ui.component.petOwner.CommonTopAppBar


@Composable
fun PetSettingsScreen(navController: NavController = rememberNavController()) {
    Column(modifier = Modifier.fillMaxSize().background(Color.White)) {
        CommonTopAppBar(
            title = "Settings",
            titleFontSize = 19.sp,
            onBackClick = { navController.popBackStack() },
            dividerColor = Color(0xFF258694),
        )

        var notificationsEnabled by remember { mutableStateOf(true) }
        Spacer(Modifier.height(20.dp))
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 15.dp)
        ) {
            // Settings Items
            SettingsItem(
                icon = R.drawable.ic_bookmark_icon,
                title = "Saved",
                onClick = { navController.navigate(Routes.PET_SAVED_SCREEN) }
            )
            Divider(
                color = Color(0xFFE5EFF2),
                thickness = 1.dp,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            SettingsItem(
                icon = R.drawable.ic_calendar_outline,
                title = "Events",
                onClick = { /* Handle events click */ }
            )

            Divider(
                color = Color(0xFFE5EFF2),
                thickness = 1.dp,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            // Notification item with toggle
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { notificationsEnabled = !notificationsEnabled }
                    .padding(horizontal = 16.dp, vertical = 5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_notification_icons),
                    contentDescription = "Notifications",
                    tint = Color.Black,
                    modifier = Modifier.size(24.dp)
                )

                Text(
                    text = "Notification",
                    fontSize = 16.sp,
                    color = Color.Black,
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 16.dp)
                )

                Switch(
                    checked = notificationsEnabled,
                    onCheckedChange = { notificationsEnabled = it },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        checkedTrackColor = Color(0xFF258694),
                        uncheckedThumbColor = Color.White,
                        uncheckedTrackColor = Color.Gray
                    )
                )
            }

            Divider(
                color = Color(0xFFE5EFF2),
                thickness = 1.dp,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            SettingsItem(
                icon = R.drawable.ic_delete_icon,
                title = "Delete Account",
                onClick = { /* Handle delete account click */ }
            )

            Divider(
                color = Color(0xFFE5EFF2),
                thickness = 1.dp,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            SettingsItem(
                icon = R.drawable.ic_about_circle_icon,
                title = "About Us",
                onClick = { /* Handle about us click */ }
            )
            Divider(
                color = Color(0xFFE5EFF2),
                thickness = 1.dp,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            SettingsItemWithArrow(
                icon = R.drawable.ic_terms_and_condition_icon,
                title = "Terms & Conditions",
                onClick = { /* Handle terms click */ }
            )
            Divider(
                color = Color(0xFFE5EFF2),
                thickness = 1.dp,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            SettingsItemWithArrow(
                icon = R.drawable.ic_policy_icon,
                title = "Privacy Policy",
                onClick = { /* Handle privacy policy click */ }
            )
            Divider(
                color = Color(0xFFE5EFF2),
                thickness = 1.dp,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            SettingsItem(
                icon = R.drawable.ic_help_faq,
                title = "FAQs",
                onClick = { /* Handle FAQs click */ }
            )
            Divider(
                color = Color(0xFFE5EFF2),
                thickness = 1.dp,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            SettingsItem(
                icon = R.drawable.ic_customer_support_icon,
                title = "Help & Support",
                onClick = { /* Handle help & support click */ }
            )

            Divider(
                color = Color(0xFFE5EFF2),
                thickness = 1.dp,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            // Logout item (red text)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { /* Handle logout click */ }
                    .padding(horizontal = 16.dp, vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_logout_icon),
                    contentDescription = "Logout",
                    tint = Color.Red,
                    modifier = Modifier.size(24.dp)
                )

                Text(
                    text = "Logout",
                    fontSize = 16.sp,
                    color = Color.Red,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
            Divider(
                color = Color(0xFFE5EFF2),
                thickness = 1.dp,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }

    }
}

@Composable
fun SettingsItem(
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
            tint = Color.Black,
            modifier = Modifier.size(24.dp)
        )

        Text(
            text = title,
            fontSize = 16.sp,
            color = Color.Black,
            fontFamily = FontFamily(Font(R.font.outfit_regular)),
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}


@Composable
fun SettingsItemWithArrow(
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
            tint = Color.Black,
            modifier = Modifier.size(24.dp)
        )

        Text(
            text = title,
            fontSize = 16.sp,
            color = Color.Black,
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
}

@Preview(showBackground = true)
@Composable
fun PetSettingsScreenPreview() {
    MaterialTheme {
        PetSettingsScreen()
    }
}