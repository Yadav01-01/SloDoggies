package com.bussiness.slodoggiesapp.ui.screens.commonscreens.home.content

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.data.model.businessProvider.GalleryItem
import com.bussiness.slodoggiesapp.navigation.Routes
import com.bussiness.slodoggiesapp.ui.component.businessProvider.GalleryItemCard
import com.bussiness.slodoggiesapp.ui.component.businessProvider.HeadingTextWithIcon
import com.bussiness.slodoggiesapp.ui.component.businessProvider.ProfileDetail
import com.bussiness.slodoggiesapp.ui.component.businessProvider.ScreenHeadingText
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor
import com.bussiness.slodoggiesapp.ui.theme.TextGrey
import com.bussiness.slodoggiesapp.viewModel.businessProvider.ProfileViewModel

@Composable

fun ClickedProfile(navController: NavHostController, viewModel: ProfileViewModel = hiltViewModel()) {

    val email by viewModel.email.collectAsState()
    val description by viewModel.description.collectAsState()
    var isNavigating by remember { mutableStateOf(false) }

    val sampleImages = listOf(
        com.bussiness.slodoggiesapp.data.model.businessProvider.GalleryItem(R.drawable.dog1),
        com.bussiness.slodoggiesapp.data.model.businessProvider.GalleryItem(R.drawable.dog2),
        com.bussiness.slodoggiesapp.data.model.businessProvider.GalleryItem(R.drawable.dog1),
        com.bussiness.slodoggiesapp.data.model.businessProvider.GalleryItem(R.drawable.dog2),
        com.bussiness.slodoggiesapp.data.model.businessProvider.GalleryItem(R.drawable.dog1),
        com.bussiness.slodoggiesapp.data.model.businessProvider.GalleryItem(R.drawable.dog2)
    )

    BackHandler {
        navController.navigate(Routes.HOME_SCREEN) {
            launchSingleTop = true
            popUpTo(Routes.HOME_SCREEN) {
                inclusive = false
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize().background(Color.White)) {

        HeadingTextWithIcon(textHeading = "Profile", onBackClick = {  if (!isNavigating) {
            isNavigating = true
            navController.popBackStack()
        } })

        HorizontalDivider(thickness = 2.dp, color = PrimaryColor)

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(horizontal = 12.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            item {
                Spacer(modifier = Modifier.height(10.dp))
                // Profile Row
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .background(Color.White, RoundedCornerShape(20.dp))
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AsyncImage(
                        model = "",
                        contentDescription = "image",
                        placeholder = painterResource(R.drawable.fluent_color_paw),
                        error = painterResource(R.drawable.fluent_color_paw),
                        modifier = Modifier
                            .size(95.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Fit
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Rosy Morgan",
                                color = Color.Black,
                                fontFamily = FontFamily(Font(R.font.outfit_medium)),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.weight(1f)
                            )
                        }

                        Spacer(Modifier.height(5.dp))

                        Text(
                            text = email,
                            fontFamily = FontFamily(Font(R.font.outfit_regular)),
                            fontSize = 12.sp,
                            color = PrimaryColor,
                            textDecoration = TextDecoration.Underline
                        )

                        Text(
                            text = description,
                            fontSize = 12.sp,
                            fontFamily = FontFamily(Font(R.font.outfit_regular)),
                            color = Color.Black,
                            maxLines = 6,
                            lineHeight = 15.sp
                        )
                    }
                }
            }

            item {
                // Posts/Followers/Following Row
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(IntrinsicSize.Min),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ProfileDetail(label = "120", value = "Posts", modifier = Modifier.weight(1f), onDetailClick = { })
                    VerticalDivider(modifier = Modifier.fillMaxHeight(), thickness = 1.dp, color = PrimaryColor)
                    ProfileDetail(label = "27.7M", value = "Followers", modifier = Modifier.weight(1f), onDetailClick = { navController.navigate("${Routes.FOLLOWER_SCREEN}/${"Follower"}")})
                    VerticalDivider(modifier = Modifier.fillMaxHeight(), thickness = 1.dp, color = PrimaryColor)
                    ProfileDetail(label = "219", value = "Following",  modifier = Modifier.weight(1f),onDetailClick = { navController.navigate("${Routes.FOLLOWER_SCREEN}/${"Following"}")})
                }
            }

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

            item {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 800.dp), // or use calculated height if needed
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    userScrollEnabled = false // Important to avoid nested scroll issues
                ) {
                    items(sampleImages.size) { index ->
                        GalleryItemCard(item = sampleImages[index], onClickItem = {navController.navigate(
                            Routes.USER_POST_SCREEN)})
                    }
                }
                Spacer(Modifier.height(10.dp))
            }
        }
    }
}