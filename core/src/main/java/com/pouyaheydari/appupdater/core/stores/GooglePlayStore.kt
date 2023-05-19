package com.pouyaheydari.appupdater.core.stores

import android.content.Intent
import android.net.Uri
import com.pouyaheydari.appupdater.core.pojo.Store
import com.pouyaheydari.appupdater.core.pojo.StoreListItem

const val PLAY_URL = "market://details?id="
const val PLAY_PACKAGE = "com.android.vending"

/**
 * Opens application's page in [GooglePlay Store](https://play.google.com)
 */
class GooglePlayStore : Stores() {

    override fun setStoreData(item: StoreListItem) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("$PLAY_URL${item.packageName}")).run {
            setPackage(PLAY_PACKAGE)
        }
        setData(intent, item, Store.GOOGLE_PLAY)
    }
}
