package ir.heydarii.appupdater.stores

import android.content.Context
import android.content.Intent
import android.net.Uri
import ir.heydarii.appupdater.pojo.Store
import ir.heydarii.appupdater.pojo.UpdaterStoreList

/**
 * shows apk in IranApps store
 */
private const val IRAN_APPS_URL = "iranapps://app/"
private const val IRAN_APPS_PACKAGE = "ir.tgbs.android.iranapp"
class IranAppsStore : Stores() {
    override fun setStoreData(context: Context?, item: UpdaterStoreList) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse("$IRAN_APPS_URL${item.packageName}")
        intent.setPackage(IRAN_APPS_PACKAGE)
        showStore(context, intent, item, Store.IRAN_APPS)
    }
}