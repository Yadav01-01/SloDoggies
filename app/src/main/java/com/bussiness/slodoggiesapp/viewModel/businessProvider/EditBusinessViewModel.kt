package com.bussiness.slodoggiesapp.viewModel.businessProvider

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.bussiness.slodoggiesapp.data.uiState.EditBusinessUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class EditBusinessViewModel @Inject constructor() : ViewModel() {



    private val _uiState = MutableStateFlow(EditBusinessUiState())
    val uiState: StateFlow<EditBusinessUiState> = _uiState.asStateFlow()

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

    fun updateLandmark(value: String) =
        _uiState.update { it.copy(landmark = value) }

    fun updateUrl(value: String) =
        _uiState.update { it.copy(url = value) }

    fun updateContact(value: String) =
        _uiState.update { it.copy(contact = value) }

    fun updateAvailableDays(days: List<String>, from: String, to: String) {
        _uiState.update {
            it.copy(
                availableDays = days,
                fromTime = from,
                toTime = to
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
}
