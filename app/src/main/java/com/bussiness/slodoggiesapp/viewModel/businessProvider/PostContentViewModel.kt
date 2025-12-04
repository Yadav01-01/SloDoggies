package com.bussiness.slodoggiesapp.viewModel.businessProvider

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bussiness.slodoggiesapp.data.newModel.businessprofile.Data
import com.bussiness.slodoggiesapp.data.newModel.servicelist.ServicesListModel
import com.bussiness.slodoggiesapp.data.remote.Repository
import com.bussiness.slodoggiesapp.data.uiState.AdCreateUiState
import com.bussiness.slodoggiesapp.data.uiState.EventCreateOwnerUiState
import com.bussiness.slodoggiesapp.network.Resource
import com.bussiness.slodoggiesapp.ui.component.common.createMultipartList
import com.bussiness.slodoggiesapp.ui.screens.petowner.post.content.Person
import com.bussiness.slodoggiesapp.util.Messages
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
class PostContentViewModel @Inject constructor(
    private val repository: Repository,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _writePost = MutableStateFlow("")
    val writePost: StateFlow<String> = _writePost

    private val _hashtags = MutableStateFlow("")
    val hashtags: StateFlow<String> = _hashtags

    private val _postalCode = MutableStateFlow("")
    val postalCode: StateFlow<String> = _postalCode

    private val _visibility = MutableStateFlow("Public")
    val visibility: StateFlow<String> = _visibility

    fun updateWritePost(newWritePost: String) {
        _writePost.value = newWritePost.take(500)
    }

    fun updateHashtags(newHashtags: String) {
        _hashtags.value = newHashtags
    }

    fun updatePostalCode(newPostalCode: String) {
        _postalCode.value = newPostalCode
    }

    fun updateVisibility(newVisibility: String) {
        _visibility.value = newVisibility
    }

    //eventScreen

    private val _eventTitle = MutableStateFlow("")
    val eventTitle: StateFlow<String> = _eventTitle

    private val _eventDescription = MutableStateFlow("")
    val eventDescription: StateFlow<String> = _eventDescription

    private val _eventPostalCode = MutableStateFlow("")
    val eventPostalCode: StateFlow<String> = _eventPostalCode

    private val _streetAddress = MutableStateFlow("")
    val streetAddress: StateFlow<String> = _streetAddress

    private val _areaSector = MutableStateFlow("")
    val areaSector: StateFlow<String> = _areaSector

    private val _landmark = MutableStateFlow("")
    val landmark: StateFlow<String> = _landmark

    fun updateLandmark(newLandmark: String) {
        _landmark.value = newLandmark
    }

    fun updateAreaSector(newAreaSector: String) {
        _areaSector.value = newAreaSector
    }

    fun updateStreetAddress(newStreetAddress: String) {
        _streetAddress.value = newStreetAddress
    }

    fun updateEventTitle(newEventTitle: String) {
        _eventTitle.value = newEventTitle
    }

    fun updateEventDescription(newEventDescription: String) {
        _eventDescription.value = newEventDescription.take(500)
    }

    fun updateEventPostalCode(newEventPostalCode: String) {
        _eventPostalCode.value = newEventPostalCode
    }

    //promotion Screen

    private val _uiState = MutableStateFlow(AdCreateUiState())
    val uiState: StateFlow<AdCreateUiState> = _uiState


    private val _title = MutableStateFlow("")
    val title: StateFlow<String> = _title

    /** Selected Image */
    private val _selectedPhotos = MutableStateFlow<List<Uri>>(emptyList())


    private val _uiStateService = MutableStateFlow(ServicesListModel())
    val uiStateService: StateFlow<ServicesListModel> = _uiStateService.asStateFlow()


    fun updateServiceLocation(newServiceLocation: String) {
        _uiState.value = _uiState.value.copy(serviceLocation = newServiceLocation)
    }

    fun updateTitle(newTitle: String) {
        _uiState.value = _uiState.value.copy(adTitle = newTitle)
    }

    fun updateAdDescription(newAdDescription: String) {
        _uiState.value = _uiState.value.copy(adDescription = newAdDescription)
    }


    fun updateTermsAndConditions(newTermsAndConditions: String) {
        _uiState.value = _uiState.value.copy(termAndConditions = newTermsAndConditions)
    }
    fun updateContactInfo(contactNumber: String) {
        _uiState.value = _uiState.value.copy(contactNumber = contactNumber)
    }

    ///////
    var selectedPet by mutableStateOf<Person?>(null)
        private set

    var allPets by mutableStateOf(listOf<Person>())
        private set

    var isSelecting by mutableStateOf(false)

    fun startSelecting() {
        isSelecting = true
    }

    fun selectPerson(pet: Person) {
        selectedPet = pet
        isSelecting = false
    }

    fun setPets(pets: List<Person>) {
        allPets = pets
    }

    fun addCategory(newItem: String) {
        val currentList = _uiState.value.category ?: emptyList()
        _uiState.value = _uiState.value.copy(
            category = currentList + newItem
        )
    }

    fun removeCategory(item: String) {
        val currentList = _uiState.value.category ?: emptyList()
        _uiState.value = _uiState.value.copy(
            category = currentList - item
        )
    }
    fun addService(newItem: String) {
        _uiState.value = _uiState.value.copy(service = newItem)
    }
    fun onLocation(location: String) {
        _uiState.value = _uiState.value.copy(serviceLocation = location)
    }

    fun onLatitude(lat: String) {
        _uiState.value = _uiState.value.copy(latitude = lat)
    }

    fun onLongitude(long: String) {
        _uiState.value = _uiState.value.copy(longitude = long)
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


    fun updateExpireDate(date: String) {
        _uiState.value = _uiState.value.copy(expiryDate=date)

    }

    fun updateExpireTime(time: String) {
        _uiState.value = _uiState.value.copy(expiryTime =time)

    }
    fun updateMobileVisible(visible: String) {
        _uiState.value = _uiState.value.copy(mobile_visual =visible)
        Log.d("******",visible)
    }

    fun updatebudget(visible: String) {
        _uiState.value = _uiState.value.copy(budget =visible)
    }


    fun servicesList(){
        viewModelScope.launch {
            repository.servicesListRequest(sessionManager.getUserId()).collectLatest { result ->
                when (result) {
                    is Resource.Loading -> _uiStateService.update { it.copy(isLoading = true) }
                    is Resource.Success -> {
                        _uiStateService.value = _uiStateService.value.copy(isLoading = false)
                        val response = result.data
                        if (response.success == true && response.data != null) {
                            val data = response.data
                            _uiStateService.value = _uiStateService.value.copy(
                                data = data
                            )
                        } else {
                            onError(response.message ?: "Failed to fetch owner details")
                        }
                    }
                    is Resource.Error -> {
                        _uiStateService.update { it.copy(isLoading = false) }
                        onError(result.message)
                    }
                    Resource.Idle -> Unit
                }

            }
        }
    }

    private fun onError(message: String?) {
        _uiStateService.update { it.copy(error = message ?: "Something went wrong") }
    }

    fun validateAdData(state: AdCreateUiState): Pair<Boolean, String> {
        return when {
            state.image.isNullOrEmpty() ->
                false to Messages.UPLOAD_IMAGE_SMS

            state.adTitle.isNullOrBlank() ->
                false to Messages.EVENT_TITLE_SMS

            state.adDescription.isNullOrBlank() ->
                false to Messages.DESCRIPTION_SMS

            state.category.isNullOrEmpty() ->
                false to Messages.CAT_SMS

            state.service.isNullOrBlank() ->
                false to Messages.SERVICE

            state.expiryDate.isNullOrBlank() ->
                false to Messages.EXPIRE_DATE

            state.expiryTime.isNullOrBlank() ->
                false to Messages.EXPIRE_TIME

            state.termAndConditions.isNullOrBlank() ->
                false to Messages.TERM_CON

            state.serviceLocation.isNullOrBlank() ->
                false to Messages.SERVICELOCATION

            state.contactNumber.isNullOrBlank() ->
                false to Messages.CONTACTNUMBER

            else -> true to "Success"
        }
    }


    fun createAd(context: Context, onSuccess: () -> Unit = { }, onError: (String) -> Unit) {
        val state = _uiState.value
        val imageParts = state.image?.let {
            createMultipartList(
                context = context,
                uris = it,
                keyName = "images[]"
            )
        }
        viewModelScope.launch {
            repository.createAd(
                adTitle = state.adTitle?:"",
                adDescription = state.adDescription?:"",
                serviceLocation = state.serviceLocation?:"",
                latitude = state.latitude?:"",
                longitude = state.longitude?:"",
                userId = sessionManager.getUserId(),
                category = (state.category ?: emptyList()).joinToString(","),
                service =state.service?:"",
                expiry_date =state.expiryDate?:"",
                expiry_time =state.expiryTime?:"",
                termAndConditions =state.termAndConditions?:"",
                contactNumber =state.contactNumber?:"",
                budget =state.budget?:"",
                mobile_visual =state.mobile_visual,
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




}

