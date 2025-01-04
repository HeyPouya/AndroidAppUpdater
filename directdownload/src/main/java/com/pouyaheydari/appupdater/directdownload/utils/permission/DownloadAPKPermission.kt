package com.pouyaheydari.appupdater.directdownload.utils.permission

import android.app.Activity

internal interface DownloadAPKPermission {
    fun resolvePermissions(activity: Activity): Boolean
}
