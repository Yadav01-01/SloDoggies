package com.bussiness.slodoggiesapp.ui.component.businessProvider

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.content.Context
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.ui.component.common.PopupTimePicker
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor
import com.bussiness.slodoggiesapp.ui.theme.TextGrey
import com.google.accompanist.flowlayout.FlowRow
import java.util.Calendar


@Composable
fun FormHeadingText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Black
) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleLarge.copy(
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(R.font.outfit_medium))
        ),
        color = color,
        modifier = modifier
    )
}


@Composable
fun InputField(
    input: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    height: Dp = 48.dp,
    fontSize: Int = 15,
    modifier: Modifier = Modifier,
    readOnly: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .clip(RoundedCornerShape(8.dp))
            .background(Color.White)
            .border(
                width = 1.dp,
                color = Color(0xFFAEAEAE),
                shape = RoundedCornerShape(8.dp)
            )
    ) {

        // Actual text field (disabled when readOnly)
        BasicTextField(
            value = input,
            onValueChange = onValueChange,
            singleLine = true,
            enabled = !readOnly,      // 👈 IMPORTANT!!
            textStyle = TextStyle(
                fontFamily = FontFamily(Font(R.font.outfit_regular)),
                fontSize = fontSize.sp,
                color = Color.Black
            ),
            cursorBrush = SolidColor(Color.Black),
            modifier = Modifier
                .matchParentSize()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            keyboardOptions = keyboardOptions,
            readOnly = readOnly
        )

        // Placeholder
        if (input.isEmpty()) {
            Text(
                text = placeholder,
                fontFamily = FontFamily(Font(R.font.outfit_regular)),
                fontSize = fontSize.sp,
                color = Color(0xFFAEAEAE),
                modifier = Modifier
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        // Transparent clickable layer
        if (readOnly) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        onClick()
                    }
            )
        }
    }
}

