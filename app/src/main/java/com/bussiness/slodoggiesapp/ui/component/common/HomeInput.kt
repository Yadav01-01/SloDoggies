package com.bussiness.slodoggiesapp.ui.component.common

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor
import com.bussiness.slodoggiesapp.ui.theme.TextGrey
import com.joelkanyi.jcomposecountrycodepicker.component.KomposeCountryCodePicker
import com.joelkanyi.jcomposecountrycodepicker.component.rememberKomposeCountryCodePickerState

@Composable
fun HomeTopBar(
    modifier: Modifier = Modifier,
    onNotificationClick: () -> Unit ,
    onMessageClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = Color.White)
            .padding(horizontal = 16.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // App Logo with Icon
        Image(
            painter = painterResource(id = R.drawable.logo_name),
            contentDescription = "SloDogies Logo",
            modifier = Modifier.wrapContentSize()
        )

        // Action Icons: Notification and Message
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            IconButton(onClick = onNotificationClick) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_notification_bell),
                    contentDescription = "Notifications",
                    tint = Color.Black
                )
            }
            IconButton(onClick = onMessageClick) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_chat_icon),
                    contentDescription = "Messages",
                    tint = Color.Black
                )
            }
        }
    }
    HorizontalDivider(thickness = 2.dp, color = PrimaryColor)
}



@Composable
private fun PhoneNumberField(value: String, onValueChange: (String) -> Unit) {
    val state = rememberKomposeCountryCodePickerState()
    KomposeCountryCodePicker(
        modifier = Modifier.fillMaxWidth(),
        text = value,
        onValueChange = onValueChange,
        state = state,
        placeholder = {
            Text(stringResource(R.string.phone_number), color = Color.Gray)
        }
    )
}


@Composable
fun EmailField(
    email: String,
    isVerified: Boolean,
    onEmailChange: (String) -> Unit,
    onVerify: () -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = stringResource(R.string.email),
    fontSize: Int = 15
) {
    OutlinedTextField(
        value = email,
        onValueChange = onEmailChange,
        placeholder = {
            Text(
                text = placeholder,
                fontSize = fontSize.sp,
                fontFamily = FontFamily(Font(R.font.outfit_regular)),
                color = Color(0xFF949494)
            )
        },
        trailingIcon = {
            if (isVerified) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_verified_icon),
                    contentDescription = null,
                    tint = Color(0xFF258694),
                    modifier = Modifier.size(24.dp)
                )
            } else {
                TextButton(onClick = onVerify) {
                    Text(
                        text = stringResource(R.string.verify),
                        color = Color(0xFF258694),
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.outfit_medium))
                    )
                }
            }
        },
        singleLine = true,
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = TextGrey,
            unfocusedBorderColor = TextGrey
        ),
        modifier = Modifier.fillMaxWidth().height(50.dp)
    )
}




@Composable
 fun BioField(value: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(stringResource(R.string.enter_bio),
            fontSize = 15.sp,
            fontFamily = FontFamily(Font(R.font.outfit_regular)),
            color = TextGrey
            ) },
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = TextGrey,
            unfocusedBorderColor = TextGrey
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),

    )
}


@Composable
fun ExpandableText(
    text: String,
    minimizedMaxLines: Int = 5
) {
    var expanded by remember { mutableStateOf(false) }

    Column {
        Text(
            text = text,
            maxLines = if (expanded) Int.MAX_VALUE else minimizedMaxLines,
            overflow = TextOverflow.Ellipsis,
            style = LocalTextStyle.current,
            fontFamily = FontFamily(Font(R.font.outfit_regular)),
            fontSize = 15.sp,
            lineHeight = 17.sp  ,
            color = TextGrey,
            modifier = Modifier.animateContentSize() // smooth expand/collapse
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = if (expanded) "Show less" else "more",
            color = PrimaryColor,
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(R.font.outfit_medium)),
            modifier = Modifier.clickable { expanded = !expanded }
        )
    }
}
