package com.bussiness.slodoggiesapp.ui.component.PetOwner.Dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import com.bussiness.slodoggiesapp.R
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material3.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.runtime.remember

@Composable
fun CustomDropdownMenuUpdated(
    value: String,
    onValueChange: (String) -> Unit,
    options: List<String>,
    label: String,
    placeholder: String,
    isExpanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {

    Column(modifier = modifier) {
        Text(
            text = label,
            fontSize = 15.sp,
            fontFamily = FontFamily(Font(R.font.outfit_medium)),
            color = Color.Black,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Box {
            // Clickable Box overlay to handle clicks
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) { onExpandedChange(!isExpanded) }
            ) {
                OutlinedTextField(
                    value = value.ifEmpty { placeholder },
                    onValueChange = { },
                    readOnly = true,
                    enabled = false,
                    modifier = Modifier.fillMaxWidth(),
//                    colors = OutlinedTextFieldDefaults.colors(
//                        focusedBorderColor = Color(0xFF00ACC1),
//                        unfocusedBorderColor = Color(0xFF949494),
//                        disabledBorderColor = Color(0xFF949494),
//                        disabledTextColor = if (value.isEmpty()) Color(0xFF949494) else Color.Black
//                    ),
                    trailingIcon = {
                        Icon(
                            painter = painterResource(
                                id = if (isExpanded) R.drawable.ic_arrow_up else R.drawable.ic_arrow_down
                            ),
                            contentDescription = "Dropdown arrow",
                            tint = Color(0xFF949494)
                        )
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF00ACC1),
                        unfocusedBorderColor = Color(0xFF949494)
                    ),
                    shape = RoundedCornerShape(12.dp)
                )
            }

            DropdownMenu(
                expanded = isExpanded,
                onDismissRequest = { onExpandedChange(false) },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        onClick = {
                            onValueChange(option)
                            onExpandedChange(false)
                        },
                        text = {
                            Text(
                                text = option,
                                fontSize = 14.sp,
                                fontFamily = FontFamily(Font(R.font.outfit_regular)),
                                color = if (option == value) Color.White else Color.Black
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                if (option == value) Color(0xFF00ACC1) else Color.Transparent
                            )
                    )
                }
            }
        }
    }
}
