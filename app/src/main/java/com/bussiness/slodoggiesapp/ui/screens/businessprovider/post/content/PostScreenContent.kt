package com.bussiness.slodoggiesapp.ui.screens.businessprovider.post.content

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.ui.component.businessProvider.DescriptionBox
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
    val streetAddress by viewModel.streetAddress.collectAsState()
    val areaSector by viewModel.areaSector.collectAsState()
    val landmark by viewModel.landmark.collectAsState()
    val visibility by viewModel.visibility.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
            .imePadding(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        item {
            FormHeadingText("Upload Media")
            MediaUploadSection(maxImages = 6) { uri ->
//            viewModel.addPetImage(uri)
            }
            Spacer(Modifier.height(15.dp))
        }

        item {
            FormHeadingText("Write Post")
            DescriptionBox(
                placeholder = "Enter Description",
                value = writePost,
                onValueChange = { viewModel.updateWritePost(it) }
            )
            Spacer(Modifier.height(15.dp))
        }

        item {
            FormHeadingText("Hashtags")
            InputField(
                placeholder = "Enter #tags",
                input = hashtags,
                onValueChange = { viewModel.updateHashtags(it) }
            )
            Spacer(Modifier.height(15.dp))
        }

        item {
            FormHeadingText(stringResource(R.string.current_location))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable { onClickLocation() }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.precise_loc),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = Modifier.wrapContentSize()
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = stringResource(R.string.use_my_current_location),
                    fontFamily = FontFamily(Font(R.font.poppins)),
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp,
                    color = Color.Black
                )
            }
        }

        item {
            FormHeadingText(stringResource(R.string.Enter_your_Address))
            InputField(
                placeholder = stringResource(R.string.enter_flat_address),
                input = streetAddress,
                onValueChange = { viewModel.updateStreetAddress(it) },
                readOnly = true
            )

        }

        item {
            Spacer(Modifier.height(15.dp))
            SubmitButton(
                modifier = Modifier,
                buttonText = "Post",
                onClickButton = { onClickPost() }
            )
            Spacer(Modifier.height(30.dp))
        }
    }

}
