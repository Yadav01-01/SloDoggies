package com.bussiness.slodoggiesapp.viewModel.servicebusiness

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bussiness.slodoggiesapp.data.newModel.businessdetails.BusinessDetailsModel
import com.bussiness.slodoggiesapp.data.remote.Repository
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

    init {
//        getBusinessDetail()
    }

    fun getBusinessDetail(){
        viewModelScope.launch {
            repository.getBusinessDashboard(sessionManager.getUserId()).collectLatest { result ->
                when (result) {
                    is Resource.Loading -> _uiState.update { it.copy(isLoading = true) }
                    is Resource.Success -> {
                        _uiState.value = _uiState.value.copy(isLoading = false)
                        val response = result.data
                        if (response.success == true && response.data != null) {
                            val data = response.data
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

    private fun onError(message: String?) {
        _uiState.update { it.copy(error = message ?: "Something went wrong") }
    }

}