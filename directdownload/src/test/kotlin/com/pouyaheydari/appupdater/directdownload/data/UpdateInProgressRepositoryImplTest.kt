package com.pouyaheydari.appupdater.directdownload.data

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class UpdateInProgressRepositoryImplTest {

    private lateinit var repository: UpdateInProgressRepositoryImpl

    @Before
    fun setUp() {
        repository = UpdateInProgressRepositoryImpl
    }

    @Test
    fun `When request id is set by setRequestId, then correct request id will be returned by getRequestId`() {
        val newRequestId = 12345L

        repository.setRequestId(newRequestId)

        val requestId = repository.getRequestId()
        assertEquals(newRequestId, requestId)
    }

    @Test
    fun `When the download in progress state is changed, then the correct value is being published to observers`() = runTest {
        repository.updateApkDownloadProgress(true)

        val isInProgress = repository.getUpdateInProgressFlow().first()
        assertEquals(true, isInProgress)
    }
}
