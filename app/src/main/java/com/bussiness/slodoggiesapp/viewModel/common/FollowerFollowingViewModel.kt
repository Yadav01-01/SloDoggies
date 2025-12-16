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

    init {
        observeSearchInput()

        // Auto load first time
        loadFollowers(reset = true)
    }

    /**
     * Called when user types in search bar
     */
    fun onSearchQueryChange(query: String) {
        searchQuery.value = query
        _uiState.update { it.copy(query = query) }
    }

    /**
     * Debounce search and auto trigger API
     */
    @OptIn(FlowPreview::class)
    private fun observeSearchInput() {
        viewModelScope.launch {
            searchQuery
                .debounce(500)
                .distinctUntilChanged()
                .collectLatest { newQuery ->
                    if (uiState.value.selectedOption == "Follower") {
                        loadFollowers(reset = true)
                    } else {
                        loadFollowing(reset = true)
                    }
                }
        }
    }


    // When user switches tab
    fun updateSelectedOption(type: String) {
        _uiState.update {
            it.copy(selectedOption = type)
        }

        if (type == "Follower") {
            if (_uiState.value.followers.isEmpty())
                loadFollowers(reset = true)
        } else {
            if (_uiState.value.following.isEmpty())
                loadFollowing(reset = true)
        }
    }


    //  Mappers
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

    //  Followers API
    fun loadFollowers(reset: Boolean = false) {

        val userId = sessionManager.getUserId()

        if (reset) {
            followerPage = 1
            followerTotalPages = 1
            _uiState.update { it.copy(followers = emptyList()) }   // CLEAR LIST
        }

        if (!reset && (followerLoadingMore || followerPage > followerTotalPages)) return

        viewModelScope.launch {
            repository.getFollowerList(
                userId = userId,
                page = followerPage.toString(),
                limit = "20",
                search = searchQuery.value
            ).collectLatest { result ->

                when (result) {

                    is Resource.Success -> {
                        val response = result.data.data
                        val newData = response?.followers?.map { it.toAudienceItem() }.orEmpty()

                        followerTotalPages = response?.totalPage ?: 1

                        _uiState.update {
                            it.copy(
                                followers = if (reset) newData else it.followers + newData,
                                isEmptyData = reset && newData.isEmpty(),
                                totalFollower = response?.totalFollowers.toString(),
                                totalFollowing = response?.totalFollowing.toString(),
                                error = null
                            )
                        }

                        followerPage++
                        followerLoadingMore = false
                    }

                    is Resource.Error -> {
                        _uiState.update {
                            it.copy(
                                error = result.message ?: "Something went wrong",
                                isEmptyData = reset
                            )
                        }
                        followerLoadingMore = false
                    }

                    else -> Unit
                }
            }
        }
    }




    //  Following API
    private fun loadFollowing(reset: Boolean = false) {

        val userId = sessionManager.getUserId()

        if (reset) {
            followingPage = 1
            followingTotalPages = 1
        }

        if (!reset && (followingLoadingMore || followingPage > followingTotalPages)) return

        viewModelScope.launch {
            repository.getFollowingList(
                userId = userId,
                page = followingPage.toString(),
                limit = "20",
                search = searchQuery.value
            ).collectLatest { result ->

                when (result) {

                    is Resource.Loading -> {
                        if (!reset) followingLoadingMore = true
                    }

                    is Resource.Success -> {
                        val response = result.data.data
                        val newData = response?.data?.map { it.toAudienceItem() }.orEmpty()

                        followingTotalPages = response?.totalPage ?: 1

                        _uiState.update {
                            it.copy(
                                following = if (reset) newData else it.following + newData,
                                isEmptyData = newData.isEmpty() && reset,
                                totalFollower = response?.totalFollower.toString(),
                                totalFollowing = response?.totalFollowing.toString(),
                                error = null
                            )
                        }

                        followingPage++
                        followingLoadingMore = false
                    }

                    is Resource.Error -> {
                        _uiState.update {
                            it.copy(
                                error = result.message ?: "Something went wrong",
                                isEmptyData = true
                            )
                        }
                        followingLoadingMore = false
                    }

                    Resource.Idle -> Unit
                }
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


    fun consumeError() {
        _uiState.update { it.copy(error = null) }
    }

    // Pagination Scrolling
    fun loadNextPage() {
        if (uiState.value.selectedOption == "Follower") {
            loadFollowers(reset = false)
        } else {
            loadFollowing(reset = false)
        }
    }
}
