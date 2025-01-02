package com.pouyaheydari.appupdater.directdownload.domain

import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock

class GetDownloadFilePathUseCaseTest {

    private val repository: UpdateInProgressRepository = mock()
    private val getDownloadFilePathUseCase = GetDownloadFilePathUseCase(repository)

    @Test
    fun `invoke should return file path from repository`() {
        // Given
        val expectedPath = "/path/to/downloaded/file.apk"
        Mockito.`when`(repository.getDownloadFilePath()).thenReturn(expectedPath)

        // When
        val actualPath = getDownloadFilePathUseCase.invoke()

        // Then
        assertEquals(expectedPath, actualPath)
    }
}
