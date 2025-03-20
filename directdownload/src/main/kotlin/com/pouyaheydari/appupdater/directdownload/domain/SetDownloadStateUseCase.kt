package com.pouyaheydari.appupdater.directdownload.domain

class SetDownloadStateUseCase(private val repository: UpdateInProgressRepository) {
    suspend operator fun invoke(downloadState: DownloadState) {
        repository.updateApkDownloadState(downloadState)
    }
}
