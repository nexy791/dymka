package com.ribsky.permission.manager

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ribsky.permission.permissions.base.PermissionChecker
import com.ribsky.permission.utils.PermissionsHelper.Companion.hasBlockedPermissions
import com.ribsky.permission.utils.PermissionsHelper.Companion.hasPermissions
import com.ribsky.permission.utils.PermissionsHelper.Companion.requestPermissions

class PermissionManagerImpl(
    private val activity: AppCompatActivity,
    private val permissionChecker: PermissionChecker,
) : PermissionManager {

    override fun hasPermissions(): Boolean =
        activity.hasPermissions(*permissionChecker.permissions.toTypedArray())

    override fun hasBlockedPermissions(): Boolean =
        activity.hasBlockedPermissions(*permissionChecker.permissions.toTypedArray())

    override fun requestPermission(callback: PermissionManager.PermissionCallback?) {
        activity.requestPermissions(
            *permissionChecker.permissions.toTypedArray(),
            callback = { isGranted ->
                if (isGranted) {
                    callback?.onPermissionGranted()
                } else {
                    callback?.onPermissionDenied()
                }
            }
        )
    }

    override fun openAppSettings() {
        Toast.makeText(activity, "Надай дозволи", Toast.LENGTH_SHORT).show()
        activity.startActivity(
            Intent.createChooser(
                Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    data = Uri.fromParts("package", activity.packageName, null)
                },
                ""
            )
        )
    }
}
