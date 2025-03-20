package com.pouyaheydari.appupdater.directdownload.domain

import java.io.File

sealed interface DownloadState {
    data object Downloading : DownloadState
    data class Downloaded(val apk: File) : DownloadState
}
