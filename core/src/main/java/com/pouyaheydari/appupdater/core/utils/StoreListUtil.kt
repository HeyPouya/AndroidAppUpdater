package com.pouyaheydari.appupdater.core.utils

import com.pouyaheydari.appupdater.core.pojo.Store
import com.pouyaheydari.appupdater.core.pojo.StoreListItem

/**
 * Checks if both [Store.DIRECT_URL] & any other stores available or not.
 * Based on the result, a divider gets shown in the update dialog
 */
fun shouldShowStoresDivider(directDownloadList: List<StoreListItem>, storeList: List<StoreListItem>): Boolean =
    directDownloadList.isNotEmpty() && storeList.isNotEmpty()
