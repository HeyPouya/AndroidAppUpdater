package com.pouyaheydari.appupdater.main.ui.model

import com.pouyaheydari.appupdater.store.domain.stores.AppStore
import java.io.File

internal sealed interface DialogScreenStates {
    data object Empty : DialogScreenStates
    data object ShowUpdateInProgress : DialogScreenStates
    data object HideUpdateInProgress : DialogScreenStates
    data class OpenStore(val store: AppStore) : DialogScreenStates
    data class DownloadApk(val apkUrl: String) : DialogScreenStates
    data class ExecuteErrorCallback(val storeName: String) : DialogScreenStates
    data class InstallApk(val apk: File) : DialogScreenStates
}
