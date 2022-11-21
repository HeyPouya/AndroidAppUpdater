package com.pouyaheydari.appupdater.core.utils

import android.graphics.Typeface

// A file to store constants of the project

const val TAG = "AndroidAppUpdater"
var requestId: Long = -10L
const val APK_NAME = "NewAPK.apk"

// to use typeface while using the dialog
var tf: Typeface? = null
var showUpdateInProgressCallback: ((Boolean) -> Unit)? = null
