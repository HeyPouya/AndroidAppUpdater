package com.pouyaheydari.appupdater.directdownload.utils.donwloadapk

import android.app.DownloadManager
import android.content.Context
import com.pouyaheydari.appupdater.directdownload.domain.SetRequestIdUseCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Mockito.any
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.kotlin.whenever
import org.robolectric.RobolectricTestRunner
import java.io.File

@RunWith(RobolectricTestRunner::class)
class APKDownloadManagerTest {

    private val downloadManager: DownloadManager = mock()
    private val context: Context = mock()
    private val downloadManagerRequestCreator: DownloadManagerRequestCreator = mock()
    private val apkFileProvider: APKFileProvider = mock()
    private val setRequestIdUseCase: SetRequestIdUseCase = mock()
    private val requestIdCaptor: ArgumentCaptor<Long> = ArgumentCaptor.forClass(Long::class.java)

    private lateinit var apkDownloadManager: APKDownloadManager

    @Before
    fun setUp() {
        apkDownloadManager = APKDownloadManager(
            downloadManagerRequestCreator,
            setRequestIdUseCase,
            apkFileProvider
        )
    }

    @Test
    fun `deleteExistingAPKAndDownloadNewAPK deletes old APK and starts new download`() {
        val url = "https://example.com/app.apk"
        val notificationTitle = "New Update"
        val notificationDescription = "Downloading latest version"
        val mockFile = mock(File::class.java)

        whenever(apkFileProvider.getFile(context)).thenReturn(mockFile)
        whenever(mockFile.exists()).thenReturn(true)
        whenever(downloadManager.enqueue(any())).thenReturn(1234L)

        apkDownloadManager.deleteExistingAPKAndDownloadNewAPK(
            downloadManager,
            url,
            context,
            notificationTitle,
            notificationDescription
        )

        verify(apkFileProvider).getFile(context)
        verify(setRequestIdUseCase).invoke(requestIdCaptor.capture())
        verify(mockFile).delete()
        assert(requestIdCaptor.value == 1234L)
    }

    @Test
    fun `deleteExistingAPKAndDownloadNewAPK handles missing APK safely`() {
        val url = "https://example.com/app.apk"
        val notificationTitle = "New Update"
        val notificationDescription = "Downloading latest version"
        val mockFile = mock(File::class.java)

        whenever(apkFileProvider.getFile(context)).thenReturn(mockFile)
        whenever(mockFile.exists()).thenReturn(false)
        whenever(downloadManager.enqueue(any())).thenReturn(5678L)

        apkDownloadManager.deleteExistingAPKAndDownloadNewAPK(
            downloadManager, url, context, notificationTitle, notificationDescription
        )

        verify(apkFileProvider).getFile(context)
        verify(setRequestIdUseCase).invoke(requestIdCaptor.capture())
        assert(requestIdCaptor.value == 5678L)
    }
}
