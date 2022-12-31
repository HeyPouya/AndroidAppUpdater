package com.pouyaheydari.appupdater.core.stores

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.pouyaheydari.appupdater.core.pojo.Store
import com.pouyaheydari.appupdater.core.pojo.UpdaterStoreList

const val TENCENT_APP_STORE_URL = "tmast://appdetails?pname="

/**
 * Opens application's page in [Tencent App Store](https://appstore.tencent.com/)
 */
class TencentAppStore : Stores() {
    override fun setStoreData(context: Context?, item: UpdaterStoreList) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("$TENCENT_APP_STORE_URL${item.packageName}"))
        showStore(context, intent, item, Store.TENCENT_APPS_STORE)
    }
}
