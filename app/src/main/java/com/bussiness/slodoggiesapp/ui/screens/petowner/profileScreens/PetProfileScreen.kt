package com.bussiness.slodoggiesapp.ui.screens.petowner.profileScreens

import android.widget.Toast
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
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
import com.bussiness.slodoggiesapp.data.newModel.ownerProfile.OwnerPostItem
import com.bussiness.slodoggiesapp.data.newModel.ownerProfile.Pet
import com.bussiness.slodoggiesapp.navigation.Routes
import com.bussiness.slodoggiesapp.ui.component.businessProvider.ProfileDetail
import com.bussiness.slodoggiesapp.ui.component.businessProvider.ScreenHeadingText
import com.bussiness.slodoggiesapp.ui.component.common.getVideoThumbnail
import com.bussiness.slodoggiesapp.ui.component.petOwner.dialog.FillPetInfoDialog
import com.bussiness.slodoggiesapp.ui.dialog.UpdatedDialogWithExternalClose
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor
import com.bussiness.slodoggiesapp.util.LocationUtils.Companion.isVideoFile
import com.bussiness.slodoggiesapp.viewModel.petOwner.ownerProfile.PetOwnerProfileViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun PetProfileScreen(navController: NavHostController) {

    val viewModel: PetOwnerProfileViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()
    val selectedPet = uiState.data.pets.getOrNull(uiState.selectedPet)
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.onRefresh()
    }

    LaunchedEffect(uiState.errorMessage) {
        uiState.errorMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }

    BackHandler {
        if (!navController.popBackStack(Routes.HOME_SCREEN,
                false)) {
            navController.navigate(Routes.HOME_SCREEN) {
                launchSingleTop = true
            }
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentPadding = PaddingValues(bottom = 30.dp)
    ) {
        item {
            ScreenHeadingText(
                textHeading = "My Profile",
                onBackClick = {
                    if (!navController.popBackStack(Routes.HOME_SCREEN, false)) {
                        navController.navigate(Routes.HOME_SCREEN) { launchSingleTop = true }
                    }
                },
                onSettingClick = { navController.navigate(Routes.SETTINGS_SCREEN) }
            )
            HorizontalDivider(thickness = 2.dp, color = PrimaryColor)
        }

        item {
            SwipeRefresh(
                state = rememberSwipeRefreshState(isRefreshing = uiState.isRefreshing),
                onRefresh = { }
            ) {
                Column(Modifier.padding(horizontal = 10.dp)) {

                    Text(
                        text = stringResource(R.string.My_Pets),
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.outfit_medium)),
                        color = Color.Black,
                        modifier = Modifier.padding(bottom = 10.dp)
                    )
                    Divider(color = Color(0xFF949494), thickness = 1.dp)

                    Spacer(Modifier.height(15.dp))

                    CircularProfileRow(
                        profiles = uiState.data.pets,
                        onProfileClick = { _, index -> viewModel.onPetSelected(index) },
                        onAddClick = { viewModel.petInfoDialog(true) }
                    )

                    Spacer(Modifier.height(24.dp))
                }
            }

        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp),
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

                        AsyncImage(
                            model = selectedPet?.petImage ?: "",
                            contentDescription = "Pet Avatar",
                            placeholder = painterResource(R.drawable.ic_pet_face_iconss),
                            error = painterResource(R.drawable.ic_pet_face_iconss) ,
                            modifier = Modifier.size(90.dp).clip(CircleShape),
                            contentScale = ContentScale.Crop
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
                                    text = selectedPet?.petName ?: "Pet Name",
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
                                            val petId = selectedPet?.id ?: return@clickable
                                            navController.navigate("${Routes.EDIT_PET_PROFILE_SCREEN}/$petId")
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
                                        text = selectedPet?.petBreed ?: "Breed",
                                        fontSize = 12.sp,
                                        color = PrimaryColor,
                                        fontFamily = FontFamily(Font(R.font.outfit_medium)),
                                        lineHeight = 20.sp,
                                        maxLines = 1,
                                        textAlign = TextAlign.Center
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
                                        text = "${ selectedPet?.petAge ?: "0" } Years Olds ",
                                        fontSize = 12.sp,
                                        color = Color(0xFFFF771C),
                                        fontFamily = FontFamily(Font(R.font.outfit_medium)),
                                        lineHeight = 20.sp
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = selectedPet?.petBio ?: "No Bio Available",
                                fontSize = 12.sp,
                                fontFamily = FontFamily(Font(R.font.outfit_regular)),
                                fontWeight = FontWeight.Normal,
                                lineHeight = 15.sp,
                                color = Color.Black,
                                maxLines = 3
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))
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
            Card(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp),
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

                    AsyncImage(
                        model = uiState.data.owner.image,
                        contentDescription = "Owner Avatar",
                        placeholder = painterResource(R.drawable.ic_person_icon),
                        error = painterResource(R.drawable.ic_person_icon),
                        modifier = Modifier.size(55.dp).clip(CircleShape)
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = uiState.data.owner.name,
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

                        Spacer(Modifier.height(3.dp))

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
                                text = uiState.data.owner.parentType ?: "",
                                fontSize = 10.sp,
                                color = PrimaryColor,
                                fontFamily = FontFamily(Font(R.font.outfit_medium)),
                                lineHeight = 20.sp,
                                maxLines = 1,
                                textAlign = TextAlign.Center
                            )
                        }

                        Text(
                            text = uiState.data.owner.bio,
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

                                /*val thumbnailBitmap by remember(imageUrl) {
                                    mutableStateOf(getVideoThumbnail(imageUrl))
                                }*/
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
                                        /*Image(
                                            bitmap = thumbnailBitmap!!.asImageBitmap(),
                                            contentDescription = "",
                                            modifier = Modifier.fillMaxSize(),
                                            contentScale = ContentScale.Crop
                                        )*/
                                    } else {
                                        Image(
                                            painter = painterResource(R.drawable.no_image),
                                            contentDescription = "",
                                            modifier = Modifier.fillMaxSize(),
                                            contentScale = ContentScale.Crop
                                        )
                                    }

                                    // ---- PLAY ICON (TOP-RIGHT) ----
