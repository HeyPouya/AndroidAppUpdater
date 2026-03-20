package com.pouyaheydari.appupdater.compose.ui.models

import android.graphics.Typeface
import com.pouyaheydari.appupdater.core.model.Theme
import com.pouyaheydari.appupdater.directdownload.data.DirectDownloadListItem
import com.pouyaheydari.appupdater.store.domain.StoreListItem

/**
 * Configuration data for the [com.pouyaheydari.appupdater.compose.ui.AndroidAppUpdater] composable.
 *
 * @property dialogTitle title text shown at the top of the update dialog
 * @property dialogDescription description text shown below the title
 * @property dividerText text displayed on the divider between store list and direct download list
 * @property storeList list of app stores the user can choose to update from
 * @property directDownloadList list of direct APK download links
 * @property onDismissRequested callback invoked when the user requests to dismiss the dialog
 * @property errorWhileOpeningStoreCallback callback invoked with the store name when a store fails to open
 * @property typeface optional custom [Typeface] applied to all text in the dialog
 * @property theme the visual [Theme] for the dialog (light, dark, or system default)
 */
data class UpdaterDialogData(
    val dialogTitle: String = "",
    val dialogDescription: String = "",
    val dividerText: String = "",
    val storeList: List<StoreListItem> = listOf(),
    val directDownloadList: List<DirectDownloadListItem> = listOf(),
    val onDismissRequested: () -> Unit = {},
    val errorWhileOpeningStoreCallback: (String) -> Unit = {},
    val typeface: Typeface? = null,
    val theme: Theme = Theme.SYSTEM_DEFAULT,
)
