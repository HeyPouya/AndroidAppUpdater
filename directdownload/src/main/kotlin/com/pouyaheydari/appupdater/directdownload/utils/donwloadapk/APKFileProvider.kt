package com.pouyaheydari.appupdater.directdownload.utils.donwloadapk

import android.content.Context
import java.io.File

interface APKFileProvider {
    fun getFile(context: Context): File
}
