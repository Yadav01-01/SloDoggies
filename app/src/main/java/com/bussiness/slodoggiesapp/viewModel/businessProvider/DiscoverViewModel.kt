package com.bussiness.slodoggiesapp.viewModel.businessProvider

import androidx.annotation.OptIn
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.util.UnstableApi
import com.bussiness.slodoggiesapp.data.newModel.discover.PetPlaceItem
import com.bussiness.slodoggiesapp.data.newModel.home.HomeFeedMapper
import com.bussiness.slodoggiesapp.data.newModel.home.PostItem
import com.bussiness.slodoggiesapp.data.remote.Repository
import com.bussiness.slodoggiesapp.data.uiState.CommentItem
import com.bussiness.slodoggiesapp.data.uiState.CommentReply
import com.bussiness.slodoggiesapp.data.uiState.CommentUser
import com.bussiness.slodoggiesapp.data.uiState.CommentsUiState
import com.bussiness.slodoggiesapp.data.uiState.DiscoverUiState
import com.bussiness.slodoggiesapp.network.Resource
import com.bussiness.slodoggiesapp.util.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiscoverViewModel @Inject constructor(
    private val repository: Repository,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(DiscoverUiState())
    val uiState: StateFlow<DiscoverUiState> = _uiState

    private var isRequestRunning = false
    private var currentPage = 1
    private var isLastPage = false
    private val localSavedState = mutableMapOf<String, Boolean>()
    private val localLikeState = mutableMapOf<String, Boolean>()
    private val searchQuery = MutableStateFlow("")
    private val _uiStateComment = MutableStateFlow(CommentsUiState())
    val uiStateComment: StateFlow<CommentsUiState> = _uiStateComment


    init {
        loadHashtags()
        loadForSelectedCategory()
        observeSearchQuery()
    }


    // ---------------------------------------------------------
    //  CATEGORY CHANGE HANDLER
    // ---------------------------------------------------------


    @kotlin.OptIn(FlowPreview::class)
    private fun observeSearchQuery() {
        viewModelScope.launch {
            searchQuery
                .debounce(400)
                .distinctUntilChanged()
                .collectLatest {

                    _uiState.update {
                        it.copy(
                            page = 1,
                            isLastPage = false,
                            posts = emptyList(),
                            pets = emptyList()
                        )
                    }

                    currentPage = 1
                    isLastPage = false
                    isRequestRunning = false

                    loadForSelectedCategory()
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

    fun selectCategory(category: String) {
        _uiState.update { it.copy(selectedCategory = category) }
        resetPagination()
        loadForSelectedCategory()
    }

    private fun loadForSelectedCategory() {
        when (uiState.value.selectedCategory) {
            "Pets Near You" -> loadPetNearMe(isFirstPage = true)
            "Pet Places" -> getPetPlaces(isFirstPage = true)
            "Activities" -> discoverActivities(isFirstPage = true)
            "Events" -> discoverEvents(isFirstPage = true)
        }
    }

    // ---------------------------------------------------------
    //  Pagination
    // ---------------------------------------------------------

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


    private fun resetPagination() {
        _uiState.update { it.copy(page = 1, isLastPage = false) }
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


    fun loadNextPage() {
        val state = uiState.value
        if (state.isLastPage || isRequestRunning) return

        when (state.selectedCategory) {
            "Pets Near You" -> loadPetNearMe(isFirstPage = false)
            "Activities"    -> discoverActivities(isFirstPage = false)
            "Events"        -> discoverEvents(isFirstPage = false)
            "Pet Places"    -> getPetPlaces(isFirstPage = false)
        }
    }

    // ---------------------------------------------------------
    //  Pets Near Me
    // ---------------------------------------------------------
    private fun loadPetNearMe(isFirstPage: Boolean) {
        if (isRequestRunning) return
        isRequestRunning = true

        val state = uiState.value

        _uiState.update {
            it.copy(
                isLoading = isFirstPage,
                isLoadingMore = !isFirstPage,
                error = null
            )
        }

        viewModelScope.launch {
            repository.petNearMe(
                userId = sessionManager.getUserId(),
                lat = "",
                long = "",
                page = state.page.toString(),
                limit = "10",
                search = state.query
            ).collectLatest { result ->

                when (result) {
                    is Resource.Success -> {
                        val newPets = result.data.data?.pets.orEmpty()
                        val merged = if (isFirstPage) newPets else state.pets + newPets

                        val totalPages = result.data.data?.total ?: 1
                        val nextPage = state.page + 1
                        val last = nextPage > totalPages

                        _uiState.update {
                            it.copy(
                                pets = merged,
                                isLoading = false,
                                isLoadingMore = false,
                                page = if (!last) nextPage else state.page,
                                isLastPage = last
                            )
                        }
                    }

                    is Resource.Error -> showError(result.message)
                    else -> Unit
                }

                isRequestRunning = false
            }
        }
    }
    fun commentLike(commentId:String,
                    onSuccess: () -> Unit = { },
                    onError: (String) -> Unit) {
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


    // ---------------------------------------------------------
    //  Discover Activities
    // ---------------------------------------------------------
    private fun discoverActivities(isFirstPage: Boolean) {
        if (isRequestRunning) return
        isRequestRunning = true

        val state = uiState.value

        _uiState.update {
            it.copy(
                isLoading = isFirstPage,
                isLoadingMore = !isFirstPage,
                error = null
            )
        }

        viewModelScope.launch {
            repository.discoverActivities(
                sessionManager.getUserId(),
                page = state.page.toString(),
                limit = "10",
                search = state.query
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
                                error = ""
                            )
                        }

                        val totalPages = response?.totalPage ?: 1
                        isLastPage = currentPage >= totalPages
                        if (!isLastPage) currentPage++
                    }

                    is Resource.Error -> {
                        _uiState.update { state ->
                            state.copy(
                                error = result.message ?: "Something went wrong",
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

    // ---------------------------------------------------------
    //  Events
    // ---------------------------------------------------------\


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



    private fun discoverEvents(isFirstPage: Boolean) {
        if (isRequestRunning) return
        isRequestRunning = true

        val state = uiState.value

        _uiState.update {
            it.copy(
                isLoading = isFirstPage,
                isLoadingMore = !isFirstPage,
                error = null
            )
        }

        viewModelScope.launch {
            repository.discoverEvents(
                sessionManager.getUserId(),
                page = state.page.toString(),
                limit = "10",
                search = state.query,
                userType = sessionManager.getUserType().toString()
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
                                error = ""
                            )
                        }

                        val totalPages = response?.totalPage ?: 1
                        isLastPage = currentPage >= totalPages
                        if (!isLastPage) currentPage++
                    }

                    is Resource.Error -> {
                        _uiState.update { state ->
                            state.copy(
                                error = result.message,
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

    // ---------------------------------------------------------
    //  Hashtags
    // ---------------------------------------------------------
    private fun loadHashtags() {
        viewModelScope.launch {
            repository.trendingHashtags().collectLatest { result ->
                when (result) {
                    is Resource.Success -> _uiState.update { it.copy(hashtags = result.data.data) }

                    is Resource.Error -> showError(result.message)

                    else -> Unit
                }
            }
        }
    }

    fun savePost(postId:String, onSuccess: () -> Unit ) {
        viewModelScope.launch {
            repository.savePost(
                userId = sessionManager.getUserId(),
                postId = postId,
                eventId = "",
                addId = "",
            ).collectLatest { result ->
                when (result) {
                    is Resource.Loading -> {
                        _uiState.value = _uiState.value.copy(isLoading = true)
                    }
                    is Resource.Success -> {
                        _uiState.value = _uiState.value.copy(isLoading = false)
                        result.data.let { response ->
                            if (response.success) {
                                toggleSave(postId)
                                onSuccess()
                            } else {
                                _uiState.value = _uiState.value.copy(isLoading = true, error = result.data.message)
                            }
                        }
                    }
                    is Resource.Error -> {
                        _uiState.value = _uiState.value.copy(isLoading = false, error = result.message)
                    }
                    Resource.Idle -> Unit
                }
            }
        }
    }

    fun updatePostId(postId: String) {
        _uiState.update {
            it.copy(postId = postId)
        }
    }
    fun postLikeUnlike(postId:String, type:String ="normal", onSuccess: () -> Unit = { }) {
        viewModelScope.launch {
            repository.postLikeUnlike(
                userId = sessionManager.getUserId(),
                postId = if(!type.equals("normal"))postId else "",
                eventId = if(type.equals("normal"))postId else "",
                addId = "",
            ).collectLatest { result ->
                when (result) {
                    is Resource.Loading -> {
                        _uiState.value = _uiState.value.copy(isLoading = true)
                    }
                    is Resource.Success -> {
                        _uiState.value = _uiState.value.copy(isLoading = false)
                        result.data.let { response ->
                            if (response.success) {
                                toggleLike(postId)
                                onSuccess()
                            } else {
                                _uiState.value = _uiState.value.copy(isLoading = false, error = response.message)
                            }
                        }
                    }
                    is Resource.Error -> {
                        _uiState.value = _uiState.value.copy(isLoading = false, error = result.message)
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

    private fun toggleLike(postId: String) {
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

    // Toggle Follow / Unfollow
    fun addAndRemoveFollowers(
        followedId: String,
        onError: (String) -> Unit
    ) {
        //  Optimistic UI update (toggle + start loader)
        _uiState.update { state ->
            state.copy(
                posts = state.posts.map { post ->
                    when (post) {
                        is PostItem.CommunityPost ->
                            if (post.userId == followedId) {
                                post.copy(
                                    iAmFollowing = !post.iAmFollowing,
                                    isFollowLoading = true
                                )
                            } else post

                        is PostItem.SponsoredPost -> post

                        is PostItem.NormalPost -> post
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

    private fun getPetPlaces(isFirstPage: Boolean = true){
        if (isRequestRunning) return
        isRequestRunning = true

        _uiState.update {
            it.copy(
                isLoading = isFirstPage,
                isLoadingMore = !isFirstPage,
                error = null
            )
        }
        viewModelScope.launch {
            repository.discoverPetPlaces(sessionManager.getUserId(),uiState.value.query).collectLatest { result ->
                when (result) {
                    is Resource.Success -> {
                        _uiState.value = _uiState.value.copy(petPlaces = result.data.data.petPlaces, isLoading = false)
                    }
                    is Resource.Loading -> {
                        _uiState.value = _uiState.value.copy(isLoading = true)
                    }
                    is Resource.Error -> {
                        _uiState.value = _uiState.value.copy(isLoading = false, error = result.message)
                    }
                    Resource.Idle -> {

                    }
                }

                isRequestRunning = false
            }
        }
    }

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

    // ---------------------------------------------------------
    // Error helper
    // ---------------------------------------------------------
    private fun showError(msg: String?) {
        _uiState.update {
            it.copy(
                isLoading = false,
                isLoadingMore = false,
                error = msg
            )
        }
    }

    // ---------------------------------------------------------
    //  UI Dialogs
    // ---------------------------------------------------------

    fun onHashtagSelected(tag: String) {
        updateQuery(tag)
    }


    fun updateQuery(newQuery: String) {
        _uiState.update {
            it.copy(query = newQuery)
        }
        searchQuery.value = newQuery
    }


    fun showPetPlaceDialog(show: Boolean) =
        _uiState.update { it.copy(showPetPlaceDialog = show) }

    fun showShareContent(show: Boolean) =
        _uiState.update { it.copy(showShareContentDialog = show) }

    fun showSavedDialog(show: Boolean) =
        _uiState.update { it.copy(showSavedDialog = show) }

    fun showReportDialog(show: Boolean) =
        _uiState.update { it.copy(showReportDialog = show) }

    fun showReportToast() =
        _uiState.update { it.copy(showReportDialog = false, showReportToast = true) }

    fun dismissReportToast() =
        _uiState.update { it.copy(showReportToast = false) }

    fun dismissPetPlaceDialog() =
        _uiState.update { it.copy(
            showPetPlaceDialog = false,
            selectedPetPlace = null) }

    fun dismissSavedDialog() =
        _uiState.update { it.copy(showSavedDialog = false) }


    fun clearComments() {
        _uiStateComment.value = uiStateComment.value.copy(
            comments = emptyList()
        )
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
                                isLoading = false,
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

    fun onPetPlaceClicked(item: PetPlaceItem) {
        _uiState.update {
            it.copy(
                selectedPetPlace = item,
                showPetPlaceDialog = true
            )
        }
    }



}
