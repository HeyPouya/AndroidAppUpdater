package ir.heydarii.appupdater.utils

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import ir.heydarii.appupdater.utils.Constants.Companion.TAG
import java.io.File


@RequiresApi(Build.VERSION_CODES.O)
fun installAPKForQ(
    context: Context,
    pathname: String?
) {
    if (context.packageManager.canRequestPackageInstalls()) {
        try {
            val intent = Intent(
                Intent.ACTION_VIEW,
                FileProvider.getUriForFile(
                    context,
                    "${context.packageName}.fileProvider.GenericFileProvider",
                    File(pathname)
                )
            )
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            intent.addFlags(FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        } catch (e: Exception) {
            Log.d(TAG, e.message)
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
