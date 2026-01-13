package com.bussiness.slodoggiesapp.ui.component.businessProvider

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor
import com.bussiness.slodoggiesapp.ui.theme.TextGrey

@Composable
fun ChoosePostTypeButton(
    modifier: Modifier = Modifier,
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {

    Button (
        onClick = onClick,
        modifier = modifier
            .height(34.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp)),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) PrimaryColor else Color.White,
            contentColor = if (isSelected) Color.White else Color.Black
        ),
        shape = RoundedCornerShape(10.dp)
    ) {
        Text(
            text = text,
            fontSize = 14.sp,
            fontFamily = if (isSelected)FontFamily(Font(R.font.outfit_semibold)) else FontFamily(Font(R.font.outfit_regular)),
            color = if (isSelected) Color.White else Color.Black
        )
    }
}

@Composable
fun VisibilityOptionsSelector(
    selected: String,
    onOptionSelected: (String) -> Unit
) {
    val options = listOf(
        "Public",
        "Followers Only",
        "Private (Only me)"
    )

    Column {
        options.forEach { option ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
                    .clickable { onOptionSelected(option) }
            ) {
                Text(
                    text = option,
                    modifier = Modifier.weight(1f),
                    fontFamily = FontFamily(Font(R.font.outfit_regular)),
                    fontSize = 15.sp,
                    color = Color.Black
                )
                RadioButton(
                    selected = selected == option,
                    onClick = { onOptionSelected(option) },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = Color(0xFF00838F)
                    )
                )
            }
        }
    }
}

@Composable
fun SettingToggleItem(
    label: String,
    checked: Boolean,
    onToggle: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            modifier = Modifier.weight(1f),
            fontSize = 15.sp,
            fontFamily = FontFamily(Font(R.font.outfit_regular)),
            color = Color.Black,
            fontWeight = FontWeight.Normal
        )
        Switch(
            checked = checked,
            onCheckedChange = onToggle,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = PrimaryColor,
                uncheckedThumbColor = Color.White,
                uncheckedTrackColor = Color.LightGray
            )
        )
    }
}

@Composable
fun CustomDropdownBox(
    label: String,
    items: List<String>,
    selectedItem: String,
    onItemSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color(0xFFAEAEAE), RoundedCornerShape(8.dp))
                .background(Color.White, RoundedCornerShape(8.dp))
                .clickable { expanded = !expanded }
                .padding(12.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = label, color = TextGrey, fontSize = 15.sp, fontFamily = FontFamily(Font(R.font.outfit_regular)))
                Icon(
                    painter = painterResource(id = if (expanded) R.drawable.ic_arrow_up else R.drawable.ic_arrow_down  ),
                    contentDescription = null,
                    tint = Color.Gray
                )
            }
        }

        // Dropdown list
        AnimatedVisibility(visible = expanded) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                shape = RoundedCornerShape(8.dp),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 8.dp // Stronger shadow for visibility
                ),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White // Ensure shadow contrast
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp, vertical = 5.dp)
                        .background(Color.White, RoundedCornerShape(8.dp))
                ) {
                    items.forEach { item ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    if (item == selectedItem) PrimaryColor else Color.White,
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .clickable {
                                    onItemSelected(item)
                                    expanded = false
                                }
                                .padding(horizontal = 12.dp, vertical = 12.dp)
                        ) {
                            Text(
                                text = item,
                                color = if (item == selectedItem) Color.White else Color.Black,
                                fontFamily = FontFamily(Font(R.font.outfit_regular)),
                                fontSize = 14.sp
                            )
                        }
                    }
                }
            }
        }
    }
}



@Composable
fun CustomDropdownBox1(
    label: String,
    items: List<String>,
    selectedItem: String,
    onItemSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }
    Log.d("TESTING_DROP_DOWN_LIST","Size is "+items.size)
    Column(modifier = Modifier.fillMaxWidth()) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color(0xFFAEAEAE), RoundedCornerShape(8.dp))
                .background(Color.White, RoundedCornerShape(8.dp))
                .clickable(
                  interactionSource = interactionSource,
                    indication = null
                ) { expanded = !expanded }
                .padding(12.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = if (selectedItem.isNotEmpty()) selectedItem else label,
                    color = if (selectedItem.isNotEmpty()) Color.Black else TextGrey,
                    fontSize = 15.sp,
                    fontFamily = FontFamily(Font(R.font.outfit_regular)))

                Icon(
                    painter = painterResource(
                        id = if (expanded) R.drawable.ic_arrow_up else R.drawable.ic_arrow_down
                    ),
                    contentDescription = null,
                    tint = Color.Gray
                )
            }
        }

        // Dropdown list
        AnimatedVisibility(
            visible = expanded,
            enter = expandVertically() + fadeIn(),
            exit = shrinkVertically() + fadeOut()
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 6.dp),
                shape = RoundedCornerShape(8.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    items.forEach { item ->
                        Log.d("TESTING_DROP_DOWN_LIST","Item name is "+item)
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    //if (item == selectedItem) PrimaryColor else Color.White,
                                    if (item == selectedItem) PrimaryColor else Color.White,
                                    RoundedCornerShape(6.dp)
                                )
                                .clickable(
                                    interactionSource = interactionSource,
                                    indication = null
                                ) {
                                    onItemSelected(item)
                                    expanded = false
                                }
                                .padding(horizontal = 12.dp, vertical = 12.dp)
                        ) {
                            Text(
                                text = item,
                                color = if (item == selectedItem) Color.Black else Color.Black,
                                fontSize = 14.sp,
                                fontFamily = FontFamily(Font(R.font.outfit_regular))
                            )
                        }
                    }
                }
            }
        }
    }
}




@Composable
fun ScrollableDropdownBox(
    label: String,
    items: List<String>,
    selectedItem: String,
    onItemSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val itemHeight = 48.dp // Height of one dropdown item
    val maxVisibleItems = 5

    Column(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color(0xFFAEAEAE), RoundedCornerShape(8.dp))
                .background(Color.White, RoundedCornerShape(8.dp))
                .clickable { expanded = !expanded }
                .padding(12.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = label,
                    color = TextGrey,
                    fontSize = 15.sp,
                    fontFamily = FontFamily(Font(R.font.outfit_regular))
                )
                Icon(
                    painter = painterResource(id = if (expanded) R.drawable.ic_arrow_up else R.drawable.ic_arrow_down),
                    contentDescription = null,
                    tint = Color.Gray
                )
            }
        }

        // Dropdown list
        AnimatedVisibility(visible = expanded) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                shape = RoundedCornerShape(8.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                val dropdownHeight = if (items.size > maxVisibleItems) itemHeight * maxVisibleItems else Dp.Unspecified

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = dropdownHeight)
                        .background(Color.White)
                ) {
                    items(items) { item ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    if (item == selectedItem) PrimaryColor else Color.White,
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .clickable {
                                    onItemSelected(item)
                                    expanded = false
                                }
                                .padding(horizontal = 12.dp, vertical = 12.dp)
                        ) {
                            Text(
                                text = item,
                                color = if (item == selectedItem) Color.White else Color.Black,
                                fontFamily = FontFamily(Font(R.font.outfit_regular)),
                                fontSize = 14.sp
                            )
                        }
                    }
                }
            }
        }
    }
}
