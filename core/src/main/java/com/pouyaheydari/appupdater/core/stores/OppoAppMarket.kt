package com.pouyaheydari.appupdater.core.stores

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.pouyaheydari.appupdater.core.pojo.Store
import com.pouyaheydari.appupdater.core.pojo.UpdaterStoreList

const val OPPO_APP_MARKET_URL = "market://details?id="
const val OPPO_PACKAGE = "com.heytap.market"

/**
 * Opens application's page in [OppoAppMarket](https://oppomobile.com/)
 */
class OppoAppMarket : Stores() {
    override fun setStoreData(context: Context?, item: UpdaterStoreList) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("$OPPO_APP_MARKET_URL${item.packageName}")).run {
            setPackage(OPPO_PACKAGE)
        }
        showStore(context, intent, item, Store.OPPO_APP_MARKET)
    }
}
