package com.pouyaheydari.appupdater.core.pojo

import com.pouyaheydari.appupdater.core.R
import java.io.Serializable

/**
 * The model that we are using for list of stores
 */
data class StoreListItem(
    var store: Store = Store.DIRECT_URL,
    var title: String = "Store",
    var icon: Int = R.drawable.appupdater_ic_cloud,
    var url: String = "",
    var packageName: String = "",
) : Serializable
