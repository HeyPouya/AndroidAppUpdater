package ir.heydarii.appupdater.utils

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import ir.heydarii.appupdater.R

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
        downloadManager.setTitle(context.getString(R.string.download_notification_title))
        downloadManager.setDescription(context.getString(R.string.download_notification_desc))

        // setting up download manager's properties
        downloadManager.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
        downloadManager.setAllowedNetworkTypes(
            DownloadManager.Request.NETWORK_WIFI or
                DownloadManager.Request.NETWORK_MOBILE
        )

        // setting the destination of the downloaded file
        downloadManager.setDestinationInExternalFilesDir(
            context,
            Environment.DIRECTORY_DOWNLOADS,
            APK_NAME
        )

        // enqueue the file to start download
        val manager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        REQUEST_ID = manager.enqueue(downloadManager)
    }
}