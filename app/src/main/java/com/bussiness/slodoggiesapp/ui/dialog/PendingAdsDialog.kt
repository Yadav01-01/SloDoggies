package com.bussiness.slodoggiesapp.ui.dialog

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.ui.component.businessProvider.AdTopUpBtn

@Composable
fun AdsStatusDialog(
    onDismiss: () -> Unit,
    @DrawableRes icon: Int,
    heading : String,
    desc1 : String,
    desc2 : String,
    buttonText : String,
    onClick : () -> Unit
){
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(

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
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Header
                    Icon(
                        painter = painterResource(icon),
                        contentDescription = null,
                        tint = Color.Unspecified,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )

                    Spacer(modifier = Modifier.height(15.dp))

                    Text(
                        text = heading,
                        fontSize = 18.sp,
                        fontFamily = FontFamily(Font(R.font.outfit_medium)),
                        fontWeight = FontWeight.Medium,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    Text(
                        text = desc1,
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.outfit_regular)),
                        fontWeight = FontWeight.Normal,
                        color = Color.Black,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    Text(
                        text = desc2,
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.outfit_regular)),
                        fontWeight = FontWeight.Normal,
                        color = Color.Black,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    AdTopUpBtn(text = buttonText, onClick = { onClick() }, modifier = Modifier.fillMaxWidth())


                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewPendingAds(){
    AdsStatusDialog(
        onDismiss = {  },
        heading = "Your ad has been approved!",
        desc1 = "Congratulations! Your ad is now live and visible to users on the platform.",
        desc2 = "You can:\n" +
                "- View performance in the dashboard\n" +
                "- Edit or stop your ad anytime",
        icon = R.drawable.icon_park_outline_success,
        buttonText = "Proceed to pay.",
        onClick = {  }
    )
}