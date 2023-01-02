package com.pouyaheydari.appupdater.core.utils

// A file to store constants of the project

const val TAG = "AndroidAppUpdater"
var requestId: Long = -10L
const val APK_NAME = "NewAPK.apk"
var showUpdateInProgressCallback: ((Boolean) -> Unit)? = null
