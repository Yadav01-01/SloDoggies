package com.bussiness.slodoggiesapp.ui.screens.commonscreens.home
import androidx.compose.runtime.key
import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.data.model.main.UserType
import com.bussiness.slodoggiesapp.data.newModel.home.PostItem
import com.bussiness.slodoggiesapp.navigation.Routes
import com.bussiness.slodoggiesapp.ui.component.common.HomeTopBar
import com.bussiness.slodoggiesapp.ui.component.petOwner.dialog.CommentsDialog
import com.bussiness.slodoggiesapp.ui.component.petOwner.dialog.PetInfoDialog
import com.bussiness.slodoggiesapp.ui.component.petOwner.dialog.ReportDialog
import com.bussiness.slodoggiesapp.ui.component.petOwner.dialog.UserDetailsDialog
import com.bussiness.slodoggiesapp.ui.component.petOwner.dialog.WelcomeDialog
import com.bussiness.slodoggiesapp.ui.dialog.BottomSheetBehaviorProperties
import com.bussiness.slodoggiesapp.ui.dialog.BottomSheetDialog
import com.bussiness.slodoggiesapp.ui.dialog.BottomSheetDialogProperties
import com.bussiness.slodoggiesapp.ui.dialog.DeleteChatDialog
import com.bussiness.slodoggiesapp.ui.dialog.ReportBottomToast
import com.bussiness.slodoggiesapp.ui.screens.commonscreens.home.content.CommunityPostItem
import com.bussiness.slodoggiesapp.ui.screens.commonscreens.home.content.NormalPostItem
import com.bussiness.slodoggiesapp.ui.screens.commonscreens.home.content.ShareContentDialog
import com.bussiness.slodoggiesapp.ui.screens.commonscreens.home.content.SponsoredPostItem
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor
import com.bussiness.slodoggiesapp.util.SessionManager
import com.bussiness.slodoggiesapp.viewModel.common.HomeViewModel
import com.google.gson.Gson
import kotlinx.coroutines.NonCancellable.key
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: HomeViewModel = hiltViewModel()) {

    val uiState by viewModel.uiState.collectAsState()
    val uiStateComment by viewModel.uiStateComment.collectAsState()
    val dialogCount by viewModel.petInfoDialogCount.collectAsState()
    val context = LocalContext.current
    val activity = context as? Activity
    val sessionManager = SessionManager.getInstance(context)

    var showCommentsDialog by remember { mutableStateOf(false) }
    var deleteComment      by remember { mutableStateOf(false) }
    var showCommentsType   by remember { mutableStateOf("")    }
    var deleteCommentId    by remember { mutableStateOf("")    }
    val posts = uiState.posts
    val userId by remember { mutableStateOf("") }

    val pullRefreshState = rememberPullRefreshState(
        refreshing = uiState.isRefreshing, onRefresh = {
            viewModel.loadFirstPage()
        }
    )

//    LaunchedEffect(Unit) {
//        viewModel.loadFirstPage()
//    }
    LaunchedEffect( navController.currentBackStackEntry){
        Log.d("TESTING_SLODOGGIES","I am inside the load again")
        viewModel.loadFirstPage()
    }

    // val onReportClick = remember { { viewModel.showReportDialog() } }
    val onShareClick = remember { { viewModel.showShareContent() } }
    val onProfileClick = remember { {
        navController.navigate(Routes.CLICKED_PROFILE_SCREEN+ "/$userId")
      }

    }


    val onSponsoredClick = remember {
        {
            val dest = if (sessionManager.getUserType() == UserType.Owner)
                Routes.SERVICE_PROVIDER_DETAILS
            else
                Routes.SPONSORED_ADS_SCREEN

            navController.navigate(dest)
        }
    }


//    val currentBackStackEntry = navController.currentBackStackEntryAsState()
//
//    LaunchedEffect(currentBackStackEntry.value) {
//        viewModel.loadFirstPage()
//    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0XFFB9D4DB))
    ) {

        BackHandler {
            activity?.finish()  // closes the app
        }

        HomeTopBar(
            onNotificationClick = {
                if (sessionManager.getUserType() == UserType.Professional) navController.navigate(
                    Routes.NOTIFICATION_SCREEN
                ) else navController.navigate(Routes.PET_NOTIFICATION_SCREEN)
            },
            onMessageClick = { navController.navigate(Routes.MESSAGE_SCREEN) },

        )

        if (posts.isEmpty()){
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        painter = painterResource(R.drawable.ic_pet_post_icon),
                        contentDescription = null,
                        tint = Color(0XFF258694),
                        modifier = Modifier.size(100.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text =uiState.responseMessage,
                        style = MaterialTheme.typography.titleMedium,
                        color = Color(0XFF258694),
                        fontFamily = FontFamily(Font(R.font.outfit_medium)),
                        fontSize = 18.sp
                    )
                }
            }
        }
        key(uiState.refreshVersion) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 50.dp, top = 12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                itemsIndexed(
                    items = posts,
                    key = { _, post -> post.stableKey }
                ) { index, post ->
                    if (index == posts.lastIndex - 2) {
                        viewModel.loadNextPage()
                    }

                    when (post) {
                        is PostItem.CommunityPost -> CommunityPostItem(
                            postItem = post,
                            onReportClick = { viewModel.showReportDialog(post.postId) },
                            onShareClick = onShareClick,
                            onJoinedCommunity = { post->
                               // navController.navigate(Routes.COMMUNITY_CHAT_SCREEN)
                                val event = post as? PostItem.CommunityPost
                                val imageUrl =  event?.media?.imageUrl
                                event?.let {
                                    val receiverId = it.userId ?: ""
                                    val chatId = it.postId ?: ""
                                    /*
                                    postItem.media?.imageUrl
                                     */
                                    Log.d("CommunityChatScreen", "${it.media?.parentImageUrl ?: ""}")
                                    val receiverImage = URLEncoder.encode(
                                        it.mediaList?.get(0)?.mediaUrl ?: "",
                                        StandardCharsets.UTF_8.toString()
                                    )
                                    val receiverName = URLEncoder.encode(
                                        it.eventTitle ?: "",
                                        StandardCharsets.UTF_8.toString()
                                    )
                                    val type = "event"
                                    navController.navigate("${Routes.COMMUNITY_CHAT_SCREEN}/$receiverId/$receiverImage/$receiverName/$chatId/$type")
                                }
                                                },

                            onProfileClick = {

                                if (post.userType == "Owner") {
                                    navController.navigate("${Routes.PERSON_DETAIL_SCREEN}/${post.userId}")
                                } else {
                                    navController.navigate(Routes.CLICKED_PROFILE_SCREEN + "/${post.userId}")
                                }

                            },
                            onLikeClick = {
                                viewModel.postLikeUnlike(
                                    "", post.postId, "",
                                    onError = { msg ->
                                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                                    },
                                    onSuccess = {
                                        viewModel.toggleLike(post.postId)
                                    }
                                )
                            },
                            onSaveClick = {
                                viewModel.savePost(
                                    "", post.postId, "",
                                    onError = { msg ->
                                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                                    },
                                    onSuccess = {
                                        viewModel.toggleSave(post.postId)
                                    }
                                )
                            },
                            onClickInterested = {
                                viewModel.savePost(
                                    "", post.postId, "",
                                    onError = { msg ->
                                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                                    },
                                    onSuccess = {
                                        viewModel.toggleSave(post.postId)
                                    }
                                )

                            },
                            onFollowingClick = {
                                viewModel.addAndRemoveFollowers(
                                    post.userId,
                                    onError = { msg ->
                                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                                    }
                                )
                            },
                        )

                        is PostItem.SponsoredPost -> SponsoredPostItem(
                            post = post,
                            onReportClick = { viewModel.showReportDialog(post.postId) },
                            onShareClick = onShareClick,
                            onProfileClick = {
                                navController.navigate(Routes.CLICKED_PROFILE_SCREEN + "/${post.userId}")
                            },
                            onSponsoredClick = {
                                val dest = when {
                                    sessionManager.getUserType() == UserType.Owner ->
                                        "${Routes.SERVICE_PROVIDER_DETAILS}/${post.userId}"

                                    post.userPost -> Routes.SPONSORED_ADS_SCREEN

                                    else -> "${Routes.SERVICE_PROVIDER_DETAILS}/${post.userId}"
                                }
                                navController.navigate(dest)
                            },
                            onLikeClick = {
                                viewModel.postLikeUnlike(
                                    "", "", post.postId,
                                    onError = { msg ->
                                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                                    },
                                    onSuccess = {
                                        viewModel.toggleLike(post.postId)
                                    }
                                )
                            },
                            onCommentClick = {
                                viewModel.updatePostId(post.postId)
                                showCommentsDialog = true
                                showCommentsType = "Ad"
                            }
                        )

                        is PostItem.NormalPost -> NormalPostItem(
                            modifier = Modifier.padding(horizontal = 12.dp),
                            postItem = post,
                            onReportClick = { viewModel.showReportDialog(post.postId) },
                            onShareClick = onShareClick,
                            normalPost = true,
                            onEditClick = {},
                            onDeleteClick = {},
                            onProfileClick = {

                                if (post.userType == "Owner") {
                                    navController.navigate("${Routes.PERSON_DETAIL_SCREEN}/${post.userId}")
                                } else {
                                    navController.navigate(Routes.CLICKED_PROFILE_SCREEN + "/${post.userId}")
                                }

                            },
                            onSelfPostEdit = {
                                val postJson = Gson().toJson(post)
                                navController.currentBackStackEntry
                                    ?.savedStateHandle
                                    ?.set("Post", postJson)

                                navController.currentBackStackEntry?.savedStateHandle?.set(
                                    "PostType",
                                    "NormalPost"
                                )
                                navController.currentBackStackEntry?.savedStateHandle?.set(
                                    "Screen",
                                    "Home"
                                )

                                navController.navigate(Routes.EDIT_POST_SCREEN)
                            },
                            onSelfPostDelete = {
                                viewModel.updatePostId(post.postId)
                                viewModel.showDeleteDialog()
                            },
                            onSaveClick = {
                                viewModel.savePost(
                                    post.postId, "", "",
                                    onError = { msg ->
                                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                                    },
                                    onSuccess = {
                                        viewModel.toggleSave(post.postId)
                                    }
                                )
                            },
                            onLikeClick = {
                                viewModel.postLikeUnlike(
                                    post.postId, "", "",
                                    onError = { msg ->
                                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                                    },
                                    onSuccess = {
                                        viewModel.toggleLike(post.postId)
                                    }
                                )
                            },
                            onCommentClick = {
                                viewModel.updatePostId(post.postId)
                                showCommentsDialog = true
                                showCommentsType = "Normal"
                            },
                            onFollowingClick = {
                                viewModel.addAndRemoveFollowers(
                                    post.userId,
                                    onError = { msg ->
                                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                                    }
                                )
                            }
                        )
                    }
                }
                item {
                    if (uiState.isLoading) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(32.dp),
                                color = PrimaryColor
                            )
                        }
                    }
                }
            }
        }

        PullRefreshIndicator(
            refreshing = uiState.isRefreshing,
            state = pullRefreshState,
            modifier = Modifier
        )


        //Comment
        if (showCommentsDialog) {
            // Load comments from API when dialog opens
            var postId = ""
            var adId = ""
            if (showCommentsType == "Ad") {
                adId = uiState.postId
            } else if (showCommentsType == "Normal") {
                postId = uiState.postId
            }
            LaunchedEffect(Unit) {
                viewModel.getComments(
                    postId = postId,
                    addId = adId,
                    page = 1,
                    limit = 20,
                    onError = { msg ->
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                        /* handle error */
                    },
                    onSuccess = {

                    }
                )
            }
            BottomSheetDialog(
                onDismissRequest = {
                    //  showSheet = false
                    showCommentsDialog = false
                },
                properties = BottomSheetDialogProperties(
                    dismissOnBackPress = true,
                    dismissOnClickOutside = false,
                    dismissWithAnimation = true,
                    enableEdgeToEdge = false,
                    behaviorProperties = BottomSheetBehaviorProperties(
                        state = BottomSheetBehaviorProperties.State.Expanded, // यह सुनिश्चित करेगा कि dialog expand state में open हो
                        isFitToContents = false, // यह बदलना important है
                        skipCollapsed = true, // Collapsed state को skip करेगा
                        isHideable = true,
                        isDraggable = true,
                        peekHeight = BottomSheetBehaviorProperties.PeekHeight.Auto
                    )
                )
            ) {
                CommentsDialog(
                    comments = uiStateComment.comments/*sampleComments*/,
                    onDismiss = {
                        viewModel.clearComments()
                        showCommentsDialog = false
                        showCommentsType = ""
                    },
                    onDeleteClick = { commentId ->
                        deleteCommentId = commentId
                        deleteComment = true
                    },
                    onSandClick = { comment, type, commentId ->
                        if (type == "reply") {
                            viewModel.replyComment(
                                postId = uiState.postId,
                                commentText = comment,
                                commentId = commentId,
                                onError = { error ->
                                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show() },
                                onSuccess = {
                                }
                            )
                        }
                        else if (type == "edit") {
                            viewModel.editComment(
                                commentId = commentId, commenText = comment,
                                onSuccess = {

                                }, onError = { error ->
                                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
                                })
                        } else/* (type.equals("new"))*/ {
                            if (showCommentsType == "Ad") {
                                viewModel.addNewComment(
                                    postId = "",
                                    addId = uiState.postId,
                                    commentText = comment,
                                    onSuccess = { viewModel.increaseCommentCount(uiState.postId) },
                                    onError = { error ->
                                        Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
                                    }
                                )

                            } else if (showCommentsType == "Normal") {
                                viewModel.addNewComment(
                                    postId = uiState.postId,
                                    addId = "",
                                    commentText = comment,
                                    onSuccess = { viewModel.increaseCommentCount(uiState.postId) },
                                    onError = { error ->
                                        Toast.makeText(
                                            context,
                                            error,
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                )
                            }

                        }
                        Log.d("********", "$comment $type")
                    },
                    onCommentLikeClick = { commentId ->
                        viewModel.commentLike(
                            commentId,
                            onError = { msg ->
                                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                            },
                            onSuccess = {

                            })
                    }
                )}
        }

        if (deleteComment) {
            DeleteChatDialog(
                onDismiss = { deleteComment = false },
                onClickRemove = {
                    deleteComment = false
                    viewModel.deleteComment(deleteCommentId, onSuccess = {

                    }, onError = { error ->
                        Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
                    })
                },
                iconResId = R.drawable.delete_mi,
                text = "Delete Comment",
                description = stringResource(R.string.delete_Comment)
            )
        }

        // --- Dialogs ---
        if (sessionManager.isSignupFlowActive()) {
            if (uiState.showWelcomeDialog) {
                WelcomeDialog(
                    onDismiss = { viewModel.dismissWelcomeDialog() },
                    onSubmitClick = { viewModel.onWelcomeSubmit() },
                    icon = R.drawable.ic_party_popper_icon,
                    title = uiState.welcomeTitle,
                    description = uiState.welcomeDescription,
                    button = uiState.welcomeButton
                )

            }
            if (uiState.showUserDetailsDialog) {
                BottomSheetDialog(
                    onDismissRequest = {
                        //  showSheet = false
                        viewModel.dismissReportDialog()

                        viewModel.onReasonSelected("")
                        viewModel.onMessageChange("")
                        //   showCommentsDialog = false
                    },
                    properties = BottomSheetDialogProperties(
                        dismissOnBackPress = true,
                        dismissOnClickOutside = false,
                        dismissWithAnimation = true,
                        enableEdgeToEdge = false,
                        behaviorProperties = BottomSheetBehaviorProperties(
                            state = BottomSheetBehaviorProperties.State.Expanded, // यह सुनिश्चित करेगा कि dialog expand state में open हो
                            isFitToContents = false, // यह बदलना important है
                            skipCollapsed = true, // Collapsed state को skip करेगा
                            isHideable = true,
                            isDraggable = true,
                            peekHeight = BottomSheetBehaviorProperties.PeekHeight.Auto
                        )
                    )
                ){
                UserDetailsDialog(
                    navController = navController,
                    onDismiss = { viewModel.dismissUserDetailsDialog() },
                    onSubmit = { viewModel.onUserDetailsSubmit() },
                    onVerify = { data, type ->
                        viewModel.onVerify(navController, data, type)
                    }
                )
              }
            }
            if (uiState.showProfileCreatedDialog) {
                WelcomeDialog(
                    onDismiss = { viewModel.dismissProfileCreatedDialog() },
                    onSubmitClick = { viewModel.dismissProfileCreatedDialog() },
                    icon = R.drawable.ic_party_popper_icon,
                    title = stringResource(R.string.profileCreateTitle),
                    description = stringResource(R.string.profileCreateDes),
                    button = stringResource(R.string.explore_now),
                )
            }
            if (uiState.showContinueAddPetDialog) {
                WelcomeDialog(
                    onDismiss = { viewModel.dismissContinueAddPetDialog() },
                    onSubmitClick = { viewModel.dismissContinueAddPetDialog() },
                    icon = R.drawable.paw_print,
                    title = stringResource(R.string.add_pet),
                    description = stringResource(R.string.paw_description),
                    button = stringResource(R.string.paw_button),
                )
            }
            if (uiState.showPetInfoDialog) {
                if (dialogCount < 2) {
                    BottomSheetDialog(
                        onDismissRequest = {
                            //  showSheet = false
                            viewModel.dismissReportDialog()

                            viewModel.onReasonSelected("")
                            viewModel.onMessageChange("")
                            //   showCommentsDialog = false
                        },
                        properties = BottomSheetDialogProperties(
                            dismissOnBackPress = true,
                            dismissOnClickOutside = false,
                            dismissWithAnimation = true,
                            enableEdgeToEdge = false,
                            behaviorProperties = BottomSheetBehaviorProperties(
                                state = BottomSheetBehaviorProperties.State.Expanded, // यह सुनिश्चित करेगा कि dialog expand state में open हो
                                isFitToContents = false, // यह बदलना important है
                                skipCollapsed = true, // Collapsed state को skip करेगा
                                isHideable = true,
                                isDraggable = true,
                                peekHeight = BottomSheetBehaviorProperties.PeekHeight.Auto
                            )
                        )
                    ){
                    PetInfoDialog(
                        title = stringResource(R.string.tell_us_about_your_pet),
                        onDismiss = { viewModel.dismissPetInfoDialog() },
                        addPet = { petInfo ->
                            viewModel.incrementPetInfoDialogCount()
                            viewModel.showPetInfoDialog()
                        },
                        onContinueClick = {
                            viewModel.dismissPetInfoDialog()
                        }
                    )
                    }
                } else {
                    LaunchedEffect(Unit) {
                        viewModel.showContinueAddPetDialog()
                    }
                }
            }
        }

        if (uiState.showReportDialog) {
            BottomSheetDialog(
                onDismissRequest = {
                    //  showSheet = false
                    viewModel.dismissReportDialog()

                    viewModel.onReasonSelected("")
                    viewModel.onMessageChange("")
                 //   showCommentsDialog = false
                },
                properties = BottomSheetDialogProperties(
                    dismissOnBackPress = true,
                    dismissOnClickOutside = false,
                    dismissWithAnimation = true,
                    enableEdgeToEdge = false,
                    behaviorProperties = BottomSheetBehaviorProperties(
                        state = BottomSheetBehaviorProperties.State.Expanded, // यह सुनिश्चित करेगा कि dialog expand state में open हो
                        isFitToContents = false, // यह बदलना important है
                        skipCollapsed = true, // Collapsed state को skip करेगा
                        isHideable = true,
                        isDraggable = true,
                        peekHeight = BottomSheetBehaviorProperties.PeekHeight.Auto
                    )
                )
            ) {
            ReportDialog(
                onDismiss = { viewModel.dismissReportDialog()
                    viewModel.onReasonSelected("")

                    viewModel.onMessageChange("")
                            },
                onCancel = { viewModel.dismissReportDialog()
                    viewModel.onReasonSelected("")
                    viewModel.onMessageChange("")
                           },
                onSendReport = { viewModel.showReportToast(context)
                    viewModel.onReasonSelected("")
                    viewModel.dismissReportDialog()

                    viewModel.onMessageChange("")
                               },
                reasons = listOf(
                    "Bullying or unwanted contact",
                    "Violence, hate or exploitation",
                    "False Information",
                    "Scam, fraud or spam"
                ),
                selectedReason = uiState.selectedReason,
                message = uiState.message,
                onReasonSelected = { reason -> viewModel.onReasonSelected(reason) },
                onMessageChange = { msg -> viewModel.onMessageChange(msg) },
                title = "Report Post"
            )
            }
        }

    if (uiState.deleteDialog) {
        DeleteChatDialog(
            onDismiss = { viewModel.dismissDeleteDialog() },
            onClickRemove = {
                Log.d("********","onClickRemove")
                viewModel.deletePost(postId = uiState.postId, onSuccess = {
                    viewModel.dismissDeleteDialog()
                },
                    onError = {error->
                        Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
                    })
                            },
            iconResId = R.drawable.delete_mi,
            text = stringResource(R.string.Delete_Post),
            description = stringResource(R.string.Delete_desc)
        )
    }

        if (uiState.showReportToast) {
            ReportBottomToast(
                onDismiss = { viewModel.dismissReportToast() }
            )
        }

        if (uiState.showShareContent) {
            ShareContentDialog(
                onDismiss = { viewModel.dismissShareContent() },
                onSendClick = { viewModel.dismissShareContent() },
                data = ""
            )
        }

    }
}


