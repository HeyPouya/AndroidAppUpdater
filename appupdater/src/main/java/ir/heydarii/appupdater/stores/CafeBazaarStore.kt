package ir.heydarii.appupdater.stores

import android.content.Context
import android.content.Intent
import android.net.Uri
import ir.heydarii.appupdater.pojo.Store
import ir.heydarii.appupdater.pojo.UpdaterStoreList

/**
 * shows apk in CafeBazaar store
 */
private const val BAZAAR_URL = "bazaar://details?id="
private const val BAZAAR_PACKAGE = "com.farsitel.bazaar"

class CafeBazaarStore : Stores() {
    override fun setStoreData(context: Context?, item: UpdaterStoreList) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse("$BAZAAR_URL${item.packageName}")
        intent.setPackage(BAZAAR_PACKAGE)
        showStore(context, intent, item, Store.CAFE_BAZAAR)
    }
}