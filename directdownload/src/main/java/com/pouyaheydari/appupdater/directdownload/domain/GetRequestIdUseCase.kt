package com.pouyaheydari.appupdater.directdownload.domain

internal class GetRequestIdUseCase(private val repository: UpdateInProgressRepository) {
    operator fun invoke(): Long = repository.getRequestId()
}
