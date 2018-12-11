package ir.heydarii.appupdater.stores

import android.content.Context
import android.content.Intent
import android.net.Uri
import ir.heydarii.appupdater.pojomodel.Store
import ir.heydarii.appupdater.pojomodel.UpdaterStoreList

/**
 * shows apk in bazaar store
 */
class CafeBazaarStore : Stores() {
    override fun setStoreData(context: Context?, item: UpdaterStoreList) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse("bazaar://details?id=${item.packageName}")
        intent.setPackage("com.farsitel.bazaar")
        showStore(context, intent, item, Store.CAFE_BAZAAR)
    }
}