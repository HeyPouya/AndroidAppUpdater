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
import androidx.fragment.app.FragmentManager
import ir.heydarii.appupdater.R
import ir.heydarii.appupdater.dialog.UpdateInProgressDialog
import ir.heydarii.appupdater.utils.Constants.Companion.APK_NAME
import ir.heydarii.appupdater.utils.Constants.Companion.REQUEST_ID
import ir.heydarii.appupdater.utils.Constants.Companion.TAG
import ir.heydarii.appupdater.utils.Constants.Companion.UPDATE_DIALOG_TAG
import ir.heydarii.appupdater.utils.InstallAPK
import ir.heydarii.appupdater.utils.PermissionUtils
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
        if (action == DownloadManager.ACTION_DOWNLOAD_COMPLETE) {
            val referenceId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)

            // if downloaded file is our apk
            if (referenceId == REQUEST_ID) {
                context?.let { installApk(it) }
            }
        }
    }

    /**
     * Shows install apk page in all versions of android devices
     */
    private fun installApk(context: Context) {

        //To dismiss the download in progress dialog
        dismissAlertDialog()

        if (!File(getDestination(context)).exists())
            Log.d(TAG, context.getString(R.string.couldnt_find_downloaded_file))
        else
            InstallAPK().installAPK(context, getDestination(context), Build.VERSION.SDK_INT)
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