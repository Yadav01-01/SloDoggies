package com.bussiness.slodoggiesapp.viewModel.businessProvider

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bussiness.slodoggiesapp.data.newModel.home.HomeFeedMapper
import com.bussiness.slodoggiesapp.data.newModel.home.PostItem
import com.bussiness.slodoggiesapp.data.remote.Repository
import com.bussiness.slodoggiesapp.data.uiState.DiscoverUiState
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

    init {
        loadHashtags()
        loadForSelectedCategory()   // Only load according to default category
    }

    // ---------------------------------------------------------
    //  CATEGORY CHANGE HANDLER
    // ---------------------------------------------------------
    fun selectCategory(category: String) {
        _uiState.update { it.copy(selectedCategory = category) }
        resetPagination()
        loadForSelectedCategory()
    }

    private fun loadForSelectedCategory() {
        when (uiState.value.selectedCategory) {
            "Pets Near You" -> loadPetNearMe(isFirstPage = true)
            "Pet Places" -> {}  // no API yet
            "Activities" -> discoverActivities(isFirstPage = true)
            "Events" -> discoverEvents(isFirstPage = true)
        }
    }

    // ---------------------------------------------------------
    //  Pagination
    // ---------------------------------------------------------

    private fun resetPagination() {
        _uiState.update { it.copy(page = 1, isLastPage = false) }
    }

    fun loadNextPage() {
        val state = uiState.value
        if (state.isLastPage || isRequestRunning) return

        when (state.selectedCategory) {
            "Pets Near You" -> loadPetNearMe(isFirstPage = false)
            "Activities" -> discoverActivities(isFirstPage = false)
            "Events" -> discoverEvents(isFirstPage = false)
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
    // ---------------------------------------------------------
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

    fun postLikeUnlike(postId:String, onSuccess: () -> Unit = { }) {
        viewModelScope.launch {
            repository.postLikeUnlike(
                userId = postId,
                postId = sessionManager.getUserId(),
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
    private fun toggleSave(postId: String) {

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

    fun updateQuery(newQuery: String) {
        _uiState.update { it.copy(query = newQuery) }
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
        _uiState.update { it.copy(showPetPlaceDialog = false) }

    fun dismissSavedDialog() =
        _uiState.update { it.copy(showSavedDialog = false) }
}
