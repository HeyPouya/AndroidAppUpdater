package com.pouyaheydari.appupdater.core.receiver

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import com.pouyaheydari.appupdater.core.utils.TAG
import com.pouyaheydari.appupdater.core.utils.getExistingApk
import com.pouyaheydari.appupdater.core.utils.hideUpdateInProgress
import com.pouyaheydari.appupdater.core.utils.installAPK
import com.pouyaheydari.appupdater.core.utils.requestId
import com.pouyaheydari.updater.core.R

/**
 * Receives when download is finished and opens the install dialog
 */
class DownloadFinishedReceiver : BroadcastReceiver() {

    /**
     * To show install page when apk got downloaded successfully
     */
    override fun onReceive(context: Context?, intent: Intent?) {

        val action = intent?.action
        if (action == DownloadManager.ACTION_DOWNLOAD_COMPLETE) {
            val referenceId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)

            // if downloaded file is our apk
            if (referenceId == requestId) {
                context?.let {
                    hideUpdateInProgress()
                    verifyDownloadedApkExists(it)
                }
            }
        }
    }

    private fun verifyDownloadedApkExists(context: Context) {
        val existingApk = context.getExistingApk()
        if (!existingApk.exists())
            Log.d(TAG, context.getString(R.string.appupdater_couldnt_find_downloaded_file))
        else
            installAPK(context, existingApk, Build.VERSION.SDK_INT)
    }
}
