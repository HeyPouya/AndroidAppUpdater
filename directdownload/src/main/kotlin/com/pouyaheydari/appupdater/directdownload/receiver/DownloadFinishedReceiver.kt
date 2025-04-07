package com.pouyaheydari.appupdater.directdownload.receiver

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.pouyaheydari.appupdater.directdownload.data.UpdateInProgressRepositoryImpl
import com.pouyaheydari.appupdater.directdownload.domain.DownloadState
import com.pouyaheydari.appupdater.directdownload.domain.GetRequestIdUseCase
import com.pouyaheydari.appupdater.directdownload.domain.SetDownloadStateUseCase
import com.pouyaheydari.appupdater.directdownload.utils.donwloadapk.APKFileProviderImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

internal class DownloadFinishedReceiver : BroadcastReceiver() {
    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    private val getRequestIdUseCase by lazy { GetRequestIdUseCase(UpdateInProgressRepositoryImpl) }
    private val setDownloadStateUseCase by lazy { SetDownloadStateUseCase(UpdateInProgressRepositoryImpl) }
    private val getFilePathProvider by lazy { APKFileProviderImpl() }

    override fun onReceive(context: Context, intent: Intent?) {
        if (intent?.action == DownloadManager.ACTION_DOWNLOAD_COMPLETE) {
            val referenceId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)

            if (referenceId == getRequestIdUseCase()) {
                notifyApkDownloaded(context)
            }
        }
    }

    private fun notifyApkDownloaded(context: Context) {
        coroutineScope.launch {
            val apk = getFilePathProvider.getFile(context = context)
            setDownloadStateUseCase(DownloadState.Downloaded(apk))
        }
    }
}
