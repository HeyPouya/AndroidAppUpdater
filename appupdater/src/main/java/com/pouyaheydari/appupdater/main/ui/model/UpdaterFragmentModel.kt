package com.pouyaheydari.appupdater.main.ui.model

import android.os.Parcelable
import com.pouyaheydari.appupdater.core.model.Theme
import com.pouyaheydari.appupdater.directdownload.data.DirectDownloadListItem
import com.pouyaheydari.appupdater.store.domain.StoreListItem
import kotlinx.parcelize.Parcelize

/**
 * This model is used to pass the data to dialog fragment via bundles.
 */
@Parcelize
internal data class UpdaterFragmentModel(
    val title: String = "",
    val description: String = "",
    val storeList: List<StoreListItem> = listOf(),
    val directDownloadList: List<DirectDownloadListItem> = listOf(),
    val isForceUpdate: Boolean = false,
    val theme: Theme = Theme.SYSTEM_DEFAULT,
) : Parcelable {
    companion object {
        val EMPTY = UpdaterFragmentModel()
    }
}
