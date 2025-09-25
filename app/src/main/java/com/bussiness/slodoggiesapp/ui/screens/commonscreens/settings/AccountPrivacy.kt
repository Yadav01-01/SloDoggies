package com.bussiness.slodoggiesapp.ui.screens.commonscreens.settings

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.navigation.Routes
import com.bussiness.slodoggiesapp.ui.component.businessProvider.HeadingTextWithIcon
import com.bussiness.slodoggiesapp.ui.component.common.SettingsItem
import com.bussiness.slodoggiesapp.ui.dialog.DeleteDialog
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor

@Composable
fun AccountPrivacy(navController: NavHostController, authNavController: NavHostController) {

    var showDeleteDialog by remember { mutableStateOf(false) }
    var isNavigating by remember { mutableStateOf(false) }
    val context = LocalContext.current

    BackHandler {
        if (!isNavigating) {
            isNavigating = true
            navController.popBackStack()
        }
    }
    Column (modifier = Modifier.fillMaxSize().background(Color.White) ){

        HeadingTextWithIcon(textHeading = "Account Privacy", onBackClick = {  if (!isNavigating) {
            isNavigating = true
            navController.popBackStack()
        } })

        HorizontalDivider(thickness = 2.dp, color = PrimaryColor)

        Spacer(Modifier.height(10.dp))

        Column  (
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 10.dp)
        ){
            Text(
                text = "Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni d",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.outfit_regular)),
                    color = Color(0xFF252E32)
                )
            )
            Spacer(Modifier.height(10.dp))

            HorizontalDivider(thickness = 1.dp, color = Color(0xFFD3D5D6))

            SettingsItem(icon = R.drawable.ph_password_change, title = "Change Password",
                onClick = { authNavController.navigate("${Routes.FORGOT_PASSWORD_SCREEN}/changePass"){
                    popUpTo(Routes.FORGOT_PASSWORD_SCREEN){
                        inclusive = true
                    }
                }
            })

            SettingsItem(icon = R.drawable.ic_delete_icon, title = "Delete Account", onClick = { showDeleteDialog = true })
        }
        // Show Delete Dialog
        if (showDeleteDialog) {
            DeleteDialog(
                onDismiss = { showDeleteDialog = false },
                iconResId = R.drawable.delete_ic,
                onClickDelete = {
                    showDeleteDialog = false
                },
                context
            )
        }
    }
}