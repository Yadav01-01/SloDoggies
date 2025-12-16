package com.bussiness.slodoggiesapp.ui.screens.commonscreens.home.content

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor
import com.bussiness.slodoggiesapp.ui.theme.TextGrey

@Composable
    fun ShareContentDialog(onDismiss: () -> Unit,onSendClick: () -> Unit,data:String) {
    val shareItems = listOf(
        ShareIcon(R.drawable.cir_dog, "Person 1"),
        ShareIcon(R.drawable.cir_dog, "Person 2"),
        ShareIcon(R.drawable.cir_dog, "Person 3"),
        ShareIcon(R.drawable.cir_dog, "Person 4"),
        ShareIcon(R.drawable.cir_dog, "Person 5"),
        ShareIcon(R.drawable.cir_dog, "Person 6"),
        ShareIcon(R.drawable.cir_dog, "Person 7"),
    )

    val shareToMedia = listOf(
        ShareIcon(R.drawable.copy_url, "Copy url"),
        ShareIcon(R.drawable.whatsapp_ic, "WhatsApp"),
        ShareIcon(R.drawable.insta_ic, "Instagram"),
        ShareIcon(R.drawable.telegram_ic, "Telegram"),
        ShareIcon(R.drawable.fb_ic, "Facebook"),
        ShareIcon(R.drawable.x_ic, "X"),
        ShareIcon(R.drawable.message_ic, "Messages"),
        ShareIcon(R.drawable.more_ic, "More"),
    )
    val selectedItems = remember { mutableStateListOf<ShareIcon>() }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            decorFitsSystemWindows = false
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // Close Button
                Box(Modifier.fillMaxWidth()) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_cross_iconx),
                        contentDescription = null,
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .clip(CircleShape)
                            .clickable { onDismiss() }
                            .padding(8.dp)
                    )
                }

                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    color = Color.White
                ) {
                    Column(modifier = Modifier.background(Color.White)) {
                        Text(
                            text = "Share with friends!",
                            fontSize = 20.sp,
                            fontFamily = FontFamily(Font(R.font.outfit_medium)),
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(16.dp)
                        )

                        HorizontalDivider(
                            thickness = 1.dp,
                            color = PrimaryColor,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )

                        LazyVerticalGrid(
                            columns = GridCells.Fixed(4),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            items(shareItems.size) { index ->
                                val item = shareItems[index]
                                val isSelected = selectedItems.contains(item)

                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center,
                                    modifier = Modifier.clickable {
                                        if (isSelected) {
                                            selectedItems.remove(item)
                                        } else {
                                            selectedItems.add(item)
                                        }
                                    }
                                ) {
                                    Box(contentAlignment = Alignment.BottomEnd) {
                                        Image(
                                            painter = painterResource(id = item.image),
                                            contentDescription = item.name,
                                            modifier = Modifier
                                                .size(55.dp)
                                                .clip(CircleShape)
                                                .background(TextGrey)
                                        )
                                        if (isSelected) {
                                            Icon(
                                                painter = painterResource(R.drawable.selected_ic),
                                                contentDescription = null,
                                                tint = PrimaryColor,
                                                modifier = Modifier
                                                    .size(18.dp)
                                                    .offset(x = 4.dp, y = (-4).dp)
                                            )
                                        }
                                    }
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = item.name,
                                        fontSize = 14.sp,
                                        fontFamily = FontFamily(Font(R.font.outfit_medium)),
                                        fontWeight = FontWeight.Medium,
                                        color = Color.Black,
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        }

                        if (selectedItems.isEmpty()) {
                            HorizontalDivider(
                                thickness = 1.dp,
                                color = PrimaryColor,
                                modifier = Modifier.padding(bottom = 16.dp)
                            )
                        }


                        if (selectedItems.isEmpty()) {
                            LazyVerticalGrid(
                                columns = GridCells.Fixed(4),
                                modifier = Modifier.fillMaxWidth().padding(16.dp),
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                verticalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                items(shareToMedia.size) { index ->
                                    val item = shareToMedia[index]
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.Center,
                                        modifier = Modifier.clickable { /* Handle click */ }
                                    ) {
                                        Image(
                                            painter = painterResource(id = item.image),
                                            contentDescription = item.name,
                                            modifier = Modifier
                                                .size(55.dp)
                                                .clip(CircleShape)
                                                .background(TextGrey)
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            text = item.name,
                                            fontSize = 14.sp,
                                            fontFamily = FontFamily(Font(R.font.outfit_medium)),
                                            fontWeight = FontWeight.Medium,
                                            color = Color.Black,
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                }
                            }

                        }

                        if (selectedItems.isNotEmpty()) {
                            Button(
                                onClick = { onSendClick() },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(42.dp)
                                    .padding(horizontal = 16.dp), // one clean padding
                                shape = RoundedCornerShape(10.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = PrimaryColor
                                )
                            ) {
                                Text(
                                    text = stringResource(R.string.send),
                                    fontSize = 15.sp,
                                    fontFamily = FontFamily(Font(R.font.outfit_medium)),
                                    fontWeight = FontWeight.Medium,
                                    color = Color.White
                                )
                            }


                        }

                        Spacer(Modifier.height(10.dp))

                    }
                }
            }
        }
    }
}

    data class ShareIcon(
        val image: Int,
        val name: String
    )
