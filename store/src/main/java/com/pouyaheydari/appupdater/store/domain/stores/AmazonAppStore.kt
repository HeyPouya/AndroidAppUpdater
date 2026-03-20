package com.pouyaheydari.appupdater.store.domain.stores

import com.pouyaheydari.appupdater.store.domain.StoreIntentBuilder
import kotlinx.parcelize.Parcelize

internal const val AMAZON_APP_STORE_URL = "amzn://apps/android?p="
internal const val AMAZON_PACKAGE = "com.amazon.venezia"

/**
 * Opens application's page in [Amazon App Store](https://www.amazon.com/gp/mas/get/amazonapp)
 */
@Parcelize
internal data class AmazonAppStore(
    val packageName: String,
) : AppStore {
    override fun getIntent() =
        StoreIntentBuilder
            .Builder("$AMAZON_APP_STORE_URL$packageName")
            .withPackage(AMAZON_PACKAGE)
            .build()

    override fun getType(): AppStoreType = AppStoreType.AMAZON_APP_STORE

    override fun getUserReadableName(): String = AppStoreType.AMAZON_APP_STORE.userReadableName
}
