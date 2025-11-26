package com.bussiness.slodoggiesapp.ui.component.common

import android.content.Context
import android.net.Uri
import android.util.Log
import com.bussiness.slodoggiesapp.util.LocationUtils.Companion.removeBaseUrl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.net.URL

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

suspend fun createMultipartListUriUrl(
    context: Context,
    items: List<String>, // can be Uri.toString() or URL string
    keyName: String
): List<MultipartBody.Part> = withContext(Dispatchers.IO) {
    val parts = mutableListOf<MultipartBody.Part>()

    items.forEachIndexed { index, item ->

        val bytes: ByteArray? = if (item.startsWith("http://") || item.startsWith("https://")) {
            // Remote URL: download bytes
            try {
                val url = URL(item)
                url.openStream().use { it.readBytes() }
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        } else {
            // Local URI
            try {
                val uri = Uri.parse(item)
                context.contentResolver.openInputStream(uri)?.use { it.readBytes() }
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }

        bytes?.let {
            val fileName = "file_${System.currentTimeMillis()}_$index.jpg"
            val body = it.toRequestBody("image/*".toMediaType())
            parts.add(
                MultipartBody.Part.createFormData(
                    keyName,
                    fileName,
                    body
                )
            )
        }
    }

    parts
}

