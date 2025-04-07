package com.pouyaheydari.appupdater.compose.ui

import app.cash.turbine.test
import com.pouyaheydari.appupdater.compose.ui.models.DialogScreenIntents
import com.pouyaheydari.appupdater.compose.ui.models.UpdaterViewModelData
import com.pouyaheydari.appupdater.directdownload.data.DirectDownloadListItem
import com.pouyaheydari.appupdater.directdownload.domain.DownloadState
import com.pouyaheydari.appupdater.directdownload.domain.GetDownloadStateUseCase
import com.pouyaheydari.appupdater.directdownload.domain.SetDownloadStateUseCase
import com.pouyaheydari.appupdater.store.domain.StoreListItem
import com.pouyaheydari.appupdater.store.domain.stores.AppStore
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class AndroidAppUpdaterViewModelTest {

    private val getDownloadStateUseCase: GetDownloadStateUseCase = mock()
    private val setDownloadStateUseCase: SetDownloadStateUseCase = mock()
    private lateinit var viewModel: AndroidAppUpdaterViewModel

    @Before
    fun setup() {
        val viewModelData = UpdaterViewModelData()
        viewModel = AndroidAppUpdaterViewModel(viewModelData, getDownloadStateUseCase, setDownloadStateUseCase)
    }

    @Test
    fun `handleIntent OnStoreClicked updates UI state correctly`() = runTest {
        val store: AppStore = mock()
        val storeItem = StoreListItem(store)

        viewModel.handleIntent(DialogScreenIntents.OnStoreClicked(storeItem))

        viewModel.uiState.test {
            assertEquals(store, awaitItem().selectedStore)
        }
    }

    @Test
    fun `handleIntent OnDirectLinkClicked updates UI state correctly`() = runTest {
        val url = "https://example.com/app.apk"
        val title = "Direct Link"
        val downloadItem = DirectDownloadListItem(title = title, url = url)

        viewModel.handleIntent(DialogScreenIntents.OnDirectLinkClicked(downloadItem))

        viewModel.uiState.test {
            val item = awaitItem()
            assertEquals(url, item.downloadState.downloadUrl)
            assertTrue(item.downloadState.shouldStartAPKDownload)
        }
    }

    @Test
    fun `handleIntent OnApkDownloadStarted triggers setDownloadStateUseCase`() = runTest {
        whenever(getDownloadStateUseCase()).thenReturn(flowOf(DownloadState.Downloading))

        viewModel.handleIntent(DialogScreenIntents.OnApkDownloadStarted)

        verify(setDownloadStateUseCase).invoke(DownloadState.Downloading)
    }
}
