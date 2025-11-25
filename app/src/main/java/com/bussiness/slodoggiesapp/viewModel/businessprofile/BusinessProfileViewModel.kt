package com.bussiness.slodoggiesapp.viewModel.businessprofile

import android.content.Context
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bussiness.slodoggiesapp.data.newModel.businessprofile.BusinessProfileModel
import com.bussiness.slodoggiesapp.data.newModel.businessprofile.Data
import com.bussiness.slodoggiesapp.data.remote.Repository
import com.bussiness.slodoggiesapp.network.Resource
import com.bussiness.slodoggiesapp.ui.component.common.createMultipartList
import com.bussiness.slodoggiesapp.ui.component.common.createMultipartListUriUrl
import com.bussiness.slodoggiesapp.ui.component.common.createSingleMultipart
import com.bussiness.slodoggiesapp.ui.component.common.createSingleMultipartUrlUri
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
class BusinessProfileViewModel @Inject constructor(
    private val repository: Repository,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(BusinessProfileModel())
    val uiState: StateFlow<BusinessProfileModel> = _uiState.asStateFlow()

    private val _selectedPhotos = MutableStateFlow<List<String>>(emptyList())

    // List of business logos
    private val _businessLogo = MutableStateFlow<List<String>>(emptyList())
    val businessLogo: StateFlow<List<String>> = _businessLogo


    init {
        getPreFillDetail()
    }

    private var originalEmail: String? = null

    private var originalPhone: String? = null

    fun setInitialEmail(email: String, verified: Boolean) {
        originalEmail = email
        _uiState.update { state ->
            val current = state.data ?: Data()
            state.copy(
                data = current.copy(
                    email = email,
                    emailVerify = verified
                )
            )
        }
    }


    fun setInitialPhone(phone: String, verified: Boolean) {
        originalPhone = phone
        _uiState.update { state ->
            val current = state.data ?: Data()
            state.copy(
                data = current.copy(
                    phone = phone,
                    phoneVerify = verified
                )
            )
        }
    }

    fun updateEmail(value: String) {
        _uiState.update { state ->
            val current = state.data ?: Data()
            val trimmed = value.trim()

            state.copy(
                data = current.copy(
                    email = trimmed,
                    emailVerify = trimmed == originalEmail
                )
            )
        }
    }



    fun updateContact(value: String) {
        _uiState.update { state ->
            val current = state.data ?: Data()
            val trimmed = value.trim()

            state.copy(
                data = current.copy(
                    phone = trimmed,
                    phoneVerify = trimmed == originalPhone
                )
            )
        }
    }

    // --- Field Updaters ---
    fun updateBusinessName(value: String) {
        _uiState.update { state ->
            val data = state.data ?: Data()
            state.copy(
                data = data.copy(
                    business_name = value
                )
            )
        }
    }

    fun updateBio(value: String) {
        _uiState.update { state ->
            val data = state.data ?: Data()
            state.copy(
                data = data.copy(
                    bio = value
                )
            )
        }
    }

    fun updateProviderName(value: String) {
        _uiState.update { state ->
            val data = state.data ?: Data()
            state.copy(
                data = data.copy(
                    provider_name = value
                )
            )
        }
    }

    fun updateBusinessAddress(value: String) {
        _uiState.update { state ->
            val data = state.data ?: Data()
            state.copy(
                data = data.copy(
                    address = value.trim()
                )
            )
        }
    }

    fun addCategory(value: String) {
        _uiState.update { state ->
            val data = state.data ?: Data()
            val currentList = data.category ?: mutableListOf()
            // Duplicate avoid
            val updatedList = currentList.toMutableList().apply {
                if (!contains(value.trim())) add(value.trim())
            }
            state.copy(
                data = data.copy(
                    category = updatedList
                )
            )
        }
    }

    fun removeCategory(value: String) {
        _uiState.update { state ->
            val data = state.data ?: Data()
            val currentList = data.category ?: mutableListOf()
            val updatedList = currentList.toMutableList().apply {
                remove(value.trim())
            }
            state.copy(
                data = data.copy(
                    category = updatedList
                )
            )
        }
    }


    fun updateWebsite(value: String) {
        _uiState.update { state ->
            val data = state.data ?: Data()
            state.copy(
                data = data.copy(
                    website_url = value.trim()
                )
            )
        }
    }

    fun updateCity(value: String) {
        _uiState.update { state ->
            val data = state.data ?: Data()
            state.copy(
                data = data.copy(
                    city = value.trim()
                )
            )
        }
    }
    fun updateState(value: String) {
        _uiState.update { state ->
            val data = state.data ?: Data()
            state.copy(
                data = data.copy(
                    state = value.trim()
                )
            )
        }
    }

    fun updateZip(value: String) {
        _uiState.update { state ->
            val data = state.data ?: Data()
            state.copy(
                data = data.copy(
                    zip_code = value.trim()
                )
            )
        }
    }

    fun updateAvailableDays(value: List<String>) {
        _uiState.update { state ->
            val data = state.data ?: Data()
            state.copy(
                data = data.copy(
                    available_days = value.toMutableList()
                )
            )
        }
    }


    fun updateFromToTime(value: String) {
        _uiState.update { state ->
            val data = state.data ?: Data()
            state.copy(
                data = data.copy(
                    available_time = value
                )
            )
        }
    }

    // Add a logo
    fun addBusinessLogo(uri: String) {
        _businessLogo.value = listOf(uri) // replaces the list with single image
        _uiState.update { state ->
            val data = state.data ?: Data()
            state.copy(
                data = data.copy(
                    business_logo = uri
                )
            )
        }
    }

    fun removeBusinessLogo(uri: String?) {
        _businessLogo.update { currentList ->
            if (uri == null) {
                emptyList()   // null pass → clear the list
            } else {
                currentList - uri   // normal remove
            }
        }
    }

    fun updateLatitude(value: String) {
        _uiState.update { state ->
            val data = state.data ?: Data()
            state.copy(
                data = data.copy(
                    latitude = value.trim()
                )
            )
        }
    }

    fun updateLongitude(value: String) {
        _uiState.update { state ->
            val data = state.data ?: Data()
            state.copy(
                data = data.copy(
                    longitude = value.trim()
                )
            )
        }
    }

    fun addPhoto(uri: String) {
        val updatedPhotos = _selectedPhotos.value + uri
        _selectedPhotos.value = updatedPhotos
        _uiState.update { state ->
            val data = state.data ?: Data()
            state.copy(
                data = data.copy(
                    verification_docs = updatedPhotos.toMutableList()
                )
            )
        }
    }

    fun removePhoto(uri: String) {
        val updatedPhotos = _selectedPhotos.value - uri
        _selectedPhotos.value = updatedPhotos
        _uiState.update { state ->
            val data = state.data ?: Data()
            state.copy(
                data = data.copy(
                    verification_docs = updatedPhotos.toMutableList()
                )
            )
        }
    }

    private fun getPreFillDetail(){
        Log.d("Api Call from edit section","*****")
        viewModelScope.launch {
            repository.getBusinessProfile(sessionManager.getUserId()).collectLatest { result ->
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
                            data.email?.let {
                                if (it.equals("",true)){
                                    setInitialEmail(email = "", verified = false)
                                }else{
                                    setInitialEmail(email =it, verified = true)
                                }
                            }?: kotlin.run {
                                setInitialEmail(email = "", verified = false)
                            }
                            data.phone?.let {
                                if (it.equals("",true)){
                                    setInitialPhone(phone = "", verified = false)
                                }else{
                                    setInitialPhone(phone = it, verified = true)
                                }
                            }?: kotlin.run {
                                setInitialPhone("", verified =  false)
                            }
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

    private fun clearPhotos() {
        _selectedPhotos.value = emptyList()
        _uiState.update { state ->
            val data = state.data ?: Data()
            state.copy(
                data = data.copy(
                    verification_docs = null
                )
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun upDateRegistration(context: Context, onSuccess: () -> Unit = { }, onError: (String) -> Unit){
        val state = _uiState.value
        if (!validateEvent(state, onError)) return
         viewModelScope.launch {
             val businessLogo = state.data?.business_logo?.let { createSingleMultipartUrlUri(context,uriOrUrl = it, keyName = "business_logo") }
             val imageParts = state.data?.verification_docs?.let {
                createMultipartListUriUrl(context = context, items = it, keyName = "verification_docs[]")
            }
            val stateData = _uiState.value.data
            repository.updateRegistrationRequest(
                userId = sessionManager.getUserId(),
                businessName = stateData?.business_name?:"",
                providerName = stateData?.provider_name?:"",
                email =stateData?.email?:"",
                businessLogo = businessLogo,
                businessCategory = stateData?.category?.joinToString(separator = ",") ?: "",
                businessAddress =stateData?.address?:"",
                latitude = stateData?.latitude?:"",
                longitude = stateData?.longitude?:"",
                city = stateData?.city?:"",
                state = stateData?.state?:"",
                zipCode = stateData?.zip_code?:"",
                websiteUrl = stateData?.website_url?:"",
                contactNumber = stateData?.phone?:"",
                availableDays = stateData?.available_days?.joinToString(separator = ",") ?: "",
                availableTime =stateData?.available_time?:"",
                bio =stateData?.bio?:"",
                imageDoc = imageParts,
            ).collectLatest { result ->
                when (result) {
                    is Resource.Loading -> {
                        _uiState.value = _uiState.value.copy(isLoading = true)
                    }
                    is Resource.Success -> {
                        _uiState.value = _uiState.value.copy(isLoading = false)
                        result.data.let { response ->
                            if (response.success) {
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

    @RequiresApi(Build.VERSION_CODES.O)
    private fun validateEvent(stateData: BusinessProfileModel, onError: (String) -> Unit): Boolean {
        val state=stateData.data
        if (state?.business_name.isNullOrEmpty()) {
            onError(Messages.BUSINESS_NAME)
            return false
        }
        if (state?.email.isNullOrEmpty()) {
            onError(Messages.EMAIL_NAME)
            return false
        }
        if (state?.emailVerify==false) {
            onError(Messages.EMAIL_VERIFY_STATUS)
            return false
        }
        /*if (state?.business_logo == null) {
            onError(Messages.BUSINESS_LOGO)
            return false
        }*/
        if (state?.category.isNullOrEmpty()) {
            onError(Messages.CAT_SMS)
            return false
        }
        if (state?.address.isNullOrEmpty()) {
            onError(Messages.ADDRESS_SMS)
            return false
        }
        if (state?.city.isNullOrEmpty()) {
            onError(Messages.CITY_SMS)
            return false
        }
        if (state?.state.isNullOrEmpty()) {
            onError(Messages.STATE_SMS)
            return false
        }
        if (state?.zip_code.isNullOrEmpty()) {
            onError(Messages.ZIP_CODE_SMS)
            return false
        }
        if (state?.phone.isNullOrEmpty()) {
            onError(Messages.PHONE_NAME)
            return false
        }
        if (state?.phoneVerify == false) {
            onError(Messages.PHONE_VERIFY_STATUS)
            return false
        }
        return true
    }


}