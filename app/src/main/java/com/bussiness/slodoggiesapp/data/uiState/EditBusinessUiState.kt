package com.bussiness.slodoggiesapp.data.uiState

import android.net.Uri

data class  EditBusinessUiState(
    var businessName: String = "",
    var providerName: String = "",
    var email: String = "",
    var bio: String = "",
    var businessAddress: String = "",
    var category: String = "",
    var city: String = "",
    val state: String = "",
    val zipCode: String = "",
    val url: String = "",
    val contact: String = "",
    val latitude: String = "",
    val longitude: String = "",

    // Changed to nullable
    val businessLogo: Uri? = null,

    // Multiple documents allowed
    val verificationDocs: List<Uri> = emptyList(),

    val availableDays: List<String> = emptyList(),
    val fromAndToTime: String = "",

    val showImagePickerDialog: Boolean = false,
    val showUpdatedDialog: Boolean = false,
    val errorMessage: String? = null,
    val isLoading : Boolean = false

)
