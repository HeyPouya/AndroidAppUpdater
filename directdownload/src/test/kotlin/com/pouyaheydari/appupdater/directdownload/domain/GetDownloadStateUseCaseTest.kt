package com.pouyaheydari.appupdater.directdownload.domain

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.io.File

class GetDownloadStateUseCaseTest {

    private val repository: UpdateInProgressRepository = mock()
    private val getDownloadStateUseCase = GetDownloadStateUseCase(repository)

    @Test
    fun `When state is downloaded, then use case returns the correct state`() = runTest {
        val expected = DownloadState.Downloaded(File(""))
        whenever(repository.getDownloadState()).thenReturn(flowOf(expected))

        val downloadState = getDownloadStateUseCase().first()
        assertEquals(expected, downloadState)
    }

    @Test
    fun `When state is downloading, then use case returns the correct state`() = runTest {
        whenever(repository.getDownloadState()).thenReturn(flowOf(DownloadState.Downloading))

        val downloadState = getDownloadStateUseCase().first()
        assertEquals(DownloadState.Downloading, downloadState)
    }
}
