package com.bussiness.slodoggiesapp.data.uiState

import android.net.Uri
import java.net.URI

data class PetAddUpDateUiState(
    val name: String? = null,
    val id: String? = null,
    val breed: String? = null,
    val age: String? = "< than 1 Year",
    val bio: String? = null,
    var image: Uri? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
