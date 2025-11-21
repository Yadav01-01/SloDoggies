package com.bussiness.slodoggiesapp.viewModel.businessProvider

import android.net.Uri
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bussiness.slodoggiesapp.data.remote.Repository
import com.bussiness.slodoggiesapp.data.uiState.EditBusinessUiState
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
class EditBusinessViewModel @Inject constructor(
    private val repository: Repository,
    private val sessionManager: SessionManager
) : ViewModel() {


    private val _uiState = MutableStateFlow(EditBusinessUiState())
    val uiState: StateFlow<EditBusinessUiState> = _uiState.asStateFlow()

    init {
        getBusinessProfile()
    }

    // -------------------- UPDATE FUNCTIONS --------------------

    fun updateLogoImage(uri: Uri) {
        _uiState.update { it.copy(businessLogo = uri) }
    }

    fun updateBusinessName(value: String) =
        _uiState.update { it.copy(businessName = value) }

    fun updateProviderName(value: String) =
        _uiState.update { it.copy(providerName = value) }

    fun updateEmail(value: String) =
        _uiState.update { it.copy(email = value) }

    fun updateBio(value: String) =
        _uiState.update { it.copy(bio = value) }

    fun updateBusinessAddress(value: String) =
        _uiState.update { it.copy(businessAddress = value) }

    fun updateCity(value: String) =
        _uiState.update { it.copy(city = value) }

    fun updateState(value: String) =
        _uiState.update { it.copy(state = value) }

    fun updateZipCode(value: String) =
        _uiState.update { it.copy(zipCode = value) }

    fun updateUrl(value: String) =
        _uiState.update { it.copy(url = value) }

    fun updateContact(value: String) =
        _uiState.update { it.copy(contact = value) }

    fun addBusinessPhoto(uri: Uri) {
        _uiState.update { it.copy(businessLogo = uri) }
    }

    fun updateAvailableDays(days: List<String>, fromToTime: String) {
        _uiState.update {
            it.copy(
                availableDays = days,
                fromAndToTime = fromToTime,
            )
        }
    }

    // -------------------- MULTIPLE FILES --------------------

    fun addVerificationDoc(uri: Uri) {
        _uiState.update { it.copy(verificationDocs = it.verificationDocs + uri) }
    }

    // -------------------- DIALOGS --------------------

    fun showImagePickerDialog() =
        _uiState.update { it.copy(showImagePickerDialog = true) }

    fun hideImagePickerDialog() =
        _uiState.update { it.copy(showImagePickerDialog = false) }

    fun showUpdateDialog() =
        _uiState.update { it.copy(showUpdatedDialog = true) }

    fun hideUpdateDialog() =
        _uiState.update { it.copy(showUpdatedDialog = false) }

    fun toggleImagePickerDialog() { _uiState.update { it.copy(showImagePickerDialog = true) } }

    fun toggleUpdateDialog() { _uiState.update { it.copy(showUpdatedDialog = true) } }

    //------------------API CALL-------------
    private fun getBusinessProfile() {
        viewModelScope.launch {
            repository.getBusinessProfileDetail(sessionManager.getUserId())
                .collectLatest { result ->

                    when (result) {

                        is Resource.Loading -> {
                            // Optional: Show loader in UI if needed

                        }

                        is Resource.Success -> {
                            val profile = result.data.data

                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    errorMessage = null,
                                    businessLogo = profile?.businessLogo?.toUri(),
                                    businessName = profile?.name.orEmpty(),
                                    providerName = profile?.providerName.orEmpty(),
                                    email = profile?.email.orEmpty(),
                                    businessAddress = profile?.address.orEmpty(),
                                    city = profile?.city.orEmpty(),
                                    state = profile?.state.orEmpty(),
                                    zipCode = profile?.zipCode.orEmpty(),
                                    contact = profile?.phone.orEmpty(),
                                    url = profile?.websiteUrl.orEmpty()
                                )
                            }
                        }

                        is Resource.Error -> {
                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    errorMessage = result.message ?: "Something went wrong"
                                )
                            }
                        }

                        Resource.Idle -> Unit
                    }

                }
        }
    }


    fun updateBusinessProfile() {
//        viewModelScope.launch {
//            repository.registerAndUpdateBusiness(sessionManager.getUserId(),
//                uiState.value.businessName,
//                uiState.value.email,
//                uiState.value.contact,
//                uiState.value.businessLogo,
//                uiState.value.businessAddress,
//                uiState.value.category,
//                uiState.value.city,
//                uiState.value.state,
//                uiState.value.zipCode,
//                uiState.value.latitude,
//                uiState.value.longitude,
//                uiState.value.url,
//                uiState.value.availableDays,
//                uiState.value.fromAndToTime,
//                uiState.value.verificationDocs)
//        }
    }

}
