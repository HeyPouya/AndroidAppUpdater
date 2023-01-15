package com.pouyaheydari.appupdater.core.stores

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.pouyaheydari.appupdater.core.pojo.Store
import com.pouyaheydari.appupdater.core.pojo.StoreListItem

const val LENOVO_APP_CENTER_URL = "leapp://ptn/appinfo.do?pn="

/**
 * Opens application's page in [Lenovo App Store](https://www.lenovomm.com/)
 */
class LenovoAppCenter : Stores() {
    override fun setStoreData(context: Context?, item: StoreListItem) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("$LENOVO_APP_CENTER_URL${item.packageName}"))
        showStore(context, intent, item, Store.LENOVO_APP_CENTER)
    }
}
