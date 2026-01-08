package com.bussiness.slodoggiesapp.viewModel.petOwner.servicesVM

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bussiness.slodoggiesapp.data.model.petOwner.ServiceDetailModel
import com.bussiness.slodoggiesapp.data.newModel.home.PostItem
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
import kotlinx.coroutines.flow.update
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

    private val _errorMessage = MutableSharedFlow<String>()
    val errorMessage = _errorMessage.asSharedFlow()

    private val _showDeleteDialog = MutableStateFlow(false)
    val showDeleteDialog: StateFlow<Boolean> = _showDeleteDialog
    private val _reviewSubmitted = MutableSharedFlow<Boolean>()
    val reviewSubmitted = _reviewSubmitted.asSharedFlow()

    private val _isFollow = MutableStateFlow(false)
    val isFollow : StateFlow<Boolean> =_isFollow


    var serviceId :String =""

    fun showDeleteDialog() {
        _showDeleteDialog.value = true
    }


    fun hideDeleteDialog() {
        _showDeleteDialog.value = false
    }

    fun setIsFollow(status:Boolean){
     //   _isFollow.value = status
        _isFollow.update { status }
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
                       setServiceDetail(serviceId.toString())
                   }

                   is Resource.Error -> {
                       _errorMessage.emit(
                           result.message ?: "Something went wrong"
                       )
                   }

                   Resource.Idle -> Unit
               }
           }
       }
   }


    fun callCheckFollowStatus(businessId:String){
        viewModelScope.launch {
            repository.businessFollow(businessId).collectLatest { result ->

                when (result) {

                    is Resource.Loading -> {
                        // _serviceDetail.value = ServiceDetailUIState(isLoading = true)
                    }

                    is Resource.Success -> {
                        setIsFollow(result.data.data?.isFollower ?:false)

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
                        Log.d("TESTING_SERVICE","Inside Api Re")
                        _serviceDetail.value = ServiceDetailUIState(
                            isLoading = false,
                            serviceDetail = result.data.data
                        )
                    }

                    is Resource.Error -> {
                        Log.d("TESTING_SERVICE","Inside Api Re"+result.message)
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


    fun addAndRemoveFollowers(
        followedId: String,
        onError: (String) -> Unit
    ) {

        viewModelScope.launch {
            repository.addAndRemoveFollowers(
                userId = sessionManager.getUserId(),
                followerId = followedId
            ).collectLatest { result ->
                when (result) {

                    is Resource.Success -> {
                        if (result.data.success) {
                            //API success → stop loader ONLY
                            _isFollow.value = !_isFollow.value

                            setIsFollow(_isFollow.value)
                        }else{
                         //   setIsFollow(false)
                        }
                    }

                    is Resource.Error -> {
                     setIsFollow(false)
                    }

                    else -> Unit
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
