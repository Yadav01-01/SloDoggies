package com.bussiness.slodoggiesapp.ui.screens.businessprovider.profile

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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.model.GalleryItem
import com.bussiness.slodoggiesapp.navigation.Routes
import com.bussiness.slodoggiesapp.ui.component.businessProvider.GalleryItemCard
import com.bussiness.slodoggiesapp.ui.component.businessProvider.OutlineCustomButton
import com.bussiness.slodoggiesapp.ui.component.businessProvider.ProfileDetail
import com.bussiness.slodoggiesapp.ui.component.businessProvider.ScreenHeadingText
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor
import com.bussiness.slodoggiesapp.ui.theme.TextGrey
import com.bussiness.slodoggiesapp.viewModel.businessProvider.ProfileViewModel

@Composable
fun ProfileScreen(navController: NavHostController) {
    val viewModel : ProfileViewModel = hiltViewModel()

    val email by viewModel.email.collectAsState()
    val description by viewModel.description.collectAsState()

    val sampleImages = listOf(
        GalleryItem(R.drawable.dog1),
        GalleryItem(R.drawable.dog2),
        GalleryItem(R.drawable.dog1),
        GalleryItem(R.drawable.dog2),
        GalleryItem(R.drawable.dog1),
        GalleryItem(R.drawable.dog2)
    )

    Column(modifier = Modifier.fillMaxSize().background(Color.White)) {

        ScreenHeadingText(textHeading = "My Profile", onBackClick = { navController.popBackStack() }, onSettingClick = { navController.navigate(Routes.SETTINGS_SCREEN)  })

        HorizontalDivider(modifier = Modifier.fillMaxWidth().height(2.dp).background(PrimaryColor))

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(horizontal = 12.dp)
                .padding(top = 10.dp, bottom = 5.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            item {
                // Profile Row
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .background(Color.White, RoundedCornerShape(20.dp))
                        .border(1.dp, Color(0xFFE5EFF2), RoundedCornerShape(20.dp))
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(R.drawable.lady_ic),
                        contentDescription = "dog image",
                        modifier = Modifier
                            .size(95.dp)
                            .clip(CircleShape)
                            .border(3.dp, Color(0xFFE5EFF2), CircleShape),
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

                            Icon(
                                painter = painterResource(R.drawable.edit_ic_p),
                                contentDescription = "icon",
                                tint = Color.Unspecified,
                                modifier = Modifier
                                    .wrapContentSize()
                                    .clickable { navController.navigate(Routes.EDIT_PROFILE_SCREEN) }
                            )
                        }

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
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    ProfileDetail(label = "120", value = "Posts", onDetailClick = { })
                    VerticalDivider(Modifier.width(2.dp).height(44.dp).background(PrimaryColor))
                    ProfileDetail(label = "27.7M", value = "Followers", onDetailClick = { navController.navigate(Routes.FOLLOWER_SCREEN)})
                    VerticalDivider(Modifier.width(2.dp).height(44.dp).background(PrimaryColor))
                    ProfileDetail(label = "219", value = "Following", onDetailClick = { navController.navigate(Routes.FOLLOWER_SCREEN)})
                }
            }

            item {
                OutlineCustomButton(modifier = Modifier.fillMaxWidth().height(48.dp), text = "Sponsored Ads Dashboard",
                    onClick = { navController.navigate(Routes.SPONSORED_ADS_SCREEN) })
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
                        GalleryItemCard(item = sampleImages[index])
                    }
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
