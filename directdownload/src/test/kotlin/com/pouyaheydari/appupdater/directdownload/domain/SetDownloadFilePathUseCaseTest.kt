package com.pouyaheydari.appupdater.directdownload.domain

import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class SetDownloadFilePathUseCaseTest {

    private val repository: UpdateInProgressRepository = mock()
    private val setDownloadFilePathUseCase = SetDownloadFilePathUseCase(repository)

    @Test
    fun `invoke should set file path in repository`() {
        // Given
        val path = "/path/to/downloaded/file.apk"

        // When
        setDownloadFilePathUseCase.invoke(path)

        // Then
        verify(repository).setDownloadFilePath(path)
    }
}
