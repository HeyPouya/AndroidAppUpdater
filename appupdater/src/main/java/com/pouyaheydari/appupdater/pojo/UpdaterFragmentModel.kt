package com.pouyaheydari.appupdater.pojo

import com.pouyaheydari.appupdater.core.pojo.StoreListItem
import com.pouyaheydari.appupdater.core.pojo.Theme
import java.io.Serializable

/**
 * This model is used to pass the data to dialog fragment via bundles
 */
data class UpdaterFragmentModel(
    var title: String = "",
    var description: String = "",
    var storeLis: List<StoreListItem> = listOf(),
    var isForceUpdate: Boolean = false,
    var theme: Theme = Theme.SYSTEM_DEFAULT,
) : Serializable {

    companion object {
        val EMPTY = UpdaterFragmentModel()
    }
}
