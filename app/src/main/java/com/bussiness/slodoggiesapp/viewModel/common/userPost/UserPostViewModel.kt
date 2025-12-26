package com.bussiness.slodoggiesapp.viewModel.common.userPost

import androidx.lifecycle.ViewModel
import com.bussiness.slodoggiesapp.data.newModel.home.PostItem
import com.bussiness.slodoggiesapp.data.remote.Repository
import com.bussiness.slodoggiesapp.data.uiState.HomeUiState
import com.bussiness.slodoggiesapp.data.uiState.UserPostUiState
import com.bussiness.slodoggiesapp.util.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class UserPostViewModel @Inject constructor(
    private val repository: Repository,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(UserPostUiState())
    val uiState: StateFlow<UserPostUiState> = _uiState.asStateFlow()
    private var currentPage = 1
    private var isLastPage = false
    private var isRequestRunning = false

    //  Added for Save Fix
    private val localSavedState = mutableMapOf<String, Boolean>()


    init {
        loadFirstPage()
    }

    fun loadFirstPage() {
        currentPage = 1
        isLastPage = false
      //  fetchPosts(isFirstPage = true)
    }
    fun loadNextPage() {
        if (!isLastPage && !isRequestRunning) {
        //    fetchPosts(isFirstPage = false)
        }
    }
//    private fun fetchPosts(isFirstPage: Boolean) {
//        viewModelScope.launch {
//            repository.getMyPostDetails(sessionManager.getUserId(),
//                page = currentPage.toString())
//                .collectLatest { result ->
//                    when (result) {
//
//                        is Resource.Loading -> Unit
//                        Resource.Idle -> Unit
//
//                        is Resource.Success -> {
//                            val response = result.data.data
//
//                         //   val items = response?.items ?: emptyList()
//                         //   val uiPosts = HomeFeedMapper.map(items)
//                            // 🔥 Merge Fix
//                            val mergedPosts = mergeApiWithLocal(uiPosts)
//                            _uiState.update { state ->
//                                state.copy(
//                                    posts = if (isFirstPage) mergedPosts
//                                    else state.posts + mergedPosts,
//                                    isLoading = false,
//                                    isLoadingMore = false,
//                                    errorMessage = ""
//                                )
//                            }
//                            val totalPages = response?.totalPage ?: 1
//                            isLastPage = currentPage >= totalPages
//                            if (!isLastPage) currentPage++
//                        }
//
//                        is Resource.Error -> {
//                            _uiState.update {
//                                it.copy(
//                                    isLoading = false,
//                                    message = result.message ?: "Something went wrong"
//                                )
//                            }
//                        }
//
//                        Resource.Idle -> {
//                            _uiState.update {
//                                it.copy(
//                                    isLoading = false,
//                                )
//                            }
//                        }
//                    }
//                }
//        }
//    }


    // 🔥 Merge function: API + Local
    private fun mergeApiWithLocal(apiPosts: List<PostItem>): List<PostItem> {
        return apiPosts.map { post ->

            val id = when (post) {
                is PostItem.NormalPost -> post.postId
                is PostItem.CommunityPost -> post.postId
                is PostItem.SponsoredPost -> post.postId
            } ?: return@map post   // ❗ Null id → return same post safely

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



}
