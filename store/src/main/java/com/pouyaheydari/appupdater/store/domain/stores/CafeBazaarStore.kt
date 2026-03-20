package com.pouyaheydari.appupdater.store.domain.stores

import com.pouyaheydari.appupdater.store.domain.StoreIntentBuilder
import kotlinx.parcelize.Parcelize

internal const val BAZAAR_URL = "bazaar://details?id="
internal const val BAZAAR_PACKAGE = "com.farsitel.bazaar"

/**
 * Opens application's page in [CafeBazaar App Store](https://cafebazaar.ir)
 */
@Parcelize
internal data class CafeBazaarStore(
    val packageName: String,
) : AppStore {
    override fun getIntent() = StoreIntentBuilder
        .Builder("$BAZAAR_URL$packageName")
        .withPackage(BAZAAR_PACKAGE)
        .build()

    override fun getType(): AppStoreType = AppStoreType.CAFE_BAZAAR

    override fun getUserReadableName(): String = AppStoreType.CAFE_BAZAAR.userReadableName
}
