package com.bussiness.slodoggiesapp.ui.screens.businessprovider.post

import android.os.Build
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
import com.bussiness.slodoggiesapp.ui.component.businessProvider.ChoosePostTypeButton
import com.bussiness.slodoggiesapp.ui.component.businessProvider.HeadingTextWithIcon
import com.bussiness.slodoggiesapp.ui.dialog.PostSuccessDialog
import com.bussiness.slodoggiesapp.ui.screens.businessprovider.post.content.EventScreenContent
import com.bussiness.slodoggiesapp.ui.screens.businessprovider.post.content.PostScreenContent
import com.bussiness.slodoggiesapp.ui.screens.businessprovider.post.content.PromotionScreenContent
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PostScreen(navController: NavHostController) {

    var selected by remember { mutableStateOf("Post") }
    var successDialog by remember { mutableStateOf(false) }
    var eventSuccess by remember { mutableStateOf(false) }

    Column ( modifier = Modifier.fillMaxSize().background(Color.White)) {

        HeadingTextWithIcon(textHeading = if (selected == "Post") "New post" else if (selected == "Event") "New Event" else "New Promotion ", onBackClick = { navController.popBackStack() })

        HorizontalDivider(modifier = Modifier.fillMaxWidth().height(2.dp).background(PrimaryColor))

        Spacer(Modifier.height(15.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp, horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            listOf("Post", "Event", "Ads.").forEach { label ->
                ChoosePostTypeButton(
                    modifier = Modifier.weight(1f),
                    text = label,
                    isSelected = selected == label,
                    onClick = { selected = label }
                )
            }
        }

        when (selected) {
            "Post" -> PostScreenContent(onClickLocation = { }, onClickPost = { successDialog = true })
            "Event" -> EventScreenContent( onClickLocation = { },onClickSubmit = { eventSuccess = true })
            "Ads." -> PromotionScreenContent(onClickLocation = { },onClickSave = { })
        }
        if (successDialog){
            PostSuccessDialog(
                onDismiss = { successDialog = false },
                text =  "Post Created!"
            )
        }
        if (eventSuccess){
            PostSuccessDialog(
                onDismiss = { eventSuccess = false },
                text =  "Event Created!"
            )
        }

    }

}



@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun NewPostScreenPreview() {
    val navController = rememberNavController()
    PostScreen(navController = navController)
}
