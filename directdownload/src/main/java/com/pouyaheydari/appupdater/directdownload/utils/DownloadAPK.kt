package com.pouyaheydari.appupdater.directdownload.utils

import android.app.Activity
import android.app.DownloadManager
import android.app.DownloadManager.Request.NETWORK_MOBILE
import android.app.DownloadManager.Request.NETWORK_WIFI
import android.app.DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Environment
import com.pouyaheydari.appupdater.directdownload.R
import com.pouyaheydari.appupdater.directdownload.data.UpdateInProgressRepositoryImpl
import com.pouyaheydari.appupdater.directdownload.domain.SetDownloadFilePathUseCase
import com.pouyaheydari.appupdater.directdownload.domain.SetRequestIdUseCase
import java.io.File

private const val APK_NAME = "NewAPK.apk"

fun checkPermissionsAndDownloadApk(url: String, activity: Activity, onDownloadingApkStarted: () -> Unit) {
    if (checkAndRequestRequiredPermissionsBasedOnOsVersion(activity, Build.VERSION.SDK_INT)) {
        prepareToDownloadApk(url, activity)
        onDownloadingApkStarted()
    }
}

private fun prepareToDownloadApk(url: String, context: Context) {
    // Delete APK if user downloaded the apk before
    deleteExistingApkIfAvailable(context)
    startDownloadManager(url, context)
}

private fun deleteExistingApkIfAvailable(context: Context) {
    val file = File(getApkFilePath(context))
    if (file.exists()) {
        file.delete()
    }
}

private fun startDownloadManager(url: String, context: Context) {
    val downloadManager = DownloadManager.Request(Uri.parse(url))
        .setTitle(context.getString(R.string.appupdater_download_notification_title))
        .setDescription(context.getString(R.string.appupdater_download_notification_desc))
        .setNotificationVisibility(VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        .setAllowedNetworkTypes(NETWORK_WIFI or NETWORK_MOBILE)
        .setDestinationInExternalFilesDir(context, Environment.DIRECTORY_DOWNLOADS, APK_NAME)

    // enqueue the file to start download
    val manager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
    SetDownloadFilePathUseCase(UpdateInProgressRepositoryImpl).invoke(getApkFilePath(context))
    SetRequestIdUseCase(UpdateInProgressRepositoryImpl).invoke(manager.enqueue(downloadManager))
}

private fun getApkFilePath(context: Context) = "${context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)}/$APK_NAME"
