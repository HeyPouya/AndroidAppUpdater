package ir.heydarii.appupdater.stores

import android.content.Context
import android.content.Intent
import android.net.Uri
import ir.heydarii.appupdater.pojo.Store
import ir.heydarii.appupdater.pojo.UpdaterStoreList

/**
 * shows apk in CafeBazaar store
 */
class CafeBazaarStore : Stores() {
    override fun setStoreData(context: Context?, item: UpdaterStoreList) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse("bazaar://details?id=${item.packageName}")
        intent.setPackage("com.farsitel.bazaar")
        showStore(context, intent, item, Store.CAFE_BAZAAR)
    }
}