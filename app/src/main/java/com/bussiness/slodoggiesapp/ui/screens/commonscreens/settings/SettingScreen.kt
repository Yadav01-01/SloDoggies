package com.bussiness.slodoggiesapp.ui.screens.commonscreens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.ui.component.businessProvider.HeadingTextWithIcon
import com.bussiness.slodoggiesapp.ui.component.common.SettingsItem
import com.bussiness.slodoggiesapp.ui.component.common.SettingsItemArrow
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor

@Composable
fun SettingsScreen(navController: NavHostController) {
    Column(modifier = Modifier.fillMaxSize().background(Color.White)) {

        HeadingTextWithIcon(textHeading = "Settings", onBackClick = { navController.popBackStack() })

        HorizontalDivider(modifier = Modifier.fillMaxWidth().height(2.dp).background(PrimaryColor))

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            item { SettingsItem(icon = R.drawable.ic_bookmark_icon, title = "Saved", onClick = {  }) }

            item { SettingsItem(icon = R.drawable.ic_calendar_outline, title = "Events", onClick = { /* Handle events click */ }) }

            item { SettingsItem( icon = R.drawable.ic_delete_icon, title = "Delete Account", onClick = { /* Handle delete account click */ }) }

            item { SettingsItem(icon = R.drawable.ic_about_circle_icon, title = "About Us", onClick = { /* Handle about us click */ }) }

            item { SettingsItemArrow( icon = R.drawable.ic_terms_and_condition_icon, title = "Terms & Conditions", onClick = { /* Handle terms click */ }) }

            item { SettingsItemArrow(icon = R.drawable.ic_policy_icon, title = "Privacy Policy", onClick = { /* Handle privacy policy click */ }) }

            item { SettingsItem( icon = R.drawable.ic_help_faq, title = "FAQs", onClick = { /* Handle FAQs click */ }) }

            item { SettingsItem(    icon = R.drawable.ic_customer_support_icon, title = "Help & Support", onClick = { /* Handle help & support click */ }) }

            item { SettingsItem(    icon = R.drawable.ic_logout_icon, title = "Logout", onClick = { /* Handle help & support click */ }) }
        }

    }
}

@Preview
@Composable
fun SettingsScreenPreview() {
    SettingsScreen(navController = NavHostController(LocalContext.current))
}