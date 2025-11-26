package com.bussiness.slodoggiesapp.ui.component.common

import android.content.Context
import android.net.Uri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.net.URL


fun createSingleMultipart(
    context: Context,
    uri: Uri,
    keyName: String
): MultipartBody.Part? {

    context.contentResolver.openInputStream(uri)?.use { input ->
        val bytes = input.readBytes()

        val fileName = "pet_image_${System.currentTimeMillis()}.jpg"

        val body = bytes.toRequestBody("image/*".toMediaType())

        return MultipartBody.Part.createFormData(
            keyName,
            fileName,
            body
        )
    }

    return null
}

suspend fun createSingleMultipartUrlUri(
    context: Context,
    uriOrUrl: String, // can be Uri.toString() or remote URL
    keyName: String
): MultipartBody.Part? = withContext(Dispatchers.IO) {

    val bytes: ByteArray? = if (uriOrUrl.startsWith("http://") || uriOrUrl.startsWith("https://")) {
        // Remote URL: download the image bytes
        try {
            val url = URL(uriOrUrl)
            url.openStream().use { it.readBytes() }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    } else {
        // Local URI
        try {
            val uri = Uri.parse(uriOrUrl)
            context.contentResolver.openInputStream(uri)?.use { it.readBytes() }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    bytes?.let {
        val fileName = "file_${System.currentTimeMillis()}.jpg"
        val body = it.toRequestBody("image/*".toMediaType())
        MultipartBody.Part.createFormData(
            keyName,
            fileName,
            body
        )
    }
}





//fun createSingleMultipart(
//    uri: Uri,
//    keyName: String
//): MultipartBody.Part {
//    // Convert URI to File
//    val file = File(uri.path!!) // Be careful: sometimes you need a helper function to get real file path
//    val requestBody = RequestBody.create("image/*".toMediaTypeOrNull(), file)
//    return MultipartBody.Part.createFormData(keyName, file.name, requestBody)
//}
