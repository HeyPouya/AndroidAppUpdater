package ir.heydarii.appupdater.pojomodel

import android.content.Context
import android.content.pm.PackageManager
import android.content.pm.PackageInfo


class Utils {

    companion object {
        fun isPackageExisted(context: Context?, targetPackage: String): Boolean {

            if (context == null)
                return false

            val pm = context.packageManager
            try {
                val info = pm.getPackageInfo(targetPackage, PackageManager.GET_META_DATA)
            } catch (e: PackageManager.NameNotFoundException) {
                return false
            }
            return true
        }

    }
}