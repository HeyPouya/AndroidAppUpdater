package ir.heydarii.appupdater.utils

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

/**
 * Some utilities to use in library
 */
class PermissionUtils {
    /**
     * Check if application has a permission or not
     */
    fun isPermissionGranted(permission: String, context: Context?): Boolean {
        return if (context != null) {
            ContextCompat.checkSelfPermission(
                context,
                permission
            ) == PackageManager.PERMISSION_GRANTED
        } else
            return false
    }

    /**
     * shows get permission page to user
     */
    fun getPermission(activity: Activity?, permission: Array<String>) {
        if (activity != null)
            ActivityCompat.requestPermissions(activity, permission, 2000)
        else
            throw NullPointerException("Provided activity is null")
    }

}