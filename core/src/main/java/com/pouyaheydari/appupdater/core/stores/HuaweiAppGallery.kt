package com.pouyaheydari.appupdater.core.stores

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.pouyaheydari.appupdater.core.pojo.Store
import com.pouyaheydari.appupdater.core.pojo.UpdaterStoreList

const val HUAWEI_APP_GALLERY_URL = "appmarket://details?id="
const val HUAWEI_APP_GALLERY_PACKAGE = "com.huawei.appmarket"

/**
 * shows apk in Huawei App Gallery store
 */
class HuaweiAppGallery : Stores() {
    override fun setStoreData(context: Context?, item: UpdaterStoreList) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("$HUAWEI_APP_GALLERY_URL${item.packageName}")).run {
            setPackage(HUAWEI_APP_GALLERY_PACKAGE)
        }
        showStore(context, intent, item, Store.HUAWEI_APP_GALLERY)
    }
}
