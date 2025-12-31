package com.bussiness.slodoggiesapp.ui.dialog

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.data.newModel.discover.PetPlaceItem
import com.bussiness.slodoggiesapp.ui.component.businessProvider.FormHeadingText
import com.bussiness.slodoggiesapp.ui.component.common.ExpandableText
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor

@Composable
fun PetPlaceDialog(petPlace : PetPlaceItem,onDismiss: () -> Unit) {


//    Dialog(
//        onDismissRequest = onDismiss,
//        properties = DialogProperties(
//            usePlatformDefaultWidth = false,
//            decorFitsSystemWindows = false
//        ),
//    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp),
            contentAlignment = Alignment.BottomCenter // Aligns content at bottom
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                // Close button (optional - move it above dialog if needed)
                Box(Modifier.fillMaxWidth()) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_cross_iconx),
                        contentDescription = null,
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .clip(CircleShape)
                            .clickable { onDismiss() }
                            .padding(vertical = 5.dp)
                    )
                }

                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    shape = RoundedCornerShape(16.dp),
                    color = Color.White
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .verticalScroll(rememberScrollState())
                    ) {
                        Text(
                            text = petPlace.location?:"Unknown",
                            fontSize = 18.sp,
                            fontFamily = FontFamily(Font(R.font.outfit_medium)),
                            color = Color.Black
                        )

                        Spacer(modifier = Modifier.height(15.dp))
                        Divider(thickness = 1.dp, color = PrimaryColor)
                        Spacer(modifier = Modifier.height(12.dp))

                        AsyncImage(
                            model = petPlace.image,
                            placeholder = painterResource(R.drawable.no_image),
                            error = painterResource(R.drawable.no_image),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(210.dp).
                            clip(shape = RoundedCornerShape(4.dp)),

                        )

                        Spacer(modifier = Modifier.height(15.dp))

                        FormHeadingText(stringResource(R.string.overview))

                        Spacer(modifier = Modifier.height(2.dp))

                        HorizontalDivider(thickness = 1.dp, color = PrimaryColor)

                        Spacer(modifier = Modifier.height(16.dp))

                        ExpandableText(petPlace.overview)
                    }
                }
            }
        }
    //}
}
