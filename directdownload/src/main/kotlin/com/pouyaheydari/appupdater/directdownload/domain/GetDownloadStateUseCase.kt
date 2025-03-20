package com.pouyaheydari.appupdater.directdownload.domain

import kotlinx.coroutines.flow.Flow

class GetDownloadStateUseCase(private val repository: UpdateInProgressRepository) {
    operator fun invoke(): Flow<DownloadState> = repository.getDownloadState()
}
