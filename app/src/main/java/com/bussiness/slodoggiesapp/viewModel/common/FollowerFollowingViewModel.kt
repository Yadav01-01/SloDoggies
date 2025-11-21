package com.bussiness.slodoggiesapp.viewModel.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bussiness.slodoggiesapp.data.model.common.AudienceItem
import com.bussiness.slodoggiesapp.data.newModel.Follow
import com.bussiness.slodoggiesapp.data.newModel.Following
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

    init {
        loadData("Follower")
    }

    fun updateSelectedOption(type: String) {
        _uiState.update { it.copy(selectedOption = type) }
        loadData(type)
    }


    private fun Follow.toAudienceItem(): AudienceItem {
        return AudienceItem(
            id = id,
            name = name.orEmpty(),
            profilePic = profilePic,
            isVerified = isVerified ?: false,
            isFollowing = isFollowing ?: false
        )
    }



    private fun Following.toAudienceItem(): AudienceItem {
        return AudienceItem(
            id = id,
            name = name,
            profilePic = profilePic,
            isVerified = isVerified ?: false,
            isFollowingMe = isFollowingMe ?: false
        )
    }


    private fun loadData(type: String) {
        val userId = sessionManager.getUserId() ?: return

        viewModelScope.launch {
            if (type == "Follower") loadFollowers(userId)
            else loadFollowing(userId)
        }
    }

    private suspend fun loadFollowers(userId: String) {
        repository.getFollowerList(userId).collectLatest { result ->
            when (result) {
                is Resource.Loading -> _uiState.update {
                    it.copy(isLoading = true)
                }

                is Resource.Success -> {
                    val list = result.data.data ?: emptyList()
                    val mapped = list.map { it.toAudienceItem() }

                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            followers = mapped,
                            isEmptyData = mapped.isEmpty(),
                            error = null
                        )
                    }
                }

                is Resource.Error -> _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = result.message,
                        isEmptyData = true
                    )
                }

                Resource.Idle -> Unit
            }
        }
    }



    private suspend fun loadFollowing(userId: String) {
        repository.getFollowingList(userId).collectLatest { result ->
            when (result) {
                is Resource.Loading -> _uiState.update {
                    it.copy(isLoading = true)
                }

                is Resource.Success -> {
                    val list = result.data.data ?: emptyList()
                    val mapped = list.map { it.toAudienceItem() }

                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            following = mapped,
                            isEmptyData = mapped.isEmpty(),
                            error = null
                        )
                    }
                }

                is Resource.Error -> _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = result.message,
                        isEmptyData = true
                    )
                }

                Resource.Idle -> Unit
            }
        }
    }


}
