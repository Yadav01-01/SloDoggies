package com.bussiness.slodoggiesapp.ui.dialog

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.bussiness.slodoggiesapp.R

@Composable
fun SavedDialog(
    onDismiss: () -> Unit = {},
    @DrawableRes icon: Int,
    title: String,
    description: String,
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)

        ) {
            Spacer(Modifier.height(45.dp))

            // Close button
            Box(Modifier.fillMaxWidth()) {
                Image(
                    painter = painterResource(id = R.drawable.ic_cross_iconx),
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .clip(CircleShape)
                        .clickable { onDismiss() }
                )
            }

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp),
                shape = RoundedCornerShape(12.dp),
                color = Color.White
            ) {
                Column(
                    modifier = Modifier
                        .background(Color.White, shape = RoundedCornerShape(12.dp))
                        .padding(horizontal = 12.dp, vertical = 10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = icon),
                        contentDescription = "Icon",
                        modifier = Modifier.size(55.dp)
                    )

                    Spacer(Modifier.height(10.dp))

                    Text(
                        text = title,
                        fontFamily = FontFamily(Font(R.font.outfit_medium)),
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center,
                        color = Color.Black
                    )

                    Spacer(Modifier.height(5.dp))

                    Text(
                        text = description,
                        fontSize = 15.sp,
                        textAlign = TextAlign.Center,
                        fontFamily = FontFamily(Font(R.font.outfit_regular)),
                        color = Color(0xFF000000),
                        lineHeight = 20.sp
                    )

                }
            }
        }
    }
}