package com.pouyaheydari.appupdater.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pouyaheydari.appupdater.directdownload.data.model.DirectDownloadListItem
import com.pouyaheydari.appupdater.directdownload.domain.GetIsUpdateInProgressUseCase
import com.pouyaheydari.appupdater.directdownload.domain.SetUpdateInProgressUseCase
import com.pouyaheydari.appupdater.main.pojo.DialogStates
import com.pouyaheydari.appupdater.main.utils.ErrorCallbackHolder
import com.pouyaheydari.appupdater.main.utils.TypefaceHolder
import com.pouyaheydari.appupdater.store.domain.StoreListItem
import com.pouyaheydari.appupdater.store.domain.stores.AppStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * View Model of [AppUpdaterDialog]
 */
internal class AppUpdaterViewModel(
    private val isUpdateInProgressUseCase: GetIsUpdateInProgressUseCase,
    private val setUpdateInProgressUseCase: SetUpdateInProgressUseCase
) : ViewModel() {
    val screenState = MutableStateFlow<DialogStates>(DialogStates.HideUpdateInProgress)

    fun onStoreClicked(item: StoreListItem) {
        screenState.value = DialogStates.OpenStore(item.store)
    }

    fun onStoreOpened() {
        screenState.value = DialogStates.Empty
    }

    fun onDirectDownloadLinkClicked(item: DirectDownloadListItem) {
        screenState.value = DialogStates.DownloadApk(item.url)
    }

    fun onErrorCallbackCalled() {
        screenState.value = DialogStates.Empty
    }

    fun onDownloadStarted() {
        setUpdateInProgress()
        observeUpdateInProgressStatus()
    }

    private fun setUpdateInProgress() {
        viewModelScope.launch {
            setUpdateInProgressUseCase(true)
        }
    }

    fun onErrorWhileOpeningStore(store: AppStore) {
        screenState.value = DialogStates.ExecuteErrorCallback(store.getUserReadableName())
    }

    private fun observeUpdateInProgressStatus() {
        viewModelScope.launch {
           isUpdateInProgressUseCase().collectLatest {
                screenState.value = if (it) DialogStates.ShowUpdateInProgress else DialogStates.HideUpdateInProgress
            }
        }
    }

    override fun onCleared() {
        TypefaceHolder.clear()
        ErrorCallbackHolder.clear()
        super.onCleared()
    }
}
