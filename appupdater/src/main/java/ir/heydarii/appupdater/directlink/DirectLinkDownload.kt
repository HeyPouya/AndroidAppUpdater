package ir.heydarii.appupdater.directlink

import android.Manifest
import android.app.Activity
import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.Settings
import android.util.Log
import androidx.core.content.FileProvider
import androidx.fragment.app.FragmentManager
import ir.heydarii.appupdater.R
import ir.heydarii.appupdater.dialog.UpdateInProgressDialog
import ir.heydarii.appupdater.utils.Constants.Companion.APK_NAME
import ir.heydarii.appupdater.utils.Constants.Companion.REQUEST_ID
import ir.heydarii.appupdater.utils.Constants.Companion.TAG
import ir.heydarii.appupdater.utils.Constants.Companion.UPDATE_DIALOG_TAG
import ir.heydarii.appupdater.utils.PermissionUtils
import ir.heydarii.appupdater.utils.installAPKForQ
import java.io.File

/**
 * starts a download manager and downloads apk
 * also shows a loading indicator showing the apk is downloading
 * after download finishes , opens install page
 */
class DirectLinkDownload : BroadcastReceiver() {

    /**
     * To show install page when apk got downloaded successfully
     */
    override fun onReceive(context: Context?, intent: Intent?) {

        val action = intent?.action
        if (DownloadManager.ACTION_DOWNLOAD_COMPLETE == action) {
            val referenceId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)

            // if downloaded file is our apk
            if (referenceId == REQUEST_ID) {
                installApk(context!!)
            }
        }
    }

    /**
     * Shows install apk page in all versions of android devices
     */
    private fun installApk(context: Context) {

        //To dismiss the download in progress dialog
        dismissAlertDialog()

        if (!File(getDestination(context)).exists()) {
            Log.d(TAG, context.getString(R.string.couldnt_find_downloaded_file))
        }
        // In android 9 and above
        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            installAPKForQ(context, getDestination(context))
        }
        // In android 7 and above
        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val uri = FileProvider.getUriForFile(
                context,
                "${context.packageName}.fileProvider.GenericFileProvider",
                File(getDestination(context))
            )
            val install = Intent(Intent.ACTION_INSTALL_PACKAGE)
            install.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            install.data = uri
            install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(install)
        }
        // in android 6 and bellow
        else {
            val apkUri = Uri.fromFile(File(getDestination(context)))
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive")
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }
    }

    fun getApk(url: String, context: Activity?, fm: FragmentManager?) {

        checkNotNull(context)

        val permission = Manifest.permission.WRITE_EXTERNAL_STORAGE
        val permissionChecker = PermissionUtils()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P && !context.packageManager.canRequestPackageInstalls()) {
            context.startActivity(
                Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES).setData(
                    Uri.parse(
                        String.format("package:%s", context.packageName)
                    )
                )
            )
        }

        if (permissionChecker.isPermissionGranted(permission, context))
            downloadApk(url, context, fm)
        else
            permissionChecker.getPermission(context, arrayOf(permission))
    }

    /**
     * sets a download manager and enqueues the download request
     */
    private fun downloadApk(url: String, context: Context?, fm: FragmentManager?) {

        val downloadManager = DownloadManager.Request(Uri.parse(url))

        // setting title and description to be shown on download notification
        downloadManager.setTitle(context?.getString(R.string.download_notification_title))
        downloadManager.setDescription(context?.getString(R.string.download_notification_description))

        //setting up download manager's properties
        downloadManager.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
        downloadManager.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)

        //setting the destination of the downloaded file
        downloadManager.setDestinationInExternalFilesDir(
            context,
            Environment.DIRECTORY_DOWNLOADS,
            "$APK_NAME.apk"
        )

        //delete APK if user downloaded the apk before
        deleteExistingFile(getDestination(context))

        //enqueue the file to start download
        val manager = context?.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        REQUEST_ID = manager.enqueue(downloadManager)

        //show alert dialog to user
        showAlertDialog(fm)
    }

    /**
     * Delete Downloaded APK if previously downloaded
     */
    private fun deleteExistingFile(destination: String) {
        val file = File(destination)
        if (file.exists())
            file.delete()
    }

    /**
     * shows a progress dialog for user to show download is in progress
     */
    private fun showAlertDialog(fm: FragmentManager?) {
        fm?.let { UpdateInProgressDialog.instance.show(it, UPDATE_DIALOG_TAG) }
    }

    private fun dismissAlertDialog() {
        if (UpdateInProgressDialog.instance.isAdded)
            UpdateInProgressDialog.instance.dismiss()
    }

    private fun getDestination(context: Context?) =
        "${context?.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)}/$APK_NAME.apk"

}