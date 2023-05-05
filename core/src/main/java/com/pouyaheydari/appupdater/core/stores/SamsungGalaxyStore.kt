package com.pouyaheydari.appupdater.core.stores

import android.content.Intent
import android.net.Uri
import com.pouyaheydari.appupdater.core.pojo.Store
import com.pouyaheydari.appupdater.core.pojo.StoreListItem

const val SAMSUNG_GALAXY_STORE_URL = "samsungapps://ProductDetail/"

/**
 * Opens application's page in [Samsung Galaxy store](https://www.samsung.com/de/apps/galaxy-store/)
 */
class SamsungGalaxyStore : Stores() {
    override fun setStoreData(item: StoreListItem) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("$SAMSUNG_GALAXY_STORE_URL${item.packageName}"))
        setData(intent, item, Store.SAMSUNG_GALAXY_STORE)
    }
}
