package com.pouyaheydari.appupdater.core.stores

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.pouyaheydari.appupdater.core.pojo.Store
import com.pouyaheydari.appupdater.core.pojo.StoreListItem

const val HUAWEI_APP_GALLERY_URL = "appmarket://details?id="
const val HUAWEI_APP_GALLERY_PACKAGE = "com.huawei.appmarket"

/**
 * Opens application's page in [Huawei App Gallery](https://appgallery.huawei.com/)
 */
class HuaweiAppGallery : Stores() {
    override fun setStoreData(context: Context?, item: StoreListItem) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("$HUAWEI_APP_GALLERY_URL${item.packageName}")).run {
            setPackage(HUAWEI_APP_GALLERY_PACKAGE)
        }
        showStore(context, intent, item, Store.HUAWEI_APP_GALLERY)
    }
}
