package com.bussiness.slodoggiesapp.viewModel.businessProvider

import android.net.Uri
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class EditBusinessViewModel @Inject constructor() : ViewModel() {

    data class UiState(
        val profileImageUri: Uri? = null,
        val businessName: String = "",
        val providerName: String = "",
        val email: String = "",
        val bio: String = "",
        val businessAddress: String = "",
        val city: String = "",
        val landmark: String = "",
        val url: String = "",
        val contact: String = "",
        val businessLogos: List<Uri> = emptyList(),
        val verificationDocs: List<Uri> = emptyList(),
        val availableDays: List<String> = emptyList(),
        val fromTime: String = "",
        val toTime: String = "",
        val zipCode: String = "",
        val showImagePickerDialog: Boolean = false
    )

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    fun updateProfileImage(uri: Uri) {
        _uiState.update { it.copy(profileImageUri = uri) }
    }

    fun updateBusinessName(value: String) {
        _uiState.update { it.copy(businessName = value) }
    }

    fun updateProviderName(value: String) {
        _uiState.update { it.copy(providerName = value) }
    }

    fun updateEmail(value: String) {
        _uiState.update { it.copy(email = value) }
    }

    fun updateBio(value: String) {
        _uiState.update { it.copy(bio = value) }
    }

    fun updateBusinessAddress(value: String) {
        _uiState.update { it.copy(businessAddress = value) }
    }

    fun updateCity(value: String) {
        _uiState.update { it.copy(city = value) }
    }

    fun updateLandmark(value: String) {
        _uiState.update { it.copy(landmark = value) }
    }

    fun updateZipCode(value: String) {
        _uiState.update { it.copy(zipCode = value) }
    }

    fun updateUrl(value: String) {
        _uiState.update { it.copy(url = value) }
    }

    fun updateContact(value: String) {
        _uiState.update { it.copy(contact = value) }
    }

    fun updateAvailableDays(days: List<String>, from: String, to: String) {
        _uiState.update { it.copy(availableDays = days, fromTime = from, toTime = to) }
    }

    fun addBusinessLogo(uri: Uri) {
        _uiState.update { it.copy(businessLogos = it.businessLogos + uri) }
    }

    fun addVerificationDoc(uri: Uri) {
        _uiState.update { it.copy(verificationDocs = it.verificationDocs + uri) }
    }

    fun toggleImagePickerDialog() {
        _uiState.update { it.copy(showImagePickerDialog = true) }
    }

    fun hideImagePickerDialog() {
        _uiState.update { it.copy(showImagePickerDialog = false) }
    }
}
