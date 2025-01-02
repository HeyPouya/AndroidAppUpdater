package com.pouyaheydari.appupdater.directdownload.utils.installapk

import android.content.Context
import android.content.Intent
import android.os.Build
import java.io.File

internal class APKIntentFactory(private val fileUriProvider: FileUriProvider = FileUriProvider()) {

    fun getInstallAPKIntent(context: Context, apk: File, androidSdkVersion: Int): Intent {
        val uri = fileUriProvider.getFileUri(context, apk, androidSdkVersion)
        return when {
            androidSdkVersion >= Build.VERSION_CODES.P -> InstallAPKIntentForPAndAbove().getIntent(uri)
            androidSdkVersion >= Build.VERSION_CODES.N -> InstallAPKIntentForNToO().getIntent(uri)
            else -> InstallAPKIntentForM().getIntent(uri)
        }
    }
}
