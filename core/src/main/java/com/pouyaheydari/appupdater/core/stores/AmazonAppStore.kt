package com.pouyaheydari.appupdater.core.stores

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.pouyaheydari.appupdater.core.pojo.Store
import com.pouyaheydari.appupdater.core.pojo.UpdaterStoreList

const val AMAZON_APP_STORE_URL = "amzn://apps/android?p="
const val AMAZON_PACKAGE = "com.amazon.venezia"

/**
 * shows apk in Amazon App Store
 */
class AmazonAppStore : Stores() {
    override fun setStoreData(context: Context?, item: UpdaterStoreList) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("$AMAZON_APP_STORE_URL${item.packageName}")).run {
            setPackage(AMAZON_PACKAGE)
        }
        showStore(context, intent, item, Store.AMAZON_APP_STORE)
    }
}
