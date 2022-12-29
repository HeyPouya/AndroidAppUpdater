package com.pouyaheydari.appupdater.core.stores

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.pouyaheydari.appupdater.core.pojo.Store
import com.pouyaheydari.appupdater.core.pojo.UpdaterStoreList

const val ONE_STORE_APP_MARKET_URL = "onestore://common/product/"

/**
 * shows apk in [OneStore](https://m.onestore.co.kr/mobilepoc/main/main.omp)
 */
class OneStoreAppMarket : Stores() {
    override fun setStoreData(context: Context?, item: UpdaterStoreList) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("$ONE_STORE_APP_MARKET_URL${item.packageName}"))
        showStore(context, intent, item, Store.MI_GET_APP_STORE)
    }
}
