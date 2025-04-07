package com.pouyaheydari.appupdater.directdownload.utils.permission

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mockStatic
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class DownloadAPKPermissionForOAndBellowTest {

    private val mockActivity: Activity = mock()
    private val permissionHandler = DownloadAPKPermissionForOAndBellow()

    @Test
    fun `resolvePermissions should return true when permission is granted`() {
        // Arrange
        whenever(ContextCompat.checkSelfPermission(mockActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE))
            .thenReturn(PackageManager.PERMISSION_GRANTED)

        // Act
        val result = permissionHandler.resolvePermissions(mockActivity)

        // Assert
        assertTrue(result)
    }

    @Test
    fun `resolvePermissions should return false and request permission when permission is not granted`() {
        // Arrange
        mockStatic(ActivityCompat::class.java).use { activityCompatMock ->
            whenever(ContextCompat.checkSelfPermission(mockActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE))
                .thenReturn(PackageManager.PERMISSION_DENIED)

            // Act
            val result = permissionHandler.resolvePermissions(mockActivity)

            // Assert
            assertFalse(result)
            activityCompatMock.verify {
                ActivityCompat.requestPermissions(
                    mockActivity,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    2000
                )
            }
        }
    }
}
