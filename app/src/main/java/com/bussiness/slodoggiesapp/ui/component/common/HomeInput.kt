package com.bussiness.slodoggiesapp.ui.component.common

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor
import com.bussiness.slodoggiesapp.ui.theme.TextGrey

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
            horizontalArrangement = Arrangement.spacedBy(2.dp)
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
fun EmailField(
    email: String,
    isVerified: Boolean,
    onEmailChange: (String) -> Unit,
    onVerify: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = stringResource(R.string.email),
    fontSize: Int = 15
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .border(1.dp, TextGrey, RoundedCornerShape(8.dp))
            .padding(horizontal = 12.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // BasicTextField for Email Input
            Box(modifier = Modifier.weight(1f)) {
                if (email.isEmpty()) {
                    Text(
                        text = placeholder,
                        fontSize = fontSize.sp,
                        fontFamily = FontFamily(Font(R.font.outfit_regular)),
                        color = Color(0xFF949494)
                    )
                }
                BasicTextField(
                    value = email,
                    onValueChange = onEmailChange,
                    singleLine = true,
                    textStyle = LocalTextStyle.current.copy(
                        fontSize = fontSize.sp,
                        fontFamily = FontFamily(Font(R.font.outfit_regular)),
                        color = Color.Black
                    ),
                    cursorBrush = SolidColor(Color.Black),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // Trailing Icon or Verify Button
            if (isVerified) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_verified_icon),
                    contentDescription = null,
                    tint = Color(0xFF258694),
                    modifier = Modifier.size(24.dp)
                )
            } else {
                TextButton(onClick = { onVerify("dialogEmail") }, enabled = email.isNotEmpty(), contentPadding = PaddingValues(0.dp)) {
                    Text(
                        text = stringResource(R.string.verify),
                        color = Color(0xFF258694),
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.outfit_medium))
                    )
                }
            }
        }
    }
}


@Composable
fun PhoneNumber(
    phone: String,
    isVerified: Boolean,
    onPhoneChange: (String) -> Unit,
    onVerify: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "555 123 456",
    fontSize: Int = 15
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color.White)
            .border(1.dp, TextGrey, RoundedCornerShape(8.dp))
            .padding(horizontal = 12.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // Phone Input Field
            Box(modifier = Modifier.weight(1f)) {
                if (phone.isEmpty()) {
                    Text(
                        text = placeholder,
                        fontSize = fontSize.sp,
                        fontFamily = FontFamily(Font(R.font.outfit_regular)),
                        color = Color(0xFF949494)
                    )
                }
                BasicTextField(
                    value = phone,
                    onValueChange = onPhoneChange,
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    textStyle = LocalTextStyle.current.copy(
                        fontSize = fontSize.sp,
                        fontFamily = FontFamily(Font(R.font.outfit_regular)),
                        color = Color.Black
                    ),
                    cursorBrush = SolidColor(Color.Black),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // Trailing Icon or Verify Button
            if (isVerified) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_verified_icon),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = Modifier.size(24.dp)
                )
            } else {
                TextButton(onClick = { onVerify("dialogPhone") }, enabled = phone.isNotEmpty(), contentPadding = PaddingValues(0.dp)) {
                    Text(
                        text = stringResource(R.string.verify),
                        color = PrimaryColor,
                        fontSize = 15.sp,
                        fontFamily = FontFamily(Font(R.font.outfit_medium))
                    )
                }
            }
        }
    }
}


@Composable
fun EmailTextField(
    email: String,
    isVerified: Boolean,
    onEmailChange: (String) -> Unit,
    onVerify: () -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String,
    fontSize: Int = 15
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .clip(RoundedCornerShape(8.dp))
            .border(1.dp, TextGrey, RoundedCornerShape(8.dp))
            .padding(horizontal = 12.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Phone Input Field
            Box(modifier = Modifier.weight(1f)) {
                if (email.isEmpty()) {
                    Text(
                        text = placeholder,
                        fontSize = fontSize.sp,
                        fontFamily = FontFamily(Font(R.font.outfit_regular)),
                        color = Color(0xFF949494)
                    )
                }
                BasicTextField(
                    value = email,
                    onValueChange = onEmailChange,
                    singleLine = true,
                    textStyle = LocalTextStyle.current.copy(
                        fontSize = fontSize.sp,
                        fontFamily = FontFamily(Font(R.font.outfit_regular)),
                        color = Color.Black
                    ),
                    cursorBrush = SolidColor(Color.Black),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // Trailing Icon or Verify Button
            if (isVerified) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_verified_icon),
                    contentDescription = null,
                    tint = Color(0xFF258694),
                    modifier = Modifier.size(24.dp)
                )
            } else {
                TextButton(onClick = onVerify,enabled = email.isNotEmpty(), contentPadding = PaddingValues(0.dp)) {
                    Text(
                        text = stringResource(R.string.verify),
                        color = Color(0xFF258694),
                        fontSize = 15.sp,
                        fontFamily = FontFamily(Font(R.font.outfit_medium))
                    )
                }
            }
        }
    }
}

@Composable
fun BioField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = stringResource(R.string.enter_bio),
    fontSize: Int = 15
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .border(1.dp, TextGrey, RoundedCornerShape(8.dp))
            .padding(horizontal = 12.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        if (value.isEmpty()) {
            Text(
                text = placeholder,
                fontSize = fontSize.sp,
                fontFamily = FontFamily(Font(R.font.outfit_regular)),
                color = TextGrey
            )
        }

        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            textStyle = LocalTextStyle.current.copy(
                fontSize = fontSize.sp,
                fontFamily = FontFamily(Font(R.font.outfit_regular)),
                color = Color.Black
            ),
            cursorBrush = SolidColor(Color.Black),
            modifier = Modifier.fillMaxWidth()
        )
    }
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


@Composable
fun ParentLabel(label: String) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(50.dp))
            .background(Color(0xFFE5EFF2))
            .padding(horizontal = 8.dp, vertical = 2.dp)
    ) {
        Text(
            text = label,
            color = PrimaryColor,
            fontSize = 8.sp,
            lineHeight = 14.sp,
            fontFamily = FontFamily(Font(R.font.outfit_medium)),
            fontWeight = FontWeight.Medium
        )
    }
}
