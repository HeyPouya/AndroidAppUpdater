package com.pouyaheydari.appupdater.directdownload.utils.permission

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

private const val PERMISSION_REQUEST_CODE = 2000

internal class DownloadAPKPermissionForOAndBellow : DownloadAPKPermission {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun resolvePermissions(activity: Activity): Boolean {
        return if (isExternalStoragePermissionGranted(activity)) {
            true
        } else {
            getWriteToStoragePermission(activity)
            false
        }
    }

    private fun isExternalStoragePermissionGranted(activity: Activity) =
        ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED

    private fun getWriteToStoragePermission(activity: Activity) {
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
            PERMISSION_REQUEST_CODE,
        )
    }
}
