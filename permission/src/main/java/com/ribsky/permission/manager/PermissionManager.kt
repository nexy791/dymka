package com.ribsky.permission.manager

interface PermissionManager {

    interface PermissionCallback {
        fun onPermissionGranted()
        fun onPermissionDenied()
    }

    fun hasPermissions(): Boolean

    fun hasBlockedPermissions(): Boolean

    fun requestPermission(
        callback: PermissionCallback?,
    )

    fun openAppSettings()
}
