package ir.heydarii.appupdater.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings

/**
 * A class to request to install the apk on android versions greater than 8
 */
class UnknownSourceInstallRequest {

    /**
     * Shows the unknown source install page
     */
    fun showRequest(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startActivity(
                Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES)
                    .setData(Uri.parse(String.format("package:%s", context.packageName)))
            )
        }
    }
}