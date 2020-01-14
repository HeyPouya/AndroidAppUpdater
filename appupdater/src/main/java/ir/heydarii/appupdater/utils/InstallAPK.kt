package ir.heydarii.appupdater.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import java.io.File

class InstallAPK {

    fun installAPK(context: Context, path: String, androidVersion: Int) {
        when (androidVersion) {
            in 0..Build.VERSION_CODES.M -> installAPKForMAndBellow(context, path)
            in Build.VERSION_CODES.N..Build.VERSION_CODES.O -> installAPKForNtoO(context, path)
            else -> installAPKForPAndAbove(context, path)
        }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun installAPKForPAndAbove(context: Context, path: String) {
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
            } catch (e: Exception) {
                Log.d(Constants.TAG, e.message.orEmpty())
            }
        } else {
            context.startActivity(
                Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES).setData(
                    Uri.parse(
                        String.format("package:%s", context.packageName)
                    )
                )
            )
        }
    }

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