package com.pouyaheydari.appupdater.core.stores

import android.content.Intent
import android.net.Uri
import com.pouyaheydari.appupdater.core.pojo.Store
import com.pouyaheydari.appupdater.core.pojo.StoreListItem

const val V_APP_STORE_URL = "vivoMarket://details?id="

/**
 * Opens application's page in [V-AppStore](https://developer.vivo.com/home)
 */
class VAppStore : Stores() {
    override fun setStoreData(item: StoreListItem) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("$V_APP_STORE_URL${item.packageName}"))
        setData(intent, item, Store.V_APP_STORE)
    }
}
