package com.pouyaheydari.appupdater.directdownload.utils.permission

internal class DownloadAPKPermissionFactory {

    fun getDownloadAPKPermissionHandler(androidSdkVersion: Int): DownloadAPKPermission = when {
        androidSdkVersion >= android.os.Build.VERSION_CODES.P -> DownloadAPKPermissionForPAndAbove()
        else -> DownloadAPKPermissionForOAndBellow()
    }
}
