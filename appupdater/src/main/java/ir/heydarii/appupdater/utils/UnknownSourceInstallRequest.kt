package ir.heydarii.appupdater.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.annotation.RequiresApi

/**
 * A class to request to install the apk on android versions greater than 8
 */
class UnknownSourceInstallRequest {

    /**
     * Shows the unknown source install page
     */
    @RequiresApi(Build.VERSION_CODES.O)
    fun showRequest(context: Context) {
        context.startActivity(
            Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES).setData(
                Uri.parse(
                    String.format("package:%s", context.packageName)
                )
            )
        )
    }
}