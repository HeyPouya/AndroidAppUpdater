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
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

/**
 * ViewModel for the app updater dialog (XML/View module).
 *
 * Uses [StateFlow] for persistent UI state (e.g. update-in-progress indicator) and
 * [Channel] for one-shot side-effects (e.g. open store, download APK) that should
 * not replay on configuration change.
 */
internal class AppUpdaterViewModel(
    private val isUpdateInProgressUseCase: GetDownloadStateUseCase,
    private val setDownloadStateUseCase: SetDownloadStateUseCase,
) : ViewModel() {
    /** Persistent UI state that survives configuration changes. */
    private val _screenState = MutableStateFlow<DialogScreenUiState>(DialogScreenUiState.Idle)
    val screenState: StateFlow<DialogScreenUiState> = _screenState.asStateFlow()

    /** One-shot side-effect events consumed exactly once by the UI. */
    private val _sideEffect = Channel<DialogScreenStates>(Channel.BUFFERED)
    val sideEffect = _sideEffect.receiveAsFlow()

    fun handleIntent(intent: DialogScreenIntents) {
        when (intent) {
            is DialogScreenIntents.OnDirectLinkClicked ->
                _sideEffect.trySend(DialogScreenStates.DownloadApk(intent.item.url))

            is DialogScreenIntents.OnStoreClicked ->
                _sideEffect.trySend(DialogScreenStates.OpenStore(intent.item.store))

            is DialogScreenIntents.OnOpeningStoreFailed ->
                _sideEffect.trySend(DialogScreenStates.ExecuteErrorCallback(intent.store.getUserReadableName()))

            DialogScreenIntents.OnApkDownloadStarted -> {
                setUpdateInProgress()
                observeUpdateInProgressStatus()
            }

            // These intents signal the UI consumed a side-effect; no further action needed.
            DialogScreenIntents.OnStoreOpened,
            DialogScreenIntents.OnErrorCallbackExecuted,
            DialogScreenIntents.OnApkDownloadRequested,
            DialogScreenIntents.OnApkInstallationStarted,
            -> { /* consumed */ }
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
                when (downloadState) {
                    is DownloadState.Downloaded -> {
                        _screenState.value = DialogScreenUiState.Idle
                        _sideEffect.trySend(DialogScreenStates.InstallApk(downloadState.apk))
                    }

                    is DownloadState.Downloading ->
                        _screenState.value = DialogScreenUiState.UpdateInProgress

                    is DownloadState.Failed ->
                        _screenState.value = DialogScreenUiState.Idle
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

/**
 * Persistent UI state for the updater dialog.
 * This state survives configuration changes and is safe to replay.
 */
internal sealed interface DialogScreenUiState {
    /** No download in progress; idle state. */
    data object Idle : DialogScreenUiState

    /** An APK download is in progress; show the progress indicator. */
    data object UpdateInProgress : DialogScreenUiState
}
