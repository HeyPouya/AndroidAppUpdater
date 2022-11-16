package com.pouyaheydari.appupdater.core.utils

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.core.content.FileProvider
import java.io.File

/**
 * A class to request apk installation in different Android apis
 */
class InstallAPKUtil {

    /**
     * Uses different methods to install the apk depending the user's Android version
     */
    fun installAPK(context: Context, path: String, androidVersion: Int) {
        when {
            androidVersion in 0..Build.VERSION_CODES.M -> installAPKForMAndBellow(context, path)
            androidVersion in Build.VERSION_CODES.N..Build.VERSION_CODES.O ->
                installAPKForNtoO(context, path)
            androidVersion >= Build.VERSION_CODES.P -> installAPKForPAndAbove(context, path)
        }
    }

    private fun installAPKForPAndAbove(context: Context, path: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            if (context.packageManager.canRequestPackageInstalls()) {
                try {
                    val intent = Intent(
                        Intent.ACTION_VIEW,
                        FileProvider.getUriForFile(
                            context,
                            "${context.packageName}.fileProvider.GenericFileProvider",
                            File(path)
                        )
                    )
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(intent)
                } catch (e: ActivityNotFoundException) {
                    Log.d(TAG, e.message.orEmpty())
                }
            } else {
                UnknownSourceInstallRequest().showRequest(context)
            }
        }
    }

    @Suppress("DEPRECATION")
    private fun installAPKForNtoO(context: Context, path: String) {
        val uri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileProvider.GenericFileProvider",
            File(path)
        )
        val install = Intent(Intent.ACTION_INSTALL_PACKAGE)
        install.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        install.data = uri
        install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(install)
    }

    private fun installAPKForMAndBellow(context: Context, path: String) {
        val apkUri = Uri.fromFile(File(path))
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setDataAndType(apkUri, "application/vnd.android.package-archive")
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }
}
