package com.bussiness.slodoggiesapp.ui.component.common

import android.content.Context
import android.net.Uri
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody

fun createMultipartList(
    context: Context,
    uris: List<Uri>,
    keyName: String
): List<MultipartBody.Part> {

    val parts = mutableListOf<MultipartBody.Part>()

    uris.forEachIndexed { index, uri ->
        context.contentResolver.openInputStream(uri)?.use { input ->
            val bytes = input.readBytes()
            val fileName = "pet_image_${System.currentTimeMillis()}_$index.jpg"

            val body = bytes.toRequestBody("image/*".toMediaType())
            parts.add(
                MultipartBody.Part.createFormData(
                    keyName,
                    fileName,
                    body
                )
            )
        }
    }

    return parts
}
