package com.pouyaheydari.appupdater.core.utils

import com.pouyaheydari.appupdater.core.pojo.Store
import com.pouyaheydari.appupdater.core.pojo.UpdaterStoreList

/**
 * Checks if both [Store.DIRECT_URL] & any other stores available or not.
 * Based on the result, a divider gets shown in the update dialog
 */
fun areDirectAndStoresAvailable(storeList: List<UpdaterStoreList>): Boolean = storeList.map { it.store }
    .distinct()
    .toList()
    .partition {
        it == Store.DIRECT_URL
    }.run {
        first.isNotEmpty() && second.isNotEmpty()
    }
