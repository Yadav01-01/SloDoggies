package com.bussiness.slodoggiesapp.viewModel.common

import android.content.Context
import android.widget.Toast
import androidx.annotation.OptIn
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import androidx.navigation.NavHostController
import com.bussiness.slodoggiesapp.data.model.main.UserType
import com.bussiness.slodoggiesapp.data.newModel.home.HomeFeedMapper
import com.bussiness.slodoggiesapp.data.newModel.home.MediaResponse
import com.bussiness.slodoggiesapp.data.newModel.home.PostItem
import com.bussiness.slodoggiesapp.data.newModel.home.PostMediaResponse
import com.bussiness.slodoggiesapp.data.remote.Repository
import com.bussiness.slodoggiesapp.data.uiState.CommentItem
import com.bussiness.slodoggiesapp.data.uiState.CommentReply
import com.bussiness.slodoggiesapp.data.uiState.CommentUser
import com.bussiness.slodoggiesapp.data.uiState.CommentsUiState
import com.bussiness.slodoggiesapp.data.uiState.HomeUiState
import com.bussiness.slodoggiesapp.navigation.Routes
import com.bussiness.slodoggiesapp.network.Resource
import com.bussiness.slodoggiesapp.util.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val sessionManager: SessionManager,
    private val repository: Repository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState

    private val _uiStateComment = MutableStateFlow(CommentsUiState())
    val uiStateComment: StateFlow<CommentsUiState> = _uiStateComment

    private var currentPage = 1
    private var isLastPage = false
    private var isRequestRunning = false
    private val localSavedState = mutableMapOf<String, Boolean>()
    private val localLikeState = mutableMapOf<String, Boolean>()

    init {
      //  loadFirstPage()
        initWelcomeDialog()
        friendList()
    }

    fun loadFirstPage() {
        currentPage = 1
        isLastPage = false
        fetchPosts(isFirstPage = true)
    }

    fun loadPostFirstPage() {
        currentPage = 1
        isLastPage = false
        fetchMyPosts(isFirstPage = true)
    }



    fun loadClickedProfile(ownerUserId: String) {
        currentPage = 1
        isLastPage = false
        fetchClickedProfilePost(ownerUserId = ownerUserId, isFirstPage = true)
    }

    fun loadNextPage() {
        if (!isLastPage && !isRequestRunning) {
            fetchPosts(isFirstPage = false)
        }
    }

    fun loadPostNextPage() {
        if (!isLastPage && !isRequestRunning) {
            fetchMyPosts(isFirstPage = false)
        }
    }

    fun loadSaveNextPage() {
        if (!isLastPage && !isRequestRunning) {
            getMySavedPosts(isFirstPage = false)
        }
    }

    fun loadSavePage() {
        if (!isLastPage && !isRequestRunning) {
            fetchSaveOnlyPosts(isFirstPage = false)
        }
    }

    private fun friendList(){
        viewModelScope.launch {
            repository.friendList(sessionManager.getUserId()).collectLatest { result ->
                when (result){
                    is Resource.Loading -> {
                        _uiState.update { state ->
                            state.copy(isLoading = true)
                        }
                    }
                    is Resource.Success -> {
                        _uiState.update { state ->
                            state.copy(isLoading = false, friendsListData = result.data.data)
                        }
                    }
                    is Resource.Error -> {
                        _uiState.update { state ->
                            state.copy(isLoading = false, errorMessage = result.message ?: "Something went wrong")
                        }
                    }
                    Resource.Idle -> Unit
                }
            }
        }
    }

    private fun fetchSaveOnlyPosts(isFirstPage: Boolean) {
        if (isRequestRunning) return
        isRequestRunning = true

        _uiState.update { state ->
            state.copy(
                isLoading = isFirstPage,
                isLoadingMore = !isFirstPage,
                errorMessage = ""
            )
        }

        viewModelScope.launch {

            repository.getMySavedPosts(
                userId = sessionManager.getUserId(),
                page = currentPage.toString()
            ).collectLatest { result ->

                when (result) {

                    is Resource.Success -> {
                        val response = result.data.data

                        val items = response?.items ?: emptyList()
                        val uiPosts = HomeFeedMapper.map(items)

                        //  Merge Fix
                        val mergedPosts = mergeApiWithLocal(uiPosts)

                        _uiState.update { state ->
                            state.copy(
                                posts = if (isFirstPage) mergedPosts
                                else state.posts + mergedPosts,
                                isLoading = false,
                                isLoadingMore = false,
                                errorMessage = ""
                            )
                        }

                        val totalPages = response?.totalPage ?: 1
                        isLastPage = currentPage >= totalPages
                        if (!isLastPage) currentPage++
                    }

                    is Resource.Error -> {
                        _uiState.update { state ->
                            state.copy(
                                errorMessage = result.message ?: "Something went wrong",
                                isLoading = false,
                                isLoadingMore = false
                            )
                        }
                    }

                    is Resource.Loading -> Unit
                    Resource.Idle -> Unit
                }

                isRequestRunning = false
            }
        }
    }
    private fun getMySavedPosts(isFirstPage: Boolean) {
        if (isRequestRunning) return
        isRequestRunning = true

        _uiState.update { state ->
            state.copy(
                isLoading = isFirstPage,
                isLoadingMore = !isFirstPage,
                errorMessage = ""
            )
        }

        viewModelScope.launch {

            repository.getMySavedPosts(
                userId = sessionManager.getUserId(),
                page = currentPage.toString()
            ).collectLatest { result ->

                when (result) {

                    is Resource.Success -> {
                        val response = result.data.data

                        val items = response?.items ?: emptyList()
                        val uiPosts = HomeFeedMapper.map(items)

                        // 🔥 Merge Fix
                        val mergedPosts = mergeApiWithLocal(uiPosts)

                        _uiState.update { state ->
                            state.copy(
                                posts = if (isFirstPage) mergedPosts
                                else state.posts + mergedPosts,
                                isLoading = false,
                                isLoadingMore = false,
                                errorMessage = ""
                            )
                        }

                        val totalPages = response?.totalPage ?: 1
                        isLastPage = currentPage >= totalPages
                        if (!isLastPage) currentPage++
                    }

                    is Resource.Error -> {
                        _uiState.update { state ->
                            state.copy(
                                errorMessage = result.message ?: "Something went wrong",
                                isLoading = false,
                                isLoadingMore = false
                            )
                        }
                    }

                    is Resource.Loading -> Unit
                    Resource.Idle -> Unit
                }

                isRequestRunning = false
            }
        }
    }

    private fun fetchPosts(isFirstPage: Boolean) {
        if (isRequestRunning) return
        isRequestRunning = true

        _uiState.update { state ->
            state.copy(
                isLoading = isFirstPage,
                isLoadingMore = !isFirstPage,
                errorMessage = ""
            )
        }

        viewModelScope.launch {

            repository.getHomeFeed(
                userId = sessionManager.getUserId(),
                page = currentPage.toString()
            ).collectLatest { result ->

                when (result) {

                    is Resource.Success -> {
                        val response = result.data.data

                        val items = response?.items ?: emptyList()
                        val uiPosts = HomeFeedMapper.map(items)

                        val mergedPosts = mergeApiWithLocal(uiPosts)

                        _uiState.update { state ->
                            state.copy(
                                posts = if (isFirstPage) mergedPosts
                                else state.posts + mergedPosts,
                                isLoading = false,
                                isLoadingMore = false,
                                errorMessage = ""
                            )
                        }

                        val totalPages = response?.totalPage ?: 1
                        isLastPage = currentPage >= totalPages
                        if (!isLastPage) currentPage++
                    }

                    is Resource.Error -> {
                        _uiState.update { state ->
                            state.copy(
                                errorMessage = result.message ?: "Something went wrong",
                                isLoading = false,
                                isLoadingMore = false
                            )
                        }
                    }

                    is Resource.Loading -> Unit
                    Resource.Idle -> Unit
                }

                isRequestRunning = false
            }
        }
    }

    //  Merge function: API + Local
    private fun fetchMyPosts(isFirstPage: Boolean) {
        if (isRequestRunning) return
        isRequestRunning = true

        _uiState.update { state ->
            state.copy(
                isLoading = isFirstPage,
                isLoadingMore = !isFirstPage,
                errorMessage = ""
            )
        }

        viewModelScope.launch {
            repository.getMyPostDetails(
                userId = sessionManager.getUserId(),
                page = currentPage.toString()
            ).collectLatest { result ->

                when (result) {

                    is Resource.Success -> {

                        val response = result.data.data
                        val apiPosts = response?.data ?: emptyList()

                        //  Convert API PostItem → UI PostItem.NormalPost
                        val normalPosts = apiPosts.map { it.toNormalPost() }

                        //  Pagination Safe Merge
                        val finalPosts =
                            if (isFirstPage) normalPosts
                            else _uiState.value.posts + normalPosts

                        //  Update UI State
                        _uiState.update { state ->
                            state.copy(
                                posts = finalPosts,
                                isLoading = false,
                                isLoadingMore = false,
                                errorMessage = ""
                            )
                        }

                        //  Pagination Handler
                        val totalPages = response?.total_page ?: 1
                        isLastPage = currentPage >= totalPages
                        if (!isLastPage) currentPage++
                    }

                    is Resource.Error -> {
                        _uiState.update { state ->
                            state.copy(
                                errorMessage = result.message ?: "Something went wrong",
                                isLoading = false,
                                isLoadingMore = false
                            )
                        }
                    }

                    is Resource.Loading -> Unit
                    Resource.Idle -> Unit
                }

                isRequestRunning = false
            }
        }
    }

    private fun fetchClickedProfilePost(ownerUserId:String, isFirstPage: Boolean){
        if (isRequestRunning) return
        isRequestRunning = true

        _uiState.update { state ->
            state.copy(
                isLoading = isFirstPage,
                isLoadingMore = !isFirstPage,
                errorMessage = ""
            )
        }

        viewModelScope.launch {

            repository.getMyPostDetails(
                userId = ownerUserId,
                page = currentPage.toString()
            ).collectLatest { result ->

                when (result) {

                    is Resource.Success -> {

                        val response = result.data.data
                        val apiPosts = response?.data ?: emptyList()

                        //  Convert API PostItem → UI PostItem.NormalPost
                        val normalPosts = apiPosts.map { it.toNormalPost() }

                        //  Pagination Safe Merge
                        val finalPosts =
                            if (isFirstPage) normalPosts
                            else _uiState.value.posts + normalPosts

                        //  Update UI State
                        _uiState.update { state ->
                            state.copy(
                                posts = finalPosts,
                                isLoading = false,
                                isLoadingMore = false,
                                errorMessage = ""
                            )
                        }

                        //  Pagination Handler
                        val totalPages = response?.total_page ?: 1
                        isLastPage = currentPage >= totalPages
                        if (!isLastPage) currentPage++
                    }

                    is Resource.Error -> {
                        _uiState.update { state ->
                            state.copy(
                                errorMessage = result.message ?: "Something went wrong",
                                isLoading = false,
                                isLoadingMore = false
                            )
                        }
                    }

                    is Resource.Loading -> Unit
                    Resource.Idle -> Unit
                }

                isRequestRunning = false
            }
        }
    }


    fun com.bussiness.slodoggiesapp.data.newModel.PostItem.toNormalPost(): PostItem.NormalPost {
        return PostItem.NormalPost(
            postId = id?.toString() ?: "",
            userId = userId?.toString() ?: "",
            userName = userDetail?.name ?: "",
            petName = petDetail?.petName ?: "",
            userBadge = null,
            time = createdAt ?: "",
            caption = postTitle ?: "",
            description = address ?: "",
            hashtags = hashTag ?: "",
            media = MediaResponse(
                parentImageUrl = petDetail?.petImage ?: userDetail?.image ?: ""
            ),
            mediaList = postMedia?.map {
                PostMediaResponse(
                    mediaUrl = it.mediaPath ?: "",
                    type = it.mediaType ?: "",
                    thumbnailUrl = ""
                )
            } ?: emptyList(),
            type = postType ?: "OwnerPost",
            likes = postLikeCount ?: 0,
            comments = postCommentCount ?: 0,
            shares = postShareCount ?: 0,
            isLiked = itemSuccess?.isLiked ?: false,
            isSaved = itemSuccess?.isSaved ?: false,
            iAmFollowing = false,
            userType = userType ?: "",
            userPost = userPost?:false
        )
    }

    //  Merge function: API + Local
    private fun mergeApiWithLocal(apiPosts: List<PostItem>): List<PostItem> {
        return apiPosts.map { post ->

            val id = when (post) {
                is PostItem.NormalPost -> post.postId
                is PostItem.CommunityPost -> post.postId
                is PostItem.SponsoredPost -> post.postId
            } ?: return@map post

            val localState = localSavedState[id]

            when (post) {
                is PostItem.NormalPost ->
                    if (localState != null) post.copy(isSaved = localState) else post

                is PostItem.CommunityPost ->
                    if (localState != null) post.copy(isSaved = localState) else post

                is PostItem.SponsoredPost -> post
            }
        }
    }


    fun savePost(postId:String, eventId: String,
                 addId: String,onSuccess: () -> Unit = { }, onError: (String) -> Unit) {
        viewModelScope.launch {
            repository.savePost(
                userId = sessionManager.getUserId(),
                postId = postId,
                eventId = eventId,
                addId = addId
            ).collectLatest { result ->
                when (result) {
                    is Resource.Loading -> {
                        _uiState.value = _uiState.value.copy(isLoading = true)
                    }
                    is Resource.Success -> {
                        _uiState.value = _uiState.value.copy(isLoading = false)
                        result.data.let { response ->
                            if (response.success) {
                                onSuccess()
                            } else {
                                onError(response.message ?: "")
                            }
                        }
                    }
                    is Resource.Error -> {
                        _uiState.value = _uiState.value.copy(isLoading = false)
                        onError(result.message)
                    }
                    Resource.Idle -> Unit
                }
            }
        }

    }


    fun postLikeUnlike(postId:String,eventId: String,
                       addId: String, onSuccess: () -> Unit = { }, onError: (String) -> Unit) {
        viewModelScope.launch {
            repository.postLikeUnlike(
                userId = sessionManager.getUserId(),
                postId = postId,
                eventId = eventId,
                addId = addId
            ).collectLatest { result ->
                when (result) {
                    is Resource.Loading -> {
                        _uiState.value = _uiState.value.copy(isLoading = true)
                    }
                    is Resource.Success -> {
                        _uiState.value = _uiState.value.copy(isLoading = false)
                        result.data.let { response ->
                            if (response.success) {
                                onSuccess()
                            } else {
                                onError(response.message ?: "")
                            }
                        }
                    }
                    is Resource.Error -> {
                        _uiState.value = _uiState.value.copy(isLoading = false)
                        onError(result.message)
                    }
                    Resource.Idle -> Unit
                }
            }
        }

    }



    fun commentLike(commentId:String, onSuccess: () -> Unit = { }, onError: (String) -> Unit) {
        viewModelScope.launch {
            repository.commentLike(
                userId = sessionManager.getUserId(),
                commentId = commentId,
            ).collectLatest { result ->
                when (result) {
                    is Resource.Loading -> {
                        _uiState.value = _uiState.value.copy(isLoading = true)
                    }
                    is Resource.Success -> {
                        _uiState.value = _uiState.value.copy(isLoading = false)
                        result.data.let { response ->
                            if (response.success) {
                                toggleCommentLike(commentId.toInt())
                                onSuccess()
                            } else {
                                onError(response.message ?: "")
                            }
                        }
                    }
                    is Resource.Error -> {
                        _uiState.value = _uiState.value.copy(isLoading = false)
                        onError(result.message)
                    }
                    Resource.Idle -> Unit
                }
            }
        }

    }



    //  Updated toggleSave to update local memory also
    fun toggleSave(postId: String) {

        val currentLocal = localSavedState[postId] ?: false
        localSavedState[postId] = !currentLocal  // important

        _uiState.update { state ->
            val updated = state.posts.map { post ->

                when (post) {
                    is PostItem.NormalPost ->
                        if (post.postId == postId) post.copy(isSaved = !post.isSaved) else post

                    is PostItem.CommunityPost ->
                        if (post.postId == postId) post.copy(isSaved = !post.isSaved) else post

                    is PostItem.SponsoredPost -> post
                }
            }

            state.copy(posts = updated)
        }
    }

    fun toggleLike(postId: String) {
        //  Local memory (same pattern as save)
        val currentLocalLike = localLikeState[postId] ?: false
        localLikeState[postId] = !currentLocalLike

        _uiState.update { state ->
            val updated = state.posts.map { post ->

                when (post) {
                    is PostItem.NormalPost ->
                        if (post.postId == postId) {
                            post.copy(
                                isLiked = !post.isLiked,
                                likes = if (post.isLiked) post.likes - 1 else post.likes + 1
                            )
                        } else post

                    is PostItem.CommunityPost ->
                        if (post.postId == postId) {
                            post.copy(
                                isLiked = !post.isLiked,
                                likes = if (post.isLiked) post.likes - 1 else post.likes + 1
                            )
                        } else post

                    is PostItem.SponsoredPost -> if (post.postId == postId) {
                        post.copy(
                            isLiked = !post.isLiked,
                            likes = if (post.isLiked) post.likes - 1 else post.likes + 1
                        )
                    } else post
                }
            }

            state.copy(posts = updated)
        }
    }


    fun toggleCommentLike(commentId: Int) {

        _uiStateComment.update { state ->

            val updatedComments = state.comments.map { comment ->

                if (comment.id == commentId) {

                    val newIsLiked = !comment.isLikedByCurrentUser

                    comment.copy(
                        isLikedByCurrentUser = newIsLiked,
                        likeCount = if (newIsLiked) comment.likeCount + 1 else comment.likeCount - 1
                    )

                } else comment
            }

            state.copy(comments = updatedComments)
        }
    }

    fun increaseCommentCount(postId: String) {

        _uiState.update { state ->

            val updatedPosts = state.posts.map { post ->

                when (post) {

                    is PostItem.NormalPost ->
                        if (post.postId == postId) {
                            post.copy(comments = post.comments + 1)
                        } else post

                    is PostItem.CommunityPost ->
                        if (post.postId == postId) {
                            post.copy(comments = post.comments + 1)
                        } else post

                    is PostItem.SponsoredPost ->
                        if (post.postId == postId) {
                            post.copy(comments = post.comments + 1)
                        } else post
                }
            }

            state.copy(posts = updatedPosts)
        }
    }

    private fun reportPost(
        postId:String,
        reportReason:String,
        message:String,
        onSuccess: () -> Unit = { },
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            repository.reportPost(
                userId = sessionManager.getUserId(),
                postId = postId,
                reportReason = reportReason,
                message = message
            ).collectLatest { result ->
                when (result) {
                    is Resource.Loading -> {
                        _uiState.value = _uiState.value.copy(isLoading = true)
                    }
                    is Resource.Success -> {
                        _uiState.value = _uiState.value.copy(isLoading = false)
                        result.data.let { response ->
                            if (response.success) {
                                onSuccess()
                            } else {
                                onError(response.message ?: "")
                            }
                        }
                    }
                    is Resource.Error -> {
                        _uiState.value = _uiState.value.copy(isLoading = false)
                        onError(result.message)
                    }
                    Resource.Idle -> Unit
                }
            }
        }
    }

     fun addNewComment(
        postId:String,
        addId: String,
        commentText:String,
        onSuccess: () -> Unit = { },
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            repository.addNewComment(
                userId = sessionManager.getUserId(),
                postId = postId,
                addId = addId,
                commentText = commentText
            ).collectLatest { result ->
                when (result) {
                    is Resource.Loading -> {
                        _uiStateComment.value = _uiStateComment.value.copy(isLoading = true)
                    }
                    is Resource.Success -> {
                        _uiStateComment.value = _uiStateComment.value.copy(isLoading = false)
                        result.data.let { response ->
                            if (response.success) {
                                addCommentToList(commentText,response.data?.id?:0)
                                onSuccess()
                            } else {
                                onError(response.message ?: "")
                            }
                        }
                    }
                    is Resource.Error -> {
                        _uiStateComment.value = _uiStateComment.value.copy(isLoading = false)
                        onError(result.message)
                    }
                    Resource.Idle -> Unit
                }
            }
        }
    }

    fun replyComment(
        postId:String,
        commentId:String,
        commentText:String,
        onSuccess: () -> Unit = { },
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            repository.replyComment(
                userId = sessionManager.getUserId(),
                postId = postId,
                commentId = commentId,
                commentText = commentText
            ).collectLatest { result ->
                when (result) {
                    is Resource.Loading -> {
                        _uiStateComment.value = _uiStateComment.value.copy(isLoading = true)
                    }
                    is Resource.Success -> {
                        _uiStateComment.value = _uiStateComment.value.copy(isLoading = false)
                        result.data.let { response ->
                            if (response.success) {
                                addReplyToList(parentCommentId = response.data?.parentCommentId?.toInt()?:commentId.toInt(),
                                    replyText = response.data?.content?:commentText, replyId = response.data?.id?:0)
                                onSuccess()
                            } else {
                                onError(response.message ?: "")
                            }
                        }
                    }
                    is Resource.Error -> {
                        _uiStateComment.value = _uiStateComment.value.copy(isLoading = false)
                        onError(result.message)
                    }
                    Resource.Idle -> Unit
                }
            }
        }
    }


    // Add comment to top of UI list instantly
    private fun addCommentToList(commentText: String,commentId:Int) {
        val newComment = CommentItem(
            id = commentId, // temporary id
            content = commentText,
            createdAt = "Just now",
            user = CommentUser(
                id = sessionManager.getUserId().toInt(),
                name = sessionManager.getUserName(),
                image = sessionManager.getUserImage()
            ),
            likeCount = 0,
            isLikedByCurrentUser = false,
            replies = emptyList()
        )

        _uiStateComment.update { state ->
            state.copy(
                comments = listOf(newComment) + state.comments
            )
        }
    }

    // Add reply to a specific comment instantly
    private fun addReplyToList(parentCommentId: Int, replyText: String, replyId: Int) {
        val newReply = CommentReply(
            id = replyId, // temporary id
            content = replyText,
            createdAt = "Just now",
            user = CommentUser(
                id = sessionManager.getUserId().toInt(),
                name = sessionManager.getUserName(),
                image = sessionManager.getUserImage()
            ),
            likeCount = 0,
            isLikedByCurrentUser = false
        )

        _uiStateComment.update { state ->
            val updatedComments = state.comments.map { comment ->
                if (comment.id == parentCommentId) {
                    comment.copy(
                        replies = comment.replies + newReply
                    )
                } else comment
            }

            state.copy(comments = updatedComments)
        }
    }


    fun clearComments() {
        _uiStateComment.value = uiStateComment.value.copy(
            comments = emptyList()
        )
    }

    private fun deleteComment(commentId: Int) {
        _uiStateComment.update { state ->
            val updatedComments = state.comments.filterNot { it.id == commentId }
            state.copy(comments = updatedComments)
        }
    }

    private fun decreaseCommentCount(postId: String) {
        _uiState.update { state ->
            val updatedPosts = state.posts.map { post ->

                when (post) {
                    is PostItem.NormalPost -> if (post.postId == postId)
                        post.copy(comments = maxOf(0, post.comments - 1))
                    else post

                    is PostItem.CommunityPost -> if (post.postId == postId)
                        post.copy(comments = maxOf(0, post.comments - 1))
                    else post

                    is PostItem.SponsoredPost -> if (post.postId == postId)
                        post.copy(comments = maxOf(0, post.comments - 1))
                    else post
                }
            }

            state.copy(posts = updatedPosts)
        }
    }


    fun deleteComment(
        commentId:String,
        onSuccess: () -> Unit = { },
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            repository.deleteComment(
                userId = sessionManager.getUserId(),
                commentId = commentId,
            ).collectLatest { result ->
                when (result) {
                    is Resource.Loading -> {
                        _uiStateComment.value = _uiStateComment.value.copy(isLoading = true)
                    }
                    is Resource.Success -> {
                        _uiStateComment.value = _uiStateComment.value.copy(isLoading = false)
                        result.data.let { response ->
                            if (response.success) {
                                deleteComment(commentId = commentId.toInt())
                                decreaseCommentCount(postId = uiState.value.postId)
                                onSuccess()
                            } else {
                                onError(response.message ?: "")
                            }
                        }
                    }
                    is Resource.Error -> {
                        _uiStateComment.value = _uiStateComment.value.copy(isLoading = false)
                        onError(result.message)
                    }
                    Resource.Idle -> Unit
                }
            }
        }
    }


    fun deletePost(postId: Int) {
        _uiState.update { state ->

            val updatedList = state.posts.filterNot { post ->

                when (post) {
                    is PostItem.NormalPost -> post.postId.toIntOrNull() == postId
                    else -> false
                }
            }

            state.copy(posts = updatedList)
        }
    }


    fun deletePost(
        postId:String,
        onSuccess: () -> Unit = { },
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            repository.deletePost(
                userId = sessionManager.getUserId(),
                postId = postId,
            ).collectLatest { result ->
                when (result) {
                    is Resource.Loading -> {
                        _uiStateComment.value = _uiStateComment.value.copy(isLoading = true)
                    }
                    is Resource.Success -> {
                        _uiStateComment.value = _uiStateComment.value.copy(isLoading = false)
                        result.data.let { response ->
                            if (response.success) {
                                deletePost(postId = postId.toInt())
                                onSuccess()
                            } else {
                                onError(response.message ?: "")
                            }
                        }
                    }
                    is Resource.Error -> {
                        _uiStateComment.value = _uiStateComment.value.copy(isLoading = false)
                        onError(result.message)
                    }
                    Resource.Idle -> Unit
                }
            }
        }
    }


    private fun updateEditedCommentInUI(commentId: String, newText: String) {
        _uiStateComment.update { state ->
            state.copy(
                comments = state.comments.map { comment ->
                    if (comment.id == commentId.toInt()) {
                        comment.copy(content = newText)
                    } else comment
                }
            )
        }
    }


    @OptIn(UnstableApi::class)
    fun addAndRemoveFollowers(
        followedId: String,
        onError: (String) -> Unit
    ) {

        //  Optimistic UI update (toggle + start loader)
        _uiState.update { state ->
            state.copy(
                posts = state.posts.map { post ->
                    when (post) {

                        is PostItem.NormalPost ->
                            if (post.userId == followedId) {
                                post.copy(
                                    iAmFollowing = !post.iAmFollowing,
                                    isFollowLoading = true
                                )
                            } else post

                        is PostItem.CommunityPost ->
                            if (post.userId == followedId) {
                                post.copy(
                                    iAmFollowing = !post.iAmFollowing,
                                    isFollowLoading = true
                                )
                            } else post

                        is PostItem.SponsoredPost ->
                            post
                    }
                }
            )
        }

        viewModelScope.launch {
            repository.addAndRemoveFollowers(
                userId = sessionManager.getUserId(),
                followerId = followedId
            ).collectLatest { result ->
                when (result) {

                    is Resource.Success -> {
                        if (result.data.success) {
                            //API success → stop loader ONLY
                            _uiState.update { state ->
                                state.copy(
                                    posts = state.posts.map { post ->
                                        if (post.stableKey == followedId && post is PostItem.CommunityPost) {
                                            post.copy(isFollowLoading = false)
                                        } else post
                                    }
                                )
                            }
                        } else {
                            rollbackFollow(followedId)
                            onError(result.data.message ?: "Something went wrong")
                        }
                    }

                    is Resource.Error -> {
                        rollbackFollow(followedId)
                        onError(result.message)
                    }

                    else -> Unit
                }
            }
        }
    }

    private fun rollbackFollow(followedId: String) {
        _uiState.update { state ->
            state.copy(
                posts = state.posts.map { post ->
                    if (post.stableKey == followedId && post is PostItem.CommunityPost) {
                        post.copy(
                            iAmFollowing = !post.iAmFollowing,
                            isFollowLoading = false
                        )
                    } else post
                }
            )
        }
    }


    fun editComment(
        commentId:String,
        commenText:String,
        onSuccess: () -> Unit = { },
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            repository.editComment(
                userId = sessionManager.getUserId(),
                commentId = commentId,
                commenText = commenText
            ).collectLatest { result ->
                when (result) {
                    is Resource.Loading -> {
                        _uiStateComment.value = _uiStateComment.value.copy(isLoading = true)
                    }
                    is Resource.Success -> {
                        _uiStateComment.value = _uiStateComment.value.copy(isLoading = false)
                        result.data.let { response ->
                            if (response.success) {
                                // ️ SINGLE FUNCTION CALL FOR UI UPDATE
                                updateEditedCommentInUI(commentId, commenText)
                                onSuccess()
                            } else {
                                onError(response.message ?: "")
                            }
                        }
                    }
                    is Resource.Error -> {
                        _uiStateComment.value = _uiStateComment.value.copy(isLoading = false)
                        onError(result.message)
                    }
                    Resource.Idle -> Unit
                }
            }
        }
    }

    fun getComments(
        postId: String,
        addId: String,
        page: Int,
        limit: Int,
        onSuccess: () -> Unit = {},
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {

            repository.getComments(
                userId = sessionManager.getUserId(),
                postId = postId,
                addId = addId,
                page = page.toString(),
                limit = limit.toString()
            ).collectLatest { result ->

                when (result) {

                    is Resource.Loading -> {
                        _uiStateComment.update { state ->
                            state.copy(
                                isLoading = page == 1,
                                isLoadingMore = page > 1
                            )
                        }
                    }

                    is Resource.Success -> {
                        val response = result.data

                        if (!response.success) {
                            _uiStateComment.update { it.copy(isLoading = false, isLoadingMore = false) }
                            onError(response.message ?: "Something went wrong")
                            return@collectLatest
                        }

                        val apiData = response.data

                        val newComments = apiData?.data?.map { api ->
                            CommentItem(
                                id = api.id ?: 0,
                                content = api.content ?: "",
                                createdAt = api.createdAt ?: "",
                                user = CommentUser(
                                    id = api.user?.id ?: 0,
                                    name = api.user?.name ?: "",
                                    image = api.user?.image ?: ""
                                ),
                                likeCount = api.likeCount ?: 0,
                                isLikedByCurrentUser = api.isLikedByCurrentUser ?: false,
                                replies = api.replies
                                    ?.map { reply ->
                                        CommentReply(
                                            id = reply?.id,
                                            content = reply?.content,
                                            createdAt = reply?.createdAt,
                                            user = reply?.user?.let { u ->
                                                CommentUser(
                                                    id = u.id ?: 0,
                                                    name = u.name ?: "",
                                                    image = u.image ?: ""
                                                )
                                            },
                                            likeCount = reply?.likeCount,
                                            isLikedByCurrentUser = reply?.isLikedByCurrentUser
                                        )
                                    }
                                    ?: emptyList()
                            )
                        }

                        val isLastPage = apiData?.totalPage == page

                        _uiStateComment.update { state ->
                            state.copy(
                                comments = if (page == 1) {
                                    newComments.orEmpty()        // page 1 → reset list
                                } else {
                                    state.comments + newComments.orEmpty()   // append safely
                                },
                                currentPage = page,
                                isLastPage = isLastPage,
                                isLoading = false,
                                isLoadingMore = false
                            )
                        }

                        onSuccess()
                    }

                    is Resource.Error -> {
                        _uiStateComment.update { it.copy(isLoading = false, isLoadingMore = false) }
                        onError(result.message)
                    }

                    Resource.Idle -> Unit
                }
            }
        }
    }



    private fun initWelcomeDialog() {
        val userType = sessionManager.getUserType()

        val title = if (userType == UserType.Owner) {
            "Welcome to SloDoggies!"
        } else "Welcome to the Pack!"

        val description = if (userType == UserType.Owner) {
            "You're officially part of the SloDoggies pack! Let's keep the tail wagging!"
        } else {
            "Your business profile is all set. Start engaging with users, managing leads, and promoting your services!"
        }

        val button = "Get Started"

        _uiState.update {
            it.copy(
                showWelcomeDialog = true,
                welcomeTitle = title,
                welcomeDescription = description,
                welcomeButton = button
            )
        }
    }

    fun toggleCommentDialog(){
        _uiState.update { it.copy(showCommentsDialog = !it.showCommentsDialog) }
    }

    fun showContinueAddPetDialog() {
        _uiState.update {
            it.copy(
                showPetInfoDialog = false,
                showContinueAddPetDialog = true
            )
        }
    }

    fun dismissWelcomeDialog() {
        _uiState.update {
            it.copy(
                showWelcomeDialog = false,
                showPetInfoDialog = sessionManager.getUserType() == UserType.Owner
            )
        }
    }

    fun onReasonSelected(reason: String) {
        _uiState.value = _uiState.value.copy(selectedReason = reason)
    }

    fun onMessageChange(text: String) {
        _uiState.value = _uiState.value.copy(message = text)
    }

    fun onWelcomeSubmit() {
        dismissWelcomeDialog()
    }

    fun onUserDetailsSubmit() {
        _uiState.update {
            it.copy(
                showUserDetailsDialog = false,
                showProfileCreatedDialog = true
            )
        }
        sessionManager.clearSignupFlow()
    }

    fun showDeleteDialog(){
        _uiState.update { it.copy(deleteDialog = true) }
    }

    fun dismissDeleteDialog(){
        _uiState.update { it.copy(deleteDialog = false) }
    }

    fun showPetInfoDialog() {
        _uiState.update { it.copy(showPetInfoDialog = true) }
    }

    fun showReportDialog(postId: String) {
        _uiState.update {
            it.copy(showReportDialog = true,
                postId = postId)
        }
    }

    fun updatePostId(postId: String) {
        _uiState.update {
            it.copy(postId = postId)
        }
    }

    fun updateUostId(userId: String) {
        _uiState.update {
            it.copy(userId = userId)
        }
    }

    fun updateCommentId(postId: String) {
        _uiStateComment.update {
            it.copy(commentsId = postId.toInt())
        }
    }

    fun showShareContent() {
        _uiState.update { it.copy(showShareContent = true) }
    }

    fun dismissShareContent() {
        _uiState.update { it.copy(showShareContent = false) }
    }

    fun dismissReportDialog() {
        _uiState.update { it.copy(showReportDialog = false) }
    }

    fun dismissPetInfoDialog() {
        _uiState.update {
            it.copy(
                showPetInfoDialog = false,
                showUserDetailsDialog = true
            )
        }
    }

    fun dismissUserDetailsDialog() {
        _uiState.update { it.copy(showUserDetailsDialog = false) }
        sessionManager.clearSignupFlow()
    }

    fun dismissProfileCreatedDialog() {
        _uiState.update { it.copy(showProfileCreatedDialog = false) }
        sessionManager.clearSignupFlow()
    }

    fun dismissContinueAddPetDialog() {
        _uiState.update {
            it.copy(
                showContinueAddPetDialog = false,
                showUserDetailsDialog = true
            )
        }
    }

    fun showReportToast(context:Context) {
        reportPost(uiState.value.postId, uiState.value.selectedReason,
            uiState.value.message,
            onSuccess = {
                _uiState.update {
                    it.copy(
                        showReportDialog = false,
                        showReportToast = true
                    )
                }
            },  onError = { msg -> Toast.makeText(context, msg, Toast.LENGTH_SHORT).show() }
        )
    }

    fun dismissReportToast() {
        _uiState.update { it.copy(showReportToast = false) }
    }

    fun onVerify(navController: NavHostController, type: String, data: String) {
        navController.navigate("${Routes.VERIFY_ACCOUNT_SCREEN}?type=$type&data=$data")
    }

    private val _petInfoDialogCount = MutableStateFlow(0)
    val petInfoDialogCount: StateFlow<Int> = _petInfoDialogCount

    fun incrementPetInfoDialogCount() {
        _uiState.update { it.copy(petInfoDialogCount = it.petInfoDialogCount + 1) }
    }

    fun resetPetInfoDialogCount() {
        _uiState.update { it.copy(petInfoDialogCount = 0) }
    }


}
