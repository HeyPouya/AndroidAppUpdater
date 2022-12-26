package com.pouyaheydari.appupdater.core.stores

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.pouyaheydari.appupdater.core.pojo.Store
import com.pouyaheydari.appupdater.core.pojo.UpdaterStoreList

private const val FDROID_URL = "fdroid.app://details?id="

/**
 * shows apk in F-Droid store
 */
class FDroid : Stores() {
    override fun setStoreData(context: Context?, item: UpdaterStoreList) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("$FDROID_URL${item.packageName}"))
        showStore(context, intent, item, Store.FDROID)
    }
}
