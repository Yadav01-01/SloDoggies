package com.bussiness.slodoggiesapp.ui.component.businessProvider

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor


@Composable
fun FormHeadingText(text: String){
    Text(
        text = text,
        style = MaterialTheme.typography.titleLarge.copy(
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        ),
        fontFamily = FontFamily(Font(R.font.outfit_medium)),
        color = Color.Black
    )
}

@Composable
fun InputField(
    input: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    height: Dp = 56.dp,
    fontSize: Int = 15,
    modifier: Modifier = Modifier,
) {
    androidx.compose.material.OutlinedTextField(
        value = input,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                text = placeholder,
                fontFamily = FontFamily(Font(R.font.outfit_regular)),
                fontSize = fontSize.sp,
                color = Color(0xFFAEAEAE)
            )
        },
        textStyle = TextStyle(
            fontFamily = FontFamily(Font(R.font.outfit_regular)),
            fontSize = fontSize.sp,
            color = Color.Black,
        ),
        modifier = modifier
            .fillMaxWidth()
            .height(height),
        shape = RoundedCornerShape(8.dp),
        colors = androidx.compose.material.TextFieldDefaults.outlinedTextFieldColors(
            backgroundColor = Color.White,
            focusedBorderColor = Color(0xFFAEAEAE),
            unfocusedBorderColor = Color(0xFFAEAEAE),
            cursorColor = Color.Black,
            focusedLabelColor = Color.Transparent,
            unfocusedLabelColor = Color.Transparent,
            placeholderColor = Color(0xFFAEAEAE),
            textColor = Color.Black
        ),
        singleLine = false
    )
}


@Composable
fun TopHeadingText(textHeading: String, onBackClick: () -> Unit) {
    val statusBarPadding = WindowInsets.statusBars.asPaddingValues()
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(
            start = 15.dp,
            end = 15.dp,
            top = statusBarPadding.calculateTopPadding()+15.dp,
            bottom = 18.dp
        )

    ) {
        Icon(
            painter = painterResource(R.drawable.back_ic),
            contentDescription = "back",
            tint = PrimaryColor,
            modifier = Modifier
                .clickable { onBackClick() }
                .wrapContentSize()
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = textHeading,
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Medium,
                fontSize = 20.sp
            ),
            fontFamily = FontFamily(Font(R.font.outfit_medium)),
            color = Color(0xFF221B22)
        )
    }
}


@Composable
fun TopStepProgressBar(
    currentStep: Int,
    totalSteps: Int,
    modifier: Modifier = Modifier
) {
    val progress = currentStep / totalSteps.toFloat()

    LinearProgressIndicator(
        progress = progress,
        color = PrimaryColor,
        trackColor = Color(0xFFE5E5E5),
        modifier = modifier
            .fillMaxWidth()
            .height(4.dp)
            .clip(RoundedCornerShape(50))
    )
}

@Composable
fun CheckInputField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    showCheckIcon: Boolean
) {
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
                value = value,
                onValueChange = onValueChange,
                singleLine = true,
                textStyle = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 15.sp,
                    color = Color.Black,
                    fontFamily = FontFamily(Font(R.font.outfit_regular))
                ),
                modifier = Modifier
                    .weight(1f)
            ) { innerTextField ->
                if (value.isEmpty()) {
                    Text(
                        text = placeholder,
                        color = Color(0xFFAEAEAE),
                        fontSize = 15.sp,
                        fontFamily = FontFamily(Font(R.font.outfit_regular))
                    )
                }
                innerTextField()
            }

            if (showCheckIcon) {
                Icon(
                    painter = painterResource(id = R.drawable.check_ic),
                    contentDescription = "Checked",
                    tint = PrimaryColor,
                    modifier = Modifier.wrapContentSize()
                )
            }
        }
    }
}

@Composable
fun ScreenHeadingText(textHeading: String, onBackClick: () -> Unit,onSettingClick: () -> Unit) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(
            start = 15.dp,
            end = 15.dp,
            top = 15.dp,
            bottom = 18.dp
        )

    ) {
        Row(modifier = Modifier.weight(1f).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(R.drawable.back_ic),
                contentDescription = "back",
                tint = PrimaryColor,
                modifier = Modifier
                    .clickable { onBackClick() }
                    .wrapContentSize()
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = textHeading,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Medium,
                    fontSize = 20.sp
                ),
                fontFamily = FontFamily(Font(R.font.outfit_medium)),
                color = Color(0xFF221B22)
            )
        }

        Icon(
            painter = painterResource(R.drawable.setting_ic),
            contentDescription = "settings",
            tint = Color.Unspecified,
            modifier = Modifier
                .wrapContentSize()
                .clickable { onSettingClick() }
        )
    }
}


