package com.pouyaheydari.appupdater.main.ui.model

import android.graphics.Typeface
import com.pouyaheydari.appupdater.core.model.Theme
import com.pouyaheydari.appupdater.directdownload.data.DirectDownloadListItem
import com.pouyaheydari.appupdater.store.domain.StoreListItem

/**
 * Configuration data for the updater dialog.
 *
 * @property title the title displayed at the top of the dialog
 * @property description the description text displayed below the title
 * @property storeList list of app stores to display as update options
 * @property directDownloadList list of direct APK download links
 * @property isForceUpdate when true, the dialog cannot be dismissed by the user
 * @property typeface optional custom typeface for all text in the dialog
 * @property errorWhileOpeningStoreCallback optional callback invoked with the store name when opening a store fails
 * @property theme the visual theme for the dialog
 */
data class UpdaterDialogData(
    val title: String = "",
    val description: String = "",
    val storeList: List<StoreListItem> = listOf(),
    val directDownloadList: List<DirectDownloadListItem> = listOf(),
    val isForceUpdate: Boolean = false,
    val typeface: Typeface? = null,
    val errorWhileOpeningStoreCallback: ((String) -> Unit)? = null,
    val theme: Theme = Theme.SYSTEM_DEFAULT,
)
