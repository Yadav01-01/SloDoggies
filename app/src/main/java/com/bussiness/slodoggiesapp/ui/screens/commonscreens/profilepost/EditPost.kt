package com.bussiness.slodoggiesapp.ui.screens.commonscreens.profilepost

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.data.newModel.home.PostItem
import com.bussiness.slodoggiesapp.data.newModel.home.PostMediaResponse
import com.bussiness.slodoggiesapp.navigation.Routes
import com.bussiness.slodoggiesapp.ui.component.businessProvider.ParticipantTextWithIcon
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor
import com.bussiness.slodoggiesapp.viewModel.common.EditPostViewModel
import com.google.gson.Gson
import kotlinx.coroutines.delay

@Composable
fun EditPostScreen(
    navController: NavHostController,
    viewModel: EditPostViewModel = hiltViewModel()
) {
    val uSitate by viewModel.uiState.collectAsState()
    var isNavigating by remember { mutableStateOf(false) }

    var screen by remember { mutableStateOf("") }



    val context = LocalContext.current
    val postJson  = navController.previousBackStackEntry
        ?.savedStateHandle
        ?.get<String>("Post")
    val type = navController.previousBackStackEntry
        ?.savedStateHandle
        ?.get<String>("PostType")
    val screentype = navController.previousBackStackEntry
        ?.savedStateHandle
        ?.get<String>("Screen")
    screen = screentype?:""
    val gson = Gson()
    val postData: PostItem? = when (type) {
        "NormalPost" -> gson.fromJson(postJson, PostItem.NormalPost::class.java)
        "CommunityPost" -> gson.fromJson(postJson, PostItem.CommunityPost::class.java)
        "SponsoredPost" -> gson.fromJson(postJson, PostItem.SponsoredPost::class.java)
        else -> null
    }

    postData?.let { viewModel.setPost(it, type ?: "") }

    Log.d("*******","${postData.toString()} Hello")

    // Handle physical back button safely
    BackHandler(enabled = !isNavigating) {
        if (!isNavigating) {
            isNavigating = true
            if (screen.equals("Home")){
//                navController.navigate(Routes.USER_POST_SCREEN) {
//                    popUpTo(Routes.HOME_SCREEN) { inclusive = true }
//                    launchSingleTop = true   // IMPORTANT: Prevent recreation
//                }
                navController.popBackStack()

            }else{
                navController.navigate(Routes.USER_POST_SCREEN) {
                popUpTo(Routes.EDIT_POST_SCREEN) { inclusive = true }
                launchSingleTop = true
            }
            }
        }
    }

    Scaffold(
        topBar = {
            Column(Modifier.background(Color.White)) {
                ParticipantTextWithIcon(
                    textHeading = stringResource(R.string.edit_info),
                    onBackClick = {
                        if (!isNavigating) {
                            isNavigating = true
                            if (screen.equals("Home")){
//                                navController.navigate(Routes.USER_POST_SCREEN) {
//                                    popUpTo(Routes.HOME_SCREEN) { inclusive = true }
//                                    launchSingleTop = true   // IMPORTANT: Prevent recreation
//                                }
                                navController.popBackStack()

                            }else{
                                navController.navigate(Routes.USER_POST_SCREEN) {
                                    popUpTo(Routes.EDIT_POST_SCREEN) { inclusive = true }
                                    launchSingleTop = true
                                }
                            }
                        }
                    },
                    onClick = {
                        viewModel.editPost(postId = uSitate.postId, postDescription =
                        uSitate.description, onSuccess = {
                            if (!isNavigating) {
                                isNavigating = true
                                navController.popBackStack()
//                            navController.navigate(Routes.USER_POST_SCREEN) {
//                                popUpTo(Routes.EDIT_POST_SCREEN) { inclusive = true }
//                                launchSingleTop = true
//                            }
                            }
                        },
                            onError = { msg -> Toast.makeText(context, msg, Toast.LENGTH_SHORT).show() },
                            )

                    },
                     selected = true
                )
                HorizontalDivider(thickness = 2.dp, color = PrimaryColor)
            }
        },
        content = { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFCEE1E6))
                    .padding(paddingValues)
                    .imePadding(),
                contentPadding = PaddingValues(bottom = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    postData?.let { post ->
                        when (post) {
                            is PostItem.NormalPost -> {
                                val displayImage = post.mediaList.firstOrNull()?.getDisplayImage()
                                PostCard(
                                    profileImageUrl = post.media?.parentImageUrl ?: "",
                                    userName = post.userName,
                                    timeAgo = post.time,
                                    role = post.type,
                                    postImageUrl = displayImage?:"",
                                    onMenuClick = {}
                                )
                            }

                            is PostItem.CommunityPost -> {
                                val displayImage = post.mediaList.firstOrNull()?.getDisplayImage()
                                PostCard(
                                    profileImageUrl = post.userImage?:"",
                                    userName = post.userName,
                                    timeAgo = post.time,
                                    role = "Community",
                                    postImageUrl = displayImage?:"",
                                    onMenuClick = {}
                                )
                            }

                            is PostItem.SponsoredPost -> {
                                val displayImage = post.mediaList.firstOrNull()?.getDisplayImage()
                                PostCard(
                                    profileImageUrl = post.media?.parentImageUrl?:"",
                                    userName = post.userName,
                                    timeAgo = post.time,
                                    role = "Sponsored",
                                    postImageUrl = displayImage?:"",
                                    onMenuClick = {}
                                )
                            }
                        }
                    }

//                    PostCard(
//                        profileImageUrl = post.media?.profileImageUrl,
//                        userName = post.userName,
//                        timeAgo = post.time,
//                        role = post.type,
//                        postImageUrl = post.mediaList.firstOrNull()?.url,
//                        onMenuClick = {}
//                    )
                }

                item {
                    postData?.let { post ->
                        when (post) {
                            is PostItem.NormalPost -> {
                                EditDescription(
                                    description = uSitate.description,
                                    onDescriptionChange = { viewModel.updateDescription(it) }
                                )
                            }

                            is PostItem.CommunityPost -> {
                                EditDescription(
                                    description =uSitate.description,
                                    onDescriptionChange = { viewModel.updateDescription(it) }
                                )
                            }

                            is PostItem.SponsoredPost -> {
                                EditDescription(
                                    description = uSitate.description,
                                    onDescriptionChange = { viewModel.updateDescription(it) }
                                )
                            }
                        }
                    }

                }
            }
        }
    )
}

