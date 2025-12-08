package com.bussiness.slodoggiesapp.ui.screens.commonscreens.home

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.data.model.main.UserType
import com.bussiness.slodoggiesapp.data.newModel.home.PostItem
import com.bussiness.slodoggiesapp.navigation.Routes
import com.bussiness.slodoggiesapp.ui.component.common.HomeTopBar
import com.bussiness.slodoggiesapp.ui.component.petOwner.dialog.Comment
import com.bussiness.slodoggiesapp.ui.component.petOwner.dialog.CommentsDialog
import com.bussiness.slodoggiesapp.ui.component.petOwner.dialog.PetInfoDialog
import com.bussiness.slodoggiesapp.ui.component.petOwner.dialog.ReportDialog
import com.bussiness.slodoggiesapp.ui.component.petOwner.dialog.UserDetailsDialog
import com.bussiness.slodoggiesapp.ui.component.petOwner.dialog.WelcomeDialog
import com.bussiness.slodoggiesapp.ui.dialog.DeleteChatDialog
import com.bussiness.slodoggiesapp.ui.dialog.ReportBottomToast
import com.bussiness.slodoggiesapp.ui.screens.commonscreens.home.content.CommunityPostItem
import com.bussiness.slodoggiesapp.ui.screens.commonscreens.home.content.NormalPostItem
import com.bussiness.slodoggiesapp.ui.screens.commonscreens.home.content.ShareContentDialog
import com.bussiness.slodoggiesapp.ui.screens.commonscreens.home.content.SponsoredPostItem
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor
import com.bussiness.slodoggiesapp.util.SessionManager
import com.bussiness.slodoggiesapp.viewModel.common.HomeViewModel

