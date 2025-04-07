package com.pouyaheydari.appupdater.directdownload.utils.donwloadapk

import android.app.DownloadManager
import android.content.Context
import android.util.Log
import androidx.core.net.toUri
import com.pouyaheydari.appupdater.core.utils.ANDROID_APP_UPDATER_DEBUG_TAG
import com.pouyaheydari.appupdater.directdownload.data.UpdateInProgressRepositoryImpl
import com.pouyaheydari.appupdater.directdownload.domain.SetRequestIdUseCase

class APKDownloadManager(
    private val downloadManagerRequestCreator: DownloadManagerRequestCreator = DownloadManagerRequestCreator(),
    private val setRequestIdUseCase: SetRequestIdUseCase = SetRequestIdUseCase(UpdateInProgressRepositoryImpl),
    private val apkFileProvider: APKFileProvider = APKFileProviderImpl(),
) {
    fun deleteExistingAPKAndDownloadNewAPK(
        downloadManager: DownloadManager,
        url: String,
        context: Context,
        notificationTitle: String,
        notificationDescription: String,
    ) {
        safeDeleteIfPreviousAPKExists(context)
        downloadAPK(downloadManager, url, context, notificationTitle, notificationDescription)
    }

    private fun downloadAPK(
        downloadManager: DownloadManager,
        url: String,
        context: Context,
        notificationTitle: String,
        notificationDescription: String,
    ) {
        val downloadManagerRequest = downloadManagerRequestCreator.create(
            uri = url.toUri(),
            context = context,
            notificationTitle = notificationTitle,
            notificationDescription = notificationDescription
        )
        setRequestIdUseCase(downloadManager.enqueue(downloadManagerRequest))
    }

    private fun safeDeleteIfPreviousAPKExists(context: Context) {
        val file = apkFileProvider.getFile(context)
        try {
            if (file.exists()) {
                file.delete()
            }
        } catch (exception: SecurityException) {
            Log.e(ANDROID_APP_UPDATER_DEBUG_TAG, "Error while deleting existing APK file: ${exception.message}")
        }
    }
}
