package com.bussiness.slodoggiesapp.viewModel.petOwner.servicesVM


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bussiness.slodoggiesapp.model.petOwner.ServiceDetailModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ServiceDetailViewModel @Inject constructor() : ViewModel() {

    private val _serviceDetail = MutableStateFlow<ServiceDetailModel?>(null)
    val serviceDetail: StateFlow<ServiceDetailModel?> = _serviceDetail

    fun setServiceDetail(detail: ServiceDetailModel) {
        viewModelScope.launch {
            _serviceDetail.value = detail
        }
    }
}
