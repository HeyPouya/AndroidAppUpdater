package com.pouyaheydari.appupdater.core.utils

import android.app.DownloadManager
import android.app.DownloadManager.Request.NETWORK_MOBILE
import android.app.DownloadManager.Request.NETWORK_WIFI
import android.app.DownloadManager.Request.VISIBILITY_VISIBLE
import android.content.Context
import android.net.Uri
import android.os.Environment
import com.pouyaheydari.updater.core.R

/**
 * A class to download the apk via Android's download manager
 */
class DownloadAPKUtil {

    /**
     * Downloads the given file via download manager
     */
    fun download(url: String, context: Context) {
        val downloadManager = DownloadManager.Request(Uri.parse(url))

        // setting title and description to be shown on download notification
        downloadManager.setTitle(context.getString(R.string.appupdater_download_notification_title))
        downloadManager.setDescription(context.getString(R.string.appupdater_download_notification_desc))

        // setting up download manager's properties
        downloadManager.setNotificationVisibility(VISIBILITY_VISIBLE)
        downloadManager.setAllowedNetworkTypes(NETWORK_WIFI or NETWORK_MOBILE)

        // setting the destination of the downloaded file
        downloadManager.setDestinationInExternalFilesDir(context, Environment.DIRECTORY_DOWNLOADS, APK_NAME)

        // enqueue the file to start download
        val manager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        requestId = manager.enqueue(downloadManager)
    }
}
