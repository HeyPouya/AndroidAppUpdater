package com.pouyaheydari.appupdater.dsl.pojo

import android.graphics.Typeface
import com.pouyaheydari.appupdater.core.pojo.Theme
import com.pouyaheydari.appupdater.core.pojo.UpdaterStoreList
import java.io.Serializable

/**
 * This model is used to pass the data to dialog fragment via bundles
 */
data class UpdaterFragmentModel(
    var title: String = "",
    var description: String = "",
    var storeList: List<UpdaterStoreList> = listOf(),
    var isForceUpdate: Boolean = false,
    var typeface: Typeface? = null,
    var theme: Theme = Theme.LIGHT,
) : Serializable