@Composable
fun DescriptionBox(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(106.dp)
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))
            .padding(8.dp) // inner padding
    ) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            textStyle = TextStyle(
                fontFamily = FontFamily(Font(R.font.outfit_regular)),
                color = Color.Black,
                fontSize = 15.sp
            ),
            modifier = Modifier.fillMaxSize(),
            singleLine = false,
            decorationBox = { innerTextField ->
                if (value.isEmpty()) {
                    Text(
                        text = placeholder,
                        fontFamily = FontFamily(Font(R.font.outfit_regular)),
                        color = TextGrey,
                        fontSize = 15.sp
                    )
                }
                innerTextField()
            }
        )
    }
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
fun TopHeadingTextWithSkip(
    textHeading: String,
    onBackClick: () -> Unit,
    onSkipClick: () -> Unit = {}
) {
    val statusBarPadding = WindowInsets.statusBars.asPaddingValues()
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 15.dp,
                end = 15.dp,
                top = statusBarPadding.calculateTopPadding() + 15.dp,
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

        Spacer(Modifier.weight(1f))

        // Skip aligned to the right
        Text(
            text = "Skip",
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp
            ),
            lineHeight = 20.sp, // corrected from 10.sp (too small, was cutting text)
            fontFamily = FontFamily(Font(R.font.outfit_medium)),
            color = PrimaryColor,
            modifier = Modifier
                .padding(end = 15.dp)
                .clickable(
                    indication = null, // removes ripple effect
                    interactionSource = remember { MutableInteractionSource() } // required with indication=null
                ) { onSkipClick() }
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
        progress = { progress },
        modifier = modifier
            .fillMaxWidth()
            .height(3.dp)
            .clip(RoundedCornerShape(50)),
        color = PrimaryColor,
        trackColor = Color(0xFFE5E5E5),
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
fun CategoryInputField(
    categories: List<String>,
    onCategoryAdded: (String) -> Unit,
    onCategoryRemoved: (String) -> Unit
) {
    var text by remember { mutableStateOf("") }

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

                if (text.isNotBlank()) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "Add Category",
                        tint = PrimaryColor,
                        modifier = Modifier
                            .size(20.dp)
                            .clickable {
                                val trimmed = text.trim()
                                if (trimmed.isNotEmpty()) {
                                    onCategoryAdded(trimmed)
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
                        .background(Color(0xFFE5EFF2), RoundedCornerShape(6.dp))
                        .padding(horizontal = 10.dp, vertical = 1.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = category,
                            color = PrimaryColor,
                            fontSize = 10.sp,
                            fontFamily = FontFamily(Font(R.font.outfit_medium))
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Remove",
                            modifier = Modifier
                                .size(12.dp)
                                .clickable { onCategoryRemoved(category) }
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun ScreenHeadingText(
    textHeading: String,
    onBackClick: () -> Unit,
    @DrawableRes endIcon: Int = R.drawable.setting_ic,
    onSettingClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 7.dp)
    ) {
        // Left Section (Back + Title)
        IconButton(onClick = onBackClick) {
            Icon(
                painter = painterResource(R.drawable.back_ic),
                contentDescription = "back",
                tint = PrimaryColor
            )
        }

        Text(
            text = textHeading,
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Medium,
                fontSize = 20.sp
            ),
            fontFamily = FontFamily(Font(R.font.outfit_medium)),
            color = Color(0xFF221B22),
            modifier = Modifier.weight(1f) // pushes end icon to the right
        )

        // Right Section (Optional End Icon)
        IconButton(onClick = onSettingClick) {
            Icon(
                painter = painterResource(endIcon),
                contentDescription = "end icon",
                tint = Color.Unspecified
            )
        }
    }
}

@Composable
fun DayTimeSelector(
    onDone: (selectedDays: List<String>, from: String, to: String) -> Unit
) {
    val context = LocalContext.current

    val days = listOf(
        "All", "Monday", "Tuesday", "Wednesday",
        "Thursday", "Friday", "Saturday", "Sunday"
    )
    val selectedDays = remember { mutableStateListOf<String>() }

    var fromTime by remember { mutableStateOf("--:--") }
    var toTime by remember { mutableStateOf("--:--") }
    var expanded by remember { mutableStateOf(false) }

    var showTimePicker by remember { mutableStateOf(false) }
    var isSelectingFrom by remember { mutableStateOf(true) }

    Column(modifier = Modifier.fillMaxWidth()) {

        // ---- Summary Box ----
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .border(1.dp, Color(0xFF9C9C9C), RoundedCornerShape(8.dp))
                .clip(RoundedCornerShape(8.dp))
                .clickable { expanded = !expanded }
                .padding(horizontal = 12.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            if (selectedDays.isNotEmpty() && fromTime != "--:--" && toTime != "--:--") {
                Text(
                    text = "${selectedDays.joinToString(", ")} | $fromTime - $toTime",
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.outfit_regular)),
                    color = Color.Black,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(end = 18.dp)
                )
            } else {
                Text(
                    text = "Select Days and Time",
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.outfit_regular)),
                    color = Color.Gray
                )
            }

            Image(
                painter = painterResource(id = R.drawable.ic_date_iconn),
                contentDescription = "Calendar",
                modifier = Modifier
                    .size(22.dp)
                    .align(Alignment.CenterEnd)
            )
        }

        // ---- Expanded Section ----
        AnimatedVisibility(visible = expanded) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp)
                    .background(Color(0xFFF9F9F9), RoundedCornerShape(8.dp))
                    .padding(12.dp)
            ) {

                // Day Checkboxes
                for (i in days.indices step 2) {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        for (j in 0..1) {
                            if (i + j < days.size) {
                                val day = days[i + j]
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(vertical = 4.dp)
                                ) {
                                    CustomCheckBox(
                                        checked = selectedDays.contains(day) ||
                                                (day == "All" && selectedDays.size == days.size - 1),
                                        onCheckedChange = { checked ->
                                            if (day == "All") {
                                                if (checked) {
                                                    selectedDays.clear()
                                                    selectedDays.addAll(days.drop(1))
                                                } else {
                                                    selectedDays.clear()
                                                }
                                            } else {
                                                if (checked) selectedDays.add(day)
                                                else selectedDays.remove(day)
                                            }
                                        }
                                    )
                                    Spacer(Modifier.width(10.dp))
                                    Text(
                                        text = day,
                                        fontFamily = FontFamily(Font(R.font.outfit_regular)),
                                        fontSize = 14.sp,
                                        color = Color.Black
                                    )
                                }
                            } else Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Time Pickers
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    TimePickerField(
                        label = "From",
                        time = fromTime,
                        onClick = {
                            isSelectingFrom = true
                            showTimePicker = true
                        },
                        modifier = Modifier.weight(1f)
                    )

                    TimePickerField(
                        label = "To",
                        time = toTime,
                        onClick = {
                            isSelectingFrom = false
                            showTimePicker = true
                        },
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Done Button
                Button(
                    onClick = {
                        if (selectedDays.isEmpty() || fromTime == "--:--" || toTime == "--:--") {
                            Toast.makeText(context, "Please select days & time", Toast.LENGTH_SHORT).show()
                        } else {
                            expanded = false
                            onDone(selectedDays.toList(), fromTime, toTime)
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryColor)
                ) {
                    Text(
                        text = "Done",
                        fontFamily = FontFamily(Font(R.font.outfit_semibold)),
                        fontSize = 15.sp,
                        color = Color.White
                    )
                }
            }
        }
    }

    // ---- iOS-style Time Picker Popup ----
    if (showTimePicker) {
        PopupTimePicker(
            show = showTimePicker,
            onDismiss = { showTimePicker = false },
            onConfirm = { hour, minute, am ->
                val formatted = "${hour.toString().padStart(2, '0')}:${minute.toString().padStart(2, '0')} ${if (am) "AM" else "PM"}"
                if (isSelectingFrom) fromTime = formatted else toTime = formatted
                showTimePicker = false
            }
        )
    }
}



