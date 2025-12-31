package com.bussiness.slodoggiesapp.ui.screens.commonscreens.discover

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.data.newModel.home.PostItem
import com.bussiness.slodoggiesapp.navigation.Routes
import com.bussiness.slodoggiesapp.ui.component.businessProvider.CategorySection
import com.bussiness.slodoggiesapp.ui.component.businessProvider.HashtagSection
import com.bussiness.slodoggiesapp.ui.component.businessProvider.SearchBar
import com.bussiness.slodoggiesapp.ui.component.businessProvider.SocialEventCard
import com.bussiness.slodoggiesapp.ui.component.petOwner.dialog.CommentsDialog
import com.bussiness.slodoggiesapp.ui.component.petOwner.dialog.ReportDialog
import com.bussiness.slodoggiesapp.ui.dialog.BottomSheetBehaviorProperties
import com.bussiness.slodoggiesapp.ui.dialog.BottomSheetDialog
import com.bussiness.slodoggiesapp.ui.dialog.BottomSheetDialogProperties
import com.bussiness.slodoggiesapp.ui.dialog.DeleteChatDialog
import com.bussiness.slodoggiesapp.ui.dialog.PetPlaceDialog
import com.bussiness.slodoggiesapp.ui.dialog.ReportBottomToast
import com.bussiness.slodoggiesapp.ui.dialog.SavedDialog
import com.bussiness.slodoggiesapp.ui.screens.commonscreens.discover.content.ActivitiesPostsList
import com.bussiness.slodoggiesapp.ui.screens.commonscreens.discover.content.PetPlacesResults
import com.bussiness.slodoggiesapp.ui.screens.commonscreens.discover.content.ShowPetsNearYou
import com.bussiness.slodoggiesapp.ui.screens.commonscreens.home.content.ShareContentDialog
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor
import com.bussiness.slodoggiesapp.viewModel.businessProvider.DiscoverViewModel
import com.bussiness.slodoggiesapp.viewModel.common.HomeViewModel

