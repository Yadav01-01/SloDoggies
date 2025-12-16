package com.bussiness.slodoggiesapp.ui.screens.businessprovider.profile

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.navigation.Routes
import com.bussiness.slodoggiesapp.ui.component.businessProvider.GalleryItemCardProfile
import com.bussiness.slodoggiesapp.ui.component.businessProvider.OutlineCustomButton
import com.bussiness.slodoggiesapp.ui.component.businessProvider.ProfileDetail
import com.bussiness.slodoggiesapp.ui.component.businessProvider.ScreenHeadingText
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor
import com.bussiness.slodoggiesapp.ui.theme.TextGrey
import com.bussiness.slodoggiesapp.util.LocationUtils.Companion.formatStringNumberShorthand
import com.bussiness.slodoggiesapp.viewModel.servicebusiness.BusinessServicesViewModel

@Composable
fun ProfileScreen(navController: NavHostController) {

    val viewModelDashboard: BusinessServicesViewModel = hiltViewModel()

    val uiState by viewModelDashboard.uiState.collectAsState()
    val galleryState by viewModelDashboard.uiStateGallery.collectAsState()
    var isNavigating by remember { mutableStateOf(false) }

    // First API Call
    LaunchedEffect(Unit) {
        viewModelDashboard.getBusinessDetail("")
        viewModelDashboard.galleryPostDetail("",1)   // first page
    }

    BackHandler {
        if (!isNavigating) {
            isNavigating = true
            navController.popBackStack()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        ScreenHeadingText(
            textHeading = "My Profile",
            onBackClick = {
                if (!isNavigating) {
                    isNavigating = true
                    navController.popBackStack()
                }
            },
            onSettingClick = { navController.navigate(Routes.SETTINGS_SCREEN) }
        )

        HorizontalDivider(thickness = 2.dp, color = PrimaryColor)

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(horizontal = 12.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // ---------------- PROFILE DETAILS ----------------
            item {
                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .background(Color.White, RoundedCornerShape(20.dp))
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    AsyncImage(
                        model = uiState.data?.business?.business_logo ?: "",
                        contentDescription = "image",
                        placeholder = painterResource(R.drawable.fluent_color_paw),
                        error = painterResource(R.drawable.fluent_color_paw),
                        modifier = Modifier
                            .size(95.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Column(modifier = Modifier.weight(1f)) {

                        Text(
                            text = uiState.data?.business?.business_name ?: "Unknown",
                            color = Color.Black,
                            fontFamily = FontFamily(Font(R.font.outfit_medium)),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                        )

                        Spacer(Modifier.height(5.dp))

                        Text(
                            text = uiState.data?.business?.email ?: "No Email",
                            fontFamily = FontFamily(Font(R.font.outfit_regular)),
                            fontSize = 12.sp,
                            color = PrimaryColor,
                            textDecoration = TextDecoration.Underline
                        )

                        Text(
                            text = uiState.data?.business?.bio ?: "Bio",
                            fontSize = 12.sp,
                            fontFamily = FontFamily(Font(R.font.outfit_regular)),
                            color = Color.Black,
                            maxLines = 6,
                            lineHeight = 15.sp
                        )
                    }
                }
            }

            // ---------------- POSTS / FOLLOWERS SECTION ----------------
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(IntrinsicSize.Min),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ProfileDetail(
                        label = formatStringNumberShorthand(uiState.data?.post ?: "0"),
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
                        label = formatStringNumberShorthand(uiState.data?.follower ?: "0"),
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
                        label = formatStringNumberShorthand(uiState.data?.following ?: "0"),
                        value = "Following",
                        modifier = Modifier.weight(1f),
                        onDetailClick = {
                            navController.navigate("${Routes.FOLLOWER_SCREEN}/Following")
                        }
                    )
                }
            }

            // ---------------- ADS BUTTON ----------------
            item {
                OutlineCustomButton(
                    modifier = Modifier.fillMaxWidth().height(48.dp),
                    text = "Sponsored Ads Dashboard",
                    onClick = { navController.navigate(Routes.SPONSORED_ADS_SCREEN) }
                )
            }

            // ---------------- GALLERY TITLE ----------------
            item {
                Text(
                    text = "Gallery",
                    fontFamily = FontFamily(Font(R.font.outfit_medium)),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )
            }

            item {
                HorizontalDivider(thickness = 1.dp, color = TextGrey)
            }

            // ---------------- GALLERY GRID WITH PAGINATION ----------------
            val posts = galleryState.posts
            if (posts.isEmpty()) {
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

                        TextButton(
                            onClick = { navController.navigate(Routes.PET_NEW_POST_SCREEN) }
                        ) {
                            Text(
                                text = "Create Post",
                                color = Color(0xFF258694),
                                fontFamily = FontFamily(Font(R.font.outfit_regular)),
                                fontSize = 16.sp
                            )
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }
            } else {
                item {

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(max = 800.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        userScrollEnabled = false
                    ) {

                        items(
                            count = posts.size,
                            key = { index -> posts[index].id!! } //  stable key
                        ) { index ->
                            val item = posts[index]

                            GalleryItemCardProfile(
                                item = item,
                                onClickItem = {
                                    navController.navigate(
                                        Routes.USER_POST_SCREEN + "/${item.id}/Profile"
                                    )
                                }
                            )

                            //  Pagination trigger (safe)
                            if (
                                index == posts.lastIndex &&
                                galleryState.canLoadMore &&
                                !galleryState.isLoadingMore
                            ) {
                                LaunchedEffect(index) {
                                    viewModelDashboard.galleryPostDetail(
                                        "",
                                        galleryState.currentPage + 1
                                    )
                                }
                            }
                        }

                        //  Load more loader
                        item(span = { GridItemSpan(3) }) {
                            if (galleryState.isLoadingMore) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(10.dp),
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    CircularProgressIndicator()
                                }
                            }
                        }
                    }

                    Spacer(Modifier.height(10.dp))
                }
            }

        }
    }
}


@Preview(showBackground = true)
@Composable
fun ProfileDetailScreenPreview() {
    val navController = rememberNavController()
    ProfileScreen(navController = navController)
}
