package com.pouyaheydari.appupdater.core.stores

import android.content.Intent
import android.net.Uri
import com.pouyaheydari.appupdater.core.pojo.Store
import com.pouyaheydari.appupdater.core.pojo.StoreListItem

const val OPPO_APP_MARKET_URL = "market://details?id="
const val OPPO_PACKAGE = "com.heytap.market"

/**
 * Opens application's page in [OppoAppMarket](https://oppomobile.com/)
 */
class OppoAppMarket : Stores() {
    override fun setStoreData(item: StoreListItem) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("$OPPO_APP_MARKET_URL${item.packageName}")).run {
            setPackage(OPPO_PACKAGE)
        }
        setData(intent, item, Store.OPPO_APP_MARKET)
    }
}
