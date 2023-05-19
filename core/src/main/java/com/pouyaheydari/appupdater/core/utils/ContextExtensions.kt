package com.pouyaheydari.appupdater.core.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.Settings
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.File
import java.util.Locale

private const val PERMISSION_REQUEST_CODE = 2000

/**
 * This function deletes the previously downloaded apk file if present,
 * to avoid filling up user's phone storage.
 */
internal fun Context.deleteExistingFile() {
    val file = getExistingApk()
    if (file.exists()) {
        file.delete()
    }
}

/**
 * @return a File to the previously downloaded APK file if present.
 */
internal fun Context.getExistingApk() = File("${this.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)}/$APK_NAME")

/**
 * Check if application has a permission or not
 */
internal fun Context?.isPermissionGranted(permission: String): Boolean = if (this != null) {
    ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
} else {
    false
}

/**
 * shows get permission page to user
 */
internal fun Activity?.getPermission(permission: Array<String>) {
    if (this != null) {
        ActivityCompat.requestPermissions(this, permission, PERMISSION_REQUEST_CODE)
    } else {
        throw NullPointerException("Provided activity is null")
    }
}

/**
 * Shows the unknown source install page
 */
internal fun Context.showRequest() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        this.startActivity(
            Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES)
                .setData(Uri.parse(String.format(Locale.ENGLISH, "package:%s", this.packageName))),
        )
    }
}
