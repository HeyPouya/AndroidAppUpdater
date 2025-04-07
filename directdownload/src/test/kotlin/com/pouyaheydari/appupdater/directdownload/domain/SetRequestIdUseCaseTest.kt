package com.pouyaheydari.appupdater.directdownload.domain

import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class SetRequestIdUseCaseTest {

    private val repository: UpdateInProgressRepository = mock()
    private val setRequestIdUseCase = SetRequestIdUseCase(repository)

    @Test
    fun `When invoked with request id, then repository with the correct request id is called`() {
        val requestId = 12345L

        setRequestIdUseCase(requestId)

        verify(repository).setRequestId(requestId)
    }
}
