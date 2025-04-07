package com.pouyaheydari.appupdater.main.ui

import app.cash.turbine.test
import com.pouyaheydari.appupdater.directdownload.data.DirectDownloadListItem
import com.pouyaheydari.appupdater.directdownload.domain.DownloadState
import com.pouyaheydari.appupdater.directdownload.domain.GetDownloadStateUseCase
import com.pouyaheydari.appupdater.directdownload.domain.SetDownloadStateUseCase
import com.pouyaheydari.appupdater.main.ui.model.DialogScreenIntents
import com.pouyaheydari.appupdater.main.ui.model.DialogScreenStates
import com.pouyaheydari.appupdater.store.domain.StoreFactory
import com.pouyaheydari.appupdater.store.domain.StoreListItem
import com.pouyaheydari.appupdater.store.domain.stores.AppStoreType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.io.File

class AppUpdaterViewModelTest {

    private lateinit var viewModel: AppUpdaterViewModel
    private val isUpdateInProgressUseCase: GetDownloadStateUseCase = mock()
    private val setDownloadStateUseCase: SetDownloadStateUseCase = mock()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        whenever(isUpdateInProgressUseCase()).thenReturn(flowOf(DownloadState.Downloading))
        viewModel = AppUpdaterViewModel(isUpdateInProgressUseCase, setDownloadStateUseCase)
    }

    @Test
    fun `handleIntent should update screen state for OnDirectLinkClicked`() = runTest {
        val testUrl = "https://example.com/app.apk"
        val testItem = DirectDownloadListItem(title = "Test", url = testUrl)

        viewModel.screenState.test {
            viewModel.handleIntent(DialogScreenIntents.OnDirectLinkClicked(testItem))
            assertEquals(DialogScreenStates.HideUpdateInProgress, awaitItem())
            assertEquals(DialogScreenStates.DownloadApk(testUrl), awaitItem())
        }
    }

    @Test
    fun `handleIntent should update screen state for OnStoreClicked`() = runTest {
        val testItem = StoreListItem(store = StoreFactory.getStore(AppStoreType.GOOGLE_PLAY, "package"), title = "Google Play", icon = 0)

        viewModel.screenState.test {
            viewModel.handleIntent(DialogScreenIntents.OnStoreClicked(testItem))
            assertEquals(DialogScreenStates.HideUpdateInProgress, awaitItem())
            assertEquals(DialogScreenStates.OpenStore(testItem.store), awaitItem())
        }
    }

    @Test
    fun `handleIntent should trigger setUpdateInProgress and observe state when OnApkDownloadStarted is received`() = runTest {
        val file: File = mock()
        whenever(isUpdateInProgressUseCase()).thenReturn(flowOf(DownloadState.Downloading, DownloadState.Downloaded(file)))

        viewModel.screenState.test {
            viewModel.handleIntent(DialogScreenIntents.OnApkDownloadStarted)
            verify(setDownloadStateUseCase).invoke(DownloadState.Downloading)
            assertEquals(DialogScreenStates.HideUpdateInProgress, awaitItem())
            assertEquals(DialogScreenStates.InstallApk(file), awaitItem())
        }
    }
}
