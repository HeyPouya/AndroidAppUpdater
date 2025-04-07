package com.pouyaheydari.appupdater.directdownload.domain

import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class SetDownloadStateUseCaseTest {

    private val repository: UpdateInProgressRepository = mock()
    private val setDownloadStateUseCase = SetDownloadStateUseCase(repository)

    @Test
    fun `When invoked, then repository with the correct value is called`() = runTest {
        val downloadState = DownloadState.Downloading

        setDownloadStateUseCase(downloadState)

        verify(repository).updateApkDownloadState(downloadState)
    }
}
