package com.bussiness.slodoggiesapp.data.uiState

import android.net.Uri
import java.net.URI

data class PostCreateOwnerUiState(
    val writePost: String? = null,
    val petId: String? = null,
    val hashTage: String? = null,
    var location: String? = null,
    var lattitude: String? = null,
    var longitude: String? = null,
    val isLoading: Boolean = false,
    val image: MutableList<Uri>? = null,
    val error: String? = null,

    )
