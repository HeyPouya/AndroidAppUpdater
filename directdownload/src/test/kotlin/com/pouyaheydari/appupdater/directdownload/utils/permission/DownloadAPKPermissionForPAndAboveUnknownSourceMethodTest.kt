package com.pouyaheydari.appupdater.directdownload.utils.permission

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class DownloadAPKPermissionForPAndAboveUnknownSourceMethodTest {

    private val permissionHandler = DownloadAPKPermissionForPAndAbove()
    private val context = RuntimeEnvironment.getApplication()

    @Test
    fun `getInstallFromUnknownSourceIntent should return intent with correct action, URI, and flags`() {
        // Arrange
        val expectedPackageName = context.packageName
        val expectedUri = Uri.parse("package:$expectedPackageName")
        val expectedAction = Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES
        val expectedFlags = Intent.FLAG_ACTIVITY_NEW_TASK

        // Act
        val intent = permissionHandler.getInstallFromUnknownSourceIntent(context)

        // Assert
        assertEquals(expectedAction, intent.action)
        assertEquals(expectedUri, intent.data)
        assertEquals(expectedFlags, intent.flags)
    }
}
