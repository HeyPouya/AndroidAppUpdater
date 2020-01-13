package ir.heydarii.appupdater.stores

import android.content.Context
import android.content.Intent
import android.net.Uri
import ir.heydarii.appupdater.pojo.Store
import ir.heydarii.appupdater.pojo.UpdaterStoreList

/**
 * shows apk in bazaar store
 */
class MyketStore : Stores() {
    override fun setStoreData(context: Context?, item: UpdaterStoreList) {
        val intent = Intent()
        intent.action = Intent.ACTION_VIEW
        intent.data = Uri.parse("myket://details?id=${item.packageName}")
        showStore(context, intent, item, Store.MYKET)
    }
}