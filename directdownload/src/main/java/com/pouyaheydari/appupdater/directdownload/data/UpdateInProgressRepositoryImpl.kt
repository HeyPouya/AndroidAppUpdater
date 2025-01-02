package com.pouyaheydari.appupdater.directdownload.data

import com.pouyaheydari.appupdater.directdownload.domain.DownloadState
import com.pouyaheydari.appupdater.directdownload.domain.UpdateInProgressRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import java.util.concurrent.atomic.AtomicLong

private const val NO_REQUEST_ID = -10L

object UpdateInProgressRepositoryImpl : UpdateInProgressRepository {
    private var requestId: AtomicLong = AtomicLong(NO_REQUEST_ID)
    private var filePath: String = ""
    private val downloadState = MutableSharedFlow<DownloadState>(replay = 1)

    override fun getRequestId(): Long = requestId.get()

    override fun setRequestId(id: Long) {
        requestId.set(id)
    }

    override fun setDownloadFilePath(path: String) {
        filePath = path
    }

    override fun getDownloadFilePath(): String = filePath

    override fun getDownloadState(): Flow<DownloadState> = downloadState.asSharedFlow()

    override suspend fun updateApkDownloadState(state: DownloadState) {
        downloadState.emit(state)
    }
}
