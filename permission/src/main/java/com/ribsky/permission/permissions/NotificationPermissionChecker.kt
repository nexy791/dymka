package com.ribsky.permission.permissions

import android.Manifest
import android.os.Build
import androidx.annotation.RequiresApi
import com.ribsky.permission.permissions.base.PermissionChecker

class NotificationPermissionChecker : PermissionChecker {

    override val permissions: List<String> = listOf(Manifest.permission.POST_NOTIFICATIONS)

}