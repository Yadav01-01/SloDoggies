package com.bussiness.slodoggiesapp.viewModel.petOwner.createeventowner

import android.content.Context
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bussiness.slodoggiesapp.data.remote.Repository
import com.bussiness.slodoggiesapp.data.uiState.EventCreateOwnerUiState
import com.bussiness.slodoggiesapp.network.Resource
import com.bussiness.slodoggiesapp.ui.component.common.createMultipartList
import com.bussiness.slodoggiesapp.util.Messages
import com.bussiness.slodoggiesapp.util.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class EventCreateOwnerViewModel @Inject constructor(private val repository: Repository,
                                                    private var sessionManager: SessionManager) : ViewModel() {

    private val _uiState = MutableStateFlow(EventCreateOwnerUiState())
    val uiState: StateFlow<EventCreateOwnerUiState> = _uiState

    /** Selected Image */
    private val _selectedPhotos = MutableStateFlow<List<Uri>>(emptyList())

    fun onTitlePost(title: String) {
        _uiState.value = _uiState.value.copy(title = title)
    }

    fun onDescriptionChange(description: String) {
        _uiState.value = _uiState.value.copy(description = description.take(500))
    }

    fun onStartDateChange(startDate: String) {
        _uiState.value = _uiState.value.copy(startDate = startDate)
    }

    fun onStartTimeChange(startTime: String) {
        _uiState.value = _uiState.value.copy(startTime = startTime)
    }

    fun onEndDateChange(endDate: String) {
        _uiState.value = _uiState.value.copy(endDate = endDate)
    }
    fun onEndTimeChange(endTime: String) {
        _uiState.value = _uiState.value.copy(endTime = endTime)
    }

    fun onLocation(location: String) {
        _uiState.value = _uiState.value.copy(location = location)
    }

    fun onLatitude(lat: String) {
        _uiState.value = _uiState.value.copy(latitude = lat)
    }

    fun onLongitude(long: String) {
        _uiState.value = _uiState.value.copy(longitude = long)
    }

    fun onCity(city: String) {
        _uiState.value = _uiState.value.copy(city = city)
    }
    fun onState(state: String) {
        _uiState.value = _uiState.value.copy(state = state)
    }

    fun onZipCode(zipcode: String) {
        _uiState.value = _uiState.value.copy(zipcode = zipcode)
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


    @RequiresApi(Build.VERSION_CODES.O)
    fun createEventOwner(context: Context, onSuccess: () -> Unit = { }, onError: (String) -> Unit) {
        val state = _uiState.value

        if (!validateEvent(state, onError)) return
        val imageParts = state.image?.let {
            createMultipartList(
                context = context,
                uris = it,
                keyName = "images[]"
            )
        }
        viewModelScope.launch {
            repository.createEventOwnerRequest(
                userId = sessionManager.getUserId(),
                postTitle = state.title?:"",
                eventDescription =state.description?:"",
                eventStartDate = state.startDate?:"",
                eventStartTime = state.startTime?:"",
                eventEndDate =state.endDate?:"",
                eventEndTime =state.endTime?:"",
                address = state.location?:"",
                latitude = state.latitude?:"",
                longitude = state.longitude?:"",
                city = state.city?:"",
                state = state.state?:"",
                zipCode = state.zipcode?:"",
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

    private fun clearState() {
        _uiState.value = EventCreateOwnerUiState()
    }
    private fun clearPhotos() {
        _selectedPhotos.value = emptyList()
        _uiState.value = _uiState.value.copy(image = null)
    }

    // Validation function
    @RequiresApi(Build.VERSION_CODES.O)
    private fun validateEvent(state: EventCreateOwnerUiState, onError: (String) -> Unit): Boolean {
        if (state.image.isNullOrEmpty()) {
            onError(Messages.UPLOAD_IMAGE_SMS)
            return false
        }
        if (state.title.isNullOrEmpty()) {
            onError(Messages.EVENT_TITLE_SMS)
            return false
        }
        if (state.description.isNullOrEmpty()) {
            onError(Messages.EVENT_DESCRIPTION_SMS)
            return false
        }
        if (state.startDate.isNullOrEmpty()) {
            onError(Messages.START_DATE_SMS)
            return false
        }
        if (state.endDate.isNullOrEmpty()) {
            onError(Messages.END_DATE_SMS)
            return false
        }
        if (!isEndDateTimeValid(state.startDate, state.startTime, state.endDate, state.endTime)) {
            onError(Messages.DATE_TIME_COMPARE_SMS)
            return false
        }
        if (state.location.isNullOrEmpty()) {
            onError(Messages.EVENT_LOCATION_SMS)
            return false
        }
        if (state.city.isNullOrEmpty()) {
            onError(Messages.CITY_SMS)
            return false
        }
        if (state.state.isNullOrEmpty()) {
            onError(Messages.STATE_SMS)
            return false
        }
        if (state.zipcode.isNullOrEmpty()) {
            onError(Messages.ZIP_CODE_SMS)
            return false
        }
        return true
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun isEndDateTimeValid(
        startDate: String?,      // "2025-11-28"
        startTime: String?,      // "12:05 AM" or "03:40 PM"
        endDate: String?,        // "2025-11-28"
        endTime: String?         // "01:20 PM"
    ): Boolean {
        return try {
            // Convert AM/PM time to 24-hour LocalTime
            fun parseTimeWithAmPm(time: String): LocalTime {
                val parts = time.trim().split(" ")
                val hm = parts[0].split(":")
                val hour = hm[0].toInt()
                val minute = hm[1].toInt()
                val amPm = parts[1].uppercase()
                val hour24 = when {
                    amPm == "AM" && hour == 12 -> 0        // 12 AM → 00
                    amPm == "PM" && hour != 12 -> hour + 12 // PM → +12 except 12PM
                    else -> hour
                }
                return LocalTime.of(hour24, minute)
            }
            // Parse Dates
            val startLocalDate = LocalDate.parse(startDate)
            val endLocalDate = LocalDate.parse(endDate)
            // Parse Times (AM/PM supported)
            val startLocalTime = parseTimeWithAmPm(startTime!!)
            val endLocalTime = parseTimeWithAmPm(endTime!!)
            // Final LocalDateTime
            val startDateTime = LocalDateTime.of(startLocalDate, startLocalTime)
            val endDateTime = LocalDateTime.of(endLocalDate, endLocalTime)
            // Rule: end must be strictly greater
            endDateTime.isAfter(startDateTime)
        } catch (e: Exception) {
            false
        }
    }
}



