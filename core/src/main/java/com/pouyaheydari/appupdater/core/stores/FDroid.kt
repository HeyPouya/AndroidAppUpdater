package com.pouyaheydari.appupdater.core.stores

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.pouyaheydari.appupdater.core.pojo.Store
import com.pouyaheydari.appupdater.core.pojo.UpdaterStoreList

const val FDROID_URL = "fdroid.app://details?id="
const val FDROID_PACKAGE = "org.fdroid.fdroid"

/**
 * shows apk in F-Droid store
 */
class FDroid : Stores() {
    override fun setStoreData(context: Context?, item: UpdaterStoreList) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("$FDROID_URL${item.packageName}")).run {
            setPackage(FDROID_PACKAGE)
        }
        showStore(context, intent, item, Store.FDROID)
    }
}
