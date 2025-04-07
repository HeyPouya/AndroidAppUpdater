package com.pouyaheydari.appupdater.directdownload.utils.donwloadapk

import android.app.Activity
import android.app.DownloadManager
import com.pouyaheydari.appupdater.directdownload.utils.permission.DownloadAPKPermission
import org.junit.Test
import org.mockito.Mockito.anyString
import org.mockito.Mockito.mock
import org.mockito.Mockito.never
import org.mockito.Mockito.verify
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.whenever

class DownloadAPKHelperTest {

    private val url = "https://example.com/app.apk"
    private val notificationTitle = "New Update"
    private val notificationDescription = "Downloading latest version"
    private val androidSdkVersion = 30
    private val activity: Activity = mock(Activity::class.java)
    private val downloadManager: DownloadManager = mock(DownloadManager::class.java)
    private val downloadAPKPermission: DownloadAPKPermission = mock(DownloadAPKPermission::class.java)
    private val apkDownloadManager: APKDownloadManager = mock(APKDownloadManager::class.java)
    private val onDownloadingApkStarted: () -> Unit = mock()

    @Test
    fun `checkPermissionsAndDownloadApk starts download when permissions are resolved`() {
        whenever(downloadAPKPermission.resolvePermissions(activity)).thenReturn(true)

        checkPermissionsAndDownloadApk(
            url,
            activity,
            androidSdkVersion,
            notificationTitle,
            notificationDescription,
            downloadManager,
            downloadAPKPermission,
            apkDownloadManager,
            onDownloadingApkStarted
        )

        verify(apkDownloadManager).deleteExistingAPKAndDownloadNewAPK(
            downloadManager,
            url,
            activity,
            notificationTitle,
            notificationDescription,
        )
        verify(onDownloadingApkStarted).invoke()
    }

    @Test
    fun `checkPermissionsAndDownloadApk does not start download when permissions are not resolved`() {
        whenever(downloadAPKPermission.resolvePermissions(activity)).thenReturn(false)

        checkPermissionsAndDownloadApk(
            url,
            activity,
            androidSdkVersion,
            notificationTitle,
            notificationDescription,
            downloadManager,
            downloadAPKPermission,
            apkDownloadManager,
            onDownloadingApkStarted
        )

        verify(apkDownloadManager, never()).deleteExistingAPKAndDownloadNewAPK(
            anyOrNull(),
            anyString(),
            anyOrNull(),
            anyString(),
            anyOrNull()
        )
        verify(onDownloadingApkStarted, never()).invoke()
    }
}
