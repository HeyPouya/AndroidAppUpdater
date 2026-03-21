package com.pouyaheydari.appupdater.compose.dsl

import android.graphics.Typeface
import com.pouyaheydari.appupdater.compose.ui.models.UpdaterDialogData
import com.pouyaheydari.appupdater.core.model.Theme
import com.pouyaheydari.appupdater.directdownload.data.DirectDownloadListItem
import com.pouyaheydari.appupdater.store.domain.StoreListItem
import com.pouyaheydari.appupdater.store.domain.stores.AppStore
import com.pouyaheydari.appupdater.store.R as storeR

/**
 * Mutable builder for [StoreListItem] used in DSL context.
 */
class StoreListItemBuilder {
    var store: AppStore? = null
    var title: String = ""
    var icon: Int = storeR.drawable.appupdater_ic_cloud

    fun build(): StoreListItem {
        val store = requireNotNull(store) { "StoreListItemBuilder requires 'store' to be set." }
        return StoreListItem(store = store, title = title, icon = icon)
    }
}

/**
 * Mutable builder for [DirectDownloadListItem] used in DSL context.
 */
class DirectDownloadListItemBuilder {
    var title: String = ""
    var url: String = ""

    fun build(): DirectDownloadListItem {
        require(url.isNotBlank()) { "DirectDownloadListItemBuilder requires 'url' to be set." }
        return DirectDownloadListItem(title = title, url = url)
    }
}

/**
 * Mutable builder for [UpdaterDialogData] used in DSL context.
 */
class UpdaterDialogDataBuilder {
    var dialogTitle: String = ""
    var dialogDescription: String = ""
    var dividerText: String = ""
    var storeList: List<StoreListItem> = listOf()
    var directDownloadList: List<DirectDownloadListItem> = listOf()
    var onDismissRequested: () -> Unit = {}
    var errorWhileOpeningStoreCallback: (String) -> Unit = {}
    var typeface: Typeface? = null
    var theme: Theme = Theme.SYSTEM_DEFAULT

    fun build(): UpdaterDialogData = UpdaterDialogData(
        dialogTitle = dialogTitle,
        dialogDescription = dialogDescription,
        dividerText = dividerText,
        storeList = storeList,
        directDownloadList = directDownloadList,
        onDismissRequested = onDismissRequested,
        errorWhileOpeningStoreCallback = errorWhileOpeningStoreCallback,
        typeface = typeface,
        theme = theme,
    )
}

/**
 * DSL builder for constructing a [StoreListItem].
 *
 * Example usage:
 * ```
 * val item = store {
 *     store = StoreFactory.getStore(AppStoreType.GOOGLE_PLAY, "com.example.app")
 *     title = "Google Play"
 *     icon = R.drawable.ic_google_play
 * }
 * ```
 */
inline fun store(block: StoreListItemBuilder.() -> Unit): StoreListItem =
    StoreListItemBuilder().apply(block).build()

/**
 * DSL builder for constructing a [DirectDownloadListItem].
 *
 * Example usage:
 * ```
 * val item = directDownload {
 *     title = "Direct APK"
 *     url = "https://example.com/app.apk"
 * }
 * ```
 */
inline fun directDownload(block: DirectDownloadListItemBuilder.() -> Unit): DirectDownloadListItem =
    DirectDownloadListItemBuilder().apply(block).build()

/**
 * DSL builder for constructing an [UpdaterDialogData] for the Compose updater.
 *
 * Example usage:
 * ```
 * val dialogData = updaterDialogData {
 *     dialogTitle = "New Update Available"
 *     dialogDescription = "Version 2.0 is ready"
 *     storeList = listOf(
 *         store {
 *             store = StoreFactory.getStore(AppStoreType.GOOGLE_PLAY, "com.example.app")
 *             title = "Google Play"
 *         }
 *     )
 *     theme = Theme.SYSTEM_DEFAULT
 * }
 * ```
 */
inline fun updaterDialogData(block: UpdaterDialogDataBuilder.() -> Unit): UpdaterDialogData =
    UpdaterDialogDataBuilder().apply(block).build()
