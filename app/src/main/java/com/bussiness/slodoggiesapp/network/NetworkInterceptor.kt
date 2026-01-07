package com.bussiness.slodoggiesapp.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import android.widget.Toast
import com.bussiness.slodoggiesapp.BuildConfig
import com.bussiness.slodoggiesapp.navigation.Routes
import com.bussiness.slodoggiesapp.util.SessionManager
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.MultipartBody
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkInterceptor @Inject constructor(
    @ApplicationContext private val context: Context,
    private val sessionManager: SessionManager,
    private val authEventBus: AuthEventBus
) : Interceptor {

    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

//    override fun intercept(chain: Interceptor.Chain): Response {
//        if (!isOnline()) {
//            throw NoNetworkException()
//        }
//
//        val token = sessionManager.getToken()
//
//        val originalRequest = chain.request()
//        val requestBuilder = originalRequest.newBuilder()
//
//        //  Add Authorization header if token exists
//        if (token.isNotEmpty()) {
//            requestBuilder.addHeader("Authorization", "Bearer $token")
//        }
//
//        //  Add common headers (optional)
//        requestBuilder.addHeader("Accept", "application/json")
//        requestBuilder.addHeader("Content-Type", "application/json")
//
//        val newRequest = requestBuilder.build()
//        return chain.proceed(newRequest)
//    }


    override fun intercept(chain: Interceptor.Chain): Response {

        if (!isOnline()) {
        throw NoNetworkException()
    }

        val token = sessionManager.getToken()
        Log.d("TESTING-TOKEN",""+token)

        val originalRequest = chain.request()

        val requestBuilder = originalRequest.newBuilder()


    if (token.isNotEmpty()) {
        requestBuilder.addHeader("Authorization", "Bearer $token")
    }

    requestBuilder.addHeader("Accept", "application/json")

    val newRequest = requestBuilder.build()

    if (BuildConfig.DEBUG) {
        Log.d("API_REQUEST", "URL: ${newRequest.url}")
        Log.d("API_REQUEST", "Method: ${newRequest.method}")
        Log.d("API_REQUEST", "Headers: ${newRequest.headers}")

        val body = newRequest.body

        when (body) {
            is MultipartBody -> {
                Log.d("API_REQUEST", "Multipart Form Data:")
                body.parts.forEachIndexed { index, part ->
                    Log.d("API_REQUEST", "Part $index Headers: ${part.headers}")
                }
            }

            else -> {
                body?.let {
                    val buffer = okio.Buffer()
                    it.writeTo(buffer)
                    Log.d("API_REQUEST", "Body: ${buffer.readUtf8()}")
                }
            }
        }
    }

    val response = chain.proceed(newRequest)
    val responseBody = response.body
    val responseBodyString = responseBody?.string()


        if (response.code == 401) {
            response.close()
            val sessionManager = SessionManager(context)
            Toast.makeText(context,"Session Expired",Toast.LENGTH_LONG).show()
            sessionManager.clearSession()
            authEventBus.sessionExpired.tryEmit(Unit)
        //    authNavController.navigate(Routes.JOIN_THE_PACK)
        }

    if (BuildConfig.DEBUG) {
        Log.d("API_RESPONSE", "URL: ${response.request.url}")
        Log.d("API_RESPONSE", "Code: ${response.code}")
        Log.d("API_RESPONSE", "Body: $responseBodyString")
    }

    // IMPORTANT: Re-create response body
    return response.newBuilder()
    .body(responseBodyString?.toResponseBody(responseBody?.contentType()))
    .build()
}

private fun isOnline(): Boolean {
    val network = connectivityManager.activeNetwork ?: return false
    val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
    return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
}

}
