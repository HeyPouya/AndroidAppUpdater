package ir.heydarii.appupdater.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings

class RequestForAppInstallUtil {
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