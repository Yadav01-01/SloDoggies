package com.bussiness.slodoggiesapp.ui.component.businessProvider

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
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
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor
import com.bussiness.slodoggiesapp.ui.theme.TextGrey
import com.google.accompanist.flowlayout.FlowRow
import java.time.format.DateTimeFormatter
import java.util.Calendar


@Composable
fun FormHeadingText(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleLarge.copy(
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(R.font.outfit_medium))
        ),
        color = Color.Black,
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
            .padding(horizontal = 12.dp, vertical = 8.dp), // inner padding
        contentAlignment = Alignment.CenterStart
    ) {
        // Placeholder
        if (input.isEmpty()) {
            Text(
                text = placeholder,
                fontFamily = FontFamily(Font(R.font.outfit_regular)),
                fontSize = fontSize.sp,
                color = Color(0xFFAEAEAE),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        // Text Input
        BasicTextField(
            value = input,
            onValueChange = onValueChange,
            singleLine = true,
            textStyle = TextStyle(
                fontFamily = FontFamily(Font(R.font.outfit_regular)),
                fontSize = fontSize.sp,
                color = Color.Black
            ),
            cursorBrush = SolidColor(Color.Black),
            modifier = Modifier.fillMaxWidth()
        )
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
fun CategoryInputField() {
    var text by remember { mutableStateOf("") }
    val categories = remember { mutableStateListOf<String>() }

    Column(modifier = Modifier.fillMaxWidth()) {
        // Input Box
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp)
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
                        painter = painterResource(id = R.drawable.ic_check_icon_blue),
                        contentDescription = "Add Category",
                        tint = Color(0xFF00B4D8),
                        modifier = Modifier
                            .size(20.dp)
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

@Composable
fun ScreenHeadingText(textHeading: String, onBackClick: () -> Unit,onSettingClick: () -> Unit) {

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

        IconButton(onClick = onSettingClick) {
            Icon(
                painter = painterResource(R.drawable.setting_ic),
                contentDescription = "settings",
                tint = Color.Unspecified,
                modifier = Modifier
                    .wrapContentSize()
            )
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

@Composable
fun LabeledCheckbox(
    label: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onCheckedChange(!checked) }
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = CheckboxDefaults.colors(
                checkedColor = Color.Black,
                uncheckedColor = Color.Gray,
                checkmarkColor = Color.White
            )
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = label,
            style = TextStyle(
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.poppins)),
                color = Color.Black
            )
        )
    }
}
