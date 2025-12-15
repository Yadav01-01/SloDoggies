package com.bussiness.slodoggiesapp.viewModel.petOwner.servicesVM


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bussiness.slodoggiesapp.data.model.petOwner.ServiceDetailModel
import com.bussiness.slodoggiesapp.data.newModel.ownerService.ServiceDetailsData
import com.bussiness.slodoggiesapp.data.remote.Repository
import com.bussiness.slodoggiesapp.data.uiState.ServiceDetailUIState
import com.bussiness.slodoggiesapp.network.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ServiceDetailViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _serviceDetail = MutableStateFlow(ServiceDetailUIState())
    val serviceDetail: StateFlow<ServiceDetailUIState> = _serviceDetail

    fun setServiceDetail(serviceId: String) {
        viewModelScope.launch {
            repository.ownerServiceDetail(serviceId).collectLatest { result ->

                when (result) {

                    is Resource.Loading -> {
                        _serviceDetail.value = ServiceDetailUIState(isLoading = true)
                    }

                    is Resource.Success -> {
                        _serviceDetail.value = ServiceDetailUIState(
                            isLoading = false,
                            serviceDetail = result.data.data
                        )
                    }

                    is Resource.Error -> {
                        _serviceDetail.value = ServiceDetailUIState(
                            isLoading = false,
                            error = result.message ?: "Unknown error"
                        )
                    }

                    Resource.Idle -> Unit
                }
            }
        }
    }
}
