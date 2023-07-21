package com.ribsky.testing.permission

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ribsky.permission.manager.PermissionManager
import com.ribsky.permission.manager.PermissionManagerImpl
import com.ribsky.permission.permissions.base.PermissionChecker
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class PermissionManagerImplTest {

    private lateinit var activity: AppCompatActivity
    private lateinit var permissionChecker: PermissionChecker
    private lateinit var permissionManager: PermissionManagerImpl
    private lateinit var callback: PermissionManager.PermissionCallback


    @Before
    fun setup() {
        activity = mockk(relaxed = true)
        permissionChecker = mockk(relaxed = true)
        callback = mockk(relaxed = true)
        permissionManager = PermissionManagerImpl(activity, permissionChecker)
    }

    @Test
    fun hasPermissions_shouldReturnTrueWhenAllPermissionsAreGranted() {
        val permissions = listOf("permission1", "permission2")

        every { permissionChecker.permissions } returns permissions
        every { activity.checkSelfPermission(any()) } returns PackageManager.PERMISSION_GRANTED

        val result = permissionManager.hasPermissions()

        assert(result)

    }

    @Test
    fun hasPermissions_shouldReturnFalseWhenAtLeastOnePermissionIsNotGranted() {
        val permissions = listOf("permission1", "permission2")

        every { permissionChecker.permissions } returns permissions
        every { activity.checkSelfPermission("permission1") } returns PackageManager.PERMISSION_GRANTED
        every { activity.checkSelfPermission("permission2") } returns PackageManager.PERMISSION_DENIED

        val result = permissionManager.hasPermissions()

        assert(!result)
    }

    @Test
    fun hasBlockedPermissions_shouldReturnTrueWhenAtLeastOnePermissionIsBlocked() {
        val permissions = listOf("permission1", "permission2")

        every { permissionChecker.permissions } returns permissions
        every { activity.shouldShowRequestPermissionRationale("permission1") } returns false
        every { activity.shouldShowRequestPermissionRationale("permission2") } returns true

        val result = permissionManager.hasBlockedPermissions()

        assert(result)
    }

    @Test
    fun hasBlockedPermissions_shouldReturnFalseWhenAllPermissionsAreNotBlocked() {
        val permissions = listOf("permission1", "permission2")

        every { permissionChecker.permissions } returns permissions
        every { activity.shouldShowRequestPermissionRationale("permission1") } returns false
        every { activity.shouldShowRequestPermissionRationale("permission2") } returns false

        val result = permissionManager.hasBlockedPermissions()

        assert(!result)
    }

    @Test
    fun requestPermission_shouldCallOnPermissionGrantedWhenAllPermissionsAreGranted() {
        val permissions = listOf("permission1", "permission2")

        every { permissionChecker.permissions } returns permissions
        every { activity.checkSelfPermission(any()) } returns PackageManager.PERMISSION_GRANTED

        permissionManager.requestPermission(callback)

        verify { callback.onPermissionGranted() }
        verify(exactly = 0) { callback.onPermissionDenied() }

    }

    @Test
    fun requestPermission_shouldCallOnPermissionDeniedWhenAtLeastOnePermissionIsNotGranted() {
        val permissions = listOf("permission1", "permission2")

        every { permissionChecker.permissions } returns permissions
        every { activity.checkSelfPermission("permission1") } returns PackageManager.PERMISSION_GRANTED
        every { activity.checkSelfPermission("permission2") } returns PackageManager.PERMISSION_DENIED

        permissionManager.requestPermission(callback)

        verify { callback.onPermissionDenied() }
        verify(exactly = 0) { callback.onPermissionGranted() }
    }

    @Test
    fun openAppSettings_shouldStartActivity() {
        permissionManager.openAppSettings()
        verify { activity.startActivity(any()) }
    }


}