@Composable
fun DiscoverScreen(navController: NavHostController, viewModel: DiscoverViewModel = hiltViewModel()){


    val uiState by viewModel.uiState.collectAsState()

    val uiStateComment by viewModel.uiStateComment.collectAsState()

    val context = LocalContext.current

    var message by remember { mutableStateOf("") }

    var selectedReason by remember { mutableStateOf("") }

    var showCommentsDialog by remember { mutableStateOf(false) }

    var deleteComment      by remember { mutableStateOf(false) }

    var showCommentsType   by remember { mutableStateOf("")    }

    var deleteCommentId    by remember { mutableStateOf("")    }

    val posts = uiState.posts

    val selectedPetPlace = uiState.selectedPetPlace




    LaunchedEffect(uiState.error) {

        val msg = uiState.error

        if (!msg.isNullOrBlank()) {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        }

    }



    BackHandler {
        if (!navController.popBackStack(Routes.HOME_SCREEN, false)) {
            navController.navigate(Routes.HOME_SCREEN) {
                launchSingleTop = true
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().background(Color.White).padding(horizontal = 12.dp)
    ) {

        Spacer(Modifier.height(8.dp))

        SearchBar(query = uiState.query, onQueryChange = viewModel::updateQuery, placeholder = "Search")

        Spacer(modifier = Modifier.height(12.dp))

        HashtagSection(
            hashtags = uiState.hashtags,
            onHashtagClick = { hashtag ->
                viewModel.onHashtagSelected(hashtag)
            }
        )

        Spacer(modifier = Modifier.height(12.dp))

        CategorySection(categories = uiState.categories, selectedCategory = uiState.selectedCategory, onCategorySelected = { viewModel.selectCategory(it) })

        Spacer(modifier = Modifier.height(10.dp))
        
        when (uiState.selectedCategory) {

            "Pets Near You" -> ShowPetsNearYou(uiState.pets, navController)

            "Pet Places"    -> PetPlacesResults(
                uiState.petPlaces,
                onItemClick = { petPlace ->
                    viewModel.onPetPlaceClicked(petPlace)
                }
            )
            "Activities"    -> ActivitiesPostsList(
                posts = uiState.posts,
                onReportClick = { viewModel.showReportDialog(true) },
                onShareClick = { viewModel.showShareContent(true) },
                onLikeClick = { postId ->viewModel.postLikeUnlike(postId,"activity")   },
                onCommentOpen = { postId->
                    showCommentsType="Normal"
                    viewModel.updatePostId(postId)
                    showCommentsDialog = true
                },
                onSaveClick = {
                     postId-> viewModel.savePost(
                     postId, "", "",
                     onError = { msg ->
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                              },
                     onSuccess = {
                        viewModel.toggleSave(postId)
                     }
                  )
                },
                onProfileClick = { postType, postUserId ->
                    Log.d("TESTING_DISCOVBER", "Post Type"+ postType +" "+postUserId)
                    if (postType == "Owner") {
                        navController.navigate("${Routes.PERSON_DETAIL_SCREEN}/$postUserId")
                    } else {
                        navController.navigate("${Routes.CLICKED_PROFILE_SCREEN}/$postUserId")
                    }
                }
            )

            "Events" -> EventsResult(
                posts = uiState.posts,
                onClickMore = { viewModel.showReportDialog(true) },
                onShareClick = { viewModel.showShareContent(true) },
                onLikeClick = { postId -> viewModel.postLikeUnlike(postId) },
                onJoinClick = { navController.navigate(Routes.COMMUNITY_CHAT_SCREEN) },
                onProfileClick = { postUserId -> navController.navigate("${Routes.PERSON_DETAIL_SCREEN}/${postUserId}") },
                onInterested = { postId -> viewModel.savePost("event",postId,
                    onSuccess = { viewModel.showSavedDialog(true) }
                ) },
                onClickFollowing = { postId -> viewModel.addAndRemoveFollowers(postId,
                    onError = { msg ->
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                    }) }
            )

            else -> ShowPetsNearYou(uiState.pets, navController)

        }
    }

    if (uiState.showPetPlaceDialog && selectedPetPlace != null) {
        BottomSheetDialog(
            onDismissRequest = {
                viewModel.dismissPetPlaceDialog()
            },
            properties = BottomSheetDialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = false,
                dismissWithAnimation = true,
                enableEdgeToEdge = false,
                behaviorProperties = BottomSheetBehaviorProperties(
                    state = BottomSheetBehaviorProperties.State.Expanded,
                    isFitToContents = false,
                    skipCollapsed = true,
                    isHideable = true,
                    isDraggable = true,
                    peekHeight = BottomSheetBehaviorProperties.PeekHeight.Auto
                )
            )
        ) {
            PetPlaceDialog(
                petPlace = selectedPetPlace,
                onDismiss = { viewModel.dismissPetPlaceDialog() }
            )
        }
    }


    if (uiState.showShareContentDialog) {
        ShareContentDialog(
            onDismiss = { viewModel.showShareContent(false) },
            onSendClick = { viewModel.showShareContent(true) },
            data = ""
        )
    }
    if (uiState.showSavedDialog){
        SavedDialog(
            icon = R.drawable.icon_park_outline_success,
            title = stringResource(R.string.Event_Saved),
            description = stringResource(R.string.saved_description),
            onDismiss = { viewModel.dismissSavedDialog() }
        )
    }
    if (uiState.showReportDialog) {

        Log.d("TESTING_STATE","CALLING DISCOVER FIRST TIME")

        BottomSheetDialog(
            onDismissRequest = {
                //  showSheet = false
                viewModel.showReportDialog(false)
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
                onDismiss = { viewModel.showReportDialog(false) },
                onCancel = { viewModel.showReportDialog(false) },
                onSendReport = { viewModel.showReportToast() },
                reasons = listOf(
                    "Bullying or unwanted contact",
                    "Violence, hate or exploitation",
                    "False Information",
                    "Scam, fraud or spam"
                ),
                selectedReason = selectedReason,
                message = message,
                onReasonSelected = { reason -> selectedReason = reason },
                onMessageChange = { message = it },
                title = "Report Post"
            )
        }
    }

    if (uiState.showReportToast){
        BottomSheetDialog(
            onDismissRequest = {
                //  showSheet = false
                viewModel.dismissReportToast()
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
            ReportBottomToast(
                onDismiss = { viewModel.dismissReportToast() }
            )
        }
    }

    //Comment
    //Comment
    if (showCommentsDialog) {
        // Load comments from API when dialog opens
        var postId = ""
        var adId = ""

            postId = uiState.postId
            Log.d("TESTING_POST_ID",postId)

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
                            commentText = comment, commentId = commentId, onError = { error ->
                                Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
                            }, onSuccess = {

                            })
                    } else if (type == "edit") {
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

}


@Composable
fun EventsResult(
    posts: List<PostItem>,
    onClickMore: () -> Unit,
    onJoinClick: () -> Unit,
    onShareClick: () -> Unit,
    onLikeClick: (String) -> Unit,
    onProfileClick: (String) -> Unit,
    onInterested: (String) -> Unit,
    onClickFollowing: (String) -> Unit,
) {
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
                    tint = Color.Gray,
                    modifier = Modifier.size(100.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "OOps! No Event found",
                    style = MaterialTheme.typography.titleMedium,
                    color = PrimaryColor,
                    fontFamily = FontFamily(Font(R.font.outfit_medium)),
                    fontSize = 18.sp
                )
            }
        }
    }else{
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(posts) { event ->
                // Check each item
                if (event is PostItem.CommunityPost) {

                    SocialEventCard(
                        postItem = event,
                        onReportClick = { onClickMore() },
                        onShareClick = { onShareClick() },
                        onLikeClick = { onLikeClick(event.postId) },
                        onJoinedCommunity = { onJoinClick() },
                        onProfileClick = { onProfileClick(event.userId) },
                        onInterested = { onInterested(event.postId) },
                        onClickFollowing = { onClickFollowing(event.postId) },
                        isFollowing = event.iAmFollowing
                    )

                }
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun DiscoverScreenPreview() {
    val navController = rememberNavController()
    DiscoverScreen(navController = navController)
}