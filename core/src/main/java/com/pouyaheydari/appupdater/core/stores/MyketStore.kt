package com.pouyaheydari.appupdater.core.stores

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.pouyaheydari.appupdater.core.pojo.Store
import com.pouyaheydari.appupdater.core.pojo.UpdaterStoreList

const val MYKET_URL = "myket://details?id="
const val MYKET_PACKAGE = "ir.mservices.market"

/**
 * shows apk in Myket store
 */
class MyketStore : Stores() {
    override fun setStoreData(context: Context?, item: UpdaterStoreList) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("$MYKET_URL${item.packageName}")).run {
            setPackage(MYKET_PACKAGE)
        }
        showStore(context, intent, item, Store.MYKET)
    }
}
