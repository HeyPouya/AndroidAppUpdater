package com.pouyaheydari.appupdater.directdownload.domain

import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class GetRequestIdUseCaseTest {

    private val repository: UpdateInProgressRepository = mock()
    private val getRequestIdUseCase = GetRequestIdUseCase(repository)

    @Test
    fun `When repository returns request id, then use case returns the same id`() {
        val requestId = 12345L
        whenever(repository.getRequestId()).thenReturn(requestId)

        val result = getRequestIdUseCase()
        assertEquals(requestId, result)
    }
}
