package com.bussiness.slodoggiesapp.ui.screens.petowner.profileScreens

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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.model.petOwner.ProfileItem
import com.bussiness.slodoggiesapp.navigation.Routes
import com.bussiness.slodoggiesapp.ui.component.businessProvider.ProfileDetail
import com.bussiness.slodoggiesapp.ui.component.businessProvider.ScreenHeadingText
import com.bussiness.slodoggiesapp.ui.component.petOwner.dialog.FillPetInfoDialog
import com.bussiness.slodoggiesapp.ui.dialog.UpdatedDialogWithExternalClose
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor

// Sample photo URLs - replace with your actual image URLs
private val photos = listOf(
    R.drawable.dummy_person_image3, // Woman with dog on beach
    R.drawable.dummy_person_image3, // Man feeding dog on beach
    R.drawable.dummy_person_image3, // Person petting dog on beach
    R.drawable.dummy_person_image3, // Boy with dog in water
    R.drawable.dummy_person_image3, // Dog jumping on beach
    R.drawable.dummy_person_image3, // Black dog with people in background
    R.drawable.dummy_person_image3, // Boy with dog in water (duplicate)
    R.drawable.dummy_person_image3,
    R.drawable.dummy_person_image3, // Dog jumping on beach
    R.drawable.dummy_person_image3, // Black dog with people in background
    R.drawable.dummy_person_image3, // Boy with dog in water (duplicate)
    R.drawable.dummy_person_image3,
    R.drawable.dummy_person_image3, // Dog jumping on beach
    R.drawable.dummy_person_image3, // Black dog with people in background
    R.drawable.dummy_person_image3, // Boy with dog in water (duplicate)
    R.drawable.dummy_person_image3,
    R.drawable.dummy_person_image3, // Dog jumping on beach
    R.drawable.dummy_person_image3, // Black dog with people in background
    R.drawable.dummy_person_image3, // Boy with dog in water (duplicate)
    R.drawable.dummy_person_image3  // Black dog with people (duplicate)
)

