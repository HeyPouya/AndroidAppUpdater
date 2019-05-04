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
import android.util.Log
import androidx.core.content.FileProvider
import androidx.fragment.app.FragmentManager
import ir.heydarii.appupdater.R
import ir.heydarii.appupdater.dialog.UpdateInProgressDialog
import ir.heydarii.appupdater.utils.Utils
import java.io.File


//Constants
var REQUEST_ID = -10L
const val UPDATE_DIALOG_TAG = "UpdateDialog"
const val FOLDER_NAME = "ApkUpdate"
const val APK_NAME = "NewAPK"
val DESTINATION = Environment.getExternalStorageDirectory().toString() + "/$FOLDER_NAME/" + "$APK_NAME.apk"


/*
    starts a download manager and downloads apk
    also shows a loading indicator showing the apk is downloading
    after download finishes , opens install page
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
    fun installApk(context: Context) {

        //To dismiss the download in progress dialog
        dismissAlertDialog()


        if (!File(DESTINATION).exists()) {
            Log.d(Utils.TAG, context.getString(R.string.couldnt_find_downloaded_file))
        }

        // In android 7 and above
        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val uri = FileProvider.getUriForFile(
                context,
                "${context.packageName}.fileProvider.GenericFileProvider",
                File(DESTINATION)
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
            val apkUri = Uri.fromFile(File(DESTINATION))
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive")
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }
    }

    fun getApk(url: String, context: Activity?, fm: FragmentManager?) {
        val permission = Manifest.permission.WRITE_EXTERNAL_STORAGE
        if (Utils.isPermissionGranted(permission, context))
            downloadApk(url, context, fm)
        else
            Utils.getPermission(context, arrayOf(permission))

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
        downloadManager.allowScanningByMediaScanner()
        downloadManager.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
        downloadManager.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
        downloadManager.setVisibleInDownloadsUi(true)

        //setting the destination of the downloaded file
        downloadManager.setDestinationInExternalPublicDir("/$FOLDER_NAME", "$APK_NAME.apk");

        //delete APK if user downloaded the apk before
        deleteExistingFile()

        //enqueue the file to start download
        val manager = context?.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        REQUEST_ID = manager.enqueue(downloadManager)

        //show alert dialog to user
        showAlertDialog(fm)
    }

    /**
     * Delete Downloaded APK if previously downloaded
     */
    private fun deleteExistingFile() {
        val file = File(DESTINATION)
        if (file.exists())
            file.delete()
    }

    /**
     * shows a progress dialog for user to show download is in progress
     */
    private fun showAlertDialog(fm: FragmentManager?) {
        UpdateInProgressDialog.instance.show(fm, UPDATE_DIALOG_TAG)
    }

    private fun dismissAlertDialog() {
        if (UpdateInProgressDialog.instance.isAdded)
            UpdateInProgressDialog.instance.dismiss()
    }

}