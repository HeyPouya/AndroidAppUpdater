package com.pouyaheydari.appupdater.directdownload.domain

class SetRequestIdUseCase(private val repository: UpdateInProgressRepository) {
    operator fun invoke(requestId: Long) {
        repository.setRequestId(requestId)
    }
}
