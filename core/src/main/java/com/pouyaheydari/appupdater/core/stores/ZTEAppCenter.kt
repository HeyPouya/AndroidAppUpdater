package com.pouyaheydari.appupdater.core.stores

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.pouyaheydari.appupdater.core.pojo.Store
import com.pouyaheydari.appupdater.core.pojo.StoreListItem

const val ZTE_APP_CENTER_URL = "zte_market://appdetails?pname="

/**
 * Opens application's page in [ZTE App Store](https://apps.ztems.com/)
 */
class ZTEAppCenter : Stores() {
    override fun setStoreData(context: Context?, item: StoreListItem) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("$ZTE_APP_CENTER_URL${item.packageName}"))
        showStore(context, intent, item, Store.ZTE_APP_CENTER)
    }
}
