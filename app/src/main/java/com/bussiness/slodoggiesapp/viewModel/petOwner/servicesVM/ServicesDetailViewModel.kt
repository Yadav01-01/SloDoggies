package com.bussiness.slodoggiesapp.viewModel.petOwner.servicesVM

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bussiness.slodoggiesapp.data.model.petOwner.ServiceDetailModel
import com.bussiness.slodoggiesapp.data.newModel.ownerService.ServiceDetailsData
import com.bussiness.slodoggiesapp.data.newModel.ownerService.ServiceItemDetails
import com.bussiness.slodoggiesapp.data.remote.Repository
import com.bussiness.slodoggiesapp.data.uiState.ServiceDetailUIState
import com.bussiness.slodoggiesapp.network.Resource
import com.bussiness.slodoggiesapp.util.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import retrofit2.http.Field
import javax.inject.Inject

@HiltViewModel
class ServiceDetailViewModel @Inject constructor(
    private val repository: Repository,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _serviceDetail = MutableStateFlow(ServiceDetailUIState())
    val serviceDetail: StateFlow<ServiceDetailUIState> = _serviceDetail
    var selectedServiceId:Int =0

    private val _showDeleteDialog = MutableStateFlow(false)
    val showDeleteDialog: StateFlow<Boolean> = _showDeleteDialog
    private val _reviewSubmitted = MutableSharedFlow<Boolean>()
    val reviewSubmitted = _reviewSubmitted.asSharedFlow()

    var serviceId :String =""

    fun showDeleteDialog() {
        _showDeleteDialog.value = true
    }

    fun hideDeleteDialog() {
        _showDeleteDialog.value = false
    }

    fun serviceReview(
        serviceId:Int,
        userId :Int,rating:String, message:String
    ){
       viewModelScope.launch {
           repository.serviceReview(serviceId,sessionManager.getUserId().toInt(),rating,message).collectLatest { result ->

               when (result) {

                   is Resource.Loading -> {
                      // _serviceDetail.value = ServiceDetailUIState(isLoading = true)
                   }

                   is Resource.Success -> {
                       _reviewSubmitted.emit(true)
                   }

                   is Resource.Error -> {

                   }

                   Resource.Idle -> Unit
               }
           }
       }
   }

    fun setServiceDetail(serviceId: String) {
        viewModelScope.launch {
            repository.ownerServiceDetail(serviceId,sessionManager.getUserId()).collectLatest { result ->

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


    fun deleteService(
        @Field("user_id") userId :Int,
        @Field("service_id") serviceId: Int
    ){
        viewModelScope.launch {
            repository.deleteService(userId,serviceId).collectLatest { result ->

                when (result) {

                    is Resource.Loading -> {
                        _serviceDetail.value = ServiceDetailUIState(isLoading = true)
                    }

                    is Resource.Success -> {
                        val updatedServices: List<ServiceItemDetails> =
                            _serviceDetail.value.serviceDetail?.services?.filter { it.serviceId != serviceId }
                                ?: mutableListOf()
                        var newDetail = _serviceDetail.value.serviceDetail
                        newDetail?.services = updatedServices

                        _serviceDetail.value = ServiceDetailUIState(
                            isLoading = false, serviceDetail = newDetail)
                            hideDeleteDialog()
                    }

                    is Resource.Error -> {

                    }

                    Resource.Idle -> Unit
                }
            }
        }
    }


    fun getServiceDetail() {
        viewModelScope.launch {
            repository.getOwnerServiceDetail(sessionManager.getUserId()).collectLatest { result ->

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
