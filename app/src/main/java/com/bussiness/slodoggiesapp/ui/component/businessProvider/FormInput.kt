package com.bussiness.slodoggiesapp.ui.component.businessProvider

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor
import com.google.accompanist.flowlayout.FlowRow


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
    modifier: Modifier = Modifier
) {
    androidx.compose.material.OutlinedTextField(
        value = input,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                text = placeholder,
                fontFamily = FontFamily(Font(R.font.outfit_regular)),
                fontSize = 15.sp,
                color = Color(0xFFAEAEAE)
            )
        },
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp),
        shape = RoundedCornerShape(8.dp),
        colors = androidx.compose.material.TextFieldDefaults.outlinedTextFieldColors(
            backgroundColor = Color.White,
            focusedBorderColor = Color(0xFFAEAEAE),
            unfocusedBorderColor = Color(0xFFAEAEAE),
            textColor = Color.Black
        ),
        singleLine = true
    )
}

@Composable
fun TopHeadingText(textHeading: String, onBackClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 18.dp, horizontal = 15.dp)
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





