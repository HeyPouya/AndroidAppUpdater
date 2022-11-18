package com.pouyaheydari.appupdater.core.directlink

import android.Manifest
import android.app.Activity
import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Environment
import android.util.Log
import com.pouyaheydari.appupdater.core.utils.APK_NAME
import com.pouyaheydari.appupdater.core.utils.DownloadAPKUtil
import com.pouyaheydari.appupdater.core.utils.InstallApkUtil
import com.pouyaheydari.appupdater.core.utils.PermissionUtils
import com.pouyaheydari.appupdater.core.utils.TAG
import com.pouyaheydari.appupdater.core.utils.UnknownSourceInstallRequest
import com.pouyaheydari.appupdater.core.utils.requestId
import com.pouyaheydari.appupdater.core.utils.showUpdateInProgressCallback
import com.pouyaheydari.updater.core.R
import java.io.File

/**
 * starts a download manager and downloads apk
 * also shows a loading indicator showing the apk is downloading
 * after download finishes , opens install page
 */
class DirectLinkDownload : BroadcastReceiver() {

    /**
     * To show install page when apk got downloaded successfully
     */
    override fun onReceive(context: Context?, intent: Intent?) {

        val action = intent?.action
        if (action == DownloadManager.ACTION_DOWNLOAD_COMPLETE) {
            val referenceId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)

            // if downloaded file is our apk
            if (referenceId == requestId) {
                context?.let { installApk(it) }
            }
        }
    }

    private fun installApk(context: Context) {

        // To dismiss the download in progress dialog
        showUpdateInProgressCallback?.let { it(false) }
        showUpdateInProgressCallback = null

        if (!File(getDestination(context)).exists())
            Log.d(TAG, context.getString(R.string.appupdater_couldnt_find_downloaded_file))
        else
            InstallApkUtil().installAPK(context, getDestination(context), Build.VERSION.SDK_INT)
    }

    /**
     * Checks for needed permissions and tries to download the apk
     */
    fun getApk(url: String, activity: Activity?, callback: (Boolean) -> Unit) {

        checkNotNull(activity)

        val permission = Manifest.permission.WRITE_EXTERNAL_STORAGE
        val permissionChecker = PermissionUtils()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P &&
            !activity.packageManager.canRequestPackageInstalls()
        ) {
            UnknownSourceInstallRequest().showRequest(activity)
        }

        if (permissionChecker.isPermissionGranted(permission, activity) || Build.VERSION.SDK_INT > Build.VERSION_CODES.P)
            downloadApk(url, activity, callback)
        else
            permissionChecker.getPermission(activity, arrayOf(permission))
    }

    private fun downloadApk(url: String, context: Context, callback: (Boolean) -> Unit) {

        // delete APK if user downloaded the apk before
        deleteExistingFile(getDestination(context))

        // downloads the apk
        DownloadAPKUtil().download(url, context)
        // show alert dialog to user
        showUpdateInProgressCallback = callback
        showUpdateInProgressCallback?.let { it(true) }
    }

    private fun deleteExistingFile(destination: String) {
        val file = File(destination)
        if (file.exists())
            file.delete()
    }

    private fun getDestination(context: Context?) =
        "${context?.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)}/${APK_NAME}"
}
