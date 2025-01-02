package com.pouyaheydari.appupdater.directdownload.domain

internal class GetDownloadFilePathUseCase(private val repository: UpdateInProgressRepository) {
    operator fun invoke(): String = repository.getDownloadFilePath()
}
