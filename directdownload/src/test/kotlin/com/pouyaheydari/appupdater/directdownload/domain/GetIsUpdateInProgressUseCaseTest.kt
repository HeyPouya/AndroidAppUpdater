package com.pouyaheydari.appupdater.directdownload.domain

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class GetIsUpdateInProgressUseCaseTest {

    private val repository: UpdateInProgressRepository = mock()
    private val getIsUpdateInProgressUseCase = GetIsUpdateInProgressUseCase(repository)

    @Test
    fun `When update in progress state is true, then use case returns true`() = runTest {
        whenever(repository.getUpdateInProgressFlow()).thenReturn(flowOf(true))

        val isInProgress = getIsUpdateInProgressUseCase().first()
        assertEquals(true, isInProgress)
    }

    @Test
    fun `When update in progress state is false, then use case returns false`() = runTest {
        whenever(repository.getUpdateInProgressFlow()).thenReturn(flowOf(false))

        val isInProgress = getIsUpdateInProgressUseCase().first()
        assertEquals(false, isInProgress)
    }
}
