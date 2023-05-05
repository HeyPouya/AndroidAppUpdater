package com.pouyaheydari.appupdater.core.stores

import android.content.Intent
import android.net.Uri
import com.pouyaheydari.appupdater.core.pojo.Store
import com.pouyaheydari.appupdater.core.pojo.StoreListItem

const val APTOIDE_URL = "aptoideinstall://package="
const val APTOIDE_PACKAGE = "cm.aptoide.pt"

/**
 * Opens application's page in [Aptoide App Store](https://en.aptoide.com/)
 */
class Aptoide : Stores() {
    override fun setStoreData(item: StoreListItem) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("$APTOIDE_URL${item.packageName}")).run {
            setPackage(APTOIDE_PACKAGE)
        }
        setData(intent, item, Store.APTOIDE)
    }
}
