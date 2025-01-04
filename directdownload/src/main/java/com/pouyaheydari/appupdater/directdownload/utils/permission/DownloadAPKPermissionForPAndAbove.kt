package com.pouyaheydari.appupdater.directdownload.utils.permission

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.annotation.RequiresApi
import org.jetbrains.annotations.VisibleForTesting
import java.util.Locale


internal class DownloadAPKPermissionForPAndAbove : DownloadAPKPermission {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun resolvePermissions(activity: Activity): Boolean {
        return if (!activity.packageManager.canRequestPackageInstalls()) {
            requestInstallFromUnknownSourcePermission(activity)
            false
        } else {
            true
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun requestInstallFromUnknownSourcePermission(context: Context) {
        val intent = getInstallFromUnknownSourceIntent(context)
        context.startActivity(intent)
    }

    @VisibleForTesting
    @RequiresApi(Build.VERSION_CODES.O)
    internal fun getInstallFromUnknownSourceIntent(context: Context): Intent {
        val uri = Uri.parse(String.format(Locale.ENGLISH, "package:%s", context.packageName))
        val intent = Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, uri).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        return intent
    }
}
