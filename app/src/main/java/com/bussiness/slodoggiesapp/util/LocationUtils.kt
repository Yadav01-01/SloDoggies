package com.bussiness.slodoggiesapp.util

import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import android.net.Uri
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
        fun getImageModel(uriOrUrl: String?): Any? {
            uriOrUrl ?: return null
            return if (uriOrUrl.startsWith("content://") || uriOrUrl.startsWith("file://")) {
                Uri.parse(uriOrUrl)
            } else {
                uriOrUrl // assume remote URL
            }
        }

        @SuppressLint("DefaultLocale")
        fun formatStringNumberShorthand(valueStr: String): String {
            val value = valueStr.toLongOrNull() ?: return valueStr // fallback if not a number
            return when {
                value >= 1_000_000_000_000 -> String.format("%.2fT", value / 1_000_000_000_000.0)
                value >= 1_000_000_000 -> String.format("%.2fB", value / 1_000_000_000.0)
                value >= 1_000_000 -> String.format("%.2fM", value / 1_000_000.0)
                value >= 1_000 -> String.format("%.2fk", value / 1_000.0)
                else -> value.toString()
            }
        }

    }
}