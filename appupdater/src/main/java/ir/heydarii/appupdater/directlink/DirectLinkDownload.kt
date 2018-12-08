package ir.heydarii.appupdater.directlink

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import ir.heydarii.appupdater.dialog.UpdateInProgressDialog


class DirectLinkDownload() {


    fun getApk(url: String, context: Context?, fm: FragmentManager?) {

//        if (Utils.isPermissionGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE, context))
            downloadApk(url, context,fm)
    }

    private fun downloadApk(url: String, context: Context?, fm: FragmentManager?) {
        val downloadManager = DownloadManager.Request(Uri.parse(url))
        downloadManager.setDescription("Downloading new version")
        downloadManager.setTitle("Downloading...")

        downloadManager.allowScanningByMediaScanner()
        downloadManager.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)

        downloadManager.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "myapp.apk");


        val manager = context?.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        manager?.enqueue(downloadManager)

        showAlertDialog(fm)
    }

    private fun showAlertDialog(fm: FragmentManager?) {

        val dialog = UpdateInProgressDialog().show(fm,"UpdateDialog")
    }

}