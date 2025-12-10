package com.bussiness.slodoggiesapp.viewModel.common

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bussiness.slodoggiesapp.data.newModel.home.PostItem
import com.bussiness.slodoggiesapp.data.remote.Repository
import com.bussiness.slodoggiesapp.network.Resource
import com.bussiness.slodoggiesapp.util.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PostUiState(
    val postNormal: PostItem.NormalPost? = null,
    val postCommunity: PostItem.CommunityPost? = null,
    val postSponsored: PostItem.SponsoredPost? = null,
    val description: String = "",
    val postId: String = "",
    val errorMessage : String = "",
    val isLoading: Boolean = false,          // For first page loader
    val type: String = ""
)
@HiltViewModel
class EditPostViewModel @Inject constructor(
    private val sessionManager: SessionManager,
    private val repository: Repository
) : ViewModel() {
    private val _uiState = MutableStateFlow(PostUiState())
    val uiState: StateFlow<PostUiState> = _uiState

    // Set post with type
    fun setPost(post: PostItem, type: String) {
        _uiState.update { state ->
            when(post) {
                is PostItem.NormalPost -> state.copy(
                    postNormal = post,
                    description = post.caption,
                    postId = post.postId,
                    type = type
                )
                is PostItem.CommunityPost -> state.copy(
                    postCommunity = post,
                    description = post.eventDescription ?: "",
                    postId = post.postId,
                    type = type
                )
                is PostItem.SponsoredPost -> state.copy(
                    postSponsored = post,
                    description = post.caption,
                    postId = post.postId,
                    type = type
                )
            }
        }
    }

    // Update description
    fun updateDescription(newDescription: String) {
        Log.d("******",newDescription)
        _uiState.update { state -> state.copy(description = newDescription) }
    }

    fun editPost(postId:String, postDescription: String,
                 onSuccess: () -> Unit = { }, onError: (String) -> Unit) {
        viewModelScope.launch {
            repository.editPost(
                userId = sessionManager.getUserId(),
                postId = postId,
                postDescription = postDescription
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
}

