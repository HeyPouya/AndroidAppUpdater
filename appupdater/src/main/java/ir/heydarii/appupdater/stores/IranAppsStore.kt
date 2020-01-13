package ir.heydarii.appupdater.stores

import android.content.Context
import android.content.Intent
import android.net.Uri
import ir.heydarii.appupdater.pojo.Store
import ir.heydarii.appupdater.pojo.UpdaterStoreList

/**
 * shows apk in bazaar store
 */
class IranAppsStore : Stores() {
    override fun setStoreData(context: Context?, item: UpdaterStoreList) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setPackage("ir.tgbs.android.iranapp")
        intent.data = Uri.parse("iranapps://app/${item.packageName}")
        showStore(context, intent, item, Store.IRAN_APPS)
    }
}