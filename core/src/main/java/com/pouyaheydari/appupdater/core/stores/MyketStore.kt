package com.pouyaheydari.appupdater.core.stores

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.pouyaheydari.appupdater.core.pojo.Store
import com.pouyaheydari.appupdater.core.pojo.UpdaterStoreList

private const val MYKET_URL = "myket://details?id="

/**
 * shows apk in Myket store
 */
class MyketStore : Stores() {
    override fun setStoreData(context: Context?, item: UpdaterStoreList) {
        val intent = Intent()
        intent.action = Intent.ACTION_VIEW
        intent.data = Uri.parse("$MYKET_URL${item.packageName}")
        showStore(context, intent, item, Store.MYKET)
    }
}
