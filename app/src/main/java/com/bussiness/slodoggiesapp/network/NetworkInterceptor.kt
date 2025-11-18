package com.bussiness.slodoggiesapp.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.bussiness.slodoggiesapp.util.SessionManager
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkInterceptor @Inject constructor(
    @ApplicationContext private val context: Context,
    private val sessionManager: SessionManager
) : Interceptor {

    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isOnline()) {
            throw NoNetworkException()
        }

        val token = sessionManager.getToken()

        val originalRequest = chain.request()
        val requestBuilder = originalRequest.newBuilder()

        //  Add Authorization header if token exists
        if (token.isNotEmpty()) {
            requestBuilder.addHeader("Authorization", "Bearer $token")
        }

        //  Add common headers (optional)
        requestBuilder.addHeader("Accept", "application/json")
        requestBuilder.addHeader("Content-Type", "application/json")

        val newRequest = requestBuilder.build()
        return chain.proceed(newRequest)
    }

    private fun isOnline(): Boolean {
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}
