package com.bussiness.slodoggiesapp.ui.screens.businessprovider.post

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.ui.component.businessProvider.ChoosePostTypeButton
import com.bussiness.slodoggiesapp.ui.component.businessProvider.FormHeadingText
import com.bussiness.slodoggiesapp.ui.component.businessProvider.HeadingTextWithIcon
import com.bussiness.slodoggiesapp.ui.component.businessProvider.InputField
import com.bussiness.slodoggiesapp.ui.component.businessProvider.SubmitButton
import com.bussiness.slodoggiesapp.ui.component.businessProvider.VisibilityOptionsSelector
import com.bussiness.slodoggiesapp.ui.screens.businessprovider.UploadPlaceholder
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor

@Composable
fun PostScreen(navController: NavHostController) {

    var selected by remember { mutableStateOf("Post") }

    Column ( modifier = Modifier.fillMaxSize().background(Color.White)) {

        HeadingTextWithIcon(textHeading = "New post", onBackClick = { navController.popBackStack() })

        HorizontalDivider(modifier = Modifier.fillMaxWidth().height(2.dp).background(PrimaryColor))

        Spacer(Modifier.height(15.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp, horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listOf("Post", "Event", "Promotion").forEach { label ->
                ChoosePostTypeButton(
                    modifier = Modifier.weight(1f),
                    text = label,
                    isSelected = selected == label,
                    onClick = { selected = label }
                )
            }
        }

        when (selected) {
            "Post" -> PostScreenContent(onClickLocation = { }, onClickPost = { })
            "Event" -> EventScreenContent( onClickLocation = { },onClickSubmit = { })
//            "Promotion" -> PromotionScreenContent()
        }

    }

}

@Composable
fun PostScreenContent( onClickLocation: () -> Unit,onClickPost: () -> Unit ) {
    var writePost by remember { mutableStateOf("") }
    var hashtags by remember { mutableStateOf("") }
    var postalCode by remember { mutableStateOf("") }
    var visibility by remember { mutableStateOf("Public") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        FormHeadingText("Upload Media")

        Spacer(Modifier.height(10.dp))

        UploadPlaceholder()

        Spacer(Modifier.height(15.dp))

        FormHeadingText("Write Post")

        Spacer(Modifier.height(10.dp))

        InputField(modifier = Modifier.height(106.dp), placeholder = "Enter Description", input = writePost, onValueChange ={ writePost = it})

        Spacer(Modifier.height(15.dp))

        FormHeadingText("Hashtags")

        Spacer(Modifier.height(10.dp))

        InputField(placeholder = "Enter Hashtags", input = hashtags, onValueChange ={ hashtags = it})

        Spacer(Modifier.height(15.dp))

        FormHeadingText("Location")

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

        InputField(placeholder = "Postal Code", input = postalCode, onValueChange ={ postalCode = it})

        Spacer(Modifier.height(15.dp))

        FormHeadingText("Privacy Settings")

        Spacer(Modifier.height(10.dp))

        VisibilityOptionsSelector(selected = visibility, onOptionSelected = { visibility = it })

        Spacer(Modifier.height(15.dp))

        SubmitButton(modifier = Modifier, buttonText = "Post", onClickButton = { onClickPost() })
    }
}


@Composable
fun EventScreenContent( onClickLocation: () -> Unit,onClickSubmit: () -> Unit) {

    var eventTitle by remember { mutableStateOf("") }
    var eventDescription by remember { mutableStateOf("") }
    var postalCode by remember { mutableStateOf("") }
    var visibility by remember { mutableStateOf("Public") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        FormHeadingText("Upload Media")

        Spacer(Modifier.height(10.dp))

        UploadPlaceholder()

        Spacer(Modifier.height(15.dp))

        FormHeadingText("Event Title")

        Spacer(Modifier.height(10.dp))

        InputField(modifier = Modifier.height(106.dp), placeholder = "Enter Title", input = eventTitle, onValueChange ={ eventTitle = it})

        Spacer(Modifier.height(15.dp))

        FormHeadingText("Event Description")

        Spacer(Modifier.height(10.dp))

        InputField(placeholder = "Enter Description", input = eventDescription, onValueChange ={ eventDescription = it})

        Spacer(Modifier.height(15.dp))

        FormHeadingText("Event Date And Time")

        InputField(input = eventTitle, onValueChange = { eventTitle = it}, placeholder = "Select Date And Time")

        Spacer(Modifier.height(15.dp))

        FormHeadingText("Event Duration")

        InputField(input = eventTitle, onValueChange = { eventTitle = it}, placeholder = "Select Duration")

        FormHeadingText("Location")

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

        InputField(placeholder = "Postal Code", input = postalCode, onValueChange ={ postalCode = it})

        Spacer(Modifier.height(15.dp))

        FormHeadingText("Additional Settings")

        Spacer(Modifier.height(10.dp))

        VisibilityOptionsSelector(selected = visibility, onOptionSelected = { visibility = it })

        Spacer(Modifier.height(15.dp))

        SubmitButton(modifier = Modifier, buttonText = "Post Event", onClickButton = { onClickSubmit() })
    }
}

@Preview(showBackground = true)
@Composable
fun NewPostScreenPreview() {
    val navController = rememberNavController()
    PostScreen(navController = navController)
}
