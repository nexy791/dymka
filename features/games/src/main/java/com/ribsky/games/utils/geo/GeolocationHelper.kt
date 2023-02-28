package com.ribsky.games.utils.geo

import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.provider.Settings
import androidx.fragment.app.Fragment
import com.ribsky.common.utils.ext.ViewExt.Companion.toast

class GeolocationHelper {

    companion object {

        fun Context.isGeolocationEnabled(): Boolean {
            val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
            return locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        }

        fun Context.turnOnGeolocation() {
            toast("Увімкни геолокацію")
            startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
        }

        fun Fragment.isGeolocationEnabled(): Boolean = requireContext().isGeolocationEnabled()

        fun Fragment.turnOnGeolocation() = requireContext().turnOnGeolocation()
    }
}
