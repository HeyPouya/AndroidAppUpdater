package com.pouyaheydari.appupdater.core.stores

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.pouyaheydari.appupdater.core.pojo.Store
import com.pouyaheydari.appupdater.core.pojo.UpdaterStoreList

const val V_APP_STORE_URL = "vivoMarket://details?id="

/**
 * Opens application's page in [V-AppStore](https://developer.vivo.com/home)
 */
class VAppStore : Stores() {
    override fun setStoreData(context: Context?, item: UpdaterStoreList) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("$V_APP_STORE_URL${item.packageName}"))
        showStore(context, intent, item, Store.V_APP_STORE)
    }
}
