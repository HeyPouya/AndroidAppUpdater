package ir.heydarii.appupdater.utils

import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Typeface
import androidx.core.content.ContextCompat

/**
 * SOme utilities to use in library
 */
class Utils {

    companion object {
        /**
         * Check if application has a permission or not
         */
        fun isPermissionGranted(permission: String, context: Context?): Boolean {
            return if (context != null) {
                ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
            } else
                return false
        }

        var typeface: Typeface? = null
    }

}