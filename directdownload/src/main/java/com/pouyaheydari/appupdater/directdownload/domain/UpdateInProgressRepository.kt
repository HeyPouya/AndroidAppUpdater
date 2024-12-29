package com.pouyaheydari.appupdater.directdownload.domain

import kotlinx.coroutines.flow.Flow

interface UpdateInProgressRepository {
    fun getRequestId(): Long
    fun setRequestId(id: Long)
    fun getDownloadState(): Flow<DownloadState>
    suspend fun updateApkDownloadState(state: DownloadState)
}
