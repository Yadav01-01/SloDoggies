package com.bussiness.slodoggiesapp.viewModel.createpostowner

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bussiness.slodoggiesapp.data.remote.Repository
import com.bussiness.slodoggiesapp.data.uiState.PostCreateOwnerUiState
import com.bussiness.slodoggiesapp.network.Resource
import com.bussiness.slodoggiesapp.ui.component.common.createMultipartList
import com.bussiness.slodoggiesapp.util.Messages
import com.bussiness.slodoggiesapp.util.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostCreateOwnerViewModel @Inject constructor(private val repository: Repository,
                                                   private var sessionManager: SessionManager) : ViewModel() {

    private val _uiState = MutableStateFlow(PostCreateOwnerUiState())
    val uiState: StateFlow<PostCreateOwnerUiState> = _uiState
    /** Selected Image */
    private val _selectedPhotos = MutableStateFlow<List<Uri>>(emptyList())

    fun onWritePost(post: String) {
        _uiState.value = _uiState.value.copy(writePost = post)
    }

    fun onHashTagChange(tage: String) {
        _uiState.value = _uiState.value.copy(hashTage = tage)
    }
    fun onLocation(location: String) {
        _uiState.value = _uiState.value.copy(location = location)
    }

    fun onLatitude(lat: String) {
        _uiState.value = _uiState.value.copy(lattitude = lat)
    }

    fun onLongitude(long: String) {
        _uiState.value = _uiState.value.copy(longitude = long)
    }

    fun onPetID(id: String) {
        _uiState.value = _uiState.value.copy(petId = id)
    }

    fun createPostOwner(context: Context, onSuccess: () -> Unit = { }, onError: (String) -> Unit) {
        val state = _uiState.value
        if (!validatePost(state, onError)) return

        val imageParts = state.image?.let {
            createMultipartList(
                context = context,
                uris = it,
                keyName = "images[]"
            )
        }
        viewModelScope.launch {
            repository.createPostOwnerRequest(
                writePost = state.writePost?:"",
                hashTage = state.hashTage?:"",
                location = state.location?:"",
                latitude = state.lattitude?:"",
                longitude = state.longitude?:"",
                userId = sessionManager.getUserId(),
                petId = state.petId?:"",
                userType =sessionManager.getUserType().toString(),
                image = imageParts,
            ).collectLatest { result ->
                when (result) {
                    is Resource.Loading -> {
                        _uiState.value = _uiState.value.copy(isLoading = true)
                    }
                    is Resource.Success -> {
                        _uiState.value = _uiState.value.copy(isLoading = false)
                        result.data.let { response ->
                            if (response.success) {
                                clearState()
                                clearPhotos()
                                onSuccess()
                            } else {
                                onError(response.message ?: "Login failed")
                            }
                        }
                    }
                    is Resource.Error -> {
                        _uiState.value = _uiState.value.copy(isLoading = false)
                        onError(result.message)
                    }
                    Resource.Idle -> TODO()
                }
            }
        }

    }

    fun addPhoto(uri: Uri) {
        val updatedPhotos = _selectedPhotos.value + uri
        _selectedPhotos.value = updatedPhotos
        _uiState.value = _uiState.value.copy(image = updatedPhotos as MutableList)
    }

    fun removePhoto(uri: Uri) {
        val updatedPhotos = _selectedPhotos.value - uri
        _selectedPhotos.value = updatedPhotos
        _uiState.value = _uiState.value.copy(image = updatedPhotos as MutableList)
    }

    private fun clearPhotos() {
        _selectedPhotos.value = emptyList()
        _uiState.value = _uiState.value.copy(image = null)
    }


    private fun clearState() {
        _uiState.value = PostCreateOwnerUiState()
    }

    private fun validatePost(state: PostCreateOwnerUiState, onError: (String) -> Unit): Boolean {
        if (state.image.isNullOrEmpty()) {
            onError(Messages.UPLOAD_IMAGE_SMS)
            return false
        }
        if (state.petId == null) {
            onError(Messages.PET_SELECT_SMS)
            return false
        }
        if (state.writePost.isNullOrEmpty()) {
            onError(Messages.WRITE_POST_SMS)
            return false
        }
        return true
    }



}



