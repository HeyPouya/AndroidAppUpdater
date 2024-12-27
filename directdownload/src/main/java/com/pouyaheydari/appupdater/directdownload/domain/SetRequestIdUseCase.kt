package com.pouyaheydari.appupdater.directdownload.domain

internal class SetRequestIdUseCase(private val repository: UpdateInProgressRepository) {
    operator fun invoke(requestId: Long) {
        repository.setRequestId(requestId)
    }
}