fun PostMediaResponse.getDisplayImage(): String? {
    return when (type?.lowercase()) {
        "video" -> thumbnailUrl   // video → thumbnail
        "image" -> mediaUrl    // image → mediaUrl
        else -> mediaUrl ?: thumbnailUrl
    }
}


@Composable
fun PostCard(
    profileImageUrl: String,
    userName: String,
    timeAgo: String,
    role: String,
    postImageUrl: String,
    modifier: Modifier = Modifier,
    onMenuClick: () -> Unit = {}
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(20.dp),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
        ) {
            // Header Row
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = profileImageUrl,
                    contentDescription = "Profile picture",
                    placeholder = painterResource(R.drawable.new_dog_ic),
                    error = painterResource(R.drawable.new_dog_ic),
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.width(8.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = userName,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Medium,
                            fontFamily = FontFamily(Font(R.font.outfit_medium)),
                            fontSize = 12.sp,
                            lineHeight = 20.sp,
                            color = Color.Black
                        )
                    )
                    Text(
                        text = " $timeAgo",
                        fontSize = 12.sp,
                        color = Color(0xFF949494),
                        modifier = Modifier,
                        lineHeight = 15.sp,
                        fontFamily = FontFamily(Font(R.font.outfit_regular)),
                    )
                }
            }

            // Post Image
            AsyncImage(
                model = postImageUrl,
                contentDescription = "Post image",
                placeholder = painterResource(R.drawable.no_image),
                error = painterResource(R.drawable.no_image),
                modifier = Modifier
                    .fillMaxWidth()
                    .size(350.dp),
                contentScale = ContentScale.Crop
            )
//            Image(
//                painter = painterResource(R.drawable.new_dog_ic),
//                contentDescription = "Post image",
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(350.dp),
//                contentScale = ContentScale.Crop
//            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun EditDescription(
    description: String,
    onDescriptionChange: (String) -> Unit
) {
    val bringIntoViewRequester = remember { BringIntoViewRequester() }
    val focusRequester = remember { FocusRequester() }
    var isFocused by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
            .offset(y = -(45).dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        LaunchedEffect(isFocused) {
            if (isFocused) {
                delay(100)
                bringIntoViewRequester.bringIntoView()
            }
        }
        BasicTextField(
            value = description,
            onValueChange = onDescriptionChange,
            textStyle = TextStyle(
                fontWeight = FontWeight.Normal,
                fontFamily = FontFamily(Font(R.font.inter_regular)),
                fontSize = 12.sp,
                lineHeight = 17.sp,
                color = Color.Black
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
                .focusRequester(focusRequester)
                .onFocusChanged { state ->
                    isFocused = state.isFocused
                },

            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (description.isEmpty()) {
                        Text(
                            text = "Write a description...",
                            style = TextStyle(
                                fontFamily = FontFamily(Font(R.font.inter_regular)),
                                fontSize = 12.sp,
                                lineHeight = 17.sp,
                                color = Color.Gray
                            )
                        )
                    }
                    innerTextField()
                }
            }
        )
    }
}
