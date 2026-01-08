package com.bussiness.slodoggiesapp.viewModel.common

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.bussiness.slodoggiesapp.data.newModel.ownerProfile.OwnerData
import com.bussiness.slodoggiesapp.data.newModel.ownerProfile.OwnerPostItem
import com.bussiness.slodoggiesapp.data.remote.Repository
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

data class PersonDetailUiState(
    val selectedImageIndex: Int = 0,
    var isFollowed: Boolean = false,
    val loading: Boolean = false,
    val error: String? = null,
    val isLoading: Boolean = false,
    val data: OwnerData = OwnerData(),
    val posts: List<OwnerPostItem> = emptyList(),
    val isLoadingMore: Boolean = false,
    val canLoadMore: Boolean = true,
    val currentPage: Int = 1,
    val isRefreshing: Boolean = false
    )

@HiltViewModel
class PersonDetailViewModel @Inject constructor(
    private val repository: Repository,
    private val sessionManager: SessionManager
): ViewModel() {

    private val _uiState = MutableStateFlow(PersonDetailUiState())
    val uiState: StateFlow<PersonDetailUiState> = _uiState

    fun selectProfileImage(index: Int) {
        _uiState.update { it.copy(selectedImageIndex = index) }
    }

    fun follow(followedId: String) {
        _uiState.update { it.copy(isFollowed = !it.isFollowed) }
        viewModelScope.launch {
            repository.addAndRemoveFollowers(sessionManager.getUserId(),followedId).collectLatest { result ->
                when(result){
                    is Resource.Loading -> {
                        _uiState.update { it.copy(loading = true) }
                    }
                    is Resource.Success -> {
                        _uiState.update { it.copy(loading = false,isFollowed = !it.isFollowed) }
                    }
                    is Resource.Error -> {
                        _uiState.update { it.copy(loading = false, error = result.message) }
                    }
                    is Resource.Idle -> Unit
                }
            }
        }
    }

    fun message(navController: NavHostController) {

        navController.navigate(Routes.CHAT_SCREEN)
    }

    fun profileDetail(ownerId : String){
        viewModelScope.launch {
            repository.getOwnerProfileDetails(sessionManager.getUserId(),ownerId).collectLatest { result ->
                when(result){
                    is Resource.Loading ->{
                        _uiState.update { it.copy(loading = true) }
                    }
                    is Resource.Success ->{
                        _uiState.update { it.copy(loading = false, data = result.data.data) }
                    }
                    is Resource.Error ->{
                        _uiState.update { it.copy(loading = false, error = result.message) }
                    }

                    Resource.Idle -> TODO()
                }
            }
        }
    }

     fun galleryPostDetail(page: Int = 1,ownerUserId: String) {
        viewModelScope.launch {
            if (page == 1) {
                _uiState.update { it.copy(isLoading = true) }
            } else {
                _uiState.update { it.copy(isLoadingMore = true) }
            }

            repository.getOwnerGalleryPost(ownerUserId, page.toString())
                .collectLatest { result ->
                    when (result) {
                        is Resource.Success -> {
                            val response = result.data

                            val galleryData = response.data
                            Log.d("******",response.toString())
                            val newPosts = galleryData?.posts ?: emptyList()

                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    isLoadingMore = false,
                                    posts = if (page == 1) {
                                        newPosts
                                    } else {
                                        it.posts + newPosts
                                    },
                                    currentPage = page,
                                    canLoadMore = newPosts.isNotEmpty(),
                                )
                            }
                        }

                        is Resource.Error -> {
                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    isLoadingMore = false,
                                    error = result.message
                                )
                            }
                        }

                        else -> Unit
                    }
                }
        }
    }

    fun loadNextPage(ownerUserId: String) {
        val state = uiState.value
        if (!state.isLoading && !state.isLoadingMore && state.canLoadMore) {
            galleryPostDetail(state.currentPage + 1, ownerUserId = ownerUserId)
        }
    }
}
