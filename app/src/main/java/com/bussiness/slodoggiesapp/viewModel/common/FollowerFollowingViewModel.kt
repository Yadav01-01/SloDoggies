package com.bussiness.slodoggiesapp.viewModel.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bussiness.slodoggiesapp.data.model.common.AudienceItem
import com.bussiness.slodoggiesapp.data.newModel.FollowerItem
import com.bussiness.slodoggiesapp.data.newModel.FollowingItem
import com.bussiness.slodoggiesapp.data.remote.Repository
import com.bussiness.slodoggiesapp.data.uiState.FollowerFollowingUiState
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
class FollowerFollowingViewModel @Inject constructor(
    private val repository: Repository,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(FollowerFollowingUiState())
    val uiState: StateFlow<FollowerFollowingUiState> = _uiState

    // Independent pagination states for each list
    private var followerPage = 1
    private var followerTotalPages = 1
    private var followerLoadingMore = false

    private var followingPage = 1
    private var followingTotalPages = 1
    private var followingLoadingMore = false

    init {
        loadFollowers(reset = true)
    }

    /**
     * When user switches tabs
     */
    fun updateSelectedOption(type: String) {
        _uiState.update {
            it.copy(
                selectedOption = type,
                // UI will show previous data until new is loaded
            )
        }

        if (type == "Follower") {
            if (_uiState.value.followers.isEmpty())
                loadFollowers(reset = true)
        } else {
            if (_uiState.value.following.isEmpty())
                loadFollowing(reset = true)
        }
    }

    // ---------------------------
    //  Mappers
    // ---------------------------

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

    // ---------------------------
    //  FOLLOWERS API
    // ---------------------------
    fun loadFollowers(reset: Boolean = false) {
        val userId = sessionManager.getUserId()

        if (reset) {
            followerPage = 1
            followerTotalPages = 1
        }

        // prevent double load
        if (!reset && (followerLoadingMore || followerPage > followerTotalPages)) return

        viewModelScope.launch {
            repository.getFollowerList(
                userId = userId,
                page = followerPage.toString(),
                limit = "20"
            ).collectLatest { result ->

                when (result) {

                    is Resource.Loading -> {
                        if (reset) {
                            _uiState.update { it.copy(isRefreshing = false) }
                        } else {
                            followerLoadingMore = true
                        }
                    }

                    is Resource.Success -> {
                        val response = result.data.data
                        val newData = response?.followers?.map { it.toAudienceItem() }.orEmpty()

                        followerTotalPages = response?.totalPage ?: 1

                        _uiState.update {
                            it.copy(
                                isRefreshing = false,
                                followers = if (reset) newData else it.followers + newData,
                                isEmptyData = newData.isEmpty() && reset,
                                error = null
                            )
                        }

                        followerPage++
                        followerLoadingMore = false
                    }

                    is Resource.Error -> {
                        _uiState.update {
                            it.copy(
                                isRefreshing = false,
                                error = result.message ?: "Something went wrong",
                                isEmptyData = true
                            )
                        }
                        followerLoadingMore = false
                    }

                    Resource.Idle -> Unit
                }
            }
        }
    }

    // ---------------------------
    //  FOLLOWING API
    // ---------------------------
    fun loadFollowing(reset: Boolean = false) {

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
                limit = "20"
            ).collectLatest { result ->

                when (result) {

                    is Resource.Loading -> {
                        if (reset) {
                            _uiState.update { it.copy(isRefreshing = false) }
                        } else {
                            followingLoadingMore = true
                        }
                    }

                    is Resource.Success -> {

                        val response = result.data.data
                        val newData = response?.data?.map { it.toAudienceItem() }.orEmpty()

                        followingTotalPages = response?.totalPage ?: 1

                        _uiState.update {
                            it.copy(
                                isRefreshing = false,
                                following = if (reset) newData else it.following + newData,
                                isEmptyData = newData.isEmpty() && reset,
                                error = null
                            )
                        }

                        followingPage++
                        followingLoadingMore = false
                    }

                    is Resource.Error -> {
                        _uiState.update {
                            it.copy(
                                isRefreshing = false,
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

    // Called when list scroll reaches bottom
    fun loadNextPage() {
        if (uiState.value.selectedOption == "Follower") {
            loadFollowers(reset = false)
        } else {
            loadFollowing(reset = false)
        }
    }
}
