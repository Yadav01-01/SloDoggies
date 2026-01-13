package com.bussiness.slodoggiesapp.ui.screens.commonscreens.profilepost

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.bussiness.slodoggiesapp.R
import com.bussiness.slodoggiesapp.data.newModel.home.PostItem
import com.bussiness.slodoggiesapp.navigation.Routes
import com.bussiness.slodoggiesapp.ui.component.businessProvider.HeadingTextWithIcon
import com.bussiness.slodoggiesapp.ui.component.common.NoDataView
import com.bussiness.slodoggiesapp.ui.component.petOwner.dialog.CommentsDialog
import com.bussiness.slodoggiesapp.ui.dialog.DeleteChatDialog
import com.bussiness.slodoggiesapp.ui.screens.commonscreens.home.content.NormalPostItem
import com.bussiness.slodoggiesapp.ui.theme.PrimaryColor
import com.bussiness.slodoggiesapp.viewModel.common.HomeViewModel
import com.google.gson.Gson

@Composable
fun UserPost(
    navController: NavHostController,
    postId: String,
    type: String,
    userId: String
) {
    val viewModel: HomeViewModel = hiltViewModel()
    var isNavigating by remember { mutableStateOf(false) }
    val state by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val listState = rememberLazyListState()

    var showCommentsDialog  by remember { mutableStateOf(false) }
    var showCommentsType  by remember { mutableStateOf("") }
    var deleteComment by remember { mutableStateOf(false) }
    var deleteCommentId by remember { mutableStateOf("") }

    val posts = state.posts
    Log.d("TAG",userId)

    val uiStateComment by viewModel.uiStateComment.collectAsState()

    LaunchedEffect(type, userId) {
        when (type) {
            "SavePost" -> { viewModel.loadSavePage() }
            "clickedProfile" -> { viewModel.loadClickedProfile(userId) }
            "Profile" -> {viewModel.loadClickedProfile(userId)}
            else -> {/* viewModel.loadFirstPage()*/ }
        }
    }

    LaunchedEffect(posts, postId) {
        Log.d("TESTING_insta","Post Id is here "+postId)
        if (postId.isNotBlank() && posts.isNotEmpty()) {

            val index = posts.indexOfFirst { post ->
                when (post) {
                    is PostItem.NormalPost -> post.postId == postId
                    is PostItem.CommunityPost -> post.postId == postId
                    is PostItem.SponsoredPost -> post.postId == postId
                }
            }

            if (index >= 0) {
                listState.scrollToItem(index)
            }

        }

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
        HeadingTextWithIcon(
            textHeading = stringResource(R.string.posts),
            onBackClick = {  if (!isNavigating) {
                isNavigating = true
                navController.popBackStack()
            } }
        )

        HorizontalDivider(thickness = 2.dp, color = PrimaryColor)

        when {
            state.posts.isEmpty() -> {
                NoDataView("Oops ,No posts available at the moment.")
            }

            else -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFFCEE1E6)),
                    contentPadding = PaddingValues(horizontal = 5.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    state = listState
                ) {
                    itemsIndexed(
                        items = posts,
                        key = { _, post -> post.stableKey }
                    ) { index, post ->

                        if (index == posts.lastIndex - 2) {
                            viewModel.loadPostNextPage()
                        }

                        when (post) {
                            is PostItem.NormalPost -> NormalPostItem(
                                modifier = Modifier.padding(horizontal = 12.dp),
                                post,
                                onReportClick = {
                                    viewModel.showReportDialog(post.postId)
                                },
                                onShareClick = {},
                                normalPost = true,
                                onEditClick = {},
                                onDeleteClick = {},
                                onProfileClick = { /*navController.navigate(Routes.PERSON_DETAIL_SCREEN)*/ },
                                onSelfPostEdit = {
                                    val postJson = Gson().toJson(post)
                                    navController.currentBackStackEntry
                                        ?.savedStateHandle
                                        ?.set("Post", postJson)
                                    navController.currentBackStackEntry?.savedStateHandle?.set("PostType", "NormalPost")
                                    navController.currentBackStackEntry?.savedStateHandle?.set("Screen", "Home")
                                    navController.navigate(Routes.EDIT_POST_SCREEN)
                                },
                                onSelfPostDelete = {
                                    viewModel.updatePostId(post.postId)
                                    viewModel.showDeleteDialog()
                                                   },
                                onSaveClick = {
                                    viewModel.savePost(post.postId, "", "",
                                        onError = { msg -> Toast.makeText(context, msg, Toast.LENGTH_SHORT).show() },
                                        onSuccess = {
                                            viewModel.toggleSave(post.postId,"user_save")
                                        })
                                },
                                onLikeClick = {
                                    viewModel.postLikeUnlike(post.postId, "", "",
                                        onError = { msg -> Toast.makeText(context, msg, Toast.LENGTH_SHORT).show() },
                                        onSuccess = {
                                            viewModel.toggleLike(post.postId)
                                        })
                                },
                                onCommentClick = {
                                    viewModel.updatePostId(post.postId)
                                    showCommentsDialog = true
                                    showCommentsType = "Normal"
                                },
                                onFollowingClick = {
                                    viewModel.addAndRemoveFollowers(post.userId, onError = {
                                            msg -> Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                                    })
                                }
                            )
                            is PostItem.CommunityPost -> {
                                // TODO: handle or skip
                            }

                            is PostItem.SponsoredPost -> {
                                // TODO: handle or skip
                            }
                        }
                    }
                }
            }
        }

    }
    if (state.deleteDialog) {
        DeleteChatDialog(
            onDismiss = { viewModel.dismissDeleteDialog() },
            onClickRemove = {
                Log.d("********","onClickRemove")
                viewModel.deletePost(postId = state.postId, onSuccess = {
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

    //Comment
    if (showCommentsDialog) {
        // Load comments from API when dialog opens
        var  postId = ""
        var  adId = ""
        if (showCommentsType.equals("Ad")){
            adId = state.postId
        }else if (showCommentsType.equals("Normal")){
            postId = state.postId
        }
        LaunchedEffect(Unit) {
            viewModel.getComments(
                postId = postId,
                addId = adId,
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
                showCommentsType = ""
            },
            onDeleteClick = {commentId ->
                deleteCommentId = commentId
                deleteComment = true
            },
            onSandClick = {comment, type,commentId ->
                if (type.equals("reply")){
                    viewModel.replyComment(postId = state.postId,
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
                    if (showCommentsType.equals("Ad")){
                        viewModel.addNewComment(
                            postId = "",
                            addId = state.postId,
                            commentText = comment,
                            onSuccess = {  viewModel.increaseCommentCount(state.postId) },
                            onError = { error -> Toast.makeText(context, error, Toast.LENGTH_SHORT).show() }
                        )

                    }else if (showCommentsType.equals("Normal")){
                        viewModel.addNewComment(
                            postId = state.postId,
                            addId = "",
                            commentText = comment,
                            onSuccess = {  viewModel.increaseCommentCount(state.postId) },
                            onError = { error -> Toast.makeText(context, error, Toast.LENGTH_SHORT).show() }
                        )
                    }

                }
                Log.d("********","$comment $type")
            },
            onCommentLikeClick = {commentId ->
                viewModel.commentLike(commentId,
                    onError = { msg -> Toast.makeText(context, msg, Toast.LENGTH_SHORT).show() },
                    onSuccess = {

                    })
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
}

