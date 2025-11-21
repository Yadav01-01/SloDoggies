package com.bussiness.slodoggiesapp.data.uiState

import android.net.Uri
import java.net.URI

data class EventCreateOwnerUiState(
    val image: MutableList<Uri>? = null,
    val title: String? = null,
    val description: String? = null,
    val startDate: String? = null,
    val startTime: String? = null,
    val endDate: String? = null,
    val endTime: String? = null,
    val location: String? = null,
    var latitude: String? = null,
    val longitude: String? = null,
    val city: String? = null,
    val state: String? = null,
    val zipcode: String? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
