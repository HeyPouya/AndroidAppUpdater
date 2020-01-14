package ir.heydarii.appupdater.directlink

import android.Manifest
import android.app.Activity
import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Environment
import android.util.Log
import androidx.fragment.app.FragmentManager
import ir.heydarii.appupdater.R
import ir.heydarii.appupdater.dialog.UpdateInProgressDialog
import ir.heydarii.appupdater.utils.Constants.Companion.APK_NAME
import ir.heydarii.appupdater.utils.Constants.Companion.REQUEST_ID
import ir.heydarii.appupdater.utils.Constants.Companion.TAG
import ir.heydarii.appupdater.utils.Constants.Companion.UPDATE_DIALOG_TAG
import ir.heydarii.appupdater.utils.DownloadAPKUtil
import ir.heydarii.appupdater.utils.InstallAPKUtil
import ir.heydarii.appupdater.utils.PermissionUtils
import ir.heydarii.appupdater.utils.UnknownSourceInstallRequest
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

    private fun installApk(context: Context) {

        //To dismiss the download in progress dialog
        dismissAlertDialog()

        if (!File(getDestination(context)).exists())
            Log.d(TAG, context.getString(R.string.couldnt_find_downloaded_file))
        else
            InstallAPKUtil().installAPK(context, getDestination(context), Build.VERSION.SDK_INT)
    }

    /**
     * Checks for needed permissions and tries to download the apk
     */
    fun getApk(url: String, context: Activity?, fm: FragmentManager?) {

        checkNotNull(context)

        val permission = Manifest.permission.WRITE_EXTERNAL_STORAGE
        val permissionChecker = PermissionUtils()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P && !context.packageManager.canRequestPackageInstalls()) {
            UnknownSourceInstallRequest().showRequest(context)
        }

        if (permissionChecker.isPermissionGranted(permission, context))
            downloadApk(url, context, fm)
        else
            permissionChecker.getPermission(context, arrayOf(permission))
    }

    private fun downloadApk(url: String, context: Context, fm: FragmentManager?) {

        //delete APK if user downloaded the apk before
        deleteExistingFile(getDestination(context))

        //downloads the apk
        DownloadAPKUtil().download(url, context)
        //show alert dialog to user
        showAlertDialog(fm)
    }

    private fun deleteExistingFile(destination: String) {
        val file = File(destination)
        if (file.exists())
            file.delete()
    }

    private fun showAlertDialog(fm: FragmentManager?) {
        fm?.let { UpdateInProgressDialog.instance.show(it, UPDATE_DIALOG_TAG) }
    }

    private fun dismissAlertDialog() {
        if (UpdateInProgressDialog.instance.isAdded)
            UpdateInProgressDialog.instance.dismiss()
    }

    private fun getDestination(context: Context?) =
        "${context?.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)}/$APK_NAME"

}