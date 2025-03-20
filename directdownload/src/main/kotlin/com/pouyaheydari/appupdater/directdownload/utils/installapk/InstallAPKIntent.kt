package com.pouyaheydari.appupdater.directdownload.utils.installapk

import android.content.Intent
import android.net.Uri

internal interface InstallAPKIntent {
    fun getIntent(fileUri: Uri): Intent
}