@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: HomeViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsState()
    val dialogCount by viewModel.petInfoDialogCount.collectAsState()

    val uiStateComment by viewModel.uiStateComment.collectAsState()

    val context = LocalContext.current
    val activity = context as? Activity
    val sessionManager = SessionManager.getInstance(context)

    var showCommentsDialog  by remember { mutableStateOf(false) }
    var deleteComment by remember { mutableStateOf(false) }
    var deleteCommentId by remember { mutableStateOf("") }

    val posts = uiState.posts

   // val onReportClick = remember { { viewModel.showReportDialog() } }
    val onShareClick = remember { { viewModel.showShareContent() } }
    val onProfileClick = remember { { navController.navigate(Routes.CLICKED_PROFILE_SCREEN) } }

    val onSponsoredClick = remember {
        {
            val dest = if (sessionManager.getUserType() == UserType.Owner)
                Routes.SERVICE_PROVIDER_DETAILS
            else
                Routes.SPONSORED_ADS_SCREEN

            navController.navigate(dest)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0XFFB9D4DB))
    ) {

        BackHandler {
            activity?.finish()  // closes the app
        }

        HomeTopBar(
            onNotificationClick = { if (sessionManager.getUserType() == UserType.Professional)navController.navigate(Routes.NOTIFICATION_SCREEN) else navController.navigate(Routes.PET_NOTIFICATION_SCREEN) },
            onMessageClick = { navController.navigate(Routes.MESSAGE_SCREEN) }
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 60.dp),
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
                        post,
                        onReportClick = {
                            viewModel.showReportDialog(post.postId)
                        },
                        onShareClick = onShareClick,
                        onJoinedCommunity = { navController.navigate(Routes.COMMUNITY_CHAT_SCREEN) },
                        onProfileClick = { navController.navigate(Routes.PERSON_DETAIL_SCREEN) },
                        onLikeClick = {
                            viewModel.postLikeUnlike(post.postId,
                                onError = { msg -> Toast.makeText(context, msg, Toast.LENGTH_SHORT).show() },
                                onSuccess = {

                                })
                        }
                    )


                    is PostItem.SponsoredPost -> SponsoredPostItem(
                        post = post,
                        onReportClick = {
                            viewModel.showReportDialog(post.postId)
                        },
                        onShareClick = onShareClick,
                        onProfileClick = onProfileClick,
                        onSponsoredClick = onSponsoredClick,
                        onLikeClick = {
                            viewModel.postLikeUnlike(post.postId,
                                onError = { msg -> Toast.makeText(context, msg, Toast.LENGTH_SHORT).show() },
                                onSuccess = {

                                })

                        },
                        onCommentClick = {
                            // Log.d("********","onCommentClick")
                            viewModel.updatePostId(post.postId)
                            showCommentsDialog = true
                        }
                    )

                    is PostItem.NormalPost -> NormalPostItem(
                        modifier = Modifier.padding(horizontal = 12.dp),
                        post,
                        onReportClick = {
                            viewModel.showReportDialog(post.postId)
                        },
                        onShareClick = onShareClick,
                        normalPost = true,
                        onEditClick = {

                        },
                        onDeleteClick = {},
                        onProfileClick = { navController.navigate(Routes.PERSON_DETAIL_SCREEN) },
                        onSelfPostEdit = { navController.navigate(Routes.EDIT_POST_SCREEN) },
                        onSelfPostDelete = { viewModel.showDeleteDialog() },
                        onSaveClick = {
                            viewModel.savePost(post.postId,
                                onError = { msg -> Toast.makeText(context, msg, Toast.LENGTH_SHORT).show() },
                                onSuccess = {

                                })

                        },
                        onLikeClick = {
                            viewModel.postLikeUnlike(post.postId,
                                onError = { msg -> Toast.makeText(context, msg, Toast.LENGTH_SHORT).show() },
                                onSuccess = {

                                })

                        },
                        onCommentClick = {
                           // Log.d("********","onCommentClick")
                            viewModel.updatePostId(post.postId)
                            showCommentsDialog = true
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

    //Comment
    if (showCommentsDialog) {
        // Load comments from API when dialog opens
        LaunchedEffect(Unit) {
            viewModel.getComments(
                postId = uiState.postId,
                page = 1,
                limit = 20,
                onError = {
                    msg -> Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                /* handle error */
                },
                onSuccess = {

                }
            )
        }
        CommentsDialog(
            comments = uiStateComment.comments/*sampleComments*/,
            onDismiss = {
                viewModel.clearComments()
                showCommentsDialog = false
                        },
            onDeleteClick = {commentId ->
                deleteCommentId = commentId
                deleteComment = true },
            onSandClick = {comment, type,commentId ->
               if (type.equals("reply")){
                    viewModel.replyComment(postId = uiState.postId,
                        commentText = comment, commentId = commentId, onError = {error->
                            Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
                        }, onSuccess = {

                        })
                }else if (type.equals("edit")){
                  viewModel.editComment(commentId = commentId, commenText = comment,
                      onSuccess = {

                      }, onError = {error->
                          Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
                      })
                }  else/* (type.equals("new"))*/ {
                viewModel.addNewComment(
                    postId = uiState.postId,
                    commentText = comment,
                    onSuccess = { Log.d("COMMENT", "Added successfully") },
                    onError = { error -> Toast.makeText(context, error, Toast.LENGTH_SHORT).show() }
                )
            }
            Log.d("********","$comment $type")
            }
        )
    }

    if (deleteComment){
        DeleteChatDialog(
            onDismiss = { deleteComment = false },
            onClickRemove = {
                deleteComment = false
                     viewModel.deleteComment(deleteCommentId, onSuccess = {

                     }, onError = {error->
                         Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
                     })
                            },
            iconResId = R.drawable.delete_mi,
            text = "Delete Comment",
            description = stringResource(R.string.delete_Comment)
        )
    }

    // --- Dialogs ---
    if (sessionManager.isSignupFlowActive()){
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
            UserDetailsDialog(
                navController = navController,
                onDismiss = { viewModel.dismissUserDetailsDialog() },
                onSubmit = { viewModel.onUserDetailsSubmit() },
                onVerify = { data,type ->
                    viewModel.onVerify(navController,data,type)
                }
            )
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
            } else {
                LaunchedEffect(Unit) {
                    viewModel.showContinueAddPetDialog()
                }
            }
        }
    }

    if (uiState.showReportDialog) {
        ReportDialog(
            onDismiss = { viewModel.dismissReportDialog() },
            onCancel = { viewModel.dismissReportDialog() },
            onSendReport = { viewModel.showReportToast(context) },
            reasons = listOf("Bullying or unwanted contact",
                             "Violence, hate or exploitation",
                             "False Information",
                             "Scam, fraud or spam"),
            selectedReason = uiState.selectedReason,
            message = uiState.message,
            onReasonSelected = { reason -> viewModel.onReasonSelected(reason) },
            onMessageChange = { msg -> viewModel.onMessageChange(msg) },
            title = "Report Post"
        )
    }

    if (uiState.deleteDialog) {
        DeleteChatDialog(
            onDismiss = { viewModel.dismissDeleteDialog() },
            onClickRemove = { viewModel.dismissDeleteDialog()  },
            iconResId = R.drawable.delete_mi,
            text = stringResource(R.string.Delete_Post),
            description = stringResource(R.string.Delete_desc)
        )
    }

    if (uiState.showReportToast){
        ReportBottomToast(
            onDismiss = {viewModel.dismissReportToast()}
        )
    }

    if (uiState.showShareContent) {
        ShareContentDialog(
            onDismiss = { viewModel.dismissShareContent() },
            onSendClick = { viewModel.dismissShareContent() }
        )
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
