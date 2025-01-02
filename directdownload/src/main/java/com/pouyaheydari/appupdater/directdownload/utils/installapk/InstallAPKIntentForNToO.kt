package com.pouyaheydari.appupdater.directdownload.utils.installapk

import android.content.Intent
import android.net.Uri

@Suppress("DEPRECATION")
internal class InstallAPKIntentForNToO : InstallAPKIntent {
    override fun getIntent(fileUri: Uri) =
        Intent(Intent.ACTION_INSTALL_PACKAGE, fileUri).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_ACTIVITY_NEW_TASK
        }
}
