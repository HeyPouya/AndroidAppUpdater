package com.pouyaheydari.appupdater.directdownload.data

import com.pouyaheydari.appupdater.directdownload.domain.DownloadState
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.io.File

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
    fun `When updateApkDownloadState is called, then the correct state is emitted`() = runTest {
        val newState = DownloadState.Downloading

        repository.updateApkDownloadState(newState)

        val state = repository.getDownloadState().first()
        assertEquals(newState, state)
    }

    @Test
    fun `When updateApkDownloadState is called with Downloaded state, then the correct state is emitted`() = runTest {
        val apkFile = File("/path/to/downloaded/apk")
        val newState = DownloadState.Downloaded(apkFile)

        repository.updateApkDownloadState(newState)

        val state = repository.getDownloadState().first()
        assertEquals(newState, state)
    }
}
