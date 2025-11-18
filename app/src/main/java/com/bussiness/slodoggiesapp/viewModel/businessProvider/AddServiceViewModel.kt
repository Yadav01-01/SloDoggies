package com.bussiness.slodoggiesapp.viewModel.businessProvider

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bussiness.slodoggiesapp.data.remote.Repository
import com.bussiness.slodoggiesapp.data.uiState.AddServiceUiState
import com.bussiness.slodoggiesapp.network.Resource
import com.bussiness.slodoggiesapp.util.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
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

    private val _selectedPhoto = MutableStateFlow<Uri?>(null)
    val selectedPhoto: StateFlow<Uri?> = _selectedPhoto

    private val _uiEvent = Channel<ServiceEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()



    fun updateTitle(value: String) = updateState { it.copy(title = value) }

    fun updateDescription(value: String) = updateState { it.copy(description = value) }

    fun updateAmount(value: String) = updateState { it.copy(amount = value) }

    fun setSelectedPhoto(uri: Uri?) {
        _selectedPhoto.value = uri
    }

    fun openAddedServiceDialog() =
        updateState { it.copy(addedServiceDialog = true) }

    fun closeAddedServiceDialog() =
        updateState { it.copy(addedServiceDialog = false) }

    fun closeSubscriptionDisclaimer() =
        updateState { it.copy(subscribeDisclaimer = false) }

    private fun updateState(block: (AddServiceUiState) -> AddServiceUiState) {
        _uiState.value = block(_uiState.value)
    }

    private fun validate(): String? {
        val s = _uiState.value
        return when {
            s.title.isBlank()        -> "Enter service title"
            s.description.isBlank()  -> "Enter service description"
            s.amount.isBlank()       -> "Enter price"
            else -> null
        }
    }

    fun addOrUpdateService(
        serviceId: String = "",
        type: String,
        images: List<MultipartBody.Part>
    ) {
        val error = validate()
        if (error != null) {
            sendEvent(ServiceEvent.ShowToast(error))
            return
        }

        viewModelScope.launch {
            repository.addAndUpdateServices(
                userId = sessionManager.getUserId(),
                serviceTitle = _uiState.value.title,
                description = _uiState.value.description,
                images = images,
                price = _uiState.value.amount,
                serviceId = if (type == "addService") "" else serviceId
            ).collect { result ->
                when (result) {
                    is Resource.Loading -> Unit
                    is Resource.Success -> sendEvent(ServiceEvent.Success)
                    is Resource.Error ->
                        sendEvent(ServiceEvent.ShowToast(result.message ?: "Something went wrong"))
                    Resource.Idle -> Unit
                }
            }
        }
    }


    private fun sendEvent(event: ServiceEvent) {
        viewModelScope.launch { _uiEvent.send(event) }
    }
}

sealed class ServiceEvent {
    data class ShowToast(val message: String) : ServiceEvent()
    data object Success : ServiceEvent()
}
