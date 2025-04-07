package com.pouyaheydari.appupdater.main.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pouyaheydari.appupdater.directdownload.domain.DownloadState
import com.pouyaheydari.appupdater.directdownload.domain.GetDownloadStateUseCase
import com.pouyaheydari.appupdater.directdownload.domain.SetDownloadStateUseCase
import com.pouyaheydari.appupdater.main.ui.model.DialogScreenIntents
import com.pouyaheydari.appupdater.main.ui.model.DialogScreenStates
import com.pouyaheydari.appupdater.main.utils.ErrorCallbackHolder
import com.pouyaheydari.appupdater.main.utils.TypefaceHolder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

internal class AppUpdaterViewModel(
    private val isUpdateInProgressUseCase: GetDownloadStateUseCase,
    private val setDownloadStateUseCase: SetDownloadStateUseCase
) : ViewModel() {
    val screenState = MutableStateFlow<DialogScreenStates>(DialogScreenStates.HideUpdateInProgress)

    fun handleIntent(intent: DialogScreenIntents) {
        when (intent) {
            is DialogScreenIntents.OnDirectLinkClicked -> screenState.value = DialogScreenStates.DownloadApk(intent.item.url)
            is DialogScreenIntents.OnStoreClicked -> screenState.value = DialogScreenStates.OpenStore(intent.item.store)
            DialogScreenIntents.OnStoreOpened -> screenState.value = DialogScreenStates.Empty
            DialogScreenIntents.OnErrorCallbackExecuted -> screenState.value = DialogScreenStates.Empty
            DialogScreenIntents.OnApkDownloadRequested -> screenState.value = DialogScreenStates.Empty
            DialogScreenIntents.OnApkDownloadStarted -> {
                setUpdateInProgress()
                observeUpdateInProgressStatus()
            }

            is DialogScreenIntents.OnOpeningStoreFailed ->
                screenState.value = DialogScreenStates.ExecuteErrorCallback(intent.store.getUserReadableName())

            DialogScreenIntents.OnApkInstallationStarted -> screenState.value = DialogScreenStates.Empty
        }
    }

    private fun setUpdateInProgress() {
        viewModelScope.launch {
            setDownloadStateUseCase(DownloadState.Downloading)
        }
    }

    private fun observeUpdateInProgressStatus() {
        viewModelScope.launch {
            isUpdateInProgressUseCase().collectLatest { downloadState ->
                screenState.value = when (downloadState) {
                    is DownloadState.Downloaded ->
                        DialogScreenStates.InstallApk(downloadState.apk)

                    is DownloadState.Downloading ->
                        DialogScreenStates.ShowUpdateInProgress
                }
            }
        }
    }

    override fun onCleared() {
        TypefaceHolder.clear()
        ErrorCallbackHolder.clear()
        super.onCleared()
    }
}
