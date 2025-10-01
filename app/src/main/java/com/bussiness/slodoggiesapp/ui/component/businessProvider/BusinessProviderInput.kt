package com.bussiness.slodoggiesapp.ui.component.businessProvider

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor
import com.bussiness.slodoggiesapp.ui.theme.TextGrey
import kotlinx.coroutines.delay

@Composable
fun TopIndicatorBar(){
    Box(
        modifier = Modifier
            .width(88.dp)
            .height(7.dp)
            .clip(RoundedCornerShape(3.dp))
            .background(PrimaryColor)
    )
}

@Composable
fun PhoneNumberInputField(
    phoneNumber: String,
    onValueChange: (String) -> Unit,
    checkIcon : Boolean
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(Color.White, shape = RoundedCornerShape(10.dp))
            .border(1.dp, TextGrey, shape = RoundedCornerShape(10.dp))
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = phoneNumber,
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp),
                color = Color.Black,
                fontFamily = FontFamily(Font(R.font.outfit_regular))
            )

            if (checkIcon){
                Image(
                    painter = painterResource(id = R.drawable.check_ic),
                    contentDescription = "Checked",
                    modifier = Modifier.size(15.dp)
                )
            }

        }
    }
}

@Composable
fun EmailInputField(
    email: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    androidx.compose.material.OutlinedTextField(
        value = email,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                text = "Enter Email/ Phone ",
                fontFamily = FontFamily(Font(R.font.outfit_regular)),
                fontSize = 14.sp,
                color = TextGrey
            )
        },
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp),
        shape = RoundedCornerShape(10.dp),
        colors = androidx.compose.material.TextFieldDefaults.outlinedTextFieldColors(
            backgroundColor = Color.White,
            focusedBorderColor = TextGrey,
            unfocusedBorderColor = TextGrey,
            textColor = Color.Black
        ),
        singleLine = true
    )
}

@Composable
fun UserNameInputField(
    input: String,
    placeholder: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    androidx.compose.material.OutlinedTextField(
        value = input,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                text = placeholder,
                fontFamily = FontFamily(Font(R.font.outfit_regular)),
                fontSize = 14.sp,
                color = TextGrey,
                modifier = Modifier.padding(vertical = 0.dp) // text ko center me rakhta hai
            )
        },
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp),
        shape = RoundedCornerShape(10.dp),
        colors = androidx.compose.material.TextFieldDefaults.outlinedTextFieldColors(
            backgroundColor = Color.White,
            focusedBorderColor = TextGrey,
            unfocusedBorderColor = TextGrey,
            textColor = Color.Black,
            cursorColor = Color.Black
        ),
        singleLine = true,
        textStyle = androidx.compose.ui.text.TextStyle(
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(R.font.outfit_regular)),
            color = Color.Black
        )
    )
}




@Composable
fun ContinueButton(
    onClick: () -> Unit,
    text: String,
    textColor: Color = Color.White,
    iconColor: Color = Color.White,
    backgroundColor: Color = PrimaryColor
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(backgroundColor)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = textColor,
            fontSize = 15.sp,
            fontFamily = FontFamily(Font(R.font.outfit_medium))
        )
    }
}

@Composable
fun OtpInputField(
    otpText: String,
    onOtpTextChange: (String) -> Unit,
    keyboardController: SoftwareKeyboardController?,
    focusRequester: FocusRequester,
) {
    // Clickable Column without ripple effect
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .pointerInput(Unit) { detectTapGestures { focusRequester.requestFocus() } },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            repeat(4) { index ->
                val char = otpText.getOrNull(index)?.toString() ?: ""
                Box(
                    modifier = Modifier
                        .height(55.dp)
                        .width(52.dp)
                        .background(Color.White)
                        .drawBehind {
                            val strokeWidth = 2.dp.toPx()
                            val y = size.height - strokeWidth / 2
                            drawLine(
                                color = Color(0xFFB2B2B2),
                                start = Offset(0f, y),
                                end = Offset(size.width, y),
                                strokeWidth = strokeWidth
                            )
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = char,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily(Font(R.font.outfit_medium)),
                        color = Color.Black
                    )
                }
            }
        }

        // Hidden BasicTextField to handle numeric input only
        BasicTextField(
            value = otpText,
            onValueChange = {
                if (it.length <= 4 && it.all { char -> char.isDigit() }) {
                    onOtpTextChange(it)
                }
            },
            modifier = Modifier
                .focusRequester(focusRequester)
                .alpha(0f)
                .fillMaxWidth()
                .height(1.dp)
                .focusable(),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.NumberPassword,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                }
            ),
        )
    }
}

