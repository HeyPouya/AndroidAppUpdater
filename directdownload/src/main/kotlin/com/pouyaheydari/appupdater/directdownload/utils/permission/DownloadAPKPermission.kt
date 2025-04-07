package com.pouyaheydari.appupdater.directdownload.utils.permission

import android.app.Activity

interface DownloadAPKPermission {
    fun resolvePermissions(activity: Activity): Boolean
}