@Composable
fun PetProfileScreen(navController: NavHostController) {
    var showPetInfoDialog by remember { mutableStateOf(false) }
    var petAddedSuccessDialog by remember { mutableStateOf(false) }
    var showAddButton by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        showAddButton = false
    }

    Column(modifier = Modifier.fillMaxSize().background(Color.White)) {

        BackHandler {
            if (!navController.popBackStack(Routes.HOME_SCREEN,
                    false)) {
                navController.navigate(Routes.HOME_SCREEN) {
                    launchSingleTop = true
                }
            }
        }

        ScreenHeadingText(textHeading = "My Profile",
            onBackClick = {    if (!navController.popBackStack(Routes.HOME_SCREEN, false)) {
                navController.navigate(Routes.HOME_SCREEN) {
                    launchSingleTop = true
                }
            } },
            onSettingClick = { navController.navigate(Routes.SETTINGS_SCREEN)  })

        HorizontalDivider(thickness = 2.dp, color = PrimaryColor)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // Header
            Text(
                text = "My Pets",
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.outfit_medium)),
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(bottom = 10.dp)
            )

            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp),
                color = Color(0xFF949494),
                thickness = 1.dp
            )

            // Add Pet Button
            Spacer(Modifier.height(15.dp))

            if (showAddButton) {
                Button(
                    onClick = { showPetInfoDialog = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .padding(horizontal = 60.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF2D8B8B)
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add",
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Add Pet",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            } else {
                val sampleProfiles = listOf(
                    ProfileItem(1, R.drawable.dog_ic),
                    ProfileItem(2, R.drawable.dog1),
                    ProfileItem(3, R.drawable.dog3),
                    ProfileItem(6, isAddButton = true)
                )

                CircularProfileRow(
                    profiles = sampleProfiles,
                    onProfileClick = { profile ->
                        // Handle profile click
                        println("Clicked profile ${profile.id}")
                    },
                    onAddClick = {
                        // Handle add button click
                        showPetInfoDialog = true
                    }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Pet Profile Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    // Pet Avatar and Basic Info
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {


//                        AsyncImage(
//                            model = R.drawable.ic_pet_face_iconss,
//                            contentDescription = "Pet Avatar",
//                            placeholder = painterResource(R.drawable.ic_pet_face_iconss),
//                            error = painterResource(R.drawable.ic_pet_face_iconss) ,
//                            modifier = Modifier.size(90.dp)
//                        )
                        Image(
                            painter = painterResource(id = R.drawable.dog_ic),
                            contentDescription = "Pet Avatar",
                            modifier = Modifier.size(90.dp)
                        )

                        Spacer(modifier = Modifier.width(16.dp))

                        // Pet Info
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(bottom = 4.dp)
                            ) {
                                Text(
                                    text = "Jimmi",
                                    fontSize = 16.sp,
                                    fontFamily = FontFamily(Font(R.font.outfit_medium)),
                                    fontWeight = FontWeight.Medium,
                                    color = Color.Black
                                )
                                Spacer(modifier = Modifier.weight(1f))

                                Image(
                                    painter = painterResource(id = R.drawable.ic_edit_icon),
                                    contentDescription = "Edit",
                                    modifier = Modifier
                                        .size(18.dp)
                                        .clickable {
                                            navController.navigate(Routes.EDIT_PET_PROFILE_SCREEN)
                                        }
                                )
                            }

                            Row(
                                modifier = Modifier.fillMaxWidth()
                            ) {

                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .background(
                                            color = Color(0xFFE5EFF2),
                                            shape = RoundedCornerShape(50)
                                        )
                                        .padding(horizontal = 0.dp, vertical = 1.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "Brown Breed",
                                        fontSize = 12.sp,
                                        color = PrimaryColor,
                                        fontFamily = FontFamily(Font(R.font.outfit_medium))
                                    )
                                }
                                Spacer(modifier = Modifier.width(14.dp))
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .background(
                                            color = Color(0xFFFFF1E8),
                                            shape = RoundedCornerShape(50)
                                        )
                                        .padding(horizontal = 0.dp, vertical = 1.dp),
                                    contentAlignment = Alignment.Center // This works in Box
                                ) {
                                    Text(
                                        text = "3 Years Olds",
                                        fontSize = 12.sp,
                                        color = Color(0xFFFF771C),
                                        fontFamily = FontFamily(Font(R.font.outfit_medium))
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed ",
                                fontSize = 12.sp,
                                fontFamily = FontFamily(Font(R.font.outfit_regular)),
                                fontWeight = FontWeight.Normal,
                                lineHeight = 15.sp,
                                color = Color.Black
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ProfileDetail(
                    label = "20",
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
                    label = "27k",
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
                    label = "219",
                    value = "Following",
                    modifier = Modifier.weight(1f),
                    onDetailClick = {
                        navController.navigate("${Routes.FOLLOWER_SCREEN}/Following")
                    }
                )
            }


            Spacer(modifier = Modifier.height(24.dp))
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                // Owner Profile Section
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp, vertical = 5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

//                    AsyncImage(
//                        model = R.drawable.ic_person_icon1,
//                        contentDescription = "Owner Avatar",
//                        placeholder = painterResource(R.drawable.dummy_person_image1),
//                        error = painterResource(R.drawable.ic_person_icon1),
//                        modifier = Modifier.size(55.dp)
//                    )
                    Image(
                        painter = painterResource(id = R.drawable.dummy_person_image1),
                        contentDescription = "Pet Avatar",
                        modifier = Modifier.size(55.dp)
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Lydia Vaccaro",
                                fontSize = 12.sp,
                                fontFamily = FontFamily(Font(R.font.outfit_medium)),
                                fontWeight = FontWeight.Medium,
                                color = Color.Black
                            )
                            Spacer(modifier = Modifier.weight(1f))

                            Image(
                                painter = painterResource(id = R.drawable.ic_edit_icon),
                                contentDescription = "Edit",
                                modifier = Modifier
                                    .size(18.dp)
                                    .clickable {
                                        navController.navigate(Routes.EDIT_PROFILE_SCREEN_PET)
                                    }
                            )
                        }

                        Box(
                            modifier = Modifier
                                .width(70.dp)
                                .background(
                                    color = Color(0xFFE5EFF2),
                                    shape = RoundedCornerShape(50)
                                )
                                .padding(horizontal = 0.dp, vertical = 1.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Pet Mom",
                                fontSize = 10.sp,
                                fontFamily = FontFamily(Font(R.font.outfit_medium)),
                                fontWeight = FontWeight.Medium,
                                lineHeight = 15.sp,
                                color = PrimaryColor
                            )
                        }
                        Spacer(Modifier.height(3.dp))

                        Text(
                            text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed ",
                            fontSize = 12.sp,
                            fontFamily = FontFamily(Font(R.font.outfit_regular)),
                            fontWeight = FontWeight.Normal,
                            color = Color.Black,
                            lineHeight = 15.sp
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(32.dp))

            // Gallery Section
            Text(
                text = "Gallery",
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.outfit_regular)),
                modifier = Modifier.padding(bottom = 10.dp)
            )
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp),
                color = Color(0xFF949494),
                thickness = 1.dp
            )

            // No Posts State
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Paw Print Icon


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
    }
    if (showPetInfoDialog) {
        FillPetInfoDialog(
            "Add Your Pet",
            onDismiss = { showPetInfoDialog = false },
            onAddPet = {
                // Handle pet info saving
                showPetInfoDialog = false
                petAddedSuccessDialog = true
            },
            onCancel = { showPetInfoDialog = false },
            onProfile = true
        )
    }
    if (petAddedSuccessDialog){
        UpdatedDialogWithExternalClose(onDismiss = { petAddedSuccessDialog = false }, iconResId = R.drawable.ic_sucess_p, text = "Pet Added Successfully!",
            description = "Thanks for keeping things up to date!")
    }

}

