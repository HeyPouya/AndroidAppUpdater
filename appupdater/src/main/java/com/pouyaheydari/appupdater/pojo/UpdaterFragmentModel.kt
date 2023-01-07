package com.pouyaheydari.appupdater.pojo

import com.pouyaheydari.appupdater.core.pojo.Theme
import com.pouyaheydari.appupdater.core.pojo.UpdaterStoreList
import java.io.Serializable

/**
 * This model is used to pass the data to dialog fragment via bundles
 */
data class UpdaterFragmentModel(
    var title: String = "",
    var description: String = "",
    var storeLis: List<UpdaterStoreList> = listOf(),
    var isForceUpdate: Boolean = false,
    var theme: Theme = Theme.LIGHT,
) : Serializable {

    companion object {
        val EMPTY = UpdaterFragmentModel()
    }
}
