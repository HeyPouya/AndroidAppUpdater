package ir.heydarii.appupdater.utils

import android.graphics.Typeface

class Constants {
    companion object {
        const val TAG = "AndroidAppUpdater"
        var REQUEST_ID = -10L
        const val UPDATE_DIALOG_TAG = "UpdateDialog"
        const val APK_NAME = "NewAPK"
        const val DATA_LIST = "DATA_LIST"

        //to use typeface while using the dialog
        var typeface: Typeface? = null

    }
}