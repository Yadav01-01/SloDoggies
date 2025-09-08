package com.bussiness.slodoggiesapp.ui.screens.businessprovider.discover

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.ui.component.businessProvider.DetailText
import com.bussiness.slodoggiesapp.ui.component.businessProvider.FilledCustomButton
import com.bussiness.slodoggiesapp.ui.component.businessProvider.GalleryItemCard
import com.bussiness.slodoggiesapp.ui.component.businessProvider.HeadingTextWithIcon
import com.bussiness.slodoggiesapp.ui.component.businessProvider.OutlineCustomButton
import com.bussiness.slodoggiesapp.ui.component.businessProvider.PetOwnerDetail
import com.bussiness.slodoggiesapp.ui.component.businessProvider.ProfileDetail
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor
import com.bussiness.slodoggiesapp.ui.theme.TextGrey
import com.bussiness.slodoggiesapp.viewModel.common.PersonDetailViewModel

@Composable
fun PersonDetailScreen(
    navController: NavHostController,
    viewModel: PersonDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var isFollowed by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        HeadingTextWithIcon(textHeading = uiState.name, onBackClick = { navController.popBackStack() })

        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp)
                .background(PrimaryColor)
        )

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 12.dp)
                .background(Color.White)
        ) {

            // Profile Image Row
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.padding(top = 8.dp)
            ) {
                itemsIndexed(uiState.profileImages) { index, imageRes ->
                    val isSelected = index == uiState.selectedImageIndex

                    Image(
                        painter = painterResource(id = imageRes),
                        contentDescription = "Profile $index",
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape)
                            .border(
                                width = if (isSelected) 3.dp else 1.dp,
                                color = if (isSelected) PrimaryColor else Color(0xFFE5EFF2),
                                shape = CircleShape
                            )
                            .clickable { viewModel.selectProfileImage(index) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Dog Profile Card
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(Color.White, RoundedCornerShape(20.dp))
                    .border(1.dp, Color(0xFFE5EFF2), RoundedCornerShape(20.dp))
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                AsyncImage(
                    model = "painterResource(R.drawable.dog_ic)",
                    contentDescription = "dog image",
                    placeholder = painterResource(R.drawable.ic_pet_face_iconss),
                    error = painterResource(R.drawable.ic_pet_face_iconss),
                    modifier = Modifier
                        .size(90.dp)
                        .clip(CircleShape)
                        .border(3.dp, Color(0xFFE5EFF2), CircleShape),
                    contentScale = ContentScale.Fit
                )

                Spacer(modifier = Modifier.width(16.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = uiState.name,
                        color = Color.Black,
                        fontFamily = FontFamily(Font(R.font.outfit_medium)),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(Modifier.height(6.dp))

                    Row {
                        DetailText(uiState.breed, Color(0xFFE5EFF2), PrimaryColor)
                        Spacer(Modifier.width(6.dp))
                        DetailText(uiState.age , Color(0xFFFFF1E8), Color(0xFFFF771C))
                    }

                    Spacer(Modifier.height(6.dp))

                    Text(
                        text = uiState.bio,
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(R.font.outfit_regular)),
                        color = Color.Black,
                        maxLines = 6,
                        lineHeight = 15.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Stats
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ProfileDetail(label = uiState.posts, value = "Posts", onDetailClick = {})
                VerticalDivider(Modifier.width(2.dp).height(44.dp).background(PrimaryColor))
                ProfileDetail(label = uiState.followers, value = "Followers", onDetailClick = {})
                VerticalDivider(Modifier.width(2.dp).height(44.dp).background(PrimaryColor))
                ProfileDetail(label = uiState.following, value = "Following", onDetailClick = {})
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Follow & Message
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                FilledCustomButton(
                    modifier = Modifier.weight(1f).height(35.dp),
                    buttonText = if (uiState.isFollowed) "Following" else "Follow",
                    onClickFilled = { viewModel.follow() },
                    buttonTextSize = 14
                )
                OutlineCustomButton(
                    modifier = Modifier.weight(1f).height(35.dp),
                    text = "Message",
                    onClick = { viewModel.message(navController) }
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Pet Owners
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(Color.White, RoundedCornerShape(20.dp))
                    .border(1.dp, Color(0xFFE5EFF2), RoundedCornerShape(20.dp))
                    .padding(12.dp)
            ) {
                uiState.petOwners.forEachIndexed { i, owner ->
                    PetOwnerDetail(
                        name = owner.name,
                        label = owner.label,
                        imageRes = owner.imageRes,
                        description = owner.description
                    )
                    if (i < uiState.petOwners.lastIndex) {
                        Spacer(modifier = Modifier.height(8.dp))
                        HorizontalDivider(
                            modifier = Modifier.fillMaxWidth().height(1.dp).background(PrimaryColor)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }

            // Gallery
            Text(
                text = "Gallery",
                fontFamily = FontFamily(Font(R.font.outfit_medium)),
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black,
                modifier = Modifier.padding(top = 16.dp)
            )

            Spacer(Modifier.height(15.dp))
            HorizontalDivider(thickness = 1.dp, color = TextGrey)
            Spacer(Modifier.height(15.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier.height(300.dp).fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(uiState.gallery.size) { index ->
                    GalleryItemCard(item = uiState.gallery[index])
                }
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun PersonDetailScreenPreview() {
    val navController = rememberNavController()
    PersonDetailScreen(navController = navController)
}
