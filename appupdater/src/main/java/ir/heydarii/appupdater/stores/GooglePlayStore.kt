package ir.heydarii.appupdater.stores

import android.content.Context
import android.content.Intent
import android.net.Uri
import ir.heydarii.appupdater.pojo.Store
import ir.heydarii.appupdater.pojo.UpdaterStoreList

/**
 * shows apk in GooglePlay store
 */
private const val PLAY_URL = "market://details?id="

class GooglePlayStore : Stores() {
    override fun setStoreData(context: Context?, item: UpdaterStoreList) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("$PLAY_URL${item.packageName}"))
        showStore(context, intent, item, Store.GOOGLE_PLAY)
    }
}
