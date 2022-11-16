package com.pouyaheydari.appupdater.core.stores

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.pouyaheydari.appupdater.core.pojo.Store
import com.pouyaheydari.appupdater.core.pojo.UpdaterStoreList

private const val IRAN_APPS_URL = "iranapps://app/"
private const val IRAN_APPS_PACKAGE = "ir.tgbs.android.iranapp"

/**
 * shows apk in IranApps store
 */
class IranAppsStore : Stores() {
    override fun setStoreData(context: Context?, item: UpdaterStoreList) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse("$IRAN_APPS_URL${item.packageName}")
        intent.setPackage(IRAN_APPS_PACKAGE)
        showStore(context, intent, item, Store.IRAN_APPS)
    }
}
