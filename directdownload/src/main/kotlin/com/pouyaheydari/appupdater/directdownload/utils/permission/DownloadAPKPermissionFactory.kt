package com.pouyaheydari.appupdater.directdownload.utils.permission

class DownloadAPKPermissionFactory {

    fun getDownloadAPKPermissionHandler(androidSdkVersion: Int): DownloadAPKPermission = when {
        androidSdkVersion >= android.os.Build.VERSION_CODES.P -> DownloadAPKPermissionForPAndAbove()
        else -> DownloadAPKPermissionForOAndBellow()
    }
}
