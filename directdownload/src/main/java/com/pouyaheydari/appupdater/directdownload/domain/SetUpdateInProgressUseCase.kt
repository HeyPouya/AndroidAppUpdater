package com.pouyaheydari.appupdater.directdownload.domain

class SetUpdateInProgressUseCase(private val repository: UpdateInProgressRepository) {
    suspend operator fun invoke(isUpdateInProgress: Boolean) {
        repository.updateApkDownloadProgress(isUpdateInProgress)
    }
}
