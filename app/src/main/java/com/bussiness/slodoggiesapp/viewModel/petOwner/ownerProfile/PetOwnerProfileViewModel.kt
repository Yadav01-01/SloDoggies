package com.bussiness.slodoggiesapp.viewModel.petOwner.ownerProfile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bussiness.slodoggiesapp.data.remote.Repository
import com.bussiness.slodoggiesapp.data.uiState.OwnerProfileUiState
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
class PetOwnerProfileViewModel @Inject constructor(
    private val repository: Repository,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(OwnerProfileUiState())
    val uiState: StateFlow<OwnerProfileUiState> = _uiState

    init {
        onRefresh()
    }

    fun onRefresh(){
        profileDetail()
        galleryPostDetail()
    }

    fun onPetSelected(index: Int) {
        _uiState.update { it.copy(selectedPet = index) }
    }


    fun petInfoDialog(show: Boolean) {
        _uiState.update { it.copy(showPetInfoDialog = show) }
    }

    fun petAddedSuccessDialog(show: Boolean) {
        _uiState.update { it.copy(petAddedSuccessDialog = show) }
    }


    private fun profileDetail() {
        viewModelScope.launch {
            repository.getOwnerProfileDetails(sessionManager.getUserId(),"")
                .collectLatest { result ->
                    when (result) {
                        is Resource.Loading -> {
                            _uiState.update {
                                it.copy(
                                    isLoading = true,
                                    errorMessage = null
                                )
                            }
                        }

                        is Resource.Success -> {
                            val response = result.data
                            sessionManager.setUserName(response?.data?.owner?.name?:"")
                            sessionManager.setUserImage(response?.data?.owner?.image?:"")
                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    data = response.data,
                                    errorMessage = if (response.success) null else response.message
                                )
                            }
                        }

                        is Resource.Error -> {
                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    errorMessage = result.message
                                )
                            }
                        }

                        Resource.Idle -> Unit
                    }
                }
        }
    }

    private fun galleryPostDetail(page: Int = 1) {
        viewModelScope.launch {
            if (page == 1) {
                _uiState.update { it.copy(isLoading = true) }
            } else {
                _uiState.update { it.copy(isLoadingMore = true) }
            }

            repository.getOwnerGalleryPost(sessionManager.getUserId(), page.toString())
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
                                    errorMessage = null
                                )
                            }
                        }

                        is Resource.Error -> {
                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    isLoadingMore = false,
                                    errorMessage = result.message
                                )
                            }
                        }

                        else -> Unit
                    }
                }
        }
    }

    fun loadNextPage() {
        val state = uiState.value
        if (!state.isLoading && !state.isLoadingMore && state.canLoadMore) {
            galleryPostDetail(state.currentPage + 1)
        }
    }

}
