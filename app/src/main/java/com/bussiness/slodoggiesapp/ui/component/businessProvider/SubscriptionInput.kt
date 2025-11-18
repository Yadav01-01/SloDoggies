package com.bussiness.slodoggiesapp.ui.component.businessProvider

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.data.model.businessProvider.SubscriptionData
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor
import com.bussiness.slodoggiesapp.ui.theme.TextGrey

@Composable
fun SubscriptionItem(
    subscriptionItem: com.bussiness.slodoggiesapp.data.model.businessProvider.SubscriptionData,
    onUpgradeClick: () -> Unit,
    onCancelClick: () -> Unit
) {
    val backgroundColor = if (subscriptionItem.isSelected) PrimaryColor else Color.White
    val textColor = if (subscriptionItem.isSelected) Color.White else Color.Black
    val featureIconColor = if (subscriptionItem.isSelected) Color.White else PrimaryColor

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {

            // Plan Name
            Text(
                text = subscriptionItem.planName,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Medium,
                    fontFamily = FontFamily(Font(R.font.outfit_medium)),
                    color = if (subscriptionItem.isSelected) Color.White else PrimaryColor,
                    fontSize = 16.sp
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Price
            Row(verticalAlignment = Alignment.Bottom) {
                Text(
                    text = subscriptionItem.price,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily(Font(R.font.outfit_bold)),
                        color = if (subscriptionItem.isSelected) Color.White else Color(0xFF282828),
                        fontSize = 30.sp
                    )
                )
                Spacer(modifier = Modifier.width(2.dp))
                Text(
                    text = "/month",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontFamily = FontFamily(Font(R.font.outfit_medium)),
                        color = if (subscriptionItem.isSelected) Color.White else TextGrey,
                        fontSize = 14.sp
                    )
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Description
            Text(
                text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit",
                style = MaterialTheme.typography.bodySmall.copy(
                    fontFamily = FontFamily(Font(R.font.outfit_medium)),
                    fontSize = 14.sp,
                    color = if (subscriptionItem.isSelected) Color.White else TextGrey
                ),
                maxLines = 5,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(8.dp))

            Divider(Modifier.height(1.dp).fillMaxWidth().background(textColor))

            Spacer(modifier = Modifier.height(12.dp))

            // Feature List
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                subscriptionItem.features.forEach {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(R.drawable.check_circle),
                            contentDescription = null,
                            tint = featureIconColor,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = it,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontFamily = FontFamily(Font(R.font.outfit_medium)),
                                fontSize = 14.sp,
                                color = textColor
                            )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Activated/Upgrade Section
            if (subscriptionItem.isActivated) {
                Text(
                    text = "Activated",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontFamily = FontFamily(Font(R.font.outfit_semibold)),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = textColor
                    ), modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(12.dp))

                OutlinedButton(
                    onClick = onCancelClick,
                    modifier = Modifier.fillMaxWidth(),
                    border = BorderStroke(1.dp, textColor),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = Color.Transparent,
                        contentColor = textColor
                    ),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(
                        text = "Cancel",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontFamily = FontFamily(Font(R.font.outfit_semibold)),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }
            } else {
                Button(
                    onClick = onUpgradeClick,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color.Black
                    ),
                    border = BorderStroke(1.dp, Color.Black),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(
                        text = "Upgrade",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontFamily = FontFamily(Font(R.font.outfit_semibold)),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }
            }
        }
    }
}

