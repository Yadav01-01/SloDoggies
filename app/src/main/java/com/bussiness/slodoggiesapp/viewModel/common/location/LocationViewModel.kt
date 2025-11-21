package com.bussiness.slodoggiesapp.viewModel.common.location

import android.annotation.SuppressLint
import android.app.Application
import android.location.Geocoder
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.bussiness.slodoggiesapp.data.uiState.LocationUiState
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.Locale

sealed class LocationAction {
    data object RequestPermission : LocationAction()
    data object AskToEnableGPS : LocationAction()
    data object FetchLocation : LocationAction()
    data class FetchSuccess(val address: String) : LocationAction()
    data class Error(val message: String) : LocationAction()
}

class LocationViewModel(app: Application) : AndroidViewModel(app) {

    private val _action = MutableStateFlow<LocationAction?>(null)
    val action: StateFlow<LocationAction?> = _action

    private val _locationState = MutableStateFlow(LocationUiState())
    val locationState: StateFlow<LocationUiState> = _locationState


    /** Main function — UI calls this */
    fun checkRequirements(
        hasPermission: Boolean,
        isGPSEnabled: Boolean
    ) {
        when {
            !hasPermission -> {
                // Permission मांगो
                _action.value = LocationAction.RequestPermission
            }
            !isGPSEnabled -> {
                // GPS का default popup खोलो
                _action.value = LocationAction.AskToEnableGPS
            }
            else -> {
                // सब ठीक → location fetch करो
                _action.value = LocationAction.FetchLocation
            }
        }
    }


    /**  Location Fetching */
    @SuppressLint("MissingPermission")
    fun fetchLocation() {
        val context = getApplication<Application>()
        val fused = LocationServices.getFusedLocationProviderClient(context)
        _locationState.value = LocationUiState(isLoading = true)
        fused.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
            .addOnSuccessListener { loc ->
                if (loc == null) {
                    _locationState.value =
                        LocationUiState(error = "Location not found", isLoading = false)
                    return@addOnSuccessListener
                }

                val address = getAddress(loc.latitude, loc.longitude)
                Log.d("user location", "*****$address")
                _locationState.value = LocationUiState(
                    latitude = loc.latitude,
                    longitude = loc.longitude,
                    address = address,
                    isLoading = false
                )
            }
            .addOnFailureListener {
                _locationState.value = LocationUiState(
                    error = it.localizedMessage,
                    isLoading = false
                )
            }
    }

    private fun getAddress(lat: Double, lng: Double): String {
        return try {
            val geo = Geocoder(getApplication(), Locale.getDefault())
            val list = geo.getFromLocation(lat, lng, 1)
            list?.firstOrNull()?.getAddressLine(0) ?: "Address not available"
        } catch (e: Exception) {
            "Address not available"
        }
    }

    fun clearAction() {
        _action.value = null
    }


}
