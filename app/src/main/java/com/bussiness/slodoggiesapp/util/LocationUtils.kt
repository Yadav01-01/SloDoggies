package com.bussiness.slodoggiesapp.util

import android.content.Context
import android.location.Geocoder
import com.bussiness.slodoggiesapp.ui.screens.petowner.post.AddressResult
import java.util.Locale

class LocationUtils {

    companion object {

        fun getAddressFromLatLng(context: Context, lat: Double, lng: Double): AddressResult? {
            return try {
                val geocoder = Geocoder(context, Locale.getDefault())
                val addresses = geocoder.getFromLocation(lat, lng, 1)
                if (!addresses.isNullOrEmpty()) {
                    val a = addresses[0]

                    // Get street (thoroughfare + number if available)
                    val street = when {
                        !a.thoroughfare.isNullOrEmpty() && !a.subThoroughfare.isNullOrEmpty() ->
                            "${a.thoroughfare} ${a.subThoroughfare}"
                        !a.thoroughfare.isNullOrEmpty() -> a.thoroughfare
                        else -> a.getAddressLine(0) // fallback to full address
                    }

                    AddressResult(
                        street = street,
                        city = a.locality,
                        state = a.adminArea,
                        zip = a.postalCode
                    )
                } else null

            } catch (e: Exception) {
                null
            }
        }

    }
}