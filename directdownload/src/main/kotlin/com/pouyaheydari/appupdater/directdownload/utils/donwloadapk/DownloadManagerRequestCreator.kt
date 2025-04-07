package com.pouyaheydari.appupdater.directdownload.utils.donwloadapk

import android.app.DownloadManager
import android.app.DownloadManager.Request.NETWORK_MOBILE
import android.app.DownloadManager.Request.NETWORK_WIFI
import android.app.DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.util.Log
import com.pouyaheydari.appupdater.core.utils.ANDROID_APP_UPDATER_DEBUG_TAG
import com.pouyaheydari.appupdater.core.utils.APK_NAME

class DownloadManagerRequestCreator {

    fun create(
        uri: Uri,
        context: Context,
        notificationTitle: String,
        notificationDescription: String,
        downloadManagerRequest: DownloadManager.Request = DownloadManager.Request(uri)
    ): DownloadManager.Request = downloadManagerRequest.apply {
        setTitle(notificationTitle)
        setDescription(notificationDescription)
        setNotificationVisibility(VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        setAllowedNetworkTypes(NETWORK_WIFI or NETWORK_MOBILE)
        try {
            setDestinationInExternalFilesDir(context, Environment.DIRECTORY_DOWNLOADS, APK_NAME)
        } catch (exception: IllegalStateException) {
            Log.e(ANDROID_APP_UPDATER_DEBUG_TAG, "Error while setting download destination for download manager request: ${exception.message}")
        }
    }
}
