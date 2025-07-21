package com.bussiness.slodoggiesapp.ui.screens.businessprovider.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.ui.component.businessProvider.FilledCustomButton
import com.bussiness.slodoggiesapp.ui.component.businessProvider.InputField
import com.bussiness.slodoggiesapp.ui.component.businessProvider.ProfileHeadingText
import com.bussiness.slodoggiesapp.ui.component.businessProvider.ProfileImageWithUpload
import com.bussiness.slodoggiesapp.ui.component.businessProvider.ScreenHeadingText
import com.bussiness.slodoggiesapp.ui.component.businessProvider.SubmitButton
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor

@Composable
fun EditProfileScreen(navController: NavHostController) {

    var providerName by remember { mutableStateOf("") }
    var bio by remember { mutableStateOf("") }

    Column (modifier = Modifier.fillMaxSize().background(Color.White).padding( vertical = 8.dp)) {

        ScreenHeadingText("Edit Profile", onBackClick = { navController.popBackStack() }, onSettingClick = { /* Handle Setting Click */ })

        HorizontalDivider(modifier = Modifier.fillMaxWidth().height(2.dp).background(PrimaryColor))

        Spacer(Modifier.height(20.dp))

        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            ProfileImageWithUpload(imagePainter = painterResource(R.drawable.lady_ic), onUploadClick = { /* Handle Upload Click */ })
        }

        Spacer(Modifier.height(25.dp))

        ProfileHeadingText("Provider Name", modifier = Modifier.padding(horizontal = 15.dp))

        Spacer(Modifier.height(8.dp))

        InputField(input = providerName, onValueChange = { providerName = it }, placeholder = "Enter name", modifier = Modifier.padding(horizontal = 15.dp))

        Spacer(Modifier.height(15.dp))

        ProfileHeadingText("Bio", modifier = Modifier.padding(horizontal = 15.dp))

        Spacer(Modifier.height(8.dp))

        InputField(input = bio, onValueChange = { bio = it }, placeholder = "Enter Bio", modifier = Modifier.padding(horizontal = 15.dp))

        Spacer(Modifier.height(35.dp))

        SubmitButton(modifier = Modifier.padding(horizontal = 20.dp), buttonText = "Save Changes", onClickButton = { /* Handle Submit Click */ }, buttonTextSize = 16)
    }
}

@Preview(showBackground = true)
@Composable
fun EditProfileScreenPreview() {
    val navController = rememberNavController()
    EditProfileScreen(navController)
}
