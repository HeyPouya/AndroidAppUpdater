package ir.heydarii.appupdater.directlink

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Environment
import androidx.core.content.FileProvider
import androidx.fragment.app.FragmentManager
import ir.heydarii.appupdater.dialog.UpdateInProgressDialog
import java.io.File


var REQUEST_ID = -10L
val DESTINATION = Environment.getExternalStorageDirectory().toString() + "/shahrdad/" + "shahrdad.apk"

class DirectLinkDownload() : BroadcastReceiver() {


    override fun onReceive(context: Context?, intent: Intent?) {

        val action = intent?.action;
        if (DownloadManager.ACTION_DOWNLOAD_COMPLETE == action) {
            val referenceId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)

            if (referenceId == REQUEST_ID) {

                installApk(context!!)


            }

        }
    }

    private fun installApk(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val uri =
                FileProvider.getUriForFile(context, "ir.heydarii.appupdater.GenericFileProvider", File(DESTINATION));
            val install = Intent(Intent.ACTION_INSTALL_PACKAGE)
            install.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            install.data = uri
            install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(install)
        }else{
            val apkUri = Uri.fromFile(File(DESTINATION))
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive")
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)

        }

    }

    fun getApk(url: String, context: Context?, fm: FragmentManager?) {

//        if (Utils.isPermissionGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE, context))
        downloadApk(url, context, fm)
    }

    private fun downloadApk(url: String, context: Context?, fm: FragmentManager?) {

        val downloadManager = DownloadManager.Request(Uri.parse(url))
        downloadManager.setDescription("Downloading new version")
        downloadManager.setTitle("Downloading...")

        downloadManager.allowScanningByMediaScanner()
        downloadManager.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
        downloadManager.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
        downloadManager.setVisibleInDownloadsUi(true)
        downloadManager.setDestinationInExternalPublicDir("/shahrdad", "shahrdad.apk");


        val manager = context?.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager


        val file = File(DESTINATION)
        if (file.exists())
            file.delete()

        REQUEST_ID = manager?.enqueue(downloadManager)

        showAlertDialog(fm)
    }

    private fun showAlertDialog(fm: FragmentManager?) {

        UpdateInProgressDialog().show(fm, "UpdateDialog")
    }

}