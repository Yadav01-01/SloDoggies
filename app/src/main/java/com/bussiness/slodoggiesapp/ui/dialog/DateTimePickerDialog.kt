package com.bussiness.slodoggiesapp.ui.dialog

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.bussiness.slodoggiesapp.ui.screens.petowner.post.content.DateTimePickerScreen
import java.time.LocalDateTime

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DateTimePickerDialog(
    showDialog: Boolean,
    onDismissRequest: () -> Unit,
    onDateTimeSelected: (LocalDateTime) -> Unit
) {
    if (showDialog) {
        Dialog(onDismissRequest = onDismissRequest) {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = Color.White,
                tonalElevation = 8.dp
            ) {
                DateTimePickerScreen(
                    modifier = Modifier
                        .padding(2.dp)
                        .fillMaxWidth(),
                    onDateTimeSelected = onDateTimeSelected
                )
            }
        }
    }
}
