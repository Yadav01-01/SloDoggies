package com.bussiness.slodoggiesapp.ui.screens.businessprovider

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.navigation.Routes
import com.bussiness.slodoggiesapp.ui.component.businessProvider.FormHeadingText
import com.bussiness.slodoggiesapp.ui.component.businessProvider.InputField
import com.bussiness.slodoggiesapp.ui.component.businessProvider.SubmitButton
import com.bussiness.slodoggiesapp.ui.component.businessProvider.TopHeadingText
import com.bussiness.slodoggiesapp.ui.component.businessProvider.TopStepProgressBar
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor
import com.google.accompanist.flowlayout.FlowRow

@Composable
fun BusinessRegistrationScreen(navController: NavHostController){

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var url by remember { mutableStateOf("") }
    var contact by remember { mutableStateOf("") }
    remember { mutableStateListOf<String>() }

    Column(modifier = Modifier.fillMaxSize()) {

        TopHeadingText(textHeading = "Business Registration", onBackClick = { navController.popBackStack()})

        TopStepProgressBar(currentStep = 1, totalSteps = 3, modifier = Modifier)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {

            Spacer(Modifier.height(5.dp))

            FormHeadingText("Business name")

            Spacer(Modifier.height(10.dp))

            InputField(input = name, onValueChange = { name = it}, placeholder = "Enter name")

            Spacer(Modifier.height(15.dp))

            FormHeadingText("Email")

            Spacer(Modifier.height(10.dp))

            InputField(input = email, onValueChange = { email = it}, placeholder = "Enter Email")

            Spacer(Modifier.height(15.dp))

            FormHeadingText("Upload Business Logo")

            Spacer(Modifier.height(10.dp))

            UploadPlaceholder()

            Spacer(Modifier.height(15.dp))

            FormHeadingText("Category")

            Spacer(Modifier.height(10.dp))

            CategoryInputField()

            Spacer(Modifier.height(15.dp))

            FormHeadingText("Location")

            Spacer(Modifier.height(10.dp))

            InputField(input = location, onValueChange = { location = it}, placeholder = "Enter Location")

            Spacer(Modifier.height(15.dp))

            FormHeadingText("Website")

            Spacer(Modifier.height(10.dp))

            InputField(input =url, onValueChange = { url = it}, placeholder = "URL")

            Spacer(Modifier.height(15.dp))

            FormHeadingText("Contact Number")

            Spacer(Modifier.height(10.dp))

            InputField(input = contact, onValueChange = { contact = it}, placeholder = "Enter Contact")

            Spacer(Modifier.height(15.dp))

            FormHeadingText("Available Days/Hours")

            Spacer(Modifier.height(10.dp))

//        InputField()

            Spacer(Modifier.height(15.dp))

            FormHeadingText("Upload Verification Docs. (Optional)")

            Spacer(Modifier.height(10.dp))

            UploadPlaceholder()

            Spacer(Modifier.height(20.dp))

            SubmitButton(modifier = Modifier,buttonText = "Submit", onClickButton = { navController.navigate(Routes.ADD_SERVICE) }, buttonTextSize = 15)

        }
    }
}

@Composable
fun UploadPlaceholder() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(85.dp)
            .background(color = Color(0xFFE5EFF2))
            .dashedBorder(
                strokeWidth = 1.dp,
                color = PrimaryColor,
                cornerRadius = 10.dp,
                dashLength = 10.dp,
                gapLength = 8.dp
            )
            .clickable { /* Open file picker */ },
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
           Icon(painter = painterResource(R.drawable.upload_ic), contentDescription = "upload",Modifier.wrapContentSize().clickable {  }
           , tint = PrimaryColor)
            Spacer(Modifier.height(3.dp))
            Text("Upload Here",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp
                ),
                fontFamily = FontFamily(Font(R.font.outfit_medium)),
                color = PrimaryColor)
        }
    }
}

@SuppressLint("SuspiciousModifierThen")
fun Modifier.dashedBorder(
    strokeWidth: Dp,
    color: Color,
    cornerRadius: Dp = 0.dp,
    dashLength: Dp = 10.dp,
    gapLength: Dp = 5.dp
): Modifier = this.then(
    drawBehind {
        val stroke = Stroke(
            width = strokeWidth.toPx(),
            pathEffect = PathEffect.dashPathEffect(
                floatArrayOf(dashLength.toPx(), gapLength.toPx()), 0f
            )
        )

        val corner = CornerRadius(cornerRadius.toPx(), cornerRadius.toPx())
        drawRoundRect(
            color = color,
            style = stroke,
            cornerRadius = corner,
            size = size
        )
    }
)

@Composable
fun CategoryInputField() {
    var text by remember { mutableStateOf("") }
    val categories = remember { mutableStateListOf<String>() }

    Column(modifier = Modifier.fillMaxWidth()) {
        // Input Box
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .background(Color.White, shape = RoundedCornerShape(8.dp))
                .border(1.dp, Color(0xFFAEAEAE), shape = RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                BasicTextField(
                    value = text,
                    onValueChange = { text = it },
                    singleLine = true,
                    textStyle = TextStyle(
                        color = Color.Black,
                        fontSize = 15.sp,
                        fontFamily = FontFamily(Font(R.font.outfit_regular))
                    ),
                    modifier = Modifier.weight(1f)
                ) { innerTextField ->
                    if (text.isEmpty()) {
                        Text(
                            text = "Category name",
                            color = Color(0xFFAEAEAE),
                            fontSize = 15.sp,
                            fontFamily = FontFamily(Font(R.font.outfit_regular))
                        )
                    }
                    innerTextField()
                }

                // Check Icon to add
                if (text.isNotBlank()) {
                    Icon(
                        painter = painterResource(id = R.drawable.check_ic),
                        contentDescription = "Add Category",
                        tint = Color(0xFF00B4D8),
                        modifier = Modifier
                            .size(18.dp)
                            .clickable {
                                if (text.isNotBlank() && !categories.contains(text.trim())) {
                                    categories.add(text.trim())
                                    text = ""
                                }
                            }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Category Chips
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            mainAxisSpacing = 8.dp,
            crossAxisSpacing = 8.dp
        ) {
            categories.forEach { category ->
                Box(
                    modifier = Modifier
                        .background(Color(0xFFEAF3F6), RoundedCornerShape(16.dp))
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = category,
                            color = Color.Black,
                            fontSize = 14.sp,
                            fontFamily = FontFamily(Font(R.font.outfit_medium))
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Remove",
                            modifier = Modifier
                                .size(16.dp)
                                .clickable {
                                    categories.remove(category)
                                }
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewBusinessRegistrationScreen() {
    // Use a dummy NavController for preview purposes
    val navController = rememberNavController()

    BusinessRegistrationScreen(navController = navController)
}
