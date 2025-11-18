package com.bussiness.slodoggiesapp.viewModel.petOwner

import androidx.lifecycle.ViewModel
import com.bussiness.slodoggiesapp.data.remote.Repository
import com.bussiness.slodoggiesapp.data.uiState.CreateOwnerPostState
import com.bussiness.slodoggiesapp.util.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class CreateOwnerPostViewModel @Inject constructor(
    private val repository: Repository,
    private val sessionManager: SessionManager
) :ViewModel() {

    private val _postState = MutableStateFlow(CreateOwnerPostState())
    val postState: StateFlow<CreateOwnerPostState> = _postState

    fun updateWritePost(text: String) {
        _postState.value = _postState.value.copy(writePost = text)
    }

    fun updateHashtags(tag: String) {
        _postState.value = _postState.value.copy(hashtags = tag)
    }

    fun updatePostalCode(code: String) {
        _postState.value = _postState.value.copy(postalCode = code)
    }


}