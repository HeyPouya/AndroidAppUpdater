package ir.heydarii.appupdater.utils

import android.content.Context
import androidx.core.content.ContextCompat


class Utils {

    companion object {
        fun isPermissionGranted(permission: String, context: Context?): Boolean {
            context?.let { return ContextCompat.checkSelfPermission(it, permission) == 1 }
        }
    }
}