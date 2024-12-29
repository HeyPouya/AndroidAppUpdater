package com.pouyaheydari.appupdater.directdownload.domain

sealed interface DownloadState {
    data object Downloading : DownloadState
    data object Downloaded : DownloadState
}