//@Composable
//fun getSamplePosts(): List<PostItem> {
//    return listOf(
//        PostItem.NormalPost(
//            user = "Lydia Vaccaro with Wixx",
//            role = "Pet Mom",
//            time = "5 Min.",
//            caption = "🐾 Meet Wixx - our brown bundle of joy!",
//            description = "From tail wags to beach days, life with this 3-year-old",
//            likes = 120,
//            comments = 20,
//            shares = 10,
//            postType = "self",
//            mediaList = listOf(
//                MediaItem(
//                    MediaType.IMAGE,
//                    imageRes = R.drawable.dog1
//                ),
//                MediaItem(
//                    MediaType.IMAGE,
//                    imageUrl = "https://picsum.photos/400"
//                ),
//                MediaItem(
//                    MediaType.VIDEO,
//                    videoUrl = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
//                    thumbnailRes = null
//                )
//            )
//        ),
//        PostItem.CommunityPost(
//            userName = "Lydia Vaccaro with Wixx",
//            userImage = R.drawable.user_ic,
//            postImage = R.drawable.post_img,
//            label = "Pet Mom",
//            time = "5 Min.",
//            eventTitle = "Event Title",
//            eventDescription = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor",
//            eventDuration = "30 Mins.",
//            location = "San Luis Obispo County",
//            onClickFollow = {},
//            onClickMore = {},
//            eventStartDate = "May 25, 4:00 PM",
//            eventEndDate = "June 16, 7:00 PM",
//            likes = 200,
//            comments = 100,
//            shares = 10
//        ),
//        PostItem.SponsoredPost(
//            user = "Aisuke",
//            role = "Pet Lover",
//            time = "2 Days",
//            caption = "Summer Special: 20% Off Grooming!",
//            description = "Limited Time Offer",
//            mediaList = listOf(
//                MediaItem(
//                    MediaType.IMAGE,
//                    imageRes = R.drawable.dog1
//                ),
//            ),
//            likes = 200,
//            comments = 100,
//            shares = 10
//        ),
//        PostItem.NormalPost(
//            user = "John Doe with Max",
//            role = "Pet Dad",
//            time = "15 Min.",
//            caption = "Enjoying the sunny day!",
//            description = "Max loves playing in the park with his friends Lorem ipsum dolor sit amet," +
//                    " consectetur adipiscing Lorem ipsum dolor sit amet, " +
//                    " consectetur adipiscing Lorem ipsum dolor sit amet, consectetur adipiscing",
//            likes = 85,
//            comments = 12,
//            shares = 5,
//            postType = "other",
//            mediaList = listOf(
//                MediaItem(
//                    MediaType.IMAGE,
//                    imageRes = R.drawable.dog1
//                ),
//                MediaItem(
//                    MediaType.IMAGE,
//                    imageUrl = "https://picsum.photos/400"
//                ),
//                MediaItem(
//                    MediaType.VIDEO,
////                    videoRes = R.raw.reel,
//                    thumbnailRes = R.drawable.dummy_social_media_post
//                )
//            ),
//        )
//    )
//}
