package com.bussiness.slodoggiesapp.viewModel.sponsoredAds

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bussiness.slodoggiesapp.data.remote.Repository
import com.bussiness.slodoggiesapp.data.uiState.AdsUiState
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
class SponsoredAdsViewmodel @Inject constructor(
    private val repository: Repository,
    private val sessionManager: SessionManager,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _uiState = MutableStateFlow(
        AdsUiState(
            fromPreview = savedStateHandle["fromPreview"] ?: false
        )
    )

    init {
        getAdsData()
    }

    private fun getAdsData(){
        viewModelScope.launch {
            repository.sponsoredAds(sessionManager.getUserId()).collectLatest { result ->
                when(result){
                    is Resource.Loading -> {
                        _uiState.update { it.copy(isLoading = true) }
                    }
                    is Resource.Success -> {
                        _uiState.update { it.copy(isLoading = false, data = result.data.data) }
                    }
                    is Resource.Error -> {
                        _uiState.update { it.copy(isLoading = false, errorMessage = result.message) }
                    }
                    is Resource.Idle -> Unit
                }
            }
        }
    }

    val uiState: StateFlow<AdsUiState> = _uiState

    fun updateSelectedStatus(status: String) {
        _uiState.update { it.copy(selectedStatus = status) }
    }

    fun showStatusDialog(show: Boolean) {
        _uiState.update { it.copy(showStatusDialog = show) }
    }

    fun setNavigating(value: Boolean) {
        _uiState.update { it.copy(isNavigating = value) }
    }

    fun clearError() {
        _uiState.update { it.copy(errorMessage = null) }
    }
}

