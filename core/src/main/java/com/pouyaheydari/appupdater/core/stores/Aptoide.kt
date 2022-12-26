package com.pouyaheydari.appupdater.core.stores

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.pouyaheydari.appupdater.core.pojo.Store
import com.pouyaheydari.appupdater.core.pojo.UpdaterStoreList

private const val APTOIDE_URL = "aptoideinstall://package="

/**
 * shows apk in Aptoide Store
 */
class Aptoide : Stores() {
    override fun setStoreData(context: Context?, item: UpdaterStoreList) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("$APTOIDE_URL${item.packageName}"))
        showStore(context, intent, item, Store.APTOIDE)
    }
}
