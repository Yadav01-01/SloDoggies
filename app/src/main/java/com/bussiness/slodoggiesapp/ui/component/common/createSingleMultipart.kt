package com.bussiness.slodoggiesapp.ui.component.common

import android.content.Context
import android.net.Uri
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File



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




//fun createSingleMultipart(
//    uri: Uri,
//    keyName: String
//): MultipartBody.Part {
//    // Convert URI to File
//    val file = File(uri.path!!) // Be careful: sometimes you need a helper function to get real file path
//    val requestBody = RequestBody.create("image/*".toMediaTypeOrNull(), file)
//    return MultipartBody.Part.createFormData(keyName, file.name, requestBody)
//}
