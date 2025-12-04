package com.bussiness.slodoggiesapp.data.uiState

import android.net.Uri

data class AdCreateUiState(val adTitle: String? = null,
                           val adDescription: String? = null,
                           val category: List<String>? = null,
                           val service: String? = null,
                           val expiryDate: String? = null,
                           val expiryTime: String? = null,
                           val termAndConditions: String? = null,
                           val latitude: String? = null,
                           val longitude: String? = null,
                           val serviceLocation: String? = null,
                           val contactNumber: String? = null,
                           val budget: String? = null,
                           val isLoading: Boolean = false,
                           val mobile_visual: String = "0",
                           val image: MutableList<Uri>? = null,
                           val error: String? = null)
