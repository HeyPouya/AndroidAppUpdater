package com.pouyaheydari.appupdater.core.stores

import android.content.Intent
import android.net.Uri
import com.pouyaheydari.appupdater.core.pojo.Store
import com.pouyaheydari.appupdater.core.pojo.StoreListItem

const val NINE_APPS_STORE_URL = "nineapps://AppDetail?id="
const val NINE_APPS_PACKAGE = "com.gamefun.apk2u"

/**
 * Opens application's page in [9-Apps](https://www.9apps.com/)
 */
class NineApps : Stores() {
    override fun setStoreData(item: StoreListItem) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("$NINE_APPS_STORE_URL${item.packageName}")).run {
            setPackage(NINE_APPS_PACKAGE)
        }
        setData(intent, item, Store.NINE_APPS_STORE)
    }
}
