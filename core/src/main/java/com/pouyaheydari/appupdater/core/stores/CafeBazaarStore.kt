package com.pouyaheydari.appupdater.core.stores

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.pouyaheydari.appupdater.core.pojo.Store
import com.pouyaheydari.appupdater.core.pojo.UpdaterStoreList

const val BAZAAR_URL = "bazaar://details?id="
const val BAZAAR_PACKAGE = "com.farsitel.bazaar"

/**
 * shows apk in CafeBazaar store
 */
class CafeBazaarStore : Stores() {
    override fun setStoreData(context: Context?, item: UpdaterStoreList) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("$BAZAAR_URL${item.packageName}")).run {
            setPackage(BAZAAR_PACKAGE)
        }
        showStore(context, intent, item, Store.CAFE_BAZAAR)
    }
}
