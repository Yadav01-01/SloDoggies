package com.bussiness.slodoggiesapp.viewModel.common.userPost

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bussiness.slodoggiesapp.data.remote.Repository
import com.bussiness.slodoggiesapp.data.uiState.UserPostUiState
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
class UserPostViewModel @Inject constructor(
    private val repository: Repository,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(UserPostUiState())
    val uiState: StateFlow<UserPostUiState> = _uiState.asStateFlow()

    init {
        fetchPosts()
    }

    private fun fetchPosts() {
        viewModelScope.launch {
            repository.getMyPostDetails(sessionManager.getUserId())
                .collectLatest { result ->
                    when (result) {

                        is Resource.Loading -> {
                            _uiState.update {
                                it.copy(
                                    isLoading = true,
                                    message = null
                                )
                            }
                        }

                        is Resource.Success -> {
                            val data = result.data.data ?: emptyList()

                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    posts = data,
                                )
                            }
                        }

                        is Resource.Error -> {
                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    message = result.message ?: "Something went wrong"
                                )
                            }
                        }

                        Resource.Idle -> TODO()
                    }
                }
        }
    }
}
