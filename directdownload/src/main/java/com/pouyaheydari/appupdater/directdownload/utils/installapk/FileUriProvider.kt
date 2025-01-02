package com.pouyaheydari.appupdater.directdownload.utils.installapk

import android.content.Context
import android.net.Uri
import android.os.Build
import androidx.core.content.FileProvider
import java.io.File

class FileUriProvider {

    fun getFileUri(context: Context, apk: File, androidSdkVersion: Int): Uri = when (androidSdkVersion) {
        Build.VERSION_CODES.M -> Uri.fromFile(apk)
        else -> FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", apk)
    }
}
