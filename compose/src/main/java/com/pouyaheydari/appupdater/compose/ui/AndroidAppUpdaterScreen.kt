package com.pouyaheydari.appupdater.compose.ui

import android.app.Activity
import android.app.DownloadManager
import android.content.Context
import android.content.ContextWrapper
import android.os.Build
import android.util.Log
import androidx.annotation.VisibleForTesting
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pouyaheydari.appupdater.compose.ui.components.AppUpdaterDialogComponent
import com.pouyaheydari.appupdater.compose.ui.components.UpdateInProgressDialogComponent
import com.pouyaheydari.appupdater.compose.ui.models.DialogScreenIntents
import com.pouyaheydari.appupdater.compose.ui.models.ErrorWhileOpeningStore
import com.pouyaheydari.appupdater.compose.ui.models.UpdaterDialogData
import com.pouyaheydari.appupdater.compose.ui.theme.AndroidAppUpdaterTheme
import com.pouyaheydari.appupdater.compose.ui.utils.previewDirectDownloadListData
import com.pouyaheydari.appupdater.compose.ui.utils.previewStoreListData
import com.pouyaheydari.appupdater.core.model.Theme
import com.pouyaheydari.appupdater.core.utils.ANDROID_APP_UPDATER_DEBUG_TAG
import com.pouyaheydari.appupdater.directdownload.utils.donwloadapk.checkPermissionsAndDownloadApk
import com.pouyaheydari.appupdater.directdownload.utils.installapk.installAPK
import com.pouyaheydari.appupdater.store.domain.AppStoreCallback
import com.pouyaheydari.appupdater.store.domain.showAppInSelectedStore
import com.pouyaheydari.appupdater.store.domain.stores.AppStore
import java.io.File
import com.pouyaheydari.appupdater.directdownload.R as directDownloadR

/**
 * Use this composable to show the updater dialog.
 *
 * @param dialogData is th
 */
@Composable
fun AndroidAppUpdater(dialogData: UpdaterDialogData) {
    AndroidAppUpdaterScreen(dialogData = dialogData)
}

@VisibleForTesting
@Composable
internal fun AndroidAppUpdaterScreen(
    dialogData: UpdaterDialogData,
    viewModel: AndroidAppUpdaterViewModel = viewModel(factory = AndroidAppUpdaterViewModelFactory(dialogData)),
) {
    val state = viewModel.uiState.collectAsStateWithLifecycle().value

    AndroidAppUpdaterTheme(darkTheme = isDarkThemeSelected(dialogData.theme, isSystemInDarkTheme())) {
        val activity = LocalContext.current.getActivity()

        AnimatedVisibility(state.shouldShowDialog) {
            AppUpdaterDialogComponent(dialogContent = state.dialogContent, typeface = dialogData.typeface)
        }

        UpdateInProgressDialogComponent(
            isUpdateInProgress = state.downloadState.shouldShowUpdateInProgress,
            dialogTitle = stringResource(id = (directDownloadR.string.appupdater_please_wait)),
            dialogDescription = stringResource(id = (directDownloadR.string.appupdater_downloading_new_version)),
            typeface = dialogData.typeface,
        )

        setupErrorCallback(state.errorWhileOpeningStore, dialogData.errorWhileOpeningStoreCallback) {
            viewModel.handleIntent(DialogScreenIntents.OnErrorCallbackExecuted)
        }

        setupStoreOpener(
            store = state.selectedStore,
            shouldOpenStore = state.shouldOpenStore,
            context = activity,
            onStoreOpenedListener = { viewModel.handleIntent(DialogScreenIntents.OnStoreOpened) },
            onOpeningStoreFailed = { viewModel.handleIntent(DialogScreenIntents.OnOpeningStoreFailed(it)) },
        )

        setupDirectApkDownload(
            url = state.downloadState.downloadUrl,
            shouldStartAPKDownload = state.downloadState.shouldStartAPKDownload,
            activity = activity,
            notificationTitle = stringResource(com.pouyaheydari.appupdater.directdownload.R.string.appupdater_download_notification_title),
            notificationDescription = stringResource(com.pouyaheydari.appupdater.directdownload.R.string.appupdater_download_notification_desc),
            onDownloadApkRequested = { viewModel.handleIntent(DialogScreenIntents.OnApkDownloadRequested) },
            onDownloadingApkStarted = { viewModel.handleIntent(DialogScreenIntents.OnApkDownloadStarted) },
        )

        setupApkInstallation(
            state.downloadState.shouldInstallApk,
            state.downloadState.apk,
            activity,
        ) { viewModel.handleIntent(DialogScreenIntents.OnApkInstallationStarted) }
    }
}

private fun setupApkInstallation(shouldInstallApk: Boolean, apk: File, activity: Activity?, onApkInstallationStartedListener: () -> Unit) {
    if (shouldInstallApk) {
        installAPKIfActivityIsNotNull(activity, apk)
        onApkInstallationStartedListener()
    }
}

