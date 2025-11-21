package com.bussiness.slodoggiesapp.ui.screens.petowner.post

import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bussiness.slodoggiesapp.navigation.Routes
import com.bussiness.slodoggiesapp.ui.component.businessProvider.ChoosePostTypeButton
import com.bussiness.slodoggiesapp.ui.component.businessProvider.HeadingTextWithIcon
import com.bussiness.slodoggiesapp.ui.dialog.PostSuccessDialog
import com.bussiness.slodoggiesapp.ui.screens.petowner.post.content.PetEventScreenContent
import com.bussiness.slodoggiesapp.ui.screens.petowner.post.content.PetPostScreenContent
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PetNewPostScreen(navController: NavHostController) {

    var selected by remember { mutableStateOf("Post") }
    var successDialog by remember { mutableStateOf(false) }
    var eventSuccess by remember { mutableStateOf(false) }

    Column ( modifier = Modifier.fillMaxSize().background(Color.White)) {
        BackHandler {
            if (!navController.popBackStack(Routes.HOME_SCREEN, false)) {
                navController.navigate(Routes.HOME_SCREEN) {
                    launchSingleTop = true
                }
            }
        }

        HeadingTextWithIcon(
            textHeading = if (selected == "Post") "New post" else "New Event",
            onBackClick = {
                if (!navController.popBackStack(Routes.HOME_SCREEN, false)) {
                    navController.navigate(Routes.HOME_SCREEN) {
                        launchSingleTop = true
                    }
                }
            }
        )

        HorizontalDivider(thickness = 2.dp, color = PrimaryColor)

        Spacer(Modifier.height(15.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp, horizontal = 16.dp)
                .padding(bottom = 15.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            listOf("Post", "Event").forEach { label ->
                ChoosePostTypeButton(
                    modifier = Modifier.weight(1f),
                    text = label,
                    isSelected = selected == label,
                    onClick = { selected = label }
                )
            }
        }

        when (selected) {
            "Post" -> PetPostScreenContent(onClickLocation = { }, onClickPost = { successDialog = true }, addPetClick = {  })
            "Event" -> PetEventScreenContent( onClickLocation = { },onClickSubmit = { eventSuccess = true })
        }

    }
    if (successDialog){
        PostSuccessDialog(
            onDismiss = {
                successDialog = false
                navController.navigate(Routes.HOME_SCREEN)},
            text =  "Post Created!"
        )
    }
    if (eventSuccess){
        PostSuccessDialog(
            onDismiss = {
                eventSuccess = false
                navController.navigate(Routes.HOME_SCREEN)},
            text =  "Event Created!"
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun PetNewPostScreenPreview() {
    val navController = rememberNavController()
    PetNewPostScreen(navController = navController)
}
