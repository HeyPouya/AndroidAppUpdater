package com.pouyaheydari.appupdater.directdownload.receiver

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import com.pouyaheydari.appupdater.core.utils.ANDROID_APP_UPDATER_DEBUG_TAG
import com.pouyaheydari.appupdater.directdownload.R
import com.pouyaheydari.appupdater.directdownload.data.UpdateInProgressRepositoryImpl
import com.pouyaheydari.appupdater.directdownload.domain.DownloadState
import com.pouyaheydari.appupdater.directdownload.domain.GetRequestIdUseCase
import com.pouyaheydari.appupdater.directdownload.domain.SetDownloadStateUseCase
import com.pouyaheydari.appupdater.directdownload.utils.getExistingApk
import com.pouyaheydari.appupdater.directdownload.utils.installAPK
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

internal class DownloadFinishedReceiver : BroadcastReceiver() {
    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    private val getRequestIdUseCase by lazy { GetRequestIdUseCase(UpdateInProgressRepositoryImpl) }
    private val setDownloadStateUseCase by lazy { SetDownloadStateUseCase(UpdateInProgressRepositoryImpl) }

    override fun onReceive(context: Context, intent: Intent?) {
        if (intent?.action == DownloadManager.ACTION_DOWNLOAD_COMPLETE) {
            val referenceId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)

            if (referenceId == getRequestIdUseCase()) {
                hideUpdateInProgress()
                verifyDownloadedApkExists(context)
            }
        }
    }

    private fun hideUpdateInProgress() {
        coroutineScope.launch {
            setDownloadStateUseCase(DownloadState.Downloaded)
        }
    }

    private fun verifyDownloadedApkExists(context: Context) {
        val existingApk = context.getExistingApk()
        if (!existingApk.exists()) {
            Log.e(ANDROID_APP_UPDATER_DEBUG_TAG, context.getString(R.string.appupdater_couldnt_find_downloaded_file))
        } else {
            installAPK(context, existingApk, Build.VERSION.SDK_INT)
        }
    }
}
