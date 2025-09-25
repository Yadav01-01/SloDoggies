package com.bussiness.slodoggiesapp.ui.screens.commonscreens.settings

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.model.main.UserType
import com.bussiness.slodoggiesapp.navigation.Routes
import com.bussiness.slodoggiesapp.ui.component.businessProvider.HeadingTextWithIcon
import com.bussiness.slodoggiesapp.ui.component.common.SettingsItem
import com.bussiness.slodoggiesapp.ui.component.common.SettingsItemArrow
import com.bussiness.slodoggiesapp.ui.component.common.ToggleItem
import com.bussiness.slodoggiesapp.ui.dialog.LogoutDialog
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor
import com.bussiness.slodoggiesapp.util.SessionManager

@Composable
fun SettingsScreen(navController: NavHostController, authNavController: NavHostController) {
    var isNotificationEnabled by remember { mutableStateOf(true) }
    val context = LocalContext.current
    val sessionManager = remember { SessionManager(context) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var showLogoutDialog by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize().background(Color.White)
    ) {

        BackHandler {
            if (sessionManager.getUserType() == UserType.PET_OWNER){
                navController.navigate(Routes.PET_PROFILE_SCREEN){
                    popUpTo(Routes.PET_PROFILE_SCREEN) { inclusive = false }
                    launchSingleTop = true
                    restoreState = true
                }
            }else{
                navController.navigate(Routes.PROFILE_SCREEN){
                    popUpTo(Routes.PROFILE_SCREEN) { inclusive = false } // clear stack above home
                    launchSingleTop = true
                    restoreState = true
                }
            }

        }
        HeadingTextWithIcon(textHeading = "Settings",
            onBackClick = {  if (sessionManager.getUserType() == UserType.PET_OWNER){
                navController.navigate(Routes.PET_PROFILE_SCREEN){
                    popUpTo(Routes.PET_PROFILE_SCREEN) { inclusive = false } // clear stack above home
                    launchSingleTop = true
                    restoreState = true
                }
            }else{
                navController.navigate(Routes.PROFILE_SCREEN){
                    popUpTo(Routes.PROFILE_SCREEN) { inclusive = false } // clear stack above home
                    launchSingleTop = true
                    restoreState = true
                }
            } })

        HorizontalDivider(thickness = 2.dp, color = PrimaryColor)

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(horizontal = 16.dp),
//            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            item { SettingsItem(icon = R.drawable.ic_bookmark_icon, title = "Saved", onClick = { navController.navigate(Routes.SAVED_ITEM_SCREEN) }) }

            item { SettingsItem(icon = R.drawable.ic_calendar_outline, title = "My Events", onClick = { if (sessionManager.getUserType() == UserType.BUSINESS_PROVIDER)
                navController.navigate(Routes.MY_EVENT_SCREEN) else navController.navigate(Routes.PET_EVENT_SCREEN)}) }

            if (sessionManager.getUserType() == UserType.BUSINESS_PROVIDER) {
                item {
                    SettingsItem(icon = R.drawable.subspt_ic, title = "Subscription", onClick = { navController.navigate(Routes.SUBSCRIPTION_SCREEN) })
                }
            }

            if (sessionManager.getUserType() == UserType.BUSINESS_PROVIDER) {
                item {
                    SettingsItem(icon = R.drawable.transaction_ic, title = "Transaction", onClick = { navController.navigate(Routes.TRANSACTION_SCREEN) })
                }
            }

            item { ToggleItem(icon = R.drawable.ic_notification_icons, text = "Notification", isEnabled = isNotificationEnabled, onToggle = { isNotificationEnabled = it }) }

            item { SettingsItem(icon = R.drawable.privac_y, title = "Account Privacy", onClick = { navController.navigate(Routes.ACCOUNT_PRIVACY_SCREEN) }) }

            item { SettingsItem(icon = R.drawable.ic_about_circle_icon, title = "About Us", onClick = { navController.navigate(Routes.ABOUT_US_SCREEN) }) }

            item { SettingsItemArrow(icon = R.drawable.ic_terms_and_condition_icon, title = "Terms & Conditions", onClick = { navController.navigate(Routes.TERMS_AND_CONDITION_SCREEN) }) }

            item { SettingsItemArrow(icon = R.drawable.ic_policy_icon, title = "Privacy Policy", onClick = { navController.navigate(Routes.PRIVACY_POLICY_SCREEN) }) }

            item { SettingsItem(icon = R.drawable.ic_help_faq, title = "FAQs", onClick = { navController.navigate(Routes.FAQ_SCREEN) }) }

            item { SettingsItem(icon = R.drawable.ic_customer_support_icon, title = "Help & Support", onClick = { navController.navigate(Routes.HELP_AND_SUPPORT_SCREEN) }) }

            item { SettingsItem(icon = R.drawable.ic_logout_icon, title = "Logout", textColor = Color(0xFFDD0B00), onClick = { showLogoutDialog = true }) }
        }

        if (showLogoutDialog){
            LogoutDialog(
                onDismiss = { showLogoutDialog = false },
                onClickLogout = {
                    showLogoutDialog = false
                    sessionManager.clearSession()
                    authNavController.navigate(Routes.JOIN_THE_PACK)
                }
            )
        }
    }
}

@Preview
@Composable
fun SettingsScreenPreview() {
    SettingsScreen(
        navController = NavHostController(LocalContext.current),
        authNavController = NavHostController(LocalContext.current)
    )
}
