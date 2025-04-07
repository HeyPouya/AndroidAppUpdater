package com.pouyaheydari.appupdater.directdownload.utils.installapk

import android.content.Intent
import android.net.Uri

private const val APK_MIME_TYPE = "application/vnd.android.package-archive"

internal class InstallAPKIntentForM : InstallAPKIntent {
    override fun getIntent(fileUri: Uri) =
        Intent(Intent.ACTION_VIEW, fileUri).apply {
            setDataAndType(fileUri, APK_MIME_TYPE)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
}
