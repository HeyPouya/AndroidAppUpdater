package com.pouyaheydari.appupdater.directdownload.domain

import kotlinx.coroutines.flow.Flow

class GetIsUpdateInProgressUseCase(private val repository: UpdateInProgressRepository) {
    operator fun invoke(): Flow<Boolean> = repository.getUpdateInProgressFlow()
}
