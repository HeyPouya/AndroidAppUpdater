package ir.heydarii.appupdater.utils

import android.content.Context
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
            context?.let { return ContextCompat.checkSelfPermission(it, permission) == 1 }
        }
    }
}