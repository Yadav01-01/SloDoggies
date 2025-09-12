package com.bussiness.slodoggiesapp.viewModel.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class PostUiState(
    val description: String = "",
    val profileImageUrl: String = "",
    val userName: String = "",
    val role: String = "",
    val timeAgo: String = "",
    val postImageUrl: String = ""
)

class EditPostViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(PostUiState())
    val uiState: StateFlow<PostUiState> = _uiState

    init {
        // Mock initial data
        _uiState.value = PostUiState(
            description = "Meet Wixx – our brown bundle of joy!\nFrom tail wags to beach days, life with this 3-year-old |",
            profileImageUrl = "https://example.com/profile.jpg",
            userName = "Lydia Vaccaro",
            role = "Pet Mom",
            timeAgo = "5 Min",
            postImageUrl = "https://example.com/dogs.jpg"
        )
    }

    fun updateDescription(newDescription: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(description = newDescription)
        }
    }

    fun saveEditedPost() {
        // Call repository / API to save edited post
    }

    fun deletePost() {
        // API call for delete
    }
}