@Composable
fun BeachPhotoGrid() {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(photos) { photo ->
            AsyncImage(
                model = photo,
                contentDescription = "Beach photo",
                modifier = Modifier
                    .height(130.dp)
                    .width(80.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )

        }
    }
}


@Composable
fun StatItem(number: String, label: String, navController: NavController) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable {
            if (label == "Followers") {
                navController.navigate(Routes.FOLLOWER_SCREEN)
            } else if (label == "Followers") {
                navController.navigate(Routes.FOLLOWER_SCREEN)
            }
        }
    ) {
        Text(
            text = number,
            fontSize = 16.sp,
            fontFamily = FontFamily(Font(R.font.outfit_medium)),
            color = Color(0xFF258694)
        )
        Text(
            text = label,
            fontSize = 15.sp,
            fontFamily = FontFamily(Font(R.font.outfit_medium)),
            color = Color.Black
        )
    }
}


@Composable
fun CircularProfileRow(
    profiles: List<ProfileItem>,
    modifier: Modifier = Modifier,
    onProfileClick: (ProfileItem) -> Unit = {},
    onAddClick: () -> Unit = {}
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(profiles) { profile ->
            if (profile.isAddButton) {
                AddButton(
                    onClick = onAddClick,
                    modifier = Modifier.size(60.dp)
                )
            } else {
                CircularProfileImage(
                    profile = profile,
                    onClick = { onProfileClick(profile) },
                    modifier = Modifier.size(60.dp)
                )
            }
        }
    }
}

@Composable
fun CircularProfileImage(
    profile: ProfileItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .clickable { onClick() }
            .background(Color.Gray.copy(alpha = 0.2f))
    ) {
        profile.imageRes?.let { imageRes ->
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = "Profile picture",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Composable
fun AddButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .border(
                width = 1.dp,
                color = Color(0xFF258694),
                shape = CircleShape
            )
            .clickable { onClick() }
            .background(Color.Transparent),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Add profile",
            tint = Color(0xFF258694),
            modifier = Modifier.size(24.dp)
        )
    }
}


@Preview(showBackground = true)
@Composable
fun PetProfileScreenPreview() {
    val navController = rememberNavController()
    MaterialTheme {
        PetProfileScreen(navController)
    }
}