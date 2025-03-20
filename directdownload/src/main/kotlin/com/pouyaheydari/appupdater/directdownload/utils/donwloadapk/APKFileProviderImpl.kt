package com.pouyaheydari.appupdater.directdownload.utils.donwloadapk

import android.content.Context
import android.os.Environment
import com.pouyaheydari.appupdater.core.utils.APK_NAME
import java.io.File

class APKFileProviderImpl : APKFileProvider {
    override fun getFile(context: Context) =
        File("${context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)}/$APK_NAME")
}
