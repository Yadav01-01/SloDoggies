package com.bussiness.slodoggiesapp.viewModel.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bussiness.slodoggiesapp.data.model.common.AudienceItem
import com.bussiness.slodoggiesapp.data.newModel.followerresponse.FollowerItem
import com.bussiness.slodoggiesapp.data.newModel.followerresponse.FollowingItem
import com.bussiness.slodoggiesapp.data.remote.Repository
import com.bussiness.slodoggiesapp.data.uiState.FollowerFollowingUiState
import com.bussiness.slodoggiesapp.network.Resource
import com.bussiness.slodoggiesapp.util.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FollowerFollowingViewModel @Inject constructor(
    private val repository: Repository,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(FollowerFollowingUiState())
    val uiState: StateFlow<FollowerFollowingUiState> = _uiState.asStateFlow()

    // Query entered by the user (debounced)
    private val searchQuery = MutableStateFlow("")

    // Pagination states
    private var followerPage = 1
    private var followerTotalPages = 1
    private var followerLoadingMore = false

    private var followingPage = 1
    private var followingTotalPages = 1
    private var followingLoadingMore = false
    private var hasUserTyped = false
    private var currentUserId: String? = null

    init {
        observeSearchInput()
    }

    fun setProfileUser(userId: String?) {
        currentUserId = userId?.takeIf { it.isNotBlank() }
    }

    fun loadMyFollowers(reset: Boolean = false) {
        loadFollowersInternal(
            userId = sessionManager.getUserId(), reset = reset
        )
    }

    fun loadOtherUserFollowers(userId: String, reset: Boolean = false) {

        loadFollowersInternal(
            userId = userId,
            reset = reset
        )

    }

    fun loadMyFollowing(reset: Boolean = false) {
        loadFollowingInternal(userId = sessionManager.getUserId(), reset = reset)
    }

    fun loadOtherUserFollowing(userId: String, reset: Boolean = false) {
        loadFollowingInternal(
            userId = userId,
            reset = reset
        )
    }

    fun onSearchQueryChange(query: String) {
        hasUserTyped = true
        searchQuery.value = query
        _uiState.update { it.copy(query = query) }
    }

    private fun loadByTab(reset: Boolean) {
        val id = currentUserId ?: sessionManager.getUserId()

        when (uiState.value.selectedOption) {
            TAB_FOLLOWER -> loadFollowersInternal(id, reset)
            TAB_FOLLOWING -> loadFollowingInternal(id, reset)
        }
    }



    @OptIn(FlowPreview::class)
    private fun observeSearchInput() {
        viewModelScope.launch {
            searchQuery
                .debounce(500)
                .distinctUntilChanged()
                .collectLatest {
                    if (!hasUserTyped) return@collectLatest
                    loadByTab(reset = true)
                }
        }
    }


    fun refresh(userId: String? = null) {
        when (uiState.value.selectedOption) {
            TAB_FOLLOWER -> {
                if (userId.isNullOrBlank()) loadMyFollowers(true)
                else loadOtherUserFollowers(userId, true)
            }

            TAB_FOLLOWING -> {
                if (userId.isNullOrBlank()) loadMyFollowing(true)
                else loadOtherUserFollowing(userId, true)
            }
        }
    }


    // When user switches tab
    fun updateSelectedOption(type: String) {

        _uiState.update {
            it.copy(selectedOption = type)
        }

        when (type) {
            TAB_FOLLOWER -> {
                if (uiState.value.followers.isEmpty()) {
                    loadMyFollowers(reset = true)
                }
            }

            TAB_FOLLOWING -> {
                if (uiState.value.following.isEmpty()) {
                    loadMyFollowing(reset = true)
                }
            }
        }
    }

    private fun canLoadMore(
        loading: Boolean,
        page: Int,
        totalPages: Int
    ): Boolean = !loading && page <= totalPages



    private fun FollowerItem.toAudienceItem() = AudienceItem(
        id = id,
        name = name.orEmpty(),
        profilePic = profilePic.orEmpty(),
        isVerified = isVerified ?: false,
        isFollowing = isFollowing ?: false
    )

    private fun FollowingItem.toAudienceItem() = AudienceItem(
        id = id,
        name = name.orEmpty(),
        profilePic = profilePic.orEmpty(),
        isVerified = isVerified ?: false,
        isFollowingMe = isFollowingMe ?: false
    )

    private fun loadFollowersInternal(
        userId: String,
        reset: Boolean
    ) {
        if (reset) {
            followerPage = 1
            followerTotalPages = 1
            _uiState.update {
                it.copy(
                    followers = emptyList(),
                    isRefreshing = true,
                    isMoreLoading = false,
                    endReached = false,
                    isEmptyData = false
                )
            }
        }

        if (!canLoadMore(followerLoadingMore, followerPage, followerTotalPages)) return

        followerLoadingMore = true
        _uiState.update { it.copy(isMoreLoading = !reset) }

        viewModelScope.launch {
            repository.getFollowerList(
                userId = userId,
                page = followerPage.toString(),
                limit = PAGE_LIMIT,
                search = searchQuery.value
            ).collectLatest { result ->

                when (result) {
                    is Resource.Success -> {
                        val response = result.data.data
                        val newData = response?.followers
                            ?.map { it.toAudienceItem() }
                            .orEmpty()

                        followerTotalPages = response?.totalPage ?: 1

                        _uiState.update {
                            it.copy(
                                followers = if (reset) newData else it.followers + newData,
                                isRefreshing = false,
                                isMoreLoading = false,
                                endReached = followerPage >= followerTotalPages,
                                isEmptyData = reset && newData.isEmpty(),
                                totalFollower = response?.totalFollowers?.toString().orEmpty(),
                                totalFollowing = response?.totalFollowing?.toString().orEmpty(),
                                error = null
                            )
                        }

                        followerPage++
                    }

                    is Resource.Error -> {
                        _uiState.update {
                            it.copy(
                                isRefreshing = false,
                                isMoreLoading = false,
                                error = result.message ?: "Something went wrong",
                                isEmptyData = reset
                            )
                        }
                    }

                    else -> Unit
                }

                followerLoadingMore = false
            }
        }
    }


    private fun loadFollowingInternal(
        userId: String,
        reset: Boolean
    ) {
        if (reset) {
            followingPage = 1
            followingTotalPages = 1
            _uiState.update {
                it.copy(
                    following = emptyList(),
                    isRefreshing = true,
                    isMoreLoading = false,
                    endReached = false,
                    isEmptyData = false
                )
            }
        }

        if (!canLoadMore(followingLoadingMore, followingPage, followingTotalPages)) return

        followingLoadingMore = true
        _uiState.update { it.copy(isMoreLoading = !reset) }

        viewModelScope.launch {
            repository.getFollowingList(
                userId = userId,
                page = followingPage.toString(),
                limit = PAGE_LIMIT,
                search = searchQuery.value
            ).collectLatest { result ->

                when (result) {
                    is Resource.Success -> {
                        val response = result.data.data
                        val newData = response?.data
                            ?.map { it.toAudienceItem() }
                            .orEmpty()

                        followingTotalPages = response?.totalPage ?: 1

                        _uiState.update {
                            it.copy(
                                following = if (reset) newData else it.following + newData,
                                isRefreshing = false,
                                isMoreLoading = false,
                                endReached = followingPage >= followingTotalPages,
                                isEmptyData = reset && newData.isEmpty(),
                                totalFollower = response?.totalFollower?.toString().orEmpty(),
                                totalFollowing = response?.totalFollowing?.toString().orEmpty(),
                                error = null
                            )
                        }

                        followingPage++
                    }

                    is Resource.Error -> {
                        _uiState.update {
                            it.copy(
                                isRefreshing = false,
                                isMoreLoading = false,
                                error = result.message ?: "Something went wrong",
                                isEmptyData = reset
                            )
                        }
                    }

                    else -> Unit
                }

                followingLoadingMore = false
            }
        }
    }

    // Toggle Follow / Unfollow
    fun addAndRemoveFollowers(followedId: String) {
        viewModelScope.launch {
            repository.addAndRemoveFollowers(
                sessionManager.getUserId(),
                followedId
            ).collectLatest { result ->

                when (result) {
                    is Resource.Success -> {

                        _uiState.update { state ->
                            val updatedList = state.followers.map { item ->
                                if (item.id.toString() == followedId) {
                                    item.copy(isFollowing = !item.isFollowing)
                                } else item
                            }
                            state.copy(followers = updatedList)
                        }
                    }

                    is Resource.Error -> {
                        _uiState.update {
                            it.copy(error = result.message ?: "Something went wrong")
                        }
                    }

                    else -> Unit
                }
            }
        }
    }

    fun removeFollowerFollowing(type: String, followedId: String){
        viewModelScope.launch {
            repository.removeFollowFollower(type = type, userId = sessionManager.getUserId(), followerId = followedId)
                .collectLatest { result->
                    when(result){
                        is Resource.Loading -> {
                            _uiState.update { it.copy(isLoading = true) }
                        }
                        is Resource.Success -> {
                            _uiState.update { it.copy(isLoading = false) }
                        }
                        is Resource.Error -> {
                            _uiState.update { it.copy(error = result.message ?: "Something went wrong") }
                        }
                        is Resource.Idle -> Unit
                    }
                }
        }
    }


    fun consumeError() {
        _uiState.update { it.copy(error = null) }
    }

    // Pagination Scrolling
    fun loadNextPage() {
        if (uiState.value.isMoreLoading || uiState.value.endReached) return

        when (uiState.value.selectedOption) {
            TAB_FOLLOWER -> loadMyFollowers(reset = false)
            TAB_FOLLOWING -> loadMyFollowing(reset = false)
        }
    }


    private companion object {
        const val PAGE_LIMIT = "20"
        const val TAB_FOLLOWER = "Follower"
        const val TAB_FOLLOWING = "Following"
    }

}



