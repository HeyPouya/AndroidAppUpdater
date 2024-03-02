package com.pouyaheydari.appupdater.pojo

import android.graphics.Typeface
import com.pouyaheydari.androidappupdater.store.model.StoreListItem
import com.pouyaheydari.androidappupdater.store.model.Theme

/**
 * This model is used to pass the data to dialog fragment via bundles
 */
data class UpdaterDialogData(
    var title: String = "",
    var description: String = "",
    var storeList: List<StoreListItem> = listOf(),
    var isForceUpdate: Boolean = false,
    var typeface: Typeface? = null,
    var theme: Theme = Theme.SYSTEM_DEFAULT,
)
