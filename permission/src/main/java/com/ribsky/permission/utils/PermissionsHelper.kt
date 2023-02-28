package com.ribsky.permission.utils

import androidx.appcompat.app.AppCompatActivity
import com.fondesa.kpermissions.allGranted
import com.fondesa.kpermissions.anyPermanentlyDenied
import com.fondesa.kpermissions.extension.permissionsBuilder
import com.fondesa.kpermissions.extension.send

class PermissionsHelper {

    companion object {

        fun AppCompatActivity.hasPermissions(vararg permission: String): Boolean {
            val builder = permissionsBuilder(
                permission.first(),
                *permission.drop(1).toTypedArray()
            ).build()
            return builder.checkStatus().allGranted()
        }

        fun AppCompatActivity.hasBlockedPermissions(vararg permission: String): Boolean {
            val builder = permissionsBuilder(
                permission.first(),
                *permission.drop(1).toTypedArray()
            ).build()
            return builder.checkStatus().anyPermanentlyDenied()
        }

        fun AppCompatActivity.requestPermissions(
            vararg permission: String,
            callback: (Boolean) -> Unit,
        ) {
            val builder = permissionsBuilder(
                permission.first(),
                *permission.drop(1).toTypedArray()
            ).build()
            builder.send {
                callback(it.allGranted())
            }
        }
    }
}
