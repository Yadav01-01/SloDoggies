package com.bussiness.slodoggiesapp.util

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

fun Map<String, String>.toRequestBodyMap(): Map<String, RequestBody> {
    return this.mapValues {
        it.value.toRequestBody("text/plain".toMediaTypeOrNull())
    }
}