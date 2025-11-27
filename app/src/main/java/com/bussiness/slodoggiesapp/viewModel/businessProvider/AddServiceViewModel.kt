package com.bussiness.slodoggiesapp.viewModel.businessProvider

import android.content.Context
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia.DefaultTab.PhotosTab.value
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bussiness.slodoggiesapp.data.newModel.businessprofile.BusinessProfileModel
import com.bussiness.slodoggiesapp.data.newModel.servicelist.Data
import com.bussiness.slodoggiesapp.data.newModel.servicelist.ServicesListModel
import com.bussiness.slodoggiesapp.data.remote.Repository
import com.bussiness.slodoggiesapp.data.uiState.AddServiceUiState
import com.bussiness.slodoggiesapp.network.Resource
import com.bussiness.slodoggiesapp.ui.component.common.createMultipartListUriUrl
import com.bussiness.slodoggiesapp.util.Messages
import com.bussiness.slodoggiesapp.util.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class AddServiceViewModel @Inject constructor(
    private val repository: Repository,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddServiceUiState())
    val uiState: StateFlow<AddServiceUiState> = _uiState.asStateFlow()

    private val _uiStateServices = MutableStateFlow(Data())
    val uiStateServices: StateFlow<Data> = _uiStateServices.asStateFlow()


    private val _selectedPhotos = MutableStateFlow<List<String>>(emptyList())
    val selectedPhotos: StateFlow<List<String>> = _selectedPhotos


    fun updateData(model: Data?) {
        val images = model?.service_image ?: emptyList()
        _uiStateServices.value = _uiStateServices.value.copy(
            service_title = model?.service_title.orEmpty(),
            description = model?.description.orEmpty(),
            price = model?.price.orEmpty(),
            id = model?.id ?: 0,
            service_image = images.toMutableList()
        )
        _selectedPhotos.value = images
    }

    fun refresh() {
        _uiStateServices.value = Data(
            service_title = "",
            description = "",
            price = "",
            id = 0,
            service_image = mutableListOf()
        )
        _selectedPhotos.value = emptyList()
    }

    private val _selectedPhoto = MutableStateFlow<Uri?>(null)
    val selectedPhoto: StateFlow<Uri?> = _selectedPhoto
    
    fun updateTitle(data: String) {
        _uiStateServices.update { current ->
            current.copy(service_title = data)
        }
    }


    fun updateDescription(data: String) {
        _uiStateServices.update { current ->
            current.copy(description = data)
        }
    }


    fun updateAmount(data: String) {
        _uiStateServices.update { current ->
            current.copy(price = data)
        }
    }



    @RequiresApi(Build.VERSION_CODES.O)
    fun addOrUpdateService(context: Context, onSuccess: () -> Unit = { }, onError: (String) -> Unit) {
        Log.d("imageSize","*******"+_uiStateServices.value.service_image?.size)
        Log.d("serviceId","*******"+_uiStateServices.value.id)

        if (!validateEvent(_uiStateServices.value, onError)) return

        viewModelScope.launch {
            val imageDocsPart = _uiStateServices.value.service_image?.let {
                createMultipartListUriUrl(
                    context,
                    items = it,
                    keyName = "images[]"
                )
            }
            repository.addAndUpdateServices(
                        userId = sessionManager.getUserId(),
                        serviceTitle = _uiStateServices.value.service_title?:"",
                        description = _uiStateServices.value.description?:"",
                        images = imageDocsPart,
                        price = _uiStateServices.value.price?:"",
                        serviceId = if (_uiStateServices.value.id == null) "" else _uiStateServices.value.id.toString()
            ).collect { result ->
                when (result) {
                    is Resource.Loading -> Unit
                    is Resource.Success -> {
                        onSuccess()
                    }
                    is Resource.Error -> {
                        onError(result.message ?: "Something went wrong")
                    }
                    Resource.Idle -> Unit
                }
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun validateEvent(stateData: Data, onError: (String) -> Unit): Boolean {
        if (stateData.service_title.isNullOrEmpty()) {
            onError(Messages.SERVICES_NAME)
            return false
        }
        if (stateData.description.isNullOrEmpty()) {
            onError(Messages.DESCRIPTION_SMS)
            return false
        }
        if (stateData.price.isNullOrEmpty()) {
            onError(Messages.PRICE_SMS)
            return false
        }
        if (stateData.service_image.isNullOrEmpty()) {
            onError(Messages.UPLOAD_IMAGE_SMS)
            return false
        }
        return true
    }


    fun addPhoto(uri: String) {
        _uiStateServices.update { current ->
            val data = current ?: Data()
            val list = data.service_image?.toMutableList() ?: mutableListOf()
            list.add(uri)
            data.copy(service_image = list)
        }
    }


    fun removePhoto(uri: String) {
        _uiStateServices.update { current ->
            val data = current ?: Data()
            val list = data.service_image?.toMutableList() ?: mutableListOf()
            list.remove(uri)
            data.copy(service_image = list)
        }
    }


}


