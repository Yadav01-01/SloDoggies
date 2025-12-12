package com.bussiness.slodoggiesapp.viewModel.servicebusiness

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bussiness.slodoggiesapp.data.newModel.businessdetails.BusinessDetailsModel
import com.bussiness.slodoggiesapp.data.remote.Repository
import com.bussiness.slodoggiesapp.data.uiState.OwnerProfileUiState
import com.bussiness.slodoggiesapp.network.Resource
import com.bussiness.slodoggiesapp.util.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BusinessServicesViewModel @Inject constructor(
    private val repository: Repository,
    private val sessionManager: SessionManager
) : ViewModel() {


    private val _uiState = MutableStateFlow(BusinessDetailsModel())
    val uiState: StateFlow<BusinessDetailsModel> = _uiState.asStateFlow()


    private val _uiStateGallery = MutableStateFlow(OwnerProfileUiState())
    val uiStateGallery: StateFlow<OwnerProfileUiState> = _uiStateGallery.asStateFlow()


    fun getBusinessDetail(userId:String){
        var logicUserId= ""
        if (userId != null  && !userId.equals("")) {
            logicUserId = userId
        }else{
            logicUserId = sessionManager.getUserId()
        }
        viewModelScope.launch {
            repository.getBusinessDashboard(logicUserId).collectLatest { result ->
                when (result) {
                    is Resource.Loading -> _uiState.update { it.copy(isLoading = true) }
                    is Resource.Success -> {
                        _uiState.value = _uiState.value.copy(isLoading = false)
                        val response = result.data
                        if (response.success == true && response.data != null) {
                            val data = response.data
                            sessionManager.setUserName(data?.business?.business_name?:"")
                            sessionManager.setUserImage(data?.business?.business_logo?:"")
                            _uiState.value = _uiState.value.copy(
                                data = data
                            )
                        } else {
                            onError(response.message ?: "Failed to fetch owner details")
                        }
                    }
                    is Resource.Error -> {
                        _uiState.update { it.copy(isLoading = false) }
                        onError(result.message)
                    }
                    Resource.Idle -> Unit
                }

            }
        }
    }

//    fun loadNextPage() {
//        if (!uiStateGallery.value.isLoadingMore && uiStateGallery.value.canLoadMore) {
//            galleryPostDetail(uiStateGallery.value.currentPage + 1)
//        }
//    }
    fun galleryPostDetail(userId:String,page: Int = 1) {
        var logicUserId= ""
        viewModelScope.launch {
            if (page == 1) {
                _uiStateGallery.update { it.copy(isLoading = true) }
            } else {
                _uiStateGallery.update { it.copy(isLoadingMore = true) }
            }

            if (userId != null  && !userId.equals("")) {
                logicUserId = userId
            }else{
                logicUserId = sessionManager.getUserId()
            }

            repository.getOwnerGalleryPost(logicUserId, page.toString())
                .collectLatest { result ->
                    when (result) {
                        is Resource.Success -> {
                            val response = result.data
                            val galleryData = response.data
                            Log.d("******",response.toString())
                            val newPosts = galleryData?.posts ?: emptyList()

                            _uiStateGallery.update {
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
                            _uiStateGallery.update {
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

    private fun onError(message: String?) {
        _uiState.update { it.copy(error = message ?: "Something went wrong") }
    }

}