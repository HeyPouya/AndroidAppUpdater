package com.pouyaheydari.appupdater.directdownload.utils.donwloadapk

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.core.net.toUri
import com.pouyaheydari.appupdater.core.utils.APK_NAME
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.kotlin.spy
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class DownloadManagerRequestCreatorTest {

    private val context: Context = mock()
    private val downloadManagerRequestCreator: DownloadManagerRequestCreator = DownloadManagerRequestCreator()

    @Test
    fun `create request with correct parameters`() {
        val uri: Uri = "https://example.com/app.apk".toUri()
        val notificationTitle = "Downloading Update"
        val notificationDescription = "Your update is being downloaded."
        val request = spy(DownloadManager.Request(uri))

        val createdRequest = downloadManagerRequestCreator.create(uri, context, notificationTitle, notificationDescription, request)

        verify(createdRequest).setTitle(notificationTitle)
        verify(createdRequest).setDescription(notificationDescription)
        verify(createdRequest).setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        verify(createdRequest).setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
        verify(createdRequest).setDestinationInExternalFilesDir(context, Environment.DIRECTORY_DOWNLOADS, APK_NAME)
    }
}
