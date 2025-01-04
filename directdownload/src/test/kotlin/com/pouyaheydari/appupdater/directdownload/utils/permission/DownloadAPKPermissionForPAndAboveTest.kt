package com.pouyaheydari.appupdater.directdownload.utils.permission

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.Mockito.any
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.never
import org.mockito.Mockito.spy
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class DownloadAPKPermissionForPAndAboveTest {

    private val mockActivity: Activity = mock()
    private val mockPackageManager: PackageManager = mock()
    private val permissionHandler = DownloadAPKPermissionForPAndAbove()

    @Test
    fun `resolvePermissions should return true when canRequestPackageInstalls is true`() {
        whenever(mockActivity.packageManager).thenReturn(mockPackageManager)
        whenever(mockActivity.packageManager.canRequestPackageInstalls()).thenReturn(true)

        // Act
        val result = permissionHandler.resolvePermissions(mockActivity)

        // Assert
        assertTrue(result)
        verify(mockActivity.packageManager).canRequestPackageInstalls()
        verify(mockActivity, never()).startActivity(any())
    }

    @Test
    fun `resolvePermissions should return false and request permission when canRequestPackageInstalls is false`() {
        val mockUri = mock<Uri>()

        whenever(mockActivity.packageManager).thenReturn(mockPackageManager)
        whenever(mockPackageManager.canRequestPackageInstalls()).thenReturn(false)
        val permissionHandler = spy(DownloadAPKPermissionForPAndAbove())
        doReturn(Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, mockUri))
            .whenever(permissionHandler).getInstallFromUnknownSourceIntent(mockActivity)

        // Act
        val result = permissionHandler.resolvePermissions(mockActivity)

        // Assert
        assertFalse(result)
        verify(mockActivity.packageManager).canRequestPackageInstalls()
    }
}
