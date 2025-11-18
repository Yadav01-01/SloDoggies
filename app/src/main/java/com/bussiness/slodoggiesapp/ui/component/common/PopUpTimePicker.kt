package com.bussiness.slodoggiesapp.ui.component.common

// File: IOSPopupTimePicker.kt


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.compose.ui.window.Dialog
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor
import com.bussiness.slodoggiesapp.ui.theme.TextGrey
import kotlin.math.absoluteValue

@Composable
fun PopupTimePicker(
    show: Boolean,
    onDismiss: () -> Unit,
    onConfirm: (hour: Int, minute: Int, am: Boolean) -> Unit
) {
    if (!show) return

    val hours = (1..12).toList()
    val minutes = (0..59).toList()

    val hourState = rememberLazyListState(initialFirstVisibleItemIndex = hours.size * 50)
    val minuteState = rememberLazyListState(initialFirstVisibleItemIndex = minutes.size * 50)

    var isAm by remember { mutableStateOf(true) }

    val itemHeight = 40.dp
    val visibleCount = 5

    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFFFDFDFE))
            ) {
                // Header
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "Cancel",
                        color = Color.Black,
                        fontFamily = FontFamily(Font(R.font.outfit_medium)),
                        fontWeight = FontWeight.Medium,
                        fontSize = 17.sp,
                        modifier = Modifier.clickable { onDismiss() }
                    )
                    Text(
                        "Done",
                        color = Color.Black,
                        fontFamily = FontFamily(Font(R.font.outfit_medium)),
                        fontWeight = FontWeight.Medium,
                        fontSize = 17.sp,
                        modifier = Modifier.clickable {
                            val selectedHour = hours[hourState.firstVisibleItemIndex % hours.size]
                            val selectedMinute = minutes[minuteState.firstVisibleItemIndex % minutes.size]
                            onConfirm(selectedHour, selectedMinute, isAm)
                            onDismiss()
                        }
                    )
                }

                HorizontalDivider(thickness = 1.dp, color = PrimaryColor)

                // Picker body
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(itemHeight * visibleCount)
                        .background(Color(0xFFF6F6F6))
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        WheelColumn(
                            items = hours,
                            state = hourState,
                            itemHeight = itemHeight,
                            visibleCount = visibleCount,
                            formatter = { it.toString().padStart(2, '0') },
                            primaryColor = PrimaryColor
                        )

                        Text(":", fontSize = 24.sp, modifier = Modifier.padding(horizontal = 4.dp))

                        WheelColumn(
                            items = minutes,
                            state = minuteState,
                            itemHeight = itemHeight,
                            visibleCount = visibleCount,
                            formatter = { it.toString().padStart(2, '0') },
                            primaryColor = PrimaryColor
                        )

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier.padding(start = 8.dp)
                        ) {
                            Text(
                                "AM",
                                fontSize = 18.sp,
                                fontWeight = if (isAm) FontWeight.Bold else FontWeight.Normal,
                                color = if (isAm) PrimaryColor else TextGrey,
                                modifier = Modifier
                                    .padding(vertical = 4.dp)
                                    .clickable { isAm = true }
                            )
                            Text(
                                "PM",
                                fontSize = 18.sp,
                                fontWeight = if (!isAm) FontWeight.Bold else FontWeight.Normal,
                                color = if (!isAm) PrimaryColor else TextGrey,
                                modifier = Modifier
                                    .padding(vertical = 4.dp)
                                    .clickable { isAm = false }
                            )
                        }
                    }

                    // Top + bottom fade overlay
                    Box(
                        Modifier
                            .align(Alignment.TopCenter)
                            .fillMaxWidth()
                            .height(itemHeight)
                            .background(
                                Brush.verticalGradient(
                                    listOf(Color.White.copy(alpha = 0.9f), Color.Transparent)
                                )
                            )
                    )
                    Box(
                        Modifier
                            .align(Alignment.BottomCenter)
                            .fillMaxWidth()
                            .height(itemHeight)
                            .background(
                                Brush.verticalGradient(
                                    listOf(Color.Transparent, Color.White.copy(alpha = 0.9f))
                                )
                            )
                    )
                }
            }
        }
    }
}

@Composable
private fun <T> WheelColumn(
    items: List<T>,
    state: LazyListState,
    itemHeight: Dp,
    visibleCount: Int,
    formatter: (T) -> String,
    primaryColor: Color
) {
    val repeated = 100
    val totalItems = items.size * repeated
    val density = LocalDensity.current
    val itemHeightPx = with(density) { itemHeight.toPx() }

    val snapBehavior = rememberSnapFlingBehavior(lazyListState = state)

    Box(
        modifier = Modifier
            .width(80.dp)
            .height(itemHeight * visibleCount),
        contentAlignment = Alignment.Center
    ) {
        LazyColumn(
            state = state,
            flingBehavior = snapBehavior,
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(vertical = itemHeight * (visibleCount / 2))
        ) {
            items(totalItems) { idx ->
                val value = items[idx % items.size]
                val layoutInfo = state.layoutInfo
                val centerPx = layoutInfo.viewportEndOffset / 2
                val itemInfo = layoutInfo.visibleItemsInfo.find { it.index == idx }
                val itemCenter = itemInfo?.let { it.offset + it.size / 2 } ?: centerPx

                val offset = itemCenter - centerPx
                val fraction = (offset.absoluteValue / (visibleCount / 2f * itemHeightPx)).coerceIn(0f, 1f)
                val isSelected = fraction < 0.5f

                val scale = if (isSelected) 1f else lerp(1f, 0.8f, fraction)
                val alpha = if (isSelected) 1f else lerp(1f, 0.4f, fraction)

                Box(
                    modifier = Modifier
                        .height(itemHeight)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = formatter(value),
                        fontSize = 18.sp * scale,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        color = primaryColor.copy(alpha = alpha),
                        fontFamily = FontFamily(Font(R.font.outfit_medium)),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}




@Preview(showBackground = true)
@Composable
fun PopupTime(){
    PopupTimePicker(
        show = true,
        onDismiss = { },
        onConfirm = { _, _, _ -> }
    )
}