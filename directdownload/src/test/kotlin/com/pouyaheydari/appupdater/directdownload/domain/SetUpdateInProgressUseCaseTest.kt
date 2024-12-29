package com.pouyaheydari.appupdater.directdownload.domain

import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class SetUpdateInProgressUseCaseTest {

    private val repository: UpdateInProgressRepository = mock()
    private val setUpdateInProgressUseCase = SetUpdateInProgressUseCase(repository)

    @Test
    fun `When invoked with isUpdateInProgress, then repository with the correct value is called`() = runTest {
        val isUpdateInProgress = true

        setUpdateInProgressUseCase(isUpdateInProgress)

        verify(repository).updateApkDownloadProgress(isUpdateInProgress)
    }
}
