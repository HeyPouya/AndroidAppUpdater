package com.pouyaheydari.appupdater.directdownload.domain

import java.io.File

/**
 * Represents the current state of an APK download.
 */
sealed interface DownloadState {
    /** The APK is currently being downloaded. */
    data object Downloading : DownloadState

    /** The APK has been downloaded successfully. */
    data class Downloaded(
        val apk: File,
    ) : DownloadState

    /**
     * The APK download failed.
     *
     * @property reason a human-readable description of the failure, or null if unknown
     */
    data class Failed(
        val reason: String? = null,
    ) : DownloadState
}
