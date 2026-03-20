package com.pouyaheydari.appupdater.directdownload.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Represents a direct download item to be displayed in the updater dialog.
 *
 * @property title the display title shown to the user
 * @property url the direct URL to the APK file
 */
@Parcelize
data class DirectDownloadListItem(
    val title: String = "",
    val url: String = "",
) : Parcelable
