package com.pouyaheydari.appupdater.core.stores

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.pouyaheydari.appupdater.core.pojo.Store
import com.pouyaheydari.appupdater.core.pojo.UpdaterStoreList

private const val BAZAAR_URL = "bazaar://details?id="
private const val BAZAAR_PACKAGE = "com.farsitel.bazaar"

/**
 * shows apk in CafeBazaar store
 */
class CafeBazaarStore : Stores() {
    override fun setStoreData(context: Context?, item: UpdaterStoreList) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse("$BAZAAR_URL${item.packageName}")
        intent.setPackage(BAZAAR_PACKAGE)
        showStore(context, intent, item, Store.CAFE_BAZAAR)
    }
}
