package com.bussiness.slodoggiesapp.data.uiState

import android.net.Uri

data class BusinessRegistrationUiState(
    val name: String = "",
    val email: String = "",
    val uploadLogo: List<Uri> = emptyList(),
    val categories: List<String> = emptyList(),
    val businessAddress: String = "",
    val city: String = "",
    val state: String = "",
    val zipCode: String = "",
    val website: String = "",
    val contact: String = "",
    val availDays: String = "",
    val availHours: String = "",
    val verificationDocs: List<Uri> = emptyList(),
    val formSubmitted: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
) {
    val isValid: Boolean
        get() = name.isNotBlank() &&
                categories.isNotEmpty() &&      // <-- MUST HAVE AT LEAST 1 CATEGORY
                uploadLogo.isNotEmpty()
}

