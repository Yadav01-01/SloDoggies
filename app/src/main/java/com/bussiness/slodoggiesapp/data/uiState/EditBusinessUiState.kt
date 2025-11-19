package com.bussiness.slodoggiesapp.data.uiState

import android.net.Uri

data class  EditBusinessUiState(
    val businessName: String = "",
    val providerName: String = "",
    val email: String = "",
    val bio: String = "",
    val businessAddress: String = "",
    val city: String = "",
    val state: String = "",
    val zipCode: String = "",
    val landmark: String = "",
    val url: String = "",
    val contact: String = "",

    // Changed to nullable
    val businessLogo: Uri? = null,

    // Multiple documents allowed
    val verificationDocs: List<Uri> = emptyList(),

    val availableDays: List<String> = emptyList(),
    val fromTime: String = "",
    val toTime: String = "",

    val showImagePickerDialog: Boolean = false,
    val showUpdatedDialog: Boolean = false
)
