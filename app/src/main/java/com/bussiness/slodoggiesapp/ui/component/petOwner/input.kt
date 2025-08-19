package com.bussiness.slodoggiesapp.ui.component.petOwner

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bussiness.slodoggiesapp.R
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import com.bussiness.slodoggiesapp.ui.theme.TextGrey


@Composable
fun CommonBlueButton(
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 16.sp,
    text: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF258694)
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontFamily = FontFamily(Font(R.font.outfit_medium)),
                fontWeight = FontWeight.Medium,
                color = Color.White,
                fontSize = fontSize
            )
        )
    }
}



@Composable
fun CommonWhiteButton(
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 16.sp,
    text: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFFFFFFF)
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontFamily = FontFamily(Font(R.font.outfit_medium)),
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                color = Color.Black
            )
        )
    }
}

@Composable
fun CustomOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    fontSize: Int = 14,
    label: String? = null
) {
    Column(modifier = modifier.fillMaxWidth()) {
        label?.let {
            Text(
                text = it,
                fontSize = 15.sp,
                fontFamily = FontFamily(Font(R.font.outfit_medium)),
                color = Color.Black,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    text = placeholder,
                    fontSize = fontSize.sp,
                    fontFamily = FontFamily(Font(R.font.outfit_regular)),
                    color = Color(0xFF949494)
                )
            },
            modifier = Modifier.fillMaxWidth().height(50.dp),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = TextGrey,
                unfocusedBorderColor = TextGrey
            )
        )
    }
}
// Input.kt
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDropdownMenu(
    value: String,
    onValueChange: (String) -> Unit,
    options: List<String>,
    label: String,
    placeholder: String,
    modifier: Modifier = Modifier,
    isExpanded: Boolean,
    onExpandedChange: (Boolean) -> Unit
) {
    Column(modifier = modifier) {
        Text(
            text = label,
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(R.font.outfit_medium)),
            color = Color.Black,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        ExposedDropdownMenuBox(
            expanded = isExpanded,
            onExpandedChange = onExpandedChange
        ) {
            OutlinedTextField(
                value = value,
                onValueChange = {},
                readOnly = true,
                placeholder = {
                    Text(
                        placeholder,
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.outfit_regular)),
                        color = Color(0xFF949494)
                    )
                },
                trailingIcon = {
                    Icon(
                        Icons.Default.ArrowDropDown,
                        contentDescription = "Dropdown",
                        tint = Color(0xFF949494)
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF00ACC1),
                    unfocusedBorderColor = Color(0xFF949494)
                ),
                shape = RoundedCornerShape(12.dp)
            )

            ExposedDropdownMenu(
                expanded = isExpanded,
                onDismissRequest = { onExpandedChange(false) }
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                option,
                                fontFamily = FontFamily(Font(R.font.outfit_regular))
                            )
                        },
                        onClick = {
                            onValueChange(option)
                            onExpandedChange(false)
                        }
                    )
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonTopAppBar(
    title: String,
    onBackClick: () -> Unit,
    titleColor: Color = Color(0xFF3F393F),
    backIconTint: Color = Color(0xFF258694),
    dividerColor: Color = Color(0xFF656565),
    containerColor: Color = Color.White,
    titleFontFamily: FontFamily = FontFamily(Font(R.font.outfit_medium)),
    titleFontSize: TextUnit = 18.sp
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                fontSize = titleFontSize,
                fontFamily = titleFontFamily,
                color = titleColor
            )
        },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = backIconTint
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = containerColor
        )
    )
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(4.dp)
            .background(dividerColor)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonTopAppBarProfile(
    title: String,
    onBackClick: () -> Unit,
    onSettingsClick: () -> Unit = {},
    titleColor: Color = Color(0xFF3F393F),
    backIconTint: Color = Color(0xFF258694),
    settingsIconTint: Color = Color(0xFF3F393F),
    dividerColor: Color = Color(0xFF656565),
    containerColor: Color = Color.White,
    titleFontFamily: FontFamily = FontFamily(Font(R.font.outfit_medium)),
    titleFontSize: TextUnit = 18.sp
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                fontSize = titleFontSize,
                fontFamily = titleFontFamily,
                color = titleColor
            )
        },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = backIconTint
                )
            }
        },
        actions = {
            IconButton(onClick = onSettingsClick) {
                Icon(
                    painter = painterResource(R.drawable.ic_setting_icons),
                    contentDescription = "Settings",
                    tint = settingsIconTint
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = containerColor
        )
    )
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(4.dp)
            .background(dividerColor)
    )
}



@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    searchText: String = "",
    onSearchTextChange: (String) -> Unit = {},
    placeholder: String = "Search",
    enabled: Boolean = true,
    leadingIcon: (@Composable () -> Unit)? = {
        Icon(
            painter = painterResource(id = R.drawable.ic_search), // Replace with your search icon
            contentDescription = "Search",
            tint = Color.Gray,
            modifier = Modifier.size(20.dp)
        )
    },
    trailingIcon: (@Composable () -> Unit)? = null,
    backgroundColor: Color = Color(0xFFF5F5F5),
    cornerRadius: Dp = 25.dp,
    contentPadding: PaddingValues = PaddingValues(horizontal = 16.dp, vertical = 12.dp)
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(cornerRadius)
            )
            .padding(contentPadding),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Leading icon (search icon)
        leadingIcon?.invoke()

        Spacer(modifier = Modifier.width(12.dp))

        // Search text field
        BasicTextField(
            value = searchText,
            onValueChange = onSearchTextChange,
            enabled = enabled,
            modifier = Modifier.weight(1f),
            textStyle = TextStyle(
                fontSize = 16.sp,
                color = Color.Black
            ),
            decorationBox = { innerTextField ->
                if (searchText.isEmpty()) {
                    Text(
                        text = placeholder,
                        color = Color.Gray,
                        fontSize = 16.sp
                    )
                }
                innerTextField()
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search
            )
        )

        // Trailing icon (optional)
        trailingIcon?.let {
            Spacer(modifier = Modifier.width(12.dp))
            it()
        }
    }
}

