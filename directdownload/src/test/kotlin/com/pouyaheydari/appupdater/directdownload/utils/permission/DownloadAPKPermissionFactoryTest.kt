package com.pouyaheydari.appupdater.directdownload.utils.permission

import android.os.Build
import org.junit.Assert.assertTrue
import org.junit.Test

class DownloadAPKPermissionFactoryTest {

    private val factory = DownloadAPKPermissionFactory()

    @Test
    fun `getDownloadAPKPermissionHandler should return DownloadAPKPermissionForPAndAbove for Android P and above`() {
        // Arrange
        val androidSdkVersion = Build.VERSION_CODES.P

        // Act
        val result = factory.getDownloadAPKPermissionHandler(androidSdkVersion)

        // Assert
        assertTrue(result is DownloadAPKPermissionForPAndAbove)
    }

    @Test
    fun `getDownloadAPKPermissionHandler should return DownloadAPKPermissionForOAndBellow for Android O and below`() {
        // Arrange
        val androidSdkVersion = Build.VERSION_CODES.O

        // Act
        val result = factory.getDownloadAPKPermissionHandler(androidSdkVersion)

        // Assert
        assertTrue(result is DownloadAPKPermissionForOAndBellow)
    }
}
