package com.pouyaheydari.appupdater.store.domain.stores

import com.pouyaheydari.appupdater.store.domain.StoreIntentBuilder
import kotlinx.parcelize.Parcelize

internal const val HUAWEI_APP_GALLERY_URL = "appmarket://details?id="
internal const val HUAWEI_APP_GALLERY_PACKAGE = "com.huawei.appmarket"

/**
 * Opens application's page in [Huawei App Gallery](https://appgallery.huawei.com/)
 */
@Parcelize
internal data class HuaweiAppGallery(
    val packageName: String,
) : AppStore {
    override fun getIntent() = StoreIntentBuilder
        .Builder("$HUAWEI_APP_GALLERY_URL$packageName")
        .withPackage(HUAWEI_APP_GALLERY_PACKAGE)
        .build()

    override fun getType(): AppStoreType = AppStoreType.HUAWEI_APP_GALLERY

    override fun getUserReadableName(): String = AppStoreType.HUAWEI_APP_GALLERY.userReadableName
}
