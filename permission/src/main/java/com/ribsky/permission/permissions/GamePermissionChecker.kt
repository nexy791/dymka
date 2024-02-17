package com.ribsky.permission.permissions

import android.Manifest
import android.os.Build
import androidx.annotation.RequiresApi
import com.ribsky.permission.permissions.base.PermissionChecker

class GamePermissionChecker : PermissionChecker {

    override val permissions: List<String>
        get() {
            return when {
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> permissions33Above()
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> permissions31Above()
                else -> permissions30Below()
            }
        }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun permissions33Above() = listOf(
        Manifest.permission.BLUETOOTH_ADVERTISE,
        Manifest.permission.BLUETOOTH_CONNECT,
        Manifest.permission.BLUETOOTH_SCAN,
        Manifest.permission.NEARBY_WIFI_DEVICES
    )

    @RequiresApi(Build.VERSION_CODES.S)
    private fun permissions31Above() = listOf(
        Manifest.permission.BLUETOOTH_ADVERTISE,
        Manifest.permission.BLUETOOTH_CONNECT,
        Manifest.permission.BLUETOOTH_SCAN,
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )

    private fun permissions30Below() = listOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )
}
