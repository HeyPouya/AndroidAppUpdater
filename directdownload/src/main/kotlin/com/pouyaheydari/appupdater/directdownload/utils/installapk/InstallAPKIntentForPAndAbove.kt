package com.pouyaheydari.appupdater.directdownload.utils.installapk

import android.content.Intent
import android.net.Uri

internal class InstallAPKIntentForPAndAbove : InstallAPKIntent {
    override fun getIntent(fileUri: Uri) =
        Intent(Intent.ACTION_VIEW, fileUri).apply {
            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_ACTIVITY_NEW_TASK
        }
}
