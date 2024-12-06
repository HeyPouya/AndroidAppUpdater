package com.pouyaheydari.appupdater.directdownload.utils

import android.content.Context
import android.os.Environment
import java.io.File

/**
 * @return a File to the previously downloaded APK file if present.
 */
internal fun Context.getExistingApk() = File("${getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)}/$APK_NAME")
