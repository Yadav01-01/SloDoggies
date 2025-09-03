package com.bussiness.slodoggiesapp.ui.screens.businessprovider.post.content

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.ui.component.businessProvider.FormHeadingText
import com.bussiness.slodoggiesapp.ui.component.businessProvider.InputField
import com.bussiness.slodoggiesapp.ui.component.businessProvider.SubmitButton
import com.bussiness.slodoggiesapp.ui.component.common.MediaUploadSection
import com.bussiness.slodoggiesapp.viewModel.businessProvider.PostContentViewModel

@Composable
fun PostScreenContent( onClickLocation: () -> Unit,onClickPost: () -> Unit,viewModel: PostContentViewModel = hiltViewModel()) {

    val writePost by viewModel.writePost.collectAsState()
    val hashtags by viewModel.hashtags.collectAsState()
    val postalCode by viewModel.postalCode.collectAsState()
    val visibility by viewModel.visibility.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        FormHeadingText("Upload Media")

        Spacer(Modifier.height(10.dp))

        MediaUploadSection()

        Spacer(Modifier.height(15.dp))

        FormHeadingText("Write Post")

        Spacer(Modifier.height(10.dp))

        InputField(modifier = Modifier.height(106.dp), placeholder = "Enter Description", input = writePost, onValueChange ={ viewModel.updateWritePost(it)})

        Spacer(Modifier.height(15.dp))

        FormHeadingText("Hashtags")

        Spacer(Modifier.height(10.dp))

        InputField(placeholder = "Enter Hashtags", input = hashtags, onValueChange ={viewModel.updateHashtags(it)})

        Spacer(Modifier.height(15.dp))

        FormHeadingText("Zip Code")

        Spacer(Modifier.height(5.dp))

        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable { onClickLocation() }) {
            Icon(
                painter = painterResource(id = R.drawable.precise_loc),
                contentDescription = null,
                tint = Color.Gray,
                modifier = Modifier.wrapContentSize()
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = "use my current location", fontFamily = FontFamily(Font(R.font.poppins)), fontSize = 12.sp, color = Color.Black)
        }

        Spacer(Modifier.height(10.dp))

        InputField(placeholder = "Enter your Zip Code", input = postalCode, onValueChange ={ viewModel.updatePostalCode(it)})

        Spacer(Modifier.height(35.dp))

        SubmitButton(modifier = Modifier, buttonText = "Post", onClickButton = { onClickPost() })
        Spacer(Modifier.height(30.dp))
    }
}
