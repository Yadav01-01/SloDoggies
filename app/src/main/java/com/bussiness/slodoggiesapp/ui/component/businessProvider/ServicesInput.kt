package com.bussiness.slodoggiesapp.ui.component.businessProvider

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.model.ServicePackage
import com.bussiness.slodoggiesapp.ui.screens.petowner.serviceProviderDetailsScreen.EnhancedExpandableInfoSpinner
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor

@Composable
fun PetSitterCard(
    name: String,
    rating: Float,
    ratingCount: Int,
    providerName: String,
    about: String,
    phone: String,
    website: String,
    address: String,
    modifier: Modifier = Modifier,
    onEditClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Top Row: Image, Name, Rating, Edit Icon
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .width(100.dp)
                        .height(86.dp)
                        .background(Color.LightGray, RoundedCornerShape(10.dp))
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = name,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily(Font(R.font.outfit_semibold)),
                                fontSize = 14.sp,
                                color = Color.Black
                            )
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            painter = painterResource(R.drawable.ic_verified),
                            contentDescription = null,
                            tint = Color.Unspecified
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Icon(
                            painter = painterResource(id = R.drawable.edit_ic_p),
                            contentDescription = "Edit",
                            tint = Color.Unspecified,
                            modifier = Modifier
                                .clickable { onEditClick() }
                                .wrapContentSize()
                        )
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(R.drawable.star_ic),
                            contentDescription = null,
                            tint = Color.Unspecified
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "${rating}/5 ($ratingCount Ratings)",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily(Font(R.font.outfit_regular)),
                                fontSize = 12.sp,
                                color = Color.Black
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(R.drawable.lady_ic),
                            contentDescription = null,
                            modifier = Modifier.size(30.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = providerName,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily(Font(R.font.outfit_medium)),
                                fontSize = 14.sp,
                                color = Color(0xFF9A9A9A)
                            )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            EnhancedExpandableInfoSpinner()
        }
    }
}




@Composable
fun TypeButton(
    modifier: Modifier = Modifier,
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Button (
        onClick = onClick,
        modifier = modifier
            .wrapContentSize()
            .fillMaxWidth()
            .clip(RoundedCornerShape(6.dp)),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) PrimaryColor else Color(0xFFE5EFF2),
            contentColor = if (isSelected) Color.White else Color.Black
        ),
        shape = RoundedCornerShape(10.dp)
    ) {
        Text(
            text = text,
            fontSize = 12.sp,
            fontFamily = if (isSelected)FontFamily(Font(R.font.outfit_medium)) else FontFamily(Font(R.font.outfit_regular)),
            color = if (isSelected) Color.White else Color.Black
        )
    }
}

@Composable
fun ServicePackageCard(
    data: ServicePackage,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Column(
        modifier = Modifier
            .background(Color.White, RoundedCornerShape(6.dp))
            .border(1.dp, Color(0xFFE5EFF2), RoundedCornerShape(6.dp))
            .padding(8.dp)
    ) {
        // Title + Action Icons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Row {
                Icon(
                    painter = painterResource(R.drawable.ic_paw_like_filled_icon),
                    contentDescription = null,
                    modifier = Modifier.wrapContentSize(),
                    tint = Color.Unspecified
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = data.title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily(Font(R.font.outfit_medium)),
                        fontSize = 14.sp,
                        color = Color.Black
                    )
                )
            }
            Row {
                Icon(
                    painter = painterResource(R.drawable.edit_ic_p),
                    contentDescription = null,
                    modifier = Modifier.clickable { onEdit() },
                )
                Spacer(modifier = Modifier.width(15.dp))
                Icon(
                    painter = painterResource(R.drawable.ic_delete_icon),
                    contentDescription = null,
                    tint = Color(0xFFDD0B00),
                    modifier = Modifier.clickable { onDelete() },
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Description
        Text(
            text = data.description,
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Normal,
                fontFamily = FontFamily(Font(R.font.outfit_regular)),
                fontSize = 12.sp,
                lineHeight = 17.sp,
                color = Color.Black
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Amount Section
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Amount",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Medium,
                    fontFamily = FontFamily(Font(R.font.outfit_medium)),
                    fontSize = 14.sp,
                    color = Color.Black
                )
            )
            Text(
                text = data.amount,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Medium,
                    fontFamily = FontFamily(Font(R.font.outfit_medium)),
                    fontSize = 14.sp,
                    color = Color.Black
                )
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Photos label
        Text(
            text = "Photos",
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Medium,
                fontFamily = FontFamily(Font(R.font.outfit_medium)),
                fontSize = 14.sp,
                color = Color.Black
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Photos Row
        val maxDisplay = 5
        val photosToShow = data.photos.take(maxDisplay)
        val remaining = data.photos.size - maxDisplay

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier
                .fillMaxWidth()
                .height(90.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            userScrollEnabled = true // Disable scrolling since it's 1 row
        ) {
            items(photosToShow) { photo ->
                Image(
                    painter = rememberAsyncImagePainter(photo),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(10.dp))
                )
            }

            if (remaining > 0) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color.LightGray),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "+$remaining",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.Medium,
                                fontSize = 20.sp,
                                fontFamily = FontFamily(Font(R.font.outfit_medium)),
                                color = Color.White
                            )
                        )
                    }
                }
            }
        }


    }
}



@Preview(showBackground = true)
@Composable
fun CardPreview(){
    PetSitterCard(
        name = "Pet Sitter Name",
        rating = 4.5f,
        ratingCount = 100,
        providerName = "Provider Name",
        about = "About the sitter",
        phone = "123-456-7890",
        website = "www.example.com",
        address = "123 Main St, City, Country",
        onEditClick = {}
    )

}