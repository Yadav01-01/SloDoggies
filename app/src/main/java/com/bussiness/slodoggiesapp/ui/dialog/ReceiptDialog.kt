package com.bussiness.slodoggiesapp.ui.dialog

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.data.model.businessProvider.AdTopUpTransaction
import com.bussiness.slodoggiesapp.ui.component.businessProvider.AdTopUpBtn
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor

@Composable
fun ReceiptDialog(
    onDismiss: () -> Unit,
    data : com.bussiness.slodoggiesapp.data.model.businessProvider.AdTopUpTransaction
){
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            decorFitsSystemWindows = false
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
                        .padding(8.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    // Header
                    Text(
                        text = stringResource(R.string.receipt),
                        fontSize = 18.sp,
                        fontFamily = FontFamily(Font(R.font.outfit_medium)),
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp)) {
                        TransactData("Transaction ID",data.id)
                        TransactData("Description",data.title)
                        TransactData("Date",data.date)
                        TransactData("Status",data.statusText)
                        TransactData("Amount",data.amount)
                        Spacer(Modifier.height(20.dp))
                    }
                    AdTopUpBtn(text = "Close", onClick = { onDismiss() }, modifier = Modifier.fillMaxWidth())
                }
            }
        }
    }
}


@Composable
fun TransactData(heading : String, data : String){
    Spacer(Modifier.height(5.dp))
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = heading,
            fontSize = 15.sp,
            fontWeight = FontWeight.Medium,
            color = PrimaryColor,
            fontFamily = FontFamily(Font(R.font.outfit_medium)),
            modifier = Modifier.weight(1f)
        )

        Text(
            text = data,
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            color = Color.Black,
            fontFamily = FontFamily(Font(R.font.outfit_regular)),
        )
    }
    Spacer(modifier = Modifier.height(5.dp))
    DashedDivider(Color(0xFFE5E5E5))
}

@Composable
fun DashedDivider(
    color: Color = Color.Gray,
    strokeWidth: Float = 2f,
    dashLength: Float = 10f,
    gapLength: Float = 10f,
    modifier: Modifier = Modifier
) {
    Canvas(
        modifier
            .fillMaxWidth()
            .height(1.dp)
    ) {
        drawLine(
            color = color,
            start = Offset(0f, 0f),
            end = Offset(size.width, 0f),
            strokeWidth = strokeWidth,
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(dashLength, gapLength), 0f)
        )
    }
}
