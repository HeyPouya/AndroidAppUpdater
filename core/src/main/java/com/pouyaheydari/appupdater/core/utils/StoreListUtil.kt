package com.pouyaheydari.appupdater.core.utils

import com.pouyaheydari.appupdater.core.pojo.Store
import com.pouyaheydari.appupdater.core.pojo.UpdaterStoreList

fun areDirectAndStoresAvailable(storeList: List<UpdaterStoreList>): Boolean = storeList.map { it.store }
    .distinct()
    .toList()
    .partition {
        it == Store.DIRECT_URL
    }.run {
        first.isNotEmpty() && second.isNotEmpty()
    }
