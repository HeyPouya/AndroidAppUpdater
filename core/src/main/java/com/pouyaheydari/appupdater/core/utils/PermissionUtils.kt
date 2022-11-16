package com.pouyaheydari.appupdater.core.utils

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

private const val PERMISSION_REQUEST_CODE = 2000

/**
 * Some utilities to use in library
 */
class PermissionUtils {
    /**
     * Check if application has a permission or not
     */
    fun isPermissionGranted(permission: String, context: Context?): Boolean = if (context != null) {
        ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
    } else {
        false
    }

    /**
     * shows get permission page to user
     */
    fun getPermission(activity: Activity?, permission: Array<String>) {
        if (activity != null) {
            ActivityCompat.requestPermissions(activity, permission, PERMISSION_REQUEST_CODE)
        } else {
            throw NullPointerException("Provided activity is null")
        }
    }
}