private fun installAPKIfActivityIsNotNull(activity: Activity?, apk: File) {
    if (activity == null) {
        Log.e(ANDROID_APP_UPDATER_DEBUG_TAG, "Provided activity is null. Skipping apk installation")
    } else {
        installAPK(activity, apk, Build.VERSION.SDK_INT)
    }
}

private fun isDarkThemeSelected(theme: Theme, isSystemDefaultInDarkTheme: Boolean): Boolean = when (theme) {
    Theme.DARK -> true
    Theme.LIGHT -> false
    Theme.SYSTEM_DEFAULT -> isSystemDefaultInDarkTheme
}

private fun setupErrorCallback(
    errorWhileOpeningStore: ErrorWhileOpeningStore,
    callback: (String) -> Unit,
    onCallbackCalledListener: () -> Unit,
) {
    if (errorWhileOpeningStore.shouldNotifyCaller) {
        callback(errorWhileOpeningStore.storeName)
        onCallbackCalledListener()
    }
}

private fun setupDirectApkDownload(
    url: String,
    shouldStartAPKDownload: Boolean,
    activity: Activity?,
    notificationTitle: String,
    notificationDescription: String,
    onDownloadApkRequested: () -> Unit,
    onDownloadingApkStarted: () -> Unit,
) {
    if (shouldStartAPKDownload) {
        getApkIfActivityIsNotNull(
            activity = activity,
            url = url,
            notificationTitle = notificationTitle,
            notificationDescription = notificationDescription,
            onDownloadingApkStarted = onDownloadingApkStarted
        )
        onDownloadApkRequested()
    }
}

private fun getApkIfActivityIsNotNull(
    activity: Activity?,
    url: String,
    notificationTitle: String,
    notificationDescription: String,
    onDownloadingApkStarted: () -> Unit
) {
    if (activity == null) {
        Log.e(ANDROID_APP_UPDATER_DEBUG_TAG, "Provided activity is null. Skipping downloading the apk")
    } else {
        checkPermissionsAndDownloadApk(
            url = url,
            activity = activity,
            androidSdkVersion = Build.VERSION.SDK_INT,
            notificationTitle = notificationTitle,
            notificationDescription = notificationDescription,
            downloadManager = activity.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager,
            onDownloadingApkStarted = onDownloadingApkStarted
        )
    }
}

private fun Context.getActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.getActivity()
    else -> null
}

private fun setupStoreOpener(
    store: AppStore,
    shouldOpenStore: Boolean,
    context: Context?,
    onStoreOpenedListener: () -> Unit,
    onOpeningStoreFailed: (AppStore) -> Unit,
) {
    if (shouldOpenStore) {
        showAppInSelectedStore(context, store) { callback ->
            when (callback) {
                is AppStoreCallback.Failure -> onOpeningStoreFailed(store)
                is AppStoreCallback.Success -> onStoreOpenedListener()
            }
        }
    }
}

@PreviewFontScale
@PreviewScreenSizes
@Composable
private fun LightPreview() {
    AndroidAppUpdaterTheme {
        AndroidAppUpdater(
            UpdaterDialogData(
                dialogTitle = stringResource(id = directDownloadR.string.appupdater_app_name),
                dialogDescription = stringResource(id = directDownloadR.string.appupdater_download_notification_desc),
                storeList = previewStoreListData,
                directDownloadList = previewDirectDownloadListData,
                theme = Theme.LIGHT,
            ),
        )
    }
}

@PreviewFontScale
@PreviewScreenSizes
@Composable
private fun LightPreviewSingleStoreItem() {
    AndroidAppUpdaterTheme {
        AndroidAppUpdater(
            UpdaterDialogData(
                dialogTitle = stringResource(id = directDownloadR.string.appupdater_app_name),
                dialogDescription = stringResource(id = directDownloadR.string.appupdater_download_notification_desc),
                storeList = previewStoreListData.subList(0, 1),
                theme = Theme.LIGHT,
            ),
        )
    }
}

@PreviewFontScale
@PreviewScreenSizes
@Composable
private fun LightPreviewSingleDirectLinkItem() {
    AndroidAppUpdaterTheme {
        AndroidAppUpdater(
            UpdaterDialogData(
                dialogTitle = stringResource(id = directDownloadR.string.appupdater_app_name),
                dialogDescription = stringResource(id = directDownloadR.string.appupdater_download_notification_desc),
                storeList = listOf(),
                directDownloadList = previewDirectDownloadListData.subList(0, 1),
                theme = Theme.LIGHT,
            ),
        )
    }
}

@PreviewFontScale
@PreviewScreenSizes
@Composable
private fun DarkPreview() {
    AndroidAppUpdaterTheme {
        AndroidAppUpdater(
            UpdaterDialogData(
                dialogTitle = stringResource(id = directDownloadR.string.appupdater_app_name),
                dialogDescription = stringResource(id = directDownloadR.string.appupdater_download_notification_desc),
                storeList = previewStoreListData,
                directDownloadList = previewDirectDownloadListData,
                theme = Theme.DARK,
            ),
        )
    }
}
