package com.pouyaheydari.appupdater.directdownload.domain

internal class SetDownloadFilePathUseCase(private val repository: UpdateInProgressRepository) {
    operator fun invoke(path: String) {
        repository.setDownloadFilePath(path)
    }
}
