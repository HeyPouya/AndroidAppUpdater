package com.pouyaheydari.appupdater.core.stores

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.pouyaheydari.appupdater.core.pojo.Store
import com.pouyaheydari.appupdater.core.pojo.UpdaterStoreList

private const val APP_GALLERY_URL = "appmarket://details?id="

/**
 * shows apk in Huawei App Gallery store
 */
class HuaweiAppGallery : Stores() {
    override fun setStoreData(context: Context?, item: UpdaterStoreList) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("$APP_GALLERY_URL${item.packageName}"))
        showStore(context, intent, item, Store.HUAWEI_APP_GALLERY)
    }
}
