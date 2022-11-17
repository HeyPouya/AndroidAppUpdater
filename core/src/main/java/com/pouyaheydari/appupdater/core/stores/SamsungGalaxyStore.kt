package com.pouyaheydari.appupdater.core.stores

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.pouyaheydari.appupdater.core.pojo.Store
import com.pouyaheydari.appupdater.core.pojo.UpdaterStoreList

private const val GALAXY_STORE_URL = "samsungapps://ProductDetail/"

/**
 * shows apk in Samsung Galaxy store
 */
class SamsungGalaxyStore : Stores() {
    override fun setStoreData(context: Context?, item: UpdaterStoreList) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("$GALAXY_STORE_URL${item.packageName}"))
        showStore(context, intent, item, Store.SAMSUNG_GALAXY_STORE)
    }
}
