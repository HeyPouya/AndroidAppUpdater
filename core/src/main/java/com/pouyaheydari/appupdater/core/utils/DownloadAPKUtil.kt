package com.pouyaheydari.appupdater.core.utils

import android.Manifest
import android.app.Activity
import android.app.DownloadManager
import android.app.DownloadManager.Request.NETWORK_MOBILE
import android.app.DownloadManager.Request.NETWORK_WIFI
import android.app.DownloadManager.Request.VISIBILITY_VISIBLE
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Environment
import com.pouyaheydari.appupdater.core.R
import com.pouyaheydari.appupdater.core.interactors.SetIsUpdateInProgress
import com.pouyaheydari.appupdater.core.interactors.SetRequestIdInteractor

/**
 * Checks for needed permissions and tries to download the apk
 */
fun getApk(url: String, activity: Activity) {
    val permission = Manifest.permission.WRITE_EXTERNAL_STORAGE

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P && !activity.packageManager.canRequestPackageInstalls()) {
        activity.showRequest()
    }

    if (activity.isPermissionGranted(permission) || Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
        prepareToDownloadApk(url, activity)
    } else {
        activity.getPermission(arrayOf(permission))
    }
}

private fun prepareToDownloadApk(url: String, context: Context) {
    // Show update in Progress alert dialog to the user
    showUpdateInProgress()

    // Delete APK if user downloaded the apk before
    context.deleteExistingFile()

    // Start downloading the APK
    downloadNewApk(url, context)
}

private fun showUpdateInProgress() {
    SetIsUpdateInProgress().invoke(true)
}

/**
 * Downloads the given file via download manager
 */
private fun downloadNewApk(url: String, context: Context) {
    val downloadManager = DownloadManager.Request(Uri.parse(url)).run {
        // setting title and description to be shown on download notification
        setTitle(context.getString(R.string.appupdater_download_notification_title))
        setDescription(context.getString(R.string.appupdater_download_notification_desc))
        setNotificationVisibility(VISIBILITY_VISIBLE)
        setAllowedNetworkTypes(NETWORK_WIFI or NETWORK_MOBILE)
        // setting the destination of the downloaded file
        setDestinationInExternalFilesDir(context, Environment.DIRECTORY_DOWNLOADS, APK_NAME)
    }

    // enqueue the file to start download
    val manager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
    SetRequestIdInteractor().invoke(manager.enqueue(downloadManager))
}
