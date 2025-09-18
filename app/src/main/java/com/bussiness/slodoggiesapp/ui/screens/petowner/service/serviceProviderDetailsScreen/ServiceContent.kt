package com.bussiness.slodoggiesapp.ui.screens.petowner.service.serviceProviderDetailsScreen

import android.R.attr.maxWidth
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.ui.component.petOwner.Dialog.FullScreenImageViewerScreen


@Composable
fun ServicesContent() {
    val servicesList = listOf(
        ServiceItem(
            packageName = "Full Grooming Package",
            description = "Complete grooming service with bath, haircut, and nail trimming.",
            amount = "100"
        ),
        ServiceItem(
            packageName = "Bath & Brush",
            description = "Includes bath, drying, and brushing for your pet.",
            amount = "50"
        ),
        ServiceItem(
            packageName = "Nail Clipping",
            description = "Professional nail trimming to keep your pet comfortable.",
            amount = "30"
        ),
        ServiceItem(
            packageName = "Bath & Brush",
            description = "Includes bath, drying, and brushing for your pet.",
            amount = "50"
        ),
        ServiceItem(
            packageName = "Nail Clipping",
            description = "Professional nail trimming to keep your pet comfortable.",
            amount = "30"
        )
    )

    LazyColumn(Modifier.fillMaxWidth()) {
        item {
            Text(
                text = stringResource(R.string.available_services),
                fontSize = 14.sp,
                color = Color.Black,
                fontFamily = FontFamily(Font(R.font.outfit_semibold)),
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        items(servicesList) { service ->
            EnhancedExpandableFullGroomingPackage(
                packageName = service.packageName,
                description = service.description,
                amount = service.amount
            )
        }
    }
}


data class ServiceItem(
    val packageName: String,
    val description: String,
    val amount: String
)


@Composable
fun EnhancedExpandableFullGroomingPackage(packageName: String,description : String,amount : String) {
    var expanded by remember { mutableStateOf(false) }
    Surface(
        shape = RoundedCornerShape(6.dp),
        border = BorderStroke(1.dp, Color(0xFFE5EFF2)),
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                vertical = 10.dp,
                horizontal = 4.dp
            )
    ) {
        Column(modifier = Modifier.animateContentSize()) {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = !expanded }
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row {
                    Image(
                        painter = painterResource(id = R.drawable.ic_paw_like_filled_icon),
                        contentDescription = "Paw",
                        modifier = Modifier.size(17.dp)
                    )
                    Spacer(Modifier.width(12.dp))
                    Text(
                        text = packageName,
                        color = Color(0xFF000000),
                        fontSize = 15.sp,
                        fontFamily = FontFamily(Font(R.font.outfit_medium)),
                    )
                }

                Image(
                    painter = painterResource(id = if (expanded) R.drawable.ic_dropdown_open_icon else R.drawable.ic_dropdown_close_icon),
                    contentDescription = null,
                    modifier = Modifier
                        .height(16.dp)
                        .width(16.dp)
                )
            }
            if (expanded) {
                Column {

                Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                    Text(
                        text = description,
                        fontSize = 12.sp,
                        color = Color(0xFF666666),
                        fontFamily = FontFamily(Font(R.font.outfit_regular)),
                        modifier = Modifier.padding(bottom = 16.dp)
                    )


                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(R.string.amount),
                            fontSize = 14.sp,
                            color = Color.Black,
                            fontFamily = FontFamily(Font(R.font.outfit_medium))
                        )

                        Text(
                            text = "$$amount",
                            fontSize = 14.sp,
                            color = Color.Black,
                            fontFamily = FontFamily(Font(R.font.outfit_medium))
                        )
                    }


                    Spacer(Modifier.height(20.dp))

                    Text(
                        text = stringResource(R.string.photos),
                        fontSize = 14.sp,
                        color = Color.Black,
                        fontFamily = FontFamily(Font(R.font.outfit_medium))
                    )

                }

                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    color = Color(0xFFE5EFF2),
                    thickness = 1.dp
                )

                ImageGalleryScreen()
            }
            }
        }

    }
}



@Composable
fun ImageGalleryScreen() {
    var selectedImageIndex by remember { mutableStateOf<Int?>(null) }

    val images = listOf(
        R.drawable.dummy_image5,
        R.drawable.dummy_image5,
        R.drawable.dummy_image5,
        R.drawable.dummy_image5,
        R.drawable.dummy_image5,
        R.drawable.dummy_image5,
        R.drawable.dummy_image5,
        R.drawable.dummy_image5,
        R.drawable.dummy_image5,
        R.drawable.dummy_image5,

        )

    val maxVisibleImages = 6
    val gridHeight = 220.dp

    Box(modifier = Modifier.fillMaxWidth().height(gridHeight)) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(8.dp)
        ) {
            if (images.size <= maxVisibleImages) {
                items(images.size) { index ->
                    ImageGridItem(
                        imageRes = images[index],
                        index = index,
                        onClick = { selectedImageIndex = index }
                    )
                }
            } else {
                // Show first (maxVisibleImages - 1) images normally
                items(maxVisibleImages - 1) { index ->
                    ImageGridItem(
                        imageRes = images[index],
                        index = index,
                        onClick = { selectedImageIndex = index }
                    )
                }


                item {
                    Box {
                        // Show the last visible image as background
                        ImageGridItem(
                            imageRes = images[maxVisibleImages - 1],
                            index = maxVisibleImages - 1,
                            onClick = { selectedImageIndex = maxVisibleImages - 1 }
                        )

                        // Overlay showing remaining count
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(90.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color.Black.copy(alpha = 0.7f))
                                .clickable {
                                    // Handle show more - you can open a full gallery view
                                    selectedImageIndex = maxVisibleImages - 1
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "+${images.size - maxVisibleImages + 1}",
                                color = Color.White,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily(Font(R.font.outfit_semibold))
                            )
                        }
                    }
                }
            }
        }

        // Full-screen image viewer
        selectedImageIndex?.let { index ->
            FullScreenImageViewerScreen(
                images = images,
                initialIndex = index,
                onDismiss = { selectedImageIndex = null }
            )
        }
    }
}

@Composable
fun ImageGridItem(
    imageRes: Int,
    index: Int,
    onClick: () -> Unit,
    showOverlay: Boolean = false
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        val size = maxWidth
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(90.dp)
                .clip(RoundedCornerShape(8.dp)),
            elevation = if (showOverlay) CardDefaults.cardElevation(0.dp)
            else CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            AsyncImage(
                model = imageRes,
                contentDescription = "Gallery Image",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ServicesContentPreview(){
    ServicesContent()
}

