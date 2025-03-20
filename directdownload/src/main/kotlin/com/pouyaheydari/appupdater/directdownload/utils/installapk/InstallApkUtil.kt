package com.pouyaheydari.appupdater.directdownload.utils.installapk

import android.content.ActivityNotFoundException
import android.content.Context
import android.util.Log
import com.pouyaheydari.appupdater.core.utils.ANDROID_APP_UPDATER_DEBUG_TAG
import java.io.File

fun installAPK(context: Context, apk: File, androidSdkVersion: Int, apkIntentFactory: APKIntentFactory = APKIntentFactory()) {
    if (!apk.exists()) {
        Log.e(ANDROID_APP_UPDATER_DEBUG_TAG, "APK file not found")
        return
    }

    val intent = apkIntentFactory.getInstallAPKIntent(context, apk, androidSdkVersion)
    try {
        context.startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        Log.e(ANDROID_APP_UPDATER_DEBUG_TAG, "Activity not found for installing APK: ${e.message.orEmpty()}")
    }
}