//                                    Icon(
//                                        imageVector = Icons.Filled.PlayArrow,
//                                        contentDescription = "Play",
//                                        tint = Color.White,
//                                        modifier = Modifier
//                                            .size(28.dp)
//                                            .align(Alignment.TopEnd)
//                                            .padding(6.dp)
//                                    )
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
                viewModel.loadNextPage()
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
    if (uiState.showPetInfoDialog) {
        FillPetInfoDialog(
            data = null,
            "Add Your Pet",
            onDismiss = { viewModel.petInfoDialog(false) },
            onAddPet = {
                // Handle pet info saving
                viewModel.petInfoDialog(false)
              viewModel.petAddedSuccessDialog(true)
            },
            onCancel = { viewModel.petInfoDialog(false) },
            onProfile = true
        )
    }
    if (uiState.petAddedSuccessDialog){
        UpdatedDialogWithExternalClose(onDismiss = { viewModel.petAddedSuccessDialog(false) }, iconResId = R.drawable.ic_sucess_p, text = "Pet Added Successfully!",
            description = "Thanks for keeping things up to date!")
    }

}

@Composable
fun BeachPhotoGrid(posts: List<OwnerPostItem>, modifier: Modifier = Modifier) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = posts,
            key = { it.id ?: it.hashCode() }
        ) { data ->
            val firstImage = data.mediaPath?.firstOrNull()
            AsyncImage(
                model = data.mediaPath?.firstOrNull() ?: "",
                contentDescription = null,
                placeholder = painterResource(R.drawable.no_image_placeholder),
                error = painterResource(R.drawable.no_image_placeholder),
                modifier = Modifier
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(10.dp)),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Composable
fun CircularProfileRow(
    profiles: List<Pet>,
    modifier: Modifier = Modifier,
    onProfileClick: (Pet, Int) -> Unit = { _, _ -> },
    onAddClick: () -> Unit = {}
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {

        if (profiles.isEmpty()) {
            item {
                Box(
                    modifier = Modifier.fillParentMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Button(
                        onClick = onAddClick,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .padding(horizontal = 60.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2D8B8B)),
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
                }
            }
            return@LazyRow
        }


        itemsIndexed(
            items = profiles,
            key = { _, pet -> pet.id.toString() }
        ) { index, profile ->
            CircularProfileImage(
                profile = profile,
                onClick = { onProfileClick(profile, index) },
                modifier = Modifier.size(60.dp)
            )
        }


        item {
            AddButton(
                onClick = onAddClick,
                modifier = Modifier.size(60.dp)
            )
        }
    }
}


@Composable
fun CircularProfileImage(
    profile: Pet,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .clickable { onClick() }
            .background(Color.Gray.copy(alpha = 0.2f))
//            .border(width = 3.dp, color = PrimaryColor, shape = CircleShape)
    ) {
        AsyncImage(
            model = profile.petImage,
            placeholder = painterResource(R.drawable.ic_dog_face_icon),
            error = painterResource(R.drawable.ic_dog_face_icon),
            contentDescription = "Profile picture",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
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