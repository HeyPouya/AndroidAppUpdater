package com.pouyaheydari.appupdater.core.stores

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.pouyaheydari.appupdater.core.pojo.Store
import com.pouyaheydari.appupdater.core.pojo.UpdaterStoreList

private const val PLAY_URL = "market://details?id="

/**
 * shows apk in GooglePlay store
 */
class GooglePlayStore : Stores() {
    override fun setStoreData(context: Context?, item: UpdaterStoreList) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("$PLAY_URL${item.packageName}"))
        showStore(context, intent, item, Store.GOOGLE_PLAY)
    }
}
