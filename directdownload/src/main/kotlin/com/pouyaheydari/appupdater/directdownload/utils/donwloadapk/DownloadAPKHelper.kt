package com.pouyaheydari.appupdater.directdownload.utils.donwloadapk

import android.app.Activity
import android.app.DownloadManager
import com.pouyaheydari.appupdater.directdownload.utils.permission.DownloadAPKPermission
import com.pouyaheydari.appupdater.directdownload.utils.permission.DownloadAPKPermissionFactory

fun checkPermissionsAndDownloadApk(
    url: String,
    activity: Activity,
    androidSdkVersion: Int,
    notificationTitle: String,
    notificationDescription: String,
    downloadManager: DownloadManager,
    downloadAPKPermission: DownloadAPKPermission = DownloadAPKPermissionFactory().getDownloadAPKPermissionHandler(androidSdkVersion),
    apkDownloadManager: APKDownloadManager = APKDownloadManager(),
    onDownloadingApkStarted: () -> Unit
) {
    if (downloadAPKPermission.resolvePermissions(activity)) {
        apkDownloadManager.deleteExistingAPKAndDownloadNewAPK(
            url = url,
            context = activity,
            notificationTitle = notificationTitle,
            notificationDescription = notificationDescription,
            downloadManager = downloadManager
        )
        onDownloadingApkStarted()
    }
}
