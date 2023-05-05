package com.pouyaheydari.appupdater.core.stores

import android.content.Intent
import android.net.Uri
import com.pouyaheydari.appupdater.core.pojo.Store
import com.pouyaheydari.appupdater.core.pojo.StoreListItem

const val AMAZON_APP_STORE_URL = "amzn://apps/android?p="
const val AMAZON_PACKAGE = "com.amazon.venezia"

/**
 * Opens application's page in [Amazon App Store](https://www.amazon.com/gp/mas/get/amazonapp)
 */
class AmazonAppStore : Stores() {
    override fun setStoreData(item: StoreListItem) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("$AMAZON_APP_STORE_URL${item.packageName}")).run {
            setPackage(AMAZON_PACKAGE)
        }
        setData(intent, item, Store.AMAZON_APP_STORE)
    }
}
