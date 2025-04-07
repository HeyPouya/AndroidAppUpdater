package com.pouyaheydari.appupdater.compose.ui.models

import com.pouyaheydari.appupdater.store.domain.StoreFactory
import com.pouyaheydari.appupdater.store.domain.stores.AppStore
import com.pouyaheydari.appupdater.store.domain.stores.AppStoreType
import java.io.File

internal data class DialogScreenState(
    val shouldShowDialog: Boolean = true,
    val errorWhileOpeningStore: ErrorWhileOpeningStore = ErrorWhileOpeningStore(),
    val selectedStore: AppStore = StoreFactory.getStore(AppStoreType.GOOGLE_PLAY, ""),
    val shouldOpenStore: Boolean = false,
    val dialogContent: UpdaterDialogUIData = UpdaterDialogUIData(),
    val downloadState: ApkDownloadState = ApkDownloadState(),
)

internal data class ErrorWhileOpeningStore(
    val shouldNotifyCaller: Boolean = false,
    val storeName: String = "",
)

internal data class ApkDownloadState(
    val shouldStartAPKDownload: Boolean = false,
    val downloadUrl: String = "",
    val shouldShowUpdateInProgress: Boolean = false,
    val shouldInstallApk: Boolean = false,
    val apk: File = File(""),
)
