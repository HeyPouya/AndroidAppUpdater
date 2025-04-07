package com.pouyaheydari.appupdater.directdownload.utils.installapk

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import junit.framework.TestCase.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.kotlin.whenever
import org.robolectric.RobolectricTestRunner
import java.io.File

@RunWith(RobolectricTestRunner::class)
class APKIntentFactoryTest {
    private val context: Context = mock()
    private val apk: File = mock()
    private val fileUriProvider: FileUriProvider = mock()
    private val uri: Uri = mock()
    private val apkIntentFactory = APKIntentFactory(fileUriProvider)

    @Test
    fun `test getInstallAPKIntent for Android P and above`() {
        whenever(fileUriProvider.getFileUri(context, apk, Build.VERSION_CODES.P)).thenReturn(uri)

        val intent = apkIntentFactory.getInstallAPKIntent(context, apk, Build.VERSION_CODES.P)

        verify(fileUriProvider, times(1)).getFileUri(context, apk, Build.VERSION_CODES.P)
        assertEquals(Intent.ACTION_VIEW, intent.action)
        assertEquals(uri, intent.data)
        assertEquals(Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_ACTIVITY_NEW_TASK, intent.flags)
    }

    @Suppress("DEPRECATION")
    @Test
    fun `test getInstallAPKIntent for Android N to O`() {
        whenever(fileUriProvider.getFileUri(context, apk, Build.VERSION_CODES.N)).thenReturn(uri)

        val intent = apkIntentFactory.getInstallAPKIntent(context, apk, Build.VERSION_CODES.N)

        verify(fileUriProvider, times(1)).getFileUri(context, apk, Build.VERSION_CODES.N)
        assertEquals(Intent.ACTION_INSTALL_PACKAGE, intent.action)
        assertEquals(uri, intent.data)
        assertEquals(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_ACTIVITY_NEW_TASK, intent.flags)
    }

    @Test
    fun `test getInstallAPKIntent for Android M and below`() {
        whenever(fileUriProvider.getFileUri(context, apk, Build.VERSION_CODES.M)).thenReturn(uri)

        val intent = apkIntentFactory.getInstallAPKIntent(context, apk, Build.VERSION_CODES.M)

        verify(fileUriProvider, times(1)).getFileUri(context, apk, Build.VERSION_CODES.M)
        assertEquals(Intent.ACTION_VIEW, intent.action)
        assertEquals(uri, intent.data)
        assertEquals(Intent.FLAG_ACTIVITY_NEW_TASK, intent.flags)
    }
}