@Composable
fun CustomCheckBox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    size: Dp = 18.dp,
    checkedColor: Color = PrimaryColor, // teal
    uncheckedColor: Color = Color.Transparent,
    borderColor: Color = PrimaryColor
) {
    Box(
        modifier = modifier
            .size(size)
            .clip(RoundedCornerShape(3.dp))
            .background(if (checked) checkedColor else uncheckedColor)
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(3.dp)
            )
            .clickable { onCheckedChange(!checked) },
        contentAlignment = Alignment.Center
    ) {
        if (checked) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Checked",
                tint = Color.White,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}

@Composable
fun TimePickerField(
    label: String,
    time: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = label,
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(R.font.outfit_medium)),
            fontWeight = FontWeight.Medium,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(4.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .clip(RoundedCornerShape(8.dp))
                .border(1.dp, Color(0xFFAEAEAE), RoundedCornerShape(8.dp))
                .clickable { onClick() }
                .padding(horizontal = 12.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = time,
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.outfit_regular)),
                    fontWeight = FontWeight.Normal,
                    color = if (time == "--:--") Color.Gray else Color.Black
                )
                Icon(
                    painter = painterResource(R.drawable.time_ic),
                    contentDescription = "Pick Time",
                    tint = Color.Unspecified
                )
            }
        }
    }
}


@Composable
fun ParticipantTextWithIcon(textHeading: String, onBackClick: () -> Unit,onClick: () -> Unit,selected:Boolean) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(
            start = 5.dp,
            end = 5.dp,
            top = 7.dp,
            bottom = 7.dp
        )

    ) {
        Row(modifier = Modifier.weight(1f).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBackClick) {
                Icon(
                    painter = painterResource(R.drawable.back_ic),
                    contentDescription = "back",
                    tint = PrimaryColor,
                    modifier = Modifier
                        .wrapContentSize()
                )
            }
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

        if(selected){
            IconButton(onClick = onClick) {
                Icon(
                    painter = painterResource(R.drawable.tickok),
                    contentDescription = "tick",
                    tint = Color.Unspecified,
                    modifier = Modifier
                        .wrapContentSize()
                )
            }
        }

    }
}

@Composable
fun DayTimeDropdown() {
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }
    val days = listOf("All", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")
    val selectedDays = remember { mutableStateListOf<String>() }

    var fromTime by remember { mutableStateOf("") }
    var toTime by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {

        // Top Box (Dropdown Trigger)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF5F5F5), RoundedCornerShape(8.dp))
                .clickable { expanded = !expanded }
                .padding(16.dp)
        ) {
            Text(text = selectedDays.firstOrNull() ?: "Select Day", fontSize = 16.sp)
        }

        // Animated Dropdown
        AnimatedVisibility(visible = expanded, enter = fadeIn(), exit = fadeOut()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, RoundedCornerShape(8.dp))
                    .padding(16.dp)
            ) {
                // Day Selection
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        days.subList(0, 4).forEach { day ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        if (selectedDays.contains(day)) selectedDays.remove(day)
                                        else selectedDays.add(day)
                                    }
                                    .padding(4.dp)
                            ) {
                                Checkbox(
                                    checked = selectedDays.contains(day),
                                    onCheckedChange = {
                                        if (it) selectedDays.add(day) else selectedDays.remove(day)
                                    }
                                )
                                Text(day, fontSize = 14.sp)
                            }
                        }
                    }
                    Column {
                        days.subList(4, 8).forEach { day ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        if (selectedDays.contains(day)) selectedDays.remove(day)
                                        else selectedDays.add(day)
                                    }
                                    .padding(4.dp)
                            ) {
                                Checkbox(
                                    checked = selectedDays.contains(day),
                                    onCheckedChange = {
                                        if (it) selectedDays.add(day) else selectedDays.remove(day)
                                    }
                                )
                                Text(day, fontSize = 14.sp)
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Time Picker Row
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TimePickerBox("From", fromTime, context) { time -> fromTime = time }
                    Spacer(modifier = Modifier.width(8.dp))
                    TimePickerBox("To", toTime, context) { time -> toTime = time }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Done Button
                Button(
                    onClick = { expanded = false },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF009688))
                ) {
                    Text("Done", color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun TimePickerBox(label: String, time: String, context: Context, onTimeSelected: (String) -> Unit) {
    Box(
        modifier = Modifier
            .background(Color(0xFFF5F5F5), RoundedCornerShape(8.dp))
            .clickable { showTimePicker(context, onTimeSelected) }
            .padding(16.dp)
    ) {
        Text(if (time.isNotEmpty()) time else label)
    }
}

@SuppressLint("DefaultLocale")
fun showTimePicker(context: Context, onTimeSelected: (String) -> Unit) {
    val calendar = Calendar.getInstance()
    val hour = calendar.get(Calendar.HOUR_OF_DAY)
    val minute = calendar.get(Calendar.MINUTE)

    TimePickerDialog(context, { _, selectedHour, selectedMinute ->
        onTimeSelected(String.format("%02d:%02d", selectedHour, selectedMinute))
    }, hour, minute, true).show()
}