@Composable
fun OtpTimer(
    totalTime: Int = 24, // total time in seconds
    onTimerEnd: () -> Unit = {} // callback when timer finishes
) {
    var timeLeft by remember { mutableStateOf(totalTime) }

    // Countdown effect
    LaunchedEffect(key1 = timeLeft) {
        if (timeLeft > 0) {
            delay(1000L) // wait 1 second
            timeLeft--
        } else {
            onTimerEnd()
        }
    }

    // Format as mm:ss
    val minutes = timeLeft / 60
    val seconds = timeLeft % 60
    val formattedTime = String.format("%02d:%02d", minutes, seconds)

    Text(
        text = formattedTime,
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
        fontSize = 14.sp,
        fontFamily = FontFamily(Font(R.font.outfit_bold)),
        color = Color.Black
    )
}



@Composable
fun  SubmitButton(modifier: Modifier = Modifier,buttonText : String, onClickButton : () -> Unit,buttonTextSize : Int = 15){
    Button(
        onClick = onClickButton,
        modifier = modifier
            .height(42.dp)
            .fillMaxWidth()
            .padding(start = 9.dp, end = 9.dp),
        colors = ButtonDefaults.buttonColors(
            PrimaryColor,
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(10.dp),
        elevation = ButtonDefaults.buttonElevation(4.dp)
    ) {
        Text(
            text = buttonText,
            fontFamily = FontFamily(Font(R.font.outfit_medium)),
            fontSize = buttonTextSize.sp,
            textAlign = TextAlign.Center,
            color = Color.White
        )
    }
}


@Composable
fun  SubmitPreviewButton(modifier: Modifier = Modifier,buttonText : String, onClickButton : () -> Unit,buttonTextSize : Int = 15){
    Button(
        onClick = onClickButton,
        modifier = modifier
            .height(42.dp)
            .fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            PrimaryColor,
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(10.dp),
        elevation = ButtonDefaults.buttonElevation(4.dp)
    ) {
        Text(
            text = buttonText,
            fontFamily = FontFamily(Font(R.font.outfit_medium)),
            fontSize = buttonTextSize.sp,
            textAlign = TextAlign.Center,
            color = Color.White
        )
    }
}


@Composable
fun OutlineCustomButton(modifier: Modifier,onClick: () -> Unit,text: String) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier
            .height(34.dp)
            .fillMaxWidth()
            .padding(start = 9.dp, end = 9.dp)
            .clip(RoundedCornerShape(6.dp)),
        border = BorderStroke(1.dp, PrimaryColor),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = Color.White,
            contentColor = PrimaryColor
        ),
        shape = RoundedCornerShape(6.dp)
    ) {
        Text(
            text = text,
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(R.font.outfit_medium)),
            color = PrimaryColor
        )
    }
}

@Composable
fun FilledCustomButton(modifier: Modifier = Modifier,buttonText : String, onClickFilled : () -> Unit,buttonTextSize : Int = 15){
    Button(
        onClick = onClickFilled,
        modifier = modifier
            .height(34.dp)
            .fillMaxWidth()
            .padding(start = 9.dp, end = 9.dp),
        colors = ButtonDefaults.buttonColors(
            PrimaryColor,
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(6.dp),
        elevation = ButtonDefaults.buttonElevation(4.dp)
    ) {
        Text(
            text = buttonText,
            fontFamily = FontFamily(Font(R.font.outfit_medium)),
            fontSize = buttonTextSize.sp,
            textAlign = TextAlign.Center,
            color = Color.White
        )
    }
}
