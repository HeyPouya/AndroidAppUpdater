package com.pouyaheydari.appupdater.directdownload.utils.installapk

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.doThrow
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoMoreInteractions
import org.mockito.Mockito.`when`
import org.mockito.kotlin.whenever
import org.robolectric.RobolectricTestRunner
import java.io.File

@RunWith(RobolectricTestRunner::class)
class InstallApkUtilTest {

    private val context: Context = mock()
    private val apkFile: File = mock()
    private val apkIntentFactory: APKIntentFactory = mock()
    private val fileUriProvider: FileUriProvider = mock()
    private val uri: Uri = mock()

    @Before
    fun setUp() {
        whenever(fileUriProvider.getFileUri(context, apkFile, 30)).thenReturn(uri)
    }

    @Test
    fun `installAPK should log error when APK file does not exist`() {
        whenever(apkFile.exists()).thenReturn(false)

        installAPK(context, apkFile, 30, apkIntentFactory)

        verify(apkFile).exists()
        verifyNoMoreInteractions(apkFile)
    }

    @Test
    fun `installAPK should start activity when APK file exists`() {
        `when`(apkFile.exists()).thenReturn(true)
        val intent = mock(Intent::class.java)
        `when`(apkIntentFactory.getInstallAPKIntent(context, apkFile, 30)).thenReturn(intent)

        installAPK(context, apkFile, 30, apkIntentFactory)

        verify(apkFile).exists()
        verify(apkIntentFactory).getInstallAPKIntent(context, apkFile, 30)
        verify(context).startActivity(intent)
    }

    @Test
    fun `installAPK should log error when ActivityNotFoundException is thrown`() {
        `when`(apkFile.exists()).thenReturn(true)
        val intent = mock(Intent::class.java)
        `when`(apkIntentFactory.getInstallAPKIntent(context, apkFile, 30)).thenReturn(intent)
        doThrow(ActivityNotFoundException::class.java).`when`(context).startActivity(intent)

        installAPK(context, apkFile, 30, apkIntentFactory)

        verify(apkFile).exists()
        verify(apkIntentFactory).getInstallAPKIntent(context, apkFile, 30)
        verify(context).startActivity(intent)
    }
}
