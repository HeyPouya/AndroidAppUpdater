package com.pouyaheydari.appupdater.core.pojo

import android.graphics.Typeface
import com.pouyaheydari.appupdater.core.R
import java.io.Serializable

/**
 * User has to pass this model to the library
 */
data class UpdaterFragmentModel(
    var title: String = "",
    var description: String = "",
    var list: List<UpdaterStoreList> = listOf(),
    var isForceUpdate: Boolean = false,
    var typeface: Typeface? = null,
    var theme: Theme = Theme.LIGHT
) : Serializable

/**
 * The model that we are using for list of stores
 */
data class UpdaterStoreList(
    var store: Store = Store.DIRECT_URL,
    var title: String = "Store",
    var icon: Int = R.drawable.appupdater_ic_cloud,
    var url: String = "",
    var packageName: String = ""
) : Serializable
