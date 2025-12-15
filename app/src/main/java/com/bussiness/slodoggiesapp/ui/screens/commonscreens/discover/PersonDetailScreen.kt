package com.bussiness.slodoggiesapp.ui.screens.commonscreens.discover

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.navigation.Routes
import com.bussiness.slodoggiesapp.ui.component.businessProvider.DetailText
import com.bussiness.slodoggiesapp.ui.component.businessProvider.FilledCustomButton
import com.bussiness.slodoggiesapp.ui.component.businessProvider.GalleryItemCard
import com.bussiness.slodoggiesapp.ui.component.businessProvider.HeadingTextWithIcon
import com.bussiness.slodoggiesapp.ui.component.businessProvider.OutlineCustomButton
import com.bussiness.slodoggiesapp.ui.component.businessProvider.PetOwnerDetail
import com.bussiness.slodoggiesapp.ui.component.businessProvider.ProfileDetail
import com.bussiness.slodoggiesapp.ui.component.businessProvider.ScreenHeadingText
import com.bussiness.slodoggiesapp.ui.screens.petowner.profileScreens.CircularProfileRow
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor
import com.bussiness.slodoggiesapp.ui.theme.TextGrey
import com.bussiness.slodoggiesapp.util.LocationUtils.Companion.isVideoFile
import com.bussiness.slodoggiesapp.viewModel.common.PersonDetailViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun PersonDetailScreen(
    navController: NavHostController,
    userId : String,
    viewModel: PersonDetailViewModel = hiltViewModel()
) {

    LaunchedEffect(userId) {
        viewModel.profileDetail(userId)
        viewModel.galleryPostDetail(1, userId)
    }

    val uiState by viewModel.uiState.collectAsState()
    var isNavigating by remember { mutableStateOf(false) }

    val profile = uiState.data
    val post = uiState.posts

    BackHandler {
        if (!isNavigating) {
            isNavigating = true
            navController.popBackStack()
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentPadding = PaddingValues(bottom = 30.dp)
    ) {
        item {
            HeadingTextWithIcon(textHeading = "jimmi", onBackClick = {  if (!isNavigating) {
                isNavigating = true
                navController.popBackStack()
            } })

            HorizontalDivider(thickness = 2.dp, color = PrimaryColor)

        }

        item {
            SwipeRefresh(
                state = rememberSwipeRefreshState(isRefreshing = uiState.isRefreshing),
                onRefresh = { }
            ) {
                Column(Modifier.padding(horizontal = 10.dp)) {

                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.padding(top = 8.dp)
                    ) {
                        itemsIndexed(profile.pets) { index, data ->
                            val isSelected = index == uiState.selectedImageIndex

                            AsyncImage(
                                model = data.petImage,
                                placeholder = painterResource(R.drawable.ic_pet_face_iconss),
                                error = painterResource(R.drawable.ic_pet_face_iconss),
                                contentDescription = "Profile $index",
                                contentScale = ContentScale.Crop,
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
                    Spacer(Modifier.height(20.dp))
                }
            }

        }

        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .background(Color.White, RoundedCornerShape(20.dp))
                        .border(1.dp, Color(0xFFE5EFF2), RoundedCornerShape(20.dp))
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    val selectedPet = profile.pets.getOrNull(uiState.selectedImageIndex)

                    AsyncImage(
                        model = selectedPet?.petImage,
                        contentDescription = "dog image",
                        placeholder = painterResource(R.drawable.ic_pet_face_iconss),
                        error = painterResource(R.drawable.ic_pet_face_iconss),
                        modifier = Modifier
                            .size(90.dp)
                            .clip(CircleShape)
                            .border(3.dp, Color(0xFFE5EFF2), CircleShape),
                        contentScale = ContentScale.Crop
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = selectedPet?.petName ?: "",
                            color = Color.Black,
                            fontFamily = FontFamily(Font(R.font.outfit_medium)),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold
                        )

                        Spacer(Modifier.height(6.dp))

                        Row {
                            DetailText(selectedPet?.petBreed ?: "Breed", Color(0xFFE5EFF2), PrimaryColor)
                            Spacer(Modifier.width(6.dp))
                            DetailText(
                                selectedPet?.petAge?.let { "$it Year Old" } ?: "",
                                Color(0xFFFFF1E8),
                                Color(0xFFFF771C)
                            )
                        }

                        Spacer(Modifier.height(6.dp))

                        Text(
                            text = selectedPet?.petBio ?: "No Bio Available",
                            fontSize = 12.sp,
                            fontFamily = FontFamily(Font(R.font.outfit_regular)),
                            color = Color.Black,
                            maxLines = 6,
                            lineHeight = 15.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))
            }
        }


        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ProfileDetail(
                    label = uiState.data.postCount.toString(),
                    value = "Posts",
                    modifier = Modifier.weight(1f),
                    onDetailClick = {}
                )

                VerticalDivider(
                    modifier = Modifier.fillMaxHeight(),
                    thickness = 1.dp,
                    color = PrimaryColor
                )

                ProfileDetail(
                    label = uiState.data.followerCount.toString(),
                    value = "Followers",
                    modifier = Modifier.weight(1f),
                    onDetailClick = {
                        navController.navigate("${Routes.FOLLOWER_SCREEN}/Follower")
                    }
                )

                VerticalDivider(
                    modifier = Modifier.fillMaxHeight(),
                    thickness = 1.dp,
                    color = PrimaryColor
                )

                ProfileDetail(
                    label = uiState.data.followingCount.toString(),
                    value = "Following",
                    modifier = Modifier.weight(1f),
                    onDetailClick = {
                        navController.navigate("${Routes.FOLLOWER_SCREEN}/Following")
                    }
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
        }

        item {
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

        }

        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .background(Color.White, RoundedCornerShape(20.dp))
                        .border(1.dp, Color(0xFFE5EFF2), RoundedCornerShape(20.dp))
                        .padding(6.dp)
                ) {
                    PetOwnerDetail(
                        name = profile.owner.name,
                        imageRes = profile.owner.image,
                        description = profile.owner.bio
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))
            }
        }


        // Gallery Section
        item {
            Text(
                text = "Gallery",
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.outfit_regular)),
                modifier = Modifier.padding(horizontal = 16.dp),
                color = Color.Black
            )
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                color = Color(0xFF949494)
            )
        }
        // No Posts State
        if (uiState.posts.isEmpty()){
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_pet_post_icon),
                        contentDescription = "Pet Avatar",
                        modifier = Modifier
                            .width(80.dp)
                            .height(90.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "No Post Yet",
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.outfit_regular)),
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.height(2.dp))
                }
            }

        }
        else{
            items(uiState.posts.chunked(3)) { rowItems ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp, vertical = 6.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    rowItems.forEach { post ->
                        val imageUrl = post.mediaPath?.firstOrNull()?.url ?: ""

                        if (imageUrl.isVideoFile()) {
                            val  thumbnailBitmap = post.mediaPath?.firstOrNull()?.thumbnailUrl ?: ""
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .aspectRatio(1f)
                                    .clip(RoundedCornerShape(10.dp))
                                    .clickable { navController.navigate(Routes.USER_POST_SCREEN) }
                            ) {

                                // ---- VIDEO THUMBNAIL ----
                                if (thumbnailBitmap != null) {
                                    AsyncImage(
                                        model = thumbnailBitmap,
                                        contentDescription = null,
                                        modifier = Modifier.fillMaxSize(),
                                        placeholder = painterResource(R.drawable.no_image),
                                        error = painterResource(R.drawable.no_image),
                                        contentScale = ContentScale.Crop
                                    )
                                } else {
                                    Image(
                                        painter = painterResource(R.drawable.no_image),
                                        contentDescription = "",
                                        modifier = Modifier.fillMaxSize(),
                                        contentScale = ContentScale.Crop
                                    )
                                }

                                Box(
                                    Modifier
                                        .fillMaxSize()
                                        .background(Color.Black.copy(.35f)),
                                    Alignment.Center
                                ) {
                                    IconButton(
                                        onClick = {
                                            navController.navigate(Routes.USER_POST_SCREEN)
                                        },
                                        modifier = Modifier
                                            .size(60.dp)
                                            .background(Color.White.copy(.9f), CircleShape)
                                    ) {
                                        Icon(
                                            Icons.Default.PlayArrow,
                                            contentDescription = "Play Video",
                                            tint = Color(0xFF0A3D62),
                                            modifier = Modifier.size(36.dp)
                                        )
                                    }
                                }
                            }

                        } else {

                            // ---- NORMAL IMAGE ----
                            AsyncImage(
                                model = imageUrl,
                                placeholder = painterResource(R.drawable.no_image),
                                error = painterResource(R.drawable.no_image),
                                contentDescription = "",
                                modifier = Modifier
                                    .weight(1f)
                                    .aspectRatio(1f)
                                    .clip(RoundedCornerShape(10.dp))
                                    .clickable {
                                        navController.navigate(Routes.USER_POST_SCREEN)
                                    },
                                contentScale = ContentScale.Crop
                            )
                        }
                    }


                    // Fill empty spaces in last row
                    repeat(3 - rowItems.size) {
                        Spacer(
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f)
                        )
                    }
                }
            }

        }
        item {
            // Trigger load more when reached bottom
            LaunchedEffect(Unit) {
                viewModel.loadNextPage(userId)
            }

            if (uiState.isLoadingMore) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator(color = PrimaryColor, modifier = Modifier.size(20.dp))
                }
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun PersonDetailScreenPreview() {
    val navController = rememberNavController()
    PersonDetailScreen(navController = navController,"1")
}
